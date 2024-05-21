package com;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChattingServer {

    private static ServerSocket serverSocket;
    private static Map<Integer, List<MultiServerThread>> chatRooms = new ConcurrentHashMap<>(); 

    public static void main(String[] args) {
        ChattingServer chattingServer = new ChattingServer(); // ChattingServer 인스턴스 생성
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("서버 실행 중");

            while (true) {
                Socket socket = serverSocket.accept();
                handleClientConnection(socket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeServerSocket();
        }
    }

    private static void handleClientConnection(Socket socket) {
        try {
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            String userName = br.readLine();
            int chatRoomId = Integer.parseInt(br.readLine()); 

            MultiServerThread mst = new MultiServerThread(userName, socket, chatRoomId);
            chatRooms.computeIfAbsent(chatRoomId, k -> new ArrayList<>()).add(mst);

            mst.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeServerSocket() {
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