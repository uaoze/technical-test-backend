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

import org.hamcrest.Matchers;
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
	private static final BigDecimal VALID_BALANCE = new BigDecimal(10.0);
	
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
}
