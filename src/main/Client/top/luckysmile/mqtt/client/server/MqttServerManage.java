package top.luckysmile.mqtt.client.server;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.MqttClient;

import top.luckysmile.mqtt.client.data.MqttDAO;
import top.luckysmile.mqtt.client.data.MqttSetBeans;
import top.luckysmile.mqtt.client.listen.MqttClientCallback;
import top.luckysmile.mqtt.client.view.MQTTChatView;
import top.luckysmile.mqtt.client.view.MainView;
/**
 * 服务管理器，负责调度所有服务
 * @author minuy
 *
 */
public class MqttServerManage {
	private ViewOpt vo;//视图操作类
	private ChatViewOpt cvo;//会话视图操作类
	private boolean isConnet = false;//连接状态
	private MqttClient MC;//客户端对象
	private List<MQTTChatView> chatSession = new ArrayList<MQTTChatView>();//会话视图列表
	private List<String> subTopic = new ArrayList<String>();//已订阅主题列表

	private static MqttServerManage msm = null;

	/**
	 * 单例化
	 */
	private  MqttServerManage() {
		this.cvo = ChatViewOpt.getChatViewOpt();
	}

	/**
	 * 返回一个服务管理器，在操作视图前需要调用public void setViewOpt(MainView mv)方法设置被操作的视图
	 * @return 一个服务管理器
	 */
	public static MqttServerManage GetMqttServerManage() {
		if(msm != null) {
			return msm;
		}else {
			msm = new MqttServerManage();
			return msm;
		}
	}

	/**
	 * 生成一个视图操作对象
	 * @param mv
	 */
	public void setViewOpt(MainView mv) {
		this.vo = new ViewOpt(mv);
	}


	/**
	 * 连接MQTT服务器
	 */
	public void MqttConnet() {
		if(isConnet) {
			//断开
			new MqttClientCloseConnet(MC,vo,this);
			//清空订阅
			SubEmpty();
		}else {
			//连接
			MqttSetBeans set = vo.getMainViewData();//获取数据 

			MqttClientConnet mqttClient = new MqttClientConnet(set,vo,this);
			mqttClient.start();//连接

			//保存下设置
			new MqttDAO().SaveSet(set);
		}
	}

	/**
	 * 左边的自定义主题发送
	 */
	public void MqttPubCustomTopic() {
		MqttSetBeans set = vo.getMainViewData();//获取数据
		MqttSendPub send = new MqttSendPub(set,MC);
		vo.SetSendSta(true);//发送中
		send.start();
	}

	/**
	 * MQTT订阅
	 */
	public void MqttSub() {
		MQTTChatView mcv = null;
		MqttSetBeans set = vo.getMainViewData();//获取数据
		boolean isCreat = false;
		//防止重复订阅
		System.out.println("检测重复......");
		for (int i = 0; i < subTopic.size(); i++) {
			//与主题列表比较
			if(set.getSubTopic().equals(subTopic.get(i))) {
				JOptionPane.showMessageDialog(null, "已订阅！");
				return;
			}
		}

		//看会话视图是否存在，存在则不用再创建
		for (int i = 0; i < chatSession.size(); i++) {
			if(set.getSubTopic().equals(chatSession.get(i).getJlbChatTopic().getText())) {
				isCreat = true;
				mcv = chatSession.get(i);
			}
		}

		MqttSubServer sub = new MqttSubServer(set,vo,MC,this,isCreat,mcv);
		sub.start();

		//避免没有数据库权限卡死
		final MqttSetBeans setfine = set;
		new Thread() {
			public void run() {
				//保存下设置
				new MqttDAO().SaveSet(setfine);
			};
		}.start();

	}

	/**
	 * 生成随机ID
	 */
	public void RandId() {
		String id = new GeneratePassword().get(8);
		vo.SetClictID(id);
	}

	/**
	 * 查询连接状态
	 * @return 连接状态，true：已连接，false：未连接
	 */
	public boolean isConnet() {
		return isConnet;
	}

	/**
	 * 设置连接状态
	 * @param isConnet true:已连接，false：未连接
	 */
	public void setConnet(boolean isConnet) {
		this.isConnet = isConnet;
	}

	/**
	 * 设置连接的客户端对象，连接成功后线程调用返回客户端对象
	 * @param mC 已连接的客户端
	 */
	public void setMC(MqttClient mC) {
		MC = mC;
		if(MC.isConnected()) {
			MC.setCallback(new MqttClientCallback(vo,this));
			System.out.println("连接成功...");
		}
	}

	/**
	 * 把新建的会话视图存到列表里
	 * @param newView
	 */
	public void addSession(MQTTChatView newView) {
		chatSession.add(newView);
	}

	/**
	 * 从视图列表里删除一个视图
	 * @param newView
	 */
	public void removeSession(MQTTChatView rmView) {
		for (int i = 0; i < chatSession.size(); i++) {
			if(chatSession.get(i) == rmView) {
				chatSession.remove(i);
				break;
			}
		}
	}


	/**
	 * 右边的自动主题发送
	 */
	public void SessionSend(MQTTChatView mcv) {
		String[] sendData;
		sendData = cvo.getSendInfo(mcv);
		MqttSendPub send = new MqttSendPub(sendData,MC);
		send.start();
	}

	/**
	 * 清除会话内容
	 * @param mcv
	 */
	public void CleanSession(MQTTChatView mcv) {
		cvo.CleanSession(mcv);
		cvo.addBitNumber(mcv, -1);//清空计数器
	}

	/**
	 * 关闭会话窗口
	 * 取消订阅
	 * @param mcv
	 */
	public void CloseSession(MQTTChatView mcv) {
		new closeTopic(vo, MC, chatSession, mcv,this);
	}

	/**
	 * 收到消息
	 * @param topic
	 * @param string
	 */
	public void MqttReceveMassage(String topic, String string, String qos) {
		for (int i = 0; i < chatSession.size(); i++) {
			if(chatSession.get(i).getJlbChatTopic().getText().equals(topic)) {
				cvo.AddMassage(string, chatSession.get(i),qos);//会话
				cvo.addBitNumber(chatSession.get(i),TextUtils.getStringLength(string));//计数器
				break;
			}
		}
	}

	/**
	 * 增加一个订阅
	 */
	public void SubAdd(String topic) {
		subTopic.add(topic);
	}

	/**
	 * 通过名字删除一个订阅
	 */
	public void SubDrop(String topic) {
		for (int i = 0; i < subTopic.size(); i++) {
			if(topic.equals(subTopic.get(i))) {
				subTopic.remove(i);
			}
		}
	}

	/**
	 * 重建订阅列表
	 */
	public void SubEmpty() {
		subTopic = new ArrayList<String>();
		//取消订阅按钮
		for (int i = 0; i < chatSession.size(); i++) {
			chatSession.get(i).SetSubEnable(false);
		}
	}

}
