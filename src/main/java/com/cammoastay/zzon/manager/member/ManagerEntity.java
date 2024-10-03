package com.cammoastay.zzon.manager.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
public class ManagerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long managerNo;
    String userId;
    String userName;
    String managerPasswd;
    String managerEmail;
    String managerPhone;
    String businessNumber;
    String campName;
    String isDelete;
    String role;

    // 기본 생성자
    public ManagerEntity() {}

    // 모든 필드를 포함한 생성자
    public ManagerEntity(Long managerNo, String userId, String userName, String managerPasswd, String managerEmail, String managerPhone, String businessNumber, String campName, String isDelete, String role) {
        this.managerNo = managerNo;
        this.userId = userId;
        this.userName = userName;
        this.managerPasswd = managerPasswd;
        this.managerEmail = managerEmail;
        this.managerPhone = managerPhone;
        this.businessNumber = businessNumber;
        this.campName = campName;
        this.isDelete = isDelete;
        this.role = role;
    }

    // ManagerDto의 값을 엔티티 필드에 반영하는 메서드
    public void updateFromDto(ManagerDto managerDto) {
        this.userId = managerDto.userId();           // getter 메서드 대신 record 필드 접근 방식 사용
        this.userName = managerDto.userName();
        this.managerPasswd = managerDto.managerPasswd();
        this.managerEmail = managerDto.managerEmail();
        this.managerPhone = managerDto.managerPhone();
        this.businessNumber = managerDto.businessNumber();
        this.campName = managerDto.campName();
        this.isDelete = managerDto.isDelete();
        this.role = managerDto.role();
    }

    // Getter 메서드만 남기고, 불필요한 Setter 제거
    public Long getManagerNo() {
        return managerNo;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getManagerPasswd() {
        return managerPasswd;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public String getCampName() {
        return campName;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public String getRole() {
        return role;
    }
}

