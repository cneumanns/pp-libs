package dev.pp.text.writer;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringConstants;

import java.io.IOException;
import java.io.Writer;

public class HelperWriter {

    private final @NotNull Writer writer;
    private final int indentSize;
    private final @NotNull String lineBreak;
    private final @NotNull String singleIndent;
    private final @NotNull StringBuilder currentIndent;


    public HelperWriter (
        @NotNull Writer writer,
        int indentSize,
        boolean useTabIndent,
        @NotNull LineBreakKind lineBreakKind ) {

        if ( indentSize < 0 || indentSize > 12 ) {
            throw new IllegalArgumentException ( "indentSize '" + indentSize + "' is invalid (must be between 1 and 12).");
        }

        this.writer = writer;
        this.indentSize = indentSize;
        this.lineBreak = switch ( lineBreakKind ) {
            case CURRENT_OS -> StringConstants.OS_LINE_BREAK;
            case UNIX -> StringConstants.UNIX_NEW_LINE;
            case WINDOWS -> StringConstants.WINDOWS_LINE_BREAK;
        };

        String indentChar = useTabIndent ? "\t" : " ";
        this.singleIndent = indentChar.repeat ( indentSize );
        this.currentIndent = new StringBuilder();
    }

    public HelperWriter ( @NotNull Writer writer, @NotNull HelperWriterConfig config ) {
        this ( writer, config.getIndentSize(), config.useTabIndent(), config.getLineBreakKind() );
    }

    public HelperWriter ( @NotNull Writer writer, int indentSize ) {
        this ( writer, indentSize, HelperWriterConfig.DEFAULT_USE_TAB_INDENT, HelperWriterConfig.DEFAULT_LINE_BREAK_KIND );
    }


    public HelperWriter ( @NotNull Writer writer ) {
        this ( writer, HelperWriterConfig.DEFAULT_INDENT_SIZE );
    }


    // Basics

    public HelperWriter writeLineBreak() throws IOException {
        writer.write ( lineBreak );
        return this;
    }

    public HelperWriter writeLine ( @NotNull String string ) throws IOException {

        writer.write ( string );
        writeLineBreak ();
        return this;
    }

    public HelperWriter writeNullable ( @Nullable String string ) throws IOException {
        if ( string != null ) writer.write ( string );
        return this;
    }


    // Indent

    public HelperWriter increaseIndent() {
        currentIndent.append ( singleIndent );
        return this;
    }

    public HelperWriter decreaseIndent() {
        if ( currentIndent.isEmpty() )
            throw new RuntimeException ( "Illegal to call 'decreaseIndent', because the indent is zero already." );

        int length = currentIndent.length();
        currentIndent.delete ( length - indentSize, length );
        return this;
    }

    public HelperWriter writeIndent() throws IOException {

        if ( ! currentIndent.isEmpty() ) {
            writer.write ( currentIndent.toString() );
        }
        return this;
    }


    // Indented Blocks

    public HelperWriter writeIndentedBlockStartLine (
        @NotNull String string,
        boolean increaseIndent ) throws IOException {

        writeIndent();
        writer.write ( string );
        writeLineBreak();
        if ( increaseIndent ) increaseIndent();

        return this;
    }

    public HelperWriter writeIndentedBlockEndLine (
        @NotNull String string,
        boolean decreaseIndent ) throws IOException {

        if ( decreaseIndent ) decreaseIndent();
        writeIndent();
        writer.write ( string );
        writeLineBreak();

        return this;
    }
}
