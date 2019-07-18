package top.luckysmile.mqtt.client.listen;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import top.luckysmile.mqtt.client.data.MqttDAO;
import top.luckysmile.mqtt.client.data.MqttMassageBeans;
import top.luckysmile.mqtt.client.server.MqttServerManage;
import top.luckysmile.mqtt.client.server.ViewOpt;

/**
 * �ͻ��˻ص����յ���Ϣ������״̬���⴦��
 * @author minuy
 *
 */
public class MqttClientCallback implements MqttCallback {

	ViewOpt vo;
	MqttServerManage msm;

	/**
	 * �ص���
	 * @param vo2 ����ͼ����
	 * @param cvo ������
	 */
	public MqttClientCallback(ViewOpt vo2, MqttServerManage cvo) {
		this.vo = vo2;
		this.msm = cvo;
	}

	@Override
	public void connectionLost(Throwable cause) {
		// ��������������Ӷ�ʧʱ�������ô˷�����
		JOptionPane.showMessageDialog(null, cause.toString(), "���Ӷ�ʧ��", 2);
		vo.SetSta("���Ӷ�ʧ");
		vo.setSubhead("���Ӷ�ʧ");//������
		vo.SetConnetButtonStr("����");
		vo.SetLoginEnable(true);
		//��ն���
		msm.SubEmpty();
		//δ����
		msm.setConnet(false);
	}

	@Override
	public void messageArrived(String title, MqttMessage msg) throws Exception {
		// ����Ϣ�ӷ���������ʱ�������ô˷�����
		msm.MqttReceveMassage(title,msg.toString(),String.valueOf(msg.getQos()));
		
		//�����Ǳ�����Ϣ
		System.out.println(title+":"+msg.toString());
		MqttMassageBeans massage = new MqttMassageBeans();
		massage.setContext(msg.toString());
		massage.setTopic(title);
		massage.setQos(String.valueOf(msg.getQos()));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		massage.setTime(df.format(new Date()));
		massage.setOther("Session:" + title);
		
		//����û�����ݿ�Ȩ�޿���
		final MqttMassageBeans finemassage = massage;
		new Thread() {
			public void run() {
				//����������
				new MqttDAO().SaveSession(finemassage);
			};
		}.start();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// ����Ϣ�Ĵ�������ɣ�������ȷ�����յ�ʱ���á�����QoS 0��Ϣ��һ����Ϣ���ύ��������н������ͻ������������QoS 1���ڽ���PUBACKʱ������������QoS 2���ڽ���PUBCOMPʱ�����������ƽ�����Ϣ����ʱ���ص�������ͬ��
		System.out.println(token.toString());
		vo.SetSendSta(false);//���
	}

}
