# Quote

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**quoteId** | **String** | Unique quote number (UUID) | 
**expiresAt** | [**LocalDate**](LocalDate.md) | Expiration timestamp. The quote will only stay active the expiration time. (Format: ISO 8601) | 
**payItemId** | **String** | Unique  Payment Item ID identifying the item to request the quote for | 
**amountLocalCur** | **Float** | Service amount in local currency | 
**priceLocalCur** | **Float** | Price of payment in local currency | 
**priceSystemCur** | **Float** | Price of payment in system currency | 
**localCur** | **String** | Local currency of service. (Format: ISO 4217) | 
**systemCur** | **String** | Currency of billing by  system. (Format: ISO 4217) | 
**promotion** | **String** | Optional comma seperated list of current or upcoming promotions offered by the quoted service |  [optional]
