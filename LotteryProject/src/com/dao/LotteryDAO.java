package com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.ConnectionPool;

public class LotteryDAO {
    private ConnectionPool cp = ConnectionPool.getInstance("jdbc:oracle:thin:@localhost:1521/xepdb1", "lottery", "lottery", 5, 10);
    private String sql;

    // 로또 번호 저장
    public void saveLottery(Map<Integer, Object> results) {
    	sql = "INSERT INTO LOTTERY (USERNO, CATEGORY, CREATEDAT, MODIFEDAT, LOTTERY1, LOTTERY2, LOTTERY3, LOTTERY4, LOTTERY5, LOTTERY6, STATUS) VALUES (1, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, 'Y')";
    	Connection con = null;
        CallableStatement cstmt = null;

        try {
            con = cp.getConnection();
            cstmt = con.prepareCall(sql);

            for (Entry<Integer, Object> result : results.entrySet()) {
                String[] values = (String[]) result.getValue();

                String category = values[0];
                int categoryNum;
                if (category.equals("자동")) {
                	categoryNum = 1;
                	cstmt.setInt(1, categoryNum);
                } else if (category.equals("반자동")) {
                	categoryNum = 2;
                	cstmt.setInt(1, categoryNum);
                } else if (category.equals("수동")) {
                	categoryNum = 3;
                	cstmt.setInt(1, categoryNum);
                }

                String numbers = values[1].substring(1, values[1].length() - 1); // 대괄호 제거
                String[] numberArray = numbers.split(", "); // 쉼표(,)를 기준으로 문자열 분할

                
                int sqlColumnIndex = 2;
                for (String num : numberArray) {
            		cstmt.setInt(sqlColumnIndex++, Integer.parseInt(num));
                }
                cstmt.execute();
            }
            System.out.println("복권 번호 저장 완료");
        } catch (Exception e) {
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
    }
    
    // 로또 번호 보기
    public Map<Integer, String[]> showLottery() {
        String sql = "SELECT * FROM LOTTERY ORDER BY LOTTERYNO ASC";
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        Map<Integer, String[]> lotteryResults = new HashMap<>();

        try {
            con = cp.getConnection();
            cstmt = con.prepareCall(sql);
            rs = cstmt.executeQuery();

            while (rs.next()) {
                int lotteryNo = rs.getInt("LOTTERYNO");
                int userNo = rs.getInt("USERNO");
                String createdAt = rs.getString("CREATEDAT");
                String category = getCategoryName(rs.getInt("CATEGORY"));
                String[] numbers = new String[6];
                numbers[0] = rs.getString("LOTTERY1");
                numbers[1] = rs.getString("LOTTERY2");
                numbers[2] = rs.getString("LOTTERY3");
                numbers[3] = rs.getString("LOTTERY4");
                numbers[4] = rs.getString("LOTTERY5");
                numbers[5] = rs.getString("LOTTERY6");

                
                // 로또 번호와 해당 번호의 모든 정보를 맵에 추가
                String[] lotteryInfo = {String.valueOf(userNo), createdAt, category, String.join(", ", numbers)};
                lotteryResults.put(lotteryNo, lotteryInfo);
            }
            System.out.println("복권 번호 전달 완료");
        } catch (Exception e) {
            System.out.println("프로시저 호출 실패: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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
        return lotteryResults;
    }


    // 로또 번호 삭제
    public void deleteLottery(int lotteryNo) {
        String sql = "DELETE FROM LOTTERY WHERE LOTTERYNO = ?";
    	Connection con = null;
        CallableStatement cstmt = null;

        try {
            con = cp.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setInt(1, lotteryNo);
            cstmt.execute();
            System.out.println(lotteryNo + "복권 번호 삭제 완료");
        } catch (Exception e) {
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
