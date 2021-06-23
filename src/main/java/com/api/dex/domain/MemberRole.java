package com.api.dex.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Data
public class MemberRole implements Serializable, GrantedAuthority {

    public enum RoleType {
        ROLE_ADMIN,
        ROLE_USER
    }
    private RoleType roleName;

    public MemberRole(RoleType roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return this.roleName.name();
    }
}
