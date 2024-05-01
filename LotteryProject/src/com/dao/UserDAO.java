package com.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.DBConnection;

import oracle.jdbc.OracleTypes;

public class UserDAO {

	private Connection con = DBConnection.getConnection();
	private CallableStatement cstmt = null;
	private String rs;
	private BigDecimal logInRs;

	// 회원가입
	public String signUp(String name, String userId, String email, String passwd) {
		try {
			String sql = "";
			cstmt = con.prepareCall("{call SIGNUP(?, ?, ?, ?,?) }");
			cstmt.registerOutParameter(5, OracleTypes.VARCHAR);
			cstmt.setString(1, userId);
			cstmt.setString(2, name);
			cstmt.setString(3, passwd);
			cstmt.setString(4, email);
			cstmt.execute();

			// 반환된 ResultSet을 받아옴
			rs = cstmt.getNString(5);
			return rs;
		} catch (Exception e) {
			System.out.println("프로시저 호출 실패: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (cstmt != null) {
					cstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	//로그인
	public Object signIn(String userId, String passwd) {
		try {
			CallableStatement cstmt = con.prepareCall("{call SIGNIN(?, ?, ?) }");
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
//로그인
}