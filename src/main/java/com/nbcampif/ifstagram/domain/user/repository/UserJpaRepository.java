package com.nbcampif.ifstagram.domain.user.repository;

import com.nbcampif.ifstagram.domain.user.UserRole;
import com.nbcampif.ifstagram.domain.user.repository.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByRole(UserRole userRole);

<<<<<<< HEAD
    Optional<UserEntity> findByEmail(String email);
=======
    User findByEmail(String email);

    Optional<UserEntity> findById(Long id);
>>>>>>> 8fc7917 (fix)
}
