/**
 * The Investment class represents a general financial investment in a portfolio.
 * It serves as a base class for specific types of investments like stocks and mutual funds.
 */
public abstract class Investment {
    private String symbol;
    private String name;
    private int quantity;
    private double price;
    private double bookValue;

    /**
     * Initializes the investment with the provided details.
     *
     * @param symbol   The investment's symbol.
     * @param name     The investment's name.
     * @param quantity The number of units.
     * @param price    The price per unit.
     */
    public Investment(String symbol, String name, int quantity, double price) {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.bookValue = Math.round(quantity * price * 100.0) / 100.0; // Initial book value calculation rounded to 2 decimal places
    }

    /**
     * Returns the symbol of the investment.
     *
     * @return The investment symbol.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the name of the investment.
     *
     * @return The investment name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the quantity of units in the investment.
     *
     * @return The number of units.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the current price per unit of the investment.
     *
     * @return The price per unit.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the current book value of the investment.
     *
     * @return The book value.
     */
    public double getBookValue() {
        return bookValue;
    }

    /**
     * Sets a new price per unit for the investment.
     *
     * @param price The new unit price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets a new quantity of units for the investment.
     *
     * @param quantity The new quantity of units.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Updates the book value of the investment.
     *
     * @param bookValue The new book value.
     */
    public void updateBookValue(double bookValue) {
        this.bookValue = Math.round(bookValue * 100.0) / 100.0;
    }

    // Abstract methods for specific investment types to implement

    /**
     * Calculates the gain from this investment.
     * Must be implemented by subclasses.
     *
     * @return The calculated gain.
     */
    public abstract double calculateGain();

    /**
     * Calculates the proceeds from selling a portion of the investment.
     * Must be implemented by subclasses.
     *
     * @param quantity The number of units being sold.
     * @param price    The sale price per unit.
     * @return The total proceeds from the sale.
     */
    public abstract double calculateSellProceeds(int quantity, double price);

    /**
     * Returns a string representation of the investment.
     *
     * @return A string containing the investment's symbol, name, quantity, price, and book value.
     */
    @Override
    public String toString() {
        return "Symbol: " + symbol + ", Name: " + name + ", Quantity: " + quantity + 
               ", Price: " + String.format("%.2f", price) + 
               ", BookValue: " + String.format("%.2f", bookValue);
    }
}
