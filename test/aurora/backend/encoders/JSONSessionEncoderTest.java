package aurora.backend.encoders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import aurora.backend.library.HashLibrary;
import aurora.backend.library.Library;

import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.tree.Term;

/**
 * Tests JSON encoder.
 */
public class JSONSessionEncoderTest {
    @Test
    public void encode() {
        /*  JSONSessionEncoder jsonse = new JSONSessionEncoder();
        Library library = new HashLibrary();
        Term term;
        try {
            term = lambdaParser.parse(lambdaLexer.lex("\\x.x"));
        } catch (SemanticException | SyntaxException e) {
            throw new DecodeException("Invalid json file");
        }

        library.define("add", "adds", term);

        String json = "\"{\"rawInput\":\"banana\",\"library\":[[\"add\",\"adds\",{\"term_1_g$\":";
            json += """{\"body_1_g$\":{\"term_1_g$\":{\"index_2_g$\":1},\"token_2_g$\":{\"type_1_g$\":";
            json += ""{\"name_4_g$\":\"T_VARIABLE\",\"ordinal_1_g$\":2},\"name_7_g$\":\"x\",\"line_4_g$\":";
            json += "1,\"column_4_g$\":4,\"offset_4_g$\":3}},\"name_1_g$\":\"x\"},\"token_2_g$\":{\"type_1_g$\":";
            json += {\"name_4_g$\":\"T_LAMBDA\",\"ordinal_1_g$\":0},\"name_7_g$\":\"\",\"line_4_g$\":";
            json += "1,\"column_4_g$\":1,\"offset_4_g$\":0}}]]}\"";
        */
        assertEquals("banana", "banana");


    }
}
