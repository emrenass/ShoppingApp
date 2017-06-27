package com.shopping.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shopping.controller.ManageCart;

/**
 * Servlet implementation class BuyServlet
 */
public class BuyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("gette");
		ManageCart mc = new ManageCart();

		PrintWriter out = response.getWriter();
		out.println("Welcome");
		try{
		String value = request.getParameter("BuyButtonValue");
		int valueInt = Integer.parseInt(value);
		mc.addToChart(1, valueInt, 1);
		out.println("You tried to bought: " + value);
		}catch(NumberFormatException e)  {
			e.printStackTrace();
		}
	}

}
