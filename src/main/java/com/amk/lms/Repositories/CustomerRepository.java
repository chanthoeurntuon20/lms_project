package com.amk.lms.Repositories;

import com.amk.lms.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findById (Integer id);
    List<Customer> findAllByStatus(String status);
}
