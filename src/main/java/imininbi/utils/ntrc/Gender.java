package imininbi.utils.ntrc;

import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;

public enum Gender {
    NEUTER,
    FEMININE,
    MASCULINE,
    //--------
    ;

    private static final Map<Object, String> UNIT_DIGIT_WORDS;
    private static final Set<Character> GENDER_AFFECTED_UNIT_DIGITS;

    static {
        val map = new HashMap<Object, String>();
        map.put(asList(NEUTER, '1'), "одно");
        map.put(asList(NEUTER, '2'), "два");
        map.put(asList(FEMININE, '1'), "одна");
        map.put(asList(FEMININE, '2'), "две");
        map.put(asList(MASCULINE, '1'), "один");
        map.put(asList(MASCULINE, '2'), "два");
        UNIT_DIGIT_WORDS = map;
    }

    static {
        val set = new HashSet<Character>();
        set.add('1');
        set.add('2');
        GENDER_AFFECTED_UNIT_DIGITS = set;
    }

    @NotNull
    public String unitsWord(char unitDigit) {
        val word = UNIT_DIGIT_WORDS.get(asList(this, unitDigit));

        if (word != null) {
            return word;

        } else {
            throw notGenderAffected(unitDigit);
        }
    }

    @NotNull
    private RuntimeException notGenderAffected(char unitDigit) {
        val format = "Input digit '%c' is not gender affected";
        val message = String.format(format, unitDigit);
        return new IllegalArgumentException(message);
    }

    public static boolean isGenderAffected(char unitDigit) {
        return GENDER_AFFECTED_UNIT_DIGITS.contains(unitDigit);
    }
}
