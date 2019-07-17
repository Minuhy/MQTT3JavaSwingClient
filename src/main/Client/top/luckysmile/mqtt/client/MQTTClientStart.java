package top.luckysmile.mqtt.client;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;

import top.luckysmile.mqtt.client.view.MainView;

/**
 * ���������ڵĿ�ʼ����
 * @author minuy
 *
 */
public class MQTTClientStart {

	/**
	 * ��ʼ
	 * @param args
	 */
	public static void main(String[] args) {

		try{
			BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.translucencyAppleLike;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}

		new MainView();

	}

}