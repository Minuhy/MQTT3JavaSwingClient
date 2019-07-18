package top.luckysmile.mqtt.client.server;

import java.text.SimpleDateFormat;
import java.util.Date;

import top.luckysmile.mqtt.client.view.MQTTChatView;

/**
 * �Ự��ͼ����������������Ự��ͼ�Ľ���
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
	 * ����Ự��������յ�������
	 */
	public void CleanSession(MQTTChatView mcv) {
		mcv.getJtaMassage().setText("");
	}

	/**
	 * ���һ����Ϣ���Ự����
	 * @param msage
	 */
	public void AddMassage(String msage,MQTTChatView mcv,String qos) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		String time = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		String history = mcv.getJtaMassage().getText();
		mcv.getJtaMassage().setText(history + time + " Qos:" + qos + "\n" + msage + "\n");
		mcv.updateUI();
	}

	/**
	 * ��ȡ���͵������Ϣ
	 * @param mcv �Ự��ͼ
	 * @return ���飬���⣬����������
	 */
	public String[] getSendInfo(MQTTChatView mcv) {
		String[] info = new String[3];
		//��Ϊ��ͼ���ƣ��ʲ��ټ�У��
		info[2] = mcv.getJtaChatSend().getText();
		info[1] = String.valueOf(mcv.getJcbQos().getSelectedIndex());
		info[0] = mcv.getJlbChatTopic().getText();
		return info;
	}

	/**
	 * ������
	 * @param mcv
	 * @param x
	 */
	public void addBitNumber(MQTTChatView mcv,int x) {
		if(x == -1) {
			mcv.setStatisticsN(0);
		}else {
			mcv.setStatisticsN(mcv.getStatisticsN()+x);
		}
		mcv.getJlbStatistics().setText("�յ����ֽ�����" + mcv.getStatisticsN());
	}
}
