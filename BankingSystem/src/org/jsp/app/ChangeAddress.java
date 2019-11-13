package org.jsp.app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChangeAddress")
public class ChangeAddress extends HttpServlet
{
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
	// TODO Auto-generated method stub
	String aNumber=request.getParameter("aNumber");
	String address=request.getParameter("newAddress");
	
	String url="jdbc:mysql://localhost:3306?user=root&password=9790270846";
	String query="update test.usertable set Address=? where AccountId=?";
	
	try 
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url);
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setString(1, address);
		preparedStatement.setLong(2, Long.parseLong(aNumber));
		preparedStatement.executeUpdate();
		connection.close();
	}
	catch (Exception e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
