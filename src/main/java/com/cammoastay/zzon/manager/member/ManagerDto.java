package com.cammoastay.zzon.manager.member;

import com.cammoastay.zzon.reservation.entities.ReserveEntity;

public record ManagerDto (
        Long managerNo,
        String userId,
        String userName,
        String managerPasswd,
        String managerEmail,
        String managerPhone,
        String businessNumber,
        String campName,
        String isDelete,
        String role
){
    public static ManagerDto from(ManagerEntity managerEntity) {
        return new ManagerDto(
                managerEntity.getManagerNo(),
                managerEntity.getUserId(),
                managerEntity.getUserName(),
                managerEntity.getManagerPasswd(),
                managerEntity.getManagerEmail(),
                managerEntity.getManagerPhone(),
                managerEntity.getBusinessNumber(),
                managerEntity.getCampName(),
                managerEntity.getIsDelete(),
                managerEntity.getRole()
        );
    }
    public static ManagerEntity to(ManagerDto managerDto) {
        return ManagerEntity.builder()
                .managerNo(managerDto.managerNo)
                .userId(managerDto.userId)
                .userName(managerDto.userName)
                .businessNumber(managerDto.businessNumber)
                .managerPasswd(managerDto.managerPasswd)
                .managerEmail(managerDto.managerEmail)
                .managerPhone(managerDto.managerPhone)
                .campName(managerDto.campName)
                .isDelete(managerDto.isDelete)
                .role(managerDto.role)
                .build();
    }
}
