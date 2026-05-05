# Pricing API

## Overview
A Spring Boot REST API that returns the applicable product price for a given application date, product id, and brand id.

## Tech Stack
- Java 17
- Spring Boot
- Maven
- Spring Data JPA
- H2 Database
- JUnit 5
- Mockito
- MockMvc

## Architecture
The project follows a simple hexagonal architecture:
- Domain: pure Java business model and exception.
- Application: input/output ports and the use case that applies the pricing priority rule.
- Infrastructure: REST inbound adapter and JPA persistence outbound adapter.

Package responsibilities:
- `application/port/in`: use case contracts.
- `application/port/out`: dependencies required by the application.
- `infrastructure/adapter/in/web`: REST adapter.
- `infrastructure/adapter/out/persistence`: persistence adapter.

## Business Rule
The service finds all prices that match brand, product, and date range.
If more than one price applies, the price with the highest priority is selected using a Java Stream in the application use case.

## API
```http
GET /api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1
```
Example response:
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```
When no applicable price exists, the API returns `404 Not Found`.
When a required query parameter is missing or has an invalid format, the API returns `400 Bad Request`.

Example validation error response:
```json
{
  "message": "applicationDate is required"
}
```

## How to Run
```bash
mvn spring-boot:run
```

## How to Test
```bash
mvn test
```

## H2 Console
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:pricingdb`
- User: `sa`
- Password: empty

## Test Scenarios
| Application date | Product id | Brand id | Expected price list | Expected price |
| --- | ---: | ---: | ---: | ---: |
| `2020-06-14T10:00:00` | 35455 | 1 | 1 | 35.50 |
| `2020-06-14T16:00:00` | 35455 | 1 | 2 | 25.45 |
| `2020-06-14T21:00:00` | 35455 | 1 | 1 | 35.50 |
| `2020-06-15T10:00:00` | 35455 | 1 | 3 | 30.50 |
| `2020-06-16T21:00:00` | 35455 | 1 | 4 | 38.95 |
