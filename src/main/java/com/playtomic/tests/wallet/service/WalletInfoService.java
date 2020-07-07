package com.playtomic.tests.wallet.service;

import java.math.BigDecimal;
import java.util.Optional;

public interface WalletInfoService {
	
	Optional<BigDecimal> checkWallet(int walletId);
}
