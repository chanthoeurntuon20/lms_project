package com.amk.lms.Repositories;

import com.amk.lms.models.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> findByOrderId(Integer orderId);
    List<OrderDetail> findAll();
}
