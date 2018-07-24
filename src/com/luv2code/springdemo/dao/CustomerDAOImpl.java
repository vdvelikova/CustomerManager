package com.luv2code.springdemo.dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession(); // Session obj from the HIbernate
		
		//create a query .. sort by last name
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName",
																					Customer.class);
		
		//execute the query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		//return the list/result
		return customers;
	}

	// save the customer to the DB via Hibernate
	@Override
	public void saveCustomer(Customer theCustomer) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theCustomer);		
	}

	@Override
	public Customer getCustomer(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		// retrieve/ read from database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery =currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		//generic method
		theQuery.executeUpdate();		
	}

}
