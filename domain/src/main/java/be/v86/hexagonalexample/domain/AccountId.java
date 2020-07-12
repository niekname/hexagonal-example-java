package be.v86.hexagonalexample.domain;

import java.util.Random;

public class AccountId {

    private final String IBAN;

    private AccountId(final String IBAN) {
        this.IBAN = IBAN;
    }

    public static AccountId BE() {
        return new AccountId(generateBE_IBAN());
    }

    public static AccountId fromString(final String iban) {
        return new AccountId(iban);
    }

    private static String generateBE_IBAN() {
        Random random = new Random();
        return "BE" +
                String.format("%02d", random.nextInt(100)) +
                String.format("%04d", random.nextInt(10000)) +
                String.format("%04d", random.nextInt(10000)) +
                String.format("%04d", random.nextInt(10000));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AccountId accountId = (AccountId) o;

        return IBAN.equals(accountId.IBAN);
    }

    @Override
    public int hashCode() {
        return IBAN.hashCode();
    }

    @Override
    public String toString() {
        return IBAN;
    }
}
