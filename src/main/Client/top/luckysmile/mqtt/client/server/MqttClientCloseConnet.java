package top.luckysmile.mqtt.client.server;

import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
/**
 * 关闭连接的相关操作
 * @author minuy
 *
 */
public class MqttClientCloseConnet {
	public MqttClientCloseConnet(MqttClient mC, final ViewOpt vo, MqttServerManage mqttServerManage) {
		boolean isClose = true;
		vo.SetConnetButtonEable(false);//按钮不可用
		try {
			mC.disconnect();
			//关闭客户机释放与客户机关联的所有资源。客户端关闭后不能重用。例如，连接尝试将失败。
			mC.close();
		} catch (MqttException e) {
			System.out.println(e.toString());
			isClose = false;
			JOptionPane.showMessageDialog(null, "未连接 "+e.toString(), "错误！", JOptionPane.ERROR_MESSAGE);
		}
		if(isClose) {
			vo.SetLoginEnable(true);//设置界面可编辑
			vo.SetConnetButtonEable(true);//设置按钮可用
			vo.SetConnetButtonStr("连接");//设置字符
			vo.SetConnetButtonEable(true);
			
			mqttServerManage.setConnet(false);//设置状态断开
			
			vo.SetSta("已断开");
			
			vo.setSubhead("已断开");//设置副标题
		}
	}

}
