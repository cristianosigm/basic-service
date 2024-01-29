package com.cs.sigm.repository;

import com.cs.sigm.entity.LoginEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginEventRepository extends JpaRepository<LoginEvent, Integer> {
}
