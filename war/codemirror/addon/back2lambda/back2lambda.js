(function(mod) {
    if (typeof exports == "object" && typeof module == "object") // CommonJS
        mod(require("../../lib/codemirror"));
    else if (typeof define == "function" && define.amd) // AMD
        define(["../../lib/codemirror"], mod);
    else // Plain browser env
        mod(CodeMirror);
})(function(CodeMirror) {

    CodeMirror.defineOption("back2Lambda", null, function(cm) {
        cm.on("change", function(cm) {
            if(cm.getValue().includes("\\")) {
                var position = cm.getCursor();
                cm.setValue(cm.getValue().replace(/\\/g, "λ"));
                cm.setCursor(position);
            }
        });
    });
});



