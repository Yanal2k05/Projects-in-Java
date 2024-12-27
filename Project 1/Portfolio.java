import java.util.ArrayList;
import java.util.Scanner;

/**
 * The protfolio class represents a collection of stocks and mutual funds.
 * It provides methods to buy and sell investments, update prices, calculate gains,
 */
public class Portfolio { 
    private ArrayList<Stock> stocks;
    private ArrayList<MutualFund> mutualFunds;

    /**
     * Initializes an empty portfolio for stocks and mutual funds.
     */
    public Portfolio() {
        stocks = new ArrayList<>();
        mutualFunds = new ArrayList<>();
    }

    /**
     * Buys an investment and adds it to the portfolio. 
     * If the investment already exists, updates its quantity and book value.
     *
     * @param type     The type of investment (stock or mutual fund).
     * @param symbol   The investment symbol.
     * @param name     The investment name.
     * @param quantity The quantity to buy.
     * @param price    The price per unit.
     */
    public void buyInvestment(String type, String symbol, String name, int quantity, double price) {
        if (type.equalsIgnoreCase("stock") || type.equalsIgnoreCase("s")) {
            Stock existingStock = findStock(symbol);
            if (existingStock != null) {
                existingStock.setQuantity(existingStock.getQuantity() + quantity);
                existingStock.updateBookValue(quantity, price);
                System.out.println("Updated book value for stock: " + existingStock.getBookValue());
            } else {
                Stock newStock = new Stock(symbol, name, quantity, price);
                stocks.add(newStock);
                System.out.println("Book value for new stock: " + newStock.getBookValue());
            }
        } else if (type.equalsIgnoreCase("mutualfund") || type.equalsIgnoreCase("m")) {
            MutualFund existingFund = findMutualFund(symbol);
            if (existingFund != null) {
                existingFund.setQuantity(existingFund.getQuantity() + quantity);
                existingFund.updateBookValue(quantity, price);
                System.out.println("Updated book value for mutual fund: " + existingFund.getBookValue());
            } else {
                MutualFund newFund = new MutualFund(symbol, name, quantity, price);
                mutualFunds.add(newFund);
                System.out.println("Book value for new mutual fund: " + newFund.getBookValue());
            }
        }
    }

    /**
     * Sells a specified quantity of an investment and calculates the proceeds.
     * Updates the book value and removes the investment if its quantity reaches zero.
     *
     * @param symbol   The investment symbol.
     * @param quantity The quantity to sell.
     * @param price    The sale price per unit.
     */
    public void sellInvestment(String symbol, int quantity, double price) {
        Investment investment = findInvestment(symbol);
        if (investment != null && investment.getQuantity() >= quantity) {
            double proceeds = investment.calculateSellProceeds(quantity, price);
            int newQuantity = investment.getQuantity() - quantity;

            if (investment instanceof Stock) {
                Stock stock = (Stock) investment;
                double newBookValue = stock.getBookValue() * ((double) newQuantity / stock.getQuantity());
                stock.updateBookValue(newBookValue);
            } else if (investment instanceof MutualFund) {
                MutualFund fund = (MutualFund) investment;
                double newBookValue = fund.getBookValue() * ((double) newQuantity / fund.getQuantity());
                fund.updateBookValue(newBookValue);
            }

            if (newQuantity == 0) {
                removeInvestment(investment);
            } else {
                investment.setQuantity(newQuantity);
            }

            System.out.println("Proceeds from sale: " + proceeds);
        } else {
            System.out.println("Error: Investment not found or insufficient quantity.");
        }
    }

    /**
     * Updates the price of a specific stock or mutual fund based on user input.
     */
    public void updatePrices() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter symbol of Stock or Mutual Fund to update: ");
        String symbol = scanner.nextLine();
        
        boolean found = false;

        // Search and update stock prices
        for (Stock stock : stocks) {
            if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                System.out.print("Enter new price for Stock " + stock.getSymbol() + ": ");
                stock.setPrice(Math.round(scanner.nextDouble() * 100.0) / 100.0);
                found = true;
                break;
            }
        }

        // Search and update mutual fund prices
        if (!found) {
            for (MutualFund fund : mutualFunds) {
                if (fund.getSymbol().equalsIgnoreCase(symbol)) {
                    System.out.print("Enter new price for Mutual Fund " + fund.getSymbol() + ": ");
                    fund.setPrice(Math.round(scanner.nextDouble() * 100.0) / 100.0);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("Symbol not found.");
        }
    }

    /**
     * Calculates the total gain of the portfolio.
     *
     * @return The total portfolio gain.
     */
    public double getGain() {
        double totalGain = 0.0;
        for (Stock stock : stocks) {
            totalGain += stock.calculateGain();
        }
        for (MutualFund fund : mutualFunds) {
            totalGain += fund.calculateGain();
        }
        return Math.round(totalGain * 100.0) / 100.0;
    }

    /**
     * Searches for investments that match the given criteria.
     *
     * @param symbol     The investment symbol.
     * @param nameKeyword A keyword to match in the investment name.
     * @param lowPrice    The minimum price range.
     * @param highPrice   The maximum price range.
     */
    public void search(String symbol, String nameKeyword, double lowPrice, double highPrice) {
        for (Stock stock : stocks) {
            if (matches(stock, symbol, nameKeyword, lowPrice, highPrice)) {
                System.out.println(stock);
            }
        }
        for (MutualFund fund : mutualFunds) {
            if (matches(fund, symbol, nameKeyword, lowPrice, highPrice)) {
                System.out.println(fund);
            }
        }
    }

    /**
     * Finds a stock by its symbol.
     *
     * @param symbol The stock symbol.
     * @return The matching stock, or null if not found.
     */
    private Stock findStock(String symbol) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                return stock;
            }
        }
        return null;
    }

    /**
     * Finds a mutual fund by its symbol.
     *
     * @param symbol The mutual fund symbol.
     * @return The matching mutual fund, or null if not found.
     */
    private MutualFund findMutualFund(String symbol) {
        for (MutualFund fund : mutualFunds) {
            if (fund.getSymbol().equalsIgnoreCase(symbol)) {
                return fund;
            }
        }
        return null;
    }

    /**
     * Finds an investment (either stock or mutual fund) by its symbol.
     *
     * @param symbol The investment symbol.
     * @return The matching investment, or null if not found.
     */
    private Investment findInvestment(String symbol) {
        Investment inv = findStock(symbol);
        if (inv == null) {
            inv = findMutualFund(symbol);
        }
        return inv;
    }

    /**
     * Removes an investment from the portfolio.
     *
     * @param investment The investment to remove.
     */
    private void removeInvestment(Investment investment) {
        if (investment instanceof Stock) {
            stocks.remove(investment);
        } else {
            mutualFunds.remove(investment);
        }
    }

    /**
     * Checks if an investment matches the search criteria.
     *
     * @param investment   The investment to check.
     * @param symbol       The symbol to match.
     * @param nameKeyword  A keyword to match in the investment name.
     * @param lowPrice     The minimum price range.
     * @param highPrice    The maximum price range.
     * @return True if the investment matches, false otherwise.
     */
    private boolean matches(Investment investment, String symbol, String nameKeyword, double lowPrice, double highPrice) {
        boolean symbolMatch = symbol.isEmpty() || investment.getSymbol().equalsIgnoreCase(symbol);
        boolean nameMatch = nameKeyword.isEmpty() || investment.getName().toLowerCase().contains(nameKeyword.toLowerCase());
        boolean priceMatch = (lowPrice <= 0 && highPrice <= 0) || (investment.getPrice() >= lowPrice && investment.getPrice() <= highPrice);
        return symbolMatch && nameMatch && priceMatch;
    }

    /**
     * Runs the main command loop to interact with the portfolio.
     */
    public void commandLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Display menu options
            System.out.println(" Buy");
            System.out.println(" Sell");
            System.out.println(" Update");
            System.out.println(" getGain");
            System.out.println(" Search");
            System.out.println(" Quit");
            System.out.print("Enter command (buy, sell, update, getGain, search, quit): ");
            
            String command = scanner.nextLine().toLowerCase();

            // Exit program if user selects "quit"
            if (command.equals("quit") || command.equals("q")) {
                System.out.println("Ending program, Goodbye!");
                break;
            }

            // Process user command
            if (command.equalsIgnoreCase("buy") || command.equalsIgnoreCase("b")) {
                String type;
                // Buy investment
                do {
                    System.out.print("Enter investment type (stock/mutualfund): ");
                    type = scanner.nextLine();
                } while (!type.equalsIgnoreCase("stock") && !type.equalsIgnoreCase("mutualfund") && !type.equalsIgnoreCase("s")  && !type.equalsIgnoreCase("m"));

                System.out.print("Enter symbol: ");
                String symbol = scanner.nextLine();
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                System.out.print("Enter price: ");
                double price = scanner.nextDouble();
                buyInvestment(type, symbol, name, quantity, price);
                scanner.nextLine(); // Consume newline

            } else if (command.equalsIgnoreCase("sell")) {
                // Sell investment
                System.out.print("Enter symbol: ");
                String symbol = scanner.nextLine();
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                System.out.print("Enter price: ");
                double price = scanner.nextDouble();
                sellInvestment(symbol, quantity, price);
                scanner.nextLine(); // Consume newline

            } else if (command.equalsIgnoreCase("update") || command.equalsIgnoreCase("u")) {
                // Update investment prices
                updatePrices();

            } else if (command.equalsIgnoreCase("getgain") || command.equalsIgnoreCase("g")) {
                // Get total portfolio gain
                System.out.println("Total Gain: " + getGain());

            } else if (command.equalsIgnoreCase("search")) {
             // Search investments
             System.out.print("Enter symbol (or leave blank): ");
             String symbol = scanner.nextLine();
             System.out.print("Enter name keyword (or leave blank): ");
             String keyword = scanner.nextLine();

    double lowPrice = 0;
    double highPrice = 0;
    boolean input = false;

    // Loop until a valid number is entered for low price
            while (!input) {
        System.out.print("Enter low price (or leave blank): ");
        String lowPriceInput = scanner.nextLine();
        if (lowPriceInput.isEmpty()) {
            lowPrice = 0;
            input = true;
        } else {
            try {
                lowPrice = Double.parseDouble(lowPriceInput);
                input = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    input = false;
    // Loop until a valid number is entered for high price
    while (!input) {
        System.out.print("Enter high price (or leave blank): ");
        String highPriceInput = scanner.nextLine();
        if (highPriceInput.isEmpty()) {
            highPrice = 0;
            input = true;
        } else {
            try {
                highPrice = Double.parseDouble(highPriceInput);
                input = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    search(symbol, keyword, lowPrice, highPrice);


            } else {
                // Invalid option
                System.out.println("Invalid Option. Please try again.");
            }
        }
        scanner.close();
    }
}
