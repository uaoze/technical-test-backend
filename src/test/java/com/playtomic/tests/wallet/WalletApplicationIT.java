package com.playtomic.tests.wallet;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.playtomic.tests.wallet.api.EndPoints;
import com.playtomic.tests.wallet.domain.WalletEntity;
import com.playtomic.tests.wallet.h2.DBRepository;
import com.playtomic.tests.wallet.service.WalletService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class WalletApplicationIT {

	private static final int VALID_ID = 101;
	private static final int INVALID_ID = 999;
	private static final BigDecimal VALID_BALANCE = new BigDecimal(10.0);
	private static final BigDecimal SMALL_AMOUNT = new BigDecimal(2);
	private static final BigDecimal HUGE_AMOUNT = new BigDecimal(200);
	
	@Autowired
	private DBRepository repository;

	@SpyBean
	WalletService walletService;

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		WalletEntity walletEntity = new WalletEntity(VALID_ID, VALID_BALANCE);
		repository.save(walletEntity);
	}

	@After
	public void cleanUp() {
		repository.delete(VALID_ID);
	}

	@Test
	public void shouldGetWalletForTheSpecifiedId() throws Exception {

	    final ResultActions response =
	            mockMvc.perform(get(EndPoints.ENDPOINT_GET_WALLET_BY_ID, VALID_ID).contentType(MediaType.APPLICATION_JSON));

	    response.andExpect(status().isOk());
	    verify(walletService, times(1)).getWallet(eq(VALID_ID));
	    response.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)); 
	    response.andExpect(jsonPath("$.walletId", is(VALID_ID)));
	    response.andExpect(jsonPath("$.balance", is(10.0)));
	}

	@Test
	public void shouldNotGetAnyWalletAsTheSpecifiedIdDoesntExist() throws Exception {

	    final ResultActions response =
	            mockMvc.perform(get(EndPoints.ENDPOINT_GET_WALLET_BY_ID, INVALID_ID).contentType(MediaType.APPLICATION_JSON));

	    response.andExpect(status().isNotFound());
	    verify(walletService, times(1)).getWallet(eq(INVALID_ID));
	}
	
	@Test
	public void shouldDiscountTheAmountForTheSpecifiedId() throws Exception {

	    final ResultActions response =
	            mockMvc.perform(get(EndPoints.ENDPOINT_DISCOUNT_AMOUNT, VALID_ID, SMALL_AMOUNT));

	    response.andExpect(status().isOk());
	    verify(walletService, times(1)).discountAmount(eq(VALID_ID), eq(SMALL_AMOUNT));
	    response.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)); 
	    response.andExpect(jsonPath("$", is(8.0)));
	}

	@Test
	public void shouldNotDiscountTheAmountAsTheSpecifiedIdDoesntExist() throws Exception {

	    final ResultActions response =
	            mockMvc.perform(get(EndPoints.ENDPOINT_DISCOUNT_AMOUNT, INVALID_ID, SMALL_AMOUNT));

	    response.andExpect(status().isNotFound());
	    verify(walletService, times(1)).discountAmount(eq(INVALID_ID), eq(SMALL_AMOUNT));
	}

	@Test
	public void shouldNotDiscountTheAmountAsItLetBalanceBelowZero() throws Exception {

	    final ResultActions response =
	            mockMvc.perform(get(EndPoints.ENDPOINT_DISCOUNT_AMOUNT, VALID_ID, HUGE_AMOUNT));

	    response.andExpect(status().is(400));
	    verify(walletService, times(1)).discountAmount(eq(VALID_ID), eq(HUGE_AMOUNT));
	}
	
	@Test
	public void shouldTopupTheAmountForTheSpecifiedId() throws Exception {

	    final ResultActions response =
	            mockMvc.perform(get(EndPoints.ENDPOINT_TOPUP_AMOUNT, VALID_ID, HUGE_AMOUNT));

	    response.andExpect(status().isOk());
	    verify(walletService, times(1)).topupAmount(eq(VALID_ID), eq(HUGE_AMOUNT));
	    response.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)); 
	    response.andExpect(jsonPath("$", is(210.0)));
	}

	@Test
	public void shouldNotTopupTheAmountAsTheSpecifiedIdDoesntExist() throws Exception {

	    final ResultActions response =
	            mockMvc.perform(get(EndPoints.ENDPOINT_TOPUP_AMOUNT, INVALID_ID, HUGE_AMOUNT));

	    response.andExpect(status().isNotFound());
	    verify(walletService, times(1)).topupAmount(eq(INVALID_ID), eq(HUGE_AMOUNT));
	}

	@Test
	public void shouldNotTopupTheAmountAsAmountIsBelowThreshold() throws Exception {

	    final ResultActions response =
	            mockMvc.perform(get(EndPoints.ENDPOINT_TOPUP_AMOUNT, VALID_ID, SMALL_AMOUNT));

	    response.andExpect(status().is(400));
	    verify(walletService, times(1)).topupAmount(eq(VALID_ID), eq(SMALL_AMOUNT));
	}
}
