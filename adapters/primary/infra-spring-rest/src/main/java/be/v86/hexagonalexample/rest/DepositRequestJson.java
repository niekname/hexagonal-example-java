package be.v86.hexagonalexample.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepositRequestJson {

    double amount;

    public DepositRequestJson(@JsonProperty(value = "amount", required = true) final double amount) {
        this.amount = amount;
    }
}
