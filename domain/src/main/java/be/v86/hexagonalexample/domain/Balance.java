package be.v86.hexagonalexample.domain;

public class Balance {

    private final double amount;

    public Balance(final double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Balance balance = (Balance) o;

        return Double.compare(balance.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        final long temp = Double.doubleToLongBits(amount);
        return (int) (temp ^ (temp >>> 32));
    }
}
