#! /bin/bash

mvn -f Microservices/accounts/pom.xml spring-boot:build-image -DskipTests
mvn -f Microservices/cards/pom.xml spring-boot:build-image -DskipTests
mvn -f Microservices/config-server/pom.xml spring-boot:build-image -DskipTests
mvn -f Microservices/eureka-server/pom.xml spring-boot:build-image -DskipTests
mvn -f Microservices/gateway-server/pom.xml spring-boot:build-image -DskipTests
mvn -f Microservices/loans/pom.xml spring-boot:build-image -DskipTests

docker push naveejr/accounts
docker push naveejr/cards
docker push naveejr/config-server
docker push naveejr/eureka-server
docker push naveejr/gateway-server
docker push naveejr/loans
