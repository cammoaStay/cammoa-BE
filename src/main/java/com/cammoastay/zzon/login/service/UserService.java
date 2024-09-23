package com.cammoastay.zzon.login.service;


import com.cammoastay.zzon.login.entity.UserEntity;
import com.cammoastay.zzon.login.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public void deleteByUsername(String username ) {
		
		UserEntity user = userRepository.findByUsername(username);
		
		if(user != null ) {
			
			userRepository.delete(user);
			
		} else {
			throw new RuntimeException("비밀번호가 틀렸습니다");
		}	
	}
}
