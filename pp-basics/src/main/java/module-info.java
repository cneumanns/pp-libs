module dev.pp.basics {

    requires java.logging;

    exports dev.pp.basics.annotations;
    exports dev.pp.basics.utilities;
    exports dev.pp.basics.utilities.character;
    exports dev.pp.basics.utilities.directory;
    exports dev.pp.basics.utilities.file;
    exports dev.pp.basics.utilities.os;
    exports dev.pp.basics.utilities.os.process;
    exports dev.pp.basics.utilities.string;
    exports dev.pp.basics.utilities.json;
    exports dev.pp.basics.utilities.networking.socket;
    exports dev.pp.basics.utilities.REMOVEreflection;
}
