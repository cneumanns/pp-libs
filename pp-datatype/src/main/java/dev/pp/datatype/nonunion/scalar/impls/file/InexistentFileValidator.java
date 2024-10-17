package dev.pp.datatype.nonunion.scalar.impls.file;

import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.token.TextToken;
import dev.pp.basics.utilities.file.FilePathUtils;

import java.io.File;
import java.util.function.Function;

public class InexistentFileValidator implements DataValidator<File> {


    public static String DEFAULT_ERROR_ID = "FILE_EXISTS_ALREADY";

    public static Function<File, String> DEFAULT_ERROR_MESSAGE_SUPPLIER = file ->
        "File '" + FilePathUtils.makeAbsoluteAndNormalize ( file.toPath() ) + "' exists already.";


    private final @NotNull String errorId;
    private final @NotNull Function<File, String> errorMessageSupplier;


    public InexistentFileValidator ( @NotNull String errorId, @NotNull Function<File, String> errorMessageSupplier ) {

        this.errorId = errorId;
        this.errorMessageSupplier = errorMessageSupplier;
    }

    public InexistentFileValidator () {
        this ( DEFAULT_ERROR_ID, DEFAULT_ERROR_MESSAGE_SUPPLIER );
    }


    public @NotNull String getErrorId () { return errorId; }

    public @NotNull Function<File, String> getErrorMessageSupplier () { return errorMessageSupplier; }


    public void validate ( @NotNull File file, @Nullable TextToken token ) throws DataValidatorException {

        if ( file.exists() ) throw new DataValidatorException (
            errorMessageSupplier.apply ( file ),
            errorId,
            token, null );
    }
}
