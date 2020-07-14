package be.v86.hexagonalexample.rest;

import be.v86.hexagonalexample.application.CreateAccount;
import be.v86.hexagonalexample.application.Deposit;
import be.v86.hexagonalexample.application.DepositRequest;
import be.v86.hexagonalexample.application.InvalidAccountIdException;
import be.v86.hexagonalexample.application.InvalidAmountException;
import be.v86.hexagonalexample.domain.AccountId;
import be.v86.hexagonalexample.domain.AccountNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AccountController.class)
@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateAccount createAccount;

    @MockBean
    private Deposit deposit;

    @Test
    void create_account() throws Exception {
        when(createAccount.execute()).thenReturn(AccountId.BE());

        mockMvc.perform(
                post("/accounts"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", startsWith("http://localhost/accounts/BE")));
    }

    @Test
    void deposit_invalid_request() throws Exception {
        mockMvc.perform(
                post("/accounts/BE12123412341234/deposit")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deposit_non_existing_account() throws Exception {
        doThrow(AccountNotFoundException.class).when(deposit).execute(any(DepositRequest.class));

        mockMvc.perform(
                post("/accounts/BE12123412341234/deposit")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"amount\":5.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deposit_invalid_account_id() throws Exception {
        doThrow(InvalidAccountIdException.class).when(deposit).execute(any(DepositRequest.class));

        mockMvc.perform(
                post("/accounts/invalid/deposit")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"amount\":5.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deposit_invalid_amount() throws Exception {
        doThrow(InvalidAmountException.class).when(deposit).execute(any(DepositRequest.class));

        mockMvc.perform(
                post("/accounts/BE12123412341234/deposit")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"amount\":-5}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deposit_success() throws Exception {
        mockMvc.perform(
                post("/accounts/BE12123412341234/deposit")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"amount\":5.0}"))
                .andExpect(status().isOk());
    }
}
