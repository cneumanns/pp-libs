package dev.pp.parameters.utilities.parameterizedtext.reader;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.location.TextLocation;
import dev.pp.text.reader.CharReader;
import dev.pp.text.reader.CharReaderImpl;
import dev.pp.text.resource.TextResource;
import dev.pp.text.token.TextToken;

import java.io.IOException;
import java.io.Reader;

public class Fences_ParameterizedTextReader implements ParameterizedTextReader {


    public static final @NotNull String FENCE = "---";


    public Fences_ParameterizedTextReader() {}


    public @NotNull ParameterizedTextComponents read (
        @NotNull Reader reader,
        @Nullable TextResource resource,
        @Nullable Integer lineOffset,
        @Nullable Integer columnOffset ) throws IOException, InvalidTextException {

//        CharReader charReader = new CharReaderImpl ( reader, resource, lineOffset, columnOffset );
        CharReader charReader = CharReaderImpl.createAndAdvance ( reader, resource, lineOffset, columnOffset );
        TextToken parameters = readParameters ( charReader );
        TextToken text = readText ( charReader );

        return new ParameterizedTextComponents ( parameters, text );
    }

    private @Nullable TextToken readParameters ( @NotNull CharReader reader ) throws IOException, InvalidTextException {

        /*
            ---
            params
            ---
         */
        boolean startFound = false;
        while ( reader.hasChar() ) {
            String line = reader.readLine ();
            if ( line != null && line.trim().equals ( FENCE ) ) {
                startFound = true;
                break;
            }
        }
        if ( ! startFound ) {
            throw new InvalidTextException (
                "'" + FENCE + "' required to start the parameters section.",
                "PARAMETER_START_LINE_REQUIRED",
                new TextToken ( reader.currentChar(), reader.currentLocation() ) );
        }

        boolean endFound = false;
        TextLocation startLocation = reader.currentLocation();
        StringBuilder params = new StringBuilder();
        while ( reader.hasChar() ) {
            String line = reader.readLine ();
            if ( line != null && line.trim().equals ( FENCE ) ) {
                endFound = true;
                break;
            }
            if ( line != null ) params.append ( line );
            params.append ( StringConstants.OS_LINE_BREAK );
        }
        if ( ! endFound ) {
            throw new InvalidTextException (
                "'" + FENCE + "' required to end the parameters section.",
                "PARAMETER_END_LINE_REQUIRED",
                new TextToken ( reader.currentChar(), reader.currentLocation() ) );
        }

        return params.isEmpty() ? null : new TextToken ( params.toString(), startLocation );
    }

    private @Nullable TextToken readText ( @NotNull CharReader reader ) throws IOException {

        TextLocation startLocation = reader.currentLocation();
        String text = reader.readRemaining();

        return text == null ? null : new TextToken ( text, startLocation );
    }
}
