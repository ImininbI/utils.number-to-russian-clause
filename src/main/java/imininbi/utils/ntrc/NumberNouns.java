package imininbi.utils.ntrc;

import lombok.val;
import org.jetbrains.annotations.NotNull;

class NumberNouns {
    private static final String[] NUMBER_NOUN_STRINGS = {
            "тысяча,тысячи,тысяч,FEMININE",
            "миллион,миллиона,миллионов,MASCULINE",
            "миллиард,миллиарда,миллиардов,MASCULINE",
            "триллион,триллиона,триллионов,MASCULINE",
            "квадриллион,квадриллиона,квадриллионов,MASCULINE",
            "квинтиллион,квинтиллиона,квинтиллионов,MASCULINE",
            "секстиллион,секстиллиона,секстиллионов,MASCULINE",
            "септиллион,септиллиона,септиллионов,MASCULINE",
            "октиллион,октиллиона,октиллионов,MASCULINE",
            "нониллион,нониллиона,нониллионов,MASCULINE",
            "дециллион,дециллиона,дециллионов,MASCULINE",
            "дециллиард,дециллиарда,дециллиардов,MASCULINE",
    };

    private static final Noun[] NUMBER_NOUNS;

    static {
        val nounsAmount = NUMBER_NOUN_STRINGS.length;
        val numberNouns = new Noun[nounsAmount];

        for (int i = 0; i < nounsAmount; i++) {
            val split = NUMBER_NOUN_STRINGS[i].split(",");

            numberNouns[i] = Noun
                    .withGender(Gender.valueOf(split[3]))
                    .withOneText(split[0])
                    .withTwoToFourText(split[1])
                    .withZeroOrMoreThenFourText(split[2])
                    .build();
        }

        NUMBER_NOUNS = numberNouns;
    }

    @NotNull
    public static Noun withIndex(int index) {
        assert (index >= 0 && index < NUMBER_NOUNS.length);
        return NUMBER_NOUNS[index];
    }

    public static int maxIndex() {
        return (NUMBER_NOUNS.length - 1);
    }
}
