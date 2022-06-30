# Graph QL Demo

## Requirements

- Java 17.
- Gradle.
- Kotlin SDK.

## How to Run

```
./gradlew bootRun
```

## Request Examples

### Example 1:

```
curl -L -X POST 'localhost:8080/graphql' -H 'Content-Type: application/json' --data-raw '{"query":"{\n    dashboard {\n        cards {\n            type\n            dateFrom\n            dateTo\n            ... on DebitCard {\n                balance\n            }\n            ... on CreditCard {\n                availableForPurchases\n            }\n        }\n    }\n}","variables":{}}'
```

### Example 2:

```
curl -L -X POST 'localhost:8080/graphql' -H 'Content-Type: application/json' --data-raw '{"query":"{\n    quotesByType(type: PROGRAMMING, limit: 3) {\n        quote\n    }\n    randomQuote {\n        quote\n        author\n    }\n}","variables":{}}'
```
