package imininbi.utils.ntrc;

import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NumberToRussianText {
    private int triadsAmount;
    private int firstTriadLength;

    private char unitDigit;
    private char dozenDigit;
    private char hundredDigit;

    private String numberString;

    private List<String> resultWords = new ArrayList<String>();

    // ---- //

    @NotNull
    public String transform(@NotNull BigInteger number) {
        init(number);
        addNumberWords(Gender.MASCULINE);
        return joinStrings(resultWords, " ");
    }

    @NotNull
    public String transform(@NotNull BigInteger number, @NotNull Noun targetNoun) {
        init(number);
        addNumberWords(targetNoun.getGender());
        addTargetNounWord(targetNoun);
        return joinStrings(resultWords, " ");
    }

    private void init(@NotNull BigInteger number) {
        if (number.signum() >= 0) {
            initWithNotNegativeNumber(number);

        } else {
            initWithNotNegativeNumber(number.negate());
            resultWords.add("минус");
        }
    }

    private void initWithNotNegativeNumber(@NotNull BigInteger number) {
        val numberString = number.toString();
        val numberStringLength = numberString.length();
        val remainder = (numberStringLength % 3);

        if (remainder == 0) {
            this.triadsAmount = (numberStringLength / 3);
            this.firstTriadLength = 3;

        } else {
            this.triadsAmount = (numberStringLength / 3) + 1;
            this.firstTriadLength = remainder;
        }

        this.numberString = numberString;
    }

    private void addNumberWords(@NotNull Gender targetNounGender) {
        if (triadsAmount == 1) {
            addFirstTriadWords(targetNounGender);

        } else {
            assert (triadsAmount >= 2);

            val numberNounIndex = (triadsAmount - 2);

            if (numberNounIndex > NumberNouns.maxIndex()) {
                throw tooBigInputNumber();
            }

            Noun numberNoun = NumberNouns.forIndex(numberNounIndex);

            addFirstTriadWords(numberNoun.getGender());
            addNounWordForFirstTriad(numberNoun);

            int currentTriadRank = (triadsAmount - 3);
            int currentTriadStart = firstTriadLength;

            while (currentTriadRank >= 0) {
                numberNoun = NumberNouns.forIndex(currentTriadRank);

                setCurrentTriad(currentTriadStart);

                if (!isCurrentTriadEmpty()) {
                    addCurrentTriadWords(numberNoun.getGender());
                    addNounWordByDozenAndUnitDigits(numberNoun);
                }

                currentTriadRank -= 1;
                currentTriadStart += 3;
            }

            setCurrentTriad(currentTriadStart);
            addCurrentTriadWords(targetNounGender);
        }
    }

    private void addFirstTriadWords(@NotNull Gender nounGender) {
        switch (firstTriadLength) {
            case 3: {
                setCurrentTriad(0);
                addCurrentTriadWords(nounGender);
                break;
            }

            case 2: {
                setCurrentDozenAndUnit(0);
                addDozenAndUnitDigitsWords(nounGender);
                break;
            }

            case 1: {
                setCurrentUnit(0);
                addUnitDigitWord(nounGender);
                break;
            }

            default: {
                throw illegalFirstTriadLength();
            }
        }
    }

    private void setCurrentTriad(int triadStartIndex) {
        unitDigit = numberString.charAt(triadStartIndex + 2);
        dozenDigit = numberString.charAt(triadStartIndex + 1);
        hundredDigit = numberString.charAt(triadStartIndex);
    }

    private void addCurrentTriadWords(@NotNull Gender nounGender) {
        if (hundredDigit != '0') {
            addHundredDigitWord();
        }

        addDozenAndUnitDigitsWords(nounGender);
    }

    private void addHundredDigitWord() {
        resultWords.add(NumberWords.forHundreds(hundredDigit));
    }

    private void addDozenAndUnitDigitsWords(@NotNull Gender nounGender) {
        if (dozenDigit == '1') {
            resultWords.add(NumberWords.forUnitsGreaterThenNine(unitDigit));

        } else {
            if (dozenDigit != '0') {
                resultWords.add(NumberWords.forDozens(dozenDigit));
            }

            if (unitDigit != '0') {
                val word = NumberWords.forUnitsLessThenTen(unitDigit, nounGender);
                resultWords.add(word);
            }
        }
    }

    private void setCurrentDozenAndUnit(int startIndex) {
        unitDigit = numberString.charAt(startIndex + 1);
        dozenDigit = numberString.charAt(startIndex);
    }

    private void setCurrentUnit(int startIndex) {
        unitDigit = numberString.charAt(startIndex);
    }

    private boolean addUnitDigitWord(@NotNull Gender nounGender) {
        return resultWords.add(NumberWords.forUnitsLessThenTen(unitDigit, nounGender));
    }

    @NotNull
    private RuntimeException tooBigInputNumber() {
        val format = ""
                + "Input number is too big. "
                + "Max supported number rank is '%s'";

        val maxNumberIndex = NumberNouns.maxIndex();
        val maxNumberNoun = NumberNouns.forIndex(maxNumberIndex);
        val message = String.format(format, maxNumberNoun.getOneText());

        return new IllegalArgumentException(message);
    }

    private void addTargetNounWord(@NotNull Noun targetNoun) {
        if (triadsAmount == 1) {
            addNounWordForFirstTriad(targetNoun);

        } else {
            addNounWordByDozenAndUnitDigits(targetNoun);
        }
    }

    private void addNounWordForFirstTriad(@NotNull Noun targetNoun) {
        switch (firstTriadLength) {
            case 3:
            case 2: {
                addNounWordByDozenAndUnitDigits(targetNoun);
                break;
            }

            case 1: {
                addNounWordByUnitDigit(targetNoun);
                break;
            }

            default: {
                throw illegalFirstTriadLength();
            }
        }
    }

    private boolean addNounWordByDozenAndUnitDigits(@NotNull Noun noun) {
        return resultWords.add(nounWordByDozenAndUnitDigits(noun));
    }

    @NotNull
    private String nounWordByDozenAndUnitDigits(@NotNull Noun noun) {
        if (dozenDigit == '1') {
            return noun.getZeroOrMoreThenFourText();

        } else {
            return nounWordByUnitDigit(noun);
        }
    }

    private boolean addNounWordByUnitDigit(@NotNull Noun noun) {
        return resultWords.add(nounWordByUnitDigit(noun));
    }

    @NotNull
    private String nounWordByUnitDigit(@NotNull Noun noun) {
        if (unitDigit == '1') {
            return noun.getOneText();

        } else if (unitDigit >= '2' && unitDigit <= '4') {
            return noun.getTwoToFourText();

        } else {
            return noun.getZeroOrMoreThenFourText();
        }
    }

    @NotNull
    private RuntimeException illegalFirstTriadLength() {
        String format = "First triad length is '%s' this should not happened";
        String errorMessage = String.format(format, firstTriadLength);
        return new IllegalStateException(errorMessage);
    }

    private boolean isCurrentTriadEmpty() {
        return (unitDigit == '0' && dozenDigit == '0' && hundredDigit == '0');
    }

    @NotNull
    private String joinStrings(
            @NotNull Iterable<String> strings,
            @NotNull String delimiter
    ) {
        Iterator<String> itr = strings.iterator();

        if (!itr.hasNext()) {
            return "";

        } else {
            String firstString = itr.next();
            StringBuilder result = new StringBuilder(firstString);

            while (itr.hasNext()) {
                String nextString = itr.next();

                result.append(delimiter);
                result.append(nextString);
            }

            return result.toString();
        }
    }
}
