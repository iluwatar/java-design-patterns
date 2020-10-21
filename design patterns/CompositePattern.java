public class CompositePattern{
	public static void main(String args[]){
		Employee CEO= new Employee("John","CEO",30000);
		Employee headsale= new Employee("robert","head sale",20000);
		Employee clerk= new Employee("dave","cleark",10000);
		Employee headMarketing= new Employee("robert steve","head Marketing",20000);
		CEO.add(headsale);
		CEO.add(headMarketing);
		headsale.add(clerk);
		for (Employee headEmployee : CEO.getSubordinates()){
			System.out.println(headEmployee);
			for (Employee employee : headEmployee.getSubordinates()){
				System.out.println(employee);
			}
		}
		
	}
}