package com.cammoastay.zzon.login.controller;

import com.cammoastay.zzon.login.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
	
	private final UserService userService;
	
	public LoginController(UserService userService) {
		
		this.userService = userService;
	}

	

	
	@DeleteMapping("/delete/{userId}")
	public String DeleteMember(@PathVariable("userId") String userId)
	{
		userService.deleteByUsername(userId);
		
		return "사용자 " + userId + "이 (가) 성공적으로 삭제 되었습니다. ";
		
	}
}
