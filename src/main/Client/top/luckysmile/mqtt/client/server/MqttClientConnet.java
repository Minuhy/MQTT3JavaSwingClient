package top.luckysmile.mqtt.client.server;

import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import top.luckysmile.mqtt.client.data.MqttSetBeans;

/**
 * �ͻ������Ӻ��������ӵ�������
 * @author minuy
 *
 */
public class MqttClientConnet extends Thread{
	MqttClient mqttClient;//�ص���
	MqttSetBeans set;
	ViewOpt vo;
	boolean isConnet;
	MqttServerManage msm;

	/**
	 * �½�һ��MQTT����
	 * @param setBeans һ�������õ���
	 */
	public MqttClientConnet(MqttSetBeans setBeans,ViewOpt vo,MqttServerManage msm) {
		this.set = setBeans;
		this.vo = vo;
		this.msm = msm;
	}

	@Override
	public void run() {
		//���ݷǿ��ж�
		if(set == null) {
			return;
		}

		vo.SetSta("��������");//��ʾ״̬��������
		vo.SetLoginEnable(false);//���ý��治�ɱ༭
		vo.SetConnetButtonEable(false);//���ð�ť������

		isConnet = true;

		String port = set.getId();
		String url = "tcp://" + set.getIp() + ":" + set.getPort();
		MemoryPersistence persistence = new MemoryPersistence();
		//���������Ϣ
		MqttConnectOptions connOpts = new MqttConnectOptions(); 
		connOpts.setCleanSession(true);
		//�������ⲻΪ��
		if(!TextUtils.isEmpty(set.getLWTTopic())) {
			if(TextUtils.isEmpty(set.getLWTContext())) {
				set.setLWTContext("");
			}
			connOpts.setWill(set.getLWTTopic(), set.getLWTContext().getBytes(), Integer.valueOf(set.getLWTQos()), true);
		}
		//�˺�����
		if(!TextUtils.isEmpty(set.getName())) {
			connOpts.setUserName(set.getName());
			connOpts.setPassword(vo.getViewPasswd());
		}

		//�½��ͻ���
		try {
			mqttClient= new MqttClient(url, port, persistence);
		} catch (MqttException e) {
			System.out.println(e.toString());
		}
		System.out.println("���ӵ�������: "+"tcp://" + set.getIp() + ":" + set.getPort());
		try {
			mqttClient.connect(connOpts);
		} catch (MqttException e) {
			//����ʧ��
			isConnet = false;
			JOptionPane.showMessageDialog(null, e.toString(), "����", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		if(isConnet) {
			//���ӳɹ�
			vo.SetSta("������");//��ʾ״̬δ����
			vo.SetConnetButtonEable(true);//���ð�ť����
			vo.SetConnetButtonStr("�Ͽ�");
			msm.setMC(mqttClient);
			msm.setConnet(true);
			
			vo.setSubhead("������:"+url);
		}else {
			//����ʧ��
			vo.SetSta("δ����");//��ʾ״̬δ����
			vo.SetLoginEnable(true);//���ý���ɱ༭
			vo.SetConnetButtonEable(true);//���ð�ť����
			msm.setConnet(false);
		}
	}
}
