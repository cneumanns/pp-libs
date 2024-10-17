package dev.pp.datatype.nonunion.scalar.impls.file;

/*
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.file.FilePathUtils;
import dev.pp.datatype.nonUnion.scalar.ScalarDataType;
import dev.pp.datatype.nonUnion.scalar.ScalarDataTypeDep;
import dev.pp.datatype.nonUnion.scalar.impls.Null.NullDataType;
import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.datatype.utils.validator.InvalidTextDataException;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.text.token.TextToken;

import java.io.File;
import java.nio.file.Path;
*/

public class FileDataType {}

/*
public class FileDataType extends ScalarDataType<Path> {


    public static final @NotNull FileDataType DEFAULT = new FileDataType ();



    private FileDataType () {}


    public @NotNull String getName() { return "file"; }

    public @Nullable SimpleDocumentation getDocumentation() { return new SimpleDocumentation (
        "File",
        "An absolute or relative file path. It the path is relative, it is relative to the current working directory.",
        "docs/health/diet.pml" ); }

    public @Nullable DataValidator<File> getValidator() { return null; }

    @NotNull public Path parse (
        @Nullable String string,
        @Nullable TextToken token ) throws InvalidTextDataException {

        NullDataType.checkNotNullString ( string, token );
        assert string != null;

        return Path.of ( string );
    }

    public @NotNull String objectToString ( @Nullable File file ) {
        return file != null ? FilePathUtils.getAbsoluteOSPath ( file ) : NullDataType.NULL_STRING;
    }
}
*/
