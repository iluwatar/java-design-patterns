package com.data.transfer.object;

/**
 * Created by ARINDAM on 1/25/2016.
 *
 * This is the Business Class , which creates the Transfer Object .
 *
 *
 */



import java.util.ArrayList;
import java.util.List;


public class EmployeeBO {


    // Here we will store the employee details in a List . It is acting like a db .

    List<EmployeeTO> employees;

   public EmployeeBO(){

       employees = new ArrayList<EmployeeTO>();
       EmployeeTO emp1 = new EmployeeTO("Arindam",11);
       EmployeeTO emp2 = new EmployeeTO("Peter",22);

       employees.add(emp1);
       employees.add(emp2);

    }


    public void deleteEmployee(EmployeeTO empTo){

        employees.remove(empTo.getEmp_ID());

        System.out.println("Employee deatils deleted : " + empTo.getName() + "," + empTo.getEmp_ID());

    }


    public List<EmployeeTO> getAllEmployee(){

        return employees ;

    }

    public EmployeeTO getEmployee(int id){
         return employees.get(id);
    }

    public void updateEmployee(EmployeeTO emp){

        EmployeeTO temp ;

        for(int i=0;i<employees.size();i++){
            temp = employees.get(i);
            if(temp .getEmp_ID()==emp.getEmp_ID()){
                temp.setName(emp.getName());
            }
        }

        System.out.println("Employee details for ID : "+"'"+emp.getEmp_ID()+"'"+" are updated");
    }



}

