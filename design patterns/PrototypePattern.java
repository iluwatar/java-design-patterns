import java.util.ArrayList;
import java.util.List;


public class PrototypePattern {

	public static void main(String[] args) throws CloneNotSupportedException {
		EmpProto emps = new EmpProto();
		emps.loadData();
		
		//Use the clone method to get the Employee object
		EmpProto empsNew = (EmpProto) emps.clone();
		EmpProto empsNew1 = (EmpProto) emps.clone();
		List<String> list = empsNew.getEmpList();
		list.add("John");
		List<String> list1 = empsNew1.getEmpList();
		list1.remove("Pankaj");
		
		System.out.println("emps List: "+emps.getEmpList());
		System.out.println("empsNew List: "+list);
		System.out.println("empsNew1 List: "+list1);
	}

}