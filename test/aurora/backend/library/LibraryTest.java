package aurora.backend.library;

import aurora.backend.library.exceptions.LibraryItemNotFoundException;
import aurora.backend.tree.Abstraction;
import aurora.backend.tree.BoundVariable;
import aurora.backend.tree.FreeVariable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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

    @Test
    public void libitem() {
        LibraryItem item = new LibraryItem("name", "desc", new FreeVariable("a"));
        assertEquals("desc", item.getDescription());
    }

    @Test
    public void multlib() {
        List<Library> l = new ArrayList<Library>();
        HashLibrary h1 = new HashLibrary();
        HashLibrary h2 = new HashLibrary();
        h1.define("test", "test", new FreeVariable("a"));
        h2.define("test2", "test2", new FreeVariable("b"));
        l.add(h1);
        l.add(h2);
        MultiLibrary ml = new MultiLibrary(l);
        try {
            LibraryItem libraryItem = ml.getItem("test");
            assertEquals("test", libraryItem.getDescription());
        } catch (LibraryItemNotFoundException e) {
            e.printStackTrace();
            fail();
        }
        boolean nothere = false;
        try {
            LibraryItem libraryItem = ml.getItem("nothere");
        } catch (LibraryItemNotFoundException e) {
            nothere = true;
        }
        assertEquals(nothere, true);

        HashLibrary h3 = new HashLibrary();
        h3.define("test3", "test3", new FreeVariable("c"));

        ml.define(h3);
        assertEquals(ml.exists("test3"), true);

        ml.remove("test3");
        assertEquals(ml.exists("test3"), false);

        Iterator it = ml.iterator();
        if (it.hasNext()) {
            it.next();
        }
        if (it.hasNext()) {
            it.next();
        }
        assertEquals(it.hasNext(), false);
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assertEquals(thrown,true);

        LibraryItem item = new LibraryItem("h4", "h4", new FreeVariable("d"));
        ml.define(item);
        assertEquals(ml.exists("h4"), true);

        try {
            LibraryItem item1 = ml.getItem("h4");
            assertEquals(item1.getDescription(), "h4");
        } catch (LibraryItemNotFoundException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void checkexception() {
        LibraryItemNotFoundException ex = new LibraryItemNotFoundException("test");
        assertEquals(ex.getMessage(), "test");
    }
}
