package aurora.backend.betareduction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import aurora.backend.Comparer;
import aurora.backend.HighlightableLambdaExpression;
import aurora.backend.betareduction.strategies.CallByName;
import aurora.backend.betareduction.strategies.NormalOrder;
import aurora.backend.library.HashLibrary;
import aurora.backend.parser.LambdaLexer;
import aurora.backend.parser.LambdaParser;
import aurora.backend.parser.exceptions.SemanticException;
import aurora.backend.parser.exceptions.SyntaxException;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.ChurchNumber;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Function;
import aurora.backend.tree.Term;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;



public class BetaReducerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * check in qualityassurance.
     */
    @Ignore
    public void noReductionPossible() {
        BetaReducer br = new BetaReducer(new CallByName());
        // \x.\s.s a
        Term t = new Abstraction(
                new Application(
                        new Abstraction(
                                new BoundVariable(1),"s"
                        ),
                        new FreeVariable("a")
                ),
                "x"
        );
        Term result = br.reduce(t);
        Term correct = new Abstraction(
                new Application(
                        new Abstraction(
                                new BoundVariable(1),"s"
                        ),
                        new FreeVariable("a")
                ),
                "x"
        );
        Comparer cr = new Comparer(correct, result);
        assertEquals(cr.compare(),true);
    }

    @Test
    public void easyreduction() {
        BetaReducer br = new BetaReducer(new NormalOrder());
        // \y.(\x.x a)
        Term t = new Abstraction(
                    new Application(
                    new Abstraction(
                        new BoundVariable(1),"x"
                ),  new FreeVariable("a")
        ),"y");
        Term result = br.reduce(t);
        Abstraction correct = new Abstraction(new FreeVariable("a"),"y");
        Comparer cr = new Comparer(result,correct);
        assertEquals(cr.compare(),true);
    }

    @Test
    public void easyreductionWithroot() {
        BetaReducer br = new BetaReducer(new NormalOrder());
        // (\x.x)z
        Term t = new Application(
                new Abstraction(
                        new BoundVariable(1),
                        "x"
                ),
                new FreeVariable("z")
        );
        Term result = br.reduce(t);
        Term correct = new FreeVariable("z");
        Comparer cr = new Comparer(result, correct);
        assertEquals(cr.compare(),true);
    }

    @Test
    public void twobounded() {
        BetaReducer br = new BetaReducer(new NormalOrder());
        // (\x.xx)(a)
        Term t = new Application(
                new Abstraction(
                        new Application(
                                new BoundVariable(1),new BoundVariable(1)
                        ), "x"
                ), new FreeVariable("a")
        );
        Term result = br.reduce(t);
        Term correct = new Application(
                new FreeVariable("a"), new FreeVariable("a")
        );
        Comparer cr = new Comparer(result, correct);
        assertEquals(cr.compare(),true);
    }

    @Test
    public void infinity() {
        BetaReducer br = new BetaReducer(new NormalOrder());
        // (\x.xx)(\x.xx)
        Term t = new Application(
                    new Abstraction(
                        new Application(
                                new BoundVariable(1),new BoundVariable(1)
                        ), "x"
                ), new Abstraction(
                        new Application(
                                new BoundVariable(1),new BoundVariable(1)
                         ), "x"
        )
        );
        Term intermediateresult = br.reduce(t);
        Term result = br.reduce(intermediateresult);
        Term correct = new Application(
                new Abstraction(
                        new Application(
                                new BoundVariable(1),new BoundVariable(1)
                        ), "x"
                ), new Abstraction(
                new Application(
                        new BoundVariable(1),new BoundVariable(1)
                ), "x"
        )
        );

        Comparer cr = new Comparer(result, correct);
        assertEquals(cr.compare(),true);
    }



    @Test
    public void twoplustwo() {
        BetaReducer br = new BetaReducer(new NormalOrder());

        Term plus = new Abstraction(
                new Abstraction(
                        new Abstraction(
                                new Abstraction(
                                        new Application(
                                                new Application(
                                                        new BoundVariable(4), new BoundVariable(2)
                                                ),
                                                new Application(
                                                        new Application(
                                                                new BoundVariable(3), new BoundVariable(2)
                                                        ), new BoundVariable(1)

                                                )

                                        ), "z"
                                ),"s"
                        ),"m"
                ),"n"
        );
        Abstraction firstTwo = new ChurchNumber(2).getAbstraction();
        Abstraction secondTwo = new ChurchNumber(2).getAbstraction();

        Term t = new Application(
                    new Application(
                            plus, firstTwo
                    ), secondTwo
        );

        Term result = br.reduce(t);

        Term correct1 = new Application(
                new Abstraction(
                        new Abstraction(
                                new Abstraction(

                                                new Application(
                                                        new Application(
                                                                firstTwo, new BoundVariable(2)
                                                        ),
                                                        new Application(
                                                                new Application(
                                                                        new BoundVariable(3), new BoundVariable(2)
                                                                ), new BoundVariable(1)

                                                        )


                                        ),"z"
                                ),"s"
                        ),"m"
                ),  secondTwo
        );
        Comparer cr1 = new Comparer(result,correct1);
        assertEquals(cr1.compare(),true); // this works


        Term result2 = br.reduce(result);
        Term correct2 = new Abstraction(
                new Abstraction(

                        new Application(
                                new Application(
                                        firstTwo, new BoundVariable(2)
                                ),
                                new Application(
                                        new Application(
                                                secondTwo, new BoundVariable(2)
                                        ), new BoundVariable(1)

                                )


                        ),"z"
                ),"s"
        );

        Comparer cr2 = new Comparer(result2,correct2);
        assertEquals(cr2.compare(),true); // works too

        Term result3 = br.reduce(result2);

        Term correct3 = new Abstraction(
                new Abstraction(
                        new Application(
                                new Abstraction(
                                        new Application(
                                                new BoundVariable(3),
                                                new Application(
                                                        new BoundVariable(3), new BoundVariable(1)
                                                )
                                        ),"z"
                                ),
                                new Application(
                                        new Application(
                                                new Abstraction(
                                                        new Abstraction(
                                                                new Application(
                                                                        new BoundVariable(2),
                                                                        new Application(
                                                                                new BoundVariable(2),
                                                                                new BoundVariable(1)
                                                                        )
                                                                ),"z"
                                                        ),"s"
                                                ),new BoundVariable(2)
                                        ),
                                        new BoundVariable(1)
                                )
                        ),"z"
                ),"s"
        );

        Comparer cr3 = new Comparer(result3,correct3);
        assertEquals(cr3.compare(),true);




        Term result4 = br.reduce(result3);
        Term result5 = br.reduce(result4);
        Term result6 = br.reduce(result5);
        Term correct = new ChurchNumber(4).getAbstraction();
        Comparer cr = new Comparer(correct,result6);

        assertEquals(cr.compare(),true);
    }

    /**
     * check in qualityassurance.
     */
    @Ignore
    public void twoplustwowithfunction() {
        Function plus = new Function("$plus",new Abstraction(
                new Abstraction(
                        new Abstraction(
                                new Abstraction(
                                        new Application(
                                                new Application(
                                                        new BoundVariable(4), new BoundVariable(2)
                                                ),
                                                new Application(
                                                        new Application(
                                                                new BoundVariable(3), new BoundVariable(2)
                                                        ), new BoundVariable(1)

                                                )

                                        ), "z"
                                ),"s"
                        ),"m"
                ),"n"
        ));

        Term t = new Application(
                new Application(plus,new ChurchNumber(2)), new ChurchNumber(2)
        );

        BetaReducer br = new BetaReducer(new NormalOrder());
        int n = 0;
        while (n < 100) {

            t = br.reduce(t);
            if (br.getFinished()) {
                break;
            }

        }
        Comparer cr = new Comparer(t,new ChurchNumber(4).getAbstraction());
        assertEquals(cr.compare(),true);

    }

    /**
     * check in qualityassurance.
     */
    @Ignore
    public void noreduction() {
        Term t = new ChurchNumber(4);
        BetaReducer br = new BetaReducer(new NormalOrder());
        Term result = br.reduce(t);
        Comparer cr = new Comparer(result, new ChurchNumber(4));
        assertEquals(cr.compare(), true);
    }

    @Test
    public void namedebug() {
        HashLibrary library = new HashLibrary();
        library.define("plus", "addition",new Abstraction(
                new Abstraction(
                new Abstraction(
                        new Abstraction(
                                new Application(
                                        new Application(
                                                new BoundVariable(4), new BoundVariable(2)
                                        ),
                                        new Application(
                                                new Application(
                                                        new BoundVariable(3), new BoundVariable(2)
                                                ), new BoundVariable(1)

                                        )

                                ), "z"
                        ),"s"
                ),"m"
        ),"n"
        ));
        String input = "$plus 2 2";
        LambdaLexer lexer = new LambdaLexer();
        LambdaParser parser = new LambdaParser(library);
        Term t = null;
        try {
            t = parser.parse(lexer.lex(input));
            HighlightableLambdaExpression hle = new HighlightableLambdaExpression(t);
        } catch (SemanticException e) {
            e.printStackTrace();
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
        BetaReducer br = new BetaReducer(new NormalOrder());
        t = br.reduce(t);
        HighlightableLambdaExpression hel1 = new HighlightableLambdaExpression(t);
        assertEquals("( \\ m . \\ s . \\ z . ( 2 ) s ( m s z ) ) ( 2 ) ", hel1.toString());
        t = br.reduce(t);
        Comparer cr = new Comparer(t, new Abstraction(
                new Abstraction(

                        new Application(
                                new Application(
                                        new ChurchNumber(2).getAbstraction(), new BoundVariable(2)
                                ),
                                new Application(
                                        new Application(
                                                new ChurchNumber(2), new BoundVariable(2)
                                        ), new BoundVariable(1)

                                )


                        ),"z"
                ),"s"
        ));
        assertTrue(cr.compare());

        HighlightableLambdaExpression hel2 = new HighlightableLambdaExpression(t);
        assertEquals("\\ s . \\ z . ( \\ s . \\ z . s ( s z ) ) s ( ( 2 ) s z ) ", hel2.toString());

    }

}