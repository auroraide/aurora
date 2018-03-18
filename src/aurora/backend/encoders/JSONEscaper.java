// BSD License (http://lemurproject.org/galago-license)
/*
 *Copyright (c) 2006-2008, Trevor Strohman
Copyright (c) 2009-2012, Marc Cartright, David Fisher, Sam Huston 

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice,
this list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

Neither the name Galago nor the names of its contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package aurora.backend.encoders;

/**
 * (Un)escape JSON Strings.
 */
public class JSONEscaper {
    /**
     * Escapes an JSON String.
     *
     * @param input JSON String to escape.
     * @return escapes JSON String.
     */
    public static String escape(String input) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            int chx = (int) ch;

            // let's not put any nulls in our strings
            assert (chx != 0);

            if (ch == '\n') {
                output.append("\\n");
            } else if (ch == '\t') {
                output.append("\\t");
            } else if (ch == '\r') {
                output.append("\\r");
            } else if (ch == '\\') {
                output.append("\\\\");
            } else if (ch == '"') {
                output.append("\\\"");
            } else if (ch == '\b') {
                output.append("\\b");
            } else if (ch == '\f') {
                output.append("\\f");
            } else if (chx >= 0x10000) {
                assert false : 
                    "Java stores as u16, so it should never give us a character that's bigger than 2 bytes." 
                    + "It literally can't.";
            //} else if(chx > 127) {
            //    output.append(String.format("\\u%04x", chx));
            } else {
                output.append(ch);
            }
        }

        return output.toString();
    }

    /**
     * Unescapes an escaped JSON String.
     *
     * @param input JSON String to unescape.
     * @return unescaped JSON String.
     */
    public static String unescape(String input) {
        StringBuilder builder = new StringBuilder();

        int i = 0;
        while (i < input.length()) {
            char delimiter = input.charAt(i++); 

            if (delimiter == '\\' && i < input.length()) {

                // consume first after backslash
                char ch = input.charAt(i++);

                if (ch == '\\' || ch == '/' || ch == '"' || ch == '\'') {
                    builder.append(ch);
                } else if (ch == 'n') {
                    builder.append('\n');
                } else if (ch == 'r') {
                    builder.append('\r');
                } else if (ch == 't') {
                    builder.append('\t');
                } else if (ch == 'b') {
                    builder.append('\b');
                } else if (ch == 'f') {
                    builder.append('\f');
                } else if (ch == 'u') {

                    StringBuilder hex = new StringBuilder();

                    // expect 4 digits
                    if (i + 4 > input.length()) {
                        throw new RuntimeException("Not enough unicode digits! ");
                    }
                    for (char x : input.substring(i, i + 4).toCharArray()) {
                        if (!Character.isLetterOrDigit(x)) {
                            throw new RuntimeException("Bad character in unicode escape.");
                        }
                        hex.append(Character.toLowerCase(x));
                    }
                    i += 4; // consume those four digits.

                    int code = Integer.parseInt(hex.toString(), 16);
                    builder.append((char) code);
                } else {
                    throw new RuntimeException("Illegal escape sequence: \\" + ch);
                }
            } else { // it's not a backslash, or it's the last character.
                builder.append(delimiter);
            }
        }

        return builder.toString();
    }
}

