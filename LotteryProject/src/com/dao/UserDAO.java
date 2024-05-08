package com.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.ConnectionPool;

import oracle.jdbc.OracleTypes;

public class UserDAO {

	private ConnectionPool cp = ConnectionPool.getInstance("jdbc:oracle:thin:@localhost:1521/xepdb1","lottery", "lottery", 5, 10);
	private String result;
	private BigDecimal logInRs;
	private String sql ;
	
	public String signUp(String name, String userId, String email, String passwd) throws SQLException {
	    sql = "{call SIGNUP(?, ?, ?, ?,?) }";
	    Connection con = null;
	    CallableStatement cstmt = null;

	    try {
	        con = cp.getConnection();
	        cstmt = con.prepareCall(sql);
	        cstmt.registerOutParameter(5, OracleTypes.VARCHAR);
	        cstmt.setString(1, userId);
	        cstmt.setString(2, name);
	        cstmt.setString(3, passwd);
	        cstmt.setString(4, email);
	        cstmt.execute();
	        
	        result = cstmt.getNString(5);
	        return result;
	        
	    } catch (SQLException e) {
	        System.out.println("프로시저 호출 실패: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        try {
	            if (cstmt != null) {
	                cstmt.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}
	
	//로그인
	public Object signIn(String userId, String passwd) {
	    Connection con = null;
	    CallableStatement cstmt = null;
	    sql = "{call SIGNIN(?, ?, ?) }";
	    
		try {
	        con = cp.getConnection();
	        cstmt = con.prepareCall(sql);
	        cstmt.registerOutParameter(3, OracleTypes.NUMBER);
			cstmt.setString(1, userId);
			cstmt.setString(2, passwd);
			cstmt.execute();

			logInRs = cstmt.getBigDecimal(3);
			return logInRs;
		} catch (Exception e) {
			System.out.println("함수 호출 실패: " + e.getMessage());
			e.printStackTrace();
		} finally {
	        try {
	            if (cstmt != null) {
	                cstmt.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}

	// 사용자 이름 얻기
	public String userName(int userNo) {
	    Connection con = null;
	    CallableStatement cstmt = null;
	    sql = "{call USERNAME(?, ?) }";
		try {
	        con = cp.getConnection();
	        cstmt = con.prepareCall(sql);
			cstmt.registerOutParameter(2, OracleTypes.VARCHAR);
			cstmt.setInt(1, userNo);
			cstmt.execute();

			String userName = cstmt.getNString(2);
			return userName;
		} catch (Exception e) {
			System.out.println("함수 호출 실패: " + e.getMessage());
			e.printStackTrace();
		}finally {
	        try {
	            if (cstmt != null) {
	                cstmt.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}
		
	}
