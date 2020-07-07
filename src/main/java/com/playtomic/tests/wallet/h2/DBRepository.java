package com.playtomic.tests.wallet.h2;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.playtomic.tests.wallet.domain.WalletEntity;

@Repository
public interface DBRepository extends JpaRepository<WalletEntity, Integer> {

  WalletEntity findByWalletId(int walletId);
  
  @Query("update WalletEntity w set w.balance = ?1 where w.walletId = ?2")
  void updateAmountForWallet(int walletId, BigDecimal amount);
}
