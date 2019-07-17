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
 * �Ự����
 * @author minuy
 *
 */
public class MQTTChatView extends JPanel {

	/**
	 * ��Ϣ����
	 */
	private static final long serialVersionUID = 2L;

	JPanel jpnChatMain;//�ܵ���
	JPanel jpnChatSend;//����
	JScrollPane jspChatMassage;//��Ϣ
	JPanel jpnChatMassage;//ȡ������

	JTextArea jtaMassage;//��Ϣ��
	JLabel jlbChatTopic;//��Ϣ����
	JLabel jlbStatistics;//��Ϣ����
	int StatisticsN = 0;//��Ϣ����
	JLabel jlbTip;//����ʹ����ʾ
	JTextArea jtaChatSend;//������Ϣ����

	JButton jbtChatClear;//�������
	JButton jbtChatSend;//����
	JButton jbtQuitSub;//ȡ������
	
	JComboBox<String> jcbQos;
	String[] items = {"Qos0","Qos1","Qos2"};
	
	Font defFont;

	/**
	 * �����Ự����
	 */
	public MQTTChatView() {
		defFont = new Font("΢���ź�", Font.PLAIN, 12);
		jpnChatMain = new JPanel();//�ܵ���
		jspChatMassage = new JScrollPane();//��Ϣ
		jpnChatSend = new JPanel();//����
		jpnChatMassage = new JPanel();//��Ϣ

		jtaMassage = new JTextArea();//��Ϣ��
		jlbChatTopic = new JLabel("����");//��Ϣ����
		jlbStatistics = new JLabel("���յ����ֽ���:0");//��Ϣ����
		jlbTip = new JLabel("��Ϣ�ᷢ�͵���������Ŷ~");//����ʹ����ʾ
		jtaChatSend = new JTextArea();//������Ϣ����
		jbtChatClear = new JButton("�����Ϣ");//�������
		jbtChatSend = new JButton("��������ǰ����");//����
		jbtQuitSub = new JButton("ȡ������");
		jcbQos = new JComboBox<String>(items);
		
		InitMqttChatView();
	}

	private void InitMqttChatView() {
		
		jlbChatTopic.setBounds(0, 0, 150, 25);//����
		jlbChatTopic.setFont(defFont);
		jlbStatistics.setBounds(155, 0, 210, 25);//ͳ��
		jlbStatistics.setFont(defFont);
		
		jtaMassage.setBounds(0, 0, 310, 245);
		jtaMassage.setFont(defFont);
		jtaMassage.setEditable(false);//���ò��ɱ༭
		jtaMassage.setLineWrap(true);// ������ݹ������Զ�����
		//��ӻ�����
		jspChatMassage.setViewportView(jtaMassage);
		jspChatMassage.setBounds(0, 25, 310, 255);
		
		jpnChatMassage.setLayout(null);
		jpnChatMassage.add(jlbChatTopic);
		jpnChatMassage.add(jspChatMassage);
		jpnChatMassage.add(jlbStatistics);
		
		//����
		jlbTip.setBounds(0, 0, 150, 25);//��ʾ
		jlbTip.setForeground(Color.blue);
		jlbTip.setFont(defFont);
		jcbQos.setBounds(150, 0, 74, 25);
		jcbQos.setFont(defFont);
		jbtQuitSub.setBounds(225, 0, 80, 25);//ȡ������
		jbtQuitSub.setFont(defFont);
		jbtChatClear.setBounds(0, 70, 90, 25);//���
		jbtChatClear.setFont(defFont);
		jbtChatSend.setBounds(185, 70, 120, 25);//����
		jbtChatSend.setFont(defFont);
		jtaChatSend.setBounds(0, 27, 310, 41);//�����
		jtaChatSend.setFont(defFont);
		
		jpnChatSend.setLayout(null);
		jpnChatSend.add(jcbQos);
		jpnChatSend.add(jlbTip);
		jpnChatSend.add(jtaChatSend);
		jpnChatSend.add(jbtChatClear);
		jpnChatSend.add(jbtChatSend);
		jpnChatSend.add(jbtQuitSub);
		
		//����
		ActionListener chatAL = new ChatViewListen(this);
		jbtChatClear.addActionListener(chatAL);
		jbtChatSend.addActionListener(chatAL);
		jbtQuitSub.addActionListener(chatAL);
		
		jpnChatMassage.setBounds(5, 0, 310, 290);
		jpnChatSend.setBounds(5, 296, 310, 110);
		
		jpnChatMain.setLayout(null);
		jpnChatMain.setBounds(0, 0, 320, 405);//�ܵ���
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
	 * ����״̬����
	 */
	public void SetSubEnable(boolean en) {
		if(en) {
			jbtQuitSub.setBounds(225, 0, 80, 25);//ȡ������
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
