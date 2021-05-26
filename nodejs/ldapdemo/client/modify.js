const fs = require('fs');
const ldapjsclient = require('ldapjs-client');
const nopt = require("nopt")
    , Stream = require("stream").Stream
    , path = require("path")
    , knownOpts = { "host" : [String, 'null']
                  , "port" : [String, 'null']
                  , "filter" : [String, 'null']
                  , "username" : [String, 'null']
                  , "password" : [String, 'null']
                  , "dn" : [String, null]
                  , "name" : [String, 'null']
                  , "value" : [String, 'null']
                  , "modifyAttrName" : [String, null]
                  , "modifyAttrValue" : [String, null]
                  }
    , shortHands = { "default" : [
                      "--host", "ldap.forumsys.com",
                      "--port", "389",
                      "--username", "cn=read-only-admin,dc=example,dc=com",
                      "--password", "password",
                      "--dn", "uid=navneet,ou=People,dc=navspeak,dc=com",
                      ]
                    }
    , parsed = nopt(knownOpts, shortHands, process.argv, 2)

  parsed.url = `ldap://${parsed.host}:${parsed.port}`
  if (parsed.modifyAttrName == undefined && parsed.modifyAttrValue == undefined){
    console.log("Missing modifyAttrName and modifyAttrValue");
    return;
  }
  let name = parsed.modifyAttrName;
  let val = parsed.modifyAttrValue;

  let modification =  {[name]: val};
  console.log(`Following modification will be made: ${JSON.stringify(modification)}`);

  modify();

  //ldapsearch -x -H ldap://127.0.0.1:3004 -b "dc=test" "(&(objectclass=person)(cn=user-login))" attribute1 attribute2
  async function modify(){
      const client = new ldapjsclient({url: parsed.url});
      try {
          await client.bind(parsed.username, parsed.password)
          const result = await client.modify(parsed.dn, {
            operation: 'replace',
            modification: {[name]: val}
        })
        console.log(result);
        return result;
    } catch(e){
        console.log(e);
    } finally {
        await client.unbind();
        await client.destroy();
    }
}
/*
node modify.js \
 --host ec2-3-82-157-48.compute-1.amazonaws.com \
 --port 389 \
 --username  cn=admin,dc=navspeak,dc=com \
 --password password \
 --dn uid=navneet,ou=People,dc=navspeak,dc=com\
 --filter objectClass=*

  node modify.js \
   --host localhost \
   --port 389 \
   --username  cn=read-only-admin,dc=example,dc=com \
   --password password \
   --dn uid=boyle,dc=example,dc=com \
   --filter objectClass=inetOrgPerson
   --modifyAttrName=main
   --modifyAttrValue
 */