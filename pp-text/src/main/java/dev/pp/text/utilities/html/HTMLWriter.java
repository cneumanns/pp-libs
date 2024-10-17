package dev.pp.text.utilities.html;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.HtmlEscaper;
import dev.pp.text.utilities.text.BasicDocumentWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@Deprecated
public class HTMLWriter extends BasicDocumentWriter {


    public HTMLWriter ( @NotNull Writer writer, @NotNull String singleIndent ) {
        super ( writer, singleIndent );
    }

    public HTMLWriter ( @NotNull Writer writer ) {
        super ( writer );
    }


    // Basics

    @Override
    public HTMLWriter write ( @NotNull String string ) throws IOException {
        super.write ( string );
        return this;
    }

    @Override
    public HTMLWriter writeNullable ( @Nullable String string ) throws IOException {
        super.writeNullable ( string );
        return this;
    }

    @Override
    public HTMLWriter writeLine ( @NotNull String string ) throws IOException {
        super.writeLine ( string );
        return this;
    }

    @Override
    public HTMLWriter writeLineBreak () throws IOException {
        super.writeLineBreak ();
        return this;
    }

    @Override
    public HTMLWriter flush() throws IOException {
        super.flush();
        return this;
    }


    // Text

    public @NotNull String escapeText ( @NotNull String text ) {
        return HtmlEscaper.escapeHTMLText ( text );
    }

    @Override
    public HTMLWriter escapeAndWriteText ( @NotNull String text ) throws IOException {
        super.escapeAndWriteText ( text );
        return this;
    }

    @Override
    public HTMLWriter escapeAndWriteNullableText ( @Nullable String text ) throws IOException {
        super.escapeAndWriteNullableText ( text );
        return this;
    }

    @Override
    public HTMLWriter writeText ( @NotNull String text, boolean escapeText ) throws IOException {
        super.writeText ( text, escapeText );
        return this;
    }


    // Indent

    @Override
    public HTMLWriter increaseIndent() {
        super.increaseIndent();
        return this;
    }

    @Override
    public HTMLWriter decreaseIndent() {
        super.decreaseIndent();
        return this;
    }

    @Override
    public HTMLWriter writeIndent() throws IOException {
        super.writeIndent();
        return this;
    }


    // Tags

    public HTMLWriter writeBlockStartLine ( @NotNull String tag ) throws IOException {

        return writeBlockStartLine ( tag, null );
    }

    public HTMLWriter writeBlockStartLine ( @NotNull String tag, @Nullable String CSSClass ) throws IOException {

        writeIndent();
        writeStartTag ( tag, CSSClass );
        writeLineBreak ();
        return this;
    }

    public HTMLWriter writeBlockEndLine ( @NotNull String tag ) throws IOException {

        writeIndent();
        writeEndTag ( tag );
        writeLineBreak ();
        return this;
    }

    public HTMLWriter writeStartTag ( @NotNull String tag ) throws IOException {

        return writeStartTag ( tag, null );
    }

    public HTMLWriter writeStartTag ( @NotNull String tag, @Nullable String CSSClass ) throws IOException {

        write ( "<" );
        write ( tag );

        if ( CSSClass != null ) {
            write ( " class=\"" );
            write ( CSSClass );
            write ( "\"" );
        }

        write ( ">" );

        return this;
    }

    public HTMLWriter writeEndTag ( @NotNull String tag ) throws IOException {

        write ( "</" );
        write ( tag );
        write ( ">" );

        return this;
    }


    // Attributes

    public HTMLWriter writeAttributes ( @Nullable Map<String,String> attributes ) throws IOException {

        if ( attributes == null ) return this;

        for ( Map.Entry<String, String> entry : attributes.entrySet() ) {
            writeAttribute ( entry.getKey(), entry.getValue() );
        }

        return this;
    }

    public HTMLWriter writeAttribute ( @NotNull String name, @Nullable String value ) throws IOException {

        write ( " " );
        write ( name );
        write ( "=\"" );
        if ( value != null )
            write ( escapeText ( value ) );
        write ( "\"" );

        return this;
    }
}
