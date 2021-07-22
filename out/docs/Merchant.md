
# Merchant

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**merchant** | **String** | Unique  merchant code. Use this value whenever \&quot;merchant\&quot; is required in request parameters. | 
**name** | **String** | Name of merchant | 
**description** | **String** | Merchant description | 
**category** | **String** | deprecated  - will be removed in future versions. Use categories in services. |  [optional]
**country** | **String** | Country of service operation (ISO 3166-1 alpha-3) | 
**status** | [**StatusEnum**](#StatusEnum) | Merchant Status (Active | Inactive) | 
**logo** | **String** | Points to a URL with the logo of the merchant if available. |  [optional]
**logoHash** | **String** | MD5 Hash of the logo provided via URL – if available. This field stores the hash of the logo. If the logo is updated the hash will change. |  [optional]


<a name="StatusEnum"></a>
## Enum: StatusEnum
Name | Value
---- | -----
ACTIVE | &quot;Active&quot;
INACTIVE | &quot;Inactive&quot;



