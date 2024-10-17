package dev.pp.scripting.env;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.scripting.bindings.ScriptingBinding;

@Deprecated
public interface ScriptingEnvironment_OLD_2 {

    @NotNull String getLanguageName();

    void addBinding ( @NotNull String identifier, @NotNull Object object );
    default void addBinding ( @NotNull ScriptingBinding binding ) {
        addBinding ( binding.bindingName(), binding );
    }

    void addDefinitions ( @NotNull String definitions ) throws ScriptingException_OLD;

    void executeScript ( @NotNull String script ) throws ScriptingException_OLD;

    // <T> @Nullable T evaluateExpression ( @NotNull String expression ) throws ScriptingException;
    @Nullable Object evaluateExpression ( @NotNull String expression ) throws ScriptingException_OLD;

    default @Nullable String evaluateExpressionToString ( @NotNull String expression ) throws ScriptingException_OLD {

        Object value = evaluateExpression ( expression );
        return value == null ? null : value.toString();
    }

    void close();
}
