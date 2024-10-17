package dev.pp.text.reader;

import dev.pp.basics.utilities.character.CharChecks;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CharReaderTest {

    private static CharReader getDefaultReader ( String s ) {

        // return CharReaderImpl.createForString ( s );
        try {
            return CharReaderImpl.createAndAdvance ( new StringReader ( s ), null, null, null );
        } catch ( IOException e ) {
            // should never happen
            throw new RuntimeException ( e );
        }
    }


    // iteration

    @Test
    public void testIteration() throws IOException {
        testIteration_ ( CharReaderTest::getDefaultReader );
    }

    public static void testIteration_ ( Function<String, CharReader> readerGetter ) throws IOException {

        CharReader r = readerGetter.apply ( "abc" );

        assertTrue ( r.hasChar () );
        assertEquals ( 'a', r.currentChar () );
        assertTrue ( r.isAtChar ( 'a' ) );
        assertEquals ( 1, r.currentLineNumber () );
        assertEquals ( 1, r.currentColumnNumber () );

        // assertEquals ( 'a', r.advance () );
        r.advance();
        assertTrue ( r.hasChar () );
        assertTrue ( r.isAtChar ( 'b' ) );
        assertEquals ( 1, r.currentLineNumber () );
        assertEquals ( 2, r.currentColumnNumber () );

        // assertEquals ( 'b', r.advance () );
        r.advance();
        assertTrue ( r.hasChar () );
        assertTrue ( r.isAtChar ( 'c' ) );
        assertEquals ( 1, r.currentLineNumber () );
        assertEquals ( 3, r.currentColumnNumber () );

        // assertEquals ( 'c', r.advance () );
        r.advance();
        assertFalse ( r.hasChar () );
        // assertTrue ( r.isNext ( 0 );
        assertEquals ( 1, r.currentLineNumber () );
        assertEquals ( 4, r.currentColumnNumber () );

        // must be commented if assertions are disabled
        // assertThrows ( NoSuchElementException.class, r::advance );


        r = readerGetter.apply ( "abc" );
        StringBuilder sb = new StringBuilder();
        while ( r.hasChar () ) {
            sb.append ( r.currentChar() );
            r.advance();
        }
        assertEquals ( "abc", sb.toString() );


        r = readerGetter.apply ( "" );
        assertFalse ( r.hasChar () );
        assertEquals ( 1, r.currentLineNumber () );
        assertEquals ( 1, r.currentColumnNumber () );

        // must be commented if assertions are disabled
        // assertThrows ( NoSuchElementException.class, r::advance );
        // assertThrows ( Throwable.class, r::advance );


        r = readerGetter.apply ( "" );
        sb = new StringBuilder();
        while ( r.hasChar () ) {
            sb.append ( r.currentChar() );
            r.advance();
        }
        assertEquals ( "", sb.toString() );
    }


    // location

    @Test
    public void testLocation() throws IOException {
        testLocation_ ( CharReaderTest::getDefaultReader );
    }

    public static void testLocation_ ( Function<String, CharReader> readerGetter ) throws IOException {

        CharReader r = readerGetter.apply ( "12\n45\r\n89\n\nAB" );

        assertEquals ( '1', r.currentChar() );
        assertEquals ( 1, r.currentLineNumber() );
        assertEquals ( 1, r.currentColumnNumber() );
        assertEquals ( 1, r.currentLocation().getLineNumber() );
        assertEquals ( 1, r.currentLocation().getColumnNumber() );

        r.advance();
        assertEquals ( '2', r.currentChar() );
        assertEquals ( 1, r.currentLineNumber() );
        assertEquals ( 2, r.currentColumnNumber() );

        r.advance();
        assertEquals ( '\n', r.currentChar() );
        assertEquals ( '\n', r.currentChar() );
        assertEquals ( 1, r.currentLineNumber() );
        assertEquals ( 3, r.currentColumnNumber() );

        r.advance();
        assertEquals ( '4', r.currentChar() );
        assertEquals ( 2, r.currentLineNumber() );
        assertEquals ( 1, r.currentColumnNumber() );

        r.advance();
        assertEquals ( '5', r.currentChar() );
        assertEquals ( 2, r.currentLineNumber() );
        assertEquals ( 2, r.currentColumnNumber() );

        r.advance();
        assertEquals ( '\r', r.currentChar() );
        assertEquals ( 2, r.currentLineNumber() );
        assertEquals ( 3, r.currentColumnNumber() );

        r.advance();
        assertEquals ( '\n', r.currentChar() );
        assertEquals ( 2, r.currentLineNumber() );
        assertEquals ( 4, r.currentColumnNumber() );

        r.advance();
        assertEquals ( '8', r.currentChar() );
        assertEquals ( 3, r.currentLineNumber() );
        assertEquals ( 1, r.currentColumnNumber() );

        r.advance();
        assertEquals ( '9', r.currentChar() );
        assertEquals ( 3, r.currentLineNumber() );
        assertEquals ( 2, r.currentColumnNumber() );

        r.advance();
        assertEquals ( '\n', r.currentChar() );
        assertEquals ( 3, r.currentLineNumber() );
        assertEquals ( 3, r.currentColumnNumber() );

        r.advance();
        assertEquals ( '\n', r.currentChar() );
        assertEquals ( 4, r.currentLineNumber() );
        assertEquals ( 1, r.currentColumnNumber() );

        r.advance();
        assertEquals ( 'A', r.currentChar() );
        assertEquals ( 5, r.currentLineNumber() );
        assertEquals ( 1, r.currentColumnNumber() );

        r.advance();
        assertEquals ( 'B', r.currentChar() );
        assertEquals ( 5, r.currentLineNumber() );
        assertEquals ( 2, r.currentColumnNumber() );

        r.advance();
        assertFalse ( r.hasChar() );
        assertEquals ( 5, r.currentLineNumber() );
        assertEquals ( 3, r.currentColumnNumber() );
        assertEquals ( 5, r.currentLocation().getLineNumber() );
        assertEquals ( 3, r.currentLocation().getColumnNumber() );


        r = readerGetter.apply ( "\n2" );

        assertTrue ( r.hasChar() );
        assertEquals ( '\n', r.currentChar() );
        assertEquals ( '2', r.peekNextChar () );
        assertEquals ( 1, r.currentLineNumber() );
        assertEquals ( 1, r.currentColumnNumber() );
        assertEquals ( 1, r.currentLocation().getLineNumber() );
        assertEquals ( 1, r.currentLocation().getColumnNumber() );

        r.advance();
        assertTrue ( r.hasChar() );
        assertEquals ( '2', r.currentChar() );
        assertEquals ( 2, r.currentLineNumber() );
        assertEquals ( 1, r.currentColumnNumber() );
        assertEquals ( 2, r.currentLocation().getLineNumber() );
        assertEquals ( 1, r.currentLocation().getColumnNumber() );

        r.advance();
        assertFalse ( r.hasChar() );
        assertEquals ( 2, r.currentLineNumber() );
        assertEquals ( 2, r.currentColumnNumber() );

        r = readerGetter.apply ( "" );

        assertFalse ( r.hasChar() );
        assertEquals ( 1, r.currentLineNumber() );
        assertEquals ( 1, r.currentColumnNumber() );
        assertEquals ( 1, r.currentLocation().getLineNumber() );
        assertEquals ( 1, r.currentLocation().getColumnNumber() );

        r = readerGetter.apply ( "1" );

        assertTrue ( r.hasChar() );
        r.advance();
        assertFalse ( r.hasChar() );
        assertEquals ( 1, r.currentLineNumber() );
        assertEquals ( 2, r.currentColumnNumber() );
        assertEquals ( 1, r.currentLocation().getLineNumber() );
        assertEquals ( 2, r.currentLocation().getColumnNumber() );
    }


    // read

    @Test
    public void testReadFunctions() throws IOException {
        testReadFunctions_ ( CharReaderTest::getDefaultReader );
    }

    public static void testReadFunctions_ ( Function<String, CharReader> readerGetter ) throws IOException {

        // readWhileNotAtCharOrEnd

        CharReader r = readerGetter.apply ( "12345" );

        assertNull ( r.readWhileNotAtCharOrEnd ( '1' ) );
        assertEquals ( "12", r.readWhileNotAtCharOrEnd ( '3' ) );
        assertNull ( r.readWhileNotAtCharOrEnd ( '3' ) );
        assertEquals ( "3", r.readWhileNotAtCharOrEnd ( '4' ) );
        assertEquals ( "45", r.readWhileNotAtCharOrEnd ( '6' ) );
        assertNull ( r.readWhileNotAtCharOrEnd ( '3' ) );

        r = readerGetter.apply ( "12345" );

        // readWhileNotAtStringOrEnd

        assertNull ( r.readWhileNotAtStringOrEnd ( "1" ) );
        assertNull ( r.readWhileNotAtStringOrEnd ( "123" ) );
        assertNull ( r.readWhileNotAtStringOrEnd ( "12345" ) );
        assertEquals ( "12", r.readWhileNotAtStringOrEnd ( "34" ) );
        assertNull ( r.readWhileNotAtStringOrEnd ( "3" ) );
        assertNull ( r.readWhileNotAtStringOrEnd ( "34" ) );
        assertNull ( r.readWhileNotAtStringOrEnd ( "345" ) );
        assertEquals ( "3", r.readWhileNotAtStringOrEnd ( "4" ) );
        assertEquals ( "45", r.readWhileNotAtStringOrEnd ( "6" ) );
        assertNull ( r.readWhileNotAtStringOrEnd ( "3" ) );

        r = readerGetter.apply ( "12[45" );
        assertEquals ( "12[45", r.readWhileNotAtStringOrEnd ( "[[" ) );

        // readWhileCharsMatch
        r = readerGetter.apply ( "123456" );
        assertNull ( r.readWhileCharsMatch ( "abc" ) );
        assertEquals ( "1", r.readWhileCharsMatch ( "1" ) );
        assertEquals ( "23", r.readWhileCharsMatch ( "23" ) );
        assertEquals ( "45", r.readWhileCharsMatch ( "45ab" ) );
        assertEquals ( "6", r.readWhileCharsMatch ( "678" ) );

        // readSpaceOrTabOrLineBreak
        r = readerGetter.apply ( "1 \t\n\r\n2" );
        assertNull ( r.readSpaceOrTabOrLineBreak() );
        r.advance();
        assertEquals ( " ", r.readSpaceOrTabOrLineBreak() );
        assertEquals ( "\t", r.readSpaceOrTabOrLineBreak() );
        assertEquals ( "\n", r.readSpaceOrTabOrLineBreak() );
        assertEquals ( "\r\n", r.readSpaceOrTabOrLineBreak() );
        assertNull ( r.readSpaceOrTabOrLineBreak() );
    }

    // isAt

    @Test
    public void testisAtFunctions() throws IOException {
        testisAtFunctions_ ( CharReaderTest::getDefaultReader );
    }

    public static void testisAtFunctions_ ( Function<String, CharReader> readerGetter ) throws IOException {

        CharReader r = readerGetter.apply ( "1234" );

        assertTrue ( r.isAtChar ( '1' ) );
        assertFalse ( r.isAtChar ( '2' ) );

        // assertTrue ( r.isNextChar ( '2' ) );
        // assertFalse ( r.isNextChar ( '1' ) );

        assertTrue ( r.isAtString ( "1" ) );
        assertTrue ( r.isAtString ( "12" ) );
        assertTrue ( r.isAtString ( "123" ) );
        assertTrue ( r.isAtString ( "1234" ) );

        assertFalse ( r.isAtString ( "2" ) );
        assertFalse ( r.isAtString ( "22" ) );
        assertFalse ( r.isAtString ( "13" ) );
        assertFalse ( r.isAtString ( "1235" ) );
        assertFalse ( r.isAtString ( "12345" ) );
        assertFalse ( r.isAtString ( "123456" ) );

        r.advance();
        r.advance();
        r.advance();

        assertTrue ( r.isAtChar ( '4' ) );
        assertFalse ( r.isAtChar ( '2' ) );

        // assertTrue ( r.isNextChar ( (char) 0 ) );
        // assertFalse ( r.isNextChar ( '4' ) );

        assertTrue ( r.isAtString ( "4" ) );
        assertFalse ( r.isAtString ( "2" ) );
        assertFalse ( r.isAtString ( "44" ) );


        r = readerGetter.apply ( "1 \t\r\n" );

        assertFalse ( r.isAt ( CharChecks::isSpaceOrTab ) );
        assertFalse ( r.isAt ( CharChecks::isLineBreak ) );
        assertFalse ( r.isAt ( CharChecks::isSpaceOrTabOrLineBreak ) );

        r.advance();
        assertTrue ( r.isAt ( CharChecks::isSpaceOrTab ) );
        assertFalse ( r.isAt ( CharChecks::isLineBreak ) );
        assertTrue ( r.isAt ( CharChecks::isSpaceOrTabOrLineBreak ) );

        r.advance();
        assertTrue ( r.isAt ( CharChecks::isSpaceOrTab ) );
        assertFalse ( r.isAt ( CharChecks::isLineBreak ) );
        assertTrue ( r.isAt ( CharChecks::isSpaceOrTabOrLineBreak ) );

        r.advance();
        assertFalse ( r.isAt ( CharChecks::isSpaceOrTab ) );
        assertTrue ( r.isAt ( CharChecks::isLineBreak ) );
        assertTrue ( r.isAt ( CharChecks::isSpaceOrTabOrLineBreak ) );

        r.advance();
        assertFalse ( r.isAt ( CharChecks::isSpaceOrTab ) );
        assertTrue ( r.isAt ( CharChecks::isLineBreak ) );
        assertTrue ( r.isAt ( CharChecks::isSpaceOrTabOrLineBreak ) );
    }


    // skip

    @Test
    public void testSkipFunctions() throws IOException {
        testSkipFunctions_ ( CharReaderTest::getDefaultReader );
    }

    public static void testSkipFunctions_ ( Function<String, CharReader> readerGetter ) throws IOException {

        final CharReader r = readerGetter.apply ( "12\n\r\n \t\n\r\n3\r\n\n\n\r\n4 \t\r\n \n\r\n \t56789123456789" );

        assertEquals ( '1', r.currentChar() );
        assertFalse ( r.skipChar ( '2' ) );
        assertFalse ( r.skipLineBreak () );
        assertFalse ( r.skipLineBreaks () );
        assertFalse ( r.skipSpaceOrTabOrLineBreak () );
        assertFalse ( r.skipSpacesAndTabsAndLineBreaks () );
        assertFalse ( r.skipString ( "2" ) );

        assertTrue ( r.skipChar ( '1' ) );
        assertTrue ( r.skipString ( "2" ) );
        assertEquals ( '\n', r.currentChar() );

        assertTrue ( r.skipLineBreak () );
        assertTrue ( r.skipLineBreak () );
        assertEquals ( ' ', r.currentChar() );
        assertTrue ( r.skipSpaceOrTabOrLineBreak () );
        assertTrue ( r.skipSpaceOrTabOrLineBreak () );
        assertTrue ( r.skipSpaceOrTabOrLineBreak () );
        assertTrue ( r.skipSpaceOrTabOrLineBreak () );
        assertEquals ( '3', r.currentChar() );
        r.advance();

        assertTrue ( r.skipLineBreaks () );
        assertEquals ( '4', r.currentChar() );
        r.advance();

        assertTrue ( r.skipSpacesAndTabsAndLineBreaks () );
        assertEquals ( '5', r.currentChar() );
        r.advance();

        // assertTrue ( r.skipSpacesAndTabsAndLineBreaks() );
        // assertEquals ( '6', r.currentChar() );

        assertTrue ( r.skipString ( "6789" ) );
        assertEquals ( '1', r.currentChar() );

        r.skipNChars ( 0 );
        assertEquals ( '1', r.currentChar() );

        r.skipNChars ( 1 );
        assertEquals ( '2', r.currentChar() );

        r.skipNChars ( 3 );
        assertEquals ( '5', r.currentChar() );

        assertThrows ( IllegalArgumentException.class, () -> r.skipNChars ( -1 ) );

        r.skipNChars ( 4 );
        assertEquals ( '9', r.currentChar() );
        r.advance();
        assertFalse ( r.hasChar() );
        assertThrows ( IllegalArgumentException.class, () -> r.skipNChars ( 1 ) );

        CharReader r2 = readerGetter.apply ( " " );
        r2.advance();
        assertFalse ( r2.hasChar() );
        assertFalse ( r2.skipChar ( ' ' ) );
        assertFalse ( r2.skipSpaceOrTabOrLineBreak () );
        assertFalse ( r2.skipSpacesAndTabsAndLineBreaks () );

        CharReader r3 = readerGetter.apply ( "123\n456\r\n789\n0" );
        r3.advance();
        assertTrue ( r3.skipUntilEndOfLine ( false ) );
        assertFalse ( r3.skipUntilEndOfLine ( false ) );
        assertTrue ( r3.skipUntilEndOfLine ( true ) );
        assertEquals ( '4', r3.currentChar() );
        assertTrue ( r3.skipUntilEndOfLine ( true ) );
        assertEquals ( '7', r3.currentChar() );
        assertTrue ( r3.skipUntilEndOfLine ( true ) );
        assertEquals ( '0', r3.currentChar() );

        // skipAllWhileCharsMatch
        CharReader r4 = readerGetter.apply ( "123456789" );
        assertFalse ( r4.skipAllWhileCharsMatch ( "abc" ) );
        assertTrue ( r4.skipAllWhileCharsMatch ( "1" ) );
        assertTrue ( r4.skipAllWhileCharsMatch ( "23" ) );
        assertFalse ( r4.skipAllWhileCharsMatch ( "45ab" ) );
        assertTrue ( r4.skipAllWhileCharsMatch ( "6" ) );
        assertFalse ( r4.skipAllWhileCharsMatch ( "6" ) );
        assertTrue ( r4.skipAllWhileCharsMatch ( "789" ) );
        assertFalse ( r4.skipAllWhileCharsMatch ( "9" ) );
    }

    // read-ahead

    @Test
    public void testMark() throws IOException {
        testMark_( CharReaderTest::getDefaultReader );
    }

    public static void testMark_ ( Function<String, CharReader> readerGetter ) throws IOException {

        CharReader r = readerGetter.apply ( "123\n45" );

        assertEquals ( '1', r.currentChar() );
        assertEquals ( 1, r.currentLineNumber() );
        assertEquals ( 1, r.currentColumnNumber() );

        r.setMark ( 1 );
        r.advance();
        assertEquals ( '2', r.currentChar() );
        assertEquals ( 1, r.currentLineNumber() );
        assertEquals ( 2, r.currentColumnNumber() );
        r.goBackToMark ();
        assertEquals ( '1', r.currentChar() );
        assertEquals ( 1, r.currentLineNumber() );
        assertEquals ( 1, r.currentColumnNumber() );

        r.setMark ( 10 );
        r.advance();
        r.advance();
        r.advance();
        r.advance();
        r.advance();
        assertEquals ( '5', r.currentChar() );
        assertEquals ( 2, r.currentLineNumber() );
        assertEquals ( 2, r.currentColumnNumber() );
        r.goBackToMark ();
        assertEquals ( '1', r.currentChar() );
        assertEquals ( 1, r.currentLineNumber() );
        assertEquals ( 1, r.currentColumnNumber() );

        // setMark cannot be called twice in a row
        r.setMark ( 1 );
        assertThrows ( RuntimeException.class, () -> r.setMark ( 1 ) );
        r.removeMark();

        r.setMark ( 1 );
        r.removeMark();
        r.setMark ( 1 );
        r.removeMark();
        assertThrows ( RuntimeException.class, r::removeMark );
        assertThrows ( RuntimeException.class, r::goBackToMark );
    }

/*
    @Test
    public void testPeekCurrentString() throws IOException {
        testPeekCurrentString_( CharReaderTest::getDefaultReader );
    }

    public static void testPeekCurrentString_ ( Function<String, CharReader> readerGetter ) throws IOException {

        CharReader r = readerGetter.apply ( "1234" );

        r.advance();

        assertEquals ( "1", r.peekCurrentString( 1 ) );
        assertEquals ( "12", r.peekCurrentString( 2 ) );
        assertEquals ( "1234", r.peekCurrentString( 4 ) );
        assertEquals ( "1234", r.peekCurrentString( 5 ) );
        assertEquals ( "1234", r.peekCurrentString( 500 ) );
        assertEquals ( '1', r.currentChar() );

        r.advance();
        assertEquals ( "2", r.peekCurrentString( 1 ) );
        assertEquals ( "23", r.peekCurrentString( 2 ) );
        assertEquals ( "234", r.peekCurrentString( 3 ) );
        assertEquals ( "234", r.peekCurrentString( 4 ) );
        assertEquals ( '2', r.currentChar() );

        r.advance();
        r.advance();
        assertEquals ( "4", r.peekCurrentString( 1 ) );
        assertEquals ( "4", r.peekCurrentString( 2 ) );
        assertEquals ( "4", r.peekCurrentString( 3 ) );
        assertEquals ( '4', r.currentChar() );
    }
*/

    @Test
    public void testPeekCharAfter() throws IOException {
        testPeekCharAfter_ ( CharReaderTest::getDefaultReader );
    }

    public static void testPeekCharAfter_ ( Function<String, CharReader> readerGetter ) throws IOException {

        CharReader r = readerGetter.apply ( "1 2  3   4 " );

        assertEquals ( '1', r.currentChar() );
        assertNull ( r.peekCharAfterRequired ( CharChecks::isSpaceOrTabOrLineBreak, 10) );

        r.advance();
        assertEquals ( ' ', r.currentChar() );
        assertEquals ( '2', r.peekCharAfterRequired ( CharChecks::isSpaceOrTabOrLineBreak,10) );

        r.advance();
        assertEquals ( '2', r.currentChar() );
        assertNull ( r.peekCharAfterRequired ( CharChecks::isSpaceOrTabOrLineBreak, 10) );

        r.advance();
        assertEquals ( ' ', r.currentChar() );
        assertEquals ( '3', r.peekCharAfterRequired ( CharChecks::isSpaceOrTabOrLineBreak,10) );

        r.advance();
        r.advance();
        assertEquals ( '3', r.currentChar() );
        r.advance();
        assertEquals ( '4', r.peekCharAfterRequired ( CharChecks::isSpaceOrTabOrLineBreak,10) );

        r.advance();
        r.advance();
        r.advance();
        assertEquals ( '4', r.currentChar() );
        r.advance();
        assertEquals ( ' ', r.currentChar() );
        assertNull ( r.peekCharAfterRequired ( CharChecks::isSpaceOrTabOrLineBreak, 10) );
    }

    @Test
    public void testPeekNextMaxNChars() throws IOException {
        testPeekNextMaxNChars_ ( CharReaderTest::getDefaultReader );
    }

    public static void testPeekNextMaxNChars_ ( Function<String, CharReader> readerGetter ) throws IOException {

        CharReader r = readerGetter.apply ( "1234" );

        assertTrue ( r.isAtChar ( '1' ) );
        assertEquals ( '2', r.peekNextChar() );
        assertEquals ( "2", r.peekNextMaxNChars ( 1 ) );
        assertEquals ( "23", r.peekNextMaxNChars ( 2 ) );
        assertEquals ( "234", r.peekNextMaxNChars ( 3 ) );
        assertEquals ( "234", r.peekNextMaxNChars ( 4 ) );
        assertEquals ( "234", r.peekNextMaxNChars ( 5 ) );

        r.advance();
        r.advance();
        r.advance();
        assertTrue ( r.isAtChar ( '4' ) );
        assertNull ( r.peekNextChar() );
        assertNull ( r.peekNextMaxNChars ( 1 ) );
        assertNull ( r.peekNextMaxNChars ( 2 ) );
    }
}
