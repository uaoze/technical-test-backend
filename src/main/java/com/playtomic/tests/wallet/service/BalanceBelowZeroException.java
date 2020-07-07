package com.playtomic.tests.wallet.service;

import java.math.BigDecimal;

public class BalanceBelowZeroException extends Exception {

	public BalanceBelowZeroException(int walletId, BigDecimal amount) {
		super("Operation for discounting " + amount + " to wallet with id " + walletId
				+ " could not be done as balance becomes below zero");
	}
}
