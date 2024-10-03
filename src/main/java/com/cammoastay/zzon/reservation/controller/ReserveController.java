package com.cammoastay.zzon.reservation.controller;

import com.cammoastay.zzon.reservation.customExceptions.PaymentFailedException;
import com.cammoastay.zzon.reservation.dtos.ReservationDto;
import com.cammoastay.zzon.reservation.entities.ReserveEntity;
import com.cammoastay.zzon.reservation.service.ReserveService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReserveController {

    private final ReserveService reserveService;


    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @PostMapping("/api/v1/reserve")
    public ReserveEntity createReservations(@RequestBody ReservationDto reservationDto) {

        if(reservationDto.isPaymentSuccesful()) {
            return reserveService.saveReservations(reservationDto);
        }
        else {
            throw new PaymentFailedException(HttpStatus.BAD_REQUEST, "잘못된 결제 정보입니다.");
        }


    }

    @GetMapping("/api/v1/reservecheck/{reserveId}")
    public ReservationDto reserveCheck(@PathVariable Long reserveId) {
        return reserveService.findByReserveId(reserveId);
    }

    @DeleteMapping("/api/v1/reserveDelete/{reserveId}")
    public void reserveDelete(@PathVariable Long reserveId) {
         reserveService.deleteReserve(reserveId);
    }
}
