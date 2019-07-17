package top.luckysmile.mqtt.client.server;

import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import top.luckysmile.mqtt.client.data.MqttSetBeans;

/**
 * 客户端连接函数，连接到服务器
 * @author minuy
 *
 */
public class MqttClientConnet extends Thread{
	MqttClient mqttClient;//回调用
	MqttSetBeans set;
	ViewOpt vo;
	boolean isConnet;
	MqttServerManage msm;

	/**
	 * 新建一个MQTT连接
	 * @param setBeans 一个带设置的类
	 */
	public MqttClientConnet(MqttSetBeans setBeans,ViewOpt vo,MqttServerManage msm) {
		this.set = setBeans;
		this.vo = vo;
		this.msm = msm;
	}

	@Override
	public void run() {
		//数据非空判断
		if(set == null) {
			return;
		}

		vo.SetSta("正在连接");//显示状态正在连接
		vo.SetLoginEnable(false);//设置界面不可编辑
		vo.SetConnetButtonEable(false);//设置按钮不可用

		isConnet = true;

		String port = set.getId();
		String url = "tcp://" + set.getIp() + ":" + set.getPort();
		MemoryPersistence persistence = new MemoryPersistence();
		//设置相关信息
		MqttConnectOptions connOpts = new MqttConnectOptions(); 
		connOpts.setCleanSession(true);
		//遗嘱主题不为空
		if(!TextUtils.isEmpty(set.getLWTTopic())) {
			if(TextUtils.isEmpty(set.getLWTContext())) {
				set.setLWTContext("");
			}
			connOpts.setWill(set.getLWTTopic(), set.getLWTContext().getBytes(), Integer.valueOf(set.getLWTQos()), true);
		}
		//账号密码
		if(!TextUtils.isEmpty(set.getName())) {
			connOpts.setUserName(set.getName());
			connOpts.setPassword(vo.getViewPasswd());
		}

		//新建客户端
		try {
			mqttClient= new MqttClient(url, port, persistence);
		} catch (MqttException e) {
			System.out.println(e.toString());
		}
		System.out.println("连接到服务器: "+"tcp://" + set.getIp() + ":" + set.getPort());
		try {
			mqttClient.connect(connOpts);
		} catch (MqttException e) {
			//连接失败
			isConnet = false;
			JOptionPane.showMessageDialog(null, e.toString(), "错误！", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		if(isConnet) {
			//连接成功
			vo.SetSta("已连接");//显示状态未连接
			vo.SetConnetButtonEable(true);//设置按钮可用
			vo.SetConnetButtonStr("断开");
			msm.setMC(mqttClient);
			msm.setConnet(true);
			
			vo.setSubhead("已连接:"+url);
		}else {
			//连接失败
			vo.SetSta("未连接");//显示状态未连接
			vo.SetLoginEnable(true);//设置界面可编辑
			vo.SetConnetButtonEable(true);//设置按钮可用
			msm.setConnet(false);
		}
	}
}
