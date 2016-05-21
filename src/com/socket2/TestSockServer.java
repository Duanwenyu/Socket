package com.socket2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestSockServer {
	static List<Client> clientList = new ArrayList<Client>();

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
				clientList.add(c);
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
				// 申明在类的外面，可以节省内存
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());

				while (true) {

					// 循环遍历发来的信息，并且将这些信息全部返回给客户端
					if ((str1 = dis.readUTF()) != null) {
						// System.out.println(socket.getPort() + "speak:" +
						// str1);
						for (int i = 0; i < clientList.size(); i++) {
							new DataOutputStream(
									clientList.get(i).socket.getOutputStream())
									.writeUTF(socket.getPort() + "speak:"
											+ str1);
						}

					}
					// dos.writeUTF("Sever speak:" + str1);
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
