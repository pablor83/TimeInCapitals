#! /bin/bash

if [ $1 == "dev" ]; then
	mvn clean install
	java -jar target/TimeInCapitals-0.0.1-SNAPSHOT.jar
else
	mvn spring-boot:run
fi