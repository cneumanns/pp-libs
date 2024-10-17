package dev.pp.text.utilities.text;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringConstants;

import java.io.IOException;
import java.io.Writer;

@Deprecated
public abstract class BasicDocumentWriter {


    protected final @NotNull Writer writer;
    private final @NotNull StringBuilder currentIndent;
    private final @NotNull String singleIndent;


    public BasicDocumentWriter ( @NotNull Writer writer, @NotNull String singleIndent ) {

        this.writer = writer;
        this.currentIndent = new StringBuilder();
        this.singleIndent = singleIndent;
    }

    public BasicDocumentWriter ( @NotNull Writer writer ) {
        this ( writer, "    " );
    }


    // Basics

    public BasicDocumentWriter write ( @NotNull String string ) throws IOException {
        writer.write ( string );
        return this;
    }

    public BasicDocumentWriter write ( char c ) throws IOException {
        writer.write ( c );
        return this;
    }

    public BasicDocumentWriter writeNullable ( @Nullable String string ) throws IOException {
        if ( string != null ) write ( string );
        return this;
    }

    public BasicDocumentWriter writeLine ( @NotNull String string ) throws IOException {
        write ( string );
        writeLineBreak ();
        return this;
    }

    public BasicDocumentWriter writeLineBreak() throws IOException {
        write ( StringConstants.OS_LINE_BREAK );
        return this;
    }

    public BasicDocumentWriter flush() throws IOException {
        writer.flush();
        return this;
    }


    // Text

    public abstract @NotNull String escapeText ( @NotNull String text );

    public BasicDocumentWriter escapeAndWriteText ( @NotNull String text ) throws IOException {
        write ( escapeText ( text ) );
        return this;
    }

    public BasicDocumentWriter escapeAndWriteNullableText ( @Nullable String text ) throws IOException {
        if ( text != null ) escapeAndWriteText ( text );
        return this;
    }

    public BasicDocumentWriter writeText ( @NotNull String text, boolean escapeText ) throws IOException {
        if ( escapeText ) {
            escapeAndWriteText ( text );
        } else {
            write ( text );
        }
        return this;
    }


    // Indent

    public BasicDocumentWriter increaseIndent() {
        currentIndent.append ( singleIndent );
        return this;
    }

    public BasicDocumentWriter decreaseIndent() {
        if ( currentIndent.isEmpty() )
            throw new RuntimeException ( "Illegal to call 'decreaseIndent', because the indent is zero already." );

        int length = currentIndent.length();
        currentIndent.delete ( length - singleIndent.length(), length );
        return this;
    }

    public BasicDocumentWriter writeIndent() throws IOException {
        if ( ! currentIndent.isEmpty() )
            write ( currentIndent.toString() );
        return this;
    }
}
