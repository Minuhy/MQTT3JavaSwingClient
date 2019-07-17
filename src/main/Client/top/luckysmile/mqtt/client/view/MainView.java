package top.luckysmile.mqtt.client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import top.luckysmile.mqtt.client.data.MqttDAO;
import top.luckysmile.mqtt.client.data.MqttSetBeans;
import top.luckysmile.mqtt.client.listen.MqttMainViewActionListener;
import top.luckysmile.mqtt.client.server.TextUtils;
/**
 * �����棬�ĸ���飬��¼�����ģ����ͺͻỰ
 * @author minuy
 *
 */
public class MainView extends JFrame {
	/**
	 * ����壬�����¼�����ͣ����ĺ�����
	 */
	private static final long serialVersionUID = 1L;
	Font defFont;//Ĭ������

	private JPanel jpnMain;//�����
	private JPanel jpnLogin;//��¼���
	private JPanel jpnSubscribe;//�������
	private JPanel jpnPublish;//�������
	private JPanel jpnChat;//��Ϣ���
	private JPanel jpnImage;//ͼƬ���

	private JLabel jlbIp;//IP��ǩ
	private JTextField jtfIp;//IP�����
	private JLabel jlbPort;//�˿�
	private JTextField jtfPort;//�˿������
	private JLabel jlbId;//ʶ���
	private JTextField jtfId;//ʶ��������
	private JButton jbtRandId;//���ʶ���
	private JLabel jlbName;//�˺�
	private JTextField jtfName;//�˺������
	private JLabel jlbPasswd;//����
	private JPasswordField jpfPasswd;//���������
	private JLabel jlbLWT;//����
	private JTextField jtfLWTTopic;//�������������
	private JTextField jtfLWTContext;//�������������
	private JComboBox<String> jcbLWTQos;//���Է�������
	private JButton jbtConnect;//���Ӱ�ť
	private JLabel jlbSta;//����״̬
	private JButton jbtMoreSet;//��������

	private JLabel jlbTopic;//��������
	private JTextField jtfSubscribeTopic;//�������������
	private JComboBox<String> jcbTopicQos;//���ķ�������
	private JButton jbtSubscribe;//���İ�ť

	private JLabel jlbSend;//��Ϣ����
	private JTextArea jtaContext;//��Ϣ����
	private JTextField jtfContextTopic;//��Ϣ���������
	private JComboBox<String> jcbSendQos;//��Ϣ��������
	private JButton jbtSend;//����
	private JLabel jlbSendTopic;
	private JLabel jlbSendsta;//��Ϣ״ָ̬ʾ

	private JTabbedPane jtpChat;//��Ϣ�б�
	private JLabel jlbPicture;

	String[] MqttQos = {"Qos0","Qos1","Qos2"};
	DefaultComboBoxModel<String> cbmMqttQos1;
	DefaultComboBoxModel<String> cbmMqttQos2;
	DefaultComboBoxModel<String> cbmMqttQos3;

	/**
	 * ��������ͼ
	 */
	public MainView() {
		defFont = new Font("΢���ź�", Font.PLAIN, 12);
		cbmMqttQos1 = new DefaultComboBoxModel<String>(MqttQos);
		cbmMqttQos2 = new DefaultComboBoxModel<String>(MqttQos);
		cbmMqttQos3 = new DefaultComboBoxModel<String>(MqttQos);

		jpnMain = new JPanel();//�����
		jpnLogin = new JPanel();//��¼���
		jpnSubscribe = new JPanel();//�������
		jpnPublish = new JPanel();//�������
		jpnChat = new JPanel();//��Ϣ���

		jlbIp = new JLabel("����:");//IP��ǩ
		jtfIp = new JTextField();//IP�����
		jlbPort = new JLabel("�˿�:");//�˿�
		jtfPort = new JTextField();//�˿������
		jlbId = new JLabel("ʶ���:");//ʶ���
		jbtRandId = new JButton("*");
		jtfId = new JTextField();//ʶ��������
		jlbName = new JLabel("�˺�:");//�˺�
		jtfName = new JTextField();//�˺������
		jlbPasswd = new JLabel("����:");//����
		jpfPasswd = new JPasswordField();//���������
		jlbLWT = new JLabel("����:");//����
		jtfLWTTopic = new JTextField();//�������������
		jtfLWTContext = new JTextField();//�������������
		jcbLWTQos = new JComboBox<String>();//���Է�������
		jbtConnect = new JButton("����");//���Ӱ�ť
		jlbSta = new JLabel("δ����",JLabel.CENTER);//����״̬
		jbtMoreSet = new JButton("����");//��������

		jlbTopic = new JLabel("��������:");//��������
		jtfSubscribeTopic = new JTextField();//�������������
		jcbTopicQos = new JComboBox<String>();//���ķ�������
		jbtSubscribe = new JButton("����");//���İ�ť

		jlbSend = new JLabel("������Ϣ:");//������Ϣ
		jtaContext = new JTextArea();//��Ϣ����
		jtfContextTopic = new JTextField();//��Ϣ���������
		jcbSendQos = new JComboBox<String>();//��Ϣ��������
		jlbSendTopic = new JLabel("����:");
		jbtSend = new JButton("����");
		jlbSendsta = new JLabel();

		jtpChat = new JTabbedPane(JTabbedPane.LEFT);//��Ϣ�б�
		jlbPicture = new JLabel();

		MainInit();
		SetMainViewData();

		//����ʹ��
		this.setVisible(true);
	}

	private void MainInit() {
		//���ñ���
		this.setTitle("MQTT�ͻ���");
		//���ô��ڿɹرգ��˳��ķ�ʽ�ж��֣�exit��dispose
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//���ô��ڴ�С
		this.setSize(720,480);
		//���ھ���
		this.setLocationRelativeTo(null);
		//�������û��������ڴ�С
		this.setResizable(false);


		//�����������ַ�ʽΪ�ղ���
		jpnMain.setLayout(null);
		//Ĭ���ǽ����ý���
		jpnMain.setFocusable(true);


		/**
		 * û���ã����ÿ�
		 */
		//���õ���������ͼƬ
		//��֪����ʲôͼƬ�ã����������Ѽ���һ��ͼ
		jpnImage = new JPanel() {
			//����һ��ͼƬ���½�һ��ImageIcon���󲢵���getImage�������һ��Image����
			/**
			 * jpanel�಻֧�����ñ���ͼ����Ҫ��дpaintComponent����
			 */
			private static final long serialVersionUID = 2019L;

			private Image image = new ImageIcon("res/main_photo.png").getImage();
			//����ϵͳҪ�������paintComponent������������ͼƬ������ϵͳ������һ��Graphics���󣨻��ʣ���
			//������Ҫ�����������������ͼƬ
			protected void paintComponent(Graphics g) {
				//���û��ʵ�drawImage������������Ҫ����ͼƬ����ʼ���꣬�������꣬�������ﻭ��this������LoginWin��
				//��������������
				g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);  
			}
			//ʵ�ֱ���ͼ
		};


		//����Ϊ�ղ���
		jpnLogin.setLayout(null);
		jpnSubscribe.setLayout(null);
		jpnPublish.setLayout(null);
		jpnChat.setLayout(null);
		jpnImage.setLayout(null);


		//��ߣ���ߣ�����
		jlbIp.setBounds(0, 0, 35, 25);//IP��
		jlbIp.setFont(defFont);
		jtfIp.setBounds(35, 0, 124, 25);//����IP
		jtfIp.setFont(defFont);
		jlbPort.setBounds(167, 0, 35, 25);//�˿ڣ�
		jlbPort.setFont(defFont);
		jtfPort.setBounds(202, 0, 48, 25);//����˿�
		jtfPort.setFont(defFont);

		jlbId.setBounds(0, 27, 47, 25);//ʶ�����
		jlbId.setFont(defFont);
		jtfId.setBounds(47, 27, 173, 25);//����ʶ���
		jtfId.setFont(defFont);
		jbtRandId.setBounds(225, 27, 25, 25);
		jbtRandId.setFont(defFont);

		jlbName.setBounds(0, 54, 35, 25);//�˺ţ�
		jlbName.setFont(defFont);
		jtfName.setBounds(35, 54, 100, 25);//�����˺�
		jtfName.setFont(defFont);
		jlbPasswd.setBounds(140, 54, 35, 25);//���룺
		jlbPasswd.setFont(defFont);
		jpfPasswd.setBounds(175, 54, 75, 25);//��������

		jlbLWT.setBounds(0, 81, 35, 25);//���ԣ�
		jlbLWT.setFont(defFont);
		jtfLWTTopic.setBounds(35, 81, 136, 25);//��������
		jtfLWTTopic.setFont(defFont);
		jcbLWTQos.setBounds(176, 81, 74, 25);//��������
		jcbLWTQos.setFont(defFont);
		jcbLWTQos.setModel(cbmMqttQos1);//��������
		jtfLWTContext.setBounds(35, 108, 215, 25);//��������
		jtfLWTContext.setFont(defFont);

		jbtMoreSet.setBounds(0, 135, 70, 25);//����
		jbtMoreSet.setFont(defFont);
		jlbSta.setBounds(95, 144, 60, 16);//״̬
		jlbSta.setForeground(Color.red);
		jlbSta.setFont(defFont);
		jbtConnect.setBounds(180, 135, 70, 25);//����
		jbtConnect.setFont(defFont);


		jpnLogin.add(jlbIp);//IP��
		jpnLogin.add(jtfIp);//����IP
		jpnLogin.add(jlbPort);//�˿ڣ�
		jpnLogin.add(jtfPort);//����˿�

		jpnLogin.add(jlbId);//ʶ�����
		jpnLogin.add(jtfId);//����ʶ���
		jpnLogin.add(jbtRandId);//���

		jpnLogin.add(jlbName);//�˺ţ�
		jpnLogin.add(jtfName);//�����˺�
		jpnLogin.add(jlbPasswd);//���룺
		jpnLogin.add(jpfPasswd);//��������

		jpnLogin.add(jlbLWT);//���ԣ�
		jpnLogin.add(jtfLWTTopic);//��������
		jpnLogin.add(jtfLWTContext);//��������
		jpnLogin.add(jcbLWTQos);//��������
		jpnLogin.add(jcbLWTQos);//��������

		jpnLogin.add(jbtConnect);//����
		jpnLogin.add(jlbSta);//״̬
		jpnLogin.add(jbtMoreSet);//����


		jlbTopic.setBounds(0, 0, 54, 25);
		jlbTopic.setFont(defFont);
		jtfSubscribeTopic.setBounds(0, 27, 100, 25);//�������������
		jtfSubscribeTopic.setFont(defFont);
		jcbTopicQos.setBounds(176, 27, 74, 25);//���ķ�������
		jcbTopicQos.setModel(cbmMqttQos2);//����Ĭ������
		jcbTopicQos.setFont(defFont);
		jbtSubscribe.setBounds(111, 27, 55, 25);//���İ�ť
		jbtSubscribe.setEnabled(false);
		jbtSubscribe.setFont(defFont);

		jpnSubscribe.add(jlbTopic);
		jpnSubscribe.add(jtfSubscribeTopic);
		jpnSubscribe.add(jcbTopicQos);
		jpnSubscribe.add(jbtSubscribe);

		jlbSend.setBounds(0, 0, 54, 25);
		jlbSend.setFont(defFont);
		jtaContext.setBounds(0, 27, 250, 66);//��Ϣ����
		jtaContext.setFont(defFont);
		jtfContextTopic.setBounds(110, 98, 140, 25);//��Ϣ���������
		jtfContextTopic.setFont(defFont);
		jcbSendQos.setBounds(0, 98, 74, 25);//��Ϣ��������
		jcbSendQos.setModel(cbmMqttQos3);
		jcbSendQos.setFont(defFont);
		jbtSend.setBounds(200, 125, 50, 25);//����
		jbtSend.setEnabled(false);
		jbtSend.setFont(defFont);
		jlbSendsta.setBounds(50, 125, 20, 25);//״ָ̬ʾ
		jlbSendTopic.setBounds(78, 98, 35, 25);//����
		jlbSendTopic.setFont(defFont);

		jpnPublish.add(jlbSend);
		jpnPublish.add(jtaContext);
		jpnPublish.add(jtfContextTopic);
		jpnPublish.add(jcbSendQos);
		jpnPublish.add(jbtSend);
		jpnPublish.add(jlbSendTopic);
		jpnPublish.add(jlbSendsta);

		jtpChat.setBounds(0, 0, 400, 405);//��Ϣ�б�
		jtpChat.setFont(defFont);
		jtpChat.setPreferredSize(new Dimension(320,405));
		
		
		jlbPicture.setBounds(115, 115, 170, 170);
		//ʵ����ImageIcon ����
		ImageIcon image = new ImageIcon("res/photo.jpg");
		//�õ�Image����
		Image img = image.getImage();
		//�������Ű汾
		img = img.getScaledInstance(170,170, Image.SCALE_DEFAULT);
		//�滻Ϊ���Ű汾
		image.setImage(img);
		jlbPicture.setIcon(image);
		jlbPicture.setBorder(BorderFactory.createLoweredBevelBorder());


		//		jtpChat.addTab("�Ự�Ự", new MQTTChatView());
		//		jtpChat.addTab("�Ự�Ự", new MQTTChatView());
		//		JLabel my = new JLabel("Minuy");
		//		my.setBounds(100, 100, 100, 100);
		//		jpnChat.add(my);

		//��һ��ͼƬ
		jpnImage.add(jtpChat);
		jpnChat.add(jtpChat);//û��ͼƬ
		jpnChat.add(jlbPicture);//û��ͼƬ
		//		jpnChat.add(jpnImage);


		//���Ӱ�ť�������������Լ����
		ActionListener mqttMainView = new MqttMainViewActionListener(this);
		jbtConnect.addActionListener(mqttMainView);
		jbtRandId.addActionListener(mqttMainView);
		jbtMoreSet.addActionListener(mqttMainView);
		jbtSend.addActionListener(mqttMainView);
		jbtSubscribe.addActionListener(mqttMainView);


		//���С
		jpnLogin.setBounds(5,5,260,170);//��¼ģ��
		jpnSubscribe.setBounds(5, 175, 260, 62);//����
		jpnPublish.setBounds(5, 242, 260, 163);//����
		jpnChat.setBounds(260, 0, 400, 405);//����
		jpnImage.setBounds(0, 0, 400, 405);//ͼƬ

		//���ñ߾�
		//jpnLogin.

		//����������ͼ�߿�
		jpnLogin.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		jpnSubscribe.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		jpnChat.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));

		//�����úõĿؼ�ȫ�ӵ�������
		jpnMain.add(jpnChat);
		jpnMain.add(jpnLogin);
		jpnMain.add(jpnSubscribe);
		jpnMain.add(jpnPublish);

		//�ѻ����ŵ�������
		this.add(jpnMain);
		Image icon = Toolkit.getDefaultToolkit().getImage("res/mqttorg-glow.png");
		this.setIconImage(icon);

		System.out.println("��ʼ�����ڳɹ���");
	}

	/**
	 * ��д���ڵ��¼���ת�����������Ǵ��������processWindowEvent���뵽���ڹر��¼���
	 */
	@Override
	protected void processWindowEvent(WindowEvent e)
	{
		//������Ҫ�Խ�����WindowEvent�����жϣ���Ϊ���������д��ڹرյ�WindowEvent��������������������WindowEvent����
		if (e.getID() == WindowEvent.WINDOW_CLOSING)
		{
			int option = JOptionPane.showConfirmDialog(null, "�Ƿ�رգ�", "�˳���ʾ", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION)
			{
				//�û�ѡ��رճ������ϴ�����ʾ��ȷ�ϴ������ദ��
				super.processWindowEvent(e);
			}
			else {
				//�û�ѡ���˳���������ѹر��¼���ȡ
				return;
			}
		}
		else {
			//����������¼����������ദ��
			super.processWindowEvent(e);
		}
	}

	/**
	 * �����ݿ��ȡ�ϴμ�¼������
	 */
	private void SetMainViewData() {
		//�����ݿ����ȡ����
		MqttSetBeans set = new MqttDAO().getSeting();
		if(set != null) {
			//�жϲ�Ϊ��
			if(!TextUtils.isEmpty(set.getId())) {
				jtfId.setText(set.getId());
			}
			//�жϲ�Ϊ��
			if(!TextUtils.isEmpty(set.getIp())) {
				jtfIp.setText(set.getIp());
			}

			//�жϲ�Ϊ��
			if(!TextUtils.isEmpty(set.getPort())) {
				jtfPort.setText(set.getPort());
			}

			//�жϲ�Ϊ��
			if(!TextUtils.isEmpty(set.getName())) {
				jtfName.setText(set.getName());
			}

			//�жϲ�Ϊ��
			if(!TextUtils.isEmpty(set.getPasswd())) {
				jpfPasswd.setText(set.getPasswd());
			}

			//�жϲ�Ϊ��
			if(!TextUtils.isEmpty(set.getLWTTopic())) {
				jtfLWTTopic.setText(set.getLWTTopic());
			}
			//�жϲ�Ϊ��
			if(!TextUtils.isEmpty(set.getLWTContext())) {
				jtfLWTContext.setText(set.getLWTContext());
			}


			//�жϲ�Ϊ��
			if(!TextUtils.isEmpty(set.getSubTopic())) {
				jtfSubscribeTopic.setText(set.getSubTopic());
			}
			//�жϲ�Ϊ��
			if(!TextUtils.isEmpty(set.getPubContext())) {
				jtaContext.setText(set.getPubContext());
			}
			//�жϲ�Ϊ��
			if(!TextUtils.isEmpty(set.getPubTopic())) {
				jtfContextTopic.setText(set.getPubTopic());
			}


			if(!TextUtils.isEmpty(set.getLWTQos())) {
				jcbLWTQos.setSelectedIndex(Integer.valueOf(set.getLWTQos()));
			}
			if(!TextUtils.isEmpty(set.getSubQos())) {
				jcbTopicQos.setSelectedIndex(Integer.valueOf(set.getSubQos()));
			}
			if(!TextUtils.isEmpty(set.getPubQos())) {
				jcbSendQos.setSelectedIndex(Integer.valueOf(set.getPubQos()));
			}

		}

	}

	public JTextField getJtfId() {
		return jtfId;
	}

	public void setJtfId(JTextField jtfId) {
		this.jtfId = jtfId;
	}

	public JButton getJbtConnect() {
		return jbtConnect;
	}

	public void setJbtConnect(JButton jbtConnect) {
		this.jbtConnect = jbtConnect;
	}

	public JTextField getJtfIp() {
		return jtfIp;
	}

	public JTextField getJtfPort() {
		return jtfPort;
	}

	public JButton getJbtRandId() {
		return jbtRandId;
	}

	public JTextField getJtfName() {
		return jtfName;
	}

	public JPasswordField getJpfPasswd() {
		return jpfPasswd;
	}

	public JTextField getJtfLWTTopic() {
		return jtfLWTTopic;
	}

	public JTextField getJtfLWTContext() {
		return jtfLWTContext;
	}

	public JComboBox<String> getJcbLWTQos() {
		return jcbLWTQos;
	}

	public JLabel getJlbSendsta() {
		return jlbSendsta;
	}

	public JLabel getJlbSta() {
		return jlbSta;
	}

	public JButton getJbtMoreSet() {
		return jbtMoreSet;
	}

	public JTextField getJtfSubscribeTopic() {
		return jtfSubscribeTopic;
	}

	public JComboBox<String> getJcbTopicQos() {
		return jcbTopicQos;
	}

	public JButton getJbtSubscribe() {
		return jbtSubscribe;
	}

	public JTextArea getJtaContext() {
		return jtaContext;
	}

	public JTextField getJtfContextTopic() {
		return jtfContextTopic;
	}

	public JComboBox<String> getJcbSendQos() {
		return jcbSendQos;
	}

	public JButton getJbtSend() {
		return jbtSend;
	}

	public DefaultComboBoxModel<String> getCbmMqttQos1() {
		return cbmMqttQos1;
	}

	public DefaultComboBoxModel<String> getCbmMqttQos2() {
		return cbmMqttQos2;
	}

	public DefaultComboBoxModel<String> getCbmMqttQos3() {
		return cbmMqttQos3;
	}

	public JTabbedPane getJtpChat() {
		return jtpChat;
	}

}
