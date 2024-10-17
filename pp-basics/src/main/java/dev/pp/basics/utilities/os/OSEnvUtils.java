package dev.pp.basics.utilities.os;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.Map;

public class OSEnvUtils {

    public static @NotNull Map<String,String> getAll() {

        return System.getenv();
    }

    public static @Nullable String getVarOrNull ( @NotNull String varName ) {

        return System.getenv ( varName );
    }

    public static @NotNull String getVarOrDefault ( @NotNull String varName, @NotNull String defaultValue ) {

        @Nullable String result = getVarOrNull ( varName );
        return result != null ? result : defaultValue;
    }

    public static @NotNull String getVar ( @NotNull String varName ) {

        @Nullable String result = getVarOrNull ( varName );
        if ( result != null ) {
            return result;
        } else {
            throw new IllegalArgumentException ( "OS environment variable '" + varName + "' doesn't exist." );
        }
    }
}
