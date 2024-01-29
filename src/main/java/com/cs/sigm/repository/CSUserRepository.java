package com.cs.sigm.repository;

import com.cs.sigm.entity.CSUser;
import com.cs.sigm.entity.definition.CSUserRole;
import com.cs.sigm.entity.definition.LanguageCode;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CSUserRepository extends JpaRepository<CSUser, Integer> {

    Optional<CSUser> findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "update CSUser u set u.role = :role, u.language = :language where u.id = :userId")
    int updateProfile(@Param("userId") Integer userId, @Param("role") CSUserRole role, @Param("language") LanguageCode language);

}
