/**
 * The {@code MutualFund} class represents an investment in mutual funds, extending the
 * {@code Investment} class. It includes specific functionality for calculating the gain
 * and sell proceeds of a mutual fund investment, with a fixed redemption fee applied to sales.
 *
 * <p>Mutual fund investments have an additional redemption fee that is subtracted from the
 * calculated gain and sell proceeds.</p>
 *
 * @since 1.0
 */
package ePortfolio;

public class MutualFund extends Investment {
    /**
     * The redemption fee applied to all sales of mutual funds.
     */
    private static final double REDEMPTION_FEE = 45.00;

    /**
     * Constructs a {@code MutualFund} with the specified symbol, name, quantity, and price.
     * The book value is initialized based on the quantity and price.
     *
     * @param symbol   the symbol representing the mutual fund
     * @param name     the name of the mutual fund
     * @param quantity the quantity of units owned
     * @param price    the price per unit of the mutual fund
     */
    public MutualFund(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price);
        updateBookValue(quantity * price);
    }

    /**
     * Calculates the gain of the mutual fund investment. The gain is computed as the difference
     * between the current value of the mutual fund and its book value, minus the redemption fee.
     *
     * @return the calculated gain
     */
    @Override
    public double calculateGain() {
        double currentPrice = getPrice();
        double bookValue = getBookValue();
        return Math.round(((currentPrice * getQuantity()) - bookValue - REDEMPTION_FEE) * 100.0) / 100.0;
    }

    /**
     * Calculates the proceeds from selling a specified quantity of the mutual fund at the
     * given price, accounting for the redemption fee.
     *
     * @param quantity the quantity of the mutual fund to sell
     * @param price    the price at which the mutual fund is sold
     * @return the calculated sell proceeds after the redemption fee
     */
    @Override
    public double calculateSellProceeds(int quantity, double price) {
        return Math.round((quantity * price - REDEMPTION_FEE) * 100.0) / 100.0;
    }

    /**
     * Updates the book value of the mutual fund investment by adding the specified quantity
     * at a new price. This method is used to recalculate the book value when additional units
     * are purchased.
     *
     * @param additionalQuantity the quantity of new units added to the investment
     * @param newPrice           the price of the additional units
     */
    public void updateBookValue(int additionalQuantity, double newPrice) {
        double newBookValue = getBookValue() + additionalQuantity * newPrice;
        updateBookValue(Math.round(newBookValue * 100.0) / 100.0);
    }
}
