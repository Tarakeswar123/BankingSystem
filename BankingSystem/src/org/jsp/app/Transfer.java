package org.jsp.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Transfer")
@SuppressWarnings("serial")
public class Transfer extends HttpServlet
{
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
	// TODO Auto-generated method stub
	String date=request.getParameter("date");
	String from=request.getParameter("from");
	String to=request.getParameter("to");
	String amount=request.getParameter("amount");
	String password=request.getParameter("password");
	PrintWriter printWriter=response.getWriter();
	
	String url="jdbc:mysql://localhost:3306?user=root&password=9790270846";
	String query1="select * from test.usertable where AccountId=? and TransactionPassword=?";
	
	try 
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url);
		PreparedStatement preparedStatement1=connection.prepareStatement(query1);
		preparedStatement1.setLong(1, Long.parseLong(from));
		preparedStatement1.setString(2, password);
		ResultSet resultSet=preparedStatement1.executeQuery();
		if(resultSet.next())
		{
			long balance=resultSet.getLong("AccountBalance");
			String tPassword=resultSet.getString("TransactionPassword");
			if(tPassword.equals(password))
			{
				long a=balance-Long.parseLong(amount);
				
				String query2="update test.usertable set AccountBalance=? where AccountId=?";
				PreparedStatement preparedStatement2=connection.prepareStatement(query2);
				preparedStatement2.setLong(1, a);
				preparedStatement2.setLong(2, Long.parseLong(from));
				preparedStatement2.executeUpdate();
				
				String query3="select * from test.usertable where AccountId=?";
				PreparedStatement preparedStatement3=connection.prepareStatement(query3);
				preparedStatement3.setLong(1, Long.parseLong(to));
				ResultSet resultSet2=preparedStatement3.executeQuery();
				if(resultSet2.next())
				{
					long balance2=resultSet2.getLong("AccountBalance");
					long b=balance2+Long.parseLong(amount);
					String query4="update test.usertable set AccountBalance=? where AccountId=?";
					PreparedStatement preparedStatement4=connection.prepareStatement(query4);
					preparedStatement4.setLong(1, b);
					preparedStatement4.setLong(2, Long.parseLong(to));
					preparedStatement4.executeUpdate();
				}
				connection.close();
				printWriter.println("Transaction Was Successfully Acompolished");
			}
			else
			{
				printWriter.println("Not a Valid Transaction Password");
			}
		}
	}
	catch (Exception e1)
	{
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	String query5="insert into test.transaction values(?,?,?,?,?)";
	
	try 
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url);
		PreparedStatement preparedStatement=connection.prepareStatement(query5);
		Random random=new Random();
		preparedStatement.setLong(1, random.nextInt(10000000));
		preparedStatement.setString(2, date);
		preparedStatement.setLong(3, Long.parseLong(to));
		preparedStatement.setLong(4, Long.parseLong(amount));
		preparedStatement.setLong(5, Long.parseLong(from));
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
