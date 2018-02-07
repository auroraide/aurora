package aurora.backend.betareduction.visitors;

import static org.junit.Assert.assertEquals;

import aurora.backend.Comparer;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.Application;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.FreeVariable;
import aurora.backend.tree.Term;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SubstitutionVisitorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void standardtest() {
        // t = \v.\ u.s
        Term with = new Abstraction(
                new Abstraction(
                        new FreeVariable("s"),
                        "u"
                ),
                "v");

        // \y.\x.ya
        Term t =
                new Abstraction(
                        new Abstraction(
                                new Application(
                                        new BoundVariable(2), new FreeVariable("a")
                                ), "x"
                        ), "y"
                );
        SubstitutionVisitor sv = new SubstitutionVisitor(with);
        Term reduced = t.accept(sv);

        Abstraction root = (Abstraction) reduced;
        assertEquals("y", ((Abstraction) reduced).name);

        Abstraction abs = (Abstraction) root.body;
        assertEquals("x", abs.name);

        Application app = (Application) abs.body;
        FreeVariable fv = (FreeVariable) app.right;

        assertEquals("a", fv.name);
        Abstraction correctwith = (Abstraction) app.left;
        assertEquals("v", correctwith.name);

    }

    @Test
    public void samewithcomparer() {
        // t = \v.\ u.s
        Term with = new Abstraction(
                new Abstraction(
                        new FreeVariable("s"),
                        "u"
                ),
                "v");

        // \y.\x.ya
        Term t =
                new Abstraction(
                        new Abstraction(
                                new Application(
                                        new BoundVariable(2), new FreeVariable("a")
                                ), "x"
                        ), "y"
                );

        SubstitutionVisitor sv = new SubstitutionVisitor(with);
        Term reduced = t.accept(sv);

        Term correct = new Abstraction(
                new Abstraction(
                        new Application(
                                new Abstraction(
                                        new Abstraction(
                                                new FreeVariable("s"),
                                                "u"
                                        ), "v"
                                ), new FreeVariable("a")
                        ), "x"
                ), "y"
        );


        Comparer comparer = new Comparer(reduced, correct);
        assertEquals(comparer.compare(), true);
    }

    @Test
    public void debruijnfixerTestfixleft() {
        // \x.(\y.xy)x
        Abstraction abs = new Abstraction(
                new Application(
                        new BoundVariable(2),
                        new BoundVariable(1)
                ), "y"
        );
        Term with = new BoundVariable(1);
        SubstitutionVisitor sv = new SubstitutionVisitor(with);
        Term result = abs.accept(sv);

        Term correct = new Abstraction(
                new Application(
                        new BoundVariable(1),
                        new BoundVariable(1)
                ), "y"
        );
        Comparer cr = new Comparer(result, correct);
        assertEquals(cr.compare(), true);
    }

    @Test
    public void debruijnfixerTestfixright() {
        // \y.(\x.\z.x)y
        Abstraction abs = new Abstraction(
                new Abstraction(
                        new BoundVariable(2), "z"
                ), "x"
        );
        Term with = new BoundVariable(1);

        SubstitutionVisitor sv = new SubstitutionVisitor(with);
        Term result = abs.accept(sv);
        Term correct = new Abstraction(
                new Abstraction(
                        new BoundVariable(2), "z"
                ), "x"
        );
        Comparer cr = new Comparer(result, correct);
        assertEquals(cr.compare(),true);
    }
}