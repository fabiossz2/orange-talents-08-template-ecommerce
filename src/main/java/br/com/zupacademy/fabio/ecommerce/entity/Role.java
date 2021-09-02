package br.com.zupacademy.fabio.ecommerce.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;


@Entity
public class Role implements GrantedAuthority {

    public static final Role ROLE_ADMIN = new Role("ROLE_ADMIN");

    @Id
    private String authority;

    @Deprecated
    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(authority, role.authority);
    }
}
