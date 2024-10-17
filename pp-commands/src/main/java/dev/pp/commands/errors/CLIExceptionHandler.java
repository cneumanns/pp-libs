package dev.pp.commands.errors;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.ExceptionUtil;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.texttable.writer.pretty.utilities.TextInspectionMessage_FormWriter;
import java.util.function.Function;

public class CLIExceptionHandler {

    private static final @NotNull Function<Throwable, String> UNCHECKED_EXCEPTION_MESSAGE_SUPPLIER = throwable -> {

        if ( throwable.getClass().getName().contains ( "picocli" ) ) {
            // Just return the error message (without stack trace) if it's a Picocli CLI input error
            return throwable.getMessage ();

        } else {
            return "The operation has been aborted due to the following unexpected application error:" +
                StringConstants.OS_LINE_BREAK +
                ExceptionUtil.getStackTrace ( throwable ) +
                StringConstants.OS_LINE_BREAK +
                "Please consider sending the above message to the developers of the application, so that they can fix the problem.";
        }
    };

    private static final @NotNull Function<Throwable, String> CHECKED_EXCEPTION_MESSAGE_SUPPLIER = throwable -> {

        if ( throwable instanceof InvalidTextException textErrorException ) {
            // use a form layout to display a more user-friendly error message in case of a text error
            return TextInspectionMessage_FormWriter.messageToString ( textErrorException.textInspectionError () );
        } else  {
            return null; // use default
        }
    };

/*
    private static final @NotNull Function<Throwable, String> CHECKED_EXCEPTION_MESSAGE_SUPPLIER = throwable -> {

        if ( throwable instanceof TextErrorException textErrorException ) {
            TextError textError = textErrorException.getTextError ();
            // if ( throwable instanceof InvalidNonCancellingTextException ) {

            if ( textError.hasBeenHandled() ) {
                // Could also do nothing, because the error has already been reported by an error handler
                return "The operation has been aborted due to errors encountered";
            } else {
                return TextErrorOrWarning_FormWriter.errorToString ( textError );
            }
        } else  {
            return null; // use default
        }
    };
*/

    public static void handleThrowable ( @NotNull Throwable throwable ) {

        /*
        // if the root exception is a TextErrorException, then ignore any wrappers
        @NotNull Throwable rootCause = ExceptionUtils.getRootCause ( throwable );
        if ( rootCause instanceof TextErrorException )
            throwable = rootCause;
        */

        // DebugUtils.writeNameValue ( "throwable", throwable );

/*
        // TODO remove
        boolean hasBeenHandledByErrorHandler = throwable instanceof InvalidTextException textErrorException
            && textErrorException.hasBeenHandled();

        // don't report the exception again if it has already been handled by a text error handler
        if ( ! hasBeenHandledByErrorHandler ) {
            @NotNull String message = ExceptionUtil.throwableToUserString (
                throwable, CHECKED_EXCEPTION_MESSAGE_SUPPLIER, UNCHECKED_EXCEPTION_MESSAGE_SUPPLIER );
            SimpleLogger.error ( message );
        }
 */
        @NotNull String message = ExceptionUtil.throwableToUserString (
            throwable, CHECKED_EXCEPTION_MESSAGE_SUPPLIER, UNCHECKED_EXCEPTION_MESSAGE_SUPPLIER );
        SimpleLogger.error ( message );

        // return 1;
    }
}
