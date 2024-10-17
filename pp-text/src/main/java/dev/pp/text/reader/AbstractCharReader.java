package dev.pp.text.reader;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.character.CharChecks;
import dev.pp.basics.utilities.character.CharConsumer;
import dev.pp.basics.utilities.character.CharPredicate;
import dev.pp.basics.utilities.string.StringBuilderUtils;
import dev.pp.text.token.TextToken;

import java.io.IOException;

public abstract class AbstractCharReader implements CharReader {

/*
    protected boolean checkHasChar() {

        if ( hasChar() ) {
            return true;
        } else {
            throw new NoSuchElementException (
                "There are no more characters to read at position" + StringConstants.OS_LINE_BREAK + currentLocation () );
        }
    }
 */

    public @NotNull TextToken currentCharToken() {
        return new TextToken ( String.valueOf ( currentChar() ), currentLocation() );
    }

    public @Nullable String advanceWhile ( @NotNull CharPredicate predicate ) throws IOException {

        // assert checkHasChar();

        StringBuilder sb = new StringBuilder ();
        if ( appendWhile ( predicate, sb ) ) {
            return sb.toString ();
        } else {
            return null;
        }
    }


    // consume

    public void consumeCurrentChar ( @NotNull CharConsumer consumer ) throws IOException {

        // assert checkHasChar();

        consumer.consume ( currentChar() );
        advance();
    }

    public boolean consumeCurrentCharIf ( @NotNull CharPredicate predicate, @NotNull CharConsumer consumer )
        throws IOException {

        // assert checkHasChar();

        if ( ! predicate.accept ( currentChar() ) ) return false;
        consumeCurrentChar ( consumer );
        return true;
    }

    public boolean consumeWhile ( @NotNull CharPredicate predicate, @NotNull CharConsumer consumer )
        throws IOException {

        // assert checkHasChar();

        boolean charFound = false;
        while ( hasChar() ) {
            if ( ! predicate.accept ( currentChar() ) ) return charFound;
            consumer.consume ( currentChar() );
            charFound = true;
            advance();
        }
        return charFound;
    }

    public boolean consumeWhileNotAtCharOrEnd ( char ch, @NotNull CharConsumer consumer ) throws IOException {

        return consumeWhile ( c -> c != ch, consumer );
    }

    public boolean consumeWhileNotAtStringOrEnd ( @NotNull String string, @NotNull CharConsumer consumer ) throws IOException {

        char firstChar = string.charAt ( 0 );
        boolean charFound = false;
        while ( true ) {
            if ( consumeWhileNotAtCharOrEnd ( firstChar, consumer ) ) charFound = true;
            if ( ! hasChar() || isAtString ( string ) ) {
                return charFound;
            }
            consumer.consume ( currentChar() );
            advance();
        }
    }

    public boolean consumeRemaining ( @NotNull CharConsumer consumer ) throws IOException {

        boolean charFound = hasChar();
        while ( hasChar() ) {
            consumer.consume ( currentChar() );
            advance();
        }
        return charFound;
    }


    // append

    public void appendCurrentCharAndAdvance ( @NotNull StringBuilder sb ) throws IOException {

        // assert checkHasChar();

        sb.append ( currentChar() );
        advance();
    }

    public boolean appendCurrentCharIfAndAdvance ( @NotNull CharPredicate predicate, @NotNull StringBuilder sb )
        throws IOException {

        return consumeCurrentCharIf ( predicate, sb::append );
    }

    public boolean appendWhile ( @NotNull CharPredicate predicate, @NotNull StringBuilder sb )
        throws IOException {

        // assert checkHasChar();

        return consumeWhile ( predicate, sb::append );
    }

    public boolean appendWhileNotAtCharOrEnd ( char ch, @NotNull StringBuilder sb ) throws IOException {

        return consumeWhileNotAtCharOrEnd ( ch, sb::append );
    }

    public boolean appendWhileNotAtStringOrEnd ( @NotNull String string, @NotNull StringBuilder sb ) throws IOException {

        return consumeWhileNotAtStringOrEnd ( string, sb::append );
    }

    public boolean appendRemaining ( @NotNull StringBuilder sb ) throws IOException {

        return consumeRemaining ( sb::append );
    }


    // read

    public @Nullable Character readIf ( @NotNull CharPredicate predicate ) throws IOException {

        char currentChar = currentChar();
        if ( predicate.accept ( currentChar ) ) {
            advance();
            return currentChar;
        } else {
            return null;
        }
    }

    public @Nullable String readWhile ( @NotNull CharPredicate predicate ) throws IOException {

        StringBuilder sb = new StringBuilder();
        appendWhile ( predicate, sb );
        return sb.isEmpty() ? null : sb.toString();
    }

    public @Nullable String readWhileAtChar ( char c ) throws IOException {

        return readWhile ( currentChar -> currentChar == c );
    }

    public @Nullable String readWhileCharsMatch ( @NotNull String chars ) throws IOException {

        StringBuilder sb = new StringBuilder();

        for ( int i = 0; i < chars.length(); i++ ) {
            char c = chars.charAt(i);

            if ( currentChar() == c ) {
                sb.append ( c );
                advance();
            } else {
                break;
            }
        }

        return sb.isEmpty() ? null : sb.toString();
    }

    public @Nullable String readWhileNotAtCharOrEnd ( char ch ) throws IOException {

        StringBuilder sb = new StringBuilder();
        appendWhileNotAtCharOrEnd ( ch, sb );
        return sb.isEmpty() ? null : sb.toString();
    }

    public @Nullable String readWhileNotAtStringOrEnd ( @NotNull String string ) throws IOException {

        StringBuilder sb = new StringBuilder();
        appendWhileNotAtStringOrEnd ( string, sb );
        return sb.isEmpty() ? null : sb.toString();
    }

    public @Nullable String readMaxNChars ( long n ) throws IOException {

        StringBuilder sb = new StringBuilder();
        for ( long i = 1; i <= n; i++ ) {
            if ( ! hasChar() ) break;
            appendCurrentCharAndAdvance ( sb );
        }
        return sb.isEmpty() ? null : sb.toString();
    }

    public @Nullable String readRemainingLine ( boolean includeLineBreak ) throws IOException {

        StringBuilder sb = new StringBuilder();
        appendWhile ( CharChecks::isNotLineBreak, sb );
        if ( includeLineBreak ) {
            String lineBreak = readLineBreak();
            if ( lineBreak != null ) sb.append ( lineBreak );
        }
        return sb.isEmpty() ? null : sb.toString();
    }

    public @Nullable String readLine () throws IOException {

        StringBuilder sb = new StringBuilder();
        appendWhile ( CharChecks::isNotLineBreak, sb );
        skipLineBreak ();
        return sb.isEmpty() ? null : sb.toString();
    }

    public @Nullable String readLineBreak () throws IOException {

        StringBuilder sb = new StringBuilder();
        if ( currentChar() == '\r' ) {
            sb.append ( '\r' );
            advance();
        }
        if ( currentChar() == '\n' ) {
            sb.append ( '\n' );
            advance();
        }
        return sb.isEmpty() ? null : sb.toString();
    }

    public @Nullable Character readSpaceOrTab() throws IOException {
        return readIf ( CharChecks::isSpaceOrTab );
    }

    public @Nullable String readSpacesAndTabs() throws IOException {
        return readWhile ( CharChecks::isSpaceOrTab );
    }

    public @Nullable String readSpaceOrTabOrLineBreak() throws IOException {

        @Nullable Character spaceOrTab = readSpaceOrTab();
        if ( spaceOrTab != null ) {
            return String.valueOf ( spaceOrTab );
        } else {
            return readLineBreak();
        }
    }

    public @Nullable String readSpacesAndTabsAndLineBreaks () throws IOException {
        return readWhile ( CharChecks::isSpaceOrTabOrLineBreak );
    }

    public @Nullable String readRemaining() throws IOException {

        StringBuilder sb = new StringBuilder();
        appendRemaining ( sb );
        return sb.isEmpty() ? null : sb.toString();
    }

    @Override
    public @Nullable String readUntilEndOfLine ( boolean includeLineBreak ) throws IOException {

        StringBuilder sb = new StringBuilder();
        appendWhile ( CharChecks::isNotLineBreak, sb );
        if ( includeLineBreak ) {
            StringBuilderUtils.appendIfNotNull ( sb, readLineBreak() );
        }
        return sb.isEmpty() ? null : sb.toString();
    }


    // isAt

    public boolean isAt ( @NotNull CharPredicate predicate ) {

        // assert checkHasChar();

        return predicate.accept ( currentChar() );
    }

    public boolean isAtChar ( char c ) {

        // assert checkHasChar();

        return currentChar() == c;
    }

    public boolean isAtLineBreak () {
        return isAt ( CharChecks::isLineBreak );
    }

    public boolean isAtSpaceOrTabOrLineBreak () {
        return isAt ( CharChecks::isSpaceOrTabOrLineBreak );
    }


    // skip

    public boolean skipChar ( char c ) throws IOException {

        // assert checkHasChar();

        if ( currentChar() == c ) {
            advance();
            return true;
        } else {
            return false;
        }
    }

    public boolean skipString ( @NotNull String string ) throws IOException {

        // assert checkHasChar();

        if ( isAtString ( string ) ) {
            skipNChars ( string.length() );
            return true;
        } else {
            return false;
        }
    }

    public boolean skipIf ( @NotNull CharPredicate predicate ) throws IOException {

        // assert checkHasChar();

        return consumeCurrentCharIf ( predicate, c -> {} );
    }

    public boolean skipWhile ( @NotNull CharPredicate predicate ) throws IOException {

        // assert checkHasChar();

        return consumeWhile ( predicate, c -> {} );
    }

    public boolean skipAllWhileCharsMatch ( @NotNull String chars ) throws IOException {

        for ( int i = 0; i < chars.length(); i++ ) {
            char c = chars.charAt(i);

            if ( currentChar() == c ) {
                advance();
            } else {
                return false;
            }
        }

        return true;
    }

    public void skipNChars ( long n ) throws IOException {

        // assert checkHasChar();

        if ( n < 0 ) throw new IllegalArgumentException ( "n cannot be < 0, but is " + n + "." );

        for ( long i = 1; i <= n; i++ ) {
            if ( hasChar() ) {
                advance();
            } else {
                throw new IllegalArgumentException (
                    "Cannot skip " + n + " characters, because the end of input has been reached after " + i + " skips."  );
            }
        }
    }

    public boolean skipLineBreak () throws IOException {

        skipChar ( '\r');
        return skipChar ( '\n');
    }

    public boolean skipLineBreaks () throws IOException {
        return skipWhile ( CharChecks::isLineBreak );
    }

    public boolean skipSpaceOrTab() throws IOException {
        return skipIf ( CharChecks::isSpaceOrTab );
    }

    public boolean skipSpacesAndTabs() throws IOException {
        return skipWhile ( CharChecks::isSpaceOrTab );
    }

    public boolean skipSpaceOrTabOrLineBreak () throws IOException {

        if ( skipSpaceOrTab() ) return true;
        return skipLineBreak ();
    }

    public boolean skipSpacesAndTabsAndLineBreaks () throws IOException {
        return skipWhile ( CharChecks::isSpaceOrTabOrLineBreak );
    }

    public boolean skipUntilEndOfLine ( boolean includeLineBreak ) throws IOException {

        boolean skipped = skipWhile ( CharChecks::isNotLineBreak );
        if ( includeLineBreak ) {
            if ( skipLineBreak() ) skipped = true;
        }
        return skipped;
    }



    // state

    public void stateToOSOut ( @Nullable String label ) {

        System.out.println();
        if ( label != null ) System.out.println ( label );
        System.out.println ( stateToString() );
    }
}
