package dev.pp.text.reader;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.character.CharPredicate;
import dev.pp.text.resource.TextResource;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

import java.io.*;

public class CharReaderImpl extends AbstractCharReader implements CharReader {

    private final @NotNull Reader reader;

    private boolean hasChar;
    private char currentChar;

    private final @Nullable TextResource resource;
    private int nextLineNumber;
    private int nextColumnNumber;

    private boolean hasNextAtMark;
    private char nextCharAtMark;
    private int nextLineNumberAtMark;
    private int nextColumnNumberAtMark;

    private boolean isMarked;


    // constructors

    public CharReaderImpl (
        @NotNull Reader reader,
        @Nullable TextResource resource,
        @Nullable Integer lineOffset,
        @Nullable Integer columnOffset ) {

        Reader readerWithMarkSupport = reader.markSupported() ? reader : new BufferedReader ( reader );
        assert readerWithMarkSupport.markSupported();
        this.reader = readerWithMarkSupport;

        this.hasChar = false;
        this.currentChar = 0;

        this.resource = resource;
        this.nextLineNumber = lineOffset == null ? 1 : lineOffset;
        this.nextColumnNumber = columnOffset == null ? 0 : columnOffset - 1;

        this.isMarked = false;
    }

    public static @NotNull CharReaderImpl createAndAdvance (
        @NotNull Reader reader,
        @Nullable TextResource resource,
        @Nullable Integer lineOffset,
        @Nullable Integer columnOffset ) throws IOException {

        CharReaderImpl result = new CharReaderImpl ( reader, resource, lineOffset, columnOffset );
        result.advance();
        return result;
    }


    public boolean hasChar () {
        return hasChar;
    }

    public char currentChar () {

        // assert checkHasChar();

        return currentChar;
    }


    // advance

    public boolean advance () throws IOException {

        // assert checkHasChar ();

        if ( currentChar == '\n' ) {
            nextLineNumber += 1;
            nextColumnNumber = 1;
        } else {
            nextColumnNumber += 1;
        }

        int nextInt = reader.read();
        if ( nextInt == -1 ) {
            hasChar = false;
            currentChar = 0;
            return false;
        } else {
            hasChar = true;
            currentChar = (char) nextInt;
            return true;
        }
    }


    // location

    public @NotNull TextLocation currentLocation () {
        return new TextLocation ( resource, nextLineNumber, nextColumnNumber, null );
    }

    public @Nullable TextResource resource () {
        return resource;
    }

    public int currentLineNumber () {
        return nextLineNumber;
    }

    public int currentColumnNumber () {
        return nextColumnNumber;
    }


    // consume

/*
    public void consumeCurrentChar ( @NotNull CharConsumer consumer ) throws IOException {

        assert checkHasChar ();

        consumer.consume ( currentChar );
        advance ();
    }

    public boolean consumeCurrentCharIf ( @NotNull CharPredicate predicate, @NotNull CharConsumer consumer ) throws IOException {

        assert checkHasChar ();

        if ( !predicate.accept ( currentChar ) ) return false;
        consumeCurrentChar ( consumer );
        return true;
    }

    public boolean consumeWhile ( @NotNull CharPredicate predicate, @NotNull CharConsumer consumer ) throws IOException {

        // assert checkHasChar();

        boolean charFound = false;
        while ( hasChar ) {
            if ( !predicate.accept ( currentChar ) ) return charFound;
            consumer.consume ( currentChar );
            charFound = true;
            advance ();
        }
        return charFound;
    }

    public boolean consumeWhileNotAtStringOrEnd ( @NotNull String string, @NotNull CharConsumer consumer ) throws IOException {

        char firstChar = string.charAt ( 0 );
        boolean charFound = false;
        while ( true ) {
            if ( consumeWhileNotAtCharOrEnd ( firstChar, consumer ) ) charFound = true;
            if ( !hasChar || isAtString ( string ) ) {
                return charFound;
            }
        }
    }

    public boolean consumeRemaining ( @NotNull CharConsumer consumer ) throws IOException {

        boolean charFound = hasChar;
        while ( hasChar ) {
            consumer.consume ( currentChar );
            advance ();
        }
        return charFound;
    }


    // append

    public void appendCurrentCharAndAdvance ( @NotNull StringBuilder sb ) throws IOException {

        assert checkHasChar ();

        sb.append ( currentChar );
        advance ();
    }
*/


    // read

/*
    public @Nullable String readMaxNChars ( long n ) throws IOException {

        assert n > 0;

        StringBuilder sb = new StringBuilder();
        for ( long i = 1; i <= n; i++ ) {
            if ( ! hasChar ) break;
            appendCurrentCharAndAdvance ( sb );
        }
        return sb.length() == 0 ? null : sb.toString();
    }
*/


    // isAt
/*
    public boolean isAt ( @NotNull CharPredicate predicate ) {

        // assert checkHasChar();

        return predicate.accept ( currentChar );
    }

    public boolean isAtChar ( char c ) {

        // assert checkHasChar();

        return currentChar == c;
    }
*/

    public boolean isAtString ( @NotNull String s ) throws IOException {

        // assert checkHasChar();

        // fast return if current character doesn't match
        if ( currentChar != s.charAt ( 0 ) ) return false;

        setMark ( s.length() );

        boolean result = true;
        for ( int i = 1; i < s.length(); i++ ) {

            int nextInt = reader.read();

            if ( nextInt == -1 ) {
                result = false;
                break;
            }

            if ( (char) nextInt != s.charAt ( i ) ) {
                result = false;
                break;
            }
        }

        goBackToMark();

        return result;
    }

    public boolean isAtEnd() {
        return ! hasChar;
    }

    public boolean isNotAtEnd() {
        return hasChar;
    }


    // skip
/*
    public boolean skipChar ( char c ) throws IOException {

        // assert checkHasChar();

        if ( currentChar == c ) {
            advance();
            return true;
        } else {
            return false;
        }
    }

    public void skipNChars ( long n ) throws IOException {

        // assert checkHasChar();

        if ( n < 0 ) throw new IllegalArgumentException ( "n cannot be < 0, but is " + n + "." );

        for ( long i = 1; i <= n; i++ ) {
            if ( hasChar ) {
                advance();
            } else {
                throw new IllegalArgumentException (
                    "Cannot skip " + n + " characters, because the end of input has been reached after " + i + " skips."  );
            }
        }
    }
*/


    // read-ahead

    public void setMark ( int readAheadLimit ) {

        // assert checkHasChar();

        if ( isMarked ) {
            throw new RuntimeException (
                "Reader is marked already. After calling 'setMark', either 'removeMark' or 'goBackToMark' must be called before calling 'setMark' again." );
        }

        hasNextAtMark = hasChar;
        nextCharAtMark = currentChar;
        nextLineNumberAtMark = nextLineNumber;
        nextColumnNumberAtMark = nextColumnNumber;

        try {
            reader.mark ( readAheadLimit );
            isMarked = true;
        } catch ( IOException e ) {
            // should never happen because markSupported() is ensured in constructor
            throw new RuntimeException ( e );
        }
    }

    public void removeMark() {

        if ( ! isMarked ) {
            throw new RuntimeException (
                "Reader is not marked. Therefore 'removeMark' cannot be called." );
        }

        isMarked = false;
    }

    public void goBackToMark() {

        if ( ! isMarked ) {
            throw new RuntimeException (
                "Reader is not marked. Therefore 'goBackToMark' cannot be called." );
        }

        try {
            reader.reset();
            isMarked = false;
        } catch ( IOException e ) {
            // should never happen because markSupported() is ensured in constructor
            throw new RuntimeException ( e );
        }

        hasChar = hasNextAtMark;
        currentChar = nextCharAtMark;
        nextLineNumber = nextLineNumberAtMark;
        nextColumnNumber = nextColumnNumberAtMark;
    }


    // peek

    public @Nullable Character peekNextChar() throws IOException {

        // assert checkHasChar();
        if ( ! hasChar ) return null;

        setMark ( 1 );
        int peekedInt = reader.read();
        goBackToMark();

        if ( peekedInt == -1 ) {
            return null;
        } else {
            return (char) peekedInt;
        }
    }

    public boolean isNextChar ( char c ) throws IOException {

        // assert checkHasChar();

        Character next = peekNextChar();
        if ( next != null ) {
            return next == c;
        } else {
            return false;
        }
    }

    public @Nullable String peekNextMaxNChars ( int n ) throws IOException {

        assert n > 0;

        if ( ! hasChar ) return null;

        setMark ( n + 1 );
        advance();
        String result = readMaxNChars ( n );
        goBackToMark();
        return result;
    }

    public @Nullable Character peekCharAfterRequired ( @NotNull CharPredicate predicate, int lookAhead ) throws IOException {

        return peekCharAfter ( predicate, lookAhead, true );
    }

    public @Nullable Character peekCharAfterOptional ( @NotNull CharPredicate predicate, int lookAhead ) throws IOException {

        return peekCharAfter ( predicate, lookAhead, false );
    }

    private @Nullable Character peekCharAfter (
        @NotNull CharPredicate predicate, int lookAhead, boolean required ) throws IOException {

        // assert checkHasChar();

        if ( required ) {
            if ( ! predicate.accept ( currentChar ) ) return null;
        }

        setMark ( lookAhead );
        skipWhile ( predicate );
        Character result = hasChar ? currentChar : null;
        goBackToMark();

        return result;
    }

/*
    public @NotNull String peekCurrentString ( int length ) throws IOException {
        if ( length <= 0 ) throw new IllegalArgumentException ( "length cannot be <= 0, but is " + length + "." );

        StringBuilder sb = new StringBuilder();
        sb.append ( previousChar );
        if ( length == 1 || ! hasNext ) return sb.toString();

        sb.append ( nextChar );
        if ( length == 2 ) return sb.toString();

        setMark ( length );
        for ( int i = 3; i <= length; i++ ) {
            int peekedInt = reader.read();
            if ( peekedInt == -1 ) break;
            sb.append ( (char) peekedInt );
        }
        goBackToMark();

        return sb.toString();
    }

*/

    @Override
    public String toString() { return resource == null ? "CharReader" : resource.toString(); }

    // debugging

    public @NotNull String stateToString() {

        StringBuilder sb = new StringBuilder();

        sb.append ( "at " );
        sb.append ( currentChar );
        sb.append ( " (" );
        sb.append ( nextLineNumber );
        sb.append ( "," );
        sb.append ( nextColumnNumber );
        sb.append ( ")" );

        return sb.toString();
    }
}
