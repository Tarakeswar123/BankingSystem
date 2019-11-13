package org.jsp.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChangeMN")
public class ChangeMN extends HttpServlet
{
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
	// TODO Auto-generated method stub
	String oldMobileNumber=request.getParameter("oldNumber");
	
	String url="jdbc:mysql://localhost:3306?user=root&password=9790270846";
	String query="select * from test.usertable where MobileNumber=?";
	
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url);
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setLong(1, Long.parseLong(oldMobileNumber));
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next())
		{
			long mobile=resultSet.getLong("MobileNumber");
			if(mobile==Long.parseLong(oldMobileNumber))
			{
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("NewMobileNumber.html");
				requestDispatcher.include(request, response);
			}
			else
			{
				PrintWriter printWriter=response.getWriter();
				printWriter.println("Not a Valid Mobile Number");
			}
		}
		connection.close();
	}
	catch (Exception e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
}
}
