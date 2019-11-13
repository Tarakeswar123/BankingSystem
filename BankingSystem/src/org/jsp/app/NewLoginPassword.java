package org.jsp.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/NewLoginPassword")
public class NewLoginPassword extends HttpServlet
{
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
	// TODO Auto-generated method stub
	String aNumber=request.getParameter("aNumber");
	String oldLoginPassword=request.getParameter("oldLoginPassword");
	String newLoginPassword=request.getParameter("newLoginPassword");
	String cNewLoginPassword=request.getParameter("cNewLoginPassword");
	PrintWriter printWriter=response.getWriter();
	
	String url="jdbc:mysql://localhost:3306?user=root&password=9790270846";
	String query="select * from test.usertable where AccountId=? and LoginPassword=?";
	
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url);
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setLong(1, Long.parseLong(aNumber));
		preparedStatement.setString(2, oldLoginPassword);
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next())
		{
			String password=resultSet.getString("LoginPassword");
			if(password.equals(oldLoginPassword))
			{
				if(newLoginPassword.equals(cNewLoginPassword))
				{
				String query2="update test.usertable set LoginPassword=? where AccountId=?";
				Connection connection2=DriverManager.getConnection(url);
				PreparedStatement preparedStatement2=connection2.prepareStatement(query2);
				preparedStatement2.setString(1, newLoginPassword);
				preparedStatement2.setLong(2, Long.parseLong(aNumber));
				preparedStatement2.executeUpdate();
				connection2.close();
				printWriter.println("LoginPassword Was Changed Successfully");
				}
				else
				{
					printWriter.println("Entered New and Confirm New Login Passwords are Not Matching");
				}
			}
			else
			{
				printWriter.println("Entered Old Password was Not Matching with Existing Password");
			}
		}
	}
	catch (Exception e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
