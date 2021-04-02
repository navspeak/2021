package com.navspeak.ldapdemo;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface UserRepository extends LdapRepository<User> {
}