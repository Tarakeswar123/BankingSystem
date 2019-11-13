package org.jsp.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/NewMobile")
public class NewMobile extends HttpServlet
{
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
	// TODO Auto-generated method stub
	String newMobileNumber=request.getParameter("newNumber");
	String aNumber=request.getParameter("aNumber");
	String cNewMobileNumber=request.getParameter("cNewNumber");
	PrintWriter printWriter=response.getWriter();
	
	String url="jdbc:mysql://localhost:3306?user=root&password=9790270846";
	String query2="update test.usertable set MobileNumber=? where AccountId=?";
	
	if(Long.parseLong(newMobileNumber)==Long.parseLong(cNewMobileNumber))
	{
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection2=DriverManager.getConnection(url);
		PreparedStatement preparedStatement2=connection2.prepareStatement(query2);
		preparedStatement2.setLong(1, Long.parseLong(newMobileNumber));
		preparedStatement2.setLong(2, Long.parseLong(aNumber));
		preparedStatement2.executeUpdate();
		connection2.close();
		printWriter.println("Mobile Number was Changed Successfully");
	}
	catch (Exception e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	else
	{
		printWriter.println("New Mobile Number was Not Matching with Confirm Mobile Number");
	}
}
}
