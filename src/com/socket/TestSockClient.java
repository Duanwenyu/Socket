package com.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class TestSockClient {
	public static void main(String[] args) {
		DataInputStream dis = null;
		DataOutputStream dos = null;

		Scanner input = new Scanner(System.in);
		String s = null;
		Socket socket = null;

		try {
			// 客户端程序启动
			// 数据交互
			do {
				socket = new Socket("localhost", 8888);
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());

				System.out.println("YOU:");
				s = input.next();
				dos.writeUTF(s);

				String str2 = null;
				if ((str2 = dis.readUTF()) != null) {
					System.out.println(str2);
				}
			} while (!s.equals("88"));
			dos.close();
			dis.close();
			socket.close();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("对方关闭了！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}

	}
}
