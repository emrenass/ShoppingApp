package com.shopping.controller;

import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.shopping.model.Product;

public class ManageProduct {
	private static SessionFactory factory;
	public ManageProduct() {
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

	public List<Product> listProducts() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Product> products = new ArrayList<Product>();

		try {
			tx = session.beginTransaction();
			products = session.createQuery("FROM Product").list();
			for (Iterator iterator = products.iterator(); iterator.hasNext();){
				Product product = (Product) iterator.next();
				System.out.print("Name: " + product.getName()); 
				System.out.print(" Quantity: " + product.getQuantity()); 
				System.out.print(" Price: " + product.getPrice());
				System.out.println(" Category: " + product.getCategory().toString());
			}
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
