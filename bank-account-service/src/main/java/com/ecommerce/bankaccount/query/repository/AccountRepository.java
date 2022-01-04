package com.ecommerce.bankaccount.query.repository;

import com.ecommerce.bankaccount.query.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {
}
