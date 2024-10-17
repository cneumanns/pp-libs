package dev.pp.scriptingbase.env;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

// TODO? extends ScriptingEnvironment
public interface ScriptingEnvironment extends AutoCloseable {

    @NotNull String getLanguageId();

    void addDefinitions ( @NotNull String definitions )
        throws ScriptingException;

    void executeScript ( @NotNull String script )
        throws ScriptingException;

    @Nullable Object evaluateExpression ( @NotNull String expression )
        throws ScriptingException;

    default @Nullable String evaluateExpressionAsString ( @NotNull String expression )
        throws ScriptingException {

        Object value = evaluateExpression ( expression );
        return value == null ? null : value.toString();
    }
}
