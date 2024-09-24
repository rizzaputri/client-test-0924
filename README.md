# Maju Mundur Eshop

## Overview
Maju Mundur is a clothing marketplace to connect multiple 
merchant and customer. To be able to maintain retention customer, 
Maju Mundur preparing reward point to customer who succeed transaction. 
Reward point can be trade with reward which already prepared by Maju Mundur. 
There is two reward, Reward A worth 20 point and Reward B worth 40 point.

## Endpoints
### Merchants
- Register
  - Endpoint: `/api/v1/auth/register-merchants`
  - Method: POST
  - Request Body:
    ```json 
    {
    "username": "string",
    "password": "string",
    "shopName": "string",
    "email": "string"
    }
    ```
    
- Create Product
  - Endpoint: `/api/v1/auth/products`
  - Method: POST
  - Header: `Authorization` - Bearer Token
  - Request Body:
    ```json 
    {
    "productName": "string",
    "unitPrice": "long",
    "stock": "integer"
    }
    ```

- Get Customer
  - Endpoint: `/api/v1/auth/customer`
  - Method: GET
  - Header: `Authorization` - Bearer Token

### Customers
- Register
    - Endpoint: `/api/v1/auth/register-customers`
    - Method: POST
    - Request Body:
      ```json 
      {
      "username": "string",
      "password": "string",
      "name": "string",
      "email": "string"
      }
      ```
      
- Get Products
  - Endpoint: `/api/v1/auth/products`
  - Method: GET

- Create Transaction
  - Endpoint: `/api/v1/auth/transactions`
  - Method: POST
  - Header: `Authorization` - Bearer Token
  - Request Body:
    ```json 
    {
    "requests": [{
    "productId":  "string",
    "quantity":  "integer"
    }]
    }
    ```

- Get Rewards
  - Endpoint: `/api/v1/auth/rewards`
  - Method: PUT
  - Header: `Authorization` - Bearer Token
  - Request Body:
    ```json 
    {
    "rewardId": "string"
    }
    ```
