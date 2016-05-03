package imininbi.utils.ntrc;

import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigInteger;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class NumberToRussianClauseTest {
    private final BigInteger input;
    private final String expected;

    public NumberToRussianClauseTest(String input, String expected) {
        this.input = new BigInteger(input);
        this.expected = expected;
    }

    @Test
    public void test() throws Exception {
        val actual = new NumberToRussianClause().transform(input);
        assertThat(actual, equalTo(expected));
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> parameters() {
        return asList(new Object[][] {
                /*0*/ {"0", "ноль"},
                /*1*/ {"1", "один"},
                /*2*/ {"11", "одиннадцать"},
                /*3*/ {"21", "двадцать один"},
                /*4*/ {"20", "двадцать"},
                /*5*/ {"94", "девяносто четыре"},
                /*6*/ {"101", "сто один"},
                /*7*/ {"2123", "две тысячи сто двадцать три"},
                /*8*/ {"1002", "одна тысяча два"},
                /*9*/ {"1000", "одна тысяча"},
                /*10*/ {"01000", "одна тысяча"},
                /*11*/ {"1000000", "один миллион"},
                /*12*/ {"1001000", "один миллион одна тысяча"},
                /*13*/ {"112354687", "сто двенадцать миллионов триста пятьдесят четыре тысячи шестьсот восемьдесят семь"},
                /*14*/ {"78944965241000000005541462378", "семьдесят восемь октиллионов девятьсот сорок четыре септиллиона девятьсот шестьдесят пять секстиллионов двести сорок один квинтиллион пять миллиардов пятьсот сорок один миллион четыреста шестьдесят две тысячи триста семьдесят восемь"},
        });
    }
}