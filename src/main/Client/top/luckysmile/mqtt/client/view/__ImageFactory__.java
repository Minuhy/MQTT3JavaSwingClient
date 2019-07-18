/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * __IconFactory__.java at 2015-2-1 20:25:38, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package top.luckysmile.mqtt.client.view;

import javax.swing.ImageIcon;

/**
* ��ͨͼƬ������.
* Minuy Ϊ�˱��ֶ�����������
* 
* @author Jack Jiang
* @version 1.0
*/
public class __ImageFactory__ extends CacheRaw<ImageIcon>
{
	
	/** ���·������Ĭ��������ڱ�����������·����. */
	public final static String IMGS_ROOT="res";

	/** The instance. */
	private static __ImageFactory__ instance = null;

	/**
	 * Gets the single instance of __IconFactory__.
	 *
	 * @return single instance of __IconFactory__
	 */
	public static __ImageFactory__ getInstance()
	{
		if(instance==null)
			instance = new __ImageFactory__();
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see org.jb2011.lnf.beautyeye.utils.RawCache#getResource(java.lang.String, java.lang.Class)
	 */
	@Override
	protected ImageIcon getResource(String relativePath, @SuppressWarnings("rawtypes") Class baseClass)
	{
		return new ImageIcon(baseClass.getResource(relativePath));
	}
	
	/**
	 * Gets the image.
	 *
	 * @param relativePath the relative path
	 * @return the image
	 */
	public ImageIcon getImage(String relativePath)
	{
		return  getRaw(relativePath,this.getClass());
	}

	/**
	 * Ӧ�ó���ͼ��
	 *
	 * @return the tree default open icon_16_16
	 */
	public ImageIcon getApplicationIcon()
	{
		return getImage(IMGS_ROOT+"/mqttorg-glow.png");
	}
	
	/**
	 * Ĭ�ϻỰ����
	 *
	 * @return the tree default closed icon_16_16
	 */
	public ImageIcon getSessionBackground()
	{
		return getImage(IMGS_ROOT+"/photo.jpg");
	}
}
