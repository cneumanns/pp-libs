package dev.pp.parameters.parameters;

import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.nonunion.scalar.impls.booleantype.BooleanDataType;
import dev.pp.parameters.cli.token.NameOrValueToken;
import dev.pp.parameters.cli.token.NameToken;
import dev.pp.parameters.cli.token.ValueToken;
import dev.pp.parameters.parameter.Parameter;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.MutableOrImmutableParameterSpecs;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.basics.annotations.NotNull;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.token.TextToken;

import java.util.List;

public class ParametersUtils {

    private static final @NotNull TextToken DEFAULT_VALUE_TOKEN =
        new TextToken ( BooleanDataType.TRUE_LITERAL, null );

    public static @Nullable Parameters<String> createFromNameOrValueTokens (
        @NotNull List<NameOrValueToken> nameOrValueTokens,
        @Nullable TextToken startToken,
        @NotNull MutableOrImmutableParameterSpecs<?> parameterSpecs ) throws InvalidTextException {

        MutableParameters<String> result = new MutableParameters<> ( startToken );
        @Nullable NameToken pendingNameToken = null;

        for ( NameOrValueToken token : nameOrValueTokens ) {

            if ( token instanceof NameToken nameToken ) {
                if ( pendingNameToken != null ) {
                    // A name without a value is considered to be a boolean flag set to true
                    // e.g. -flag1 -flag2
                    result.add ( Parameter.ofString ( pendingNameToken, DEFAULT_VALUE_TOKEN, null ) );
                }
                if ( ! parameterSpecs.containsName ( nameToken.getText() ) ) {
                    throw new InvalidTextException (
                        "Parameter '" + nameToken.getText() + "' doesn't exist.",
                        "INVALID_PARAMETER",
                        nameToken );
                }
                pendingNameToken = nameToken;

            } else if ( token instanceof ValueToken valueToken ) {
                @Nullable Integer position = valueToken.getPosition();
                if ( position == null ) {
                    if ( pendingNameToken == null ) {
                        throw new InvalidTextException (
                            "Parameter name is required for value '" + valueToken.getText() + "'.",
                            "PARAMETER_NAME_MISSING",
                            valueToken );
                    }
                    @NotNull String name = pendingNameToken.getText();
                    if ( result.containsName ( name ) ) {
                        throw new InvalidTextException (
                            "Parameter '" + name + "' has already been defined.",
                            "PARAMETER_EXISTS_ALREADY",
                            pendingNameToken );
                    }
                    result.add ( Parameter.ofString ( pendingNameToken, valueToken, null ) );

                } else {
                    if ( pendingNameToken != null ) {
                        result.add ( Parameter.ofString ( pendingNameToken, DEFAULT_VALUE_TOKEN, null ) );
                    }
                    int positionalParametersCount = parameterSpecs.positionalParametersCount ();
                    if ( position >= positionalParametersCount ) {
                        throw new InvalidTextException (
                            "Parameter value '" + valueToken.getText() + "' at position " + position + 1 +
                                " is invalid, because there are only " +
                                positionalParametersCount + " positional parameters.",
                            "TOO_MANY_POSITIONAL_PARAMETERS",
                            valueToken );
                    }
                    @Nullable ParameterSpec<?> parameterSpec =
                        parameterSpecs.positionalParameterWithPosition ( position );
                    if ( parameterSpec == null ) {
                        throw new InvalidTextException (
                            "Parameter value '" + valueToken.getText() +
                                "' is invalid, because there is no positional parameter at position " + position + 1 + ".",
                            "INVALID_POSITIONAL_PARAMETER",
                            valueToken );
                    }
                    TextToken nameToken = new TextToken ( parameterSpec.getName(), null );
                    result.add ( Parameter.ofString ( nameToken, valueToken, null ) );
                }
                pendingNameToken = null;

            } else {
                throw new RuntimeException ( "Unexpected NameOrValueToken " + token );
            }
        }

        if ( pendingNameToken != null ) {
            result.add ( Parameter.ofString ( pendingNameToken, DEFAULT_VALUE_TOKEN, null ) );
        }

        return result.makeImmutableOrNull();
    }

    public static <V> @Nullable Parameters<V> merge (
        @NotNull List<? extends MutableOrImmutableParameters<V>> parametersList,
        @NotNull ParameterSpecs<?> parameterSpecs ) {

        MutableParameters<V> result = new MutableParameters<> ();

        for ( MutableOrImmutableParameters<V> parameters : parametersList ) {
            if ( parameters == null ) continue;

            parameters.forEach ( parameter -> {

                // use the parameter's standard name in all cases
                // to avoid double entries when different alternative names are used
                String parameterName = parameter.getName();
                ParameterSpec<?> spec = parameterSpecs.getOrNull ( parameterName );
                String specName = spec == null ? null : spec.getName();
                Parameter<V> parameterToUse =
                    specName == null || specName.equals ( parameterName )
                    ? parameter
                    : parameter.withName ( specName );
                result.addOrReplace ( parameterToUse );
            } );
        }

        return result.makeImmutableOrNull();
    }
}
