package com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.ConnectionPool;

import oracle.jdbc.OracleTypes;

public class LotteryDAO {
  
    // 로또 번호 저장
    public void saveLottery(int userNo, Map<Integer, Object> results) {
        
        try (Connection con = ConnectionPool.getConnection();
			CallableStatement cstmt = con.prepareCall("{call INSERT_LOTTERY(?, ?, ?, ?, ?, ?, ?, ?) }")){
        	
        	cstmt.setInt(1, userNo);
        	
            for (Entry<Integer, Object> result : results.entrySet()) {
                String[] values = (String[]) result.getValue();

                String category = values[0];
                int categoryNum;
                if (category.equals("자동")) {
                	categoryNum = 1;
                	cstmt.setInt(2, categoryNum);
                } else if (category.equals("반자동")) {
                	categoryNum = 2;
                	cstmt.setInt(2, categoryNum);
                } else if (category.equals("수동")) {
                	categoryNum = 3;
                	cstmt.setInt(2, categoryNum);
                }

                String numbers = values[1].substring(1, values[1].length() - 1); // 대괄호 제거
                String[] numberArray = numbers.split(", "); // 쉼표(,)를 기준으로 문자열 분할

                int sqlColumnIndex = 3;
                for (String num : numberArray) {
            		cstmt.setInt(sqlColumnIndex++, Integer.parseInt(num));
                }
                cstmt.execute();
            }
            System.out.println("복권 번호 저장 완료");
        } catch (Exception e) {
            System.out.println("프로시저 호출 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
 // 로또 번호 반환
    public Map<Integer, String[]> showLottery(int userNo) {
        ResultSet rs = null;
        Map<Integer, String[]> lotteryResults = new HashMap<>();

        try (Connection con = ConnectionPool.getConnection();
            CallableStatement cstmt = con.prepareCall("{call SHOW_LOTTERY(?, ?)}")) {
            cstmt.setInt(1, userNo); // p_userno 설정

            // OUT 매개변수를 등록합니다.
            cstmt.registerOutParameter(2, OracleTypes.CURSOR); // lotteryList

            cstmt.execute(); // 프로시저 실행

            rs = (ResultSet) cstmt.getObject(2); // 결과 세트 가져오기
                
            // 결과 처리
            while (rs.next()) {
                int lotteryNo = rs.getInt("LOTTERYNO");
                Timestamp createdAt = rs.getTimestamp("CREATEDAT");
                int category = rs.getInt("CATEGORY");
                int lottery1 = rs.getInt("LOTTERY1");
                int lottery2 = rs.getInt("LOTTERY2");
                int lottery3 = rs.getInt("LOTTERY3");
                int lottery4 = rs.getInt("LOTTERY4");
                int lottery5 = rs.getInt("LOTTERY5");
                int lottery6 = rs.getInt("LOTTERY6");
                String status = rs.getString("STATUS");

                // 로또 번호와 해당 번호의 모든 정보를 맵에 추가
                String[] lotteryInfo = { String.valueOf(userNo), String.valueOf(createdAt), getCategoryName(category),
                        String.join(", ", String.valueOf(lottery1), String.valueOf(lottery2), String.valueOf(lottery3),
                                String.valueOf(lottery4), String.valueOf(lottery5), String.valueOf(lottery6)) };
                lotteryResults.put(lotteryNo, lotteryInfo);
            }
            System.out.println("복권 번호 전달 완료");
        } catch (Exception e) {
            System.out.println("프로시저 호출 실패: " + e.getMessage());
            e.printStackTrace();
        }
        return lotteryResults;
    }


    // 로또 번호 삭제
    public void deleteLottery(int lotteryNo) {
        try (Connection con = ConnectionPool.getConnection();
				CallableStatement cstmt = con.prepareCall("{call DELETE_LOTTERY(?)}")){
        	
            cstmt.setInt(1, lotteryNo);
            cstmt.execute();
            System.out.println(lotteryNo + "복권 번호 삭제 완료");
            
        } catch (Exception e) {
            System.out.println("프로시저 호출 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private String getCategoryName(int index) {
    	if (index == 0) {
    		return "자동";
    	} else if (index == 1) {
    		return "반자동";
    	} else {
    		return "수동";
    	}
    }
}