
# java-smobilpay-s3p-api-client-standard

The purpose of this documentation is to show how to run the examples projects which are using the smobilpay-s3p-java-client library.

All the examples are presents in **examples** folder. Let's consider that folder as our working folder for the next steps.

## Library Installation

- Create a folder **lib** in your working directory if not prensents => **examples/lib**
- Paste the downloaded library in lib folder => **examples/lib/smobilpay-s3p-java-client-x.x.x.jar** (where **x.x.x** represents the current version of the library)

##  Configuration
In other to access smobilpay apis, we need to set the information provided by Maviance for this goal.
- From our working folder, let's open the file **AccessDetails.java** and set our user information provided by Maviance such as: Server url, access token and secret.

Once these are done, we can jump to the next step which consist to run those examples.

## Run examples

### Description

It consists of 2 steps to run the examples projects.

- Compilation: ```javac -cp ".;lib/smobilpay-s3p-java-client-x.x.x.jar" EXAMPLE_FILE.java```
- Run: ```java -cp ".;lib/smobilpay-s3p-java-client-x.x.x.jar" EXAMPLE_FILE```
Where **x.x.x** is the current version of the library and **EXAMPLE_FILE** is the name of the example file we are testing.

The option **-cp** is to specify the paths of directories and jar files where class will be searched. In our case we set the **.** for the current directory (where the example file is located) and **lib/smobilpay-s3p-java-client-x.x.x.jar** the relative path of s3p library from our working folder.
To set more paths, we can separate them with **semicolon(;)** if we are using a Windows operating system and **colon(:)**  for **nix system.

### Test

Let's consider that we have the version 1.0.0 of the api.
As example, we can run the file **MasterDataApiExample.java** which provide us information about the API such as:
- API version
- Current user information (name, company, balance, ...)
- List of merchants
- List of available services.

#### Step 1: compilation

Execute:
```java
javac -cp ".;lib/smobilpay-s3p-java-client-1.0.0.jar" MasterDataApiExample.java
```
This will generate of file **MasterDataApiExample.class** with contents the byte code to run in the next step.

#### Step 2: Run

Execute:
```java
java -cp ".;lib/smobilpay-s3p-java-client-1.0.0.jar" MasterDataApiExample
```

This will use the credentials provided in the **AccessDetails.java** file to connect to Smobilpay server through the **smobilpay-s3p-java-client** API client and retrieved the above information.


