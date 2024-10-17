package dev.pp.datatype;

import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.nonunion.collection.ListDataType;
import dev.pp.datatype.nonunion.collection.ListDataTypeImpl;
import dev.pp.datatype.nonunion.scalar.impls.booleantype.BooleanDataType;
import dev.pp.datatype.nonunion.scalar.impls.filesystempath.DirectoryOrFilePathDataType;
import dev.pp.datatype.nonunion.scalar.impls.filesystempath.DirectoryPathDataType;
import dev.pp.datatype.nonunion.scalar.impls.filesystempath.FilePathDataType;
import dev.pp.datatype.nonunion.scalar.impls.integer.IntDataType;
import dev.pp.datatype.nonunion.scalar.impls.regex.RegexDataType;
import dev.pp.datatype.nonunion.scalar.impls.string.StringDataType;
import dev.pp.datatype.union.NullablePairDataType;
import dev.pp.text.documentation.SimpleDocumentation;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

public class CommonDataTypes {


    // String

    public static final @NotNull StringDataType STRING = StringDataType.DEFAULT;
    public static final @NotNull NullablePairDataType<String> STRING_OR_NULL = new NullablePairDataType<> ( StringDataType.DEFAULT );

    public static final @NotNull ListDataType<String> STRING_LIST = new ListDataTypeImpl<> (
        "string_list",
        STRING,
        null,
        () -> new SimpleDocumentation (
            "List of Strings",
            "A comma separated list of strings.",
            "good, better, best" ));

    public static final @NotNull NullablePairDataType<List<String>> STRING_LIST_OR_NULL = new NullablePairDataType<> ( STRING_LIST );


    // Integer

    public static final @NotNull IntDataType INTEGER_32 = IntDataType.DEFAULT;
    public static final @NotNull NullablePairDataType<Integer> INTEGER_32_OR_NULL =
        new NullablePairDataType<> ( IntDataType.DEFAULT );


    // Boolean

    public static final @NotNull BooleanDataType BOOLEAN = BooleanDataType.INSTANCE;
    public static final @NotNull NullablePairDataType<Boolean> BOOLEAN_OR_NULL = new NullablePairDataType<> ( BOOLEAN );


    // Regex

    public static final @NotNull RegexDataType REGEX = RegexDataType.DEFAULT;
    public static final @NotNull NullablePairDataType<Pattern> REGEX_OR_NULL = new NullablePairDataType<> ( REGEX );


    // Directory or File Path

    public static final @NotNull DirectoryOrFilePathDataType DIRECTORY_OR_FILE_PATH = DirectoryOrFilePathDataType.DEFAULT;

    public static final @NotNull NullablePairDataType<Path> DIRECTORY_OR_FILE_PATH_OR_NULL = new NullablePairDataType<> ( DIRECTORY_OR_FILE_PATH );

    public static final @NotNull ListDataType<Path> DIRECTORY_OR_FILE_PATHS = new ListDataTypeImpl<> (
        "directory_or_file_paths",
        DirectoryOrFilePathDataType.DEFAULT,
        null,
        () -> new SimpleDocumentation (
            "List of Directories and Files",
            "A list of directories and/or files. Each path can be absolute or relative.",
            "docs/health/diet.pml, docs/fitness" ));

    public static final @NotNull NullablePairDataType<List<Path>> DIRECTORY_OR_FILE_PATHS_OR_NULL = new NullablePairDataType<> ( DIRECTORY_OR_FILE_PATHS );


    // Directory Path

    public static final @NotNull DirectoryPathDataType DIRECTORY_PATH = DirectoryPathDataType.DEFAULT;

    public static final @NotNull NullablePairDataType<Path> DIRECTORY_PATH_OR_NULL = new NullablePairDataType<> ( DIRECTORY_PATH );

    public static final @NotNull ListDataType<Path> DIRECTORY_PATHS = new ListDataTypeImpl<> (
        "directory_paths",
        DirectoryPathDataType.DEFAULT,
        null,
        () -> new SimpleDocumentation (
            "List of Directories",
            "A list of directory paths. Each path can be absolute or relative.",
            "products/fruits, products/vegetables" ));

    public static final @NotNull NullablePairDataType<List<Path>> DIRECTORY_PATHS_OR_NULL = new NullablePairDataType<> ( DIRECTORY_PATHS );


    // File Path

    public static final @NotNull FilePathDataType FILE_PATH = FilePathDataType.DEFAULT;

    public static final @NotNull NullablePairDataType<Path> FILE_PATH_OR_NULL = new NullablePairDataType<> ( FILE_PATH );

    public static final @NotNull ListDataType<Path> FILE_PATHS = new ListDataTypeImpl<> (
        "file_paths",
        FilePathDataType.DEFAULT,
        null,
        () -> new SimpleDocumentation (
            "List of Files",
            "A list of file paths. Each path can be absolute or relative.",
            "docs/health/diet.pml, docs/health/fitness.pml" ));

    public static final @NotNull NullablePairDataType<List<Path>> FILE_PATHS_OR_NULL = new NullablePairDataType<> ( FILE_PATHS );
}
