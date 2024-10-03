package com.cammoastay.zzon.manager.member.repository;

import com.cammoastay.zzon.manager.member.ManagerEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepositoryCustom {
    Optional<ManagerEntity> findByManagerNo(Long managerNo);
}
