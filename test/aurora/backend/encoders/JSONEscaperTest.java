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
