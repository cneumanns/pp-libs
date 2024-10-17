package dev.pp.text.inspection;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.basics.utilities.file.FilePathUtils;
import dev.pp.basics.utilities.os.process.OSCommand;
import dev.pp.text.inspection.message.TextInspectionError;
import dev.pp.text.location.TextLocation;

import java.io.IOException;
import java.nio.file.Path;

public class InvalidTextUtil {

    public static void showInEditor (
        @NotNull TextInspectionError error,
        @NotNull String openFileOSCommandTemplate ) throws IOException {

        @Nullable TextLocation location = error.getLocation();
        if ( location == null ) return;
        @Nullable Path filePath = location.getResourceAsFilePath();
        if ( filePath == null ) return;
        filePath = FilePathUtils.toExistingOSPath ( filePath );

        long lineNumber = location.getLineNumber();
        if ( lineNumber < 1 ) lineNumber = 1;
        long columnNumber = location.getColumnNumber();
        if ( columnNumber < 1 ) columnNumber = 1;

        // TODO use parameters instead of hard-coded values for place-holders
        @NotNull String command = openFileOSCommandTemplate
            .replace ( "[[file]]", filePath.toString () )
            .replace ( "[[line]]", String.valueOf ( lineNumber ) )
            .replace ( "[[column]]", String.valueOf ( columnNumber ) );

        try {
            OSCommand.startAndContinue ( command );
        } catch ( IOException e ) {
            SimpleLogger.error (
                "The editor could not be opened. The following error occurred when executing " +
                command + ": " + e.getMessage () );
        }
    }
}
