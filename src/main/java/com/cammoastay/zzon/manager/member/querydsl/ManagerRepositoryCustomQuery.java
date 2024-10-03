package com.cammoastay.zzon.manager.member.querydsl;

import com.cammoastay.zzon.manager.member.ManagerEntity;
import com.cammoastay.zzon.manager.member.QManagerEntity;
import com.cammoastay.zzon.manager.member.repository.ManagerRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class ManagerRepositoryCustomQuery implements ManagerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ManagerRepositoryCustomQuery(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<ManagerEntity> findByManagerNo(Long managerNo) {

        QManagerEntity qManagerEntity = QManagerEntity.managerEntity;

        ManagerEntity managerEntity = queryFactory
                .selectFrom(qManagerEntity)
                .where(qManagerEntity.managerNo.eq(managerNo))
                .fetchOne();
        return Optional.ofNullable(managerEntity);
        // nullable로 감싸 null 체킹은 서비스단에서 진행

    }
}
