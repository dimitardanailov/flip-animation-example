package filipanimation.exampleflipanimation.enums.fragments;

import filipanimation.exampleflipanimation.collections.enums.AbbreviationMap;

/**
 * Created by Dimitar Danailov on 10/14/15.
 * email: dimityr.danailov@gmail.com
 *
 * Post: http://stackoverflow.com/questions/1080904/how-can-i-lookup-a-java-enum-from-its-string-value
 */
public enum FragmentTag {
    CARD_HOLDER("CARD_HOLDER"),
    GOOGLE_MAP("GOOGLE_MAP"),
    SECOND_CART("SECOND_CART");

    private final String abbreviation;

    /**
     * Reverse-lookup map for getting a fragment tag from an abbreviation
     */
    // private static final Map<String, FragmentTag> lookup = new HashMap<String, FragmentTag>();
    private static final AbbreviationMap<String, FragmentTag> lookup = new AbbreviationMap<String, FragmentTag>();

    static {
        for (FragmentTag tag : FragmentTag.values()) {
            lookup.put(tag.getAbbreviation(), tag);
        }
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static FragmentTag get(String abbreviation) {
        return lookup.get(abbreviation);
    }

    FragmentTag(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
