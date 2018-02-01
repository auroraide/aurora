CodeMirror.defineSimpleMode("aurorascript", {
    start: [
        {regex: /#[^\n]*\n*/, token: "comment"},
        {regex: /\$[a-z]+/, token: "keyword"},
        {regex: /[0-9]+/, token: "number"},
        {regex: /[a-z]+/, token: "variable-2"}
    ],
    meta: {
        lineComment: "#"
    }
});
