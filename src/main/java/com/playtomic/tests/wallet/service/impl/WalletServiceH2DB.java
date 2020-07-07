package com.playtomic.tests.wallet.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.domain.WalletEntity;
import com.playtomic.tests.wallet.domain.WalletEntityToWalletMapper;
import com.playtomic.tests.wallet.h2.DBRepository;
import com.playtomic.tests.wallet.service.BalanceBelowZeroException;
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

	@Override
	public Optional<BigDecimal> discountAmount(int walletId, BigDecimal amount) throws BalanceBelowZeroException {

		Wallet wallet = walletEntityToWalletMapper.apply(repository.findByWalletId(walletId));

		BigDecimal remainingBalance = null;
		if (wallet != null) {
			if (wallet.getBalance().compareTo(amount) > 0) {
				remainingBalance = wallet.getBalance().subtract(amount);
				repository.saveAndFlush(new WalletEntity(walletId, amount));
			} else {
				throw new BalanceBelowZeroException(walletId, amount);
			}
		} else {
			return Optional.empty();
		}

		return Optional.ofNullable(remainingBalance);
	}

	@Override
	public Optional<BigDecimal> topupAmount(int walletId, BigDecimal amount) {

		Wallet wallet = walletEntityToWalletMapper.apply(repository.findByWalletId(walletId));

		BigDecimal remainingBalance = null;
		if (wallet != null) {
			remainingBalance = wallet.getBalance().add(amount);
			repository.saveAndFlush(new WalletEntity(walletId, amount));
		} 

		return Optional.ofNullable(remainingBalance);
	}
}
