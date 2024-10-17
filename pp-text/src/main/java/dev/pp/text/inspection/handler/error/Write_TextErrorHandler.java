package dev.pp.text.inspection.handler.error;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSIO;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.text.inspection.message.TextInspectionError;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Function;

@Deprecated
public class Write_TextErrorHandler extends AbstractTextErrorHandler {


    private final @NotNull Writer writer;
    private final @Nullable String messageSeparator;


    public Write_TextErrorHandler (
        @NotNull Writer writer,
        @NotNull Function<TextInspectionError, String> errorToStringConverter,
        @Nullable String messageSeparator ) {

        super ( errorToStringConverter );

        this.writer = writer;
        this.messageSeparator = messageSeparator;
    }

    public Write_TextErrorHandler ( @NotNull Writer writer ) {
        this ( writer, TextInspectionError::toString, StringConstants.OS_LINE_BREAK );
    }

    public Write_TextErrorHandler() {
        this ( OSIO.standardErrorUTF8Writer() );
    }


    public void handle ( @NotNull TextInspectionError error, @NotNull String text ) {

        try {
            writer.write ( text );
            if ( messageSeparator != null ) writer.write ( messageSeparator );
            writer.flush();
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }
}
