package dev.pp.text.inspection;

// Not allowed in Java
// public class InvalidDataException <T> extends Exception {

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.message.TextInspectionError;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

public class InvalidDataException extends Exception {

    private final @Nullable Object invalidData;
    public @Nullable Object getInvalidData() { return invalidData; }

    private final @Nullable String errorId;
    public @Nullable String getErrorId() { return errorId; }

    private final @Nullable String invalidTextFragment;
    public @Nullable String getInvalidTextFragment () { return invalidTextFragment; }

    private final @Nullable TextLocation invalidTextLocation;
    public @Nullable TextLocation getInvalidTextLocation () { return invalidTextLocation; }


    public InvalidDataException (
        @NotNull String message,
        @Nullable Object invalidData,
        @Nullable String errorId,
        @Nullable String invalidTextFragment,
        @Nullable TextLocation invalidTextLocation,
        @Nullable Throwable cause ) {

        super ( message, cause );

        this.invalidData = invalidData;
        this.errorId = errorId;
        this.invalidTextFragment = invalidTextFragment;
        this.invalidTextLocation = invalidTextLocation;
    }

    public InvalidDataException (
        @NotNull String message,
        @Nullable String errorId,
        @Nullable String invalidTextFragment,
        @Nullable TextLocation invalidTextLocation,
        @Nullable Throwable cause ) {

        this ( message,
            invalidTextFragment,
            errorId,
            invalidTextFragment,
            invalidTextLocation,
            cause );
    }

    public InvalidDataException (
        @NotNull String message,
        @Nullable String errorId,
        @Nullable String invalidTextFragment,
        @Nullable TextLocation invalidTextLocation ) {

        this ( message,
            invalidTextFragment,
            errorId,
            invalidTextFragment,
            invalidTextLocation,
            null );
    }

    public InvalidDataException (
        @NotNull TextInspectionError textError,
        @Nullable Object invalidData,
        @Nullable Throwable cause ) {

        this ( textError.getMessage(),
            invalidData,
            textError.getId(),
            textError.getTextFragment (),
            textError.getLocation(),
            cause );
    }

    public InvalidDataException (
        @NotNull TextInspectionError textError ) {

        this ( textError.getMessage(),
            textError.getTextFragment (),
            textError.getId(),
            textError.getTextFragment (),
            textError.getLocation(),
            null );
    }

    public InvalidDataException (
        @NotNull String message,
        @Nullable Object invalidData,
        @Nullable String errorId,
        @NotNull TextToken errorToken,
        @Nullable Throwable cause ) {

        this ( message,
            invalidData,
            errorId,
            errorToken.getText(),
            errorToken.getLocation(),
            cause );
    }

    public InvalidDataException (
        @NotNull String message,
        @Nullable String errorId,
        @NotNull TextToken errorToken ) {

        this ( message,
            errorToken.getText(),
            errorId,
            errorToken.getText(),
            errorToken.getLocation(),
            null );
    }

    public @Nullable TextToken invalidTextToken() {
        return invalidTextFragment == null ? null : new TextToken ( invalidTextFragment, invalidTextLocation );
    }
}
