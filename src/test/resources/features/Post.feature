Feature: Post products functionality


 Scenario Outline: Verify post method for products with valid fields
  Given User has product with "<name>", "<price>", "<itemCount>" and "<active>" expecting "<expectedResultCode>"
   When User post this product to server
   And Checks post method response status
   And User making a get call for posted product by ID
   Then Server response product correspond to posted one

   Examples:
     |name|price|itemCount|active|expectedResultCode|
     |Product|10|2|true|200|
     |Product name length of 50 symbols is max   allowed|15|3|true|200|
     |Inactive product|1|3|false|200|


  Scenario Outline: Verify posting product with duplicated name is not allowed
    Given User has product with "<name>", "<price>", "<itemCount>" and "<active>" expecting "<expectedResultCode>"
    When User post this product to server
    And Checks post method response status
    And User making a get call for posted product by ID
    Then Server response product correspond to posted one

    Examples:
      |name|price|itemCount|active|expectedResultCode|
      |Product|10|2|true|400|

  Scenario: Verify product names >50 symbols are not allowed
    Given User post new product with name > 50 symbols
    When User post this product to server
    Then Server response error 400

  Scenario: Verify product name is required field
    Given User post new product without product name
    When User post this product to server
    Then Server response error 400