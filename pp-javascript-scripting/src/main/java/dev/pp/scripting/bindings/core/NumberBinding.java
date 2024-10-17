package dev.pp.scripting.bindings.core;

import dev.pp.basics.annotations.NotNull;
import dev.pp.scripting.bindings.ScriptingBinding;

import java.text.DecimalFormat;

/**
 * Utility functions to work with numbers.
 */
public class NumberBinding implements ScriptingBinding {

    public NumberBinding () {}


    public @NotNull String bindingName () { return "numberUtils"; }

    /**
     * Format and round a floating point number.
     * @param number The floating point number to be formatted.
     * @param format The format to apply.
     * Please refer to https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/text/DecimalFormat.html for more information.
     * @return A string representing the formatted and rounded floating point number.
     */
    public @NotNull String formatFloat ( double number, @NotNull String format ) {

        DecimalFormat df = new DecimalFormat ( format );
        return df.format ( number );
    }
}
