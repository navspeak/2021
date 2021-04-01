#!/bin/bash
# Being just for test and demo, this scripts runs everything as su
# ensure permissions as needed
sudo su
#############################################################
# install openldap-servers and copy default DB config file
############################################################
yum install -y openldap-servers openldap-clients
cp /usr/share/openldap-servers/DB_CONFIG.example /var/lib/ldap/DB_CONFIG
# chown -R ec2-user:ec2-user /var/lib/ldap/*
#############################################################
# start slapd
############################################################
systemctl start slapd
systemctl enable slapd

################ SLAPD CONFIG FOLDER #######################
# ls -l  /etc/openldap/slapd.d/cn\=config/
# drwxr-x--- 2 ldap ldap 108 Mar 30 22:56 cn=schema
# -rw------- 1 ldap ldap 378 Mar 30 22:26 cn=schema.ldif
# -rw-r--r-- 1 root root  89 Mar 30 22:40 monitor.ldif
# -rw------- 1 ldap ldap 513 Mar 30 22:26 olcDatabase={0}config.ldif
# -rw------- 1 ldap ldap 443 Mar 30 22:26 olcDatabase={-1}frontend.ldif
# -rw------- 1 ldap ldap 605 Mar 30 22:48 olcDatabase={1}monitor.ldif
# -rw------- 1 ldap ldap 716 Mar 30 22:37 olcDatabase={2}hdb.ldif
###########################################################

mkdir /opt/openldap # folder to contain ldif files

#######################################################
# Create ldapdb.ldif to replace olcSuffix, olcRootDN and olcRootPW
#######################################################
echo -e "dn:olcDatabase={2}hdb,cn=config
changetype: modify
replace: olcSuffix
olcSuffix: dc=navspeak,dc=com

dn:olcDatabase={2}hdb,cn=config
changetype: modify
replace: olcRootDN
olcRootDN: cn=admin,dc=navspeak,dc=com

dn:olcDatabase={2}hdb,cn=config
changetype: modify
replace: olcRootPW
olcRootPW: {SSHA}R9XHJ5Ppf9YiZ+3cyOQw4iWNJ6xW7H8H " > /opt/openldap/ldapdb.ldif
# NOTE: the olcRootPW is generated using slappwd and is a hash of "password"
ldapmodify -Y EXTERNAL -H ldapi:/// -f /opt/openldap/ldapdb.ldif

#######################################################
# Create monitor.ldif to grant access to cn=admin
#######################################################
echo -e "dn: olcDatabase={1}monitor,cn=config
changetype: modify
replace: olcAccess" > /opt/openldap/monitor.ldif
echo olcAccess: {0}to * by dn.base="gidNumber=0+uidNumber=0,cn=peercred,cn=external,cn=auth" read by dn.base="cn=admin,dc=navspeak,dc=com" read by * none >> /opt/openldap/monitor.ldif
ldapmodify -Y EXTERNAL -H ldapi:/// -f /opt/openldap/monitor.ldif

#######################################################
# Add openldap schema
#######################################################
ldapadd -Y EXTERNAL -H ldapi:/// -f /etc/openldap/schema/cosine.ldif
ldapadd -Y EXTERNAL -H ldapi:/// -f /etc/openldap/schema/nis.ldif
ldapadd -Y EXTERNAL -H ldapi:/// -f /etc/openldap/schema/inetorgperson.ldif

#######################################################
# Create domain controller, People and group
#######################################################
echo -e "dn: dc=navspeak,dc=com
objectClass: domain
objectClass: top
dc: navspeak

dn: cn=admin,dc=navspeak,dc=com
objectClass: organizationalRole
cn: admin
description: LDAP Manager

dn: ou=People,dc=navspeak,dc=com
objectClass: organizationalUnit
ou: People

dn: ou=Group,dc=navspeak,dc=com
objectClass: organizationalUnit
ou: Group " > /opt/openldap/base.ldif
echo password | ldapadd -c -W -D "cn=admin,dc=navspeak,dc=com" -f /opt/openldap/base.ldif

#######################################################
# Add two users
#######################################################
echo -e "dn: uid=user1,ou=People,dc=navspeak,dc=com
objectClass: inetOrgPerson
objectClass: organizationalPerson
objectClass: person
objectClass: top
cn: User One
sn: One
uid: user1

dn: uid=user2,ou=People,dc=navspeak,dc=com
objectClass: inetOrgPerson
objectClass: organizationalPerson
objectClass: person
objectClass: top
cn: User Two
sn: Two
uid: user2" > /opt/openldap/users.ldif
ldapadd -x -w password -D "cn=admin,dc=navspeak,dc=com" -f /opt/openldap/users.ldif

# https://raw.githubusercontent.com/linuxautomations/openldap/master/install.sh