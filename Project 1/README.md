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
javac *.java
Run the program using:
java Main

Once the program starts, you will interact with the portfolio through the menu usinf the following commands:

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
Test Plan
The following scenarios can be tested to ensure correctness:

Buy a new stock: Buy 500 shares of AAPL at $110.08.
Update prices: Update the price of a stock to 142.23.
decrease the quantity of stock: sell 200 shares of AAPL at $142.23.
Sell a portion of stock: Sell 50 shares of AAPL at $142.23.
The search to see what is your bookvalue, amount of stocks and the price

Buy a mutual sotck: Buy 450 units of SSTEX at 56.26
Update prices: Update the price of the mutal fund to 42.21.
Calculate total gain: Use getGain to check total gain based on the current portfolio.
Sell a portion of mutual fund: Sell 150 units of SSTEX at $42.21.
The search to see what is your bookvalue, amount of stocks and the price


##5. 
Possible Improvements
Persistent Storage: Add a database to store investment data between sessions.
UI Improvements: Develop a graphical user interface (GUI) for easier interaction.
Enhanced Search: Include more search filters and optimize search queries.