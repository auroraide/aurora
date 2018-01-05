package aurora.shared.backend;

import aurora.shared.backend.tree.Term;

import java.util.Map;

/**
 * Usually there should be two instances of this class: Standard library, and then the user library.
 */
public class Library {
    private Map<String, Term> map;

    public Term getTerm(String name) {
        return null;
    }

    public void defTerm(String name, Term term) {
    }
}
