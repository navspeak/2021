package com.navspeak.ldapdemo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor()
@Entry(objectClasses = { "inetOrgPerson", "organizationalPerson","person" })
public class User {
    @Id
    private Name id;
    private @DnAttribute(value = "uid", index = 0) String uid;
    private @Attribute(name = "cn") String username;
    private @Attribute(name = "sn") String lastname;

}
//uid=boyle,dc=example,dc=com
