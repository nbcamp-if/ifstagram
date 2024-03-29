package com.nbcampif.ifstagram.domain.user.repository;

import com.nbcampif.ifstagram.domain.admin.dto.UserForceUpdateRequestDto;
import com.nbcampif.ifstagram.domain.user.UserRole;
import com.nbcampif.ifstagram.domain.user.dto.UserUpdateRequestDto;
import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.domain.user.repository.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepository {

  private final UserJpaRepository userJpaRepository;

  public void createUser(User user) {
    UserEntity userEntity = UserEntity.fromModel(user);
    userJpaRepository.save(userEntity);
  }

  public Optional<User> findUser(Long userId) {
    return userJpaRepository.findById(userId).map(UserEntity::toModel);
  }

  public User findUserOrElseThrow(Long userId) {
    return findUser(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
  }

  public User findByEmailOrElseThrow(String email) {
    return userJpaRepository.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("User not found"))
        .toModel();
  }

  public Optional<User> findByEmail(String email) {
    return userJpaRepository.findByEmail(email).map(UserEntity::toModel);
  }

  public boolean existsByRole(UserRole userRole) {
    return userJpaRepository.existsByRole(userRole);
  }

  public User updateUser(UserUpdateRequestDto requestDto, User savedUser) {
    Optional<UserEntity> userEntity = userJpaRepository.findByEmail(savedUser.getEmail());
    userEntity.get().update(requestDto);
    return userJpaRepository.save(userEntity.get()).toModel();
  }

  public User updateUser(UserForceUpdateRequestDto requestDto, User savedUser) {
    UserEntity userEntity = userJpaRepository.findById(savedUser.getUserId()).get();
    userEntity.update(requestDto);
    return userEntity.toModel();
  }

  public void updateReportedCount(User reportedUser) {
    UserEntity user = userJpaRepository.findById(reportedUser.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));
    user.updateReportedCount();
  }

}
