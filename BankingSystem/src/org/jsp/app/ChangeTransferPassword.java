package org.jsp.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChangeTransferPassword")
public class ChangeTransferPassword extends HttpServlet
{
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
	// TODO Auto-generated method stub
	String aNumber=request.getParameter("aNumber");
	String oldTransferPassword=request.getParameter("oldTransferPassword");
	String newTransferPassword=request.getParameter("newTransferPassword");
	String cNewTransferPassword=request.getParameter("cNewTransferPassword");
	
	PrintWriter printWriter=response.getWriter();
	
	String url="jdbc:mysql://localhost:3306?user=root&password=9790270846";
	String query="select * from test.usertable where AccountId=? and TransactionPassword=?";
	
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url);
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setLong(1, Long.parseLong(aNumber));
		preparedStatement.setString(2, oldTransferPassword);
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next())
		{
			String password=resultSet.getString("TransactionPassword");
			if(password.equals(oldTransferPassword))
			{
				if(newTransferPassword.equals(cNewTransferPassword))
				{
				String query2="update test.usertable set TransactionPassword=? where AccountId=?";
				Connection connection2=DriverManager.getConnection(url);
				PreparedStatement preparedStatement2=connection2.prepareStatement(query2);
				preparedStatement2.setString(1, newTransferPassword);
				preparedStatement2.setLong(2, Long.parseLong(aNumber));
				preparedStatement2.executeUpdate();
				connection2.close();
				printWriter.println("TransactionPassword Was Changed Successfully");
				}
				else
				{
					printWriter.println("Entered New and Confirm New Transaction Passwords are Not Matching");
				}
			}
			else
			{
				printWriter.println("Entered Old Transaction Password was Not Matching with Existing Password");
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
