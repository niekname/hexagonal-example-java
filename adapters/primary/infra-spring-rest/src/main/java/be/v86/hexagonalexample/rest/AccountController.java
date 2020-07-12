package be.v86.hexagonalexample.rest;

import be.v86.hexagonalexample.application.CreateAccount;
import be.v86.hexagonalexample.domain.AccountId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private CreateAccount createAccount;

    public AccountController(final CreateAccount createAccount) {
        this.createAccount = createAccount;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createAccount(final UriComponentsBuilder uriBuilder) {
        AccountId accountId = createAccount.execute();
        return ResponseEntity
                .created(uriBuilder
                        .pathSegment("accounts", "{id}")
                        .buildAndExpand(accountId.toString())
                        .toUri())
                .build();
    }
}
