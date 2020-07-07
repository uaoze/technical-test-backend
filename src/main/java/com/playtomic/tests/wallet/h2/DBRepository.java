package com.playtomic.tests.wallet.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playtomic.tests.wallet.domain.WalletEntity;

@Repository
public interface DBRepository extends JpaRepository<WalletEntity, Integer> {

  WalletEntity findByWalletId(int walletId);
}
