package br.com.zupacademy.fabio.ecommerce.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Roles implements GrantedAuthority {

    @Id
    private String authority;

    @Deprecated
    public Roles() {
    }

    public Roles(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
