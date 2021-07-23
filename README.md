
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
    
    class CashInCollection {
        private static final String separator = "  --  ";
    
        // Cash In service number
        private static final String serviceNumber = "237674827066";
        private static final int serviceId = 3002;
    
        // Customer details
        private static final String phone = "698223844";
        private static final String email = "name@example.com";
    
        public static void main(String[] args) {
            ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
            apiClient.setDebugging(false);
            MasterdataApi masterdataApi = new MasterdataApi(apiClient);
    
            try {
                List<Cashin> packages = masterdataApi.cashinGet(AccessDetails.VERSION, serviceId);
                System.out.println("==========================PACKAGES========================================");
                packages.forEach(item -> System.out.println(item.getServiceid() + separator + item.getName()));
                System.out.println("===========================================================================");
                Cashin cashin = packages.get(0);
    
                System.out.println("Cash-In Service: " + cashin.getServiceid());
                System.out.println("Cash-In Description: " + cashin.getDescription());
                System.out.println("Cash-In Amount: " + cashin.getAmountLocalCur());
                System.out.println("Cash-In Payment Item Id: " + cashin.getPayItemId());
    
                QuoteRequest quote = new QuoteRequest();
                quote.setAmount(500);
                quote.setPayItemId(cashin.getPayItemId());
                InitiateApi initiateApi = new InitiateApi(apiClient);
                Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);
                System.out.println("Quote ID: " + offer.getQuoteId());
                System.out.println(offer);
                ConfirmApi confirmApi = new ConfirmApi(apiClient);
                // Execute the collection
                CollectionRequest collection = new CollectionRequest();
                collection.setCustomerPhonenumber(phone);
                collection.setCustomerEmailaddress(email);
                collection.setQuoteId(offer.getQuoteId());
                collection.setServiceNumber(""+serviceNumber);
                CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);
                System.out.println("Collection Payment TX Number:" + payment.getPtn());
    
                // Lookup record in Smobilpay by PTN
                VerifyApi verifyApi = new VerifyApi(apiClient);
                List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
                if (historystds.size() != 1) {
                    System.out.println("Should have found exactly one record.");
                    System.exit(0);
                }
                System.out.println("History Result (Status): " + historystds.get(0).getStatus());
            } catch (ApiException e) {
                System.out.println("An error occurred: \n");
                System.out.println(e.getResponseBody());
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
import org.maviance.s3pjavaclient.api.InitiateApi;
import org.maviance.s3pjavaclient.api.VerifyApi;
import org.maviance.s3pjavaclient.model.*;

import java.util.List;

class CashInCollection {
    private static final String separator = "  --  ";

    // Cash In service number
    private static final String serviceNumber = "237674827066";
    private static final int serviceId = 3002;

    // Customer details
    private static final String phone = "698223844";
    private static final String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(false);
        MasterdataApi masterdataApi = new MasterdataApi(apiClient);

        try {
            List<Cashin> packages = masterdataApi.cashinGet(AccessDetails.VERSION, serviceId);
            System.out.println("==========================PACKAGES========================================");
            packages.forEach(item -> System.out.println(item.getServiceid() + separator + item.getName()));
            System.out.println("===========================================================================");
            Cashin cashin = packages.get(0);

            System.out.println("Cash-In Service: " + cashin.getServiceid());
            System.out.println("Cash-In Description: " + cashin.getDescription());
            System.out.println("Cash-In Amount: " + cashin.getAmountLocalCur());
            System.out.println("Cash-In Payment Item Id: " + cashin.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(500);
            quote.setPayItemId(cashin.getPayItemId());
            InitiateApi initiateApi = new InitiateApi(apiClient);
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);
            System.out.println("Quote ID: " + offer.getQuoteId());
            System.out.println(offer);
            ConfirmApi confirmApi = new ConfirmApi(apiClient);
            // Execute the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);
            System.out.println("Collection Payment TX Number:" + payment.getPtn());

            // Lookup record in Smobilpay by PTN
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
                System.out.println("Should have found exactly one record.");
                System.exit(0);
            }
            System.out.println("History Result (Status): " + historystds.get(0).getStatus());
        } catch (ApiException e) {
            System.out.println("An error occurred: \n");
            System.out.println(e.getResponseBody());
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

public class ProductCollection {
    private static String separator = "  --  ";

    // Number to buy product for
    private static String serviceNumber = "2577631317";
    // Cash In service number
    private static int serviceId = 20021;

    // Customer details
    private static String phone = "653754334";
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(false);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);
        InitiateApi initiateApi = new InitiateApi(apiClient);
        MasterdataApi masterDataApi = new MasterdataApi(apiClient);

        try {
            List<Product> products = masterDataApi.productGet(AccessDetails.VERSION, serviceId);
            System.out.println("===================================PRODUCTS===========================================");
            products.forEach(product -> System.out.println(product.getServiceid() + separator + product.getName()));
            System.out.println("=======================================================================================");
            Product product = products.get(0);

            System.out.println("Product Service: " + product.getServiceid());
            System.out.println("Product Description: " + product.getDescription());
            System.out.println("Product Amount: " + product.getAmountLocalCur());
            System.out.println("Product Payment Item Id: " + product.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(product.getAmountLocalCur());
            quote.setPayItemId(product.getPayItemId());

            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);
            System.out.println("Quote ID: " + offer.getQuoteId());
            System.out.println(offer);

            // Execute the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(String.valueOf(serviceNumber));
            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);
            System.out.println("Collection Payment TX Number:" + payment.getPtn());


            // Lookup record in Smobilpay by PTN
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
                System.out.println("Should have found exactly one record.");
                System.exit(0);
            }
            System.out.println("History Result (Status): " + historystds.get(0).getStatus());
        } catch (ApiException e) {
            System.out.println("An error occurred: \n");
            System.out.println(e.getResponseBody());
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

public class NonSearchableBillCollection {

    private static String merchantCode = "ENEO";
    private static int serviceId = 1001;
    // Cash In service number
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
            List<Bill> bills = initiateApi.billGet(AccessDetails.VERSION, merchantCode, serviceId, serviceNumber);
            if (bills.isEmpty()) {
                System.out.println("No matching open bills found");
                System.exit(0);
            }

            // Take the first bill in the list and request quote
            Bill bill = bills.get(0);
            System.out.println("Bill Payment Item Id: " + bill.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(amount);
            quote.setPayItemId(bill.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);
            System.out.println("Quote ID: " + offer.getQuoteId());

            // Execute the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
            collection.setCustomerAddress(address);
            collection.setCustomerName(name);
            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);
            System.out.println("Collection Payment TX Number:" + payment.getPtn());

            // Lookup record in Smobilpay by PTN
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
                System.out.println("Should have found exactly one record.");
                System.exit(0);
            }
            System.out.println("History Result (Status): " + historystds.get(0).getStatus());
        } catch (ApiException e) {
            System.out.println("An error occurred: \n");
            System.out.println(e.getResponseBody());
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

    private static String merchantCode = "ENEO";
    private static int serviceId = 10039;
    // Cash In service number
    private static String serviceNumber = "201761727";

    // Customer details
    private static String phone = "698223844";
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(false);
        InitiateApi initiateApi = new InitiateApi(apiClient);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);

        try {
            List<Bill> bills = initiateApi.billGet(AccessDetails.VERSION, merchantCode, serviceId, serviceNumber);
            if (bills.isEmpty()) {
                System.out.println("No matching open bills found");
                System.exit(0);
            }
            System.out.println("================================OPEN BILLS=====================================");
            bills.forEach(bill -> System.out.printf("%s - bill due date: %s\n",bill.getPayItemId(),bill.getBillDueDate().toString()));
            System.out.println("===============================================================================");
            Bill bill = bills.get(0);
            System.out.println(bill);
            System.out.println("Bill Payment Item Id: " + bill.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(bill.getAmountLocalCur());
            quote.setPayItemId(bill.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);
            System.out.println("Quote ID: " + offer.getQuoteId());
            System.out.println(offer);

            // Execute the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(serviceNumber);
//            collection.setCustomerName("Lowe Florian");
            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);
            System.out.println("Collection Payment TX Number:" + payment.getPtn());

            // Lookup record in Smobilpay by PTN
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
                System.out.println("Should have found exactly one record.");
                System.exit(0);
            }
            System.out.println("History Result (Status): " + historystds.get(0).getStatus());
        } catch (ApiException e) {
            System.out.println("An error occurred: \n");
            System.out.println(e.getResponseBody());
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
    private static String merchantCode = "CMSABC";
    private static int serviceId = 5000;
    // Cash In service number
    private static String serviceNumber = null;
    // Customer number
    private static String customerNumber = "0000000102";

    // Customer details
    private static String phone = "653754334";
    private static String email = "name@example.com";

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(AccessDetails.BASE_URL, AccessDetails.ACCESS_TOKEN, AccessDetails.ACCESS_SECRET);
        apiClient.setDebugging(true);
        ConfirmApi confirmApi = new ConfirmApi(apiClient);
        InitiateApi initiateApi = new InitiateApi(apiClient);

        try {
            List<Subscription> subscriptions = initiateApi.subscriptionGet(AccessDetails.VERSION, merchantCode, String.valueOf(serviceId), serviceNumber, customerNumber);
            if (subscriptions.isEmpty()) {
                System.out.println("No matching subscriptions found");
                System.exit(0);
            }

            Subscription subscription = subscriptions.get(0);
            System.out.println("Subscription Payment Item Id: " + subscription.getPayItemId());
            System.out.println("Subscription Payment Amount: " + subscription.getAmountLocalCur());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(1000);
            quote.setPayItemId(subscription.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);
            System.out.println("Quote ID: " + offer.getQuoteId());

            // Execute the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerNumber(customerNumber);
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);
            System.out.println("Collection Payment TX Number:" + payment.getPtn());

            // Lookup record in Smobilpay by PTN
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
                System.out.println("Should have found exactly one record.");
                System.exit(0);
            }
            System.out.println("History Result (Status): " + historystds.get(0).getStatus());
        } catch (ApiException e) {
            System.out.println("An error occurred: \n");
            System.out.println(e.getResponseBody());
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

    private static int serviceId = 20062;

    // Cash In service number
    private static String serviceNumber = "698223844";

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

            List<Topup> topups = masterdataApi.topupGet(AccessDetails.VERSION, serviceId);
            topups.forEach(topup -> System.out.println(topup.getServiceid() + separator + topup.getName()));

            //to be updated with the particular type of topup to carry out
            int indexOfTopup = 0;
            Topup topup = topups.get(indexOfTopup);
            if (topup.getAmountType() == Topup.AmountTypeEnum.CUSTOM) {
                //you must set the amount;
                final int topUpAmount = 100;
                topup.setAmountLocalCur(topUpAmount);
            }
            System.out.println("Topup Service: " + topup.getServiceid());
            System.out.println("Topup Description: " + topup.getDescription());
            System.out.println("Topup Amount: " + topup.getAmountLocalCur());
            System.out.println("Topup Payment Item Id: " + topup.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(topup.getAmountLocalCur());
            quote.setPayItemId(topup.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);
            System.out.println("Quote ID: " + offer.getQuoteId());

            // Execute the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);
            collection.setCustomerName("Lowe Florian");
            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);
            System.out.println("Collection Payment TX Number:" + payment.getPtn());

            // Lookup record in Smobilpay by PTN
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
                System.out.println("Should have found exactly one record.");
                System.exit(0);
            }
            System.out.println("History Result (Status): " + historystds.get(0).getStatus());
        } catch (ApiException e) {
            System.out.println("An error occurred: \n");
            System.out.println(e.getResponseBody());
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

    // Cash In service number
    private static String serviceNumber = "014375112886";

    // Customer details.This refers to the information of the smobilpay account owner
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

            List<Product> products = masterdataApi.voucherGet(AccessDetails.VERSION, serviceId);

            products.forEach(product -> System.out.println(product.getServiceid() + separator + product.getName()));

            Product voucher = products.get(0);
            //set the voucher amount.
            voucher.setAmountLocalCur(1000);

            System.out.println("Voucher Service: " + voucher.getServiceid());
            System.out.println("Voucher Description: " + voucher.getDescription());
            System.out.println("Voucher Amount: " + voucher.getAmountLocalCur());
            System.out.println("Voucher Payment Item Id: " + voucher.getPayItemId());

            QuoteRequest quote = new QuoteRequest();
            quote.setAmount(voucher.getAmountLocalCur());
            quote.setPayItemId(voucher.getPayItemId());
            Quote offer = initiateApi.quotestdPost(AccessDetails.VERSION, quote);
            System.out.println("Quote ID: " + offer.getQuoteId());

            // Execute the collection
            CollectionRequest collection = new CollectionRequest();
            collection.setCustomerPhonenumber(phone);
            collection.setCustomerEmailaddress(email);
            collection.setQuoteId(offer.getQuoteId());
            collection.setServiceNumber(""+serviceNumber);

            CollectionResponse payment = confirmApi.collectstdPost(AccessDetails.VERSION, collection);
            System.out.println("Collection Payment TX Number:" + payment.getPtn());
            System.out.println("Voucher PIN: " + payment.getPin());

            // Lookup record in Smobilpay by PTN
            VerifyApi verifyApi = new VerifyApi(apiClient);
            List<PaymentStatus> historystds =  verifyApi.verifytxGet(AccessDetails.VERSION, payment.getPtn(), null);
            if (historystds.size() != 1) {
                System.out.println("Should have found exactly one record.");
                System.exit(0);
            }
            System.out.println("History Result (Status): " + historystds.get(0).getStatus());
        } catch (ApiException e) {
            System.out.println("An error occurred: \n");
            System.out.println(e.getResponseBody());
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

## Authors
- Valdese Kamdem.
- Florian Njiyim Lowe
- Akongnwi C. Devert







