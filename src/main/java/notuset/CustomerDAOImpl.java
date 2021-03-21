package notuset;

import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    /*//need to inject the session factory
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {
        // get the cur hibernate session
        Session session = sessionFactory.getCurrentSession();

        // create a query ... sort by last name
        Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);

        // execute query and get rs list
        List<Customer> customers = query.getResultList();

        return customers;
    }

    @Override
    public void saveCustomer(Customer customer) {
        Session session = sessionFactory.getCurrentSession();

        //save/update(if have id) customer
        session.saveOrUpdate(customer);
    }

    @Override
    public Customer getCustomer(int theId) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Customer.class, theId);
    }

    @Override
    public void deleteCustomer(int theId) {
        Session session = sessionFactory.getCurrentSession();

        // delete obj with pk
        Query query = session.createQuery("delete from Customer where id=:customerId");
        query.setParameter("customerId", theId);
        query.executeUpdate();
    }*/
}
