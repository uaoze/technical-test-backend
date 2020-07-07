package com.playtomic.tests.wallet.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;

import com.playtomic.tests.wallet.domain.WalletEntity;
import com.playtomic.tests.wallet.domain.WalletEntityToWalletMapper;
import com.playtomic.tests.wallet.h2.DBRepository;

public class WalletInfoServiceTest {

	private static final int VALID_ID = 101;
	private static final BigDecimal VALID_BALANCE = new BigDecimal(10);
	
	private DBRepository repository = mock(DBRepository.class);
	private WalletEntityToWalletMapper walletEntityToWalletMapper = new WalletEntityToWalletMapper();
	
	private WalletInfoService walletInfoService = new WalletInfoService(repository, walletEntityToWalletMapper);

    @Test
    public void checkWallet_validId_validBalance() {
    	
    	WalletEntity walletEntity = new WalletEntity(VALID_ID, VALID_BALANCE);
    	given(repository.findByWalletId(VALID_ID)).willReturn(walletEntity);
    	
    	Optional<BigDecimal> balance = walletInfoService.checkWallet(VALID_ID);
    	
    	assertTrue(balance.isPresent());
    }
}
