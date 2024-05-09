package com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import com.ConnectionPool;

public class LotteryDAO {
    private ConnectionPool cp = ConnectionPool.getInstance("jdbc:oracle:thin:@localhost:1521/xepdb1", "lottery", "lottery", 5, 10);
    private String sql;

    // 저장
    public void SaveLottery(Map<Integer, Object> results) {
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
}
