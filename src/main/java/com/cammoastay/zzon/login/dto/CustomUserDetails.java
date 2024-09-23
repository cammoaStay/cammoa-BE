package com.cammoastay.zzon.login.dto;

import com.cammoastay.zzon.jointest.MemberEntity;
import com.cammoastay.zzon.login.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails  implements UserDetails {
// CustomUserDetails 클래스는 UserDetails 인터페이스를 구현하여 사용자의 인증 및 권한 부여를 처리합니다.

	private final MemberEntity userEntity;

    public CustomUserDetails(MemberEntity userEntity) {
        this.userEntity = userEntity;
    }



    // 사용자의 권한을 반환하는 메서드

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

    	// UserEntity 객체에서 권한을 추출하여 GrantedAuthority 객체로 변환하여 반환합니다.
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {

            @Override

            public String getAuthority() {

                return userEntity.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

        return userEntity.getPassWd();
    }

    @Override
    public String getUsername() {

        return userEntity.getUserId();
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
}
