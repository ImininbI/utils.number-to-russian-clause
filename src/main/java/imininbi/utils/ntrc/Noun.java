package imininbi.utils.ntrc;

import org.jetbrains.annotations.NotNull;

// Empty structure like class with getters
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
    public String getTwoToFourText() {
        return twoToFourText;
    }

    @NotNull
    public String getZeroOrMoreThenFourText() {
        return zeroOrMoreThenFourText;
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
