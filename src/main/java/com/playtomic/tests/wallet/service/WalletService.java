package com.playtomic.tests.wallet.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.playtomic.tests.wallet.domain.Wallet;

public interface WalletService {
	
	Optional<Wallet> getWallet(int walletId);

	Optional<BigDecimal> discountAmount(int walletId, BigDecimal amount) throws BalanceBelowZeroException;
}
