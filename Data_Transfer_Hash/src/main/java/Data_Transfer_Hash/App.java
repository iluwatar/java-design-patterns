package Data_Transfer_Hash;
/*
Data transfer hashes may be implemented as simple hash tables (or HashMaps). 
A more robust implementation uses a container object to hold the hash, as well as identifying information, type-safe data retrieval methods, and well-known keys.
*/

public class App 
{
    public static void main( String[] args )
    {
        Hash hash= new Hash();
        Business business=new Business();
        Presentation presentation=new Presentation();
        business.create("a", "aaa", hash);
        System.out.println("Business create "+"aaa");
        business.create("b", "bbb", hash);
        System.out.println("Business create "+"bbb");
        business.create("c", "ccc", hash);
        System.out.println("Business create "+"ccc");

        presentation.get("a", hash);
        System.out.println("Presentation heard "+presentation.get("a", hash));
        presentation.get("b", hash);
        System.out.println("Presentation heard "+presentation.get("b", hash));

        
        
    }

    
}
