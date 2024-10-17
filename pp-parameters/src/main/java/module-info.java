module dev.pp.parameters {

    requires dev.pp.basics;
    requires dev.pp.text;
    requires dev.pp.datatype;

    exports dev.pp.parameters.parameter;
    exports dev.pp.parameters.parameters;
    exports dev.pp.parameters.parameterspec;
    exports dev.pp.parameters.parameterspecs;
    exports dev.pp.parameters.utilities;
    exports dev.pp.parameters.utilities.parameterizedtext.parser;
    exports dev.pp.parameters.utilities.parameterizedtext.reader;
    exports dev.pp.parameters.cli;
    exports dev.pp.parameters.cli.token;
}
