
# java-smobilpay-s3p-api-client-standard

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-java-client</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "io.swagger:swagger-java-client:1.0.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/swagger-java-client-1.0.0.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.ChecksApi;

import java.io.File;
import java.util.*;

public class ChecksApiExample {

    public static void main(String[] args) {
        
        ChecksApi apiInstance = new ChecksApi();
        try {
            Ping result = apiInstance.pingGet();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ChecksApi#pingGet");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://localhost/v2*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*ChecksApi* | [**pingGet**](docs/ChecksApi.md#pingGet) | **GET** /ping | Run a ping to check on the availability of the api
*CollectionApi* | [**billGet**](docs/CollectionApi.md#billGet) | **GET** /bill | Get bill payment handler
*CollectionApi* | [**cashinGet**](docs/CollectionApi.md#cashinGet) | **GET** /cashin | Retrieve available cashin packages
*CollectionApi* | [**collectstdPost**](docs/CollectionApi.md#collectstdPost) | **POST** /collectstd | Execute payment collection
*CollectionApi* | [**productGet**](docs/CollectionApi.md#productGet) | **GET** /product | Retrieve list of available products
*CollectionApi* | [**quotestdPost**](docs/CollectionApi.md#quotestdPost) | **POST** /quotestd | Request quote with price details about the payment
*CollectionApi* | [**subscriptionGet**](docs/CollectionApi.md#subscriptionGet) | **GET** /subscription | Get subscription payment handler
*CollectionApi* | [**topupGet**](docs/CollectionApi.md#topupGet) | **GET** /topup | Retrieve available topup packages
*CollectionApi* | [**verifyGet**](docs/CollectionApi.md#verifyGet) | **GET** /verify | Verify service number
*HistoryApi* | [**historystdGet**](docs/HistoryApi.md#historystdGet) | **GET** /historystd | Retrieve list of historic payment collection.
*MasterdataApi* | [**accountGet**](docs/MasterdataApi.md#accountGet) | **GET** /account | Retrieve account information and remaining account balance
*MasterdataApi* | [**merchantGet**](docs/MasterdataApi.md#merchantGet) | **GET** /merchant | Retrieve list of merchants supported by the system.
*MasterdataApi* | [**serviceGet**](docs/MasterdataApi.md#serviceGet) | **GET** /service | Retrieve list of services supported by the system.
*MasterdataApi* | [**serviceIdGet**](docs/MasterdataApi.md#serviceIdGet) | **GET** /service/{id} | Retrieve single service


## Documentation for Models

 - [Account](docs/Account.md)
 - [Bill](docs/Bill.md)
 - [Cashin](docs/Cashin.md)
 - [Collectionstd](docs/Collectionstd.md)
 - [CollectionstdRequest](docs/CollectionstdRequest.md)
 - [Error](docs/Error.md)
 - [Historystd](docs/Historystd.md)
 - [I18nText](docs/I18nText.md)
 - [Merchant](docs/Merchant.md)
 - [Ping](docs/Ping.md)
 - [Product](docs/Product.md)
 - [QuoteRequest](docs/QuoteRequest.md)
 - [Quotestd](docs/Quotestd.md)
 - [Service](docs/Service.md)
 - [Subscription](docs/Subscription.md)
 - [Topup](docs/Topup.md)


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author



