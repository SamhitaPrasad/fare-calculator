# Fare Calculator

## Maven Commands

Run install
```
mvn clean install
```

Run tests
```
mvn clean test
```

Run as spring-boot application
```
mvn clean spring-boot:run
```

Create a fatjar
```
mvn clean package
```

Run using fatjar bash
```
$ java -jar /f/development/poc/fare-calculator-poc/fare-calculator/target/fare-calculator-0.0.1-SNAPSHOT.jar
```

Run using fatjar cmd
```
C:\Users\samhita>java -jar F:\development\poc\fare-calculator-poc\fare-calculator\target\fare-calculator-0.0.1-SNAPSHOT.jar
```

## Curl Commands
Method to upload csv
````
curl -v -F file=@taps.csv http://localhost:8080/api/csv/upload
````
Method to upload csv using bash path
````
curl -v -F file=@/f/development/poc/fare-calculator-poc/fare-calculator/src/main/resources/taps.csv http://localhost:8080/api/csv/upload
````

Test get method
````
curl http://localhost:8080/api/csv/test
````
