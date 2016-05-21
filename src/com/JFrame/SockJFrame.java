package com.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class SockJFrame extends javax.swing.JFrame {
	private JButton send;
	private JTextField inputTxt;
	private JTextArea showTxt;
	
	private DataInputStream dis;
	private DataOutputStream dos;
	private Socket socket;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SockJFrame inst = new SockJFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public SockJFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
//		String str = null;
//		Scanner input = new Scanner(System.in);
		
		try {
			socket = new Socket("127.0.0.1", 8888);
			System.out.println("客户端已连接！");
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			new Thread() {
				@Override
				public void run() {
					String s = null;
					try {
						while (true) {
							if ((s = dis.readUTF()) != null)
//								System.out.println(s);
								showTxt.append(s+"\r\n");
						}
					} catch (IOException e) {
						//e.printStackTrace();
					}
				}
			}.start();
			
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				send = new JButton();
				send.setText("Send");
				send.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
//						sendMouseClicked(evt);
						String str = null;
						str = inputTxt.getText();
						try {
							dos.writeUTF(str);
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							inputTxt.setText("");
						}
					}
				});
			}
			{
				showTxt = new JTextArea();
			}
			{
				inputTxt = new JTextField();
				inputTxt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
//						inputTxtActionPerformed(evt);
						String str = null;
						str = inputTxt.getText();
						try {
							dos.writeUTF(str);
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							inputTxt.setText("");
						}
						
					}
				});
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(showTxt, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(send, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(inputTxt, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(showTxt, GroupLayout.Alignment.LEADING, 0, 360, Short.MAX_VALUE)
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(inputTxt, 0, 292, Short.MAX_VALUE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addComponent(send, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap());
			pack();
			setSize(400, 300);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private void sendMouseClicked(MouseEvent evt) {
		System.out.println("send.mouseClicked, event="+evt);
		//TODO add your code for send.mouseClicked
	}
	
	private void inputTxtActionPerformed(ActionEvent evt) {
		System.out.println("inputTxt.actionPerformed, event="+evt);
		//TODO add your code for inputTxt.actionPerformed
	}

}
