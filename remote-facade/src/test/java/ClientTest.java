import com.iluwatar.remote.facade.AssemblersFacades.CustomerDTOAssembler;
import com.iluwatar.remote.facade.AssemblersFacades.RemoteFacade;
import com.iluwatar.remote.facade.Domain.Customer;
import com.iluwatar.remote.facade.Domain.Domain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClientTest {

    Customer AMY = new Customer("Amy Adams", "0490490490", "88 Radford Street");
    Customer JOHN = new Customer("John Adams", "0449944994", "12 Kingsford Street");

    @Test
    public void checkClientAMY(){
        RemoteFacade.makeClient(CustomerDTOAssembler.makeCustomerDTO(AMY));
        Assertions.assertEquals("Amy Adams", Domain.customers.get(1).getName());
        Assertions.assertEquals("0490490490", Domain.customers.get(1).getPhone());
        Assertions.assertEquals("88 Radford Street", Domain.customers.get(1).getAddress());

    }
    @Test
    public void checkClientJOHN(){
        RemoteFacade.makeClient(CustomerDTOAssembler.makeCustomerDTO(JOHN));
        Assertions.assertEquals("John Adams", Domain.customers.get(0).getName());
        Assertions.assertEquals("0449944994", Domain.customers.get(0).getPhone());
        Assertions.assertEquals("12 Kingsford Street", Domain.customers.get(0).getAddress());
    }
    @Test
    public void checkCustomerAddition(){
        Assertions.assertEquals(2, Domain.customers.size());
    }
}
