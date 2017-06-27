package com.shopping.controller;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.shopping.model.Cart;
import com.shopping.model.Person;
import com.shopping.model.Product;

public class ManageCart {

	private SessionFactory factory;

	
	public ManageCart() {
		try{
			factory = new Configuration().configure().buildSessionFactory();
		}catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
	}

	public Integer addToChart(int userId, int productId, int quantity) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer CartId = null;
		try{
			tx = session.beginTransaction();
			Product product = (Product) session.get(Product.class, productId);
			if(product.getQuantity()-quantity >=0) {
				Cart cart = new Cart(userId, productId, quantity);
				CartId = (Integer) session.save(cart);
				product.setQuantity(product.getQuantity()-quantity);
			}
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return CartId;
	}

	public void deleteProductFromCart(Integer CartId){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Cart cart = 
					(Cart)session.get(Cart.class, CartId); 
			session.delete(cart); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}

	public List listCart(int userId){
		Session session = factory.openSession();
		Transaction tx = null;
		List carts = null;
		try{
			tx = session.beginTransaction();
			carts = session.createQuery("FROM Cart where userID = " + userId).list(); 
			for (Iterator iterator = 
					carts.iterator(); iterator.hasNext();){
				Cart cart = (Cart) iterator.next(); 
				System.out.print("User ID: " + cart.getUserId()); 
				System.out.print("  Product ID: " + cart.getProductId());
				System.out.println("  Quantity: " + cart.getQuantity()); 
			}
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return carts;
	}

	/*public static void main(String[] args) {
		ManageCart MC = new ManageCart();
		ManageProduct MP = new ManageProduct();
		ManagePerson MPerson = new ManagePerson();

		MP.listProducts();
		Integer cartID1 = MC.addToChart(2, 7, 2);

		System.out.println("*************************************");

		MP.listProducts();
	}*/

}
