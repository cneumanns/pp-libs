package dev.pp.parameters.parameterspecs;

import dev.pp.parameters.parameters.Parameters;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.text.token.TextToken;

import java.util.*;

public abstract class MutableOrImmutableParameterSpecs<V> {

    protected @NotNull List<ParameterSpec<V>> list;
    protected @NotNull Map<String, ParameterSpec<V>> allNamesMap;

    protected @Nullable DataValidator<Parameters<V>> validator;
    @Nullable public DataValidator<Parameters<V>> getValidator () { return validator; }

    // private @Nullable ParameterSpec<?> defaultParameter;


    protected MutableOrImmutableParameterSpecs ( @Nullable DataValidator<Parameters<V>> validator ) {

        this.validator = validator;

        this.list = new ArrayList<>();
        this.allNamesMap = new HashMap<>();
        // this.defaultParameter = null;
    }

    protected MutableOrImmutableParameterSpecs (
        @NotNull List<ParameterSpec<V>> list,
        @NotNull Map<String, ParameterSpec<V>> allNamesMap,
        @Nullable DataValidator<Parameters<V>> validator ) {

        this.list = list;
        this.allNamesMap = allNamesMap;
        this.validator = validator;
    }


    public @Nullable ParameterSpec<V> firstPositionalParameterOrNull () {

        List<ParameterSpec<V>> list_ = positionalParametersSortedByPositionalIndex ();
        if ( list_ != null ) {
            return list_.get ( 0 );
        } else {
            return null;
        }
    }

    public @Nullable ParameterSpec<V> positionalParameterWithPosition ( int position ) {

        for ( ParameterSpec<V> parameterSpec : list ) {
            @Nullable Integer pos = parameterSpec.getPosition ();
            if ( pos != null && pos == position ) return parameterSpec;
        }
        return null;
    }

    public @Nullable String firstPositionalParameterNameOrNull () {

        ParameterSpec<V> parameter = firstPositionalParameterOrNull ();
        return parameter == null ? null : parameter.getName();
    }

    /*
    public @Nullable ParameterSpec<?> getDefaultParameter () { return defaultParameter; }

    public void setDefaultParameter ( @NotNull ParameterSpecs<V><?> defaultParameter ) {

        if ( this.defaultParameter != null ) throw new IllegalArgumentException (
            "Default parameter is set already (" + this.defaultParameter + ")" );

        if ( ! containsName ( defaultParameter.getName() ) ) throw new IllegalArgumentException (
            "Parameter '" + defaultParameter.getName() + "' doesn't exist." );

        this.defaultParameter = defaultParameter;
    }
    */

    public @NotNull List<ParameterSpec<V>> list() { return new ArrayList<> ( list ); }

    public @NotNull List<ParameterSpec<V>> listSortedByName () {

        return list
            .stream()
            .sorted ( Comparator.comparing ( ParameterSpec::getName ) )
            .toList();
    }

    public @NotNull List<ParameterSpec<V>> listSortedByIndex () {

        return list
            .stream()
            .sorted ( Comparator.comparingInt ( ParameterSpec::sortIndexOrZero ) )
            .toList();
    }

    public @NotNull List<ParameterSpec<V>> listSortedByName_RequiredFirst () {

        return list
            .stream()
            .sorted ( Comparator.comparing ( p -> (p.isRequired() ? "0" : "1") + p.getName() ) )
            .toList();
    }

    public @Nullable List<ParameterSpec<V>> positionalParametersSortedByPositionalIndex () {

        List<ParameterSpec<V>> result = list
            .stream()
            .filter ( ParameterSpec::isPositionalParameter )
            .sorted ( Comparator.comparingInt ( ParameterSpec::positionalIndexOrZero ) )
            .toList();

        return result.isEmpty() ? null : result;
    }

    public int positionalParametersCount () {

        return (int) list
            .stream()
            .filter ( ParameterSpec::isPositionalParameter )
            .count();
    }

    public @NotNull List<ParameterSpec<V>> listSortedByPositionalIndexThenName() {

        List<ParameterSpec<V>> result = new ArrayList<>();

        List<ParameterSpec<V>> positionals = positionalParametersSortedByPositionalIndex ();
        if ( positionals != null ) result.addAll ( positionals );

        List<ParameterSpec<V>> named = nonPositionalParametersSortedByName ();
        if ( named != null ) result.addAll ( named );

        return result;
/*
        return list
            .stream()
            .sorted ( Comparator.comparing (
                // p -> p.isPositionalParameter() ? "0" + p.getPositionalIndexOrZero() : "1" + p.getName () ) )
                p -> p.isPositionalParameter() ? String.valueOf ( p.getPositionalIndexOrZero() ) : ( "9999" + p.getName () ) ) )
            .collect ( Collectors.toList() );

 */
    }

    public @Nullable List<ParameterSpec<V>> nonPositionalParametersSortedByName () {

        List<ParameterSpec<V>> result = list
            .stream()
            .filter ( p -> ! p.isPositionalParameter() )
            .sorted ( Comparator.comparing ( ParameterSpec::getName ) )
            .toList();

        return result.isEmpty() ? null : result;
    }

    // @NotNull List<ParameterSpec<T>> getListSortedByName_firstIsDefault();


    public boolean containsName ( @NotNull String name ) { return allNamesMap.containsKey ( name ); }

    /*
    public boolean containsParameter ( @NotNull ParameterSpec<T> parameterSpec ) {
        return containsName ( parameterSpec.getName() ); }
    */

    public @Nullable Set<String> sortedNames () {

        Set<String> set = new LinkedHashSet<>();
        for ( ParameterSpec<V> parameterSpec : listSortedByName () ) {
            set.add ( parameterSpec.getName() );
        }

        return set.isEmpty() ? null : set;
    }

    public @Nullable String sortedNamesAsString () {
        return sortedNamesAsString ( ", " );
    }

    public @Nullable String sortedNamesAsString ( @NotNull String separator ) {

        return namesSetToString ( sortedNames (), separator );
    }

    private @Nullable String namesSetToString ( @Nullable Set<String> names, @NotNull String separator ) {

        if ( names == null || names.isEmpty() ) {
            return null;
        } else {
            return String.join ( separator, names );
        }
    }

    public boolean hasRequiredParameters() {

        for ( ParameterSpec<V> parameterSpec : list ) {
            if ( parameterSpec.isRequired() ) return true;
        }

        return false;
    }

    public @Nullable Set<String> sortedRequiredParameterNames () {

        Set<String> set = new LinkedHashSet<>();
        for ( ParameterSpec<V> parameterSpec : listSortedByName () ) {
            if ( parameterSpec.isRequired() ) set.add ( parameterSpec.getName() );
        }

        return set.isEmpty() ? null : set;
    }

    public @Nullable String sortedRequiredParameterNamesAsString () {

        return namesSetToString ( sortedRequiredParameterNames(), ", " );
    }


    // Get Parameter

    public @NotNull ParameterSpec<V> get ( @NotNull String name ) {

        checkExists ( name );
        return allNamesMap.get ( name );
    }

    public @NotNull <T extends V> ParameterSpec<T> getCasted ( @NotNull String name ) {

        @SuppressWarnings ( "unchecked" )
        ParameterSpec<T> result = (ParameterSpec<T>) get ( name );
        return result;
    }

    public @Nullable ParameterSpec<V> getOrNull ( @NotNull String name ) {

        return allNamesMap.get ( name );
    }

    public @Nullable <T extends V> ParameterSpec<T> getCastedOrNull ( @NotNull String name ) {

        @SuppressWarnings ( "unchecked" )
        ParameterSpec<T> result = (ParameterSpec<T>) getOrNull ( name );
        return result;
    }

    public void validate ( Parameters<V> parameters, TextToken token ) throws DataValidatorException {

        if ( validator == null ) return;

        validator.validate ( parameters, token );
    }


    // Private Helpers

    protected void checkExists ( @NotNull String name ) {

        if ( ! containsName ( name ) )
            throw new IllegalArgumentException ( "Parameter Spec '" + name + "' does not exist." );
    }

    protected void checkNotExists ( @NotNull String name ) {

        if ( containsName ( name ) )
            throw new IllegalArgumentException ( "Parameter Spec '" + name + "' exists already." );
    }
}
