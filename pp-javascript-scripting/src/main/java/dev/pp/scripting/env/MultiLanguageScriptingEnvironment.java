package dev.pp.scripting.env;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.scripting.bindings.ScriptingBinding;

interface MultiLanguageScriptingEnvironment extends ScriptingEnvironment_OLD_2 {

    void addBinding ( @NotNull String identifier, @NotNull Object object, @NotNull String language );

    default void addBinding ( @NotNull ScriptingBinding binding, @NotNull String language ) {
        addBinding ( binding.bindingName(), binding, language );
    }

    void addDefinitions ( @NotNull String definitions, @NotNull String language ) throws ScriptingException_OLD;

    void executeScript ( @NotNull String script, @NotNull String language ) throws ScriptingException_OLD;

    @Nullable Object evaluateExpression ( @NotNull String expression, @NotNull String language ) throws ScriptingException_OLD;

    default @Nullable String evaluateExpressionToString ( @NotNull String expression, @NotNull String language ) throws ScriptingException_OLD {

        Object value = evaluateExpression ( expression, language );
        return value == null ? null : value.toString();
    }
}
