package com.cammoastay.zzon.camp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int campId;

    private String campName;
    private String campState;
    private String campCity;
    private String campAddress;
    private String campTel;
    private String campNotice;
    private String campIntro;
    private String campService;
    private String campRating;

    public CampEntity(int campId, String campName, String campState, String campAddress, String campTel, String campNotice, String campIntro, String campService, String campRating) {

    }

    public CampEntity() {

    }
}
