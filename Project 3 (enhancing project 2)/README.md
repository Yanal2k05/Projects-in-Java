##1.
The ePortfolio application allows investors to manage their stock and mutual fund investments. Investors can track actions such as buying, selling, updating prices, calculating total gains, and searching for investments. The portfolio consists of two types of investments: Stocks and Mutual Funds. The system allows users to perform common operations on these investments and manage them efficiently.

##2. 
Assumptions:
Commission for buying and selling stocks is fixed at $9.99.
Redemption fee for mutual funds is fixed at $45 for each sale.
The buy command can either add a new investment or update the quantity of an existing investment.
Price updates are manual and entered by the user.
Investments have unique symbols across both stocks and mutual funds.
Limitations:
The system does not support fractional shares or units.
No database support; investments are stored only in memory.

##3. 
User Guide
Compile the program 
javac ePortfolio/*.java
Run the program using:
java ePortfolio.PortfolioGUI

Once the program starts, you will interact with the portfolio through the GUI menu using the following commands:

Buy: Enter buy and provide details for a stock or mutual fund investment.
If buying a new investment, provide symbol, name, quantity, and price.
If increasing quantity, provide symbol, new quantity, and price.

Sell: Enter sell and provide the symbol, quantity, and price.
The program will process the sale and update the portfolio accordingly.

Update: Enter update to manually refresh the prices of investments.

Get Gain: Enter getGain to calculate the total gain based on current prices.

Search: Enter search and provide optional criteria: symbol, name keywords, and price range.

Quit: Enter quit to exit the program.

Example:
Buy 500 shares of AAPL stock:
buy
stock
AAPL
Apple
500
110.08

Sell 200 units of mutual fund:
sell
SSETX
50
42.26
Update stock price:

##4. 
Buy a New Stock:

what to do: Buy 500 shares of AAPL at $110.08.
result: Adds a new entry for AAPL with correct quantity and price.
Update Stock Price:

what to do: Update the price of AAPL to $142.23.
result: Reflects updated price in the portfolio.
Decrease Stock Quantity:

what to do: Sell 200 shares of AAPL at $142.23.
result: Reduces quantity for AAPL; updates portfolio accordingly.
Sell Portion of Stock:

what to do: Sell 50 shares of AAPL at $142.23.
result: Quantity decreases by 50, and portfolio reflects the change.
Buy a Mutual Fund:

what to do: Buy 450 units of SSTEX at $56.26.
result: Adds a new entry for SSTEX with correct quantity and price.
Update Mutual Fund Price:

what to do: Update SSTEX to $42.21.
result: Reflects updated price for mutual fund in the portfolio.
Calculate Total Gain:

what to do: Use getGain to check total gain.
result: Program calculates and displays gain based on current prices.
Sell Portion of Mutual Fund:

what to do: Sell 150 units of SSTEX at $42.21.
result: Reduces SSTEX quantity; updates portfolio accordingly.
Search Book Value and Stock Details:

what to do: Perform search for current book value, quantity, and price of all stocks.
result: Displays book value and stock details accurately.
Buy with Existing Symbol:

what to do: Buy 300 shares of AAPL at $120.00.
result: Increases quantity for AAPL without adding a new entry.
Buy with Existing Name but Different Symbol:

what to do: Buy 200 shares of APLE with name "Apple Inc." at $105.00.
result: Prompts for confirmation due to name similarity.
Partial Name Keyword Search:

what to do: Search with keyword "App."
result: Lists AAPL or investments containing "App."
Invalid Sell with Excess Quantity:

what to do: Sell 700 shares of AAPL at $140.00 with only 500 owned.
result: Displays error for insufficient quantity.
Sell Entire Stock Quantity:

what to do: Sell all remaining shares of AAPL.
result: Removes AAPL from the portfolio.
Edge Case for Invalid Price Update:

what to do: Update with a negative price or zero.
result: Displays error for invalid price.
Multiple Keyword Search for Mutual Fund:

what to do: Search with keywords "growth" and "international."
result: Lists all matching mutual funds.
Check Book Value After Multiple Transactions:

what to do: After buys and sells, verify book value.
result: Reflects accurate calculations.
Quit Command:

what to do: Enter "quit" to exit.
result: Saves changes and exits program.
Duplicate Symbol and Name Match in Buy:

what to do: Buy 200 shares of AAPL at $130.00 (matching symbol and name).
result: Adds to existing investment’s quantity.
Invalid Command Entry:

what to do: Enter unrecognized command like "invest."
result: Displays error for invalid command.
Search with Price Range Only:

what to do: Search with price range (100.00 to 150.00).
result: Lists investments within the range.
Update Prices for Multiple Investments:

what to do: Use "update" to refresh all investment prices.
result: All investments reflect updated prices.
Get Gain on Empty Portfolio:

what to do: Use "getGain" when portfolio is empty.
result: Returns zero gain or message indicating no investments.
Sell Nonexistent Investment:

what to do: Sell a symbol not in the portfolio.
result: Displays error for nonexistent investment.
Invalid Price or Quantity in Buy:

what to do: Enter negative price or quantity during buy.
result: Displays error for invalid input.
Verify Book Value After Partial Sell:

what to do: Check book value after partial stock sale.
result: Reflects correct book value decrease.
Update Prices After Sell:

what to do: Update prices after selling shares.
result: Only updates remaining investments’ prices.
Exact Price Boundary in Search:

what to do: Search with exact price as min and max (e.g., 110.08).
result: Lists investments exactly at specified price.
Concurrent Buy and Update:

what to do: Buy a new investment, then immediately update price.
result: Reflects added and updated values correctly.
Large Quantity and Price Values in Buy:

what to do: Buy 1,000,000 shares at $1,000.00.
result: Handles large values without errors.
Exit and Reload for File Persistence:

what to do: Perform transactions, quit, restart, and check portfolio.
result: Reloads portfolio with latest saved state.


##5. 
Possible Improvements
Persistent Storage: Add a database to store investment data between sessions.
Enhanced Search: Include more search filters and optimize search queries.