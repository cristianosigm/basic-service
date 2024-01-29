package com.cs.sigm.repository;

import com.cs.sigm.entity.PasswordResetEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetEventRepository extends JpaRepository<PasswordResetEvent, Integer> {

    // @formatter:off
    @Query(value = "select pe from PasswordResetEvent pe where " +
                    "pe.userId = :userId and " +
                    "pe.completed is null and " +
                    "pe.successful = false " +
                    "order by pe.id desc limit 1")
    Optional<PasswordResetEvent> findPendingEventsByUserId(@Param("userId") Integer userId);
    // @formatter:on

}
