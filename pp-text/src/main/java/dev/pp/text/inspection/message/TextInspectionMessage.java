package dev.pp.text.inspection.message;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.basics.utilities.string.StringUtils;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

import java.nio.file.Path;

public abstract class TextInspectionMessage {


    protected final @NotNull String message;
    public @NotNull String getMessage() { return message; }

    protected final @Nullable String id;
    public @Nullable String getId() { return id; }

    protected final @Nullable String textFragment;
    public @Nullable String getTextFragment () { return textFragment; }

    protected final @Nullable TextLocation location;
    public @Nullable TextLocation getLocation() { return location; }


    protected TextInspectionMessage (
        @NotNull String message,
        @Nullable String id,
        @Nullable String textFragment,
        @Nullable TextLocation location ) {

        this.message = message;
        this.id = id;
        this.textFragment = textFragment;
        this.location = location;
    }

    protected TextInspectionMessage (
        @NotNull String message,
        @Nullable String id,
        @Nullable TextToken token ) {

        this ( message,
            id,
            token == null ? null : token.getText(),
            token == null ? null : token.getLocation() );
    }


    public abstract @NotNull String label();

    public @Nullable TextToken token() {
        return textFragment == null ? null : new TextToken ( textFragment, location );
    }

    public @Nullable Path filePathOfLocation() {

        if ( location == null ) {
            return null;
        } else {
            return location.getResourceAsFilePath();
        }
    }

    public @Nullable String textLine() throws Exception {

        if ( location == null ) {
            return null;
        } else {
            return location.getTextLine();
        }
    }

    public @Nullable String textLineWithInlineMarker() throws Exception {

        if ( location == null ) {
            return null;
        } else {
            return location.getTextLineWithInlineMarker();
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append ( label() );
        sb.append ( ":" );
        sb.append ( StringConstants.OS_LINE_BREAK );

        sb.append ( message );

        if ( id != null ) {
            sb.append ( " [" );
            sb.append ( id );
            sb.append ( "]" );
        }

        sb.append ( StringConstants.OS_LINE_BREAK );

        if ( location != null ) sb.append ( location );

        return sb.toString();
    }

    public @NotNull String toLogString() {

        StringBuilder sb = new StringBuilder();

        @Nullable TextLocation location_ = location != null ? location : TextLocation.UNKNOWN_LOCATION;
        sb.append ( location_.toLogString() );

        sb.append ( "," );
        sb.append ( label().charAt ( 0 ) );

        sb.append ( ",id " );
        sb.append ( id );

        sb.append ( ",\"" );
        sb.append ( StringUtils.replaceQuoteWith2Quotes ( message ) );
        sb.append ( '"' );

        return sb.toString();
    }
}
