# Currency Convert REST API

Description

Service responsible for converting monetary values between various available currencies. 
The exchange rates are consumed from the service Exchange Rates. See the referenrence 
[here](https://exchangeratesapi.io/documentation/) 

## How it works:
### **1. Docker. First, you need to install docker**
* Download Docker [Here](https://docs.docker.com/desktop/);
* Then open powershell and check:
```bash
docker info
```
or check docker version
```bash
docker -v
```
or docker compose version
```bash
docker-compose -v
```
### **2. Spring boot app**
* Clone the repository:
```bash
git clone https://github.com/adrianobaz/rest-currency-convert.git
```
* Build the maven project:
```bash
./mvnw clean package -DskipTests
```
* Running the containers:

This command will build the docker containers and start them.
```bash
docker-compose up
```
or

This is a similar command as above, except it will run all the processes in the background.
```bash
docker-compose -f docker-compose.yml up
```

Appendix A.

All commands should be run from project root (where docker-compose.yml locates)

* If you have to want to see running containers. Checklist docker containers
```bash
docker container list -a
```
or
```bash
docker-compose ps
```

---

# How to consume API

You can consume the API from the user interface provided by the [Open API](https://springdoc.org/) specification at 
this [LINK](http://localhost:8088/swagger-ui.html).

## Create a new conversion transactions with success

### Request

`POST /v1/conversion-transactions`

    curl -i -H 'Accept: application/json' -d '{"userId":1,"originCurrency":"EUR","originValue":1998.89,"destinyCurrencys":["BRL","AFN","ALL","AMD"]}' http://localhost:8088/v1/conversion-transactions

### Response

    HTTP/1.1 201 Created
    Date: Mon, 24 May 2021 02:57:13 GMT
    Status: 201 Created
    Connection: close
    Content-Type: application/json
    Content-Length: 36

    [
        {
            "id": 1,
            "userId": 1,
            "originCurrency": "EUR",
            "originValue": 1998.89,
            "destinyCurrency": "BRL",
            "conversionRate": 6.533829,
            "destinyValue": 13060.41,
            "creationDateTime": "2021-05-24T02:57:13.37541Z"
        },
        {
            "id": 2,
            "userId": 1,
            "originCurrency": "EUR",
            "originValue": 1998.89,
            "destinyCurrency": "AFN",
            "conversionRate": 94.904891,
            "destinyValue": 189704.44,
            "creationDateTime": "2021-05-24T02:57:13.409642Z"
        },
        {
            "id": 3,
            "userId": 1,
            "originCurrency": "EUR",
            "originValue": 1998.89,
            "destinyCurrency": "ALL",
            "conversionRate": 123.15983,
            "destinyValue": 246182.95,
            "creationDateTime": "2021-05-24T02:57:13.409995Z"
        }
    ]   

## Try to create a new conversion transactions but violates request body constraints

### Request

`POST /v1/conversion-transactions`

    curl -i -H 'Accept: application/json' -d '{"userId":1,"originValue":1998.89,"destinyCurrencys":["BRL","AFN","ALL","AMD"]}' http://localhost:8088/v1/conversion-transactions

### Response

    HTTP/1.1 400 Bad Request
    Date: Mon, 24 May 2021 03:25:23 GMT
    Status: 400 Bad Request
    Connection: close
    Content-Type: application/json
    Content-Length: 254
    
    {
        "timestamp": "2021-05-24T03:25:23.376+00:00",
        "path": "/v1/conversion-transactions",
        "status": 400,
        "error": "Bad Request",
        "message": "Validation failed for argument at index 0 in method: public reactor.core.publisher.Flux<com.playground.restcurrencyconvert.api.model.output.ConversionTransactionOutput> com.playground.restcurrencyconvert.api.controller.ConversionTransactionController.register(com.playground.restcurrencyconvert.api.model.input.ConversionTransactionInput), with 1 error(s): [Field error in object 'conversionTransactionInput' on field 'originCurrency': rejected value [null]; codes [NotBlank.conversionTransactionInput.originCurrency,NotBlank.originCurrency,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [conversionTransactionInput.originCurrency,originCurrency]; arguments []; default message [originCurrency]]; default message [ERROR: |Initial currency origin cannot be empty!|]] ",
        "description": "Initial currency origin cannot be empty"
    }

## Create a new conversion transactions but Exchange Rates Service could not find the requested resource

### Request

`POST /v1/conversion-transactions`

    curl -i -H 'Accept: application/json' -d '{"userId":1,"originCurrency":"BRL","originValue":1998.89,"destinyCurrencys":["BRL","AFN","ALL","AMD"]}' http://localhost:8088/v1/conversion-transactions

### Response

    HTTP/1.1 404 Not Found
    Date: Mon, 24 May 2021 02:57:13 GMT
    Status: 404 Not Found
    Connection: close
    Content-Type: application/json
    Content-Length: 36
    
    {
        "timestamp": "2021-05-24T03:31:19.468+00:00",
        "path": "/v1/conversion-transactions",
        "status": 404,
        "error": "Not Found",
        "message": "404 NOT_FOUND \"Exchange Rates Service could not find the requested resource! Please, check https://exchangeratesapi.io/ \"",
        "description": "404 NOT_FOUND \"Exchange Rates Service could not find the requested resource! Please, check https://exchangeratesapi.io/ \""
    }
        
        
## Get all conversion transactions by user id

### Request

`GET /v1/users/{id}/conversion-transactions`

    curl -i -H 'Accept: text/event-stream;charset=UTF-8' http://localhost:8088/v1/users/1/conversion-transactions

### Response

    HTTP/1.1 200 OK
    Date: Thu, 24 Feb 2011 12:36:30 GMT
    Status: 200 OK
    Connection: close
    Content-Type: text/event-stream;charset=UTF-8
    Content-Length: 36

    data:{"id":1,"userId":1,"originCurrency":"EUR","originValue":1998.89,"destinyCurrency":"BRL","conversionRate":6.533829,"destinyValue":13060.41,"creationDateTime":"2021-05-24T02:57:13.37541Z"}
    
    data:{"id":2,"userId":1,"originCurrency":"EUR","originValue":1998.89,"destinyCurrency":"AFN","conversionRate":94.904891,"destinyValue":189704.44,"creationDateTime":"2021-05-24T02:57:13.409642Z"}

## Get all conversion transactions by user id that not exists

### Request

`GET /v1/users/{id}/conversion-transactions`

    curl -i -H 'Accept: application/json' http://localhost:8088/v1/users/2/conversion-transactions

### Response

    HTTP/1.1 404 Not Found
    Date: Mon, 24 May 2021 03:31:19 UTC
    Status: 404 Not Found
    Connection: close
    Content-Type: application/json
    Content-Length: 196

    {
        "timestamp": "2021-05-24T03:31:19.468+00:00",
        "path": "/v1/users/2/conversion-transactions",
        "status": 404,
        "error": "Not Found",
        "message": "404 NOT_FOUND \"User not found\"",
        "description": "404 NOT_FOUND \"User not found\""
    }