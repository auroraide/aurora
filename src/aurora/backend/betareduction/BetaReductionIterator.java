package aurora.backend.betareduction;

import aurora.backend.tree.Term;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BetaReductionIterator implements Iterator<Term> {
    private final BetaReducer betaReducer;

    private Term next;

    public BetaReductionIterator(BetaReducer betaReducer, Term start) {
        this.betaReducer = betaReducer;
        next = betaReducer.reduce(start);
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public Term next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Term last = next;
        next = betaReducer.reduce(last);
        return last;
    }
}