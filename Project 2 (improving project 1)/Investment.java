/**
 * The {@code Investment} class is an abstract base class that represents a general investment.
 * It provides common properties and methods for all types of investments, including the symbol,
 * name, quantity, price, and book value. This class implements {@code Serializable} to allow
 * instances to be saved and loaded from files.
 * 
 * <p>Subclasses of {@code Investment} must implement the methods for calculating the gain 
 * and sell proceeds.</p>
 * 
 * @since 1.0
 */
package ePortfolio;

import java.io.Serializable;

public abstract class Investment implements Serializable {
    /**
     * The symbol of the investment.
     */
    private String symbol;

    /**
     * The name of the investment.
     */
    private String name;

    /**
     * The quantity of units owned.
     */
    private int quantity;

    /**
     * The current price per unit of the investment.
     */
    private double price;

    /**
     * The book value of the investment, calculated based on quantity and price.
     */
    private double bookValue;

    /**
     * Constructs an {@code Investment} with the specified symbol, name, quantity, and price.
     * Initializes the book value based on the given quantity and price.
     *
     * @param symbol  the symbol representing the investment
     * @param name    the name of the investment
     * @param quantity the quantity of the investment owned
     * @param price   the price per unit of the investment
     */
    public Investment(String symbol, String name, int quantity, double price) {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.bookValue = Math.round(quantity * price * 100.0) / 100.0;
    }

    /**
     * Gets the symbol of the investment.
     *
     * @return the symbol of the investment
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets the name of the investment.
     *
     * @return the name of the investment
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the quantity of the investment owned.
     *
     * @return the quantity of the investment
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the current price per unit of the investment.
     *
     * @return the current price of the investment
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the book value of the investment.
     *
     * @return the book value of the investment
     */
    public double getBookValue() {
        return bookValue;
    }

    /**
     * Sets the price per unit of the investment.
     *
     * @param price the new price per unit of the investment
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the quantity of the investment owned.
     *
     * @param quantity the new quantity of the investment
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Updates the book value of the investment to the specified value.
     *
     * @param bookValue the new book value of the investment
     */
    public void updateBookValue(double bookValue) {
        this.bookValue = Math.round(bookValue * 100.0) / 100.0;
    }

    /**
     * Calculates the gain of the investment. This method must be implemented by subclasses
     * as the gain calculation depends on the specific type of investment.
     *
     * @return the calculated gain
     */
    public abstract double calculateGain();

    /**
     * Calculates the proceeds from selling a specified quantity of the investment at the
     * given price. This method must be implemented by subclasses as the sell proceeds
     * calculation depends on the specific type of investment.
     *
     * @param quantity the quantity of the investment to sell
     * @param price    the price at which the investment is sold
     * @return the calculated sell proceeds
     */
    public abstract double calculateSellProceeds(int quantity, double price);

    /**
     * Returns a string representation of the investment, including symbol, name, quantity,
     * price, and book value.
     *
     * @return a string representation of the investment
     */
    @Override
    public String toString() {
        return "Symbol: " + symbol + ", Name: " + name + ", Quantity: " + quantity + 
               ", Price: " + String.format("%.2f", price) + 
               ", BookValue: " + String.format("%.2f", bookValue);
    }
}
