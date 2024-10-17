package dev.pp.datatype.nonunion.scalar.impls.filesystempath;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.nonunion.scalar.ScalarDataType;
import dev.pp.datatype.nonunion.scalar.impls.nulltype.NullDataType;
import dev.pp.datatype.utils.parser.DataParserException;
import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.text.location.TextLocation;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.function.Supplier;

public class DirectoryOrFilePathDataType extends ScalarDataType<Path> {


    public static final @NotNull DirectoryOrFilePathDataType DEFAULT = new DirectoryOrFilePathDataType (
        "directory_or_file_path",
        null,
        () -> new SimpleDocumentation (
            "Directory or File Path",
            "An absolute or relative file or directory path.",
            "docs/health/diet.pml" ) );


    public DirectoryOrFilePathDataType (
        @NotNull String name,
        @Nullable DataValidator<Path> validator,
        @Nullable Supplier<SimpleDocumentation> documentation ) {

        super ( name, validator, documentation );
    }


    @NotNull public Path parse (
        @Nullable String string,
        @Nullable TextLocation location ) throws DataParserException {

        NullDataType.checkNotNullString ( string, location );
        assert string != null;

        try {
            return Path.of ( string );
        } catch ( InvalidPathException e ) {
            throw new DataParserException (
                "'" + string + "' is an invalid file path. Reason: " + e.getMessage(),
                "INVALID_FILE_PATH",
                string, location, e );
        }
    }

    public  @NotNull String objectToString ( @Nullable Path path ) {
        return path != null ? path.toAbsolutePath().normalize().toString() : NullDataType.NULL_STRING;
    }
}
