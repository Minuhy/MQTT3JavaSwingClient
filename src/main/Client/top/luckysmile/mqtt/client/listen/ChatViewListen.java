package top.luckysmile.mqtt.client.listen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import top.luckysmile.mqtt.client.server.MqttServerManage;
import top.luckysmile.mqtt.client.view.MQTTChatView;

/**
 * 会话视图监听，每个视图一个监听，调用事件用视图对象和主题文字判断
 * @author minuy
 *
 */
public class ChatViewListen implements ActionListener {
	MqttServerManage sm;
	MQTTChatView mcv;

	public ChatViewListen(MQTTChatView mqttChatView) {
		this.mcv = mqttChatView;
		this.sm = MqttServerManage.GetMqttServerManage();//获取服务
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object clicket = arg0.getSource();
		if(clicket == mcv.getJbtChatSend()) {
			//发送
			sm.SessionSend(mcv);
		}else {
			if(clicket == mcv.getJbtChatClear()) {
				//清除会话消息
				sm.CleanSession(mcv);
			}else {
				if(clicket == mcv.getJbtQuitSub()) {
					//取消订阅
					sm.CloseSession(mcv);
				}
			}
		}
	}

}
