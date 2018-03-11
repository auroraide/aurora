CodeMirror.defineSimpleMode("aurorascript", {
    start: [
        {regex: /#[^\n]*\n*/, token: "comment"},
        {regex: /\$[A-Za-z][A-Za-z0-9_]*/, token: "keyword"},
        {regex: /[0-9]+/, token: "number"},
        {regex: /[A-Za-z][A-Za-z0-9_]*/, token: "variable-2"},
        {regex: /λ/, token: "atom"}
    ],
    meta: {
        lineComment: "#"
    }
});
