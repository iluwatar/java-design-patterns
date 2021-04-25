package Data_Transfer_Hash;
/*Business objects
Create hashes for sending data, or use values in received hashes.*/
public class Business {
    public void create(String k,Object v,Hash hash)
    {
        hash.create(k, v);
        
    }
    public Object get(String k,Hash hash)
    {  
        return  hash.get(k);
    }
}
