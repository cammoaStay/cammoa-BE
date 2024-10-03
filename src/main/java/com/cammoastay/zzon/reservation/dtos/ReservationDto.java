package com.cammoastay.zzon.reservation.dtos;

import com.cammoastay.zzon.reservation.entities.ReserveEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationDto(
    Long resId,
    String roomName,
    LocalDate resDate,
    LocalDate startDate,
    LocalDate endDate,
    int totalPrice,
    boolean isPaymentSuccesful

) {

    public static ReservationDto from(ReserveEntity reserveEntity) {
        return new ReservationDto(
                reserveEntity.getResId(),
                reserveEntity.getRoomName(),
                reserveEntity.getResDate(),
                reserveEntity.getStartDate(),
                reserveEntity.getEndDate(),
                reserveEntity.getTotalPrice(),
                reserveEntity.getIsPaymentSuccesful()

        );
    }
     public static ReserveEntity to(ReservationDto reservationDto) {
                return ReserveEntity.builder()
                        .resId(reservationDto.resId)
                        .roomName(reservationDto.roomName)
                        .resDate(reservationDto.resDate)
                        .startDate(reservationDto.startDate)
                        .endDate(reservationDto.endDate)
                        .isPaymentSuccesful(reservationDto.isPaymentSuccesful)
                        .totalPrice(reservationDto.totalPrice)
                        .build();
        }


    }

