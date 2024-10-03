package com.cammoastay.zzon.reservation.service;


import com.cammoastay.zzon.reservation.dtos.ReservationDto;
import com.cammoastay.zzon.reservation.entities.ReserveEntity;
import com.cammoastay.zzon.reservation.repositories.ReserveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class ReserveService {

    private final ReserveRepository reserveRepository;
     // 임시 결제 완료

    public ReserveService(ReserveRepository reserveRepository) {
        this.reserveRepository = reserveRepository;
    }

    @Transactional
    public ReserveEntity saveReservations(ReservationDto reservationDto) {

        // 외부 카카오페이 API 사용중 , 우리서버가 아니라서 외부 카카오페이쪽에서 결제완료들어오면
        // true , false 나눠서 결제완료, 실패 나눠서 save
            ReserveEntity dtoToEntity = ReservationDto.to(reservationDto);
        return reserveRepository.save(dtoToEntity);
//            else {
//            // 결제취소 또는 실패라면 리다이렉트
//            throw new IllegalStateException("Payment failed. Reservation cannot be completed");
//            }

    }


    public ReservationDto findByReserveId(Long reserveId) {
        ReserveEntity reserveEntity = reserveRepository.findById(reserveId)
                .orElseThrow(() -> new EntityNotFoundException(("예약 조회불가 id :" + reserveId)));
        return ReservationDto.from(reserveEntity);
    }

    @Transactional
    public void deleteReserve(Long reserveId) {
        reserveRepository.deleteById(reserveId);
    }
}