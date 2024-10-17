package dev.pp.text.writer;

import dev.pp.basics.annotations.NotNull;

public class HelperWriterConfig {


    public static final int DEFAULT_INDENT_SIZE = 4;
    public static final boolean DEFAULT_USE_TAB_INDENT = false;
    public static final @NotNull LineBreakKind DEFAULT_LINE_BREAK_KIND = LineBreakKind.CURRENT_OS;
    public static final @NotNull HelperWriterConfig DEFAULT_CONFIG = new HelperWriterConfig (
        DEFAULT_INDENT_SIZE, DEFAULT_USE_TAB_INDENT, DEFAULT_LINE_BREAK_KIND );


    private final int indentSize;
    public int getIndentSize() { return indentSize; }

    private final boolean useTabIndent;
    public boolean useTabIndent() { return useTabIndent; }

    private final @NotNull LineBreakKind lineBreakKind;
    public @NotNull LineBreakKind getLineBreakKind() { return lineBreakKind; }


    public HelperWriterConfig (
        int indentSize,
        boolean useTabIndent,
        @NotNull LineBreakKind lineBreakKind ) {

        this.indentSize = indentSize;
        this.useTabIndent = useTabIndent;
        this.lineBreakKind = lineBreakKind;
    }

    public HelperWriterConfig ( int indentSize ) {
        this ( indentSize, DEFAULT_USE_TAB_INDENT, DEFAULT_LINE_BREAK_KIND );
    }
}
