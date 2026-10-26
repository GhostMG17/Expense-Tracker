# Expense Tracker: Specification
## Purpose

A backend service where a user records their expenses and sees where their money went


## Entities 

### User
| Field     | Type   | Notes                  |
|-----------|--------|------------------------|
| id        | Long   | assigned by the server |
| firstname | String |
| lastname  | String |
| email     | String | unique                 |
| phone     | String | optional

### Category
| Field | Type    | Notes                                  |
|-------|---------|----------------------------------------|
| id    | Long    | assigned by the server                 |
| name  | String  | unique, not blank; shared by all users |

### Expense
| Field       | Type       | Notes                            |
|-------------|------------|----------------------------------|
| id          | Long       | assigned by the server           |
| amount      | BigDecimal | strictly greater than zero       |
| date        | LocalDate  | not in the future                |
| description |            |                                  |
| userId      | Long       | the owner of the expense         |
| categoryId  | Long       | exactly one category per expense |

## Relationships
- One user has many expenses 
- One category has many expenses
- Each expense belongs to exactly one user and exactly one category.

## Features (version 1)

1. Create a category.
2. List categories. 
3. Create a user.
4. Add an expense.
5. List a user's expenses for a period (from, to).
6. Get a user's total per category for a month.

## Business rules

- Money is stored as 'BigDecimal', never 'double'.
- Expense amount must be greater than zero.
- Expense date must not be in the future.
- Category name and user email must be unique.
- Ids are assigned by the server; ids sent by the client are ignored.
- An expense must reference an existing user and an existing category.

## Out of scope for version 1
- Authentication and authorization (the user is identified by id).
- Multiple currencies.
- Several categories per expense.
- Charts, imports from banks, notifications.

## Roadmap

1. In-memory version (lists), to get the logic working.
2. PostgreSQL with Spring Data JPA.
3. Validation, error handling (404 / 400), tests.
