package dev.pp.text.inspection.handler;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.inspection.message.TextInspectionError;
import dev.pp.text.inspection.message.TextInspectionMessage;
import dev.pp.text.inspection.message.TextInspectionWarning;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

import java.io.IOException;

public interface TextInspectionMessageHandler {

    void handleMessage ( @NotNull TextInspectionMessage message );

    default void handleError (
        @NotNull String message,
        @Nullable String id,
        @Nullable String textFragment,
        @Nullable TextLocation location ) {

        handleMessage ( new TextInspectionError ( message, id, textFragment, location ) );
    }

    default void handleError (
        @NotNull String message,
        @Nullable String id,
        @Nullable TextToken textToken ) {

        handleError ( message, id,
            textToken == null ? null : textToken.getText (),
            textToken == null ? null : textToken.getLocation() );
    }

    default void handleException ( @NotNull Exception exception ) {

        if ( exception instanceof InvalidTextException e ) {
            handleError ( e.getMessage(), e.getErrorId(), e.textToken() );
        } else if ( exception instanceof IOException e ) {
            handleError ( e.getMessage(), "IO_EXCEPTION", null );
        } else {
            handleError ( exception.getMessage(), "EXCEPTION", null );
        }
    }

    default void handleWarning (
        @NotNull String message,
        @Nullable String id,
        @Nullable String textFragment,
        @Nullable TextLocation location ) {

        handleMessage ( new TextInspectionWarning ( message, id, textFragment, location ) );
    }

    default void handleWarning (
        @NotNull String message,
        @Nullable String id,
        @Nullable TextToken textToken ) {

        handleWarning ( message, id,
            textToken == null ? null : textToken.getText (),
            textToken == null ? null : textToken.getLocation() );
    }

    @Nullable TextInspectionError firstError();
    @Nullable TextInspectionError lastError();

/*
    @Nullable TextError firstWarning();
    @Nullable TextError lastWarning();

    @Nullable TextError firstErrorOrWarning();
    @Nullable TextError lastErrorOrWarning();

    boolean hasErrors();
    boolean hasWarnings();
*/

    @Nullable TextInspectionError newestErrorAfterInitialLastError ( @Nullable TextInspectionError initialLastError );
    boolean hasNewErrors ( @Nullable TextInspectionError initialLastError );

/*
    void setErrorMark();
    @Nullable TextError firstErrorAfterMark();
*/
}
