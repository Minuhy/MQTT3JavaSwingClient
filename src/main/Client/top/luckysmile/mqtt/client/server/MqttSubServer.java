package top.luckysmile.mqtt.client.server;

import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import top.luckysmile.mqtt.client.data.MqttSetBeans;
import top.luckysmile.mqtt.client.view.MQTTChatView;
/**
 * 客户端订阅函数，用于订阅的相关逻辑
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
	 * 订阅并创建会话，需要调用.start函数运行
	 * @param set MQTTSetBean取得的数据，其中必须包含主题信息，否则调用无效
	 * @param vo 视图操作类，需要实现会话窗口的创建
	 * @param mC 已连接的MQTT客户端，未连接出错
	 * @param msm 回调用，完成订阅后添加到管理器的列表里
	 * @param en 是否已存在会话，存在则启用，未存在则创建
	 * @param mcv 如果上面存在，需要用到，会启用视图
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
	 * 订阅消息
	 */
	@Override
	public void run() {

		vo.setSubBtnEanle(false);//按钮不可用
		
		//确定此客户端当前是否连接到服务器。
		if(!mc.isConnected()) {
			return;
		}
		//是否得到了设置
		if(set == null) {
			return;
		}
		boolean isOk = true;
		String topic = set.getSubTopic();
		int qos = Integer.valueOf(set.getSubQos());

		if(TextUtils.isEmpty(topic)) {
			System.out.println("主题异常！");
			return;
		}
		if(qos>2||qos<0) {
			System.out.println("SubQos异常");
			qos = 0;
		}

		vo.setSubBtnEanle(false);
		//订阅
		try {
			mc.subscribe(topic,qos);
		} catch (NumberFormatException e) {
			System.out.println(e.toString());
			isOk = false;
			JOptionPane.showMessageDialog(null, e.toString(), "错误！", JOptionPane.ERROR_MESSAGE);
		} catch (MqttException e) {
			System.out.println(e.toString());
			isOk = false;
			JOptionPane.showMessageDialog(null, e.toString(), "错误！", JOptionPane.ERROR_MESSAGE);
		}

		if(isOk&&(!isCreat)) {
			//订阅成功
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
		
		vo.setSubBtnEanle(true);//订阅按钮可用
	}


}
