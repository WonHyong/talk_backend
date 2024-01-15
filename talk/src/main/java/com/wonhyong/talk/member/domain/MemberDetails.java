package com.wonhyong.talk.member.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class MemberDetails implements UserDetails {

    private final Member member;

    public MemberDetails(Member member) {this.member = member;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        System.out.println("ROLE_"+ member.getRole().toString());
        collectors.add(()->{return "ROLE_"+ member.getRole().toString();}); //add에 들어올 파라미터는 GrantedAuthority밖에 없으니

        return collectors;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public Long getId() {
        return member.getId();
    }

    public Role getRole() {
        return member.getRole();
    }
}
