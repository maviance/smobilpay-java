
# java-smobilpay-s3p-api-client-standard

----
##Objective

This user guide is to be used if you wish to use the API Client by importing the provided jar into your application.

## Requirements

Smobilpay third party API java client library requires [Java Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) to be installed and configured.

You can verify it by checking the java version:

```
javac -version
```
You should see something similar to this output depending on your Java build version

```
javac 1.8.0_161
```

## Installation

Install the JAR library provided by Maviance (```smobilpay-s3p-java-client.jar```) which give you access to all Java classes that help you interact with Smobilpay APIs using Java, by importing it into your project.
The import process depends on the IDE you are using.

## Getting Started

Assuming that the library [installation](##installation) is done, copy the following example into your favorite IDE.

This example consists to get the API version information by doing a ping to server.

```java
import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.ChecksApi;
import org.maviance.s3pjavaclient.model.Ping;

class CheckApiExample {
    static final String BASE_URL = "XXXXXXXXXX";
    static final String ACCESS_TOKEN = "XXXXXXXXXXXXXXXXXXXX";
    static final String ACCESS_SECRET = "XXXXXXXXXXXXXXXXXXXXXXXXX";
    static final ApiClient API_CLIENT = new ApiClient(BASE_URL, ACCESS_TOKEN, ACCESS_SECRET);

    public static void main(String[] args) {
        ChecksApi checksApi = new ChecksApi(API_CLIENT);        
        try {
                    Ping ping = checksApi.pingGet();
                    System.out.println(ping);
                } catch (ApiException e) {
                    System.out.println("An error occurred: \n");
                    System.out.println(e.getResponseBody());
                }
    }
}
```
### Replace the placeholder variables values

Swap the placeholder values for **BASE_URL**, **ACCESS_TOKEN**, **ACCESS_SECRET** with your personal Smobilpay credentials and Server url provided by Maviance. 

### Compile and Run

Once the changes are done, you can compile and run the example code with your IDE. 

##Run the Example Files
The example files portray how the different endpoints can be used. There are two ways to run the example files:
>1. Through the command-line.
>2. In a project. 

###A) Run Example in Command Line
Let's consider that we have the version 1.1 of the api.
As example, we can run the file **MasterData.java** which provide us information about the API such as:
- API version
- Current user information (name, company, balance, ...)
- List of merchants
- List of available services.

#### Step 1: Compilation
**All the commands must be run in the folder containing the example file** <br/>

In the [*AccessDetails.java*](src/main/java/org/maviance/s3pjavaclient/examples/AccessDetails.java) file provide in the example folder, enter the required Smobilpay Credentials. Create
 a folder called lib in which you will copy the S3P Jar client.<br/>


Execute:
```
javac -cp ".;lib/smobilpay-s3p-java-client-1.0.0.jar" MasterData.java
```
This will generate of file **MasterData.class** with contents the byte code to run in the next step.

#### Step 2: Run

Execute:
```
java -cp ".;lib/smobilpay-s3p-java-client-1.0.0.jar" MasterData
```

This will use the credentials provided in the **AccessDetails.java** file to connect to Smobilpay server through the **smobilpay-s3p-java-client** API client and retrieved the above information.

###B) Run in a project
This is the simplest approach for running an example file. 
1. Open the `s3p_java_client_project` folder in your preferred IDE.
2. Enter the required details in the [*AccessDetails.java*](src/main/java/org/maviance/s3pjavaclient/examples/AccessDetails.java) 
file.
3. You can now run each of the example files.

## Documentation for API Endpoints

In all API Endpoints documentation, **BASE_URL**, **ACCESS_TOKEN**, **ACCESS_SECRET** constants have to be replaced by the one provided by Maviance.

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*ChecksApi* | [**pingGet**](docs/ChecksApi.md#pingGet) | **GET** /ping | Run a ping to check on the availability of the api
*CollectionApi* | [**billGet**](docs/CollectionApi.md#billGet) | **GET** /bill | Get bill payment handler
*CollectionApi* | [**cashinGet**](docs/CollectionApi.md#cashinGet) | **GET** /cashin | Retrieve available cashin packages
*CollectionApi* | [**cashoutGet**](docs/CollectionApi.md#cashoutGet) | **GET** /cashout | Retrieves available cashout packages
*CollectionApi* | [**collectstdPost**](docs/CollectionApi.md#collectstdPost) | **POST** /collectstd | Execute payment collection
*CollectionApi* | [**productGet**](docs/CollectionApi.md#productGet) | **GET** /product | Retrieve list of available products
*CollectionApi* | [**quotestdPost**](docs/CollectionApi.md#quotestdPost) | **POST** /quotestd | Request quote with price details about the payment
*CollectionApi* | [**subscriptionGet**](docs/CollectionApi.md#subscriptionGet) | **GET** /subscription | Get subscription payment handler
*CollectionApi* | [**topupGet**](docs/CollectionApi.md#topupGet) | **GET** /topup | Retrieve available topup packages
*CollectionApi* | [**verifyGet**](docs/CollectionApi.md#verifyGet) | **GET** /verify | Verify service number
*CollectionApi* | [**voucherGet**](docs/CollectionApi.md#voucherGet) | **GET** /voucher | Retrieve list of available vouchers to purchase
*HistoryApi* | [**historystdGet**](docs/HistoryApi.md#historystdGet) | **GET** /historystd | Retrieve list of historic payment collection.
*MasterdataApi* | [**accountGet**](docs/MasterdataApi.md#accountGet) | **GET** /account | Retrieve account information and remaining account balance
*MasterdataApi* | [**merchantGet**](docs/MasterdataApi.md#merchantGet) | **GET** /merchant | Retrieve list of merchants supported by the system.
*MasterdataApi* | [**serviceGet**](docs/MasterdataApi.md#serviceGet) | **GET** /service | Retrieve list of services supported by the system.
*MasterdataApi* | [**serviceIdGet**](docs/MasterdataApi.md#serviceIdGet) | **GET** /service/{id} | Retrieve single service


## Documentation for Models

 - [Account](docs/Account.md)
 - [Bill](docs/Bill.md)
 - [Cashin](docs/Cashin.md)
 - [Cashout](docs/Cashout.md)
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


## Documentation for Authentication

The authentication is already ensured through the calculation of a signature that will
be verified at the server level.

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Authors
Valdese Kamdem.

Florian Njiyim Lowe







