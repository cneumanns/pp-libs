package dev.pp.scripting.bindings.core;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.process.OSCommand;
import dev.pp.scripting.bindings.ScriptingBinding;

import java.io.IOException;

public class OSCommandBinding implements ScriptingBinding {

    public OSCommandBinding() {}


    public @NotNull String bindingName () { return "OSCommand"; }

    public int runAndWait ( @NotNull String[] OSCommandTokens ) throws IOException, InterruptedException {

        return OSCommand.runAndWait ( OSCommandTokens );
    }

    public void startAndContinue ( @NotNull String[] OSCommandTokens ) throws IOException {

        OSCommand.startAndContinue ( OSCommandTokens );
    }

    public String textPipe (
        @NotNull String[] OSCommandTokens,
        @Nullable String input ) throws IOException, InterruptedException {

        return OSCommand.textPipe ( OSCommandTokens, input );
    }

    /* TODO
    public int filePipe (
        @NotNull String inputFile,
        @NotNull String OScommand,
        @Nullable List<String> arguments,
        @NotNull String outputFile ) {
    }
    */
}
