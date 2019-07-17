/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corp.
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
 *    Ian Craggs - MQTT 3.1.1 support
 *    James Sutton - Automatic Reconnect & Offline Buffering
 */
package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.MessageCatalog;

/**
 * Thrown if an error occurs communicating with the server.
 */
public class MqttException extends Exception {
	private static final long serialVersionUID = 300L;
	
	/** 
	 * 客户端遇到异常。使用{@link #getCause()}方法找出根本原因。
	 * Client encountered an exception.  Use the {@link #getCause()}
	 * method to get the underlying reason.
	 */
	public static final short REASON_CODE_CLIENT_EXCEPTION              = 0x00;

	// CONNACK return codes
	/**服务器不支持请求的协议版本。 The protocol version requested is not supported by the server. */
	public static final short REASON_CODE_INVALID_PROTOCOL_VERSION		= 0x01;
	/**服务器拒绝了提供的客户机ID The server has rejected the supplied client ID */
	public static final short REASON_CODE_INVALID_CLIENT_ID      		= 0x02;
	/**服务器无法处理请求。 The broker was not available to handle the request. */
	public static final short REASON_CODE_BROKER_UNAVAILABLE             = 0x03;
	/**由于用户名或密码错误，与服务器的身份验证失败。 Authentication with the server has failed, due to a bad user name or password. */
	public static final short REASON_CODE_FAILED_AUTHENTICATION			= 0x04; 
	/**未被授权执行请求的操作  Not authorized to perform the requested operation */
	public static final short REASON_CODE_NOT_AUTHORIZED				= 0x05;

	/**发生了意外错误。 An unexpected error has occurred. */
	public static final short REASON_CODE_UNEXPECTED_ERROR				= 0x06;
	
	/**订阅错误-从服务器返回。 Error from subscribe - returned from the server. */
	public static final short REASON_CODE_SUBSCRIBE_FAILED				= 0x80;
	
	/** 
	 * 客户机在等待服务器响应时超时。
	 * 服务器不再响应保持活动的消息。
	 * Client timed out while waiting for a response from the server.
	 * The server is no longer responding to keep-alive messages.
	 */
	public static final short REASON_CODE_CLIENT_TIMEOUT                = 32000;

	/**
	 * 内部错误，原因是没有可用的新消息id。
	 * Internal error, caused by no new message IDs being available.
	 */
	public static final short REASON_CODE_NO_MESSAGE_IDS_AVAILABLE      = 32001;

	/** 
	 * 客户机在等待向服务器写入消息时超时。
	 * Client timed out while waiting to write messages to the server.
	 */
	public static final short REASON_CODE_WRITE_TIMEOUT                 = 32002;
	
	/**
	 * 客户端已经连接。
	 * The client is already connected.
	 */
	public static final short REASON_CODE_CLIENT_CONNECTED              = 32100;

	/**
	 * 客户端已经断开连接。
	 * The client is already disconnected.
	 */
	public static final short REASON_CODE_CLIENT_ALREADY_DISCONNECTED   = 32101;
	/** 
	 * 客户端目前正在断开连接，无法接受任何新工作。
	 * 这可能发生在等待令牌，然后断开客户机连接时。
	 * 如果消息传递没有在静默超时期间完成，则会通知正在等待的令牌，但会出现异常。
	 * The client is currently disconnecting and cannot accept any new work.
	 * This can occur when waiting on a token, and then disconnecting the client.  
	 * If the message delivery does not complete within the quiesce timeout 
	 * period, then the waiting token will be notified with an exception.
	 */
	public static final short REASON_CODE_CLIENT_DISCONNECTING          = 32102;
	
	/**无法连接到服务器 Unable to connect to server */
	public static final short REASON_CODE_SERVER_CONNECT_ERROR          = 32103;

	/** 
	 * 客户机没有连接到服务器。必须先调用{@link MqttClient#connect()}
	 * 或者{@link MqttClient#connect(MqttConnectOptions)}方法。
	 * 也有可能连接丢失了—请参见{@link MqttClient#setCallback(MqttCallback)} 以获取跟踪丢失连接的方法。
	 * The client is not connected to the server.  The {@link MqttClient#connect()}
	 * or {@link MqttClient#connect(MqttConnectOptions)} method must be called
	 * first.  It is also possible that the connection was lost - see 
	 * {@link MqttClient#setCallback(MqttCallback)} for a way to track lost
	 * connections.  
	 */
	public static final short REASON_CODE_CLIENT_NOT_CONNECTED          = 32104;

	/** 
	 * 服务器URI和提供的<code>SocketFactory</code>不匹配。
	 * URIs开头<code>tcp://</code>必须使用<code>javax.net.SocketFactory</code>，
	 * 而URIs开头<code>ssl://</code>必须使用<code>javax.net.ssl.SSLSocketFactory</code>。
	 * Server URI and supplied <code>SocketFactory</code> do not match.
	 * URIs beginning <code>tcp://</code> must use a <code>javax.net.SocketFactory</code>,
	 * and URIs beginning <code>ssl://</code> must use a <code>javax.net.ssl.SSLSocketFactory</code>.
	 */
	public static final short REASON_CODE_SOCKET_FACTORY_MISMATCH       = 32105;
	
	/**
	 * SSL配置错误。
	 * SSL configuration error.
	 */
	public static final short REASON_CODE_SSL_CONFIG_ERROR              = 32106;

	/** 
	 * 当试图从{@link MqttCallback}上的方法中调用{@link MqttClient#disconnect()}时引发。
	 * 这些方法由客户机的线程调用，不能用于控制断开连接。
	 * Thrown when an attempt to call {@link MqttClient#disconnect()} has been 
	 * made from within a method on {@link MqttCallback}.  These methods are invoked
	 * by the client's thread, and must not be used to control disconnection.
	 * 
	 * @see MqttCallback#messageArrived(String, MqttMessage)
	 */
	public static final short REASON_CODE_CLIENT_DISCONNECT_PROHIBITED  = 32107;

	/** 
	 * 协议错误:消息没有被识别为有效的MQTT包。
	 * 可能的原因包括连接到非MQTT服务器，或者在客户机不使用SSL时连接到SSL服务器端口。
	 * Protocol error: the message was not recognized as a valid MQTT packet.
	 * Possible reasons for this include connecting to a non-MQTT server, or
	 * connecting to an SSL server port when the client isn't using SSL.
	 */
	public static final short REASON_CODE_INVALID_MESSAGE				= 32108;

	/**
	 * 客户机意外地断开了与服务器的连接。{@link #getCause() cause}将提供更多细节。
	 * The client has been unexpectedly disconnected from the server. The {@link #getCause() cause}
	 * will provide more details. 
	 */
	public static final short REASON_CODE_CONNECTION_LOST               = 32109;
	
	/**
	 * 连接操作已经在进行中，一次只能发生一个连接。
	 * A connect operation in already in progress, only one connect can happen
	 * at a time.
	 */
	public static final short REASON_CODE_CONNECT_IN_PROGRESS           = 32110;
	
	/**
	 * 客户端已关闭——在此状态下，不允许对客户端进行任何操作。新增一个新客户继续。
	 * The client is closed - no operations are permitted on the client in this
	 * state.  New up a new client to continue.
	 */
	public static final short REASON_CODE_CLIENT_CLOSED		           = 32111;
	
	/**
	 * 请求使用已经与另一个操作关联的令牌。
	 * 如果操作完成，可以对令牌调用reset()，以便重用它。
	 * A request has been made to use a token that is already associated with
	 * another action.  If the action is complete the reset() can ve called on the
	 * token to allow it to be reused.  
	 */
	public static final short REASON_CODE_TOKEN_INUSE		           = 32201;
	
	/**
	 * 已发出发送消息的请求，但已达到飞行消息的最大数量。
	 * 一旦移动了一个或多个消息，就可以发送新的消息。
	 * A request has been made to send a message but the maximum number of inflight 
	 * messages has already been reached. Once one or more messages have been moved
	 * then new messages can be sent.   
	 */
	public static final short REASON_CODE_MAX_INFLIGHT    			= 32202;
	
	/**
	 * 客户端试图在“休眠”/脱机状态下发布消息，
	 * 同时启用了断开连接的发布功能，但是缓冲区已满，
	 * 且已禁用删除最老的消息，因此在客户端重新连接或应用程序手动删除已缓存的消息之前，
	 * 不能再发布任何消息。
	 * The Client has attempted to publish a message whilst in the 'resting' / offline
	 * state with Disconnected Publishing enabled, however the buffer is full and
	 * deleteOldestMessages is disabled, therefore no more messages can be published
	 * until the client reconnects, or the application deletes buffered message
	 * manually. 
	 */
	public static final short REASON_CODE_DISCONNECTED_BUFFER_FULL	= 32203;

	private int reasonCode;
	private Throwable cause;
	
	/**
	 * 构造一个新的<code>MqttException</code>，指定代码作为底层原因。
	 * Constructs a new <code>MqttException</code> with the specified code
	 * as the underlying reason.
	 * @param reasonCode the reason code for the exception.
	 * @param 推理代码异常的原因代码。
	 */
	public MqttException(int reasonCode) {
		super();
		this.reasonCode = reasonCode;
	}
	
	/**
	 * 构造一个新的<code>MqttException</code>，指定的<code>Throwable</code>作为底层原因。
	 * Constructs a new <code>MqttException</code> with the specified 
	 * <code>Throwable</code> as the underlying reason.
	 * @param cause the underlying cause of the exception.
	 * @param 导致异常的基本原因。
	 */
	public MqttException(Throwable cause) {
		super();
		this.reasonCode = REASON_CODE_CLIENT_EXCEPTION;
		this.cause = cause;
	}

	/**
	 * 构造一个新的<code>MqttException</code>，
	 * 指定的<code>Throwable</code>作为底层原因。
	 * Constructs a new <code>MqttException</code> with the specified 
	 * <code>Throwable</code> as the underlying reason.
	 * @param reason the reason code for the exception.
	 * @param cause the underlying cause of the exception.
	 * @param 推理异常的原因代码。
	 * @param 导致异常的基本原因。
	 */
	public MqttException(int reason, Throwable cause) {
		super();
		this.reasonCode = reason;
		this.cause = cause;
	}

	
	/**
	 * 返回此异常的原因代码。
	 * Returns the reason code for this exception.
	 * @return the code representing the reason for this exception.
	 * @return 表示此异常原因的代码。
	 */
	public int getReasonCode() {
		return reasonCode;
	}
	
	/**
	 * 返回此异常的基本原因(如果可用)。
	 * Returns the underlying cause of this exception, if available.
	 * @return the Throwable that was the root cause of this exception,
	 * which may be <code>null</code>.
	 * @return 这个异常的根本原因是可抛出的，它可能是<code>null</code>。
	 */
	public Throwable getCause() {
		return cause;
	}
	
	/**
	 * 返回此异常的详细信息。
	 * Returns the detail message for this exception.
	 * @return the detail message, which may be <code>null</code>.
	 * @return 详细信息，可能是<code>null</code>。
	 */
	public String getMessage() {
		return MessageCatalog.getMessage(reasonCode);
	}
	
	/**
	 * 返回此异常的<code>字符串</code>表示。
	 * Returns a <code>String</code> representation of this exception.
	 * @return a <code>String</code> representation of this exception.
	 * @return 这个异常的<code>字符串</code>表示。
	 */
	public String toString() {
		String result = getMessage() + " (" + reasonCode + ")";
		if (cause != null) {
			result = result + " - " + cause.toString();
		}
		return result;
	}
}
