package com.playtomic.tests.wallet.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.mockito.stubbing.Answer;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletEntity;
import com.playtomic.tests.wallet.domain.WalletEntityToWalletMapper;
import com.playtomic.tests.wallet.h2.DBRepository;
import com.playtomic.tests.wallet.service.WalletService;

public class WalletServiceTest {

	private static final int VALID_ID = 101;
	private static final int INVALID_ID = 999;
	private static final BigDecimal VALID_BALANCE = new BigDecimal(10);
	private static final BigDecimal VALID_AMOUNT = new BigDecimal(2);

	private DBRepository repository = mock(DBRepository.class);
	private WalletEntityToWalletMapper walletEntityToWalletMapper = new WalletEntityToWalletMapper();

	private WalletService walletService = new WalletServiceH2DB(repository, walletEntityToWalletMapper);

	@Test
	public void getWallet_validId_validBalance() {

		WalletEntity walletEntity = new WalletEntity(VALID_ID, VALID_BALANCE);
		given(repository.findByWalletId(VALID_ID)).willReturn(walletEntity);

		Optional<Wallet> returnedWallet = walletService.getWallet(VALID_ID);

		assertTrue(returnedWallet.isPresent());
		assertExpectedWallet(returnedWallet);
	}

	@Test
	public void getWallet_notExistingId_noBalanceReturned() {

		given(repository.findByWalletId(INVALID_ID)).willReturn(null);

		Optional<Wallet> returnedWallet = walletService.getWallet(INVALID_ID);

		assertFalse(returnedWallet.isPresent());
	}
	
	@Test
	public void discountAmount_validIdAndAmount_AmountDiscountedToWallet() {

		WalletEntity walletEntity = new WalletEntity(VALID_ID, VALID_BALANCE);
		given(repository.findByWalletId(VALID_ID)).willReturn(walletEntity);
		
		Optional<BigDecimal> remainingBalance = walletService.discountAmount(VALID_ID, VALID_AMOUNT);

		assertTrue(remainingBalance.isPresent());
		assertEquals(VALID_BALANCE.subtract(VALID_AMOUNT), remainingBalance.get());
	}

	private void assertExpectedWallet(Optional<Wallet> returnedWallet) {
		assertEquals(VALID_ID, returnedWallet.get().getWalletId());
		assertEquals(VALID_BALANCE, returnedWallet.get().getBalance());
	}
}
