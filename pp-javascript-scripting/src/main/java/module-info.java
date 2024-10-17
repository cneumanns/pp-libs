module dev.pp.scripting {

    requires java.desktop;

    requires jdk.javadoc;

    // requires org.graalvm.sdk;
    requires org.graalvm.polyglot;

    requires dev.pp.basics;
    requires dev.pp.text;

    exports dev.pp.scripting;
    exports dev.pp.scripting.env;
    exports dev.pp.scripting.bindings;
    exports dev.pp.scripting.docletParser;
    exports dev.pp.scripting.bindings.core;
    exports dev.pp.scripting.bindings.builder;
}
