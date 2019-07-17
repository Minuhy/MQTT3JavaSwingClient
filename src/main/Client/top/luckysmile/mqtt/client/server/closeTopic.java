package top.luckysmile.mqtt.client.server;

import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import top.luckysmile.mqtt.client.view.MQTTChatView;
/**
 * ȡ�����ģ��Ự�б���İ�ť���մ������¼�
 * @author minuy
 *
 */
public class closeTopic {
	/**
	 * ȡ�����Ĳ��رջỰ��ͼ
	 * @param vo ��ͼ�����࣬���ڹرջỰ��ͼ
	 * @param MC Mqtt�ͻ��˶���ȡ��������
	 * @param chatSession ����������ͼ�б����ڲ��ҹر��Ƿ���Ч
	 * @param mcv ����ȡ�������������ͼ����������
	 * @param mqttServerManage ���������ص���
	 */
	public closeTopic(ViewOpt vo,MqttClient MC,List<MQTTChatView> chatSession,MQTTChatView mcv, MqttServerManage mqttServerManage) {
		//������
		for (int i = 0; i < chatSession.size(); i++) {
			//����Ự�����Ҫ�رյ�������ͬ
			if(mcv.getJlbChatTopic().getText().equals(chatSession.get(i).getJlbChatTopic().getText())) {
				try {
					//ȡ����������
					MC.unsubscribe(mcv.getJlbChatTopic().getText());
					//������������б����ͼ�б���ɾ��
					mqttServerManage.SubDrop(mcv.getJlbChatTopic().getText());
					mqttServerManage.removeSession(mcv);
					//�ر���ͼ
					vo.CloseSessionWindow(mcv.getJlbChatTopic().getText());
				} catch (MqttException e) {
					System.out.println(e.toString());
					JOptionPane.showMessageDialog(null, "û�ж���������⣡","����",2);
				}
			}
		}
	}
}
