package com.teacher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestSockServer {
	// 创建用来存多个用户的数组
	static List<Client> clientList = new ArrayList<Client>();

	// 主方法
	public void init() {
		// SeverScoket 和 Socket
		ServerSocket server = null;
		Socket socket = null;

		try {
			// 打开端口，等待服务器连接
			server = new ServerSocket(8888);
			System.out.println("服务器已开启！");

			// 服务端循环监听，若是有客户端连接，服务端接受并且创建一个线程，将连接通道socket放入该线程中，并启动线程
			while (true) {
				socket = server.accept();
				Client c = new Client(socket);// ****关于在Main内new内部类的方式 new// Client(socket);
				clientList.add(c);// 将新连接的客户端存入list中，以便于后面遍历发送信息
				c.start();
				updataNameList();
			}
		} catch (IOException e) {
			System.out.println("server over!");
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 获得名字的方法
	public String getName() {
		String str = ",";
		for (int i = 0; i < clientList.size(); i++) {
			if(clientList.get(i).name!=null){
			str += ("用户:" + clientList.get(i).name);
			}
			else{
				str += ("用户："+clientList.get(i).socket.getPort());
			}
			str += ",";
		}
		return str;
	}

	// 更新名字列表
	public void updataNameList() {
		String strs=getName();
		for (int i = 0; i < clientList.size(); i++) {
			try {
				if(getName()!=null){
				new DataOutputStream(clientList.get(i).socket.getOutputStream()).writeUTF(strs);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Main方法
	public static void main(String[] args) {
		TestSockServer ts = new TestSockServer();
		ts.init();
	}

	// 因为需要启用多个线程来完成客户端的连接，所以这里使用内部类
	// 用到多线程，用来创建多个客户端
	class Client extends Thread {
		Socket socket;
		String name;
		DataOutputStream dos;
		DataInputStream dis;

		// 使用带参数的构造方法来将通道放入线程中。
		public Client(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			String str = null;
			try {
				// 在while外创建数据输入输出流，可以省下内存
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());

				// 循环遍历用来接收某个客户端传来的信息，并且将这些信息遍历发送给各个客户端。
				while (true) {
					// 判断名字输入格式是否是"#xx"，如果是，就更新昵称
					if ((str = dis.readUTF()) != null) {
						if (str.startsWith("#")) {
							this.name = str.substring(1);
							updataNameList();
							for (int i = 0; i < clientList.size(); i++) {
								new DataOutputStream(clientList.get(i).socket.getOutputStream()).writeUTF("用户" + socket.getPort()+ "改名为：" + name);
							}
						} else {
							// 循环遍历将信息发送给所有客户端。
							for (int i = 0; i < clientList.size(); i++) {
								if (name != null) {
									new DataOutputStream(clientList.get(i).socket.getOutputStream()).writeUTF(name + ":" + str);
								} else {
									new DataOutputStream(clientList.get(i).socket.getOutputStream()).writeUTF(socket.getPort() + ":"+ str);
								}
							}
						}
					}
				}

			} catch (IOException e) {
				System.out.println("客户端" + socket.getPort() + "退出");
				clientList.remove(this);
				updataNameList();
				// e.printStackTrace();
			} finally {
				// 关闭各种流
				try {
					dis.close();
					dos.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
	}
}