package com.shopping.controller;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator; 

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.shopping.model.Person;

public class ManagePerson {
	private SessionFactory factory; 

	public ManagePerson() {
		try{
			factory = new Configuration().configure().buildSessionFactory();
		}catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
	}
	
	public Integer getUserID(String userName) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer userId = null;
		try{
			tx = session.beginTransaction();
			Query query= session.
			        createQuery("from Person where userName=:name");
			query.setParameter("name", userName);
			Person person = (Person) query.uniqueResult();
			userId = person.getId();
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			System.out.println("*******************************************"+userId);
			session.close(); 
		}
		return userId;
	}

	public Integer addPerson(String userName, String password, String fname, String lname, String phoneNumber){

		Session session = factory.openSession();
		Transaction tx = null;
		Integer personID = null;
		try{
			tx = session.beginTransaction();
			Person person = new Person(userName, password,fname, lname, phoneNumber);
			personID = (Integer) session.save(person); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return personID;
	}
	public void listPeople( ){
		try{
			factory = new Configuration().configure().buildSessionFactory();
		}catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			List people = session.createQuery("FROM Person").list(); 
			for (Iterator iterator = 
					people.iterator(); iterator.hasNext();){
				Person person = (Person) iterator.next(); 
				System.out.print("First Name: " + person.getFirstName()); 
				System.out.print("  Last Name: " + person.getLastName()); 
				System.out.println("  Phone Number: " + person.getPhoneNumber()); 
			}
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}
	
	public boolean isValid(String username, String password) {
		boolean valid = false;
		System.out.println("Girdi");
		try{
			factory = new Configuration().configure().buildSessionFactory();
		}catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			List person = session.createQuery("select username, password from person where username = " + "\""+ username + "\"" + " and password"
					+ " = " +"\""+ password +"\"" + ";").list(); 
			System.out.println(person.size());
			tx.commit();
			if(person.size() == 1)
				valid = true;
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close();
		}
		return valid;
		
	}
	public void updatePerson(Integer personID, String phoneNumber ){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Person person = 
					(Person)session.get(com.shopping.model.Person.class, personID); 
			person.setPhoneNumber( phoneNumber );
			session.update(person); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}

	public void deletePerson(Integer personID){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Person person = 
					(Person)session.get(com.shopping.model.Person.class, personID); 
			session.delete(person); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}

	public boolean listEnteredUser(String username, String password ){
		try{
			factory = new Configuration().configure().buildSessionFactory();
		}catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
		Session session = factory.openSession();
		Transaction tx = null;
		List people = null;
		try{
			tx = session.beginTransaction();
			String usern = "'" + username + "'";
			String psswrd = "'" + password + "'";
			people = session.createQuery("Select userName, password from Person where username = "+usern+" and password = "+psswrd).list(); 
			if(people.iterator().hasNext())
				return true;
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return false;
	}
}