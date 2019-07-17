package top.luckysmile.mqtt.client.server;

import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import top.luckysmile.mqtt.client.data.MqttSetBeans;

/**
 * �����̣߳�������Ϣʱ����
 * @author minuy
 *
 */
public class MqttSendPub extends Thread{
	
	String[] info = null;
	MqttClient mc;
	
	public MqttSendPub(String[] info,MqttClient mc) {
		this.info = info;
		this.mc = mc;
	}
	
	public MqttSendPub(MqttSetBeans set,MqttClient mc) {
		if(set == null) {
			return;
		}
		info = new String[3];
		
		this.info[2] = set.getPubContext();
		this.info[1] = set.getPubQos();
		this.info[0] = set.getPubTopic();
		this.mc = mc;
	}

	@Override
	public void run() {
		if(!mc.isConnected()) {
			System.out.println("û�����ӣ�");
			JOptionPane.showMessageDialog(null, "û�����ӣ�", "ʧ��", 2);
			return;
		}
		String msm = info[2];
		String topic = info[0];
		int qos;
		if(!TextUtils.isEmpty(info[1])) {
			qos = Integer.valueOf(info[1]);
		}else {
			qos = 0;
		}
		
		if(TextUtils.isEmpty(topic)) {
			System.out.println("�����쳣��");
			return;
		}
		
		if(TextUtils.isEmpty(msm)) {
			System.out.println("��Ϣ�쳣��");
			return;
		}
		
		MqttMessage message = new MqttMessage(info[2].getBytes());
		message.setQos(qos);
		
		try {
			mc.publish(topic, message);
		} catch (MqttPersistenceException e) {
			System.out.println(e.toString());
		} catch (MqttException e) {
			System.out.println(e.toString());
		}
	}
}
