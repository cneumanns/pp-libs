package dev.pp.scriptingbase.env;

import dev.pp.basics.annotations.Nullable;

public interface ScriptingEnvironmentWithFixedContext<C> extends ScriptingEnvironment {
// public interface ScriptingEnvironmentWithContext<C> {

    @Nullable C getContext();

/*
    void addDefinitions ( @NotNull String definitions )
        throws ScriptingException;

    void executeScript ( @NotNull String script )
        throws ScriptingException;

    @Nullable Object evaluateExpression ( @NotNull String expression )
        throws ScriptingException;

    default @Nullable String evaluateExpressionToString ( @NotNull String expression )
        throws ScriptingException {

        Object value = evaluateExpression ( expression );
        return value == null ? null : value.toString();
    }

    void close();
 */
}
