package dev.pp.scripting.bindings.core;

import dev.pp.basics.annotations.NotNull;
import dev.pp.scripting.bindings.ScriptingBinding;

import java.io.PrintStream;

/**
 * Functions to write to the current process's standard output device (stdout).
 */

public class OSOutBinding implements ScriptingBinding {

    public static final PrintStream OUT = System.out;


    public OSOutBinding () {}


    public @NotNull String bindingName () { return "stdout"; }

    /**
     * Write a string to stdout.
     * @param string The string to be written
     */
    // @HostAccess.Export
    public void write ( String string ) {
        OUT.print ( string );
    }

    /**
     * Write a string, followed by a new line, to stdout.
     *
     * The new line is OS dependant (LF on Linux, CRLF on Windows).
     * @param string The string to be written
     */
    public void writeLine ( String string ) {
        OUT.println ( string );
    }

    /**
     * Write a line break (new line) to stdout.
     *
     * The line break is OS dependant (LF on Linux, CRLF on Windows).
     */
    public void writeLineBreak() {
        OUT.println();
    }
}
