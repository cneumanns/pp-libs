package dev.pp.text.utilities.url;

import java.io.*;
import java.net.URL;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.utilities.text.TextLines;

import static dev.pp.basics.utilities.URLUtils.getUTF8URLReader;

public class URLUtils {

    public static @NotNull String getNthLineInURL ( @NotNull URL URL, long n ) throws IOException {

        try ( BufferedReader br = new BufferedReader ( getUTF8URLReader ( URL ) ) ) {
            return TextLines.getNthLineInReader ( br, n );
        }
    }
}
