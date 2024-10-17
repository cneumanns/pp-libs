package dev.pp.datatype.nonunion.scalar.impls.filesystempath;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.text.documentation.SimpleDocumentation;

import java.nio.file.Path;
import java.util.function.Supplier;

public class DirectoryPathDataType extends DirectoryOrFilePathDataType {

    public static final @NotNull DirectoryPathDataType DEFAULT = new DirectoryPathDataType (
        "directory_path",
        null,
        () -> new SimpleDocumentation (
            "Directory Path",
            "An absolute or relative directory path.",
            "docs/health" ) );


    public DirectoryPathDataType (
        @NotNull String name,
        @Nullable DataValidator<Path> validator,
        @Nullable Supplier<SimpleDocumentation> documentation ) {

        super ( name, validator, documentation );
    }
}
