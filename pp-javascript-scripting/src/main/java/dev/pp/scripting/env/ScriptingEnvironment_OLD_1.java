package dev.pp.scripting.env;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.Map;

@Deprecated
public interface ScriptingEnvironment_OLD_1 {

    void addDefinitions (
        @NotNull String languageID,
        @NotNull String code,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess );

    void executeScript (
        @NotNull String languageID,
        @NotNull String script,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess );

    @Nullable String evaluateExpressionToString (
        @NotNull String languageID,
        @NotNull String expression,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess );
}
