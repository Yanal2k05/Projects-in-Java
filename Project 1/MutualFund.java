/**
 * The MutualFund investment class.
 * It connects to the invesment class.
 */
public class MutualFund extends Investment {
    private static final double REDEMPTION_FEE = 45.00; // Redemption fee for mutual fund transactions

    /**
     * Constructor for creating a MutualFund object.
     *
     * @param symbol   The symbol.
     * @param name     The name.
     * @param quantity The number.
     * @param price    The price.
     */
    public MutualFund(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price);
        updateBookValue(quantity * price); // Book value without fee
    }

    /**
     * Calculates the gain.
     * 
     *
     * @return The gain rounded to two decimal places.
     */
    @Override
    public double calculateGain() {
        double currentPrice = getPrice();
        double bookValue = getBookValue();
        return Math.round(((currentPrice * getQuantity()) - bookValue - REDEMPTION_FEE) * 100.0) / 100.0;
    }

    /**
     * Calculates the proceeds from selling.
     * 
     *
     * @param quantity The number of units to sell.
     * @param price    The price per unit during the sale.
     * @return The proceeds from the sale.
     */
    @Override
    public double calculateSellProceeds(int quantity, double price) {
        return Math.round((quantity * price - REDEMPTION_FEE) * 100.0) / 100.0;
    }

    /**
     * Updates the book value.
     *
     * @param additionalQuantity The amount of extra units purchased.
     * @param newPrice           The price per unit from extra units purchase.
     */
    public void updateBookValue(int additionalQuantity, double newPrice) {
        double newBookValue = getBookValue() + additionalQuantity * newPrice;
        updateBookValue(Math.round(newBookValue * 100.0) / 100.0);
    }
}
