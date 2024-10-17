package dev.pp.scripting.bindings.core;

import dev.pp.basics.annotations.NotNull;
import dev.pp.scripting.bindings.ScriptingBinding;

import java.io.PrintStream;

public class OSErrBinding implements ScriptingBinding {

    public static final PrintStream ERR = System.err;


    public OSErrBinding () {}


    public @NotNull String bindingName () { return "stderr"; }

    public void write ( String string ) {
        ERR.print ( string );
    }

    public void writeLine ( String string ) {
        ERR.println ( string );
    }

    public void writeLineBreak() {
        ERR.println();
    }
}
