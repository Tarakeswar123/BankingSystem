package org.jsp.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Otp")
@SuppressWarnings("serial")
public class Otp extends HttpServlet
{
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
String otp=request.getParameter("otp");
PrintWriter printWriter=response.getWriter();

String url="jdbc:mysql://localhost:3306?user=root&password=9790270846";
String query="select * from test.otp where OTP=?";
try 
{
	Class.forName("com.mysql.jdbc.Driver");
	Connection connection=DriverManager.getConnection(url);
	PreparedStatement preparedStatement=connection.prepareStatement(query);
	preparedStatement.setString(1, otp);
	ResultSet resultSet=preparedStatement.executeQuery();
	if(resultSet.next())
	{
		long otp9=resultSet.getInt("OTP");
		String userName=resultSet.getString("UserName");
		if(otp9==Long.parseLong(otp))
		{
			printWriter.println("Welcome"+userName);
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("Home.html");
			requestDispatcher.include(request, response);
		}
		else
		{
			printWriter.println("Not a Valid OTP");
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
