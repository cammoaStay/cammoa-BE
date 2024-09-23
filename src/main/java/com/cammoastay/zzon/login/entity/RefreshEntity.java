package com.cammoastay.zzon.login.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "refreshentity")
@Getter
@Setter
public class RefreshEntity {
// refresh 토큰 Entitiy
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	private String username;	
	private String refresh;
	private String expiration;
}
