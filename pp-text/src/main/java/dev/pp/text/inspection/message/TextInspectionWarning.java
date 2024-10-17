package dev.pp.text.inspection.message;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

public class TextInspectionWarning extends TextInspectionMessage {

    public static final @NotNull String LABEL = "Warning";


    public TextInspectionWarning (
        @NotNull String message,
        @Nullable String id,
        @Nullable String textFragment,
        @Nullable TextLocation location ) {

        super ( message, id, textFragment, location );
    }

    public TextInspectionWarning (
        @NotNull String message,
        @Nullable String id,
        @Nullable TextToken token ) {

        super ( message, id, token );
    }


    public @NotNull String label() { return LABEL; }
}
