package com.ahmad.myproject.appuser;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.ahmad.myproject.appuser.Permission.*;


public enum AppUserRole {



    USER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(USER_READ,USER_WRITE,ADMIN_READ,ADMIN_WRITE));

    private final Set<Permission> permissions;

    AppUserRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
    public Set<SimpleGrantedAuthority> getGrantedAuthority(){

        Set<SimpleGrantedAuthority> collect = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        collect.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return collect;
    }



}
