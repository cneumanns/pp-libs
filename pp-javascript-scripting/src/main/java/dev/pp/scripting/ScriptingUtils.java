package dev.pp.scripting;

import java.util.Map;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

public class ScriptingUtils {

    // Note: Currently (2021-09) only Javascript is well supported in GraalVM

    public static @Nullable String evaluateJavascriptStringExpression (
        @NotNull String expression,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        return evaluateStringExpression ( JavaScriptConstants.LANGUAGE_ID, expression, bindings, allowAllAccess );
    }

    public static @Nullable String evaluateStringExpression (
        @NotNull String languageID,
        @NotNull String expression,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        try ( Context context = createContext ( allowAllAccess, bindings, languageID ) ) {

            @NotNull Value value = evaluate ( context, languageID, expression );
            // DebugUtils.writeNameValue ( "value", value );
            assert value.isString() || value.isNull();
            return value.asString();
        }
    }

    public static <T> T evaluateJavascriptExpression (
        @NotNull String expression,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        return evaluateExpression ( JavaScriptConstants.LANGUAGE_ID, expression, bindings, allowAllAccess );
    }

    @SuppressWarnings ( "unchecked" )
    public static <T> T evaluateExpression (
        @NotNull String languageID,
        @NotNull String expression,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        return (T) evaluateExpressionAsObject ( languageID, expression, bindings, allowAllAccess );
    }

    public static @Nullable Object evaluateJavascriptExpressionAsObject (
        @NotNull String expression,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        return evaluateExpressionAsObject ( JavaScriptConstants.LANGUAGE_ID, expression, bindings, allowAllAccess );
    }

    public static @Nullable Object evaluateExpressionAsObject (
        @NotNull String languageID,
        @NotNull String expression,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        try ( Context context = createContext ( allowAllAccess, bindings, languageID ) ) {

            @NotNull Value value = evaluate ( context, languageID, expression );
            return value.as ( Object.class );
        }
    }

/*
    public static @NotNull Value evaluateJavascriptExpression (
        @NotNull String expression,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        return evaluateExpression ( JAVASCRIPT_LANGUAGE_ID, expression, bindings, allowAllAccess );
    }

    public static @NotNull Value evaluateExpression (
        @NotNull String languageID,
        @NotNull String expression,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        try ( Context context = createContext ( allowAllAccess, bindings, languageID ) ) {

            return evaluate ( context, languageID, expression );
        }
    }

 */

    public static void executeJavascriptScript (
        @NotNull String script,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        executeScript ( JavaScriptConstants.LANGUAGE_ID, script, bindings, allowAllAccess );
    }

    public static void executeScript (
        @NotNull String languageID,
        @NotNull String script,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        try ( Context context = createContext ( allowAllAccess, bindings, languageID ) ) {
            evaluate ( context, languageID, script );
        }
    }

/*
    public static @Nullable Object executeJavascriptScriptWithReturn (
        @NotNull String script,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        return executeScriptWithReturn ( JAVASCRIPT_LANGUAGE_ID, script, bindings, allowAllAccess );
    }

    public static @Nullable Object executeScriptWithReturn (
        @NotNull String languageID,
        @NotNull String script,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        try ( Context context = createContext ( allowAllAccess, bindings, languageID ) ) {

            @NotNull Value value = evaluate ( context, languageID, script );
            return value.as ( Object.class );
        }
    }
 */

    public static @NotNull Value evaluate (
        @NotNull Context context,
        @NotNull String languageID,
        @NotNull String script ) {

        // TODO? check exception
        return context.eval ( languageID, script );
    }

    public static @NotNull Value parse (
        @NotNull String languageID,
        @NotNull String code,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        try ( Context context = createContext ( allowAllAccess, bindings, languageID ) ) {

            // TODO? check exception
            return context.parse ( languageID, code );
        }
    }

    public static Context createContext (
        boolean allowAllAccess,
        @Nullable Map<String, Object> bindings,
        @NotNull String languageId ) {

        Context context = createContext ( allowAllAccess );
        setBindings ( context, languageId, bindings );
        return context;
    }

    private static Context createContext ( boolean allowAllAccess ) {

        return Context.newBuilder()
            .option ( "engine.WarnInterpreterOnly", "false" ) // see Warning: Implementation does not support runtime compilation.
            // at https://www.graalvm.org/reference-manual/js/FAQ/#errors
            .allowAllAccess ( allowAllAccess )
            // TODO?
            // .in ( System.in )
            // .out ( System.out )
            // .err ( System.err )
            .build();
    }

    private static void setBindings (
        @NotNull Context context,
        @NotNull String languageId,
        @Nullable Map<String, Object> bindings ) {

        if ( bindings == null ) return;

        Value contextBindings = context.getBindings ( languageId );
        for ( var entry : bindings.entrySet () ) {
            contextBindings.putMember ( entry.getKey(), entry.getValue() );
        }
    }

/*
    public static void executeJavascriptWithBindings (
        @NotNull String script,
        @NotNull Map<String, Object> bindings,
        @NotNull TextErrorHandler errorHandler ) {

        try {
            executeJavascriptScript ( script, bindings, true );
        } catch ( PolyglotException | IllegalStateException | IllegalArgumentException e ) {
            errorHandler.handleAbortingError (
                "SCRIPTING_ERROR",
                "The following error occurred when a script was executed: " + e.getMessage(),
                null );
        }
    }
*/
}
