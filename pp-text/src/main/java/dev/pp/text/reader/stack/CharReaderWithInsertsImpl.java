package dev.pp.text.reader.stack;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.character.CharPredicate;
import dev.pp.text.utilities.file.TextFileReaderUtil;
import dev.pp.text.reader.AbstractCharReader;
import dev.pp.text.reader.CharReader;
import dev.pp.text.reader.CharReaderImpl;
import dev.pp.text.location.TextLocation;
import dev.pp.text.resource.File_TextResource;
import dev.pp.text.resource.String_TextResource;
import dev.pp.text.resource.TextResource;
import dev.pp.basics.utilities.string.StringConstants;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.Stack;


public class CharReaderWithInsertsImpl extends AbstractCharReader implements CharReaderWithInserts {


    private final @NotNull Stack<CharReader> readers;

    private @NotNull CharReader currentReader;
    // public @NotNull CharReader getCurrentReader() { return currentReader; }

    // Can possibly be uncommented when 'mark' and 'readahead' functions are fixed (see comment below)
//    private boolean hasChar;
//    private char currentChar;


    // constructors

    public CharReaderWithInsertsImpl ( @NotNull CharReader initialReader ) {

        this.readers = new Stack<>();

//        this.hasChar = initialReader.hasChar();
//        this.currentChar = initialReader.currentChar();

        insert ( initialReader );
    }

/*
    private CharReaderWithInsertsImpl (
        @NotNull TextResourceReader reader,
        @Nullable Integer lineOffset,
        @Nullable Integer columnOffset ) throws IOException {

        this ( new CharReaderImpl ( reader, null, lineOffset, columnOffset, false ) );
    }

 */

    private CharReaderWithInsertsImpl (
        @NotNull Reader reader,
        @Nullable TextResource resource,
        @Nullable Integer lineOffset,
        @Nullable Integer columnOffset ) {

        this ( new CharReaderImpl ( reader, resource, lineOffset, columnOffset ) );
    }

    public static @NotNull CharReaderWithInsertsImpl createAndAdvance (
        @NotNull Reader reader,
        @Nullable TextResource resource,
        @Nullable Integer lineOffset,
        @Nullable Integer columnOffset ) throws IOException {

        CharReaderWithInsertsImpl result = new CharReaderWithInsertsImpl ( reader, resource, lineOffset, columnOffset );
        result.advance();
        return result;
    }


    // insert

    public void insert ( @NotNull CharReader reader ) {

        readers.push ( reader );
        currentReader = reader;
    }

    public void insert ( @NotNull Path filePath ) throws IOException {

        // TODO fileReader must be closed later
        FileReader fileReader = TextFileReaderUtil.createUTF8FileReader ( filePath );
//        CharReader charReader = new CharReaderImpl ( fileReader, new File_TextResource ( filePath ), null, null );
        CharReader charReader = CharReaderImpl.createAndAdvance ( fileReader, new File_TextResource ( filePath ), null, null );
        insert ( charReader );
    }

/*
    public void insert ( @NotNull URL url ) throws IOException {

        insert ( new CharReaderImpl ( url ) );
    }

 */

    public void insert ( @NotNull String string ) {

        // TODO stringReader must be closed later
        StringReader stringReader = new StringReader ( string );
        try {
//            CharReader charReader = new CharReaderImpl ( stringReader, new String_TextResource ( string ), null, null );
            CharReader charReader = CharReaderImpl.createAndAdvance ( stringReader, new String_TextResource ( string ), null, null );
            insert ( charReader );
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }


    // iteration

    public boolean hasChar() { return currentReader.hasChar(); }
//    public boolean hasChar() { return hasChar; }

    public char currentChar () { return currentReader.currentChar(); }
//    public char currentChar () { return currentChar; }


    // advance

    public boolean advance() throws IOException {

        currentReader.advance();

        while ( true ) {

            if ( currentReader.hasChar() ) {
//                this.hasChar = true;
//                this.currentChar = currentReader.currentChar();
                return true;
            }

            readers.pop();

            if ( readers.empty() ) {
//                this.hasChar = false;
//                this.currentChar = 0;
                return false;
            }

            currentReader = readers.peek();
        }
    }


    // location

    public @NotNull TextLocation currentLocation () {

        if ( readers.isEmpty() ) return currentReader.currentLocation();

        TextLocation result = null;
        for ( CharReader reader : readers ) {
            result = new TextLocation (
                reader.resource(), reader.currentLineNumber (), reader.currentColumnNumber (), result );
        }
        return result;
    }

    public @Nullable TextResource resource() { return currentReader.resource(); }

    public int currentLineNumber () { return currentReader.currentLineNumber(); }

    public int currentColumnNumber () { return currentReader.currentColumnNumber(); }


    // isAt

    // TODO does not work if part of the end of the string is on stacked reader
    // can be fixed after 'mark' functions are fixed (see comment below)
    public boolean isAtString ( @NotNull String s ) throws IOException { return currentReader.isAtString ( s ); }

    public boolean isAtEnd() { return currentReader.isAtEnd(); }

    public boolean isNotAtEnd() { return currentReader.isNotAtEnd(); }


    /*
        TODO !!!
        The following mark and peek functions do not work correctly if the current reader is finished
        and more non-finished readers are on the stack.
        Instead of delegating to currentReader, these functions should have their own implementation that considers
        all readers on the stack
    */

    // read-ahead

    public void setMark ( int readAheadLimit ) { currentReader.setMark ( readAheadLimit ); }

    public void removeMark() { currentReader.removeMark(); }

    public void goBackToMark() { currentReader.goBackToMark(); }


    // peek

    public @Nullable Character peekNextChar() throws IOException {
        return currentReader.peekNextChar(); }

    public boolean isNextChar ( char c ) throws IOException {
        return currentReader.isNextChar ( c ); }

    public @Nullable String peekNextMaxNChars ( int n ) throws IOException {
        return currentReader.peekNextMaxNChars ( n ); }

    public @Nullable Character peekCharAfterRequired (
        @NotNull CharPredicate predicate, int lookAhead ) throws IOException {

        return currentReader.peekCharAfterRequired ( predicate, lookAhead );
    }

    public @Nullable Character peekCharAfterOptional (
        @NotNull CharPredicate predicate, int lookAhead ) throws IOException {

        return currentReader.peekCharAfterOptional ( predicate, lookAhead );
    }


    @Override
    public String toString() { return currentReader.toString(); }


    // debugging

    public @NotNull String stateToString() {

        StringBuilder sb = new StringBuilder();

        sb.append ( "Readers on stack: " );
        sb.append ( readers.size() );
        sb.append ( StringConstants.OS_LINE_BREAK );

        int i = 1;
        for ( CharReader reader : readers ) {
            sb.append ( "#" + i + ": " + reader.stateToString() );
            sb.append ( StringConstants.OS_LINE_BREAK );
            i++;
        }
        sb.append ( "current reader: " );
        sb.append ( currentReader.stateToString() );

        return sb.toString();
    }
}
