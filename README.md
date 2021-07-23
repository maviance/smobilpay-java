
# java-s3p-api-client

----
## Objective

This user guide is to be used if you wish to use the API Client by importing the provided jar into your application.

## Requirements

S3P, third party API Java client library requires [Java Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) to be installed and configured.

You can verify it by checking the java version:

```
javac -version
```
You should see something similar to this output depending on your Java build version

```
javac 1.8.0_161
```

## Installation

Install the JAR library provided by Maviance (```s3p-java-client.jar```) which give you access to all Java classes that help you interact with S3P APIs using Java, by importing it into your project.
The import process depends on the IDE you are using.

## Getting Started

Assuming that the library [installation](##installation) is done, copy the following example into your favorite IDE.
Create a file called `Check.java` copy the content below into this file.
This example consists in getting the API version information by doing a ping to server.

```java
package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.HealthcheckApi;
import org.maviance.s3pjavaclient.model.Ping;


class Check {

 public static void main(String[] args) {
  ApiClient apiClient = new ApiClient("<BASE_URL>", "<ACCESS_TOKEN>", "<ACCESS_SECRET>");

  HealthcheckApi checksApi = new HealthcheckApi(apiClient);

  try {
   Ping ping = checksApi.pingGet(AccessDetails.VERSION);
   System.out.println(ping);
  } catch (ApiException e) {
   System.out.println("An error occurred: \n");
   System.out.println(e.getResponseBody());
  }
 }
}
```
### Replace the placeholder variables values

Swap the placeholder values for **BASE_URL**, **ACCESS_TOKEN**, **ACCESS_SECRET** with your personal S3P credentials and Server url provided by Maviance. 

### Compile and Run

Once the changes are done, you can compile and run the example code with your IDE. 

## Run the Example
The example above portrays how the different ping endpoint can be called. There are two ways to run the example file:
>1. Through the command-line.
>2. In a project. 

## Example per Service Type

### Cashin
```java
    package org.maviance.s3pjavaclient.examples;
    
    import org.maviance.s3pjavaclient.ApiClient;
    import org.maviance.s3pjavaclient.ApiException;
    import org.maviance.s3pjavaclient.api.ConfirmApi;
    import org.maviance.s3pjavaclient.api.MasterdataApi;
    import org.maviance.s3pjavaclient.api.InitiateApi;
    import org.maviance.s3pjavaclient.api.VerifyApi;
    import org.maviance.s3pjavaclient.model.*;
    
    import java.util.List;
    
    class CashInCollectionExample {
        private static final String separator = "  --  ";
        
        // Some sample values - these are not valid identifiers
        // Cash In service number -> In this case a msisdn
        private static final String serviceNumber = "2371122334455";
        private static final int serviceId = 999999;
    
        // Customer details
        private static final String phone = "23712345678";
        private static final String email = "name@example.com";
    
        public static void main(String[] args) {
            ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
            apiClient.setDebugging(false);
            MasterdataApi masterdataApi = new MasterdataApi(apiClient);
    
            try {
            
                // Retrieve available cashin packages
                
                List<Cashin> packages = masterdataApi.cashinGet(AccessDetails.VERSION, serviceId);
                
                // Select the first packages for sake of demonstration
                
                Cashin cashin = packages.get(0);
    
                // Retrieve pricing information by requesting a quote for a set amount for the linked payment item id   
                
                QuoteRequest quote = new QuoteRequest();
                quote.setAmount(500);
                quote.setPayItemId(cashin.getPayItemId());                 
                InitiateApi initiateApi = new InitiateApi(apiClient);
                Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);                
                
                // Finalize by confirming the collection
                
                ConfirmApi confirmApi = new ConfirmApi(apiClient);
                CollectionRequest collection = new CollectionRequest();
                collection.setCustomerPhonenumber(phone);
                collection.setCustomerEmailaddress(email);
                collection.setQuoteId(offer.getQuoteId());
                collection.setServiceNumber(""+serviceNumber);
                CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);
    
                // Lookup record in Smobilpay by PTN to retrieve the payment status
                
                VerifyApi verifyApi = new VerifyApi(apiClient);
                List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
                if (historystds.size() != 1) {
                // Should have found exactly one record."
                    System.exit(0);
                }
            } catch (ApiException e) {
                // Add more detailed handling here 
            }
    
        }
    }
```

### Cashout

```java
package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.ConfirmApi;
import org.maviance.s3pjavaclient.api.MasterdataApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.api.InitiateApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

public class CashOutCollectionExample {
    private static final String separator = "  --  ";

    // Some sample values - these are not valid identifiers
    // Cash Out service number -> In this case a msisdn
    private static final String serviceNumber = "2371122334455";
    private static final int serviceId = 969873;

    // Customer details
    private static final String phone = "23712345678";
    private static final String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(false);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);
        InitiateApi initiateApi = new InitiateApi(apiClient);
        MasterdataApi masterDataApi = new MasterdataApi(apiClient);

        try {
        
            // Retrieve available cash out packages
            
            List<Cashout> packages = masterDataApi.cashoutGet(AccessDetails.VERSION, serviceId);
            
            // Select the first packages for sake of demonstration
            Cashout cashout = packages.get(0);

            // Retrieve pricing information by requesting a quote for a set amount for the linked payment item id   

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(2000);
            quote.setPayItemId(cashout.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);

            // Finalize by confirming the collection
            
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);

            // Lookup record in Smobilpay by PTN to retrieve the payment status
            
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
            // Should have found exactly one record."
                System.exit(0);
            }
        } catch (ApiException e) {
            // Add more detailed handling here 
        }

    }
}

```

### Product

```java
package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.MasterdataApi;
import org.maviance.s3pjavaclient.api.ConfirmApi;
import org.maviance.s3pjavaclient.api.InitiateApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

public class ProductCollectionExample {
    private static String separator = "  --  ";

    // Some sample values - these are not valid identifiers
    private static String serviceNumber = "011234665878";
    // Product service number
    private static int serviceId = 888887;

    // Customer details
    private static String phone = "123754334";
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(false);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);
        InitiateApi initiateApi = new InitiateApi(apiClient);
        MasterdataApi masterDataApi = new MasterdataApi(apiClient);

        try {
            // Retrieve available product packages 

            List<Product> products = masterDataApi.productGet(AccessDetails.VERSION, serviceId);
            
            // Select the first product for sake of demonstration
            
            Product product = products.get(0);

            // Retrieve pricing information by requesting a quote for a set amount for the linked payment item id   

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(product.getAmountLocalCur());
            quote.setPayItemId(product.getPayItemId());

            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);

            // Finalize by confirming the collection
            
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(String.valueOf(serviceNumber));
            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);

            // Lookup record in Smobilpay by PTN to retrieve the payment status
            
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
                // Should have found exactly one record."
                System.exit(0);
            }
        } catch (ApiException e) {
            // add more handling
        }
    }
}

```
### Non Searchable Bill

```java 
package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.ConfirmApi;
import org.maviance.s3pjavaclient.api.InitiateApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

public class NonSearchableBillCollectionExample {

    // Some sample values - these are not valid identifiers
    private static String merchantCode = "ENEO";
    private static int serviceId = 98999;
    
    private static String serviceNumber = "";
    private static Integer amount = 100;

    // Customer details
    private static String phone = "653754334";
    private static String email = "name@example.com";
    private static String address = "My Street N33";
    private static String name = "John Doe";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(true);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);
        InitiateApi initiateApi = new InitiateApi(apiClient);

        try {
        
            // Retrieve open bills and list them out 
        
            List<Bill> bills = initiateApi.billGet(AccessDetails.VERSION, merchantCode, serviceId, serviceNumber);
            if (bills.isEmpty()) {
            // Should have found atleast one record."
                System.exit(0);
            }

            // Select the first bill for sake of demonstration
            Bill bill = bills.get(0);
            
            // Retrieve pricing information by requesting a quote for a set amount for the linked payment item id   
            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(amount);
            quote.setPayItemId(bill.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);

            // Finalize by confirming the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
            collection.setCustomerAddress(address);
            collection.setCustomerName(name);
            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);

            // Lookup record in Smobilpay by PTN to retrieve the payment status
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
            // Should have found exactly one record."
                System.exit(0);
            }
        } catch (ApiException e) {
            // add more handling
        }

    }
}
```

### Searchable Bill
```java 
package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.ConfirmApi;
import org.maviance.s3pjavaclient.api.InitiateApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

public class SearchableBillCollection {

    // Some sample values - these are not valid identifiers
    private static String merchantCode = "ENEO";
    private static int serviceId = 10039;
    
    // Service number is the contract number with the merchant
    private static String serviceNumber = "2021961727";

    // Customer details
    private static String phone = "698223844";
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(false);
        InitiateApi initiateApi = new InitiateApi(apiClient);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);

        try {
            // Retrieve open bills and list them out 
            List<Bill> bills = initiateApi.billGet(AccessDetails.VERSION, merchantCode, serviceId, serviceNumber);
            if (bills.isEmpty()) {
            // Should have found atleast one record."
                System.exit(0);
            }
            
            // Select the first bill for sake of demonstration
            Bill bill = bills.get(0);

            // Retrieve pricing information by requesting a quote for a set amount for the linked payment item id   

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(bill.getAmountLocalCur());
            quote.setPayItemId(bill.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);

            // Finalize by confirming the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(serviceNumber);
//            collection.setCustomerName("Lowe Florian");
            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);

            // Lookup record in Smobilpay by PTN to retrieve the payment status
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
            // Should have found exactly one record."
                System.exit(0);
            }
        } catch (ApiException e) {
            // add more handling
        }

    }
}
```
### Subscription
```java 
package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.ConfirmApi;
import org.maviance.s3pjavaclient.api.InitiateApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

public class SubscriptionCollection {

    // Some sample values - these are not valid identifiers
    // customer number - customer identifier in biller's system
   
    private static String merchantCode = "SABC";
    private static int serviceId = 001235485;
    // Subscription service number
    private static String serviceNumber = null;
    // Customer number
    private static String customerNumber = "0000000999";

    // Customer details
    private static String phone = "6532548545";
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(true);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);
        InitiateApi initiateApi = new InitiateApi(apiClient);

        try {
            // Retrieve available packages and list them out 
            List<Subscription> subscriptions = initiateApi.subscriptionGet(AccessDetails.VERSION, merchantCode, String.valueOf(serviceId), serviceNumber, customerNumber);
            if (subscriptions.isEmpty()) {
            // Should have found atleast one record."
                System.exit(0);
            }
            
            // Select the first package for sake of demonstration

            Subscription subscription = subscriptions.get(0);


            // Retrieve pricing information by requesting a quote for a set amount for the linked payment item id   

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(1000);
            quote.setPayItemId(subscription.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);

            // Finalize by confirming the collection

            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerNumber(customerNumber);
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);

            // Lookup record in Smobilpay by PTN to retrieve the payment status
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
            // Should have found exactly one record."
                System.exit(0);
            }
        } catch (ApiException e) {
            // add more handling here
        }

    }
}

```
### Top Up 
```java
package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.ConfirmApi;
import org.maviance.s3pjavaclient.api.InitiateApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.api.MasterdataApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

public class TopupCollection {
    private static String separator = "  --  ";

    // Some sample values - these are not valid identifiers

    private static int serviceId = 9998878;

    // Top up service number
    private static String serviceNumber = "987987987";

    // Customer details
    private static String phone = "698223844";
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(false);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);
        MasterdataApi masterdataApi = new MasterdataApi(apiClient);
        InitiateApi initiateApi = new InitiateApi(apiClient);

        try {
            // Retrieve available topup packages 

            List<Topup> topups = masterdataApi.topupGet(AccessDetails.VERSION, serviceId);

            // Select the first top up package for sake of demonstration

            int indexOfTopup = 0;
            Topup topup = topups.get(indexOfTopup);
            if (topup.getAmountType() == Topup.AmountTypeEnum.CUSTOM) {
                //you must set the amount;
                final int topUpAmount = 100;
                topup.setAmountLocalCur(topUpAmount);
            }

            // Retrieve pricing information by requesting a quote for a set amount for the linked payment item id   

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(topup.getAmountLocalCur());
            quote.setPayItemId(topup.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);

            // Finalize by confirming the collection
            
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
            collection.setCustomerName("Lowe Florian");
            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);

            // Lookup record in Smobilpay by PTN to retrieve the payment status
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
            // Should have found exactly one record."
                System.exit(0);
            }
        } catch (ApiException e) {
             // add more handling here
        }

    }
}

```
### Voucher
```java
package org.maviance.s3pjavaclient.examples;

import org.maviance.s3pjavaclient.ApiClient;
import org.maviance.s3pjavaclient.ApiException;
import org.maviance.s3pjavaclient.api.ConfirmApi;
import org.maviance.s3pjavaclient.api.InitiateApi;
import org.maviance.s3pjavaclient.api.MasterdataApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

public class VoucherCollection {
    private static String separator = "  --  ";

    private static int serviceId = 2000;

    // Voucher service number
    private static String serviceNumber = "00000123456";

    // Customer details
    private static String phone = "653754334";
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(true);
        apiClient.setDebugging(false);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);
        MasterdataApi masterdataApi = new MasterdataApi(apiClient);
        InitiateApi initiateApi = new InitiateApi(apiClient);

        try {
            // Retrieve available voucher packages 
            List<Product> products = masterdataApi.voucherGet(AccessDetails.VERSION, serviceId);

            // Select the first top up package for sake of demonstration

            Product voucher = products.get(0);
            //set the voucher amount.
            voucher.setAmountLocalCur(1000);

            // Retrieve pricing information by requesting a quote for a set amount for the linked payment item id   

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(voucher.getAmountLocalCur());
            quote.setPayItemId(voucher.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);

            // Finalize by confirming the collection
            
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);

            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);

            // Lookup record in Smobilpay by PTN to retrieve the payment status
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
            // Should have found exactly one record."
                System.exit(0);
            }
        } catch (ApiException e) {
            // add more handling
        }

    }
}

```

### A) Run Example in Command Line
Let's consider that we have the version 3.0.2 of the api.
As example, we can run the file **Check.java** checks to see if the server is up and running nolly

Execute:
```
javac -cp ".;lib/s3p-java-client-1.0.0.jar" Check.java
```
This will generate a file **Check.class** which contains the byte code to run in the next step.

#### Step 2: Run

Execute:
```
java -cp ".;lib/s3p-java-client-1.0.0.jar" Check
```

###B) Run in a project
This is the simplest approach for running an example file. 
1. Copy the `s3p_java_client_project` folder into your project.
2. You can now use the classes in the library as shown in the `docs` folder

## Documentation for Authentication

The authentication process is handled by the library. All you have to do is provide the BASE_URL, ACCESS_TOKEN and ACCESS_SECRET as shown in the example above 


