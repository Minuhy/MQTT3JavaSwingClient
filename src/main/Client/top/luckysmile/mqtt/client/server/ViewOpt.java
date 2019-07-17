package top.luckysmile.mqtt.client.server;

import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import top.luckysmile.mqtt.client.data.MqttSetBeans;
import top.luckysmile.mqtt.client.view.MQTTChatView;
import top.luckysmile.mqtt.client.view.MainView;
/**
 * ��������ͼ�����в���
 * @author minuy
 *
 */
public class ViewOpt {
	MainView mv;

	public ViewOpt(MainView mv) {
		this.mv = mv;
	}
	/**
	 * ����ID�������ַ���
	 * @param id
	 */
	public void SetClictID(String id) {
		mv.getJtfId().setText(id);
	}

	/**
	 * ������ͼ��״̬������
	 * @param sta
	 */
	public void SetSta(String sta) {
		mv.getJlbSta().setText(sta);
	}
	/**
	 * ��ȡ����ͼ����������������
	 * @param mv ����ͼ
	 * @return һ��Mqtt�����࣬��Ϊ<code>null</code>ʱ����
	 */
	public MqttSetBeans getMainViewData() {
		MqttSetBeans set = new MqttSetBeans();

		String data = null;
		String tips = null;

		tips = "����";
		data = mv.getJtfIp().getText();
		if(TextUtils.isEmpty(data)) {
			JOptionPane.showMessageDialog(null, tips + "����Ϊ�գ�", "����", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if(!(data.equals("localhost")||Pattern.matches("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?", data)||Pattern.matches("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)", data))) {
			JOptionPane.showMessageDialog(null, tips + "��ʽ����ȷ��", "����", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setIp(data);

		tips = "�˿ں�";
		data = mv.getJtfPort().getText();
		if(TextUtils.isEmpty(data)) {
			JOptionPane.showMessageDialog(null, tips + "����Ϊ�գ�", "����", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if(!Pattern.matches("[1-9]\\d*", data)) {
			JOptionPane.showMessageDialog(null, tips + "��ʽ����ȷ��", "����", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if(Integer.valueOf(data)>65535) {
			JOptionPane.showMessageDialog(null, tips + "��Χ����ȷ����1-65535��", "����", JOptionPane.ERROR_MESSAGE);
		}
		set.setPort(data);

		tips = "ʶ���";
		data = mv.getJtfId().getText();
		if(TextUtils.isEmpty(data)) {
			JOptionPane.showMessageDialog(null, tips + "����Ϊ�գ�", "����", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if(data.length()>65535) {
			JOptionPane.showMessageDialog(null, tips + "���Ȳ��ܴ���65535��", "����", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setId(data);

		tips = "�˺�";
		data = mv.getJtfName().getText();
		if(data.length()>65535) {
			JOptionPane.showMessageDialog(null, tips + "���Ȳ��ܴ���65535��", "����", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setName(data);

		tips = "����";
		data = String.valueOf(mv.getJpfPasswd().getPassword());
		if(data.length()>65535) {
			JOptionPane.showMessageDialog(null, tips + "���Ȳ��ܴ���65535��", "����", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setPasswd(data);

		tips = "��������";
		data = mv.getJtfLWTTopic().getText();
		if(data.length()>65535) {
			JOptionPane.showMessageDialog(null, tips + "���Ȳ��ܴ���65535��", "����", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setLWTTopic(data);

		tips = "��������";
		data = mv.getJtfSubscribeTopic().getText();
		if(data.length()>65535) {
			JOptionPane.showMessageDialog(null, tips + "���Ȳ��ܴ���65535��", "����", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setSubTopic(data);

		tips = "��������";
		data = mv.getJtfContextTopic().getText();
		if(data.length()>65535) {
			JOptionPane.showMessageDialog(null, tips + "���Ȳ��ܴ���65535��", "����", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setPubTopic(data);

		//��������
		set.setLWTContext(mv.getJtfLWTContext().getText());

		//��Ϣ����
		set.setPubContext(mv.getJtaContext().getText());
		System.out.println(set.getPubContext());

		set.setLWTQos(String.valueOf(mv.getJcbLWTQos().getSelectedIndex()));
		set.setSubQos(String.valueOf(mv.getJcbTopicQos().getSelectedIndex()));
		set.setPubQos(String.valueOf(mv.getJcbSendQos().getSelectedIndex()));

		return set;
	}

	/**
	 * �������Ӱ�ť������
	 * @param str
	 */
	public void SetConnetButtonStr(String str) {
		mv.getJbtConnect().setText(str);
	}
	
	/**
	 * �������Ӱ�ť�Ƿ����
	 * @param ea
	 */
	public void SetConnetButtonEable(boolean ea) {
		mv.getJbtConnect().setEnabled(ea);
	}
	
	/**
	 * ��ȡ����
	 * @return ����
	 */
	public char[] getViewPasswd() {
		return mv.getJpfPasswd().getPassword();
	}

	/**
	 * ���õ�¼���Ŀɱ༭��
	 * @param en
	 */
	public void SetLoginEnable(boolean en) {
			mv.getJtfIp().setEditable(en);
			mv.getJtfPort().setEditable(en);
			mv.getJtfId().setEditable(en);
			mv.getJtfName().setEditable(en);
			mv.getJpfPasswd().setEditable(en);
			mv.getJtfLWTContext().setEditable(en);
			mv.getJtfLWTTopic().setEditable(en);
			mv.getJcbLWTQos().setEnabled(en);
			mv.getJbtRandId().setEnabled(en);
			
			if(en) {
				SetRSendEnable(false);
			}else {
				SetRSendEnable(true);
			}
	}
	/**
	 * ���ö��İ�ť�Ƿ�ɰ���
	 * @param b
	 */
	public void setSubBtnEanle(boolean b) {
		mv.getJbtSubscribe().setEnabled(b);
	}
	
	/**
	 * ���ô��ڸ�����
	 * @param sub
	 */
	public void setSubhead(String sub) {
		mv.setTitle("MQTT�ͻ��� - " + sub);
	}
	/**
	 * ����һ���Ự��ͼ
	 * @param topic 
	 * @return һ���Ự��ͼ
	 */
	public MQTTChatView CreateSession(String topic) {
		MQTTChatView mcv = new MQTTChatView();//�½�һ����ͼ
		mcv.getJlbChatTopic().setText(topic);//���ñ���
		
		topic = TextUtils.subStringCN(topic,8,8);//��ȡ����
		mv.getJtpChat().addTab(topic, mcv);//��ӵ���ͼ
		
		return mcv;
	}
	/**
	 * ���ݱ���ر�һ��ѡ�
	 * @param topic
	 */
	public void CloseSessionWindow(String topic) {
		int index = mv.getJtpChat().indexOfTab(TextUtils.subStringCN(topic, 8, 8));
		if(index == -1) {
			System.out.println("�����ڴ�ѡ���");
		}
		mv.getJtpChat().remove(index);
		
	}
	
	/**
	 * ���ö��ĺͷ��Ͱ�ť�Ŀ���״̬
	 * @param en
	 */
	public void SetRSendEnable(boolean en) {
		mv.getJbtSubscribe().setEnabled(en);//����
		mv.getJbtSend().setEnabled(en);
	}
	
	/**
	 * ���÷���״̬��һ���ڷ��͵�ʱ��򿪣��յ���ʱ��ر�
	 * @param en
	 */
	public void SetSendSta(boolean en) {
		if(en) {
			mv.getJlbSta().setText("...");
		}else {
			mv.getJlbSta().setText("");
		}
	}

}
