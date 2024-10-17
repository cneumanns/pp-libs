package dev.pp.scripting.docletParser;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.doctree.ParamTree;
import com.sun.source.doctree.ReturnTree;
import com.sun.source.util.DocTrees;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

@Deprecated
public class MyDoclet implements Doclet {

    public MyDoclet() {}

    @Override
    public void init ( Locale locale, Reporter reporter ) {}

    @Override
    public String getName() {

        return getClass().getSimpleName();
    }

    @Override
    public Set<? extends Option> getSupportedOptions() {

        return Collections.emptySet();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {

        return SourceVersion.latest();
    }

    @Override
    public boolean run ( DocletEnvironment docletEnv ) {

        DocTrees docTrees = docletEnv.getDocTrees();
        // Types types = docletEnv.getTypeUtils();
        docletEnv.getSpecifiedElements().forEach ( element -> {

            @Nullable DocCommentTree commentTree = docTrees.getDocCommentTree ( element );
            handleClass ( element.getSimpleName(), commentTree );

            for ( Element childElement : element.getEnclosedElements() ) {
                if ( childElement.getKind() == ElementKind.METHOD ) {
                    ExecutableElement method = (ExecutableElement) childElement;

                    @NotNull Name methodName = method.getSimpleName();
                    @Nullable DocCommentTree methodComment = docTrees.getDocCommentTree ( method );
                    handleMethod ( methodName, methodComment );

                    @Nullable List<? extends VariableElement> inputArguments = method.getParameters(); // empty list if there are none
                    @Nullable Map<Name, List<? extends DocTree>> inputArgumentDocs = inputArgumentDocs ( methodComment );
                    handleInputArguments ( inputArguments, inputArgumentDocs );

                    @Nullable TypeMirror returnType = method.getReturnType();
                    @Nullable List<? extends DocTree> outputDoc = outputDoc ( methodComment );
                    boolean isNullable = isNullable ( method );
                    handleOutput ( returnType, isNullable, outputDoc );
                }
            }
        });

        return true;
    }

    private static @Nullable Map<Name, List<? extends DocTree>> inputArgumentDocs ( @Nullable DocCommentTree methodComment ) {

        if ( methodComment == null ) return null;

        List<? extends DocTree> tags = methodComment.getBlockTags();
        if ( tags == null ) return null;

        Map<Name, List<? extends DocTree>> inputArgumentDocs = new HashMap<>();
        for ( DocTree tag : tags ) {
            if ( tag instanceof ParamTree paramDoc ) {
                Name name = paramDoc.getName().getName();
                List<? extends DocTree> description = paramDoc.getDescription();
                inputArgumentDocs.put ( name, description );
            }
        }

        return inputArgumentDocs.isEmpty() ? null : inputArgumentDocs;
    }

    private static @Nullable List<? extends DocTree> outputDoc ( @Nullable DocCommentTree methodComment ) {

        if ( methodComment == null ) return null;

        List<? extends DocTree> tags = methodComment.getBlockTags();
        if ( tags == null ) return null;

        for ( DocTree tag : tags ) {
            if ( tag instanceof ReturnTree returnDoc ) return returnDoc.getDescription();
        }

        return null;
    }

    private static void handleClass (
        @NotNull Name name,
        @Nullable DocCommentTree doc ) {

        System.out.println();
        System.out.println ( "Class " + name );
        System.out.println ( "=====" );

        if ( doc != null ) {
            System.out.println ( doc.getFullBody() );
        }
    }

    private static void handleMethod (
        @NotNull Name name,
        @Nullable DocCommentTree doc ) {

        System.out.println ( "Function " + name );

        if ( doc != null ) {
            System.out.println ( doc.getFullBody() );
        }
    }

    private static void handleInputArguments (
        @Nullable List<? extends VariableElement> inputArguments,
        @Nullable Map<Name, List<? extends DocTree>> inputArgumentDocs ) {

        System.out.println ( "   input" );
        if ( inputArguments == null || inputArguments.isEmpty () ) {
            System.out.println ( "       none" );
        } else {
            for ( VariableElement inputArgument : inputArguments ) {
                Name name = inputArgument.getSimpleName();
                System.out.println ( "       name: " + name );
                boolean isNullable = isNullable ( inputArgument );
                System.out.println ( "       type: " + typeToString ( inputArgument.asType(), isNullable ) );
                List<? extends DocTree> doc = inputArgumentDocs == null ? null : inputArgumentDocs.get ( name );
                if ( doc != null ) {
                    System.out.println ( "       doc: " + doc );
                }
            }
        }
    }

    private static void handleOutput (
        @Nullable TypeMirror type,
        boolean isNullable,
        @Nullable List<? extends DocTree> doc ) {

        System.out.println ( "   output" );
        if ( type == null ) {
            System.out.println ( "       none" );
        } else {
            System.out.println ( "       type: " + typeToString ( type, isNullable ) );
            if ( doc != null ) {
                System.out.println ( "       doc: " + doc );
            }
        }
    }


    private static @NotNull String typeToString ( TypeMirror type, boolean isNullable ) {

        // return type.toString(); // java.lang.String
        String r = type.toString();
        if ( isNullable ) r = r + " or null";
        return r;
    }

    private static boolean isNullable ( Element element ) {

        return element.getAnnotation ( Nullable.class ) != null;
    }
}
