/**
 * Created by ARINDAM on 1/25/2016.
 *
 * Here in this client , there is no need to call getMethods multiple times .
 * The Businees Object creates a Transfer Object and pass it on to the client .
 *
 * THIS IS THE TRANSFER OBJECT PATTERN
 *
 *
 */
public class Client {



    public static void main (String a[]){

        EmployeeBO businessObject = new EmployeeBO() ;

        // Get all the employees

        for(EmployeeTO et:businessObject.getAllEmployee() ){
            System.out.println("EMPLOYEE DETAILS: EMP_NAME : " + et.getName()+ ", EMP_ID : " + et.getEmp_ID());
        }

        EmployeeTO empTO = businessObject.getAllEmployee().get(0);


        empTO.setName("Arindam Mishra");


        businessObject.updateEmployee(empTO);


       empTO = businessObject.getEmployee(0);


        System.out.println("Updated details :" +empTO.getEmp_ID()+","+empTO.getName());





    }


}
