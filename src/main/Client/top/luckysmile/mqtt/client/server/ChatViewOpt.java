package top.luckysmile.mqtt.client.server;

import java.text.SimpleDateFormat;
import java.util.Date;

import top.luckysmile.mqtt.client.view.MQTTChatView;

/**
 * 会话视图操作，在这里操作会话视图的界面
 * @author minuy
 *
 */
public class ChatViewOpt {
	private static ChatViewOpt cvo = null;
	private ChatViewOpt() {
	}

	public static ChatViewOpt getChatViewOpt() {
		if(cvo!=null) {
			return cvo;
		}else {
			cvo = new ChatViewOpt();
			return cvo;
		}
	}

	/**
	 * 清理会话，清除接收到的数据
	 */
	public void CleanSession(MQTTChatView mcv) {
		mcv.getJtaMassage().setText("");
	}

	/**
	 * 添加一条消息到会话窗口
	 * @param msage
	 */
	public void AddMassage(String msage,MQTTChatView mcv,String qos) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time = df.format(new Date());// new Date()为获取当前系统时间
		String history = mcv.getJtaMassage().getText();
		mcv.getJtaMassage().setText(history + time + " Qos:" + qos + "\n" + msage + "\n");
		mcv.updateUI();
	}

	/**
	 * 获取发送的相关信息
	 * @param mcv 会话视图
	 * @return 数组，标题，质量，内容
	 */
	public String[] getSendInfo(MQTTChatView mcv) {
		String[] info = new String[3];
		//因为视图限制，故不再加校验
		info[2] = mcv.getJtaChatSend().getText();
		info[1] = String.valueOf(mcv.getJcbQos().getSelectedIndex());
		info[0] = mcv.getJlbChatTopic().getText();
		return info;
	}

	/**
	 * 计数器
	 * @param mcv
	 * @param x
	 */
	public void addBitNumber(MQTTChatView mcv,int x) {
		if(x == -1) {
			mcv.setStatisticsN(0);
		}else {
			mcv.setStatisticsN(mcv.getStatisticsN()+x);
		}
		mcv.getJlbStatistics().setText("收到的字节数：" + mcv.getStatisticsN());
	}
}
