# MQTT3客户端 - Java图形化界面
这是一个用Java写的有会话模式的MQTT客户端，用到了JavaSwing、SQLite、Paho、Beautyeye等技术。界面简洁清新，有MQTT的基本功能，有会话模式，可以在同一主题下会话，可以设置账号、密码、ID、遗嘱等登录初始信息。

## 起因
是想参加全国大学生电子设计大赛，队友抱怨用蓝牙调试每次都要连接太麻烦了，所以想用WIFI，于是想到了MQTT协议，由于MQTT客户端还没有我满意的（接触了两个，MQTT.fx、通信猫，MQTT.fx挺好的，但是不怎么懂英文，而且单片机由于内存限制，一般只定义一个主题，都没有会话模式，发主题太麻烦了），于是开始动手，花几天做了一个这个。

## MQTT相关
MQTT是用的paho的包，直接到官网下的，支持MQTT3.1.1，MQTT的关键部分（MqttClient类、MqttClientCallback类等）添加了中文注释，可以中英文对比，没有修改官方的代码。MQTT比较小就不裁剪了。这个有5M多（87%）都是SQLite的大小(>_<)。

## SQLite相关
SQLite的JDBC是3.27.2.1版本，官网打不开是在[Bitbucket](https://bitbucket.org/xerial/sqlite-jdbc/downloads/)下载的。用JavaBeans做的（校企合作只学了这个，听说还有更厉害的），数据库相关全在top.luckysmile.mqtt.client.data这个包里，采用静态加载方式加载驱动，初次学Java注释比较详细。

## Swing相关
Swing的控件命名都还算满足老师说的规范，像标签是用jlb开头，名字是用英文的，有意义的。用了JackJiang2011的[beautyeye](https://github.com/JackJiang2011/beautyeye)皮肤包，挺好看的。Swing的结构应该是比较清晰明了的，主界面一个Jpn，会话，登录，订阅，发布各一个Jpn，所有控件放在后4个Jpn上，会话选项卡单独一个类，一个主jpn，接收和发送各一个jpn在主jpn上。注释比较详细。

## 项目架构相关
初学Java，只是跟着老师的思路来，不懂什么架构，这里简单说下我的结构。分为视图、监听、服务和数据这四个部分，其中，服务有服务管理器。视图驱动，最终回到视图，监听调用服务管理器，由服务管理器统一管理其他服务和数据。服务管理器，和两个视图操作类应该是要单例化的，但时间不够就不改了。自己写的代码全在Client里。

## 界面截图
![未连接](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/1.jpg ''未连接'')
![已连接](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/2.jpg ''已连接'')
![会话](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/3.jpg ''主题会话'')
![断开](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/4.jpg ''断开连接'')
![退出](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/5.jpg ''退出'')


# MQTT3JavaSwingClient
This is a conversational MQTT client written in Java that USES JavaSwing, SQLite, Paho, Beautyeye, and other technologies.The interface is simple and fresh, with the basic functions of MQTT and the session mode, which allows you to have a conversation under the same theme, and you can set the initial login information such as account number, password, ID and will.

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
![no connet](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/1.jpg ''no connet'')
![conneted](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/2.jpg ''conneted'')
![Session model](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/3.jpg ''Topic Session model'')
![disconnect](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/4.jpg ''disconnect'')
![exit](https://github.com/Minuy/MQTT3JavaSwingClient/blob/master/screenshot/5.jpg ''exit'')
