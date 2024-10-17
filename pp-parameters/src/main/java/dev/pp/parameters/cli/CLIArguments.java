package dev.pp.parameters.cli;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.cli.token.NameOrValueToken;
import dev.pp.parameters.cli.token.NameToken;
import dev.pp.parameters.cli.token.ValueToken;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.parameters.parameters.ParametersUtils;
import dev.pp.parameters.parameterspecs.MutableOrImmutableParameterSpecs;
import dev.pp.parameters.parameters.ParametersCreator;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.token.TextToken;

import java.util.ArrayList;
import java.util.List;

public class CLIArguments {

    public static <V> @Nullable Parameters<V> parseToParameters (
        @NotNull String[] cliStrings,
        int cliStringsStartIndex,
        @Nullable TextToken startToken,
        @Nullable MutableOrImmutableParameterSpecs<V> parameterSpecs ) throws InvalidTextException {

        @Nullable Parameters<String> stringParameters = parseToStringParameters (
            cliStrings, cliStringsStartIndex, startToken, parameterSpecs );

        return ParametersCreator.createFromStringParameters ( stringParameters, startToken, parameterSpecs );
    }

    public static @Nullable Parameters<String> parseToStringParameters (
        @NotNull String[] cliStrings,
        int cliStringsStartIndex,
        @Nullable TextToken startToken,
        @Nullable MutableOrImmutableParameterSpecs<?> parameterSpecs ) throws InvalidTextException {

        if ( cliStringsStartIndex > (cliStrings.length - 1) ) {
            return null;
        }
        @Nullable List<NameOrValueToken> nameOrValueTokens = parseToNameOrValueTokens ( cliStrings, cliStringsStartIndex );
        if ( nameOrValueTokens == null ) {
            return null;
        }
        if ( parameterSpecs == null ) {
            throw new InvalidTextException (
                "Parameters are not allowed in this context.",
                "INVALID_PARAMETERS",
                startToken );
        }
        return ParametersUtils.createFromNameOrValueTokens ( nameOrValueTokens, startToken, parameterSpecs );
    }

    public static @Nullable List<NameOrValueToken> parseToNameOrValueTokens (
        @NotNull String[] cliArguments,
        int startIndex ) throws InvalidTextException {

        if ( startIndex < 0 || startIndex >= cliArguments.length ) throw new IllegalArgumentException (
            "startIndex (" + startIndex + ") out of range 0.." + (cliArguments.length - 1) + "." );

        List<NameOrValueToken> result = new ArrayList<>();

        boolean previousWasName = false;
        int currentValuePosition = 0;
        boolean parsingOnlyValues = false;

        for ( int i = startIndex; i < cliArguments.length; i++ ) {
            @Nullable String CLIString = cliArguments[ i ];
            if ( CLIString == null ) CLIString = "";

            if ( CLIString.equals ( "--" ) ) {
                parsingOnlyValues = true;
                previousWasName = false;
                continue;
            }

            // - is commonly used for file parameters to use STDIN or STDOUT instead
            boolean isValue = parsingOnlyValues ||
                CLIString.equals ( "-" ) ||
                ! CLIString.startsWith ( "-" ) ;

            // if ( ! parsingOnlyValues && CLIString.startsWith ( "-" ) ) {
            if ( ! isValue ) {

                int CLIStringStartIndex = CLIString.startsWith ( "--" ) ? 2 : 1;
                // if ( CLIStringStartIndex >= CLIString.length() ) throw new CLIArgumentsException (
                //    "'" + CLIString + "' is invalid. Name missing after '" + CLIString + "'." );
                assert CLIStringStartIndex < CLIString.length();
                String unprefixedCLIString = CLIString.substring ( CLIStringStartIndex );
                int assignmentIndex = unprefixedCLIString.indexOf ( '=' );
                if ( assignmentIndex == -1 ) {
                    result.add ( new NameToken ( unprefixedCLIString, null ) );
                    previousWasName = true;
                } else{
                    if ( assignmentIndex == 0 ) {
                        // --=value
                        throw new InvalidTextException (
                            "'" + CLIString + "' is invalid. Argument name missing before '='.",
                            "INVALID_CLI_ARGUMENTS",
                            new TextToken ( CLIString ) );
                    } else if ( assignmentIndex == unprefixedCLIString.length() - 1 ) {
                        // --name=
                        String name = unprefixedCLIString.substring ( 0, assignmentIndex );
                        result.add ( new NameToken ( name, null ) );
                        result.add ( new ValueToken ( "", null, null ) );
                    } else {
                        // --name=value
                        String name = unprefixedCLIString.substring ( 0, assignmentIndex );
                        String value = unprefixedCLIString.substring ( assignmentIndex + 1 );
                        result.add ( new NameToken ( name, null ) );
                        result.add ( new ValueToken ( value, null, null ) );
                    }
                    previousWasName = false;
                }

            } else {
                result.add ( new ValueToken ( CLIString, null, previousWasName ? null : currentValuePosition ) );
                if ( ! previousWasName ) currentValuePosition++;
                previousWasName = false;
            }
        }

        return result.isEmpty() ? null : result;
    }
}
