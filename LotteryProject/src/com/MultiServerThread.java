package com;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.dao.ChatDAO;


public class MultiServerThread extends Thread {
    private static List<MultiServerThread> threads = new ArrayList<>();
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private BufferedReader br;
    private BufferedWriter bw;
    private PrintWriter writer;
    private String userName;
    private ChatDAO cdao;

    public MultiServerThread(String userName, Socket socket) {
        this.userName = userName;
        this.socket = socket;
        threads.add(this);
    }

    @Override
    public void run() {
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            br = new BufferedReader(new InputStreamReader(is));
            bw = new BufferedWriter(new OutputStreamWriter(os));
            writer = new PrintWriter(bw, true);
            writer.println("\n"+userName + "님이 접속되었습니다.");
            while (true) {
                String message = br.readLine();
                if (message != null) {
                    sendMessageAll(message); // 클라이언트 이름과 메시지를 합쳐서 보냄
                }
            }
        } catch (IOException e) {
        	
            writer.println(userName + "님이 나갔습니다.");
            threads.remove(this);
            try {
                if (br != null) br.close();
                if (bw != null) bw.close();
                if (socket != null) socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void sendMessageAll(String message) {
        for (MultiServerThread thread : threads) {
            try {
                PrintWriter threadWriter = new PrintWriter(thread.os, true);
                threadWriter.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
