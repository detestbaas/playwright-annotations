# Playwright Additional Annotations

## Prerequisites 
* JDK21 or higher
* Playwright 1.41.2 or higher

## Installation
Place this piece of code in your pom.xml
```xml
<dependency>
    <groupId>nl.detestbaas</groupId>
    <artifactId>playwright-annotations</artifactId>
    <version>1.0.0</version>
</dependency>
```
## Usage
In your constructor of your Page Object you need to call `PageFactory.initAnnotatedLocators(page, this);` to initialize the
locators, where `page` is your Playwright Page and this is the page from your Page Object Model.

In your PageObject, you can now use `@LocateBy` annotations. So for example:
```java
    @LocateBy("#password")
    private Locator passwordField;
```
This will locate the input tag with id `password` and place it into the field passwordField

A few more examples:

`@LocateBy("li:visible.my-account a")` for using CSS or XPATH locators

`@LocateBy(how = How.LABEL, "Log in")` for using getByLabel with normal text

`@LocateBy(how = How.LABEL, pattern = "")` for using getByLabel using a regex Pattern

`@LocateBy(how = How.ROLE, ariaRole = AriaRole.BUTTON, "Submit")` for using getByRole locators

## Limitations

Using the Playwright Additional Annotations, you cannot use the options:
* GetByAltTextOptions
* GetByLabelOptions
* GetByPlaceholderOptions
* GetByTextOptions
* GetByTitleOptions

Copyright (c) 2024 De Testbaas

Licensed under the Apache License, Version 2.0 (the "License"),
http://www.apache.org/licenses/LICENSE-2.0