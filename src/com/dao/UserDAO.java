package com.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.ConnectionPool;

import oracle.jdbc.OracleTypes;

public class UserDAO {

	private String result;
	private BigDecimal logInRs;

	public String signUp(String name, String userId, String email, String passwd) throws SQLException {

		try (Connection con = ConnectionPool.getConnection();
				CallableStatement cstmt = con.prepareCall("{call SIGNUP(?, ?, ?, ?, ?) }")) {

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
		}

		return null;
	}

	// 로그인
	public Object signIn(String userId, String passwd) {

		try (Connection con = ConnectionPool.getConnection();
				CallableStatement cstmt = con.prepareCall("{call SIGNIN(?, ?, ?)}")) {

			cstmt.registerOutParameter(3, OracleTypes.NUMBER);
			cstmt.setString(1, userId);
			cstmt.setString(2, passwd);
			cstmt.execute();

			logInRs = cstmt.getBigDecimal(3);
			return logInRs;

		} catch (Exception e) {
			System.out.println("함수 호출 실패: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	// 사용자 이름 얻기
	public String userName(int userNo) {

		try (Connection con = ConnectionPool.getConnection();
				CallableStatement cstmt = con.prepareCall("{call USERNAME(?, ?)}")) {

			cstmt.registerOutParameter(2, OracleTypes.VARCHAR);
			cstmt.setInt(1, userNo);
			cstmt.execute();

			String userName = cstmt.getNString(2);
			return userName;
			
		} catch (Exception e) {
			System.out.println("함수 호출 실패: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	// 회원 탈퇴
	public void deleteUser(int userNo) {
        try (Connection con = ConnectionPool.getConnection();
				CallableStatement cstmt = con.prepareCall("{call DELETE_USER(?)}")){
        	
            cstmt.setInt(1, userNo);
            cstmt.execute();
            System.out.println(userNo + "사용자 삭제 완료");
            
        } catch (Exception e) {
            System.out.println("프로시저 호출 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
