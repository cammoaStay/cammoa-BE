package com.cammoastay.zzon.login.controller;


import com.cammoastay.zzon.login.service.ReissueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ReissueController {
	
	private final ReissueService reissueService;
	// 생성자 방식 주입.
	public ReissueController ( ReissueService reissueService) {
		
		this.reissueService = reissueService;
	}
	
	@PostMapping("/api/reissue")
	public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
		        return reissueService.reissue(request, response);
		    }
	}
	
	


