import java.util.ArrayList;
import java.util.List;

class EmpProto implements Cloneable{

	private List<String> empList;
	
	public EmpProto(){
		empList = new ArrayList<String>();
	}
	
	public EmpProto(List<String> list){
		this.empList=list;
	}
	public void loadData(){
		//read all employees from database and put into the list
		empList.add("Pankaj");
		empList.add("Raj");
		empList.add("David");
		empList.add("Lisa");
	}
	
	public List<String> getEmpList() {
		return empList;
	}

	@Override
	public Object clone() throws CloneNotSupportedException{
			List<String> temp = new ArrayList<String>();
			for(String s : this.getEmpList()){
				temp.add(s);
			}
			return new EmpProto(temp);
	}
	
}
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