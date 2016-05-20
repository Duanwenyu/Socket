package com.socket2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class TestSockServer {

	public static void main(String[] args) {

		Socket socket = null;
		ServerSocket s = null;
		
		try {
			s = new ServerSocket(8888);
			// 准备
			System.out.println("聊天程序启动！");
			while (true) {
				// 连接
				socket = s.accept();
				Client c = new TestSockServer().new Client(socket);
				c.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
				System.out.println("Close!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class Client extends Thread {
		DataInputStream dis = null;
		DataOutputStream dos = null;
		Socket socket = null;

		public Client(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			String str1 = null;
			try {
				while(true){
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());

				
				if ((str1 = dis.readUTF()) != null) {
					System.out.println(socket.getPort() + "speak:" + str1);
				}
				dos.writeUTF("Sever speak:" + str1);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					dos.close();
					dis.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
