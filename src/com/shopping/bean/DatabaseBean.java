package com.shop.bean;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.shopping.controller.ManageCart;
import com.shopping.controller.ManagePerson;
import com.shopping.controller.ManageProduct;
import com.shopping.ejb.ManageCartEJB;
import com.shopping.ejb.ManageProductEJB;
import com.shopping.model.LazyProductModel;
import com.shopping.model.Product;

public class DatabaseBean {

	private List<Product> products;
	private List<Product> filteredProducts;
	private List<Product> cart;
	String userName;
	int userId;
	String userIdString;
	int productId;
	String productIdString;
	int quantity;
	String quantityString;

	private int page = 0;
	private int pageLast = 0;
	private LazyDataModel<Product> lazyModel;
	private LazyProductModel lazyProduct;

	@EJB
	ManageCartEJB cartEJB;

	@EJB
	ManageProductEJB productEJB;

	@PostConstruct
	public void init(){
		lazyProduct = new LazyProductModel(productEJB.listProducts(10, 0));
		lazyModel = lazyProduct;
		products = productEJB.listProducts(10, 0);
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		LoginBean bean = (LoginBean) elContext.getELResolver().getValue(elContext, null, "loginBean");
		userName = bean.getName();
		userId = getUserID(userName);
		cart = cartEJB.listCart(userId);

	}

	public void update(PageEvent event){
		page = event.getPage();
		if(pageLast<page)
		{
			lazyProduct.getDatasource().addAll(productEJB.listProducts(10, page));
			lazyModel = lazyProduct;
			pageLast = page;
			products.addAll(productEJB.listProducts(10, page));
		}
		
		System.out.println(page);
		
	}

	public LazyDataModel<Product> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Product> lazyModel) {
		this.lazyModel = lazyModel;
	}



	public List<Product> getFilteredProducts() {
		return filteredProducts;
	}
	public void setFilteredProducts(List<Product> filteredProducts) {
		this.filteredProducts = filteredProducts;
	}

	public List<Product> getProducts() {
		return products;
	}

	public List<Product> getCart() {
		return cart;
	}

	public int getUserID(String userName)
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = 
				fc.getExternalContext().getRequestParameterMap();
		//String userName = params.get("userId");
		//System.out.println("********************************** Name: " + userName);
		ManagePerson mperson = new ManagePerson();
		userId = mperson.getUserID(userName);
		return userId;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void addtoCart(){
		//userId = getUserID();
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = 
				fc.getExternalContext().getRequestParameterMap();
		String productIdString =  params.get("productId"); 
		int productId = Integer.parseInt(productIdString);
		cartEJB.addToChart(userId, productId, 1);
		cart = cartEJB.listCart(userId);
		//lazyModel = new LazyProductModel(productEJB.listProducts(10, page));
		//products = productEJB.listProducts(10, page);
		System.out.println("Girdi");
		productEJB.search("Computer");

		/*for (int i = 0; i < 700000; i++) {
			productEJB.addProduct("GPU", 70, 2500, "Hardware");
			System.out.println("Girdi: " + i);
		}*/
	}
}
