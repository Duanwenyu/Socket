package com.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class SockJFrame extends javax.swing.JFrame {
	private JButton send;
	private JTextField inputTxt;
	private JButton nameButton;
	private JLabel sunName;
	private JList<String> nameList;
	private JTextField inputName;
	private JLabel nameLable;
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

	public void mainInit() {
		try {
			socket = new Socket("127.0.0.1", 8888);
			System.out.println("客户端已连接！");
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			// 不断刷新显示
						new Thread() {
							@Override
							public void run() {
								String s = null;
								try {
									while (true) {
										if ((s = dis.readUTF()) != null)
											if(s.startsWith(",")){
												addList(s);
											}else{
											showTxt.append(s + "\r\n");
											}
									}
								} catch (IOException e) {
									// e.printStackTrace();
								}
							}
						}.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout(
					(JComponent) getContentPane());
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			// 监听窗口的关闭
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					// System.out.println("this.windowClosing, event="+evt);
					try {
						dis.close();
						dos.close();
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

			{
				send = new JButton();
				send.setText("\u53d1\u9001");
				send.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						// sendMouseClicked(evt);
						String str = null;
						str = inputTxt.getText();
						try {
							dos.writeUTF(str);
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							inputTxt.setText("");
						}
					}
				});
			}
			{
				showTxt = new JTextArea();
				showTxt.setEditable(false);
			}
			{
				nameList = new JList<String>();
			}
			{
				sunName = new JLabel();
				sunName.setText("\u5728\u7ebf\u7528\u6237\u5217\u8868");
			}
			{
				nameLable = new JLabel();
				GroupLayout nameLableLayout = new GroupLayout(
						(JComponent) nameLable);
				nameLable.setLayout(nameLableLayout);
				nameLable.setText("Name\uff1a");
				nameLableLayout.setVerticalGroup(nameLableLayout
						.createParallelGroup());
				nameLableLayout.setHorizontalGroup(nameLableLayout
						.createParallelGroup());
			}
			{
				inputName = new JTextField();
			}
			{
				nameButton = new JButton();
				nameButton.setText("确认");
				nameButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {

						String str = null;
						str = inputName.getText();

						try {
							dos.writeUTF("#" + str);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				});
			}
			{
				inputTxt = new JTextField();
				inputTxt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						// inputTxtActionPerformed(evt);
						String str = null;
						str = inputTxt.getText();
						try {
							dos.writeUTF(str);
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							inputTxt.setText("");
						}

					}
				});
			}
			thisLayout
					.setVerticalGroup(thisLayout
							.createSequentialGroup()
							.addContainerGap()
							.addGroup(
									thisLayout
											.createParallelGroup()
											.addGroup(
													GroupLayout.Alignment.LEADING,
													thisLayout
															.createSequentialGroup()
															.addGroup(
																	thisLayout
																			.createParallelGroup(
																					GroupLayout.Alignment.BASELINE)
																			.addComponent(
																					nameLable,
																					GroupLayout.Alignment.BASELINE,
																					GroupLayout.PREFERRED_SIZE,
																					28,
																					GroupLayout.PREFERRED_SIZE)
																			.addComponent(
																					inputName,
																					GroupLayout.Alignment.BASELINE,
																					GroupLayout.PREFERRED_SIZE,
																					GroupLayout.PREFERRED_SIZE,
																					GroupLayout.PREFERRED_SIZE)
																			.addComponent(
																					nameButton,
																					GroupLayout.Alignment.BASELINE,
																					GroupLayout.PREFERRED_SIZE,
																					GroupLayout.PREFERRED_SIZE,
																					GroupLayout.PREFERRED_SIZE))
															.addGap(0,
																	18,
																	Short.MAX_VALUE)
															.addComponent(
																	showTxt,
																	GroupLayout.PREFERRED_SIZE,
																	258,
																	GroupLayout.PREFERRED_SIZE)
															.addGap(12))
											.addGroup(
													GroupLayout.Alignment.LEADING,
													thisLayout
															.createSequentialGroup()
															.addGap(15)
															.addComponent(
																	sunName,
																	GroupLayout.PREFERRED_SIZE,
																	42,
																	GroupLayout.PREFERRED_SIZE)
															.addPreferredGap(
																	LayoutStyle.ComponentPlacement.RELATED)
															.addComponent(
																	nameList,
																	0,
																	253,
																	Short.MAX_VALUE)))
							.addGroup(
									thisLayout
											.createParallelGroup(
													GroupLayout.Alignment.BASELINE)
											.addComponent(
													inputTxt,
													GroupLayout.Alignment.BASELINE,
													GroupLayout.PREFERRED_SIZE,
													30,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(
													send,
													GroupLayout.Alignment.BASELINE,
													GroupLayout.PREFERRED_SIZE,
													29,
													GroupLayout.PREFERRED_SIZE))
							.addContainerGap());
			thisLayout
					.setHorizontalGroup(thisLayout
							.createSequentialGroup()
							.addContainerGap()
							.addGroup(
									thisLayout
											.createParallelGroup()
											.addGroup(
													GroupLayout.Alignment.LEADING,
													thisLayout
															.createSequentialGroup()
															.addComponent(
																	inputTxt,
																	GroupLayout.PREFERRED_SIZE,
																	345,
																	GroupLayout.PREFERRED_SIZE)
															.addPreferredGap(
																	LayoutStyle.ComponentPlacement.RELATED)
															.addComponent(
																	send,
																	GroupLayout.PREFERRED_SIZE,
																	74,
																	GroupLayout.PREFERRED_SIZE))
											.addComponent(
													showTxt,
													GroupLayout.Alignment.LEADING,
													GroupLayout.PREFERRED_SIZE,
													425,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(
													GroupLayout.Alignment.LEADING,
													thisLayout
															.createSequentialGroup()
															.addGap(19)
															.addComponent(
																	nameLable,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.PREFERRED_SIZE)
															.addPreferredGap(
																	LayoutStyle.ComponentPlacement.RELATED)
															.addComponent(
																	inputName,
																	GroupLayout.PREFERRED_SIZE,
																	241,
																	GroupLayout.PREFERRED_SIZE)
															.addPreferredGap(
																	LayoutStyle.ComponentPlacement.UNRELATED)
															.addComponent(
																	nameButton,
																	GroupLayout.PREFERRED_SIZE,
																	85,
																	GroupLayout.PREFERRED_SIZE)
															.addGap(9)))
							.addGap(23)
							.addGroup(
									thisLayout
											.createParallelGroup()
											.addGroup(
													thisLayout
															.createSequentialGroup()
															.addComponent(
																	nameList,
																	GroupLayout.PREFERRED_SIZE,
																	148,
																	GroupLayout.PREFERRED_SIZE)
															.addGap(0,
																	0,
																	Short.MAX_VALUE))
											.addGroup(
													GroupLayout.Alignment.LEADING,
													thisLayout
															.createSequentialGroup()
															.addGap(36)
															.addComponent(
																	sunName,
																	GroupLayout.PREFERRED_SIZE,
																	82,
																	GroupLayout.PREFERRED_SIZE)
															.addGap(0,
																	30,
																	Short.MAX_VALUE)))
							.addContainerGap(18, 18));

			// 绘制窗口
			pack();
			this.setSize(642, 408);

			// 调用主方法
			mainInit();
	
			
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	// 方法：在list里面添加数据
	public void addList(String st) {
		String[] str=st.split(",");
		DefaultListModel<String> nal = new DefaultListModel<String>();
		for (int i = 1; i < str.length; i++) {
			nal.addElement(str[i]);			
		}
		nameList.setModel(nal);
	}
}
