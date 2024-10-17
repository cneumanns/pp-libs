package dev.pp.text.inspection.handler.warning;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSIO;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.text.inspection.message.TextInspectionWarning;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Function;

@Deprecated
public class Write_TextWarningHandler extends AbstractTextWarningHandler {


    private final @NotNull Writer writer;
    private final @Nullable String messageSeparator;


    public Write_TextWarningHandler (
        @NotNull Writer writer,
        @NotNull Function<TextInspectionWarning, String> warningToStringConverter,
        @Nullable String messageSeparator ) {

        super ( warningToStringConverter );

        this.writer = writer;
        this.messageSeparator = messageSeparator;
    }

    public Write_TextWarningHandler ( @NotNull Writer writer ) {
        this ( writer, TextInspectionWarning::toString, StringConstants.OS_LINE_BREAK );
    }

    public Write_TextWarningHandler () {
        this ( OSIO.standardErrorUTF8Writer() );
    }


    public void handle ( @NotNull TextInspectionWarning warning, @NotNull String text ) {

        try {
            writer.write ( text );
            if ( messageSeparator != null ) writer.write ( messageSeparator );
            writer.flush();
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }
}
