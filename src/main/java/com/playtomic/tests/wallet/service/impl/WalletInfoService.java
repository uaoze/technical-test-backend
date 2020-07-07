package com.playtomic.tests.wallet.service.impl;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletInfoService implements com.playtomic.tests.wallet.service.WalletInfoService {

    @Override
	public BigDecimal checkWallet(int validId) {
		return BigDecimal.ZERO;
	}
}
