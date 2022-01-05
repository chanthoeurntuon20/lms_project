package com.amk.lms.Repositories;

import com.amk.lms.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Optional<Order> findById(Integer id);
//    List<Order> findAllOrder();
}
