package com.iluwatar.commander;

import com.iluwatar.commander.employeehandle.EmployeeDatabase;
import com.iluwatar.commander.employeehandle.EmployeeHandle;
import com.iluwatar.commander.exceptions.DatabaseUnavailableException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


public class EmployeeHandleTest {

    @Test
    public void testReceiveId() throws DatabaseUnavailableException {
        EmployeeDatabase database = Mockito.mock(EmployeeDatabase.class);

        when(database.get("123")).thenReturn(null);

        assertNull(database.get("123"));
    }

    @Test
    public void testUpdateDb() throws DatabaseUnavailableException {

        EmployeeDatabase database = Mockito.mock(EmployeeDatabase.class);
        Order order = Mockito.mock(Order.class);
        Service service = new EmployeeHandle(database, new DatabaseUnavailableException());

        service.updateDb(order);
        Mockito.verify(database).add(order);
    }
}
