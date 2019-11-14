## TimeInCapitals

>Simple API that allows you to get time for capitals

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Example use](#example-use)
* [Features](#features)

## General info
TimeInCapitals is a SpringBoot app.

Simple API that displays the time in a specific capital.

## Technologies
* JAVA

## Setup
 Create jar: `mvnw clean install`  
 Run: `java -jar target\TimeInCapitals-0.0.1-SNAPSHOT.jar`  
 or  
 To compile and run: `mvnw spring-boot:run`
 
## Example use
To view the time and date for a specific capital, enter in your browser  
http://localhost:8080/nameOfContinent/capital  
example:  
http://localhost:8080/europe/london
 
Names of continents:  
/africa/  
/asia/  
/australia/  
/europe/  
/northamerica/  
/southamerica/
 
To display the local time and date, enter in the browser  
http://localhost:8080/localtime

Local time only  
http://localhost:8080/localtime/time
 
Local date only  
http://localhost:8080/localtime/date

## Features
* showing time in capitals
* taking into account time changes (summer / winter time) in countries where the change applies
