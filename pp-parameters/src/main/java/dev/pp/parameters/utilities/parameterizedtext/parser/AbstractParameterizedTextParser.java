package dev.pp.parameters.utilities.parameterizedtext.parser;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.utilities.file.TextFileReaderUtil;
import dev.pp.parameters.parameters.MutableParameters;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.parameters.utilities.parameterizedtext.reader.Fences_ParameterizedTextReader;
import dev.pp.parameters.utilities.parameterizedtext.reader.ParameterizedTextComponents;
import dev.pp.parameters.utilities.parameterizedtext.reader.ParameterizedTextReader;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.resource.File_TextResource;
import dev.pp.text.resource.String_TextResource;
import dev.pp.text.resource.TextResource;
import dev.pp.text.token.TextToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;

public abstract class AbstractParameterizedTextParser {


    public static final @NotNull String DEFAULT_TEXT_PARAMETER_NAME = "text";


    private final @NotNull ParameterizedTextReader textReader;
    private final @NotNull String textParameterName;


    protected AbstractParameterizedTextParser (
        @NotNull ParameterizedTextReader textReader,
        @NotNull String textParameterName ) {

        this.textReader = textReader;
        this.textParameterName = textParameterName;
    }

    protected AbstractParameterizedTextParser () {
        this ( new Fences_ParameterizedTextReader(), DEFAULT_TEXT_PARAMETER_NAME );
    }


    public @Nullable Parameters<String> parse (
        @NotNull Reader reader,
        @NotNull TextResource resource,
        @Nullable Integer lineOffset,
        @Nullable Integer columnOffset ) throws IOException, InvalidTextException {

        ParameterizedTextComponents components = textReader.read ( reader, resource, lineOffset, columnOffset );

        @Nullable TextToken parametersToken = components.parameters();
        MutableParameters<String> parameters = new MutableParameters<> ( parametersToken );
        if ( parametersToken != null ) {
            Parameters<String> frontMatterParameters = parseParameters ( parametersToken );
            if ( frontMatterParameters != null ) {
                parameters.addAll ( frontMatterParameters );
            }
        }

        @Nullable TextToken textToken = components.text();
        if ( parameters.containsName ( textParameterName ) ) {
            throw new InvalidTextException (
                "Invalid parameter '" + textParameterName + "'. This parameter is implicitly defined by the text block, and can therefore not be specified explicitly.",
                "DUPLICATE_PARAMETER",
                textToken );
        }
        parameters.add (
            textParameterName,
            textToken == null ? null : textToken.getText(),
            null, null,
            textToken == null ? null : textToken.getLocation() );

        return parameters.makeImmutableOrNull();
    }

    public @Nullable Parameters<String> parseFile (
        @NotNull Path filePath ) throws IOException, InvalidTextException {

        try ( FileReader reader = TextFileReaderUtil.createUTF8FileReader ( filePath ) ) {
            return parse ( reader, new File_TextResource ( filePath ), null, null );
        }
    }

    public @Nullable Parameters<String> parseString (
        @NotNull String string ) throws IOException, InvalidTextException {

        try ( Reader reader = new StringReader ( string ) ) {
            return parse ( reader, new String_TextResource ( string ), null, null );
        }
    }

    public abstract @Nullable Parameters<String> parseParameters ( @NotNull TextToken parameters )
        throws IOException, InvalidTextException;
}
