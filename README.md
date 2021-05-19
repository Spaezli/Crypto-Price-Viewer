# Crypto-Price-Viewer

On the website https://www.coindesk.com/coindesk-api several APIs are described to get information about Bitcoin.  The API endpoint https://api.coindesk.com/v1/bpi/currentprice.json returns the current Bitcoin Price in 3 different currencies as JSON string. The prices are updated every minute.

+	Implement a Console program that retrieves the Bitcoin prices online in an asynchronous way using Kotlin coroutines. The program should output the prices formatted in the console.
+	Write a TornadoFX App, that displays the Bitcoin prices retrieved asynchronously from the API.
+	Implement an "Update" Button to get the newest values from the Bitcoin API. Hint: Use the construct runAsync { /* call the API  */ } ui { /* Update the ui */}. 
+	Implement an automatic update  of the UI, when the bitcoin value change. Hint: Use the observer pattern.
