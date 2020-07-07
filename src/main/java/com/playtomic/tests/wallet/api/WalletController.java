package com.playtomic.tests.wallet.api;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playtomic.tests.wallet.api.dto.WalletDto;
import com.playtomic.tests.wallet.api.dto.WalletToDtoMapper;
import com.playtomic.tests.wallet.domain.Wallet;
import com.playtomic.tests.wallet.service.WalletService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class WalletController {

	private Logger log = LoggerFactory.getLogger(WalletController.class);

	private WalletService walletService;

	private WalletToDtoMapper walletToDtoMapper;

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

		// TODO:
		WalletDto walletDto = new WalletDto(101, new BigDecimal(10));
		ResponseEntity<WalletDto> response = new ResponseEntity<>(walletDto, HttpStatus.OK);
		
		return response;
	}
}
