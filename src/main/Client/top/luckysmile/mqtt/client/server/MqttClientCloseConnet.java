package top.luckysmile.mqtt.client.server;

import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
/**
 * �ر����ӵ���ز���
 * @author minuy
 *
 */
public class MqttClientCloseConnet {
	public MqttClientCloseConnet(MqttClient mC, final ViewOpt vo, MqttServerManage mqttServerManage) {
		boolean isClose = true;
		vo.SetConnetButtonEable(false);//��ť������
		try {
			mC.disconnect();
			//�رտͻ����ͷ���ͻ���������������Դ���ͻ��˹رպ������á����磬���ӳ��Խ�ʧ�ܡ�
			mC.close();
		} catch (MqttException e) {
			System.out.println(e.toString());
			isClose = false;
			JOptionPane.showMessageDialog(null, "δ���� "+e.toString(), "����", JOptionPane.ERROR_MESSAGE);
		}
		if(isClose) {
			vo.SetLoginEnable(true);//���ý���ɱ༭
			vo.SetConnetButtonEable(true);//���ð�ť����
			vo.SetConnetButtonStr("����");//�����ַ�
			vo.SetConnetButtonEable(true);
			
			mqttServerManage.setConnet(false);//����״̬�Ͽ�
			
			vo.SetSta("�ѶϿ�");
			
			vo.setSubhead("�ѶϿ�");//���ø�����
		}
	}

}
