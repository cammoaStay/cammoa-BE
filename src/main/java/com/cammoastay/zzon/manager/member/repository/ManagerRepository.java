package com.cammoastay.zzon.manager.member.repository;

import com.cammoastay.zzon.manager.member.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, Long> , ManagerRepositoryCustom {

}
