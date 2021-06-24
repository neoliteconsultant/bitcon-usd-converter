# About
A Spring Boot application for converting from Bitcoin to USD.



### Prerequisites

Before running this application, ensure you have installed and configured
the following tools

- JDK 1.8 or later
- Maven 3.2+



### Running the project     

1. Navigate to the project root (bitcoin-usd-converer).

   
2. Run the application:
   ```
   $ ./mvnw spring-boot:run
   ```


### Calling API Endpoints

Get the latest exchange rates.

``` 
$ curl http://localhost:8097/v1/rates/latest
```


Get historical exchange rates.

``` 
$ "curl http://localhost:8097/v1/rates/historical?startDate={startDate}&endDate={endDate}"
```

Where {startDate} and {endDate} are the start and end dates in YYYY-MM-DD format
respectively, e.g

``` 
$ "curl http://localhost:8097/v1/rates/historical?startDate=2021-01-25&endDate=2021-01-27"
```
### Configuring exchange rate period

To configure the exchange period to check exchange rates, modify the **rate.period** key
in src/main/resources/application.properties to a value in seconds

```
rate.period = 30 
```



### Testing
To run unit tests, run mvn test in a shell/command line from the project root.
  ```
  $ mvn test
  ```
 
 

### Javadoc 
To generate javadocs, run mvn javadoc:javadoc goal in a shell from the project root.
  ```
  $ mvn javadoc:javadoc
  ```

The generated javadoc is located in target/site/apidocs
  


