package dev.pp.basics.utilities.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringBuilderUtilitiesTest {

    @Test
    public void testRemoveOptionalLineBreakAtEnd() {

        StringBuilder sb = new StringBuilder ( "abc\r\n\r\n" );
        assertEquals ( "abc\r\n", StringBuilderUtils.removeOptionalLineBreakAtEnd ( sb ).toString() );

        sb = new StringBuilder ( "abc\n\n" );
        assertEquals ( "abc\n", StringBuilderUtils.removeOptionalLineBreakAtEnd ( sb ).toString() );

        sb = new StringBuilder ( "abc\r\n" );
        assertEquals ( "abc", StringBuilderUtils.removeOptionalLineBreakAtEnd ( sb ).toString() );

        sb = new StringBuilder ( "abc\n" );
        assertEquals ( "abc", StringBuilderUtils.removeOptionalLineBreakAtEnd ( sb ).toString() );

        sb = new StringBuilder ( "abc" );
        assertEquals ( "abc", StringBuilderUtils.removeOptionalLineBreakAtEnd ( sb ).toString() );
    }
}
