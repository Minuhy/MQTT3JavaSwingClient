/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *    Dave Locke - initial API and implementation and/or initial documentation
 */
package org.eclipse.paho.client.mqttv3;


/**
 * <p>
 * 使应用程序在发生与客户机相关的异步事件时得到通知。
 * 实现此接口的类可以在这两种类型的客户机上注册{@link IMqttClient#setCallback(MqttCallback)}
 * 和 {@link IMqttAsyncClient#setCallback(MqttCallback)}
 * </p>
 * <p>
 * Enables an application to be notified when asynchronous
 * events related to the client occur.
 * Classes implementing this interface
 * can be registered on both types of client: {@link IMqttClient#setCallback(MqttCallback)}
 * and {@link IMqttAsyncClient#setCallback(MqttCallback)}
 * </p>
 */
public interface MqttCallback {
	/**
	 * 当与服务器的连接丢失时，将调用此方法。
	 * @param 造成联系中断的原因。
	 * 
	 * This method is called when the connection to the server is lost.
	 * @param cause the reason behind the loss of connection.
	 */
	public void connectionLost(Throwable cause);

	/**
	 * <p>
	 * 当消息从服务器到达时，将调用此方法。
	 *
	 * <p>
	 * 此方法由MQTT客户机同步调用。在此方法干净地返回之前，不会将确认发送回服务器。</p>
	 * <p>
	 * 如果该方法的实现抛出一个<code>Exception</code>（异常），则客户机将被关闭。
	 * 当客户机下一次重新连接时，服务器将重新发送所有服务质量为 1或2的消息。</p>
	 * <p>
	 * 在运行此方法的实现时到达的任何附加消息都将在内存中生成，然后在网络上备份。</p>
	 * <p>
	 * 如果应用程序需要持久化数据，
	 * 那么它应该确保数据在从这个方法返回之前被持久化，
	 * 就像从这个方法返回之后，消息被认为已经被传递了，
	 * 并且是不可复制的。</p>
	 * <p>
	 * 可以发送一个新消息在这个回调的实现(例如,回应这个消息),
	 * 但不得断开客户端实现,
	 * 因为它不可能发送一个确认消息的处理,并会发生死锁。</p>
	 *
	 * @param topic 消息上主题的名称已发布到
	 * @param message 实际的消息。
	 * @throws Exception 如果发生终端错误，应该关闭客户机。
	 * </p>
	 * 
	 * <p>
	 * 
	 * This method is called when a message arrives from the server.
	 *
	 * <p>
	 * This method is invoked synchronously by the MQTT client. An
	 * acknowledgment is not sent back to the server until this
	 * method returns cleanly.</p>
	 * <p>
	 * If an implementation of this method throws an <code>Exception</code>, then the
	 * client will be shut down.  When the client is next re-connected, any QoS
	 * 1 or 2 messages will be redelivered by the server.</p>
	 * <p>
	 * Any additional messages which arrive while an
	 * implementation of this method is running, will build up in memory, and
	 * will then back up on the network.</p>
	 * <p>
	 * If an application needs to persist data, then it
	 * should ensure the data is persisted prior to returning from this method, as
	 * after returning from this method, the message is considered to have been
	 * delivered, and will not be reproducible.</p>
	 * <p>
	 * It is possible to send a new message within an implementation of this callback
	 * (for example, a response to this message), but the implementation must not
	 * disconnect the client, as it will be impossible to send an acknowledgment for
	 * the message being processed, and a deadlock will occur.</p>
	 *
	 * @param topic name of the topic on the message was published to
	 * @param message the actual message.
	 * @throws Exception if a terminal error has occurred, and the client should be
	 * shut down.
	 * </p>
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception;

	/**
	 * 当消息的传递已完成，且所有确认已收到时调用。
	 * 对于QoS 0消息，一旦消息被提交到网络进行交付，
	 * 就会调用它。对于QoS 1，在接收PUBACK时调用它，
	 * 对于QoS 2，在接收PUBCOMP时调用它。
	 * 令牌将与消息发布时返回的令牌相同。
	 *
	 * @param token 与消息关联的传递令牌。
	 * 
	 * Called when delivery for a message has been completed, and all
	 * acknowledgments have been received. For QoS 0 messages it is
	 * called once the message has been handed to the network for
	 * delivery. For QoS 1 it is called when PUBACK is received and
	 * for QoS 2 when PUBCOMP is received. The token will be the same
	 * token as that returned when the message was published.
	 *
	 * @param token the delivery token associated with the message.
	 */
	public void deliveryComplete(IMqttDeliveryToken token);

}
