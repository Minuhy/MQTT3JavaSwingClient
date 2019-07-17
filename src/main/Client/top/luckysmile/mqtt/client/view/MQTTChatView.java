package top.luckysmile.mqtt.client.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import top.luckysmile.mqtt.client.listen.ChatViewListen;
 
/**
 * 会话界面
 * @author minuy
 *
 */
public class MQTTChatView extends JPanel {

	/**
	 * 消息界面
	 */
	private static final long serialVersionUID = 2L;

	JPanel jpnChatMain;//总底子
	JPanel jpnChatSend;//发送
	JScrollPane jspChatMassage;//消息
	JPanel jpnChatMassage;//取消订阅

	JTextArea jtaMassage;//消息框
	JLabel jlbChatTopic;//消息主题
	JLabel jlbStatistics;//消息计数
	int StatisticsN = 0;//消息计数
	JLabel jlbTip;//发送使用提示
	JTextArea jtaChatSend;//发送消息输入

	JButton jbtChatClear;//清除输入
	JButton jbtChatSend;//发送
	JButton jbtQuitSub;//取消订阅
	
	JComboBox<String> jcbQos;
	String[] items = {"Qos0","Qos1","Qos2"};
	
	Font defFont;

	/**
	 * 创建会话界面
	 */
	public MQTTChatView() {
		defFont = new Font("微软雅黑", Font.PLAIN, 12);
		jpnChatMain = new JPanel();//总底子
		jspChatMassage = new JScrollPane();//消息
		jpnChatSend = new JPanel();//发送
		jpnChatMassage = new JPanel();//消息

		jtaMassage = new JTextArea();//消息框
		jlbChatTopic = new JLabel("主题");//消息主题
		jlbStatistics = new JLabel("接收到的字节数:0");//消息计数
		jlbTip = new JLabel("消息会发送到该主题内哦~");//发送使用提示
		jtaChatSend = new JTextArea();//发送消息输入
		jbtChatClear = new JButton("清空消息");//清除输入
		jbtChatSend = new JButton("发送至当前主题");//发送
		jbtQuitSub = new JButton("取消订阅");
		jcbQos = new JComboBox<String>(items);
		
		InitMqttChatView();
	}

	private void InitMqttChatView() {
		
		jlbChatTopic.setBounds(0, 0, 150, 25);//主题
		jlbChatTopic.setFont(defFont);
		jlbStatistics.setBounds(155, 0, 210, 25);//统计
		jlbStatistics.setFont(defFont);
		
		jtaMassage.setBounds(0, 0, 310, 245);
		jtaMassage.setFont(defFont);
		jtaMassage.setEditable(false);//设置不可编辑
		jtaMassage.setLineWrap(true);// 如果内容过长。自动换行
		//添加滑动条
		jspChatMassage.setViewportView(jtaMassage);
		jspChatMassage.setBounds(0, 25, 310, 255);
		
		jpnChatMassage.setLayout(null);
		jpnChatMassage.add(jlbChatTopic);
		jpnChatMassage.add(jspChatMassage);
		jpnChatMassage.add(jlbStatistics);
		
		//发送
		jlbTip.setBounds(0, 0, 150, 25);//提示
		jlbTip.setForeground(Color.blue);
		jlbTip.setFont(defFont);
		jcbQos.setBounds(150, 0, 74, 25);
		jcbQos.setFont(defFont);
		jbtQuitSub.setBounds(225, 0, 80, 25);//取消订阅
		jbtQuitSub.setFont(defFont);
		jbtChatClear.setBounds(0, 70, 90, 25);//清除
		jbtChatClear.setFont(defFont);
		jbtChatSend.setBounds(185, 70, 120, 25);//发送
		jbtChatSend.setFont(defFont);
		jtaChatSend.setBounds(0, 27, 310, 41);//输入框
		jtaChatSend.setFont(defFont);
		
		jpnChatSend.setLayout(null);
		jpnChatSend.add(jcbQos);
		jpnChatSend.add(jlbTip);
		jpnChatSend.add(jtaChatSend);
		jpnChatSend.add(jbtChatClear);
		jpnChatSend.add(jbtChatSend);
		jpnChatSend.add(jbtQuitSub);
		
		//监听
		ActionListener chatAL = new ChatViewListen(this);
		jbtChatClear.addActionListener(chatAL);
		jbtChatSend.addActionListener(chatAL);
		jbtQuitSub.addActionListener(chatAL);
		
		jpnChatMassage.setBounds(5, 0, 310, 290);
		jpnChatSend.setBounds(5, 296, 310, 110);
		
		jpnChatMain.setLayout(null);
		jpnChatMain.setBounds(0, 0, 320, 405);//总底子
		jpnChatMain.add(jpnChatMassage);
		jpnChatMain.add(jpnChatSend);
		
		this.setLayout(null);
		
		this.setBounds(0, 0, 320, 405);
		this.add(jpnChatMain);
	}
	
	public JTextArea getJtaMassage() {
		return jtaMassage;
	}

	public JTextArea getJtaChatSend() {
		return jtaChatSend;
	}

	public JButton getJbtChatClear() {
		return jbtChatClear;
	}

	public JButton getJbtChatSend() {
		return jbtChatSend;
	}

	public JButton getJbtQuitSub() {
		return jbtQuitSub;
	}
	
	public JLabel getJlbChatTopic() {
		return jlbChatTopic;
	}
	
	public JComboBox<String> getJcbQos(){
		return jcbQos;
	}
	
	/**
	 * 订阅状态设置
	 */
	public void SetSubEnable(boolean en) {
		if(en) {
			jbtQuitSub.setBounds(225, 0, 80, 25);//取消订阅
			jbtQuitSub.setFont(defFont);
			jcbQos.setBounds(150, 0, 74, 25);
			jcbQos.setFont(defFont);
			
			jpnChatSend.add(jcbQos);
			jpnChatSend.add(jbtQuitSub);
			
			jpnChatSend.updateUI();
		}else {
			jcbQos.setBounds(231, 0, 74, 25);
			jcbQos.setFont(defFont);
			
			jpnChatSend.remove(jbtQuitSub);
			
			jpnChatSend.updateUI();
		}
	}

	public JLabel getJlbStatistics() {
		return jlbStatistics;
	}
	
	public int getStatisticsN() {
		return StatisticsN;
	}
	
	public void setStatisticsN(int statisticsN) {
		StatisticsN = statisticsN;
	}

}
