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
 * 主界面，四个板块，登录，订阅，发送和会话
 * @author minuy
 *
 */
public class MainView extends JFrame {
	/**
	 * 主面板，负责登录，发送，订阅和推送
	 */
	private static final long serialVersionUID = 1L;
	Font defFont;//默认字体

	private JPanel jpnMain;//主面板
	private JPanel jpnLogin;//登录面板
	private JPanel jpnSubscribe;//订阅面板
	private JPanel jpnPublish;//发送面板
	private JPanel jpnChat;//消息面板
	private JPanel jpnImage;//图片面板

	private JLabel jlbIp;//IP标签
	private JTextField jtfIp;//IP输入框
	private JLabel jlbPort;//端口
	private JTextField jtfPort;//端口输入框
	private JLabel jlbId;//识标符
	private JTextField jtfId;//识标符输入框
	private JButton jbtRandId;//随机识标符
	private JLabel jlbName;//账号
	private JTextField jtfName;//账号输入框
	private JLabel jlbPasswd;//密码
	private JPasswordField jpfPasswd;//密码输入框
	private JLabel jlbLWT;//遗言
	private JTextField jtfLWTTopic;//遗言主题输入框
	private JTextField jtfLWTContext;//遗言内容输入框
	private JComboBox<String> jcbLWTQos;//遗言服务质量
	private JButton jbtConnect;//连接按钮
	private JLabel jlbSta;//连接状态
	private JButton jbtMoreSet;//更多设置

	private JLabel jlbTopic;//订阅主题
	private JTextField jtfSubscribeTopic;//订阅主题输入框
	private JComboBox<String> jcbTopicQos;//订阅服务质量
	private JButton jbtSubscribe;//订阅按钮

	private JLabel jlbSend;//消息发送
	private JTextArea jtaContext;//消息内容
	private JTextField jtfContextTopic;//消息主题输入框
	private JComboBox<String> jcbSendQos;//消息服务质量
	private JButton jbtSend;//发送
	private JLabel jlbSendTopic;
	private JLabel jlbSendsta;//消息状态指示

	private JTabbedPane jtpChat;//消息列表
	private JLabel jlbPicture;

	String[] MqttQos = {"Qos0","Qos1","Qos2"};
	DefaultComboBoxModel<String> cbmMqttQos1;
	DefaultComboBoxModel<String> cbmMqttQos2;
	DefaultComboBoxModel<String> cbmMqttQos3;

	/**
	 * 创建主视图
	 */
	public MainView() {
		defFont = new Font("微软雅黑", Font.PLAIN, 12);
		cbmMqttQos1 = new DefaultComboBoxModel<String>(MqttQos);
		cbmMqttQos2 = new DefaultComboBoxModel<String>(MqttQos);
		cbmMqttQos3 = new DefaultComboBoxModel<String>(MqttQos);

		jpnMain = new JPanel();//主面板
		jpnLogin = new JPanel();//登录面板
		jpnSubscribe = new JPanel();//订阅面板
		jpnPublish = new JPanel();//发送面板
		jpnChat = new JPanel();//消息面板

		jlbIp = new JLabel("域名:");//IP标签
		jtfIp = new JTextField();//IP输入框
		jlbPort = new JLabel("端口:");//端口
		jtfPort = new JTextField();//端口输入框
		jlbId = new JLabel("识标符:");//识标符
		jbtRandId = new JButton("*");
		jtfId = new JTextField();//识标符输入框
		jlbName = new JLabel("账号:");//账号
		jtfName = new JTextField();//账号输入框
		jlbPasswd = new JLabel("密码:");//密码
		jpfPasswd = new JPasswordField();//密码输入框
		jlbLWT = new JLabel("遗言:");//遗言
		jtfLWTTopic = new JTextField();//遗言主题输入框
		jtfLWTContext = new JTextField();//遗言内容输入框
		jcbLWTQos = new JComboBox<String>();//遗言服务质量
		jbtConnect = new JButton("连接");//连接按钮
		jlbSta = new JLabel("未连接",JLabel.CENTER);//连接状态
		jbtMoreSet = new JButton("更多");//更多设置

		jlbTopic = new JLabel("订阅主题:");//订阅主题
		jtfSubscribeTopic = new JTextField();//订阅主题输入框
		jcbTopicQos = new JComboBox<String>();//订阅服务质量
		jbtSubscribe = new JButton("订阅");//订阅按钮

		jlbSend = new JLabel("发布消息:");//发布消息
		jtaContext = new JTextArea();//消息内容
		jtfContextTopic = new JTextField();//消息主题输入框
		jcbSendQos = new JComboBox<String>();//消息服务质量
		jlbSendTopic = new JLabel("主题:");
		jbtSend = new JButton("发送");
		jlbSendsta = new JLabel();

		jtpChat = new JTabbedPane(JTabbedPane.LEFT);//消息列表
		jlbPicture = new JLabel();

		MainInit();
		SetMainViewData();

		//窗口使能
		this.setVisible(true);
	}

	private void MainInit() {
		//设置标题
		this.setTitle("MQTT客户端");
		//设置窗口可关闭，退出的方式有多种，exit和dispose
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置窗口大小
		this.setSize(720,480);
		//窗口居中
		this.setLocationRelativeTo(null);
		//不允许用户调整窗口大小
		this.setResizable(false);


		//设置容器布局方式为空布局
		jpnMain.setLayout(null);
		//默认是界面获得焦点
		jpnMain.setFocusable(true);


		/**
		 * 没有用，不好看
		 */
		//觉得单调，加张图片
		//不知道加什么图片好，网上随意搜集了一幅图
		jpnImage = new JPanel() {
			//定义一张图片，新建一个ImageIcon对象并调用getImage方法获得一个Image对象
			/**
			 * jpanel类不支持设置背景图，需要重写paintComponent方法
			 */
			private static final long serialVersionUID = 2019L;

			private Image image = new ImageIcon("res/main_photo.png").getImage();
			//这里系统要调用这个paintComponent方法来画这张图片，这里系统传入了一个Graphics对象（画笔），
			//我们需要用这个对象来画背景图片
			protected void paintComponent(Graphics g) {
				//调用画笔的drawImage方法，参数是要画的图片，初始坐标，结束坐标，和在哪里画，this代表是LoginWin这
				//个“画布”对象
				g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);  
			}
			//实现背景图
		};


		//设置为空布局
		jpnLogin.setLayout(null);
		jpnSubscribe.setLayout(null);
		jpnPublish.setLayout(null);
		jpnChat.setLayout(null);
		jpnImage.setLayout(null);


		//宽高，宽高，横竖
		jlbIp.setBounds(0, 0, 35, 25);//IP：
		jlbIp.setFont(defFont);
		jtfIp.setBounds(35, 0, 124, 25);//输入IP
		jtfIp.setFont(defFont);
		jlbPort.setBounds(167, 0, 35, 25);//端口：
		jlbPort.setFont(defFont);
		jtfPort.setBounds(202, 0, 48, 25);//输入端口
		jtfPort.setFont(defFont);

		jlbId.setBounds(0, 27, 47, 25);//识标符：
		jlbId.setFont(defFont);
		jtfId.setBounds(47, 27, 173, 25);//输入识标符
		jtfId.setFont(defFont);
		jbtRandId.setBounds(225, 27, 25, 25);
		jbtRandId.setFont(defFont);

		jlbName.setBounds(0, 54, 35, 25);//账号：
		jlbName.setFont(defFont);
		jtfName.setBounds(35, 54, 100, 25);//输入账号
		jtfName.setFont(defFont);
		jlbPasswd.setBounds(140, 54, 35, 25);//密码：
		jlbPasswd.setFont(defFont);
		jpfPasswd.setBounds(175, 54, 75, 25);//输入密码

		jlbLWT.setBounds(0, 81, 35, 25);//遗言：
		jlbLWT.setFont(defFont);
		jtfLWTTopic.setBounds(35, 81, 136, 25);//遗言主题
		jtfLWTTopic.setFont(defFont);
		jcbLWTQos.setBounds(176, 81, 74, 25);//遗言质量
		jcbLWTQos.setFont(defFont);
		jcbLWTQos.setModel(cbmMqttQos1);//遗言质量
		jtfLWTContext.setBounds(35, 108, 215, 25);//遗言内容
		jtfLWTContext.setFont(defFont);

		jbtMoreSet.setBounds(0, 135, 70, 25);//更多
		jbtMoreSet.setFont(defFont);
		jlbSta.setBounds(95, 144, 60, 16);//状态
		jlbSta.setForeground(Color.red);
		jlbSta.setFont(defFont);
		jbtConnect.setBounds(180, 135, 70, 25);//连接
		jbtConnect.setFont(defFont);


		jpnLogin.add(jlbIp);//IP：
		jpnLogin.add(jtfIp);//输入IP
		jpnLogin.add(jlbPort);//端口：
		jpnLogin.add(jtfPort);//输入端口

		jpnLogin.add(jlbId);//识标符：
		jpnLogin.add(jtfId);//输入识标符
		jpnLogin.add(jbtRandId);//随机

		jpnLogin.add(jlbName);//账号：
		jpnLogin.add(jtfName);//输入账号
		jpnLogin.add(jlbPasswd);//密码：
		jpnLogin.add(jpfPasswd);//输入密码

		jpnLogin.add(jlbLWT);//遗言：
		jpnLogin.add(jtfLWTTopic);//遗言主题
		jpnLogin.add(jtfLWTContext);//遗言内容
		jpnLogin.add(jcbLWTQos);//遗言质量
		jpnLogin.add(jcbLWTQos);//遗言质量

		jpnLogin.add(jbtConnect);//连接
		jpnLogin.add(jlbSta);//状态
		jpnLogin.add(jbtMoreSet);//更多


		jlbTopic.setBounds(0, 0, 54, 25);
		jlbTopic.setFont(defFont);
		jtfSubscribeTopic.setBounds(0, 27, 100, 25);//订阅主题输入框
		jtfSubscribeTopic.setFont(defFont);
		jcbTopicQos.setBounds(176, 27, 74, 25);//订阅服务质量
		jcbTopicQos.setModel(cbmMqttQos2);//设置默认质量
		jcbTopicQos.setFont(defFont);
		jbtSubscribe.setBounds(111, 27, 55, 25);//订阅按钮
		jbtSubscribe.setEnabled(false);
		jbtSubscribe.setFont(defFont);

		jpnSubscribe.add(jlbTopic);
		jpnSubscribe.add(jtfSubscribeTopic);
		jpnSubscribe.add(jcbTopicQos);
		jpnSubscribe.add(jbtSubscribe);

		jlbSend.setBounds(0, 0, 54, 25);
		jlbSend.setFont(defFont);
		jtaContext.setBounds(0, 27, 250, 66);//消息内容
		jtaContext.setFont(defFont);
		jtfContextTopic.setBounds(110, 98, 140, 25);//消息主题输入框
		jtfContextTopic.setFont(defFont);
		jcbSendQos.setBounds(0, 98, 74, 25);//消息服务质量
		jcbSendQos.setModel(cbmMqttQos3);
		jcbSendQos.setFont(defFont);
		jbtSend.setBounds(200, 125, 50, 25);//发送
		jbtSend.setEnabled(false);
		jbtSend.setFont(defFont);
		jlbSendsta.setBounds(50, 125, 20, 25);//状态指示
		jlbSendTopic.setBounds(78, 98, 35, 25);//主题
		jlbSendTopic.setFont(defFont);

		jpnPublish.add(jlbSend);
		jpnPublish.add(jtaContext);
		jpnPublish.add(jtfContextTopic);
		jpnPublish.add(jcbSendQos);
		jpnPublish.add(jbtSend);
		jpnPublish.add(jlbSendTopic);
		jpnPublish.add(jlbSendsta);

		jtpChat.setBounds(0, 0, 400, 405);//消息列表
		jtpChat.setFont(defFont);
		jtpChat.setPreferredSize(new Dimension(320,405));
		
		
		jlbPicture.setBounds(115, 115, 170, 170);
		//实例化ImageIcon 对象
		ImageIcon image = new ImageIcon("res/photo.jpg");
		//得到Image对象
		Image img = image.getImage();
		//创建缩放版本
		img = img.getScaledInstance(170,170, Image.SCALE_DEFAULT);
		//替换为缩放版本
		image.setImage(img);
		jlbPicture.setIcon(image);
		jlbPicture.setBorder(BorderFactory.createLoweredBevelBorder());


		//		jtpChat.addTab("会话会话", new MQTTChatView());
		//		jtpChat.addTab("会话会话", new MQTTChatView());
		//		JLabel my = new JLabel("Minuy");
		//		my.setBounds(100, 100, 100, 100);
		//		jpnChat.add(my);

		//加一层图片
		jpnImage.add(jtpChat);
		jpnChat.add(jtpChat);//没有图片
		jpnChat.add(jlbPicture);//没有图片
		//		jpnChat.add(jpnImage);


		//增加按钮监听，监听类自己设计
		ActionListener mqttMainView = new MqttMainViewActionListener(this);
		jbtConnect.addActionListener(mqttMainView);
		jbtRandId.addActionListener(mqttMainView);
		jbtMoreSet.addActionListener(mqttMainView);
		jbtSend.addActionListener(mqttMainView);
		jbtSubscribe.addActionListener(mqttMainView);


		//块大小
		jpnLogin.setBounds(5,5,260,170);//登录模块
		jpnSubscribe.setBounds(5, 175, 260, 62);//订阅
		jpnPublish.setBounds(5, 242, 260, 163);//发布
		jpnChat.setBounds(260, 0, 400, 405);//聊天
		jpnImage.setBounds(0, 0, 400, 405);//图片

		//设置边距
		//jpnLogin.

		//配置内容视图边框
		jpnLogin.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		jpnSubscribe.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		jpnChat.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));

		//把设置好的控件全加到画布里
		jpnMain.add(jpnChat);
		jpnMain.add(jpnLogin);
		jpnMain.add(jpnSubscribe);
		jpnMain.add(jpnPublish);

		//把画布放到窗口里
		this.add(jpnMain);
		Image icon = Toolkit.getDefaultToolkit().getImage("res/mqttorg-glow.png");
		this.setIconImage(icon);

		System.out.println("初始化窗口成功！");
	}

	/**
	 * 重写窗口的事件中转方法，程序是从这个方法processWindowEvent进入到窗口关闭事件的
	 */
	@Override
	protected void processWindowEvent(WindowEvent e)
	{
		//这里需要对进来的WindowEvent进行判断，因为，不仅会有窗口关闭的WindowEvent进来，还可能有其他的WindowEvent进来
		if (e.getID() == WindowEvent.WINDOW_CLOSING)
		{
			int option = JOptionPane.showConfirmDialog(null, "是否关闭？", "退出提示", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION)
			{
				//用户选择关闭程序，以上代码提示后确认传给父类处理
				super.processWindowEvent(e);
			}
			else {
				//用户选择不退出程序，这里把关闭事件截取
				return;
			}
		}
		else {
			//如果是其他事件，交给父类处理
			super.processWindowEvent(e);
		}
	}

	/**
	 * 从数据库读取上次记录的数据
	 */
	private void SetMainViewData() {
		//从数据库里获取数据
		MqttSetBeans set = new MqttDAO().getSeting();
		if(set != null) {
			//判断不为空
			if(!TextUtils.isEmpty(set.getId())) {
				jtfId.setText(set.getId());
			}
			//判断不为空
			if(!TextUtils.isEmpty(set.getIp())) {
				jtfIp.setText(set.getIp());
			}

			//判断不为空
			if(!TextUtils.isEmpty(set.getPort())) {
				jtfPort.setText(set.getPort());
			}

			//判断不为空
			if(!TextUtils.isEmpty(set.getName())) {
				jtfName.setText(set.getName());
			}

			//判断不为空
			if(!TextUtils.isEmpty(set.getPasswd())) {
				jpfPasswd.setText(set.getPasswd());
			}

			//判断不为空
			if(!TextUtils.isEmpty(set.getLWTTopic())) {
				jtfLWTTopic.setText(set.getLWTTopic());
			}
			//判断不为空
			if(!TextUtils.isEmpty(set.getLWTContext())) {
				jtfLWTContext.setText(set.getLWTContext());
			}


			//判断不为空
			if(!TextUtils.isEmpty(set.getSubTopic())) {
				jtfSubscribeTopic.setText(set.getSubTopic());
			}
			//判断不为空
			if(!TextUtils.isEmpty(set.getPubContext())) {
				jtaContext.setText(set.getPubContext());
			}
			//判断不为空
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
