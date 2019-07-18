# MQTT3�ͻ��� - Javaͼ�λ�����
����һ����Javaд���лỰģʽ��MQTT�ͻ��ˣ��õ���JavaSwing��SQLite��Paho��Beautyeye�ȼ��������������£���MQTT�Ļ������ܣ��лỰģʽ��������ͬһ�����»Ự�����������˺š����롢ID�������ȵ�¼��ʼ��Ϣ��dist�µ�Jar������ֱ���á�

## ����
����μ�ȫ����ѧ��������ƴ��������ѱ�Թ����������ÿ�ζ�Ҫ����̫�鷳�ˣ���������WIFI�������뵽��MQTTЭ�飬����MQTT�ͻ��˻�û��������ģ��Ӵ���������MQTT.fx��ͨ��è��MQTT.fxͦ�õģ����ǲ���ô��Ӣ�ģ����ҵ�Ƭ�������ڴ����ƣ�һ��ֻ����һ�����⣬��û�лỰģʽ��������̫�鷳�ˣ������ǿ�ʼ���֣�����������һ�������

## MQTT���
MQTT���õ�paho�İ���ֱ�ӵ������µģ�֧��MQTT3.1.1��MQTT�Ĺؼ����֣�MqttClient�ࡢMqttClientCallback��ȣ����������ע�ͣ�������Ӣ�ĶԱȣ�û���޸Ĺٷ��Ĵ��롣MQTT�Ƚ�С�Ͳ��ü��ˡ������5M�ࣨ87%������SQLite�Ĵ�С(>_<)��

## SQLite���
SQLite��JDBC��3.27.2.1�汾�������򲻿�����[Bitbucket](https://bitbucket.org/xerial/sqlite-jdbc/downloads/)���صġ���JavaBeans���ģ�У�����ֻѧ���������˵���и������ģ������ݿ����ȫ��top.luckysmile.mqtt.client.data���������þ�̬���ط�ʽ��������������ѧJavaע�ͱȽ���ϸ��

## Swing���
Swing�Ŀؼ�����������������ʦ˵�Ĺ淶�����ǩ����jlb��ͷ����������Ӣ�ĵģ�������ġ�����JackJiang2011��[beautyeye](https://github.com/JackJiang2011/beautyeye)Ƥ������ͦ�ÿ��ġ�Swing�ĽṹӦ���ǱȽ��������˵ģ�������һ��Jpn���Ự����¼�����ģ�������һ��Jpn�����пؼ����ں�4��Jpn�ϣ��Ựѡ�����һ���࣬һ����jpn�����պͷ��͸�һ��jpn����jpn�ϡ�ע�ͱȽ���ϸ��������beautyeye�İ�ͼƬ�����Jar�ļ�����

## ��Ŀ�ܹ����
��ѧJava��ֻ�Ǹ�����ʦ��˼·��������ʲô�ܹ��������˵���ҵĽṹ����Ϊ��ͼ��������������������ĸ����֣����У������з������������ͼ���������ջص���ͼ���������÷�����������ɷ��������ͳһ����������������ݡ��������������������ͼ������Ӧ����Ҫ�������ģ���ʱ�䲻���Ͳ����ˡ��Լ�д�Ĵ���ȫ��Client�

## �����ͼ
### δ����
![](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/1.jpg)
### ������
![](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/2.jpg)
### ����Ự
![](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/3.jpg)
### �Ͽ�����
![](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/4.jpg)
### �˳�
![](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/5.jpg)



# MQTT3JavaSwingClient
This is a conversational MQTT client written in Java that USES JavaSwing, SQLite, Paho, Beautyeye, and other technologies.The interface is simple and fresh, with the basic functions of MQTT and the session mode, which allows you to have a conversation under the same theme, and you can set the initial login information such as account number, password, ID and will.You can use this Jar with dist.

## the cause
Is to want to take part in the national undergraduate electronic design contest, teammate complain with bluetooth debugging to connect every time too much trouble, so want to use WIFI, so the thought of the MQTT protocol, due to the MQTT client haven't my satisfaction (contact with the two, MQTT. Cat fx, communication, MQTT. Fx quite good, but don't understand English, and single chip microcomputer because memory limit, generally define only one theme, conversational mode, theme is too much trouble), so start, take a few days to do this.

## MQTT related
MQTT is a package of paho, directly to the official website, and supports MQTT3.1.1. The key parts of MQTT (MqttClient class, MqttClientCallback class, etc.) are annotated in Chinese and can be compared between Chinese and English without modifying the official code.MQTT is small so it doesn't crop.This is more than 5M (87%), which is the size of SQLite (>_<).

## SQLite related
JDBC is 3.27.2.1 version of SQLite, website can't open is in [Bitbucket] (https://bitbucket.org/xerial/sqlite-jdbc/downloads/) to download.Made of JavaBeans (university-enterprise cooperation just learned this, I heard that there are more powerful), database related on top. All luckysmile.. The MQTT client. The data in the packet, driven by loading ways of the static load, initial learning Java annotations are detailed.

## is related to the Swing
Swing controls are named to meet the teacher's specifications, like the tag is JLB, the name is in English, meaningful.Using the JackJiang2011 [beautyeye] (https://github.com/JackJiang2011/beautyeye) skin bag, look good.Swing's structure should be fairly straightforward, with one Jpn for the main interface, one Jpn for session, login, subscribe, publish, and all the controls on the last four JPNS, and a single class for the session TAB, one for the main Jpn, one for receive and send and one for Jpn on the main Jpn.The comments are more detailed.
Project architecture is relevant
I just followed the teacher's train of thought when I started to learn Java. I don't understand any structure. Here is my structure.It is divided into four parts: view, monitor, service and data. Among them, service has service manager.View driven, and eventually back to the view, listening to the invoking service manager, which uniformly manages other services and data.The service manager, and the two view action classes should be singletons, but there isn't enough time to change them.All the code I wrote is in the Client.

## Screen shots
### no connet
![](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/1.jpg)
### conneted
![](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/2.jpg)
### Topic Session model
![](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/3.jpg)
### disconnect
![](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/4.jpg)
### exit
![](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/5.jpg)
