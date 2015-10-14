package filipanimation.exampleflipanimation.collections.enums;

import java.util.HashMap;

/**
 * Created by Dimitar Danailov on 10/14/15.
 * email: dimityr.danailov@gmail.com
 *
 * I get idea for implementation from http://docs.oracle.com/javase/6/docs/api/java/util/EnumMap.html
 * Class EnumMap<K extends Enum<K>,V>
 */
public class AbbreviationMap<K, V extends Enum<V>> extends HashMap<String, V> {

    // Get Class Name
    private static final String TAG = AbbreviationMap.class.getName();


}
