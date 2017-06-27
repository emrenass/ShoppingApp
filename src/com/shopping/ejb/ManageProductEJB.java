package com.shopping.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.shopping.model.Product;

/**
 * Session Bean implementation class ProductSessionBean
 */


@Stateless(mappedName = "manageProductEJB")
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class ManageProductEJB {
	private SessionFactory factory;

	@PostConstruct
	public void init() {
		try{
			factory = new Configuration().configure().buildSessionFactory();
		}catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
	}

	public Integer addProduct(String name, int quantity, double price, String category) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer productID = null;

		try {
			tx = session.beginTransaction();
			Product product = new Product(name, quantity, price, category);
			productID = (Integer) session.save(product);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

		return productID;
	}

	public List<Product> listProducts(int elementsPerBlock, int page) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Product> products = new ArrayList<Product>();

		try {
			tx = session.beginTransaction();
			products = session.createQuery("FROM Product")
					.setFirstResult(elementsPerBlock * (page) + 1 )
					.setMaxResults(elementsPerBlock+1)
					.list();
			System.out.println("New list");
			for(Product a : products)
				System.out.println(a);

			tx.commit();
		}catch (HibernateException e) {
			if (tx !=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

		return products;
	}

	public List<Product> search(String name){
		Session session = factory.openSession();
		Transaction tx = null;
		List<Product> products = new ArrayList<Product>();

		try {
			tx = session.beginTransaction();
			List<Product> query= session.
					createQuery("from Person where userName=:name").
					setParameter("name", name).setMaxResults(1000).list();
			for(Product a : products)
				System.out.println(a);

			tx.commit();
		}catch (HibernateException e) {
			if (tx !=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

		return products;
	}

	public void updateProductPrice(Integer productID, double price ){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Product product = 
					(Product) session.get(com.shopping.model.Product.class, productID);
			product.setPrice(price);
			session.update(product); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}

	public void updateProductQuantity(Integer productID, int quantity){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Product product = 
					(Product) session.get(com.shopping.model.Product.class, productID);
			product.setQuantity(quantity);
			session.update(product); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}

	public void deleteProduct (Integer Product){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Product product = 
					(Product)session.get(com.shopping.model.Product.class, Product); 
			session.delete(product); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}
}
