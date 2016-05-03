### Number to russian text library

Library to translate integer numbers (instances of `java.math.BigInteger`) to russian text. 

For example, number "1&nbsp;000&nbsp;230&nbsp;121" turn into "один миллиард двести тридцать тысяч сто двадцать один". Library can be used with custom nouns to produce results like "тринадцать карандашей" or "восемьдесят четыре японские иены". 

### Code examples

Basic example:
```java
BigInteger number = new BigInteger("65240");
String numberString = new NumberToRussianClause().transform(number);

assertThat(numberString, equalTo("шестьдесят пять тысяч двести сорок"));
```
Examples with custom nouns:
```java
Noun catNoun = Noun
        .withGender(Gender.FEMININE)
        .withOneText("кошка") // "одна кошка"
        .withTwoToFourText("кошки") // "четыре кошки"
        .withZeroOrMoreThenFourText("кошек") // "пять кошек"
        .build();

BigInteger number = new BigInteger("33");
String numberString = new NumberToRussianClause().transform(number, catNoun);

assertThat(numberString, equalTo("тридцать три кошки"));
```
```java
Noun UsDollarNoun = Noun
        .withGender(Gender.MASCULINE)
        .withOneText("доллар США")
        .withTwoToFourText("доллара США")
        .withZeroOrMoreThenFourText("долларов США")
        .build();

BigInteger number = new BigInteger("1");
String numberString = new NumberToRussianClause().transform(number, UsDollarNoun);

assertThat(numberString, equalTo("один доллар США"));
```
### Maven compile dependencies
- org.jetbrains:annotations:15.0

### License
MIT
