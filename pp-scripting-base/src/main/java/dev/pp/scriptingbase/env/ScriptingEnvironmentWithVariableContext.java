package dev.pp.scriptingbase.env;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

public interface ScriptingEnvironmentWithVariableContext<C> extends AutoCloseable {

    @NotNull String getLanguageId();
    // @Nullable C getDefaultContext();

    void addDefinitions ( @NotNull String definitions ) throws ScriptingException;

    void executeScript (
        @NotNull String script,
        @NotNull C context ) throws ScriptingException;

    @Nullable Object evaluateExpression (
        @NotNull String expression,
        @NotNull C context ) throws ScriptingException;

    default @Nullable String evaluateExpressionToString (
        @NotNull String expression,
        @NotNull C context ) throws ScriptingException {

        Object value = evaluateExpression ( expression, context );
        return value == null ? null : value.toString();
    }

    void close();
}
