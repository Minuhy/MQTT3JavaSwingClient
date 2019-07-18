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
 * ���������������������з���
 * @author minuy
 *
 */
public class MqttServerManage {
	private ViewOpt vo;//��ͼ������
	private ChatViewOpt cvo;//�Ự��ͼ������
	private boolean isConnet = false;//����״̬
	private MqttClient MC;//�ͻ��˶���
	private List<MQTTChatView> chatSession = new ArrayList<MQTTChatView>();//�Ự��ͼ�б�
	private List<String> subTopic = new ArrayList<String>();//�Ѷ��������б�

	private static MqttServerManage msm = null;

	/**
	 * ������
	 */
	private  MqttServerManage() {
		this.cvo = ChatViewOpt.getChatViewOpt();
	}

	/**
	 * ����һ��������������ڲ�����ͼǰ��Ҫ����public void setViewOpt(MainView mv)�������ñ���������ͼ
	 * @return һ�����������
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
	 * ����һ����ͼ��������
	 * @param mv
	 */
	public void setViewOpt(MainView mv) {
		this.vo = new ViewOpt(mv);
	}


	/**
	 * ����MQTT������
	 */
	public void MqttConnet() {
		if(isConnet) {
			//�Ͽ�
			new MqttClientCloseConnet(MC,vo,this);
			//��ն���
			SubEmpty();
		}else {
			//����
			MqttSetBeans set = vo.getMainViewData();//��ȡ���� 

			MqttClientConnet mqttClient = new MqttClientConnet(set,vo,this);
			mqttClient.start();//����

			//����������
			new MqttDAO().SaveSet(set);
		}
	}

	/**
	 * ��ߵ��Զ������ⷢ��
	 */
	public void MqttPubCustomTopic() {
		MqttSetBeans set = vo.getMainViewData();//��ȡ����
		MqttSendPub send = new MqttSendPub(set,MC);
		vo.SetSendSta(true);//������
		send.start();
	}

	/**
	 * MQTT����
	 */
	public void MqttSub() {
		MQTTChatView mcv = null;
		MqttSetBeans set = vo.getMainViewData();//��ȡ����
		boolean isCreat = false;
		//��ֹ�ظ�����
		System.out.println("����ظ�......");
		for (int i = 0; i < subTopic.size(); i++) {
			//�������б�Ƚ�
			if(set.getSubTopic().equals(subTopic.get(i))) {
				JOptionPane.showMessageDialog(null, "�Ѷ��ģ�");
				return;
			}
		}

		//���Ự��ͼ�Ƿ���ڣ����������ٴ���
		for (int i = 0; i < chatSession.size(); i++) {
			if(set.getSubTopic().equals(chatSession.get(i).getJlbChatTopic().getText())) {
				isCreat = true;
				mcv = chatSession.get(i);
			}
		}

		MqttSubServer sub = new MqttSubServer(set,vo,MC,this,isCreat,mcv);
		sub.start();

		//����û�����ݿ�Ȩ�޿���
		final MqttSetBeans setfine = set;
		new Thread() {
			public void run() {
				//����������
				new MqttDAO().SaveSet(setfine);
			};
		}.start();

	}

	/**
	 * �������ID
	 */
	public void RandId() {
		String id = new GeneratePassword().get(8);
		vo.SetClictID(id);
	}

	/**
	 * ��ѯ����״̬
	 * @return ����״̬��true�������ӣ�false��δ����
	 */
	public boolean isConnet() {
		return isConnet;
	}

	/**
	 * ��������״̬
	 * @param isConnet true:�����ӣ�false��δ����
	 */
	public void setConnet(boolean isConnet) {
		this.isConnet = isConnet;
	}

	/**
	 * �������ӵĿͻ��˶������ӳɹ����̵߳��÷��ؿͻ��˶���
	 * @param mC �����ӵĿͻ���
	 */
	public void setMC(MqttClient mC) {
		MC = mC;
		if(MC.isConnected()) {
			MC.setCallback(new MqttClientCallback(vo,this));
			System.out.println("���ӳɹ�...");
		}
	}

	/**
	 * ���½��ĻỰ��ͼ�浽�б���
	 * @param newView
	 */
	public void addSession(MQTTChatView newView) {
		chatSession.add(newView);
	}

	/**
	 * ����ͼ�б���ɾ��һ����ͼ
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
	 * �ұߵ��Զ����ⷢ��
	 */
	public void SessionSend(MQTTChatView mcv) {
		String[] sendData;
		sendData = cvo.getSendInfo(mcv);
		MqttSendPub send = new MqttSendPub(sendData,MC);
		send.start();
	}

	/**
	 * ����Ự����
	 * @param mcv
	 */
	public void CleanSession(MQTTChatView mcv) {
		cvo.CleanSession(mcv);
		cvo.addBitNumber(mcv, -1);//��ռ�����
	}

	/**
	 * �رջỰ����
	 * ȡ������
	 * @param mcv
	 */
	public void CloseSession(MQTTChatView mcv) {
		new closeTopic(vo, MC, chatSession, mcv,this);
	}

	/**
	 * �յ���Ϣ
	 * @param topic
	 * @param string
	 */
	public void MqttReceveMassage(String topic, String string, String qos) {
		for (int i = 0; i < chatSession.size(); i++) {
			if(chatSession.get(i).getJlbChatTopic().getText().equals(topic)) {
				cvo.AddMassage(string, chatSession.get(i),qos);//�Ự
				cvo.addBitNumber(chatSession.get(i),TextUtils.getStringLength(string));//������
				break;
			}
		}
	}

	/**
	 * ����һ������
	 */
	public void SubAdd(String topic) {
		subTopic.add(topic);
	}

	/**
	 * ͨ������ɾ��һ������
	 */
	public void SubDrop(String topic) {
		for (int i = 0; i < subTopic.size(); i++) {
			if(topic.equals(subTopic.get(i))) {
				subTopic.remove(i);
			}
		}
	}

	/**
	 * �ؽ������б�
	 */
	public void SubEmpty() {
		subTopic = new ArrayList<String>();
		//ȡ�����İ�ť
		for (int i = 0; i < chatSession.size(); i++) {
			chatSession.get(i).SetSubEnable(false);
		}
	}

}
