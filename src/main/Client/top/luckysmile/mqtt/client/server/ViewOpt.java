package top.luckysmile.mqtt.client.server;

import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import top.luckysmile.mqtt.client.data.MqttSetBeans;
import top.luckysmile.mqtt.client.view.MQTTChatView;
import top.luckysmile.mqtt.client.view.MainView;
/**
 * 关于主视图的所有操作
 * @author minuy
 *
 */
public class ViewOpt {
	MainView mv;

	public ViewOpt(MainView mv) {
		this.mv = mv;
	}
	/**
	 * 设置ID输入框的字符串
	 * @param id
	 */
	public void SetClictID(String id) {
		mv.getJtfId().setText(id);
	}

	/**
	 * 设置视图中状态的文字
	 * @param sta
	 */
	public void SetSta(String sta) {
		mv.getJlbSta().setText(sta);
	}
	/**
	 * 获取主视图左侧的所有输入数据
	 * @param mv 主视图
	 * @return 一个Mqtt设置类，当为<code>null</code>时出错！
	 */
	public MqttSetBeans getMainViewData() {
		MqttSetBeans set = new MqttSetBeans();

		String data = null;
		String tips = null;

		tips = "域名";
		data = mv.getJtfIp().getText();
		if(TextUtils.isEmpty(data)) {
			JOptionPane.showMessageDialog(null, tips + "不能为空！", "错误！", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if(!(data.equals("localhost")||Pattern.matches("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?", data)||Pattern.matches("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)", data))) {
			JOptionPane.showMessageDialog(null, tips + "格式不正确！", "错误！", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setIp(data);

		tips = "端口号";
		data = mv.getJtfPort().getText();
		if(TextUtils.isEmpty(data)) {
			JOptionPane.showMessageDialog(null, tips + "不能为空！", "错误！", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if(!Pattern.matches("[1-9]\\d*", data)) {
			JOptionPane.showMessageDialog(null, tips + "格式不正确！", "错误！", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if(Integer.valueOf(data)>65535) {
			JOptionPane.showMessageDialog(null, tips + "范围不正确！（1-65535）", "错误！", JOptionPane.ERROR_MESSAGE);
		}
		set.setPort(data);

		tips = "识标符";
		data = mv.getJtfId().getText();
		if(TextUtils.isEmpty(data)) {
			JOptionPane.showMessageDialog(null, tips + "不能为空！", "错误！", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if(data.length()>65535) {
			JOptionPane.showMessageDialog(null, tips + "长度不能大于65535！", "错误！", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setId(data);

		tips = "账号";
		data = mv.getJtfName().getText();
		if(data.length()>65535) {
			JOptionPane.showMessageDialog(null, tips + "长度不能大于65535！", "错误！", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setName(data);

		tips = "密码";
		data = String.valueOf(mv.getJpfPasswd().getPassword());
		if(data.length()>65535) {
			JOptionPane.showMessageDialog(null, tips + "长度不能大于65535！", "错误！", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setPasswd(data);

		tips = "遗嘱主题";
		data = mv.getJtfLWTTopic().getText();
		if(data.length()>65535) {
			JOptionPane.showMessageDialog(null, tips + "长度不能大于65535！", "错误！", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setLWTTopic(data);

		tips = "订阅主题";
		data = mv.getJtfSubscribeTopic().getText();
		if(data.length()>65535) {
			JOptionPane.showMessageDialog(null, tips + "长度不能大于65535！", "错误！", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setSubTopic(data);

		tips = "发布主题";
		data = mv.getJtfContextTopic().getText();
		if(data.length()>65535) {
			JOptionPane.showMessageDialog(null, tips + "长度不能大于65535！", "错误！", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		set.setPubTopic(data);

		//遗嘱内容
		set.setLWTContext(mv.getJtfLWTContext().getText());

		//消息内容
		set.setPubContext(mv.getJtaContext().getText());
		System.out.println(set.getPubContext());

		set.setLWTQos(String.valueOf(mv.getJcbLWTQos().getSelectedIndex()));
		set.setSubQos(String.valueOf(mv.getJcbTopicQos().getSelectedIndex()));
		set.setPubQos(String.valueOf(mv.getJcbSendQos().getSelectedIndex()));

		return set;
	}

	/**
	 * 设置连接按钮的文字
	 * @param str
	 */
	public void SetConnetButtonStr(String str) {
		mv.getJbtConnect().setText(str);
	}
	
	/**
	 * 设置连接按钮是否可用
	 * @param ea
	 */
	public void SetConnetButtonEable(boolean ea) {
		mv.getJbtConnect().setEnabled(ea);
	}
	
	/**
	 * 获取密码
	 * @return 密码
	 */
	public char[] getViewPasswd() {
		return mv.getJpfPasswd().getPassword();
	}

	/**
	 * 设置登录面板的可编辑性
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
	 * 设置订阅按钮是否可按下
	 * @param b
	 */
	public void setSubBtnEanle(boolean b) {
		mv.getJbtSubscribe().setEnabled(b);
	}
	
	/**
	 * 设置窗口副标题
	 * @param sub
	 */
	public void setSubhead(String sub) {
		mv.setTitle("MQTT客户端 - " + sub);
	}
	/**
	 * 创建一个会话视图
	 * @param topic 
	 * @return 一个会话视图
	 */
	public MQTTChatView CreateSession(String topic) {
		MQTTChatView mcv = new MQTTChatView();//新建一个视图
		mcv.getJlbChatTopic().setText(topic);//设置标题
		
		topic = TextUtils.subStringCN(topic,8,8);//截取长度
		mv.getJtpChat().addTab(topic, mcv);//添加到视图
		
		return mcv;
	}
	/**
	 * 根据标题关闭一个选项卡
	 * @param topic
	 */
	public void CloseSessionWindow(String topic) {
		int index = mv.getJtpChat().indexOfTab(TextUtils.subStringCN(topic, 8, 8));
		if(index == -1) {
			System.out.println("不存在此选项卡！");
		}
		mv.getJtpChat().remove(index);
		
	}
	
	/**
	 * 设置订阅和发送按钮的可用状态
	 * @param en
	 */
	public void SetRSendEnable(boolean en) {
		mv.getJbtSubscribe().setEnabled(en);//订阅
		mv.getJbtSend().setEnabled(en);
	}
	
	/**
	 * 设置发送状态，一般在发送的时候打开，收到的时候关闭
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
