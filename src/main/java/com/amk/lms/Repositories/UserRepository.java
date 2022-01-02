package com.amk.lms.Repositories;

import com.amk.lms.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Integer> {
    Integer countByEmail(String email);
    User findByUserId(Integer userId);
    User findByEmail(String email);
    List<User> findAll();
}
