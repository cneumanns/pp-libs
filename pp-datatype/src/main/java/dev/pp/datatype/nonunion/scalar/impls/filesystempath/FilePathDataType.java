package dev.pp.datatype.nonunion.scalar.impls.filesystempath;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.text.documentation.SimpleDocumentation;

import java.nio.file.Path;
import java.util.function.Supplier;

public class FilePathDataType extends DirectoryOrFilePathDataType {

    public static final @NotNull FilePathDataType DEFAULT = new FilePathDataType (
        "file_path",
        null,
        () -> new SimpleDocumentation (
            "File Path",
            "An absolute or relative file path.",
            "docs/health" ) );


    public FilePathDataType (
        @NotNull String name,
        @Nullable DataValidator<Path> validator,
        @Nullable Supplier<SimpleDocumentation> documentation ) {

        super ( name, validator, documentation );
    }
}
