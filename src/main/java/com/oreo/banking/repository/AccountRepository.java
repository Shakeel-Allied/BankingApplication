package com.oreo.banking.repository;


import com.oreo.banking.model.Account;
import com.oreo.banking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUser(User user);
}
