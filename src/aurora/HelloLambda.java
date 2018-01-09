package aurora;

import aurora.backend.tree.*;
import aurora.backend.betareduction.visitors.SubstitutionVisitor;
import aurora.backend.TermVisitor;

public class HelloLambda {

    public static void main(String[] args) {
        TermVisitor<String> tv = new TermVisitor<String>() {
            @Override
            public String visit(Abstraction abs) {
                return "(\\" + abs.getName() + "." + abs.getBody().accept(this) + ")";
            }

            @Override
            public String visit(Application app) {
                return "[" + app.getLeft().accept(this) + " " + app.getRight().accept(this) + "]";
            }

            @Override
            public String visit(BoundVariable bvar) {
                return "" + bvar.getIndex();
            }

            @Override
            public String visit(FreeVariable fvar) {
                return fvar.getName();
            }

            @Override
            public String visit(LibraryTerm libterm) {
                return "$" + libterm.getName();
            }

            @Override
            public String visit(ChurchNumber c) {
                return "c" + c.getValue();
            }
        };

        Term abs = new Abstraction(
            new Abstraction(
                new Application(
                    new Application(
                        new BoundVariable(2),
                        new Abstraction(
                            new BoundVariable(1),
                            "z"
                        )
                    ),
                    new BoundVariable(1)
                ),
                "x"
            ),
            "y"

        );

        Term app = new Application(
                new Abstraction(
                        new BoundVariable(1),
                        "x"
                ),
                new Abstraction(
                        new Application(
                                new BoundVariable(1),
                                new BoundVariable(2)),
                        "y"
                )
        );

        Term root =
                new Abstraction(
                        new Application(
                                abs,
                                app
                        ), "r");

        System.out.println(root.accept(tv));

        SubstitutionVisitor sv = new SubstitutionVisitor(app);
        Term reduced = abs.accept(sv);

        Term newroot =
                new Abstraction(
                                ((Abstraction)reduced).getBody()
                        , "r");

        System.out.println(newroot.accept(tv));

    }

}
