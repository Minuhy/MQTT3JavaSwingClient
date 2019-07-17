package top.luckysmile.mqtt.client.listen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import top.luckysmile.mqtt.client.server.MqttServerManage;
import top.luckysmile.mqtt.client.view.MainView;

/**
 * ����ͼ����
 * @author minuy
 *
 */
public class MqttMainViewActionListener implements ActionListener {
	MqttServerManage sm;
	MainView mv;
	public MqttMainViewActionListener(MainView mainView) {
		this.mv = mainView;
		this.sm = MqttServerManage.GetMqttServerManage();//��ȡ����
		sm.setViewOpt(mv);//���ò�����ͼ
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object clicked = e.getSource();
		if(clicked == mv.getJbtConnect()) {
			//����
			System.out.println("���ӣ�");
			sm.MqttConnet();
		}else {
			if(clicked == mv.getJbtSend()) {
				//����
				System.out.println("���ͣ�");
				sm.MqttPubCustomTopic();
			}else {
				if(clicked == mv.getJbtRandId()) {
					//���ID
					System.out.println("�������ID��");
					sm.RandId();
				}else {
					if(clicked == mv.getJbtSubscribe()) {
						System.out.println("���ģ�");
						sm.MqttSub();
					}else {
						if(clicked == mv.getJbtMoreSet()) {
							System.out.println("���࣡");
							JOptionPane.showMessageDialog(null, "׼��������ƴ���-2019-С�㰡С��", "�ڴ�", 1);
						}
					}
				}
			}
		}
	}

}
