package com.cammoastay.zzon.manager.member;

import com.cammoastay.zzon.manager.member.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public ManagerDto getManagerById(Long managerNo) {
         return managerRepository.findByManagerNo(managerNo)
                 .map(ManagerDto::from)
                 .orElseThrow(() -> new RuntimeException("Manager not found"));
        // 주어진 managerId로 Manager를 조회하고, 조회된 ManagerEntity를 ManagerDto로 변환하여 반환합니다.
        // 조회 결과가 없으면 "Manager not found"라는 메시지와 함께 RuntimeException을 던집니다.
    }

    @Transactional
    public void updateById(Long managerNo, ManagerDto managerDto) {
        ManagerEntity managerEntity = managerRepository.findById(managerNo)
                .orElseThrow(() -> new RuntimeException("Manager not found with id " + managerNo));
        managerEntity.updateFromDto(managerDto);
        // 트랜잭션시 JPA 변경감지로 Entity Update 되어 Save 쓰지않아도된다.
    }

    @Transactional
    public void deleteById(Long managerId) {
        managerRepository.deleteById(managerId);
    }
}
