package aurora.shared.backend.resultHandler;

import aurora.shared.backend.tree.Term;

public interface ResultGuesser<T extends Term> {
    T guess(Term t);
}
