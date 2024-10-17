module dev.pp.text {

    requires dev.pp.basics;

    exports dev.pp.text.inspection;
    exports dev.pp.text.inspection.handler;
    exports dev.pp.text.inspection.handler.error;
    exports dev.pp.text.inspection.handler.warning;
    exports dev.pp.text.inspection.message;
    exports dev.pp.text.documentation;
    exports dev.pp.text.location;
    exports dev.pp.text.reader;
    exports dev.pp.text.reader.stack;
    exports dev.pp.text.reader.util;
    exports dev.pp.text.resource;
    exports dev.pp.text.token;
    exports dev.pp.text.utilities.html;
    exports dev.pp.text.utilities.json;
    exports dev.pp.text.utilities.file;
    exports dev.pp.text.utilities.text;
    exports dev.pp.text.resource.reader;
    exports dev.pp.text.resource.writer;
    exports dev.pp.text.writer;
}
