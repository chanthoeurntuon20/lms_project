package com.amk.lms.Repositories;

import com.amk.lms.models.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByCusId(Integer cusId);
    List<Account> findAll();
    Account findByAcctNo(String acctNo);
}
