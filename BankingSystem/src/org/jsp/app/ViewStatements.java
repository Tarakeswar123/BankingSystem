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

@WebServlet("/ViewStatements")
public class ViewStatements extends HttpServlet
{
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
	// TODO Auto-generated method stub
	String aNumber=request.getParameter("aNumber");
	String fromDate=request.getParameter("from");
	
	String url="jdbc:mysql://localhost:3306?user=root&password=9790270846";
	String query="select * from test.transaction where DateOfTransaction=? and DebitedAccountNumber=?";
	
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url);
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setString(1, fromDate);
		preparedStatement.setLong(2, Long.parseLong(aNumber));
		ResultSet resultSet=preparedStatement.executeQuery();
		while(resultSet.next())
		{
			long trid=resultSet.getLong("TransactionId");
			String date=resultSet.getString("DateOfTransaction");
			long to=resultSet.getLong("CreditedAccountNumber");
			long amount=resultSet.getLong("AmountOfTransfer");
			long from=resultSet.getLong("DebitedAccountNumber");
			PrintWriter printWriter=response.getWriter();
			printWriter.println("Transactionid:"+trid+" date:"+date+" To:"+to+" Amount:"+amount+" From:"+from);
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
