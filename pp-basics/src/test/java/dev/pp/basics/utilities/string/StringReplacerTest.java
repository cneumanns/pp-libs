package dev.pp.basics.utilities.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringReplacerTest {

    @Test
    public void testReplaceAll() {

        assertEquals ( "12---1212-12", StringReplacer.replaceAll ( "ab---abab-ab", "ab", "12", 4 ) );
        assertEquals ( "ab---abab-ab", StringReplacer.replaceAll ( "ab---abab-ab", "cd", "12", 0 ) );
        assertEquals ( "bar", StringReplacer.replaceAll ( "foo", "foo", "bar", 1 ) );
        assertEquals ( "bar", StringReplacer.replaceAll ( "foo", "foo", "bar", null ) );
        assertThrows ( RuntimeException.class, () -> StringReplacer.replaceAll ( "foo", "foo", "bar", 0 ) );
    }
}