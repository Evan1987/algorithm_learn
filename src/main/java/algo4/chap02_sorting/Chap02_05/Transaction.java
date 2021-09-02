package algo4.chap02_sorting.Chap02_05;

import java.util.Comparator;

/**
 * @author Evan
 * @date 2020/3/5 21:44
 */
public class Transaction {
    private final String user;
    private final String date;
    private final double amount;

    public Transaction(String user, String date, double amount) {
        this.user = user;
        this.date = date;
        this.amount = amount;
    }

    public String getUser() {
        return user;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public static class UserOrder implements Comparator<Transaction> {

        @Override
        public int compare(Transaction o1, Transaction o2) {
            return o1.user.compareTo(o2.user);
        }
    }

    public static class DateOrder implements Comparator<Transaction> {

        @Override
        public int compare(Transaction o1, Transaction o2) {
            return o1.date.compareTo(o2.date);
        }
    }

    public static class AmountOrder implements Comparator<Transaction> {
        @Override
        public int compare(Transaction o1, Transaction o2) {
            return Double.compare(o1.amount, o2.amount);
        }
    }
}
