package com.cammoastay.zzon.login.service;

import com.cammoastay.zzon.jointest.MemberEntity;
import com.cammoastay.zzon.jointest.SaveTestRepository;
import com.cammoastay.zzon.login.dto.CustomUserDetails;
import com.cammoastay.zzon.login.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final SaveTestRepository memberRepository;

    public CustomUserDetailsService(UserRepository userRepository, SaveTestRepository memberRepository) {
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        Optional<MemberEntity> userData = memberRepository.findByUserId(username);

        if (userData.isPresent()) {
            return new CustomUserDetails(userData.orElse(null));
        }
        return null;
    }

}
