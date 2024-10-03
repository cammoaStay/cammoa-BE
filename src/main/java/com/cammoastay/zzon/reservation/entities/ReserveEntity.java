package com.cammoastay.zzon.reservation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Builder
@Entity
@Table
@AllArgsConstructor
public class ReserveEntity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long resId;
    private String roomName;
    private LocalDate resDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalPrice;
    private boolean isPaymentSuccesful;

    public ReserveEntity() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReserveEntity that = (ReserveEntity) o;
        return totalPrice == that.totalPrice && isPaymentSuccesful == that.isPaymentSuccesful && Objects.equals(resId, that.resId) && Objects.equals(roomName, that.roomName) && Objects.equals(resDate, that.resDate) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resId, roomName, resDate, startDate, endDate, totalPrice, isPaymentSuccesful);
    }



    public Long getResId() {
        return resId;
    }

    public String getRoomName() {
        return roomName;
    }

    public LocalDate getResDate() {
        return resDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public boolean getIsPaymentSuccesful() {
        return isPaymentSuccesful;
    }








}
