package org.jsp.app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/NewAccount")
@SuppressWarnings("serial")
public class NewAccount extends HttpServlet
{
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
String name=request.getParameter("name");
String mobile=request.getParameter("mobile");
String email=request.getParameter("email");
String balance=request.getParameter("balance");
String date=request.getParameter("date");
String loginPassword=request.getParameter("loginPassword");
String cPassword=request.getParameter("cPassword");
String address=request.getParameter("address");
String accountType=request.getParameter("aType");
String govtIdName=request.getParameter("govtIdName");
String govtIdNumber=request.getParameter("govtIdNumber");
String tPassword=request.getParameter("transferPassword");
String cTransferPassword=request.getParameter("cTransferPassword");
String sQuestion=request.getParameter("securityQuestion");
String answer=request.getParameter("answer");
Random random=new Random();
long accountNumber=random.nextInt(1000000000);

String url="jdbc:mysql://localhost:3306?user=root&password=9790270846";
String query1="insert into test.usertable values(?,?,?,?,?,?,?,?)";

try 
{
	Class.forName("com.mysql.jdbc.Driver");
	Connection connection1=DriverManager.getConnection(url);
	PreparedStatement preparedStatement1=connection1.prepareStatement(query1);
	preparedStatement1.setLong(1, accountNumber);
	preparedStatement1.setString(2, email);
	preparedStatement1.setString(3, loginPassword);
	preparedStatement1.setString(4, sQuestion);
	preparedStatement1.setString(5, answer);
	preparedStatement1.setString(6, tPassword);
	preparedStatement1.setLong(7, Long.parseLong(mobile));
	preparedStatement1.setLong(8, Long.parseLong(balance));
	if(tPassword.equals(cTransferPassword)&&loginPassword.equals(cPassword))
	{
		preparedStatement1.executeUpdate();
		connection1.close();
	}
}
catch (Exception e) 
{
	// TODO Auto-generated catch block
	e.printStackTrace();
}

String query2="insert into test.customer values(?,?,?,?,?,?)";

try 
{
	Class.forName("com.mysql.jdbc.Driver");
	Connection connection2=DriverManager.getConnection(url);
	PreparedStatement preparedStatement2=connection2.prepareStatement(query2);
	preparedStatement2.setLong(1, accountNumber);
	preparedStatement2.setString(2, govtIdName);
	preparedStatement2.setString(3, govtIdNumber);
	preparedStatement2.setString(4, name);
	preparedStatement2.setString(5, email);
	preparedStatement2.setString(6, address);
	if(tPassword.equals(cTransferPassword)&&loginPassword.equals(cPassword))
	{
		preparedStatement2.executeUpdate();
		connection2.close();
	}
}
catch (Exception e) 
{
	// TODO Auto-generated catch block
	e.printStackTrace();
}
String query3="insert into test.accountmaster values(?,?,?)";

try 
{
	Class.forName("com.mysql.jdbc.Driver");
	Connection connection3=DriverManager.getConnection(url);
	PreparedStatement preparedStatement3=connection3.prepareStatement(query3);
	preparedStatement3.setLong(1, accountNumber);
	preparedStatement3.setString(2, accountType);
	preparedStatement3.setString(3, date);
	if(tPassword.equals(cTransferPassword)&&loginPassword.equals(cPassword))
	{
		preparedStatement3.executeUpdate();
		connection3.close();
	}
}
catch (Exception e) 
{
	// TODO Auto-generated catch block
	e.printStackTrace();
}
RequestDispatcher requestDispatcher=request.getRequestDispatcher("Login.html");
requestDispatcher.include(request, response);
}
}
