package dev.pp.text.reader.stack;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.character.CharChecks;
import dev.pp.text.reader.CharReaderTest;
import dev.pp.text.resource.String_TextResource;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

public class CharReaderWithInsertsTest {

    private static CharReaderWithInsertsImpl createReader ( @NotNull String string ) {

        try {
            return CharReaderWithInsertsImpl.createAndAdvance ( new StringReader ( string ), new String_TextResource ( string ), null, null );
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }


    @Test
    public void testStack() throws IOException {
/*
        DefaultStackedCharReader r = new DefaultStackedCharReader ( "abc" );

        r.stateToOSOut ( "start" );
        assertTrue ( r.hasChar () );
        assertEquals ( 'a', r.currentChar () );
        assertTrue ( r.isNext ( 'a' ) );
        assertEquals ( 1, r.nextLineNumber() );
        assertEquals ( 1, r.nextColumnNumber() );

        // assertEquals ( 'a', r.advance () );
        r.advance();
        r.stateToOSOut ( "after advance" );
        assertTrue ( r.hasChar () );
        assertEquals ( 'b', r.currentChar () ); // c
        assertTrue ( r.isNext ( 'b' ) );
        assertEquals ( 1, r.nextLineNumber() );
        assertEquals ( 2, r.nextColumnNumber() );
*/
/*
        DefaultStackedCharReader i = new DefaultStackedCharReader ( "" );
        assertTrue ( i.isEof() );
        assertThrows ( NoSuchElementException.class, i::read );

        i = new DefaultStackedCharReader ( "1" );
        assertFalse ( i.isEof() );
        i.next();
        assertTrue ( i.isEof() );
        assertThrows ( NoSuchElementException.class, i::read );

        i = new DefaultStackedCharReader ( "abc" );

        assertFalse ( i.isEof() );
        assertEquals ( 0, i.previousChar() );
        assertEquals ( 'a', i.currentChar() );
        assertEquals ( 1, i.currentLineNumber() );
        assertEquals ( 1, i.currentColumnNumber() );

        assertEquals ( 'a', i.next() );
        assertFalse ( i.isEof() );
        assertEquals ( 'a', i.previousChar() );
        assertEquals ( 'b', i.currentChar() );
        assertEquals ( 1, i.currentLineNumber() );
        assertEquals ( 2, i.currentColumnNumber() );

        assertEquals ( 'b', i.next() );
        assertFalse ( i.isEof() );
        assertEquals ( 'b', i.previousChar() );
        assertEquals ( 'c', i.currentChar() );
        assertEquals ( 1, i.currentLineNumber() );
        assertEquals ( 3, i.currentColumnNumber() );

        assertEquals ( 'c', i.next() );
        assertTrue ( i.isEof() );
        assertEquals ( 'c', i.previousChar() );
        assertEquals ( 0, i.currentChar() );
        assertEquals ( 1, i.currentLineNumber() );
        assertEquals ( 4, i.currentColumnNumber() );

        assertThrows ( NoSuchElementException.class, i::read );
*/
        // test 1

        CharReaderWithInsertsImpl i = createReader ( "12" );
        i.insert ( "34" );
        i.insert ( "56" );

        i.stateToOSOut ( "start" );
        assertTrue ( i.hasChar () );
        /*
        assertEquals ( '5', i.advance () );
        assertEquals ( '6', i.advance () );
        assertEquals ( '3', i.advance () );
        assertEquals ( '4', i.advance () );
        assertEquals ( '1', i.advance () );
        assertEquals ( '2', i.advance () );
        */
        assertEquals ( '5', i.currentChar() );
        i.advance();
        i.stateToOSOut ( "after first advance" );
        assertEquals ( '6', i.currentChar() );



        i.advance();
        i.stateToOSOut ( "after second advance" );
        assertEquals ( '3', i.currentChar() );
        i.advance();
        assertEquals ( '4', i.currentChar() );
        i.advance();
        assertEquals ( '1', i.currentChar() );
        i.advance();
        assertEquals ( '2', i.currentChar() );
        i.advance();
        assertFalse ( i.hasChar () );

        // must be commented if assertions are disabled
        // assertThrows ( NoSuchElementException.class, i::advance );

        // test 2
        i = createReader ( "1" );
        i.insert ( "2" );
        i.insert ( "" );
        i.insert ( "3" );

        assertEquals ( '3', i.currentChar() );
        i.advance ();
        assertEquals ( '2', i.currentChar () );
        i.advance ();
        assertEquals ( '1', i.currentChar () );
        i.advance ();
        assertFalse ( i.hasChar () );

        // must be commented if assertions are disabled
        // assertThrows ( NoSuchElementException.class, i::advance );

        // test 3

        i = createReader ( "123" );
        assertEquals ( '1', i.currentChar() );
        i.advance();
        i.advance();
        assertEquals ( '3', i.currentChar() );
        i.insert ( "ab" );
        assertEquals ( 'a', i.currentChar() );
        i.advance();
        // System.out.println ( i.currentLocation() );
        assertEquals ( 'b', i.currentChar() );
        i.advance();
        assertEquals ( '3', i.currentChar() );
        i.advance();
        assertFalse ( i.hasChar () );

        // test 4

        i = createReader ( "  \r\n" );
        i.skipWhile ( CharChecks::isSpaceOrTabOrLineBreak );
        assertFalse ( i.hasChar () );

        // test 5

        i = createReader ( "123" );
        assertEquals ( '1', i.currentChar() );
        i.advance();
        assertEquals ( '2', i.currentChar() );
        i.insert ( "!" );
        assertEquals ( '!', i.currentChar() );
        i.advance();
        assertEquals ( '2', i.currentChar() );
        i.insert ( "!" );
        assertEquals ( '!', i.currentChar() );
        i.advance();
        assertEquals ( '2', i.currentChar() );
        i.advance();
        assertEquals ( '3', i.currentChar() );
        i.advance();
        assertFalse ( i.hasChar () );

        // test 6
        i = createReader ( "ac" );
        i.advance();
        assertEquals ( 'c', i.currentChar() );
        i.insert ( "b" );
        assertEquals ( 'b', i.currentChar() );
        assertTrue ( i.skipChar ( 'b' ) );
        assertEquals ( 'c', i.currentChar() );

        // test 7
/*
        i = new DefaultStackedCharReader ( "1!2!345" );
        StringBuilder sb = new StringBuilder();
        sb.append ( i.currentChar () );
        assertEquals ( "1", sb.toString() );
        i.advance();

        assertEquals ( '!', i.advance () );
        i.push ( "@@@" );

        i.appendCurrentChar ( sb );

        sb.append ( i.advance () );
        sb.append ( i.advance () );
        sb.append ( i.advance () );
        sb.append ( i.advance () );
        assertEquals ( "1@@@2", sb.toString() );

        assertEquals ( '!', i.advance () );
        i.push ( "@@@" );

        char c = i.advance ();
        assertEquals ( '@', c );
        sb.append ( c );
        sb.append ( i.advance () );
        sb.append ( i.advance () );
        assertEquals ( "1@@@2@@@", sb.toString() );
        assertTrue ( i.hasChar () );

        c = i.advance ();
        assertTrue ( i.hasChar () );
        assertEquals ( '3', c );
        sb.append ( c );
        sb.append ( i.advance () );
        sb.append ( i.advance () );
        assertFalse ( i.hasChar () );
        assertEquals ( "1@@@2@@@345", sb.toString() );
*/
    }


    // execute tests defined in CharReaderTest

    private static CharReaderWithInserts getDefaultReader ( String s ) {

        return createReader ( s );
    }

    @Test
    public void testIteration() throws IOException {
        CharReaderTest.testIteration_ ( CharReaderWithInsertsTest::getDefaultReader );
    }

    @Test
    public void testLocation() throws IOException {
        CharReaderTest.testLocation_ ( CharReaderWithInsertsTest::getDefaultReader );
    }

    @Test
    public void testReadFunctions() throws IOException {
        CharReaderTest.testReadFunctions_ ( CharReaderWithInsertsTest::getDefaultReader );
    }

    @Test
    public void testisAtFunctions() throws IOException {
        CharReaderTest.testisAtFunctions_ ( CharReaderWithInsertsTest::getDefaultReader );
    }

    @Test
    public void testSkipFunctions() throws IOException {
        CharReaderTest.testSkipFunctions_ ( CharReaderWithInsertsTest::getDefaultReader );
    }

    @Test
    public void testMark() throws IOException {
        CharReaderTest.testMark_ ( CharReaderWithInsertsTest::getDefaultReader );
    }

/*
    @Test
    public void testPeekCurrentString() throws IOException {
        CharReaderTest.testPeekCurrentString_ ( StackedCharReaderTest::getDefaultReader );
    }
*/

    @Test
    public void testPeekCharAfter() throws IOException {
        CharReaderTest.testPeekCharAfter_ ( CharReaderWithInsertsTest::getDefaultReader );
    }

    @Test
    public void testPeekNextMaxNChars() throws IOException {
        CharReaderTest.testPeekNextMaxNChars_ ( CharReaderWithInsertsTest::getDefaultReader );
    }
}
