package com.cammoastay.zzon.reservation.repositories;

import com.cammoastay.zzon.reservation.entities.ReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<ReserveEntity, Long> {
}
