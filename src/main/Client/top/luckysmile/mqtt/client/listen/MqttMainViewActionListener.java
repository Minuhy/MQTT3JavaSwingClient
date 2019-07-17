package top.luckysmile.mqtt.client.listen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import top.luckysmile.mqtt.client.server.MqttServerManage;
import top.luckysmile.mqtt.client.view.MainView;

/**
 * 主视图监听
 * @author minuy
 *
 */
public class MqttMainViewActionListener implements ActionListener {
	MqttServerManage sm;
	MainView mv;
	public MqttMainViewActionListener(MainView mainView) {
		this.mv = mainView;
		this.sm = MqttServerManage.GetMqttServerManage();//获取服务
		sm.setViewOpt(mv);//设置操作视图
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object clicked = e.getSource();
		if(clicked == mv.getJbtConnect()) {
			//连接
			System.out.println("连接！");
			sm.MqttConnet();
		}else {
			if(clicked == mv.getJbtSend()) {
				//发送
				System.out.println("发送！");
				sm.MqttPubCustomTopic();
			}else {
				if(clicked == mv.getJbtRandId()) {
					//随机ID
					System.out.println("生成随机ID！");
					sm.RandId();
				}else {
					if(clicked == mv.getJbtSubscribe()) {
						System.out.println("订阅！");
						sm.MqttSub();
					}else {
						if(clicked == mv.getJbtMoreSet()) {
							System.out.println("更多！");
							JOptionPane.showMessageDialog(null, "准备电子设计大赛-2019-小鱼啊小鱼", "期待", 1);
						}
					}
				}
			}
		}
	}

}
