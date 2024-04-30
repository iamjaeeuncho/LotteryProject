package com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JTextField;

import com.DBConnection;

public class UserDAO {
	CallableStatement cstmt = null;
	private Connection con = DBConnection.getConnection();
	private Statement stmt= null; //connect를 이용해 sql명령을 실행하는 객체
	String rs;
	
	public String singUp(String name, String userId, String email, String passwd) {
		System.out.println("되나"+con);
		try {
			
			String sql = "{? = call singUp(?,?,?,?)}";
            cstmt = con.prepareCall(sql);
            cstmt.registerOutParameter(1, Types.VARCHAR); // 결과값을 받을 타입 설정
            cstmt.setString(2, userId);
            cstmt.setString(3, name);
            cstmt.setString(4, passwd);
            cstmt.setString(5, email);
            cstmt.execute();
            rs = cstmt.getString(1); // 함수에서 반환된 값을 받음
			
//			String sql="singUp("+userId+name+passwd+email+")";
//			stmt = con.createStatement();
//			rs = stmt.executeQuery(sql); //아이디 이름 pw 이메일
			return rs;
		} catch (Exception e) {
			System.out.println("함수 호출 실패");
		}
		return rs;
	}
}


