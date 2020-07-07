package com.playtomic.tests.wallet.domain;

import java.math.BigDecimal;

public final class Wallet {

	private final int walletId;
	
	private final BigDecimal balance;

	public Wallet(int walletId, BigDecimal balance) {
		this.walletId = walletId;
		this.balance = balance;
	}

	public int getWalletId() {
		return walletId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + walletId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wallet other = (Wallet) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (walletId != other.walletId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Wallet [walletId=" + walletId + ", balance=" + balance + "]";
	}
}
