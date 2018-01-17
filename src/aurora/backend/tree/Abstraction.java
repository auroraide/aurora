package aurora.backend.tree;

import aurora.backend.TermVisitor;

/**
 * An Abstraction is a "lambda", a "variable" a "." and a "body" in this order.
 * The body is a {@link Term}.
 * The "lambda" and the "." don't have to be saved, only the variable and the body.
 */
public class Abstraction extends Term {

    public final Term body;

    public final String name;

    /**
     * The Abstractions gets initialized with a body and a name.
     * The name has to consist of lower case letters.
     */
    public Abstraction(Term body, String name) {
        this.body = body;
        this.name = name;
    }


    @Override
    public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
