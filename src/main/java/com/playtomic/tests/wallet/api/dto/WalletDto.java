package com.playtomic.tests.wallet.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public final class WalletDto implements Serializable {

	private static final long serialVersionUID = 1339526496377070808L;

	@ApiModelProperty
	private final int walletId;
	
	@ApiModelProperty
	private final BigDecimal balance;

	public WalletDto(int walletId, BigDecimal balance) {
		this.walletId = walletId;
		this.balance = balance;
	}

	public int getWalletId() {
		return walletId;
	}

	public BigDecimal getBalance() {
		return balance;
	}
}
