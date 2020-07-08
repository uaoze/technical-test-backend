package com.playtomic.tests.wallet.api;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playtomic.tests.wallet.api.dto.WalletDto;
import com.playtomic.tests.wallet.api.dto.WalletToDtoMapper;
import com.playtomic.tests.wallet.service.BalanceBelowZeroException;
import com.playtomic.tests.wallet.service.PaymentServiceException;
import com.playtomic.tests.wallet.service.WalletService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api
@Validated
public class WalletController {

	private Logger log = LoggerFactory.getLogger(WalletController.class);

	private WalletService walletService;

	private WalletToDtoMapper walletToDtoMapper;

	public WalletController(WalletService walletService, WalletToDtoMapper walletToDtoMapper) {
		this.walletService = walletService;
		this.walletToDtoMapper = walletToDtoMapper;
	}

	@RequestMapping("/")
	void log() {
		log.info("Logging from /");
	}

	@GetMapping(EndPoints.ENDPOINT_GET_WALLET_BY_ID)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "wallet not found"),
			@ApiResponse(code = 500, message = "server error") })
	@ApiOperation(value = "Get Wallet By Id", response = WalletDto.class)
	public ResponseEntity<WalletDto> getWallet(
			@ApiParam(value = "The wallet identifier", name = "walletId", required = true) @PathVariable int walletId) {

		return (walletService.getWallet(walletId)).map(walletToDtoMapper).map(ResponseEntity::ok)
				.orElseGet(this::notFound);
	}

	@GetMapping(EndPoints.ENDPOINT_DISCOUNT_AMOUNT)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "balance below zero after discount amount"),
			@ApiResponse(code = 404, message = "wallet not found"),
			@ApiResponse(code = 500, message = "server error") })
	@ApiOperation(value = "Discount the amount to the wallet", response = BigDecimal.class)
	public ResponseEntity<Float> discountAmount(
			@ApiParam(value = "The wallet identifier", name = "walletId", required = true) @PathVariable int walletId,
			@ApiParam(value = "The amount to discount", name = "amount", required = true) @PathVariable BigDecimal amount) {

		Optional<BigDecimal> remainingBalance = Optional.empty();
		try {
			remainingBalance = walletService.discountAmount(walletId, amount);
		} catch (BalanceBelowZeroException e) {
			return ResponseEntity.status(400).build();
		}
		
		return (remainingBalance.isPresent()) ? ResponseEntity.ok().body(remainingBalance.get().floatValue()) : ResponseEntity.notFound().build();
	}

	@GetMapping(EndPoints.ENDPOINT_TOPUP_AMOUNT)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "amount below the threshold for a topup"),
			@ApiResponse(code = 404, message = "wallet not found"),
			@ApiResponse(code = 500, message = "server error") })
	@ApiOperation(value = "Discount the amount to the wallet", response = BigDecimal.class)
	public ResponseEntity<Float> topupAmount(
			@ApiParam(value = "The wallet identifier", name = "walletId", required = true) @PathVariable int walletId,
			@ApiParam(value = "The amount to topup", name = "amount", required = true) @PathVariable BigDecimal amount) {

		Optional<BigDecimal> remainingBalance;
		try {
			remainingBalance = walletService.topupAmount(walletId, amount);
		} catch (PaymentServiceException e) {
			return ResponseEntity.status(400).build();
		}
		
		return (remainingBalance.isPresent()) ? ResponseEntity.ok().body(remainingBalance.get().floatValue()) : ResponseEntity.notFound().build();
	}

	private ResponseEntity<WalletDto> notFound() {
		return ResponseEntity.notFound().build();
	}
}
