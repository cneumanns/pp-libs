
package dev.pp.text.reader;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.character.CharConsumer;
import dev.pp.basics.utilities.character.CharPredicate;
import dev.pp.text.location.TextLocation;
import dev.pp.text.resource.TextResource;
import dev.pp.text.token.TextToken;

import java.io.IOException;

public interface CharReader {

    @Deprecated
    boolean hasChar();

    char currentChar();

    // advance
    // returns true if successfully advanced; false if eof
    boolean advance() throws IOException;

    // char getCurrentAndAdvance() throws IOException;
    // char advanceAndGetCurrent() throws IOException;
    // ? @Nullable Character advanceIf ( @NotNull CharPredicate predicate ) throws IOException;
    // advanceIf ( @NotNull CharPredicate predicate ) throws IOException;
    // char getCurrentAndAdvanceIf ( @NotNull CharPredicate predicate ) throws IOException;
    // char AdvanceIfAndGetCurrent ( @NotNull CharPredicate predicate ) throws IOException;
    @Nullable String advanceWhile ( @NotNull CharPredicate predicate ) throws IOException;

    // location
    @NotNull TextLocation currentLocation();
    @NotNull TextToken currentCharToken();
    @Nullable TextResource resource();
    int currentLineNumber();
    int currentColumnNumber();

    // consume
    void consumeCurrentChar ( @NotNull CharConsumer consumer ) throws IOException;
    boolean consumeCurrentCharIf ( @NotNull CharPredicate predicate, @NotNull CharConsumer consumer ) throws IOException;
    boolean consumeWhile ( @NotNull CharPredicate predicate, @NotNull CharConsumer consumer ) throws IOException;
    boolean consumeWhileNotAtCharOrEnd ( char ch, @NotNull CharConsumer consumer ) throws IOException;
    boolean consumeWhileNotAtStringOrEnd ( @NotNull String string, @NotNull CharConsumer consumer ) throws IOException;
    boolean consumeRemaining ( @NotNull CharConsumer consumer ) throws IOException;
    // boolean consumeIfHasNext ( @NotNull CharConsumer consumer ) throws IOException;
    // void consumeUntil ( @NotNull CharPredicate predicate, @NotNull CharConsumer consumer ) throws IOException;
    // void consumeUntilOrAll ( @NotNull CharPredicate predicate, @NotNull CharConsumer consumer ) throws IOException;
    // void consumeNChars ( long n, @NotNull CharConsumer consumer ) throws IOException;
    // void consumeNCharsOrAll ( long n, @NotNull CharConsumer consumer ) throws IOException;

    // append
    void appendCurrentCharAndAdvance ( @NotNull StringBuilder sb ) throws IOException;
    boolean appendCurrentCharIfAndAdvance ( @NotNull CharPredicate predicate, @NotNull StringBuilder sb ) throws IOException;
    boolean appendWhile ( @NotNull CharPredicate predicate, @NotNull StringBuilder sb ) throws IOException;
    boolean appendWhileNotAtCharOrEnd ( char ch, @NotNull StringBuilder sb ) throws IOException;
    boolean appendWhileNotAtStringOrEnd ( @NotNull String string, @NotNull StringBuilder sb ) throws IOException;
    boolean appendRemaining ( @NotNull StringBuilder sb ) throws IOException;
    // boolean appendIfHasNext ( @NotNull StringBuilder sb ) throws IOException;
    // boolean appendNextIf ( @NotNull CharPredicate predicate, @NotNull StringBuilder sb ) throws IOException;

    // read
    @Nullable Character readIf ( @NotNull CharPredicate predicate ) throws IOException;
    @Nullable String readWhile ( @NotNull CharPredicate predicate ) throws IOException;
    @Nullable String readWhileAtChar ( char c ) throws IOException;
    @Nullable String readWhileCharsMatch ( @NotNull String chars ) throws IOException;
    // @Nullable String readNChars ( long n ) throws IOException;
    @Nullable String readWhileNotAtCharOrEnd ( char ch ) throws IOException;
    @Nullable String readWhileNotAtStringOrEnd ( @NotNull String string ) throws IOException;
    @Nullable String readMaxNChars ( long n ) throws IOException;
    @Nullable String readRemainingLine ( boolean includeLineBreak ) throws IOException;
    @Nullable String readLine() throws IOException;
    @Nullable String readRemaining() throws IOException;
    @Nullable String readLineBreak() throws IOException;
    @Nullable Character readSpaceOrTab() throws IOException;
    @Nullable String readSpacesAndTabs() throws IOException;
    @Nullable String readSpaceOrTabOrLineBreak() throws IOException;
    @Nullable String readSpacesAndTabsAndLineBreaks() throws IOException;
    @Nullable String readUntilEndOfLine ( boolean includeLineBreak ) throws IOException;

    // isAt
    boolean isAt ( @NotNull CharPredicate predicate );
    boolean isAtChar ( char c );
    boolean isAtString ( @NotNull String s ) throws IOException;
    boolean isAtLineBreak();
    boolean isAtSpaceOrTabOrLineBreak();
    boolean isAtEnd();
    boolean isNotAtEnd();

    // skip
    boolean skipChar ( char c ) throws IOException;
    boolean skipString ( @NotNull String s ) throws IOException;
    boolean skipIf ( @NotNull CharPredicate predicate ) throws IOException;
    boolean skipWhile ( @NotNull CharPredicate predicate ) throws IOException;
    boolean skipAllWhileCharsMatch ( @NotNull String chars ) throws IOException;
    void skipNChars ( long n ) throws IOException;
    // void skipNCharsOrRemaining ( long n ) throws IOException;
    boolean skipLineBreak() throws IOException;
    boolean skipLineBreaks() throws IOException;
    boolean skipSpaceOrTab() throws IOException;
    boolean skipSpacesAndTabs() throws IOException;
    boolean skipSpaceOrTabOrLineBreak() throws IOException;
    boolean skipSpacesAndTabsAndLineBreaks() throws IOException;
    boolean skipUntilEndOfLine ( boolean includeLineBreak ) throws IOException;

    // read-ahead
    void setMark ( int readAheadLimit );
    void removeMark();
    void goBackToMark();

    // peek
    @Nullable Character peekNextChar() throws IOException;
    boolean isNextChar ( char c ) throws IOException;
    @Nullable String peekNextMaxNChars ( int n ) throws IOException;
    @Nullable Character peekCharAfterRequired ( @NotNull CharPredicate predicate, int lookAhead ) throws IOException;
    @Nullable Character peekCharAfterOptional ( @NotNull CharPredicate predicate, int lookAhead ) throws IOException;
    // void peekNextWhile ( @NotNull CharPredicate predicate, @NotNull CharConsumer consumer ) throws IOException;
    // @NotNull String peekNextWhile ( int length ) throws IOException;
    // void peekNextWhile ( @NotNull CharPredicate predicate, @NotNull StringBuilder sb ) throws IOException;
    // void peekNextNCharsOrAll ( long n ) throws IOException;
    // void peekNextAll ( long n ) throws IOException;

    // debugging
    @NotNull String stateToString();
    void stateToOSOut ( @Nullable String label );
}
