package top.luckysmile.mqtt.client.listen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import top.luckysmile.mqtt.client.server.MqttServerManage;
import top.luckysmile.mqtt.client.view.MQTTChatView;

/**
 * �Ự��ͼ������ÿ����ͼһ�������������¼�����ͼ��������������ж�
 * @author minuy
 *
 */
public class ChatViewListen implements ActionListener {
	MqttServerManage sm;
	MQTTChatView mcv;

	public ChatViewListen(MQTTChatView mqttChatView) {
		this.mcv = mqttChatView;
		this.sm = MqttServerManage.GetMqttServerManage();//��ȡ����
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object clicket = arg0.getSource();
		if(clicket == mcv.getJbtChatSend()) {
			//����
			sm.SessionSend(mcv);
		}else {
			if(clicket == mcv.getJbtChatClear()) {
				//����Ự��Ϣ
				sm.CleanSession(mcv);
			}else {
				if(clicket == mcv.getJbtQuitSub()) {
					//ȡ������
					sm.CloseSession(mcv);
				}
			}
		}
	}

}
