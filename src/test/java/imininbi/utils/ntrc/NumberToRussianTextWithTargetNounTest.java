package imininbi.utils.ntrc;

import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigInteger;

import static imininbi.utils.ntrc.Gender.*;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class NumberToRussianTextWithTargetNounTest {
    public static final Noun PENCIL = Noun
            .withGender(MASCULINE)
            .withOneText("карандаш")
            .withTwoToFourText("карандаша")
            .withZeroOrMoreThenFourText("карандашей")
            .build();

    public static final Noun CLOUD = Noun
            .withGender(NEUTER)
            .withOneText("облако")
            .withTwoToFourText("облака")
            .withZeroOrMoreThenFourText("облаков")
            .build();

    public static final Noun CAT = Noun
            .withGender(FEMININE)
            .withOneText("кошка")
            .withTwoToFourText("кошки")
            .withZeroOrMoreThenFourText("кошек")
            .build();

    private final Noun noun;
    private final BigInteger input;
    private final String expected;

    public NumberToRussianTextWithTargetNounTest(String input, Noun noun, String expected) {
        this.noun = noun;
        this.input = new BigInteger(input);
        this.expected = expected;
    }

    @Test
    public void test() throws Exception {
        val actual = new NumberToRussianText().transform(input, noun);
        assertThat(actual, equalTo(expected));
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> parameters() {
        return asList(new Object[][]{
                /*0*/ {"0", PENCIL, "ноль карандашей"},
                /*1*/ {"1", PENCIL, "один карандаш"},
                /*2*/ {"11", PENCIL, "одиннадцать карандашей"},
                /*3*/ {"21", PENCIL, "двадцать один карандаш"},
                /*4*/ {"20", PENCIL, "двадцать карандашей"},
                /*5*/ {"94", PENCIL, "девяносто четыре карандаша"},
                /*6*/ {"101", PENCIL, "сто один карандаш"},
                /*7*/ {"2123", PENCIL, "две тысячи сто двадцать три карандаша"},
                /*8*/ {"1002", PENCIL, "одна тысяча два карандаша"},
                /*9*/ {"1000", PENCIL, "одна тысяча карандашей"},
                /*10*/ {"01000", PENCIL, "одна тысяча карандашей"},
                /*11*/ {"1000000", PENCIL, "один миллион карандашей"},
                /*12*/ {"1001000", PENCIL, "один миллион одна тысяча карандашей"},
                /*13*/ {"112354687", PENCIL, "сто двенадцать миллионов триста пятьдесят четыре тысячи шестьсот восемьдесят семь карандашей"},
                /*14*/ {"78944965241000000005541462378", PENCIL, "семьдесят восемь октиллионов девятьсот сорок четыре септиллиона девятьсот шестьдесят пять секстиллионов двести сорок один квинтиллион пять миллиардов пятьсот сорок один миллион четыреста шестьдесят две тысячи триста семьдесят восемь карандашей"},

                /*16*/ {"1", CLOUD, "одно облако"},
                /*17*/ {"2", CLOUD, "два облака"},
                /*18*/ {"5", CLOUD, "пять облаков"},
                /*19*/ {"11", CLOUD, "одиннадцать облаков"},
                /*20*/ {"1000100", CLOUD, "один миллион сто облаков"},

                /*21*/ {"1", CAT, "одна кошка"},
                /*22*/ {"2", CAT, "две кошки"},
                /*23*/ {"5", CAT, "пять кошек"},
                /*24*/ {"11", CAT, "одиннадцать кошек"},
                /*25*/ {"1000100", CAT, "один миллион сто кошек"},
                /*26*/ {"1000101", CAT, "один миллион сто одна кошка"},
        });
    }
}