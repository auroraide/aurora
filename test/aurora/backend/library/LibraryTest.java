package aurora.backend.library;

import aurora.backend.tree.Abstraction;
import aurora.backend.tree.BoundVariable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class LibraryTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testiterator() {
        Library library = new HashLibrary();
        library.define("ntest1","desctest1",new Abstraction(
                new BoundVariable(1),"x"
        ));
        library.define("test2","test2",new Abstraction(
                new BoundVariable(1),"y"));
        library.define("test3","test3",new Abstraction(
                new BoundVariable(1),"z"));
        Iterator it = library.iterator();
        assertEquals(it.hasNext(),true);


    }
}