package dev.pp.scripting.bindings.core;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSIO;
import dev.pp.scripting.bindings.ScriptingBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class OSInBinding implements ScriptingBinding {

    public static final BufferedReader IN = OSIO.standardInputUTF8Reader ();


    public OSInBinding() {}


    public @NotNull String bindingName () { return "stdin"; }

    public int readInt() throws IOException {
        return IN.read();
    }

    public @Nullable String readLine() throws IOException {
        return IN.readLine();
    }

    public @Nullable String readRest() {

        Scanner s = new Scanner ( IN ).useDelimiter ( "\\A" );
        return s.hasNext() ? s.next() : null;
    }
}
