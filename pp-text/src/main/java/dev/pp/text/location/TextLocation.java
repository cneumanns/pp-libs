package dev.pp.text.location;

import dev.pp.text.resource.File_TextResource;
import dev.pp.text.resource.TextResource;
import dev.pp.text.resource.URL_TextResource;
import dev.pp.basics.utilities.file.FilePathUtils;
import dev.pp.basics.utilities.string.StringBuilderUtils;
import dev.pp.basics.utilities.string.StringUtils;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.text.utilities.text.TextMarker;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TextLocation {


    public final static String DEFAULT_INLINE_MARKER = " ==> ";
    public final static TextLocation UNKNOWN_LOCATION = new TextLocation (
        (TextResource) null, 0, 0, null);


    // private final @Nullable Object resource;
    private final @Nullable TextResource resource;
    private final long lineNumber;
    private final long columnNumber;
    private final @Nullable TextLocation parentLocation;


    public TextLocation (
        @Nullable TextResource resource,
        long lineNumber,
        long columnNumber,
        @Nullable TextLocation parentLocation ) {

        this.resource = resource;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.parentLocation = parentLocation;
    }

    public TextLocation (
        @NotNull Path filePath,
        long lineNumber,
        long columnNumber,
        @Nullable TextLocation parentLocation ) {

        this ( new File_TextResource ( filePath ), lineNumber, columnNumber, parentLocation );
    }

    public TextLocation (
        @NotNull URL URL,
        long lineNumber,
        long columnNumber,
        @Nullable TextLocation parentLocation ) {

        this ( new URL_TextResource ( URL ), lineNumber, columnNumber, parentLocation );
    }


    public @Nullable TextResource getResource() { return resource; }

    public long getLineNumber() { return lineNumber; }

    public long getColumnNumber() { return columnNumber; }

    public @Nullable TextLocation getParentLocation() { return parentLocation; }


    // resource

    public @Nullable Path getResourceAsFilePath () {

        if ( resource != null ) {
            return resource.getResourceAsFilePath ();
        } else {
            return null;
        }
    }

    public @Nullable String getResourceName() { return resource == null ? null : resource.getName(); }


    // text line

    public @Nullable String getTextLine () throws Exception {

        if ( resource != null ) {
            return resource.getTextLine ( lineNumber );
        } else {
            return null;
        }
    }

    public @Nullable String getTextLineWithInlineMarker ( @NotNull String marker ) throws Exception {

        String line = getTextLine();
        if ( line == null ) return null;
        return TextMarker.insertInlineMarker ( line, (int) columnNumber, marker );
    }

    public @Nullable String getTextLineWithInlineMarker () throws Exception {

        return getTextLineWithInlineMarker ( DEFAULT_INLINE_MARKER );
    }

    public @Nullable String getTextLineWithMarkerLine (
        char markerChar,
        int markerLength,
        boolean removeIndent,
        @Nullable Integer maxCharsInLine ) throws Exception {

        return null;
    }


    // parent location

    public boolean isRootLocation() { return parentLocation == null; }

    public @Nullable List<TextLocation> getParentLocations() {

        if ( parentLocation == null ) return null;

        List<TextLocation> list = new ArrayList<>();
        TextLocation parent = parentLocation;
        while ( parent != null ) {
            list.add ( parent );
            parent = parent.getParentLocation();
        }

        return list;
    }

    public @Nullable String parentLocationsToString ( boolean includeTextLine ) {

        if ( parentLocation == null ) return null;

        StringBuilder sb = new StringBuilder();
        List<TextLocation> list = getParentLocations();
        assert list != null;
        for ( TextLocation parent : list ) {
            sb.append ( parent.toString ( includeTextLine, false ) );
            sb.append ( StringConstants.OS_LINE_BREAK );
        }
        StringBuilderUtils.removeOptionalLineBreakAtEnd ( sb );
        return sb.toString();
    }


    // toString

    public @NotNull String toString ( boolean includeTextLine, boolean includeParentLocations ) {

        StringBuilder sb = new StringBuilder();

        Path file = getResourceAsFilePath ();
        if ( file != null ) {
            sb.append ( file.getFileName() ); // only name, no directory
            sb.append ( " (" );
            sb.append ( FilePathUtils.makeAbsoluteAndNormalize ( file ) );
            sb.append ( ")" );
            sb.append ( StringConstants.OS_LINE_BREAK );
        } else if ( resource != null ) {
            sb.append ( resource.getName() );
            sb.append ( StringConstants.OS_LINE_BREAK );
        }

        if ( lineNumber != 0 || columnNumber != 0 ) {

            if ( lineNumber != 0 ) {
                sb.append ( "Line " );
                sb.append ( lineNumber );
            }

            if ( columnNumber != 0 ) {
                if ( lineNumber != 0 ) {
                    sb.append ( ", " );
                    sb.append ( "column " );
                } else {
                    sb.append ( "Column " );
                }
                sb.append ( columnNumber );
            }

            sb.append ( StringConstants.OS_LINE_BREAK );
        }

        if ( includeTextLine ) {
            String line;
            try {
                line = getTextLineWithInlineMarker ();
            } catch ( Exception e ) {
                line = null;
            }
            if ( line != null ) {
                sb.append ( line );
                sb.append ( StringConstants.OS_LINE_BREAK );
            }
        }

        if ( includeParentLocations ) {
            String parentLocations = parentLocationsToString ( includeTextLine );
            if ( parentLocations != null ) {
                sb.append ( "Call stack:" );
                sb.append ( StringConstants.OS_LINE_BREAK );
                sb.append ( parentLocations );
            }
        }

        StringBuilderUtils.removeOptionalLineBreakAtEnd ( sb );

        return sb.toString();
    }

    public @NotNull String toString() { return toString ( true, true ); }

    public @NotNull String toLogString () {

        StringBuilder sb = new StringBuilder();

        sb.append ( '"' );
        if ( resource != null ) {
            sb.append ( StringUtils.replaceQuoteWith2Quotes ( resource.getName() ) );
        }
        sb.append ( '"' );

        sb.append ( ",l " );
        sb.append ( lineNumber );

        sb.append ( ",c " );
        sb.append ( columnNumber );

        return sb.toString();
    }
}
