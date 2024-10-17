package dev.pp.basics.utilities;

import dev.pp.basics.annotations.NotNull;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class HtmlEscaper {

    public static void writeText (
        @NotNull CharSequence unescapedText,
        @NotNull Writer writer ) throws IOException {

        for ( int i = 0; i < unescapedText.length(); i++) {
            char currentChar = unescapedText.charAt(i);

            switch ( currentChar ) {
                case '<' -> writer.write ( "&lt;" );
                case '>' -> writer.write ( "&gt;" );
                case '&' -> writer.write ( "&amp;" );
                case '"' -> writer.write ( "&quot;" );
                case '\'' -> writer.write ( "&apos;" );
                default -> writer.write ( currentChar );
            }
        }
    }

    public static @NotNull String escapeHTMLText ( @NotNull CharSequence unescapedText ) {

        try ( StringWriter writer = new StringWriter() ) {
            writeText ( unescapedText, writer );
            return writer.toString();
        } catch ( IOException e ) {
            // should never happen
            throw new RuntimeException ( e );
        }
    }
}
