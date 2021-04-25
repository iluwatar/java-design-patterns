package Data_Transfer_Hash;

import java.util.Hashtable;
/*Data transfer hash
Stores data for transport between layers as a set of values associated with well-known keys.*/
public class Hash {
    private Hashtable hash;
    Hash()
    {hash=new Hashtable();}
    public void create(String k,Object v)
    {
        this.hash.put(k, v);
    }
    public Object get(String k)
    {
        return  this.hash.get(k);
    }
}
