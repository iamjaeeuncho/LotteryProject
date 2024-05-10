package com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ConnectionPool;
import com.dto.ChatVO;
import com.dto.MessageVO;

import oracle.jdbc.OracleTypes;


public class ChatDAO {
	private ConnectionPool cp = ConnectionPool.getInstance("jdbc:oracle:thin:@localhost:1521/xepdb1","lottery", "lottery", 5, 10);
	private String sql;

	public void addChat(int userNo, String title) {
	    sql = "{CALL CHAT_PAK.ADD_PROD(?, ?)}";
	    Connection con = null;
	    CallableStatement cstmt = null;
		try {
	        con = cp.getConnection();
	        cstmt = con.prepareCall(sql);
			cstmt.setLong(1, userNo);
			cstmt.setString(2, title);
			cstmt.execute();
			System.out.println("채팅방 생성");
		} catch (Exception e) {
			System.out.println("프로시저 호출 실패: " + e.getMessage());
			e.printStackTrace();
		}  finally {
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

	public List<ChatVO> chatList() {
	    List<ChatVO> chatList = new ArrayList<>();
	    sql = "{CALL CHAT_PAK.CHATLIST_PROD(?)}";
	    Connection con = null;
	    CallableStatement cstmt = null;
	    try {
	        con = cp.getConnection();
	        cstmt = con.prepareCall(sql);
	        cstmt.registerOutParameter(1, OracleTypes.CURSOR); // 1부터 시작하는 인덱스 사용
	        cstmt.execute();
	        ResultSet resultSet = (ResultSet) cstmt.getObject(1); // 1부터 시작하는 인덱스 사용
	        System.out.println(resultSet);
	        
	        // 결과셋에서 데이터를 읽어와서 ChatVO 객체로 변환하여 리스트에 추가
	        while (resultSet.next()) {
	            ChatVO chat = new ChatVO();
	            chat.setChatNo(resultSet.getInt("chatNo"));
	            chat.setUserNo(resultSet.getInt("userNo"));
	            chat.setChatName(resultSet.getString("chatName"));
	            chat.setStatus(resultSet.getString("status"));
	            chat.setCreatedChat(resultSet.getString("createdChat"));
	            chatList.add(chat);
	        }
	    } catch (Exception e) {
	        System.out.println("프로시저 호출 실패1: " + e.getMessage());
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
	    // 리스트 반환
	    return chatList;
	}

	public void saveChat(int userNo, String chat,int chatNo) {
	    sql = "{CALL MESG_PAK.SAVECHAT_PROD(?, ?, ?)}";
	    Connection con = null;
	    CallableStatement cstmt = null;
		try {
	        con = cp.getConnection();
	        cstmt = con.prepareCall(sql);
			cstmt.setLong(1, userNo);
			cstmt.setString(2, chat);
			cstmt.setLong(3, chatNo);
			cstmt.execute();
		} catch (Exception e) {
			System.out.println("프로시저 호출 실패: " + e.getMessage());
			e.printStackTrace();
		}  finally {
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

	public List<MessageVO> mesgList(int chatNo) {
		List<MessageVO> mesgList = new ArrayList<>();
	    sql = "{CALL MESG_PAK.MESGLIST_PROD(?, ?)}";
	    Connection con = null;
	    CallableStatement cstmt = null;
	    try {
	        con = cp.getConnection();
	        cstmt = con.prepareCall(sql);
	        cstmt.setLong(1, chatNo);
	        cstmt.registerOutParameter(2, OracleTypes.CURSOR); // 1부터 시작하는 인덱스 사용
	        cstmt.execute();
	        ResultSet resultSet = (ResultSet) cstmt.getObject(2); // 1부터 시작하는 인덱스 사용
	        System.out.println(resultSet);
	        
	        // 결과셋에서 데이터를 읽어와서 ChatVO 객체로 변환하여 리스트에 추가
	        while (resultSet.next()) {
	        	MessageVO mesg = new MessageVO();
	        	mesg.setChatNo(resultSet.getInt("chatNo"));
	        	mesg.setUserNo(resultSet.getInt("userNo"));
	        	mesg.setMesgNo(resultSet.getInt("mesgNo"));
	        	mesg.setUserName(resultSet.getString("userName"));
	        	mesg.setContent(resultSet.getString("content"));
	        	mesg.setCreatedMesg(resultSet.getString("createdMesg"));
	        	mesg.setUpdateMesg(resultSet.getString("updateMesg"));
	        	mesg.setStatus(resultSet.getString("status"));
	        	mesgList.add(mesg);
	        }
	    } catch (Exception e) {
	        System.out.println("프로시저 호출 실패1: " + e.getMessage());
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
	    // 리스트 반환
	    return mesgList;
	}
}