import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.vividsolutions.jts.index.bintree.Root;

public class ManageEmployeeNative 
{
   private static SessionFactory factory; 
   
   private static Session session;
   private static Transaction tx;
   private static Integer employeeID;
   public ManageEmployeeNative()
   {
	   factory = new Configuration().configure().buildSessionFactory();
	   session = factory.openSession();
	   tx= null;
   }
   
   public static void main(String[] args) {
      
      ManageEmployeeNative ME = new ManageEmployeeNative();

      /* Add few employee records in database */
      Integer empID1 = ME.addEmployee("Zara", "Ali", 2000);
      Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
      Integer empID3 = ME.addEmployee("John", "Paul", 5000);
      Integer empID4 = ME.addEmployee("Mohd", "Yasee", 3000);

      /* List down employees and their salary using Scalar Query */
      ME.listEmployeesEntity( );
      System.out.println("Using criteria query:");
      ME.listEmployees();
      System.out.println("Using criteria query delete:");
      //ME.updateCriteria(2000,10000);
      //ME.listEmployees();
      ME.criDel(empID1);
      ME.criDel(empID2);
      ME.listEmployeesEntity( );
      
   }
   
   /* Method to CREATE an employee in the database */
   public Integer addEmployee(String fname, String lname, int salary){
      
	   session = factory.openSession();
      try {
         tx = session.beginTransaction();
         Employee employee = new Employee(fname, lname, salary);
         employeeID = (Integer) session.save(employee); 
         tx.commit();
      }
      catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }
      finally {
         session.close(); 
      }
      return employeeID;
   }

   public void listEmployeesEntity( ){
	     
	   session = factory.openSession();
	      try {
	         tx = session.beginTransaction();
	         String sql = "SELECT * FROM EMPLOYEE4";
	         SQLQuery query = session.createSQLQuery(sql);
	         query.addEntity(Employee.class);
	         List employees = query.list();

	         for (Iterator iterator = employees.iterator(); iterator.hasNext();){
	            Employee employee = (Employee) iterator.next(); 
	            System.out.print("Id: " + employee.getId()); 
	            System.out.print("  First Name: " + employee.getFirstName()); 
	            System.out.print("  Last Name: " + employee.getLastName()); 
	            System.out.println("  Salary: " + employee.getSalary()); 
	         }
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	   }
   

	   /* Method to  READ all the employees having salary more than 2000 using criteria query*/
	   @SuppressWarnings("deprecation")
	public void listEmployees( ) {
		   session = factory.openSession();
	      
	      try {
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(Employee.class);
	         // Add restriction salary>2000
	         cr.add(Restrictions.gt("salary", 2000));
	         cr.add(Restrictions.like("firstName","J%"));
	         List employees = cr.list();

	         for (Iterator iterator = employees.iterator(); iterator.hasNext();){
	            Employee employee = (Employee) iterator.next(); 
	            System.out.print("Id: " + employee.getId()); 
	            System.out.print("  First Name: " + employee.getFirstName()); 
	            System.out.print("  Last Name: " + employee.getLastName()); 
	            System.out.println("  Salary: " + employee.getSalary()); 
	         }
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	   }

	   
	   /*public void updateCriteria(int oldSalary,int newSalary) 
	   {
	        CriteriaBuilder cb = this.em.getCriteriaBuilder();

	        // create update
	       CriteriaUpdate<Employee> update = (CriteriaUpdate) cb.createCriteriaUpdate(Employee.class);

	        // set the root class
	        javax.persistence.criteria.Root<Employee> e = update.from(Employee.class);
		     

	        // set update and where clause
	        update.set("salary", newSalary);
	        update.where(cb.greaterThanOrEqualTo(e.get("salary"), oldSalary));

	        // perform update
	        this.em.createQuery(update).executeUpdate();
		      }
		      
	    }*/
public void criDel(int newid)
{
	session = factory.openSession();
	try
	{
		tx = session.beginTransaction();
		Employee emp = (Employee ) session.createCriteria(Employee.class)
	            .add(Restrictions.eq("id", newid)).uniqueResult();
	session.delete(emp);
	tx.commit();
	}
	catch (HibernateException e) {
        if (tx!=null) tx.rollback();
        e.printStackTrace(); 
     } finally {
        session.close(); 
     } 
}

}	   
	   