package imininbi.utils.ntrc;

import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class Noun {
    private final Gender gender;
    private final String oneText;
    private final String twoToFourText;
    private final String zeroOrMoreThenFourText;

    private Noun(Builder.Impl b) {
        this.gender = b.gender;
        this.oneText = b.oneText;
        this.twoToFourText = b.twoToFourText;
        this.zeroOrMoreThenFourText = b.zeroOrMoreThenFourText;
    }

    @NotNull
    public Gender getGender() {
        return gender;
    }

    @NotNull
    public String getOneText() {
        return oneText;
    }

    @NotNull
    @SuppressWarnings("unused")
    public String getTwoToFourText() {
        return twoToFourText;
    }

    @NotNull
    @SuppressWarnings("unused")
    public String getZeroOrMoreThenFourText() {
        return zeroOrMoreThenFourText;
    }

    @NotNull
    @SuppressWarnings("unused")
    public String getText(@NotNull BigInteger number) {
        val numberAbsString = number.abs().toString();
        val stringLength = numberAbsString.length();

        switch (stringLength) {
            case 0: {
                return zeroOrMoreThenFourText;
            }

            case 1: {
                char unitDigit = numberAbsString.charAt(0);
                return getTextByUnitDigit(unitDigit);
            }

            default: {
                char unitDigit = numberAbsString.charAt(stringLength - 1);
                char dozenDigit = numberAbsString.charAt(stringLength - 2);
                return getTextByDozenAdnUnitDigits(dozenDigit, unitDigit);
            }
        }
    }

    @NotNull String getTextByDozenAdnUnitDigits(char dozenDigit, char unitDigit) {
        if (dozenDigit == '1') {
            return zeroOrMoreThenFourText;

        } else {
            return getTextByUnitDigit(unitDigit);
        }
    }

    @NotNull String getTextByUnitDigit(char unitDigit) {
        if (unitDigit == '1') {
            return oneText;

        } else if (unitDigit >= '2' && unitDigit <= '4') {
            return twoToFourText;

        } else {
            return zeroOrMoreThenFourText;
        }
    }

    @Override
    public String toString() {
        return "['" + oneText + "' (" + gender.name().substring(0, 1) + ")]";
    }

    public static Builder.B0 builder() {
        return new Builder.Impl();
    }

    public static Builder.B1 withGender(@NotNull Gender nounGender) {
        return builder().withGender(nounGender);
    }

    public static class Builder {
        private Builder() {
        }

        public static class Impl implements B0, B1, B2, B3, B4 {
            private Gender gender;
            private String oneText;
            private String twoToFourText;
            private String zeroOrMoreThenFourText;

            private Impl() {
            }

            @Override
            public B1 withGender(@NotNull Gender gender) {
                this.gender = gender;
                return this;
            }

            @Override
            public B2 withOneText(@NotNull String oneText) {
                this.oneText = oneText;
                return this;
            }

            @Override
            public B3 withTwoToFourText(@NotNull String twoToFourText) {
                this.twoToFourText = twoToFourText;
                return this;
            }

            @Override
            public B4 withZeroOrMoreThenFourText(@NotNull String zeroOrMoreThenFourText) {
                this.zeroOrMoreThenFourText = zeroOrMoreThenFourText;
                return this;
            }

            @Override
            public Noun build() {
                return new Noun(this);
            }
        }

        public interface B0 {
            B1 withGender(@NotNull Gender gender);
        }

        public interface B1 {
            B2 withOneText(@NotNull String oneText);
        }

        public interface B2 {
            B3 withTwoToFourText(@NotNull String twoToFourText);
        }

        public interface B3 {
            B4 withZeroOrMoreThenFourText(@NotNull String zeroOrMoreThenFourText);
        }

        public interface B4 {
            Noun build();
        }
    }
}
