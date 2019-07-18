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
 * 客户端回调，收到消息，连接状态在这处理
 * @author minuy
 *
 */
public class MqttClientCallback implements MqttCallback {

	ViewOpt vo;
	MqttServerManage msm;

	/**
	 * 回调用
	 * @param vo2 主视图操作
	 * @param cvo 管理器
	 */
	public MqttClientCallback(ViewOpt vo2, MqttServerManage cvo) {
		this.vo = vo2;
		this.msm = cvo;
	}

	@Override
	public void connectionLost(Throwable cause) {
		// 当与服务器的连接丢失时，将调用此方法。
		JOptionPane.showMessageDialog(null, cause.toString(), "连接丢失！", 2);
		vo.SetSta("连接丢失");
		vo.setSubhead("连接丢失");//副标题
		vo.SetConnetButtonStr("连接");
		vo.SetLoginEnable(true);
		//清空订阅
		msm.SubEmpty();
		//未连接
		msm.setConnet(false);
	}

	@Override
	public void messageArrived(String title, MqttMessage msg) throws Exception {
		// 当消息从服务器到达时，将调用此方法。
		msm.MqttReceveMassage(title,msg.toString(),String.valueOf(msg.getQos()));
		
		//下面是保存信息
		System.out.println(title+":"+msg.toString());
		MqttMassageBeans massage = new MqttMassageBeans();
		massage.setContext(msg.toString());
		massage.setTopic(title);
		massage.setQos(String.valueOf(msg.getQos()));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		massage.setTime(df.format(new Date()));
		massage.setOther("Session:" + title);
		
		//避免没有数据库权限卡死
		final MqttMassageBeans finemassage = massage;
		new Thread() {
			public void run() {
				//保存下设置
				new MqttDAO().SaveSession(finemassage);
			};
		}.start();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// 当消息的传递已完成，且所有确认已收到时调用。对于QoS 0消息，一旦消息被提交到网络进行交付，就会调用它。对于QoS 1，在接收PUBACK时调用它，对于QoS 2，在接收PUBCOMP时调用它。令牌将与消息发布时返回的令牌相同。
		System.out.println(token.toString());
		vo.SetSendSta(false);//完成
	}

}
