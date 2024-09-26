package com.cammoastay.zzon.login.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member")
public class UserEntity {
// 로그인을 위한 Entity

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="user_id")
	private String  username;
	@Column(name="user_pw")
	private String  password;
	@Column(name="user_authority")
	private String  role;

}
