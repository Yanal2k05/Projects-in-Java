/**
 * The stock investment class.
 * It connects to the invesment class.
 */
public class Stock extends Investment {
    private static final double COMM = 9.99; // Commission fee

    /**
     * Constructor for creating a Stock object.
     *
     * @param symbol   The symbol.
     * @param name     The name.
     * @param quantity The number.
     * @param price    The price.
     */
    public Stock(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price);
        updateBookValue(quantity * price + COMM); // Initial book value
    }

    /**
     * Calculates the gain for the stock.
     * 
     *
     * @return The gain rounded to two decimal places.
     */
    @Override
    public double calculateGain() {
        double currentPrice = getPrice();
        double bookValue = getBookValue();
        return Math.round(((currentPrice * getQuantity()) - bookValue - COMM) * 100.0) / 100.0;
    }

    /**
     * Calculates the proceeds from selling stocks.
     * 
     *
     * @param quantity The number of units to sell.
     * @param price    The price per unit during the sale.
     * @return The proceeds from the sale.
     */
    @Override
    public double calculateSellProceeds(int quantity, double price) {
        return Math.round((quantity * price - COMM) * 100.0) / 100.0;
    }

    /**
     * Updates the book value when buying more stocks.
     * 
     *
     * @param additionalQuantity The number of extra units purchased.
     * @param newPrice           The price per extra unit purchased.
     */
    public void updateBookValue(int additionalQuantity, double newPrice) {
        double newBookValue = getBookValue() + additionalQuantity * newPrice + COMM;
        updateBookValue(Math.round(newBookValue * 100.0) / 100.0);
    }
}
