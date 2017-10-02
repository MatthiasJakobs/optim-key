package com.example.matthias.customkeyboardpoc;

/**
 * Created by smuecke on 02.10.17.
 */

public enum KeyMapping {

    LEVEL1 ( 'r', 'c', ' ', 't', 'n', 'l', 'i', 'o', 'e', 'h', 'a', 'u'),
    LEVEL2 ( 'v', 'y', 'd', 'w', 'k', '-', '.', 'p', 's', 'm', ',', 'f'),
    LEVEL3 ( 'ü', 'q', 'b', 'z', 'j', '?', 'ö', '!', 'g', 'ä', 'ß', 'x'),
    LEVEL4 ( '\'', '{', '(', '[', '"', '_', ':', '}', ')', ']', ';', '/');

    private final char[] chars = new char[12];
    KeyMapping(char ... chars) {
        System.arraycopy(chars, 0, this.chars, 0, chars.length);
    }

    public char get(int i) {
        return chars[i];
    }

}
