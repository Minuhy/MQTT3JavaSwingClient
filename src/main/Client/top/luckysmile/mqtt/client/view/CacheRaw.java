/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * RawCache.java at 2015-2-1 20:25:40, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package top.luckysmile.mqtt.client.view;


import java.util.HashMap;

/**
 * ���ش�����Դ�ļ��������ĳ��࣬����ɼ̳б�����ʵ�ִ�����Դ�ļ��л���.
 * Minuy Ϊ�˱��ֶ�����������
 *
 * @param <T> the generic type
 * @author Jack Jiang(jb2011@163.com), 2010-09-11
 * @version 1.0
 */
public abstract class CacheRaw<T>
{
	
	/** ���ش�����Դ�������ģ�key=path,value=image����. */
	private HashMap<String,T> rawCache = new HashMap<String,T>();
	
	/**
	 * ���ش�����Դ������������Ѵ��ڣ������ȡ֮������Ӵ��̶�ȡ������֮����.
	 *
	 * @param relativePath ���ش�����Դ�����baseClass������·���������������/res/imgs/pic/�£�baseClass��
	 * /res�£��򱾵ش�����Դ�˴������������·��Ӧ����/imgs/pic/some.png
	 * @param baseClass ��׼�ָ࣬���������ȡ���ش�����Դʱ���Դ���Ϊ��׼ȡ���ش�����Դ���������Ŀ¼
	 * @return T
	 */
	public T getRaw(String relativePath,@SuppressWarnings("rawtypes") Class baseClass)
	{
		T ic=null;
		
		String key = relativePath+baseClass.getCanonicalName();
		if(rawCache.containsKey(key))
			ic = rawCache.get(key);
		else
		{
			try
			{
				ic = getResource(relativePath, baseClass);
				rawCache.put(key, ic);
			}
			catch (Exception e)
			{
				System.out.println("ȡ���ش�����Դ�ļ�����,path="+key+","+e.getMessage());
				e.printStackTrace();
			}
		}
		return ic;
	}
	
	/**
	 * ������Դ��ȡ����ʵ��.
	 *
	 * @param relativePath ���·��
	 * @param baseClass ��׼��
	 * @return the resource
	 */
	protected abstract T getResource(String relativePath,@SuppressWarnings("rawtypes") Class baseClass);
}
