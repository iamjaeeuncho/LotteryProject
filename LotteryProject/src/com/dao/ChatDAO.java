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
    private ConnectionPool cp;
    private String sql;

    // 생성자를 통해 ConnectionPool을 초기화합니다.
    public ChatDAO() {
        // ConnectionPool의 인스턴스를 생성하고 초기화합니다.
        cp = ConnectionPool.getInstance("jdbc:oracle:thin:@localhost:1521/xepdb1", "lottery", "lottery", 50, 10);
    }

    public void addChat(int userNo, String title) {
        sql = "{CALL CHAT_PAK.ADD_PROD(?, ?)}";
        Connection con = null;
        CallableStatement cstmt = null;
        try {
            con = cp.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setInt(1, userNo);
            cstmt.setString(2, title);
            cstmt.execute();
            System.out.println("채팅방 생성");
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

    public List<ChatVO> chatList() {
        List<ChatVO> chatList = new ArrayList<>();
        sql = "{CALL CHAT_PAK.CHATLIST_PROD(?)}";
        Connection con = null;
        CallableStatement cstmt = null;
        try {
            con = cp.getConnection();
            cstmt = con.prepareCall(sql);
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
        return chatList;
    }

    public void saveChat(int userNo, String chat, int chatNo) {
        sql = "{CALL MESG_PAK.SAVECHAT_PROD(?, ?, ?)}";
        Connection con = null;
        CallableStatement cstmt = null;
        try {
            con = cp.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setInt(1, userNo);
            cstmt.setString(2, chat);
            cstmt.setInt(3, chatNo);
            cstmt.execute();
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

    public List<MessageVO> mesgList(int chatNo) {
        List<MessageVO> mesgList = new ArrayList<>();
        sql = "{CALL MESG_PAK.MESGLIST_PROD(?, ?)}";
        Connection con = null;
        CallableStatement cstmt = null;
        try {
            con = cp.getConnection();
            cstmt = con.prepareCall(sql);
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
        return mesgList;
    }

    public void chatRoomDel(int chatNo) {
        sql = "{CALL CHAT_PAK.CHATROOMDEL_PROD(?)}";
        Connection con = null;
        CallableStatement cstmt = null;
        try {
            con = cp.getConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setInt(1, chatNo);
            cstmt.execute();
            System.out.println("채팅방 삭제");
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
