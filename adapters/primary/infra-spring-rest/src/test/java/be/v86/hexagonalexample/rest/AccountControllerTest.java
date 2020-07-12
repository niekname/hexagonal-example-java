package be.v86.hexagonalexample.rest;

import be.v86.hexagonalexample.application.CreateAccount;
import be.v86.hexagonalexample.domain.AccountId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AccountController.class)
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateAccount createAccount;

    @Test
    void create_account() throws Exception {
        when(createAccount.execute()).thenReturn(AccountId.BE());

        mockMvc.perform(
                get("/accounts"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", startsWith("http://localhost/accounts/BE"))
                );
    }
}
