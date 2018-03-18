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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JSONEscaperTest {

    @Test
    public void testUnescape() throws Exception {
        JSONEscaper je = new JSONEscaper();

        //String testShort = "Eating a piece of \u03c0 (pi)";
        //assertEquals("Eating a piece of \\u03c0 (pi)", je.escape(testShort));

        //String testLong = "I stole this guy from wikipedia: \ud83d\ude02"; // emoji "face with tears of joy"
        //assertEquals("I stole this guy from wikipedia: \\ud83d\\ude02", je.escape(testLong));

        String testQuote = "here it comes \" to wreck the day...";
        assertEquals("here it comes \\\" to wreck the day...", je.escape(testQuote));
        String testNewline = "here it comes \n to wreck the day...";
        assertEquals("here it comes \\n to wreck the day...", je.escape(testNewline));
        String testBackslash = "here it comes \\ to wreck the day...";
        assertEquals("here it comes \\\\ to wreck the day...", je.escape(testBackslash));

        String testAsciiEscapes = "\\\r\n\t\b\f \\hmm\\ \f\b\n\r\\";
        assertEquals(testAsciiEscapes, je.unescape(je.escape(testAsciiEscapes)));
        //assertEquals(testLong, je.unescape(je.escape(testLong)));
        //assertEquals(testShort, je.unescape(je.escape(testShort)));

    }
}
