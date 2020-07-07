package com.playtomic.tests.wallet.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletEntityToWalletMapper;
import com.playtomic.tests.wallet.h2.DBRepository;
import com.playtomic.tests.wallet.service.WalletService;

@Service
public class WalletServiceH2DB implements WalletService {
	
	private final DBRepository repository;
	
	private final WalletEntityToWalletMapper walletEntityToWalletMapper;
	
    public WalletServiceH2DB(DBRepository repository, WalletEntityToWalletMapper walletEntityToWalletMapper) {
		this.repository = repository;
		this.walletEntityToWalletMapper = walletEntityToWalletMapper;
	}

	@Override
	public Optional<Wallet> getWallet(int walletId) {    	
    	Wallet wallet = walletEntityToWalletMapper.apply(repository.findByWalletId(walletId));
    	
    	return Optional.ofNullable(wallet);
	}
}
