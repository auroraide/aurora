package aurora.backend.betareduction;

import aurora.backend.tree.Term;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BetaReductionIterator implements Iterator<Term> {
    private final BetaReducer betaReducer;
    private boolean finished;
    private Term next;

    public BetaReductionIterator(BetaReducer betaReducer, Term start) {
        this.betaReducer = betaReducer;
        next = start;
        // to see if there is atleast one redex we have to test this here in the constructor
        betaReducer.reduce(start);
        finished = betaReducer.getFinished();

    }

    @Override
    public boolean hasNext() {
        return !finished;
    }

    @Override
    public Term next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Term last = next;
        next = betaReducer.reduce(last);
        finished = betaReducer.getFinished();
        return last;
    }
}
