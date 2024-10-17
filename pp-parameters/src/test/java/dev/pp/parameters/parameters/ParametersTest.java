package dev.pp.parameters.parameters;

import dev.pp.parameters.parameter.Parameter;
import dev.pp.parameters.parameterspec.ParameterSpec;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParametersTest {

    @Test
    void test() {


        // One type parameters

        Parameter<String> stringParameter1 = new Parameter<> ( "s1", "v1" );
        ParameterSpec<String> stringParameter1Spec = ParameterSpec.ofString ( "s1", null );
        Parameter<String> stringParameter2 = new Parameter<> ( "s2", "v2" );

        Parameters<String> stringParameters = new MutableParameters<String>()
            .add ( stringParameter1 )
            .add ( stringParameter2 )
            .makeImmutable();

        assertEquals ( "v1", stringParameters.value ( "s1" ) );
        assertEquals ( "v1", stringParameters.value ( stringParameter1Spec ) );
        assertEquals ( "v2", stringParameters.value ( "s2" ) );


        // Subtypes

        Parameter<Number> integerParameter2 = new Parameter<> ( "i2", 222 );
        Parameter<Number> doubleParameter1 = new Parameter<> ( "d1", 11.11 );

        Parameters<Number> numberParameters = new MutableParameters<Number>()
            .add ( integerParameter2 )
            .add ( doubleParameter1 )
            .makeImmutable();
/*
        Integer i2 = numberParameters.castedValueOld ( "i2" );
        assertEquals ( 222, i2 );
        Double d1 = numberParameters.castedValueOld ( "d1" );
        assertEquals ( 11.11, d1 );
 */


        // Raw (untyped, mixed) parameters

        Parameter<Integer> integerParameter1 = new Parameter<> ( "i1", 111 );

        @SuppressWarnings ( {"unchecked", "rawtypes"} )
        Parameters rawParameters = new MutableParameters()
            .add ( stringParameter1 )
            .add ( integerParameter1 )
            .makeImmutable();

        // String s1 = rawParameters.castedValue ( "s1" );
        String s1= (String) rawParameters.value ( "s1" );

        // TODO use in PDML !!!
        var sInferred = (String) rawParameters.value ( "s1" );

        // s1 = rawParameters.castedValueNew ( "s1" );
        // s1 = rawParameters.castedValueNew ( stringParameter1Spec );
        // s1 = rawParameters.castedValueNew_2 ( "s1" );
        s1 = rawParameters.valueGetter().nonNull ( "s1" );
        assertEquals ( "v1", s1 );
        Integer i1 = (Integer) rawParameters.value ( "i1" );
        assertEquals ( 111, i1 );



        // Wildcard

        Parameters<?> wildcardParameters = stringParameters;
        s1 = wildcardParameters.castedValue ( "s1" );
        s1 = wildcardParameters.castedValueOrDefault ( "qqq", "foo" );
        s1 = wildcardParameters.castedValueOrDefault ( stringParameter1Spec, "foo" );
        s1 = wildcardParameters.castedValue ( stringParameter1Spec );
        s1 = wildcardParameters.valueGetter().nonNull ( "s1" );
        s1 = wildcardParameters.valueGetter().nonNull ( stringParameter1Spec );
        // s1 = wildcardParameters.castedValue ( "s1" );
        // s1 = wildcardParameters.castedValue ( stringParameter1Spec );
        // s1 = wildcardParameters.castedValueNew_2 ( "s1" );
        // s1 = wildcardParameters.castedValueNew_3 ( "s1" );
        s1 = (String) wildcardParameters.value ( "s1" );
        s1 = wildcardParameters.valueGetter().nonNull ( "s1" );
        assertEquals ( "v1", s1 );

        wildcardParameters = rawParameters;
        s1 = (String) wildcardParameters.value ( "s1" );
        assertEquals ( "v1", s1 );
        i1 = (Integer) wildcardParameters.value ( "i1" );
        assertEquals ( 111, i1 );
    }
}
