const fs = require('fs');
const ldapjsclient = require('ldapjs-client');
//https://www.forumsys.com/tutorials/integration-how-to/ldap/online-ldap-test-server/#comment-6953
//const tls = {
//    ca: [fs.readFileSync('/path/to/the/pem/file')]
//}
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
                  , "attributes" : [String, Array]
                  }
    , shortHands = { "default" : [
                      "--host", "ldap.forumsys.com",
                      "--port", "389",
                      "--username", "cn=read-only-admin,dc=example,dc=com",
                      "--password", "password",
                      "--dn", "uid=navneet,ou=People,dc=navspeak,dc=com"
                      ]
                    }
    , parsed = nopt(knownOpts, shortHands, process.argv, 2)

  parsed.url = `ldap://${parsed.host}:${parsed.port}`
  console.log(parsed);
  let change = {
     [parsed.name] : parsed.value
  }
  console.log(change)
  modify()

  //ldapsearch -x -H ldap://127.0.0.1:3004 -b "dc=test" "(&(objectclass=person)(cn=user-login))" attribute1 attribute2
  async function modify(){
      const client = new ldapjsclient({url: parsed.url});
      try {
          await client.bind(parsed.username, parsed.password)
          const result = await client.modify(parsed.dn, {
            operation: 'delete',
            modification: {homePhone: []}
        })
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
 */