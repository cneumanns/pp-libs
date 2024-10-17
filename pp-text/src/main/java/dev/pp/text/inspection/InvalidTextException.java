package dev.pp.text.inspection;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.message.TextInspectionError;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

// TODO?  inherit from InvalidDataException
public class InvalidTextException extends Exception {

    private final @Nullable String errorId;
    public @Nullable String getErrorId() { return errorId; }

    private final @Nullable String invalidTextFragment;
    public @Nullable String getInvalidTextFragment() { return invalidTextFragment; }

    private final @Nullable TextLocation invalidTextLocation;
    public @Nullable TextLocation getInvalidTextLocation() { return invalidTextLocation; }


    public InvalidTextException (
        @NotNull String message,
        @Nullable String errorId,
        @Nullable String invalidTextFragment,
        @Nullable TextLocation invalidTextLocation,
        @Nullable Throwable cause ) {

        super ( message, cause );

        this.errorId = errorId;
        this.invalidTextFragment = invalidTextFragment;
        this.invalidTextLocation = invalidTextLocation;
    }

    public InvalidTextException (
        @NotNull String message,
        @Nullable String errorId,
        @Nullable TextToken invalidTextToken,
        @Nullable Throwable cause ) {

        this ( message,
            errorId,
            invalidTextToken == null ? null : invalidTextToken.getText(),
            invalidTextToken == null ? null : invalidTextToken.getLocation(),
            cause );
    }

    public InvalidTextException (
        @NotNull String message,
        @Nullable String errorId,
        @Nullable TextToken invalidTextToken ) {

        this ( message, errorId, invalidTextToken, null );
    }

    public InvalidTextException (
        @NotNull TextInspectionError textInspectionError,
        @Nullable Throwable cause ) {

        this ( textInspectionError.getMessage(),
            textInspectionError.getId(),
            textInspectionError.getTextFragment(),
            textInspectionError.getLocation(),
            cause );
    }

    public InvalidTextException (
        @NotNull TextInspectionError textInspectionError ) {

        this ( textInspectionError, null );
    }


    public @Nullable TextToken textToken() {
        return invalidTextFragment == null ? null : new TextToken ( invalidTextFragment, invalidTextLocation );
    }

    public @NotNull TextInspectionError textInspectionError() {
        return new TextInspectionError ( getMessage(), errorId, invalidTextFragment, invalidTextLocation );
    }

    public @NotNull String toString() {
        return textInspectionError ().toString();
    }
}
