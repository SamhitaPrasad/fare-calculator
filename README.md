# Fare Calculator

## Prerequisites
1. Java 8
2. Maven 3.8.4
3. Git Bash
4. Require access to C Drive to create C:\tmp to create and view generated file.

## Maven Commands

### How to setup this project.

1. Run install
```
mvn clean install
```
2. Tets run as part of install, to explicitly Run tests
```
mvn clean test
```
### You can start-up the project in different ways. 
1. Run as spring-boot application
```
mvn clean spring-boot:run
```
2. To compile and run the project as a jar file, you will need to 

- Create a fatjar
```
mvn clean package
```
3. Run the fatjar in cmd or bash

Run using fatjar bash
```
$ java -jar /f/development/poc/fare-calculator-poc/fare-calculator/target/fare-calculator-0.0.1-SNAPSHOT.jar
```

Run using fatjar cmd
```
C:\Users\samhita>java -jar F:\development\poc\fare-calculator-poc\fare-calculator\target\fare-calculator-0.0.1-SNAPSHOT.jar
```

## Curl Commands

### How to process a csv file and view the output.
The input csv is taps.csv found inside the resources folder. This file can be sent through either of the following curl commands:

1. Method to upload csv (assumes that you are in the resources folder and taps.csv is available)
````
curl -v -F file=@taps.csv http://localhost:8080/api/csv/upload
````
2. Method to upload csv using bash path
````
curl -v -F file=@/f/development/poc/fare-calculator-poc/fare-calculator/src/main/resources/taps.csv http://localhost:8080/api/csv/upload
````
Test get method
````
curl http://localhost:8080/api/csv/test
````

### Output file
1. You will find the processed csv output under C:\tmp
2. You can also see the output printed as a json on the console.

###Sample output
1. Happy Path
```
samhita@ /f/development/poc/fare-calculator-poc/fare-calculator (main)
$ curl -v -F file=@/f/development/poc/fare-calculator-poc/fare-calculator/src/main/resources/taps.csv http://localhost:8081/api/csv/upload
* timeout on name lookup is not supported
*   Trying ::1...
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Connected to localhost (::1) port 8081 (#0)
> POST /api/csv/upload HTTP/1.1
> Host: localhost:8081
> User-Agent: curl/7.49.1
> Accept: */*
> Content-Length: 603
> Expect: 100-continue
> Content-Type: multipart/form-data; boundary=------------------------9ca2695c90f776ee
>
< HTTP/1.1 100
} [152 bytes data]
< HTTP/1.1 200
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Tue, 30 Nov 2021 23:56:54 GMT
<
{ [680 bytes data]
100  1276    0   673  100   603   1346   1206 --:--:-- --:--:-- --:--:--  1346
[
  {
    "started": "2018-01-22T13:00:00",
    "finished": "2018-01-22T13:05:00",
    "durationSecs": 300000,
    "fromStopId": "Stop2",
    "toStopId": "Stop1",
    "chargeAmount": 3.25,
    "companyId": "Company1",
    "busID": "Bus37",
    "pan": "5500005555555559",
    "status": "COMPLETE"
  },
  {
    "started": "2018-01-24T11:10:00",
    "finished": null,
    "durationSecs": 0,
    "fromStopId": "Stop2",
    "toStopId": null,
    "chargeAmount": 5.5,
    "companyId": "Company1",
    "busID": "Bus37",
    "pan": "34343434343434",
    "status": "INCOMPLETE"
  },
  {
    "started": "2018-01-23T10:05:00",
    "finished": "2018-01-23T10:10:00",
    "durationSecs": 300000,
    "fromStopId": "Stop2",
    "toStopId": "Stop2",
    "chargeAmount": 0.0,
    "companyId": "Company1",
    "busID": "Bus37",
    "pan": "122000000000003",
    "status": "CANCELLED"
  }
]
* Connection #0 to host localhost left intact
```

2. when a wrong/empty CSV is supplied:
```
$ curl -v -F file=@/f/development/poc/fare-calculator-poc/fare-calculator/src/main/resources/application.yml http://localhost:8081/api/csv/upload
* timeout on name lookup is not supported
*   Trying ::1...
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Connected to localhost (::1) port 8081 (#0)
> POST /api/csv/upload HTTP/1.1
> Host: localhost:8081
> User-Agent: curl/7.49.1
> Accept: */*
> Content-Length: 610
> Expect: 100-continue
> Content-Type: multipart/form-data; boundary=------------------------4ca263922ae61566
>
< HTTP/1.1 100
} [159 bytes data]
100   610    0     0  100   610      0     30  0:00:20  0:00:20 --:--:--     0< HTTP/1.1 500
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 01 Dec 2021 09:51:40 GMT
< Connection: close
<
{ [34 bytes data]
100   638    0    28  100   610      1     29  0:00:21  0:00:20  0:00:01     0[{"message":"CSV is empty"}]
* Closing connection 0
```


## Assumptions
1. That the input file is well-formed and is not missing data and validated.
2. File will be sent over REST http POST.
3. That it's a single Bus and Company.
4. Stops have equivalent numeric keys which are mapped in the application.yml.


## TODO's
1.Validation of data.
2. More test cases around integration and Rules unit tests.
3. Covers few scenarios, mostly happy path in integration testing.
4. Builder pattern.
5. TODO's in code level.
6. Architecture Diagram.
7. Currently using public modifiers, this need to be updated.

## Project Flow and Structure and Specifications
1. The FareCalculatorApplication is the entry point for the application where it loads the profile from application.yml.
2. Loads all the components and instantiates the classes once complete, it listens on port 8081 for csv files.
3. The FareCalculatorController receives the input csv file over POST call for "/api/csv/upload" api.
4. The controller calls AggregatorService to get matchAndPrice.
5. Rate is being calculated from a cost matrix, which is configured in application.yml

### Flow
![flow](./docs/flow.png)

## Known Issues
1. Headers seem to disappear when ordered using position annotation in output.csv
2. When csv file fields are null the following exception is thrown even though it is handled: CsvRequiredFieldEmptyException.

## Refactor

1. Before refactor we were reading two configurations from application.yml, matrix and stops to map the cost.

Old Configuration:
```
matrix: "{{0, 3.25, 7.3}, {3.25, 0, 5.5},{7.3, 5.5, 0}}"

stops:
  Stop1: 0
  Stop2: 1
  Stop3: 2
```
Now, we have a stop to price mapping configured in yaml file.
We are reading both stops and cost as a key value pair into a Map as follows:
```
stops:
Stop1Stop1: 0
Stop1Stop2: 3.25
Stop1Stop3: 7.3
Stop2Stop1: 3.25
Stop2Stop2: 0
Stop2Stop3: 5.5
Stop3Stop1: 7.3
Stop3Stop2: 5.5
Stop3Stop3: 0
```
2. Previously, we were iterating through two for loops to compute a pair of matched taps based on pan.
Now, using streams we are groupingBy pan and tapType in a single iteration.
Data structure from streams is as below:
````
{
   123={
      "OFF="[
         "Taps"{
            "ID=""",
            "DateTimeUTC=""null",
            "TapType=""OFF",
            "StopId=""",
            "CompanyId=""",
            "BusID=""",
            "PAN=""123"
         }
      ],
      "ON="[
         "Taps"{
            "ID=""",
            "DateTimeUTC=""null",
            "TapType=""ON",
            "StopId=""",
            "CompanyId=""",
            "BusID=""",
            "PAN=""123"
         }
      ]
   }
}
````
3. Updated pom.xml for validation framework using springboot custom validation as this is validated at bean level,
reduces using null checks.
Test case for validation can be found in `AggregatorServiceIntegrationTest`.
4. Added Exception handler using ControllerAdvice.
## Code changes

- Removed static in DTO's
- Made loggers static final as per standard programming practice and code clean up.
- Refactored matchTrips to loop one time to match pan and TapOnOffDTO using streams.
- Reading yaml and mapping it directly to all the possible values as Map.
- Used Object.isNull instead of checking custom null check.
- Added more test cases for most of the scenarios.
- Used ControllerAdvice for exception handling to make Controller more readable.
- Bean validation.