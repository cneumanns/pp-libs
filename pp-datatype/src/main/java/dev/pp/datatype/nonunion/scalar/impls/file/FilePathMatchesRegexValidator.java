package dev.pp.datatype.nonunion.scalar.impls.file;

import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.token.TextToken;
import dev.pp.basics.utilities.file.FilePathUtils;
import dev.pp.basics.utilities.string.StringUtils;

import java.io.File;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class FilePathMatchesRegexValidator implements DataValidator<File> {


    public static String DEFAULT_ERROR_ID = "INVALID_FILE_PATH";

    public static BiFunction<File, Pattern, String> DEFAULT_ERROR_MESSAGE_SUPPLIER = ( file, pattern) ->
        "File '" + FilePathUtils.makeAbsoluteAndNormalize ( file.toPath() ) + "' does not match the regular expression '" + pattern + "'.";


    private final @NotNull Pattern pattern;
    private final @NotNull String errorId;
    private final @NotNull BiFunction<File, Pattern, String> errorMessageSupplier;


    public FilePathMatchesRegexValidator (
        @NotNull Pattern pattern,
        @NotNull String errorId,
        @NotNull BiFunction<File, Pattern, String> errorMessageSupplier ) {

        this.pattern = pattern;
        this.errorId = errorId;
        this.errorMessageSupplier = errorMessageSupplier;
    }

    public FilePathMatchesRegexValidator ( @NotNull Pattern pattern ) {
        this ( pattern, DEFAULT_ERROR_ID, DEFAULT_ERROR_MESSAGE_SUPPLIER );
    }

    public FilePathMatchesRegexValidator ( @NotNull String pattern ) {
        this ( Pattern.compile ( pattern ) );
    }


    public @NotNull String getErrorId () { return errorId; }

    public @NotNull BiFunction<File, Pattern, String> getErrorMessageSupplier () { return errorMessageSupplier; }

    public @NotNull Pattern getPattern () { return pattern; }


    public void validate ( @NotNull File file, @Nullable TextToken token ) throws DataValidatorException {

        if ( ! StringUtils.stringMatchesRegex ( FilePathUtils.makeAbsoluteAndNormalize ( file.toPath() ).toString(), pattern ) )
            throw new DataValidatorException (
                errorMessageSupplier.apply ( file, pattern ),
                errorId,
                token, null );
    }
}
