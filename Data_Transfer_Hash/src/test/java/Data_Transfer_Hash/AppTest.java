package Data_Transfer_Hash;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppTest 
{
    @Test
    public void getTrueCvreate()
    {
        Hash hash= new Hash();
        Business business=new Business();
        business.create("a", "aaa", hash);
        
    }
    @Test
    public void getTrueAnswer()
    {
        Hash hash= new Hash();
        Business business=new Business();
        Presentation presentation=new Presentation();
        business.create("a", "aaa", hash);
        business.create("b", "bbb", hash);
        business.create("c", "ccc", hash);

        assertEquals(presentation.get("a", hash),"aaa");
        assertEquals(presentation.get("b", hash),"bbb");
        
    }
    @Test
    public void getTrueAnswerAfterChange()
    {
        Hash hash= new Hash();
        Business business=new Business();
        Presentation presentation=new Presentation();
        business.create("a", "aaa", hash);
        business.create("b", "bbb", hash);
        business.create("c", "ccc", hash);

        assertEquals(presentation.get("a", hash),"aaa");
        assertEquals(presentation.get("b", hash),"bbb");
        business.create("a", "abc", hash);
        assertEquals(presentation.get("a", hash),"abc");
    }
}
