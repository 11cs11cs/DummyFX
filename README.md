<h1>Dummy FX Platform</h1>

<h2>Run</h2>
<p>This application using Spring Boot. Build the project with Maven, and run the application in IDE, or run with the command:</br>
java -jar DummyFX-0.0.1-SNAPSHOT.jar</p>

<h2>RESTful API call examples</h2>
<b>To view all orders:</b>
<p>Run command: curl -X GET http://localhost:8080/orders</br>
or request from browser: http://localhost:8080/orders</br>
It will return all the orders.</p>

<b>Register an order:</b>
<p>Command: curl -d '{"userId":2, "orderType":"BID","currency":"GBP","amount":4000,"status":"LIVE"}' -H "Content-Type: application/json" -X POST http://localhost:8080/orders/register</br>
It will return the registered order.</p>

<b>Cancel an order:</b>
<p>Run command: curl -X PATCH http://localhost:8080/orders/1</br>
It will return the cancelled order.</p>

<b>To view the summary regarding live not matched orders:</b>
<p>Run command: curl -X GET http://localhost:8080/orders/notmatched</br>
Or request from browser: http://localhost:8080/orders/notmatched</br>
It will return a list of live not matched orders.</p>

<b>To view the summary of matched orders:</b>
<p>Run command: curl -X GET http://localhost:8080/orders/matched</br>
Or request from browser: http://localhost:8080/orders/matched</br>
It will return a list of matched order pairs. Each pair contains 2 live matched orders.</p>

<h2>Matched orders condition</h2>
<p>1. Order A, Order B are both LIVE.</br>
2. User Id not same (not the same user)</br>
3. Order Type not same (if user1 is BIT, then user2 is ASK, and vice versa)</br>
4. Currency is same</br>
5. Amount is same</p>

<h2>Config file</h2>
<p>The static price has been configured in application.properties file</p>

<h2>Test</h2>
<p>Tests using MockMvc to send GET,POST,PATCH request with parameters.</br>
getOrdersAPI()
registerOrderAPI()
cancelOrderAPI()
matchedOrdersAPI()
liveNotMatchedOrdersAPI()</br>
are written in TestOrderController.java in the test package.</p>

