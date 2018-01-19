package aurora.backend.tree;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChurchNumberTestGenerateAbstraction {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testzero() {
       ChurchNumber churchzero = new ChurchNumber(0);
        Abstraction a = churchzero.getAbstraction();
        Abstraction abody = (Abstraction)a.body;
        BoundVariable bound = (BoundVariable)abody.body;
        assertEquals(1,bound.index);

    }
    @Test
    public void testone() throws Exception{
    ChurchNumber churchone = new ChurchNumber(1);
    Abstraction a = churchone.getAbstraction();
    Abstraction abody = (Abstraction)a.body;
    Application app = (Application)abody.body;
    BoundVariable left = (BoundVariable)app.left;
    BoundVariable right = (BoundVariable)app.right;
    assertEquals(2,left.index);
    assertEquals(1,right.index);
    }

    @Test
    public void testtwo() throws Exception{
        ChurchNumber churchtwo = new ChurchNumber(2);
        Abstraction a = churchtwo.getAbstraction();
        Abstraction abody = (Abstraction)a.body;
        Application app = (Application)abody.body;
        BoundVariable left = (BoundVariable)app.left;
        assertEquals(2,left.index);
        Application appright = (Application) app.right;
        BoundVariable apprightleft = (BoundVariable)appright.left;
        BoundVariable apprightright = (BoundVariable) appright.right;
        assertEquals(2,apprightleft.index);
        assertEquals(1,apprightright.index);
    }
}