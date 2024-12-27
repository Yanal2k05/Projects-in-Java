package ePortfolio;

/**
 * The {@code Stock} class represents an investment in stocks, extending the
 * {@code Investment} class. It includes specific functionality for calculating the gain
 * and sell proceeds of a stock investment, with a fixed commission applied to purchases and sales.
 *
 * <p>Stocks have an additional commission fee applied to both the book value during buying
 * and proceeds during selling.</p>
 *
 * @since 1.0
 */
public class Stock extends Investment {
    /**
     * The commission fee applied to each stock transaction.
     */
    private static final double COMM = 9.99;

    /**
     * Constructs a {@code Stock} with the specified symbol, name, quantity, and price.
     * Initializes the book value by adding the quantity, price, and commission fee.
     *
     * @param symbol   the symbol representing the stock
     * @param name     the name of the stock
     * @param quantity the quantity of units owned
     * @param price    the price per unit of the stock
     */
    public Stock(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price);
        // Initialize book value with quantity, price, and commission fee
        updateBookValue(quantity * price + COMM);
    }

    /**
     * Calculates the gain of the stock investment. The gain is computed as the difference
     * between the current value of the stock and its book value, minus the commission fee.
     *
     * @return the calculated gain
     */
    @Override
    public double calculateGain() {
        double currentPrice = getPrice();
        double bookValue = getBookValue();
        // Calculate gain by subtracting book value and commission from total current value
        return Math.round(((currentPrice * getQuantity()) - bookValue - COMM) * 100.0) / 100.0;
    }

    /**
     * Calculates the proceeds from selling a specified quantity of the stock at the
     * given price, accounting for the commission fee.
     *
     * @param quantity the quantity of the stock to sell
     * @param price    the price at which the stock is sold
     * @return the calculated sell proceeds after the commission fee
     */
    @Override
    public double calculateSellProceeds(int quantity, double price) {
        // Calculate sell proceeds by subtracting commission fee from total sell price
        return Math.round((quantity * price - COMM) * 100.0) / 100.0;
    }

    /**
     * Updates the book value of the stock investment by adding the specified quantity
     * at a new price, plus the commission fee. This method is used to recalculate the book
     * value when additional units are purchased.
     *
     * @param additionalQuantity the quantity of new units added to the investment
     * @param newPrice           the price of the additional units
     */
    public void updateBookValue(int additionalQuantity, double newPrice) {
        // Calculate new book value with additional quantity, new price, and commission fee
        double newBookValue = getBookValue() + additionalQuantity * newPrice + COMM;
        updateBookValue(Math.round(newBookValue * 100.0) / 100.0);
    }
}
