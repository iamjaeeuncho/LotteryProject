package com;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.dao.ChatDAO;
import com.dto.ChatVO;

public class ChattingServer {
    private static ServerSocket serverSocket;
    private static ArrayList<MultiServerThread> activeThreads = new ArrayList<>();

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(1000);
            System.out.println("서버 실행 중");

            // 기존에 생성된 채팅방 정보를 읽어올 수 있는 로직 구현

            while (true) {
                Socket socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String userName = br.readLine();

                MultiServerThread thread = new MultiServerThread(userName, socket);
                activeThreads.add(thread);
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (Exception e) {
                System.err.println("서버 종료 중 오류 발생: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

//    // 서버가 재가동될 때 기존 채팅방 정보를 읽어오는 메서드
//    private static void loadChatRooms() {
//        // 데이터베이스나 파일 시스템 등에서 채팅방 정보를 읽어와서 처리하는 로직 구현
//    	ChatDAO cdao = new ChatDAO();
//        List<ChatVO> chatList = cdao.chatList();
//    }
}
