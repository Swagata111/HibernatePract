import javax.transaction.Transaction;
import javax.websocket.Session;

import org.hibernate.SessionFactory;

public class EmployeeDAOImpl extends EmployeeDao{

    private SessionFactory sessionFactory;

    @Override
    public void saveEmployee(Employee e) {
        org.hibernate.Session session = this.sessionFactory.openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        session.persist(e);
        tx.commit();
        session.close();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}