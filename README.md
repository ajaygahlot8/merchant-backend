# Merchant Transaction Backend Application

### Project Structure
###### unzip the main merchant-backend.zip folder
1. merchant-backend [SpringBoot2.4.2|Java11|Gradle|H2Database]

### Steps to compile and test
```sh
$ cd merchant-backend
$ ./gradlew clean build
```
- This will compile, build and run the unit test, database integration tests and controller tests.
- Gradle wrapper is used hence system without gradle will be able to run this apploication
- Code coverage of the project is > 90%(Other than lombok annotations) . Checked it with IntelliJ code coverage functionality

### Steps to run the application
```sh
$ cd merchant-backend
$ ./gradlew bootRun
```
- This will run the spring boot server on ***port 4567***

### Assumptions while building application
- Update API only updates status
- Delete API changes transaction status to `DELETED`
- You cannot call GET API or Update API on `DELTETED` transaction id
- Get API has 3 filters `status`, `currency` and `date`
- Only 2 currencies are supported `GBP` and `USD`
- If you do not provide filters in GET API it will return all non deleted transactions
- All API's are behind authentication using JWT based authentication mechanism
- Some dummy transactions are added via flyway migration

### Approaches/Patterns/OtherDetails used
1. Test Driven Development
2. Domain Driven Design
3. Hexagonal Architecture Project Structure [Ports and Adapters Pattern]
4. Flyway for Migrations
5. Used JWT based authentication mechanism
5. Spring boot JDBC integration tests
6. Spring boot controller tests
7. Mockito for unit tests
8. H2 In-MemoryDatabase


### Authentication
- In interest of time I have hardcoded the username and password in the code.
- All API's are behind authentication and you would require to call below api to get the authorization bearer token, which you can then pass into all other API's.
- Default username is `payvyne` and password is `password123`
```
curl --location --request POST 'http://localhost:4567/api/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username" : "payvyne",
    "password" : "password123"
}'
```

### CRUD API's
- Create transaction API
```
curl --location --request POST 'http://localhost:4567/api/v1/transaction' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXl2eW5lIiwiZXhwIjoxNjQ2NzAyNzY5LCJpYXQiOjE2NDY2NjY3Njl9.x4m6ngmsW8KxMeB5ZKlaiRX4RBFJ6mkeCQNX54jmPic' \
--header 'Content-Type: application/json' \
--data-raw '{
    "amount" : 1200.50,
    "currency" : "GBP",
    "status" : "PENDING",
    "description" : "for computer sales"
}'
```
- Update Transaction API
```
curl --location --request PUT 'http://localhost:4567/api/v1/transaction/aa6be991-8902-4728-ae5c-3687d7f5da0b' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXl2eW5lIiwiZXhwIjoxNjQ2NzAyNzY5LCJpYXQiOjE2NDY2NjY3Njl9.x4m6ngmsW8KxMeB5ZKlaiRX4RBFJ6mkeCQNX54jmPic' \
--header 'Content-Type: application/json' \
--data-raw '{
    "status" : "SUCCEED"
}'
```
- Delete Transaction API
```
curl --location --request DELETE 'http://localhost:4567/api/v1/transaction/aa6be991-8902-4728-ae5c-3687d7f5da0b' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXl2eW5lIiwiZXhwIjoxNjQ2NzAyNzY5LCJpYXQiOjE2NDY2NjY3Njl9.x4m6ngmsW8KxMeB5ZKlaiRX4RBFJ6mkeCQNX54jmPic'
```
- Get transactions with filters

```
curl --location --request GET 'http://localhost:4567/api/v1/transactions?status=PENDING&currency=GBP&date=2022-03-07' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXl2eW5lIiwiZXhwIjoxNjQ2NzAyNzY5LCJpYXQiOjE2NDY2NjY3Njl9.x4m6ngmsW8KxMeB5ZKlaiRX4RBFJ6mkeCQNX54jmPic'
```

### System Health Endpoint
```
curl --location --request GET 'http://localhost:4568/health'
```

Please do reach out to me in case something does not work.Thanks
#### Author
##### [Ajay Singh]
[Ajay Singh]: <https://www.linkedin.com/in/ajaygahlot/>
