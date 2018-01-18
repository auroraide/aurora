package aurora.backend.tree;

import aurora.backend.TermVisitor;

/**
 * A church number is a representation of a number in lambda calculus.
 */
public class ChurchNumber extends Term {

    public final int value;

    /**
     * Get the value of the church number as a numerical value and initializes a ChurchNumber.
     *
     * @param value The value as Integer.
     */
    public ChurchNumber(int value) {
        this.value = value;
    }

    @Override
    public <T> T accept(TermVisitor<T> visitor) {
        return visitor.visit(this);
    }

    /**
     * This method takes the numerical number and returns the number as a lambda expression.
     * Every number can be represented as an abstraction.
     *
     * @return The number which got converted into an abstraction.
     */
    public Abstraction getAbstraction() {
        return null;
    }
    
}
