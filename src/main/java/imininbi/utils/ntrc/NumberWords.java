package imininbi.utils.ntrc;

import org.jetbrains.annotations.NotNull;

class NumberWords {
    private static final String[] UNIT_WORDS = {
            "ноль",
            "один",
            "два",
            "три",
            "четыре",
            "пять",
            "шесть",
            "семь",
            "восемь",
            "девять",
            "десять",
            "одиннадцать",
            "двенадцать",
            "тринадцать",
            "четырнадцать",
            "пятнадцать",
            "шестнадцать",
            "семнадцать",
            "восемнадцать",
            "девятнадцать",
    };

    private static final String[] DOZEN_WORDS = {
            "десять",
            "двадцать",
            "тридцать",
            "сорок",
            "пятьдесят",
            "шестьдесят",
            "семьдесят",
            "восемьдесят",
            "девяносто",
    };

    private static final String[] HUNDRED_WORDS = {
            "сто",
            "двести",
            "триста",
            "четыреста",
            "пятьсот",
            "шестьсот",
            "семьсот",
            "восемьсот",
            "девятьсот",
    };

    @NotNull
    public static String forUnitsLessThenTen(char unitDigit, @NotNull Gender gender) {
        if (Gender.isGenderAffected(unitDigit)) {
            return gender.unitsWord(unitDigit);

        } else {
            return UNIT_WORDS[unitDigit - '0'];
        }
    }

    @NotNull
    public static String forUnitsGreaterThenNine(char unitDigit) {
        return UNIT_WORDS[10 + (unitDigit - '0')];
    }

    @NotNull
    public static String forDozens(char dozenDigit) {
        return DOZEN_WORDS[dozenDigit - '1'];
    }

    @NotNull
    public static String forHundreds(char hundredDigit) {
        return HUNDRED_WORDS[hundredDigit - '1'];
    }
}
