package com.spring.creamBank.account.repository;

import com.spring.creamBank.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
