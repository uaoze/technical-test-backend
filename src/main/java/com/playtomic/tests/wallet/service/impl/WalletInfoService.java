package com.playtomic.tests.wallet.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletEntityToWalletMapper;
import com.playtomic.tests.wallet.h2.DBRepository;

@Service
public class WalletInfoService implements com.playtomic.tests.wallet.service.WalletInfoService {
	
	private final DBRepository repository;
	
	private final WalletEntityToWalletMapper walletEntityToWalletMapper;
	
    public WalletInfoService(DBRepository repository, WalletEntityToWalletMapper walletEntityToWalletMapper) {
		this.repository = repository;
		this.walletEntityToWalletMapper = walletEntityToWalletMapper;
	}

	@Override
	public Optional<BigDecimal> checkWallet(int walletId) {    	
    	Wallet wallet = walletEntityToWalletMapper.apply(repository.findByWalletId(walletId));
    	
    	return (wallet != null) ? Optional.ofNullable(wallet.getBalance()) : Optional.empty();
	}
}
