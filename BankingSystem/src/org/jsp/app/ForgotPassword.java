package org.jsp.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/ForgotPassword")
@SuppressWarnings("serial")
public class ForgotPassword extends HttpServlet
{
	String url="jdbc:mysql://localhost:3306?user=root&password=9790270846";
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
String userName=request.getParameter("userName");
String sQuestion=request.getParameter("securityQuestion");
String answer=request.getParameter("answer");

String query1="select * from test.usertable where (UserId=? or MobileNumber=?) and SecretQuestion=? and Answer=?";

try
{
	Class.forName("com.mysql.jdbc.Driver");
	Connection connection1=DriverManager.getConnection(url);
	PreparedStatement preparedStatement1=connection1.prepareStatement(query1);
	preparedStatement1.setString(1, userName);
	preparedStatement1.setString(2, userName);
	preparedStatement1.setString(3, sQuestion);
	preparedStatement1.setString(4, answer);
	ResultSet resultSet=preparedStatement1.executeQuery();
	if(resultSet.next())
	{
		otp(request, response, userName);
	}
	else
	{
		PrintWriter printWriter=response.getWriter();
		printWriter.println("Invalid Credentials");
	}
}
catch (Exception e) 
{
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
public void otp(HttpServletRequest request, HttpServletResponse response, String userName)
{
	String query2="insert into test.otp values(?,?)";
	
	try
	{
	Class.forName("com.mysql.jdbc.Driver");
	Connection connection2=DriverManager.getConnection(url);
	PreparedStatement preparedStatement2=connection2.prepareStatement(query2);
	preparedStatement2.setString(1, userName);
	Random random=new Random();
	long otp=random.nextInt(100000);
	if(otp<0)
	{
		otp=otp*-1;
	}
	preparedStatement2.setLong(2, otp);
	preparedStatement2.executeUpdate();
	connection2.close();
	RequestDispatcher requestDispatcher=request.getRequestDispatcher("otp.html");
			requestDispatcher.include(request, response);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
}
