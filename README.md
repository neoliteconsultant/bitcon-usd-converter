# About
A Spring Boot application for converting from Bitcoin to USD.



### Prerequisites

Before running this application, ensure you have installed and configured
the following tools

- JDK 1.8 or later
- Maven 3.2+



### Running the project     

1. To run the project, navigate to the project root (bitcoin-usd-converer).

   
2. Build an executable jar at the command line :
   ```
   $ mvn package
   ```


3. Run the java archive located in the target folder:
   ```
   $ java -jar target/<jar-name>.jar
   ```


### Calling API Endpoints

Now that the service is up, make a HTTP POST to reverse a string.

``` 
$ curl -X POST http://localhost:8097/api/v1/ExchangeRate/reversetext -H "Content-Type: application/json"  -d '{"content":"a,b#@c"}'
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
  


