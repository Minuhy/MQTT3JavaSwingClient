package top.luckysmile.mqtt.client.server;

import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import top.luckysmile.mqtt.client.view.MQTTChatView;
/**
 * 取消订阅，会话列表里的按钮最终触发的事件
 * @author minuy
 *
 */
public class closeTopic {
	/**
	 * 取消订阅并关闭会话视图
	 * @param vo 视图操作类，用于关闭会话视图
	 * @param MC Mqtt客户端对象，取消订阅用
	 * @param chatSession 管理器的视图列表，用于查找关闭是否有效
	 * @param mcv 发起取消订阅请求的视图，用于搜索
	 * @param mqttServerManage 管理器，回调用
	 */
	public closeTopic(ViewOpt vo,MqttClient MC,List<MQTTChatView> chatSession,MQTTChatView mcv, MqttServerManage mqttServerManage) {
		//找主题
		for (int i = 0; i < chatSession.size(); i++) {
			//如果会话主题和要关闭的主题相同
			if(mcv.getJlbChatTopic().getText().equals(chatSession.get(i).getJlbChatTopic().getText())) {
				try {
					//取消订阅主题
					MC.unsubscribe(mcv.getJlbChatTopic().getText());
					//把主题从主题列表和视图列表中删除
					mqttServerManage.SubDrop(mcv.getJlbChatTopic().getText());
					mqttServerManage.removeSession(mcv);
					//关闭视图
					vo.CloseSessionWindow(mcv.getJlbChatTopic().getText());
				} catch (MqttException e) {
					System.out.println(e.toString());
					JOptionPane.showMessageDialog(null, "没有订阅这个主题！","错误",2);
				}
			}
		}
	}
}
