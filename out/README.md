
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







