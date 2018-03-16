package aurora.backend.library;

import aurora.backend.tree.Abstraction;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.FreeVariable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    @Test
    public void addlibrary() {
        Library og = new HashLibrary();
        og.define("no1", "no1", new FreeVariable("a"));

        Library ng = new HashLibrary();
        ng.define("no2", "no2", new FreeVariable("b"));

        og.define(ng);
        Iterator it = og.iterator();
        assertNotNull(it.next());
        assertNotNull(it.next());
        assertEquals(it.hasNext(),false);

        og.remove("no1");

        Iterator ti = og.iterator();
        assertNotNull(ti.next());
        assertEquals(ti.hasNext(), false);

    }
}