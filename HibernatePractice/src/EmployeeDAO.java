import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class EmployeeDAO {
	
	Configuration configuration;
	SessionFactory factory;
	
	public EmployeeDAO() {
		configuration= new Configuration();
		configuration.configure();
		factory=configuration.buildSessionFactory();
	}
	
	public ArrayList<Employee> getAllEmployee_HQL(){
		Session session= factory.openSession();
		Transaction transaction= session.beginTransaction();
		Query query= session.createQuery("from Employee");
		ArrayList<Employee> empList= (ArrayList<Employee>) query.list();
		transaction.commit();
		session.close();
		return empList;
	}
	
	public int addNewEmployee_HQL(Employee emp) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		Integer empID =(Integer) session.save(emp);
		transaction.commit();
		session.close();
		return empID;
	}
	
	public void updateEmployeeSalary(int empId, float empSalary) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		Employee emp =(Employee) session.get(Employee.class, empId);
		System.out.println(emp);
		emp.setEmpSal(emp.getEmpSal()+empSalary);
		session.update(emp);
		transaction.commit();
		session.close();
	}
	public void deleteEmployeeByEmpID(int empId) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		Employee emp =(Employee) session.get(Employee.class, empId);
		session.delete(emp);
		transaction.commit();
		session.close();
	}	
	
	public ArrayList<Employee> getEmployeeByCriteriaSal_Name(String empName,float empSalary){
		Session session= factory.openSession();
		Transaction transaction= session.beginTransaction();
		//Query query= session.createQuery("from Employee");
		Criteria query= session.createCriteria(Employee.class).add(Restrictions.gt("empSal", empSalary))
				.add(Restrictions.like("empNm", empName));
		ArrayList<Employee> empList= (ArrayList<Employee>) query.list();
		transaction.commit();
		session.close();
		return empList;
	}
	
	public static void main(String[] args) {
		EmployeeDAO dao = new EmployeeDAO();
		
		System.out.println(dao.getEmployeeByCriteriaSal_Name("A%",5000));
		//Before
		System.out.println("Before Any changes");
		ArrayList<Employee> empListBefore =dao.getAllEmployee_HQL();
		for(Employee emp: empListBefore) {
			System.out.println(emp.getEmpNm() +"==="+emp.getEmpSal());
		}
		
		//Adding new Employee
		int empId= dao.addNewEmployee_HQL(new Employee("Ashish", 4000.00f));
		int empId1= dao.addNewEmployee_HQL(new Employee("Ashwini", 4000.00f));
	System.out.println("Employee Id is "+ empId1);
		
		//update Employee Salary
		dao.updateEmployeeSalary(311, 3000.00f);
		
		//delete employee By Id
		//dao.deleteEmployeeByEmpID(3);
		//After
		System.out.println("After Any changes");
		//Retrieving All Employee Names
		ArrayList<Employee> empListAfter =dao.getAllEmployee_HQL();
		for(Employee emp: empListAfter) {
			System.out.println(emp.getEmpNm() +"==="+emp.getEmpSal());
		}
	}
	
	

}
