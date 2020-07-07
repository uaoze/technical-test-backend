package com.playtomic.tests.wallet.domain;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class WalletEntityToWalletMapper implements Function<WalletEntity, Wallet> {

	@Override
	public Wallet apply(WalletEntity walletEntity) {
		return (walletEntity != null) ? new Wallet(walletEntity.getWalletId(), walletEntity.getBalance()) : null;
	}
}
