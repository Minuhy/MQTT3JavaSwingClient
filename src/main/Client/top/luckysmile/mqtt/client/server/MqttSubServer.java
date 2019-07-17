package top.luckysmile.mqtt.client.server;

import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import top.luckysmile.mqtt.client.data.MqttSetBeans;
import top.luckysmile.mqtt.client.view.MQTTChatView;
/**
 * �ͻ��˶��ĺ��������ڶ��ĵ�����߼�
 * @author minuy
 *
 */
public class MqttSubServer extends Thread {
	MqttSetBeans set;
	ViewOpt vo;
	MqttClient mc;
	MqttServerManage msm;
	boolean isCreat;
	MQTTChatView mcv;

	/**
	 * ���Ĳ������Ự����Ҫ����.start��������
	 * @param set MQTTSetBeanȡ�õ����ݣ����б������������Ϣ�����������Ч
	 * @param vo ��ͼ�����࣬��Ҫʵ�ֻỰ���ڵĴ���
	 * @param mC �����ӵ�MQTT�ͻ��ˣ�δ���ӳ���
	 * @param msm �ص��ã���ɶ��ĺ���ӵ����������б���
	 * @param en �Ƿ��Ѵ��ڻỰ�����������ã�δ�����򴴽�
	 * @param mcv ���������ڣ���Ҫ�õ�����������ͼ
	 */
	public MqttSubServer(MqttSetBeans set, ViewOpt vo, MqttClient mC, MqttServerManage msm,boolean en, MQTTChatView mcv) {
		this.set = set;
		this.vo = vo;
		this.mc = mC;
		this.msm = msm;
		this.isCreat = en;
		this.mcv = mcv;
	}

	/**
	 * ������Ϣ
	 */
	@Override
	public void run() {

		vo.setSubBtnEanle(false);//��ť������
		
		//ȷ���˿ͻ��˵�ǰ�Ƿ����ӵ���������
		if(!mc.isConnected()) {
			return;
		}
		//�Ƿ�õ�������
		if(set == null) {
			return;
		}
		boolean isOk = true;
		String topic = set.getSubTopic();
		int qos = Integer.valueOf(set.getSubQos());

		if(TextUtils.isEmpty(topic)) {
			System.out.println("�����쳣��");
			return;
		}
		if(qos>2||qos<0) {
			System.out.println("SubQos�쳣");
			qos = 0;
		}

		vo.setSubBtnEanle(false);
		//����
		try {
			mc.subscribe(topic,qos);
		} catch (NumberFormatException e) {
			System.out.println(e.toString());
			isOk = false;
			JOptionPane.showMessageDialog(null, e.toString(), "����", JOptionPane.ERROR_MESSAGE);
		} catch (MqttException e) {
			System.out.println(e.toString());
			isOk = false;
			JOptionPane.showMessageDialog(null, e.toString(), "����", JOptionPane.ERROR_MESSAGE);
		}

		if(isOk&&(!isCreat)) {
			//���ĳɹ�
			MQTTChatView newView = vo.CreateSession(topic);
			msm.addSession(newView);
		}else {
			if(mcv!=null) {
				mcv.SetSubEnable(true);
			}
		}
		
		if(isOk) {
			msm.SubAdd(topic);
		}
		
		vo.setSubBtnEanle(true);//���İ�ť����
	}


}
