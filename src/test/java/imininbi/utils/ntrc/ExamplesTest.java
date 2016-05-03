package imininbi.utils.ntrc;

import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ExamplesTest {
    @Test
    public void basic() throws Exception {
        BigInteger number = new BigInteger("65240");
        String numberString = new NumberToRussianText().transform(number);
        assertThat(numberString, equalTo("шестьдесят пять тысяч двести сорок"));
    }

    @Test
    public void withCustomNoun1() throws Exception {
        Noun catNoun = Noun
                .withGender(Gender.FEMININE)
                .withOneText("кошка") // "одна кошка"
                .withTwoToFourText("кошки") // "четыре кошки"
                .withZeroOrMoreThenFourText("кошек") // "пять кошек"
                .build();

        BigInteger number = new BigInteger("33");
        String numberString = new NumberToRussianText().transform(number, catNoun);

        assertThat(numberString, equalTo("тридцать три кошки"));
    }

    @Test
    public void withCustomNoun2() throws Exception {
        Noun UsDollarNoun = Noun
                .withGender(Gender.MASCULINE)
                .withOneText("доллар США")
                .withTwoToFourText("доллара США")
                .withZeroOrMoreThenFourText("долларов США")
                .build();

        BigInteger number = new BigInteger("1");
        String numberString = new NumberToRussianText().transform(number, UsDollarNoun);

        assertThat(numberString, equalTo("один доллар США"));
    }

}
