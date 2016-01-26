/**
 * Created by ARINDAM on 1/25/2016.
 *
 * This is the Transfer Object , which is imnplemented by the business layer ,
 * and finally passed to the client .
 *
 */





public class EmployeeTO {

  private String name ;
    private int emp_ID ;

    EmployeeTO(String name ,int emp_ID){
        this.name = name ;
        this.emp_ID= emp_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmp_ID() {
        return emp_ID;
    }

    public void setEmp_ID(int emp_ID) {
        this.emp_ID = emp_ID;
    }
}
