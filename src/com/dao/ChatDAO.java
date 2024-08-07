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
    // ConnectionPool을 필드로 추가합니다.

    public ChatDAO() {
    	
    }

    public void addChat(int userNo, String title) throws SQLException {
    	
        try (Connection con = ConnectionPool.getConnection();
				CallableStatement cstmt = con.prepareCall("{CALL CHAT_PAK.ADD_PROD(?, ?)}")){
        	
            cstmt.setInt(1, userNo);
            cstmt.setString(2, title);
            cstmt.execute();
            
        } catch (Exception e) {
            System.out.println("프로시저 호출 실패: " + e.getMessage());
            e.printStackTrace();
            }
        }

    public List<ChatVO> chatList() {
        List<ChatVO> chatList = new ArrayList<>();
        
        try (Connection con = ConnectionPool.getConnection();
				CallableStatement cstmt = con.prepareCall("{CALL CHAT_PAK.CHATLIST_PROD(?)}")){
        	
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.execute();
            ResultSet resultSet = (ResultSet) cstmt.getObject(1);

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
        }
        return chatList;
    }

    public void saveChat(int userNo, String chat, int chatNo) {
        try (Connection con = ConnectionPool.getConnection();
				CallableStatement cstmt = con.prepareCall("{CALL MESG_PAK.SAVECHAT_PROD(?, ?, ?)}")){
        	
            cstmt.setInt(1, userNo);
            cstmt.setString(2, chat);
            cstmt.setInt(3, chatNo);
            cstmt.execute();
            
        } catch (Exception e) {
            System.out.println("프로시저 호출 실패: " + e.getMessage());
            e.printStackTrace();
        } 
    }

    public List<MessageVO> mesgList(int chatNo) {
        List<MessageVO> mesgList = new ArrayList<>();

        try (Connection con = ConnectionPool.getConnection();
				CallableStatement cstmt = con.prepareCall("{CALL MESG_PAK.MESGLIST_PROD(?, ?)}")){
        	
            cstmt.setInt(1, chatNo);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);
            cstmt.execute();
            ResultSet resultSet = (ResultSet) cstmt.getObject(2);

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
        }
        return mesgList;
    }

    public void chatRoomDel(int chatNo) {
    	
        try (Connection con = ConnectionPool.getConnection();
 				CallableStatement cstmt = con.prepareCall( "{CALL CHAT_PAK.CHATROOMDEL_PROD(?)}")){
        	
            cstmt.setInt(1, chatNo);
            cstmt.execute();
            
        } catch (Exception e) {
            System.out.println("프로시저 호출 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
