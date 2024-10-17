package dev.pp.parameters.utilities.parameterizedtext.parser;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameters.MutableParameters;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.parameters.utilities.parameterizedtext.reader.ParameterizedTextReader;
import dev.pp.text.token.TextToken;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class JavaProperties_ParameterizedTextParser extends AbstractParameterizedTextParser {


    public JavaProperties_ParameterizedTextParser (
        @NotNull ParameterizedTextReader textReader,
        @NotNull String textParameterName ) {

        super ( textReader, textParameterName );
    }

    public JavaProperties_ParameterizedTextParser() {
        super();
    }

    public @Nullable Parameters<String> parseParameters ( @NotNull TextToken parametersToken )
        throws IOException {

        Properties properties = new Properties();
        properties.load ( new StringReader ( parametersToken.getText() ) );

        MutableParameters<String> parameters = new MutableParameters<> ( parametersToken );
        properties.forEach ( (key, value) -> parameters.add ( key.toString(), value.toString() ) );

        return parameters.makeImmutableOrNull();
    }
}
