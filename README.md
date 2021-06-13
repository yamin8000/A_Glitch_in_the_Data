
# A Glitch in the Data

> A no-nonsense Android Library for Form Validation and String Matching

  [![Build](https://api.travis-ci.com/yamin8000/A_Glitch_in_the_Data.svg?branch=master)](https://travis-ci.com/github/yamin8000/A_Glitch_in_the_Data)
  
|-|-|
|--|--|
|📺|[Preview](#Preview)|
|📱|[Compatibility](#Compatibility)|
|💻|[Usage](#Usage)|
|📩|[Download](#Download)|
|📋|[Features](#Features)|
|🧾|[Changelog](#Changelog)|
|🏆|[Credits](#Credits)|
|👨‍💻|[Contribution](#Contribution)|
|⚖️|[License](#License)|


## Preview

![ScreenShot](/screen.webp)

## Compatibility
 Android Jellybean 4.1+/SDK 16+
## Usage
Simple usage:

Forms
```kotlin
    val element = FormElement(edittext1)  
    val form = FormValidator()  
    form.add(element).withRule(Rules.Digit())  
    Log.d("TAG","${form.isValid()}")
```
This is true when text is digit only.

Strings
```kotlin
    val stringValidator = StringValidator().addWithRule("111", Rules.Digit())  
      .addWithRule("a1aa", Rules.Decimal())  
    println(stringValidator.isValid())
```
This simple type of validation is passive and only should be used for debugging mode or for checking strings, but you can validate your form in active way, using active way library will manufacture a `TextWatcher` for each of elements and as you type validation is checked and error message is shown.

```kotlin
    val form = FormValidator() 
    form.addWithRule(FormElement(edittext1).error("sss"),  
      Rules.Length.Min(3, "You must enter at least 3 characters"),  
      Rules.AlphaNumeric("Alpha Numeric Only"))  
    form.addWithRule(FormElement(edittext2),  
      Rules.Length.Min(10, "You can not enter less than 10 characters"),  
      Rules.Digit("You can only enter digits"))  
    form.addWithRule(FormElement(edittext3), Rules.NotEmpty("You can not enter empty text"))  
      
    form.addSubmitButton(findViewById(R.id.submitButton))  
      
    form.activeCheck()
```
Elements also can have name which are used when you want to know exactly which element is valid or not, where `states` is a `MutabeMap<String, Boolean>` with a pair of element name and element validity.
```kotlin
    FormElement(edittext1, "myelementname")
    val states = form.getFormState()
```
Elements can have general error message:

```kotlin
    FormElement(...).error("enter a proper input")
```
Alternatively error messages can be different for each rule:
```kotlin
    form.addWithRule(FormElement(edittext2),  
      Rules.Length.Min(10, "You can not enter less than 10 characters"),  
      Rules.Digit("You can only enter digits"))
```
For the sake of brevity and convenience elements and rules can be added in different kind of ways:
you can pass a single element, `variable argument` of elements or a `List` of elements
```kotlin
    form.add(element,element,...)
    form.add(elementList)
```
Above format is also applied when adding elements with rule or alternatively you can create a `Map` with pair of element and rules and pass it as an argument.

One thing to consider is you can  also add (a) rule/rules as global rule for elements but you should not combine this with different rules for each element. you either set a global rule for all elements or set a different rule for each element.
```kotlin
    form.add(...).add(...).withRule(...)
```
  For forms a submit button also can be added, adding a submit button makes it disabled. submit button will be enabled when every element of form is valid.
```kotlin
      form.addSubmitButton(button)
```
Currently following Rules are supported:

 - [x] Digit - (1, 2225, 12)
 - [x] Decimal - (1.1, 0.5, .5)
 - [x] Not Empty - (returns true when input is not empty)
 - [x] Length - (returns true only when exactly equal to provided length)
 - [x] Minimum Length - (returns true when length is equal or bigger than minimum)
 - [x] Maximum Length - (returns true when length is less than or equal maximum)
 - [x] Regex - (custom regex pattern)
 - [x] Custom - (custom logic provided by you)
 - [x]  Arabic Numbers - (۰, ۹)
 - [x] Persian Numbers - (٠-٩) 
 - [x] Persian Text - (سلام دنیا)
 - [x] Iranian Mobile Numbers - (09111257622)
 - [x] Iranian National Code
 - [ ] Email
 - [ ] URL
 - [ ] ...

You can use custom rule like this:
```kotlin
    form.addWithRule(FormElement(input), Rules.Custom {  
      val isValid = false  
      //your logic here  
      return@Custom isValid  
    })
```
#### Notice
This library doesn't delete whitespace so `125` is a digit but `111 25` is not a digit (sometime mobile number is typed in that format with spaces between every three number) if you want to check if the second one is digit or not you either should use `ContainsDigit` rule or force library to ignore whitespace which I do not recommend because of obvious reasons.
```kotlin
    val form = FormValidator().ignoreWhiteSpace(true)
```
#### Null Safety
I tried my best to make it null safe but you can always force-pass a null argument, if you try that library will slap you in the face with a `NullPointerException`.

## Download
### Gradle
**Step 1.** Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
**Step 2.** Add the dependency
```groovy
dependencies {
	implementation 'com.github.yamin8000:A_Glitch_in_the_Data:0.0.2'
}
```
### Maven
**Step 1.** Add the JitPack repository to your build file
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
**Step 2.** Add the dependency
```xml
	<dependency>
	    <groupId>com.github.yamin8000</groupId>
	    <artifactId>A_Glitch_in_the_Data</artifactId>
	    <version>Tag</version>
	</dependency>
```
## Features
 - Form Validation
 - String Validation
 - Validate Form and Strings using different rules
 - Currently supported rules: Alpha Numeric (English), Digit, Decimal, Not Empty, Length (exact, min, max), Arabic Number, Persian Number, Persian Text, Iranian Mobile Numbers, Iranian National Code, ...
 - Regex Rule with custom regex pattern
 - Custom Rule with custom logic
 - Currently only `EditText`, `TextInputEditText`, `TextInputLayout` are supported
 - Submit button subscription
## Changelog
 - **0.0.1** first build
 - **0.0.2** bug fix
## Credits
Inspired by [Dhaval2404](https://github.com/Dhaval2404) / **[android-form-validation](https://github.com/Dhaval2404/android-form-validation)**
## Contribution
Any contribution is welcome, For Persian speakers (:iran: :afghanistan:) creating issues in Persian is also allowed.
## License
> yamin8000/A_Glitch_in_the_Data is licensed under the **[GNU General
> Public License v3.0](./LICENSE)**
> 
> Permissions of this strong copyleft license are conditioned on making
> available complete source code of licensed works and modifications,
> which include larger works using a licensed work, under the same
> license. Copyright and license notices must be preserved. Contributors
> provide an express grant of patent rights.

