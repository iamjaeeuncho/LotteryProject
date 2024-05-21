package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;

@Getter
public class MultiServerThread extends Thread {
    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private PrintWriter writer;
    private String userName;
    private int chatRoomId;
    private static Map<Integer, List<MultiServerThread>> chatRooms = new ConcurrentHashMap<>();

    public MultiServerThread(String userName, Socket socket, int chatRoomId) {
        this.userName = userName;
        this.socket = socket;
        this.chatRoomId = chatRoomId;
    }

    @Override
    public void run() {
        try {
            setupStreams();
            notifyUserJoined();

            String message;
            while ((message = br.readLine()) != null) {
                if (message.equals("online_request")) {
                    sendOnlineUsers();
                } else if (message.equals("exit")) {
                    closeClientSocket();
                    break;
                } else {
                    sendMessageToAll(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void setupStreams() throws IOException {
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer = new PrintWriter(bw, true);
    }

    private void notifyUserJoined() {
        writer.println(userName + "님이 접속되었습니다.");
        synchronized (chatRooms) {
            chatRooms.computeIfAbsent(chatRoomId, k -> new ArrayList<>()).add(this);
        }
    }

    private void sendOnlineUsers() {
        StringBuilder onlineUsers = new StringBuilder();
        synchronized (chatRooms) {
            for (MultiServerThread thread : chatRooms.get(chatRoomId)) {
                onlineUsers.append(thread.getUserName()).append("\n");
            }
        }
        writer.println("현재 접속 중인 사용자 목록:\n" + onlineUsers.toString());
    }

    private void sendMessageToAll(String message) {
        synchronized (chatRooms) {
            for (MultiServerThread thread : chatRooms.get(chatRoomId)) {
                thread.getWriter().println(message);
            }
        }
    }

    private void closeClientSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            synchronized (chatRooms) {
                chatRooms.get(chatRoomId).remove(this);
            }
        }
    }

    private void cleanup() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (br != null) {
                br.close();
            }
            if (bw != null) {
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}