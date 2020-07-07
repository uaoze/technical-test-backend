package com.playtomic.tests.wallet.service;

import java.util.Optional;

import com.playtomic.tests.wallet.domain.Wallet;

public interface WalletInfoService {
	
	Optional<Wallet> checkWallet(int walletId);
}
