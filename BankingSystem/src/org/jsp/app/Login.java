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
@WebServlet("/Login")
@SuppressWarnings("serial")
public class Login extends HttpServlet
{
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
String userName=request.getParameter("userName");
String password=request.getParameter("password");
PrintWriter printWriter=response.getWriter();

String url="jdbc:mysql://localhost:3306?user=root&password=9790270846";
String query="select * from test.usertable where (UserId=? or MobileNumber=?) and LoginPassword=?";

try 
{
	Class.forName("com.mysql.jdbc.Driver");
	Connection connection1=DriverManager.getConnection(url);
	PreparedStatement preparedStatement1=connection1.prepareStatement(query);
	preparedStatement1.setString(1, userName);
	preparedStatement1.setString(2, userName);
	preparedStatement1.setString(3, password);
	ResultSet resultSet=preparedStatement1.executeQuery();
	if(resultSet.next())
	{
		String accountNumber=resultSet.getString("AccountId");
		printWriter.println("Welcome"+accountNumber);
		RequestDispatcher requestDispatcher=request.getRequestDispatcher("Home.html");
		requestDispatcher.include(request, response);
	}
	else
	{
		printWriter.println("Invalid Credentials");
	}
	connection1.close();
}
catch (Exception e) 
{
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}
}
