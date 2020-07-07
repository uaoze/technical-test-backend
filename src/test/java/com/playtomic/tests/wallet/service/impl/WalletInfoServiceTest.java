package com.playtomic.tests.wallet.service.impl;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

public class WalletInfoServiceTest {

	private static final int VALID_ID = 101;
	
	WalletInfoService walletInfoService = new WalletInfoService();

    @Test
    public void checkWallet_validId_validBalance() {
    	BigDecimal balance = walletInfoService.checkWallet(VALID_ID);
    	
    	assertNotNull(balance);
    }
}
