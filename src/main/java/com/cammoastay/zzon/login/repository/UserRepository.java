package com.cammoastay.zzon.login.repository;


import com.cammoastay.zzon.login.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	UserEntity findByUsername(String username);
}
