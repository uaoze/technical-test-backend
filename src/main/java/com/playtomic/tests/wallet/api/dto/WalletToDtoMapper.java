package com.playtomic.tests.wallet.api.dto;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.playtomic.tests.wallet.domain.Wallet;

@Component
public class WalletToDtoMapper implements Function<Wallet, WalletDto> {

	@Override
	public WalletDto apply(Wallet wallet) {
		return (wallet != null) ? new WalletDto(wallet.getWalletId(), wallet.getBalance()) : null;
	}
}
