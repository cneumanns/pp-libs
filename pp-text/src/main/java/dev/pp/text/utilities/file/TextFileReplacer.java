package dev.pp.text.utilities.file;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringReplacer;

import java.io.IOException;
import java.nio.file.Path;

public class TextFileReplacer {

    public static @Nullable String replaceAll (
        @NotNull Path file,
        @NotNull String toReplace,
        @NotNull String replaceBy,
        @Nullable Integer expectedReplaceCount ) throws IOException {

        @Nullable String text = TextFileReaderUtil.readTextFromUTF8File ( file );
        if ( text == null ) return null;

        String newText = StringReplacer.replaceAll ( text, toReplace, replaceBy, expectedReplaceCount );
        TextFileWriterUtil.writeTextToUTF8File ( newText, file, true );
        return newText;
    }
}
