package dev.pp.basics.utilities.os;

import dev.pp.basics.annotations.NotNull;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class OSIO {

    public static @NotNull BufferedReader standardInputUTF8Reader () {
        return standardInputReader ( StandardCharsets.UTF_8 );
    }

    public static @NotNull BufferedReader standardInputReader ( @NotNull Charset charset ) {
        return new BufferedReader ( new InputStreamReader ( System.in, charset ) );
    }

    public static @NotNull BufferedWriter standardOutputUTF8Writer () {
        return standardOutputWriter ( StandardCharsets.UTF_8 );
    }

    public static @NotNull BufferedWriter standardOutputWriter ( @NotNull Charset charset ) {
        return new BufferedWriter ( new OutputStreamWriter ( System.out, charset ) );
    }

    public static @NotNull BufferedWriter standardErrorUTF8Writer () {
        return standardErrorWriter ( StandardCharsets.UTF_8 );
    }

    public static @NotNull BufferedWriter standardErrorWriter ( @NotNull Charset charset ) {
        return new BufferedWriter ( new OutputStreamWriter ( System.err, charset ) );
    }
}
