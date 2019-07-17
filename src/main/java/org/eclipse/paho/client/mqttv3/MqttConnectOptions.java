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

import java.util.Properties;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import org.eclipse.paho.client.mqttv3.internal.NetworkModuleService;
import org.eclipse.paho.client.mqttv3.util.Debug;

/**
 * Holds the set of options that control how the client connects to a server.
 */
public class MqttConnectOptions {
	/**
	 * The default keep alive interval in seconds if one is not specified
	 */
	public static final int KEEP_ALIVE_INTERVAL_DEFAULT = 60;
	/**
	 * The default connection timeout in seconds if one is not specified
	 */
	public static final int CONNECTION_TIMEOUT_DEFAULT = 30;
	/**
	 * The default max inflight if one is not specified
	 */
	public static final int MAX_INFLIGHT_DEFAULT = 10;
	/**
	 * The default clean session setting if one is not specified
	 */
	public static final boolean CLEAN_SESSION_DEFAULT = true;
	/**
	 * The default MqttVersion is 3.1.1 first, dropping back to 3.1 if that fails
	 */
	public static final int MQTT_VERSION_DEFAULT = 0;
	/**
	 * Mqtt Version 3.1
	 */
	public static final int MQTT_VERSION_3_1 = 3;
	/**
	 * Mqtt Version 3.1.1
	 */
	public static final int MQTT_VERSION_3_1_1 = 4;

	private int keepAliveInterval = KEEP_ALIVE_INTERVAL_DEFAULT;
	private int maxInflight = MAX_INFLIGHT_DEFAULT;
	private String willDestination = null;
	private MqttMessage willMessage = null;
	private String userName;
	private char[] password;
	private SocketFactory socketFactory;
	private Properties sslClientProps = null;
	private boolean httpsHostnameVerificationEnabled = true;
	private HostnameVerifier sslHostnameVerifier = null;
	private boolean cleanSession = CLEAN_SESSION_DEFAULT;
	private int connectionTimeout = CONNECTION_TIMEOUT_DEFAULT;
	private String[] serverURIs = null;
	private int mqttVersion = MQTT_VERSION_DEFAULT;
	private boolean automaticReconnect = false;
	private int maxReconnectDelay = 128000;
	private Properties customWebSocketHeaders = null;

	// Client Operation Parameters
	private int executorServiceTimeout = 1; // How long to wait in seconds when terminating the executor service.

	/**
	 * <p>
	 * 使用默认值构造一个新的<code>MqttConnectOptions</code>对象。
	 * 
	 * 默认值是:
	 * <ul>
	 * <li>监测活性时间间隔为60秒</li>
	 * <li>默认清除会话</li>
	 * <li>消息传递重试间隔为15秒</li>
	 * <li>连接超时时间为30秒</li>
	 * <li>没有设置遗嘱信息</li>
	 * <li>使用标准SocketFactory</li>
	 * </ul>
	 * 有关这些值的更多信息可以在setter方法中找到。
	 * </p>
	 * <p>
	 * Constructs a new <code>MqttConnectOptions</code> object using the default
	 * values.
	 *
	 * The defaults are:
	 * <ul>
	 * <li>The keepalive interval is 60 seconds</li>
	 * <li>Clean Session is true</li>
	 * <li>The message delivery retry interval is 15 seconds</li>
	 * <li>The connection timeout period is 30 seconds</li>
	 * <li>No Will message is set</li>
	 * <li>A standard SocketFactory is used</li>
	 * </ul>
	 * More information about these values can be found in the setter methods.
	 * </p>
	 */
	public MqttConnectOptions() {
		// Initialise Base MqttConnectOptions Object
		//初始化基本MqttConnectOptions对象
	}

	/**
	 * <p>
	 * 返回用于连接的密码。
	 * @return 用于连接的密码。
	 * </p>
	 * <p>
	 * Returns the password to use for the connection.
	 * 
	 * @return the password to use for the connection.
	 * </p>
	 */
	public char[] getPassword() {
		return password;
	}

	/**
	 * <p>
	 * 设置连接使用的密码。
	 * </p>
	 * <p>
	 * Sets the password to use for the connection.
	 * 
	 * @param password
	 *            A Char Array of the password
	 * </p>
	 */
	public void setPassword(char[] password) {
		this.password = password.clone();
	}

	/**
	 * <p>
	 * 返回要用于连接的用户名。
	 * @return 用于连接的用户名。
	 * </p>
	 * <p>
	 * Returns the user name to use for the connection.
	 * 
	 * @return the user name to use for the connection.
	 * </p>
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * <p>
	 * 设置要用于连接的用户名。
	 * </p>
	 * <p>
	 * Sets the user name to use for the connection.
	 * 
	 * @param userName
	 *            The Username as a String
	 * </p>
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * <p>
	 * 获得在重新连接之间等待的最大时间(单位为millis)
	 * </p>
	 * <p>
	 * Get the maximum time (in millis) to wait between reconnects
	 * 
	 * @return Get the maximum time (in millis) to wait between reconnects
	 * </p>
	 */
	public int getMaxReconnectDelay() {
		return maxReconnectDelay;
	}

	/**
	 * <p>
	 * 设置重新连接之间等待的最长时间
	 * Set the maximum time to wait between reconnects
	 * </p>
	 * <p>
	 * @param maxReconnectDelay
	 *            the duration (in millis)
	 * </p>
	 */
	public void setMaxReconnectDelay(int maxReconnectDelay) {
		this.maxReconnectDelay = maxReconnectDelay;
	}

	/**
	 * <p>
	 * 为连接设置“Last Will and Testament”(临终遗嘱)。
	 * 如果这个客户机意外地失去了与服务器的连接，
	 * 服务器将使用提供的详细信息向自己发布一条消息。
	 * 
	 * @param topic
	 *            要发布到的主题。
	 * @param payload
	 *            消息的字节有效负载。
	 * @param qos
	 *            为(0,1或2)的发布消息的服务质量。
	 * @param retained
	 *            是否保留该消息。
	 * </p>
	 * <p>
	 * Sets the "Last Will and Testament" (LWT) for the connection. In the event
	 * that this client unexpectedly loses its connection to the server, the server
	 * will publish a message to itself using the supplied details.
	 *
	 * @param topic
	 *            the topic to publish to.
	 * @param payload
	 *            the byte payload for the message.
	 * @param qos
	 *            the quality of service to publish the message at (0, 1 or 2).
	 * @param retained
	 *            whether or not the message should be retained.
	 * </p>
	 */
	public void setWill(MqttTopic topic, byte[] payload, int qos, boolean retained) {
		String topicS = topic.getName();
		validateWill(topicS, payload);
		this.setWill(topicS, new MqttMessage(payload), qos, retained);
	}

	/**
	 * <p>
	 * 为连接设置“Last Will and Testament”(临终遗嘱)。
	 * 如果这个客户机意外地失去了与服务器的连接，
	 * 服务器将使用提供的详细信息向自己发布一条消息。
	 * 
	 * @param topic
	 *            要发布到的主题。
	 * @param payload
	 *            消息的字节有效负载。
	 * @param qos
	 *            为(0,1或2)的发布消息的服务质量。
	 * @param retained
	 *            是否保留该消息。
	 * </p>
	 * <p>
	 * Sets the "Last Will and Testament" (LWT) for the connection. In the event
	 * that this client unexpectedly loses its connection to the server, the server
	 * will publish a message to itself using the supplied details.
	 *
	 * @param topic
	 *            the topic to publish to.
	 * @param payload
	 *            the byte payload for the message.
	 * @param qos
	 *            the quality of service to publish the message at (0, 1 or 2).
	 * @param retained
	 *            whether or not the message should be retained.
	 * </p>
	 */
	public void setWill(String topic, byte[] payload, int qos, boolean retained) {
		validateWill(topic, payload);
		this.setWill(topic, new MqttMessage(payload), qos, retained);
	}

	/**
	 * Validates the will fields.
	 * 验证will字段。
	 */
	private void validateWill(String dest, Object payload) {
		if ((dest == null) || (payload == null)) {
			throw new IllegalArgumentException();
		}

		MqttTopic.validate(dest, false/* wildcards NOT allowed */);
	}

	/**
	 * <p>
	 * 根据提供的参数设置will信息。
	 * 
	 * @param topic
	 *            发送LWT消息的主题
	 * @param msg
	 *            要发送的{@link MqttMessage}
	 * @param qos
	 *            发送消息的QoS级别
	 * @param retained
	 *            是否保留该讯息
	 * </p>
	 * <p>
	 * Sets up the will information, based on the supplied parameters.
	 * 
	 * @param topic
	 *            the topic to send the LWT message to
	 * @param msg
	 *            the {@link MqttMessage} to send
	 * @param qos
	 *            the QoS Level to send the message at
	 * @param retained
	 *            whether the message should be retained or not
	 * </p>
	 */
	protected void setWill(String topic, MqttMessage msg, int qos, boolean retained) {
		willDestination = topic;
		willMessage = msg;
		willMessage.setQos(qos);
		willMessage.setRetained(retained);
		// Prevent any more changes to the will message
		// 防止对will消息进行任何更改
		willMessage.setMutable(false);
	}

	/**
	 * <p>
	 * 返回“保持活动”间隔。
	 * </p>
	 * <p>
	 * Returns the "keep alive" interval.
	 * 
	 * @see #setKeepAliveInterval(int)
	 * @return the keep alive interval.
	 * </p>
	 */
	public int getKeepAliveInterval() {
		return keepAliveInterval;
	}

	/**
	 * <p>
	 * 返回MQTT版本。
	 * </p>
	 * <p>
	 * Returns the MQTT version.
	 * 
	 * @see #setMqttVersion(int)
	 * @return the MQTT version.
	 * </p>
	 */
	public int getMqttVersion() {
		return mqttVersion;
	}

	/**
	 * <p>
	 * 设置“keep alive”间隔。
	 * 这个值(以秒为单位)定义了发送或接收消息之间的最大时间间隔。
	 * 它使客户机能够检测服务器是否不再可用，而不必等待TCP/IP超时。
	 * 客户端将确保在每个keep alive期间至少有一条消息通过网络传输。
	 * 在这段时间内，如果没有与数据相关的消息，客户端将发送一个非常小的“ping”消息，
	 * 服务器将确认该消息。值0禁用客户端中的keepalive处理。
	 * 
	 * @param keepAliveInterval
	 *            间隔(以秒为单位)必须是 &gt;= 0。
	 * @throws IllegalArgumentException
	 *             如果keepAliveInterval无效
	 * </p>
	 * <p>
	 * Sets the "keep alive" interval. This value, measured in seconds, defines the
	 * maximum time interval between messages sent or received. It enables the
	 * client to detect if the server is no longer available, without having to wait
	 * for the TCP/IP timeout. The client will ensure that at least one message
	 * travels across the network within each keep alive period. In the absence of a
	 * data-related message during the time period, the client sends a very small
	 * "ping" message, which the server will acknowledge. A value of 0 disables
	 * keepalive processing in the client.
	 * <p>
	 * The default value is 60 seconds
	 * </p>
	 *
	 * @param keepAliveInterval
	 *            the interval, measured in seconds, must be &gt;= 0.
	 * @throws IllegalArgumentException
	 *             if the keepAliveInterval was invalid
	 * </p>
	 */
	public void setKeepAliveInterval(int keepAliveInterval) throws IllegalArgumentException {
		if (keepAliveInterval < 0) {
			throw new IllegalArgumentException();
		}
		this.keepAliveInterval = keepAliveInterval;
	}

	/**
	 * <p>
	 * 返回“max inflight”。在没有收到确认的情况下，我们最多只能发送多少条消息。
	 * </p>
	 * <p>
	 * Returns the "max inflight". The max inflight limits to how many messages we
	 * can send without receiving acknowledgments.
	 * 
	 * @see #setMaxInflight(int)
	 * @return the max inflight
	 * </p>
	 */
	public int getMaxInflight() {
		return maxInflight;
	}

	/**
	 * <p>
	 * 设置“max inflight”。请在高流量环境中增加此值。
	 * <p>
	 * 默认值是10
	 * </p>
	 * </p>
	 * <p>
	 * Sets the "max inflight". please increase this value in a high traffic
	 * environment.
	 * <p>
	 * The default value is 10
	 * </p>
	 * 
	 * @param maxInflight
	 *            the number of maxInfligt messages
	 * </p>
	 */
	public void setMaxInflight(int maxInflight) {
		if (maxInflight < 0) {
			throw new IllegalArgumentException();
		}
		this.maxInflight = maxInflight;
	}

	/**
	 * <p>
	 * 返回连接超时值。
	 * </p>
	 * <p>
	 * Returns the connection timeout value.
	 * 
	 * @see #setConnectionTimeout(int)
	 * @return the connection timeout value.
	 * </p>
	 */
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * <p>
	 * 设置连接超时值。
	 * 这个值(以秒为单位)定义了客户机等待建立到MQTT服务器的网络连接的最大时间间隔。
	 * 默认超时时间为30秒。值0禁用超时处理，这意味着客户机将等待网络连接成功或失败。
	 * </p>
	 * <p>
	 * Sets the connection timeout value. This value, measured in seconds, defines
	 * the maximum time interval the client will wait for the network connection to
	 * the MQTT server to be established. The default timeout is 30 seconds. A value
	 * of 0 disables timeout processing meaning the client will wait until the
	 * network connection is made successfully or fails.
	 * 
	 * @param connectionTimeout
	 *            the timeout value, measured in seconds. It must be &gt;0;
	 * </p>
	 */
	public void setConnectionTimeout(int connectionTimeout) {
		if (connectionTimeout < 0) {
			throw new IllegalArgumentException();
		}
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * <p>
	 * 返回连接时将使用的socket factory，或者<code>null</code>(如果没有设置的话)。
	 * </p>
	 * <p>
	 * Returns the socket factory that will be used when connecting, or
	 * <code>null</code> if one has not been set.
	 * 
	 * @return The Socket Factory
	 * </p>
	 */
	public SocketFactory getSocketFactory() {
		return socketFactory;
	}

	/**
	 * <p>
	 * 设置要使用的<code>SocketFactory</code>。
	 * 这允许应用程序围绕创建网络套接字应用自己的策略。
	 * 如果使用SSL连接，可以使用<code>SSLSocketFactory</code>来提供特定于应用程序的安全设置。
	 * </p>
	 * <p>
	 * Sets the <code>SocketFactory</code> to use. This allows an application to
	 * apply its own policies around the creation of network sockets. If using an
	 * SSL connection, an <code>SSLSocketFactory</code> can be used to supply
	 * application-specific security settings.
	 * 
	 * @param socketFactory
	 *            the factory to use.
	 * </p>
	 */
	public void setSocketFactory(SocketFactory socketFactory) {
		this.socketFactory = socketFactory;
	}

	/**
	 * <p>
	 * 返回用于最后遗嘱(last will and testament, LWT)的主题。
	 * </p>
	 * <p>
	 * 
	 * Returns the topic to be used for last will and testament (LWT).
	 * 
	 * @return the MqttTopic to use, or <code>null</code> if LWT is not set.
	 * @see #setWill(MqttTopic, byte[], int, boolean)
	 * </p>
	 */
	public String getWillDestination() {
		return willDestination;
	}

	/**
	 * <p>
	 * 返回将作为最后遗嘱(last will and testament, LWT)发送的消息。
	 * 返回的对象是“只读”。
	 * 在返回的对象上调用任何“setter”方法将导致抛出<code>IllegalStateException</code>。
	 * </p>
	 * <p>
	 * Returns the message to be sent as last will and testament (LWT). The returned
	 * object is "read only". Calling any "setter" methods on the returned object
	 * will result in an <code>IllegalStateException</code> being thrown.
	 * 
	 * @return the message to use, or <code>null</code> if LWT is not set.
	 * </p>
	 */
	public MqttMessage getWillMessage() {
		return willMessage;
	}

	/**
	 * <p>
	 * 返回连接的SSL属性。
	 * </p>
	 * <p>
	 * Returns the SSL properties for the connection.
	 * 
	 * @return the properties for the SSL connection
	 * </p>
	 */
	public Properties getSSLProperties() {
		return sslClientProps;
	}

	/**
	 * Sets the SSL properties for the connection.
	 * <p>
	 * Note that these properties are only valid if an implementation of the Java
	 * Secure Socket Extensions (JSSE) is available. These properties are
	 * <em>not</em> used if a SocketFactory has been set using
	 * {@link #setSocketFactory(SocketFactory)}. The following properties can be
	 * used:
	 * </p>
	 * <dl>
	 * <dt>com.ibm.ssl.protocol</dt>
	 * <dd>One of: SSL, SSLv3, TLS, TLSv1, SSL_TLS.</dd>
	 * <dt>com.ibm.ssl.contextProvider
	 * <dd>Underlying JSSE provider. For example "IBMJSSE2" or "SunJSSE"</dd>
	 *
	 * <dt>com.ibm.ssl.keyStore</dt>
	 * <dd>The name of the file that contains the KeyStore object that you want the
	 * KeyManager to use. For example /mydir/etc/key.p12</dd>
	 *
	 * <dt>com.ibm.ssl.keyStorePassword</dt>
	 * <dd>The password for the KeyStore object that you want the KeyManager to use.
	 * The password can either be in plain-text, or may be obfuscated using the
	 * static method:
	 * <code>com.ibm.micro.security.Password.obfuscate(char[] password)</code>. This
	 * obfuscates the password using a simple and insecure XOR and Base64 encoding
	 * mechanism. Note that this is only a simple scrambler to obfuscate clear-text
	 * passwords.</dd>
	 *
	 * <dt>com.ibm.ssl.keyStoreType</dt>
	 * <dd>Type of key store, for example "PKCS12", "JKS", or "JCEKS".</dd>
	 *
	 * <dt>com.ibm.ssl.keyStoreProvider</dt>
	 * <dd>Key store provider, for example "IBMJCE" or "IBMJCEFIPS".</dd>
	 *
	 * <dt>com.ibm.ssl.trustStore</dt>
	 * <dd>The name of the file that contains the KeyStore object that you want the
	 * TrustManager to use.</dd>
	 *
	 * <dt>com.ibm.ssl.trustStorePassword</dt>
	 * <dd>The password for the TrustStore object that you want the TrustManager to
	 * use. The password can either be in plain-text, or may be obfuscated using the
	 * static method:
	 * <code>com.ibm.micro.security.Password.obfuscate(char[] password)</code>. This
	 * obfuscates the password using a simple and insecure XOR and Base64 encoding
	 * mechanism. Note that this is only a simple scrambler to obfuscate clear-text
	 * passwords.</dd>
	 *
	 * <dt>com.ibm.ssl.trustStoreType</dt>
	 * <dd>The type of KeyStore object that you want the default TrustManager to
	 * use. Same possible values as "keyStoreType".</dd>
	 *
	 * <dt>com.ibm.ssl.trustStoreProvider</dt>
	 * <dd>Trust store provider, for example "IBMJCE" or "IBMJCEFIPS".</dd>
	 *
	 * <dt>com.ibm.ssl.enabledCipherSuites</dt>
	 * <dd>A list of which ciphers are enabled. Values are dependent on the
	 * provider, for example:
	 * SSL_RSA_WITH_AES_128_CBC_SHA;SSL_RSA_WITH_3DES_EDE_CBC_SHA.</dd>
	 *
	 * <dt>com.ibm.ssl.keyManager</dt>
	 * <dd>Sets the algorithm that will be used to instantiate a KeyManagerFactory
	 * object instead of using the default algorithm available in the platform.
	 * Example values: "IbmX509" or "IBMJ9X509".</dd>
	 *
	 * <dt>com.ibm.ssl.trustManager</dt>
	 * <dd>Sets the algorithm that will be used to instantiate a TrustManagerFactory
	 * object instead of using the default algorithm available in the platform.
	 * Example values: "PKIX" or "IBMJ9X509".</dd>
	 * </dl>
	 * 
	 * @param props
	 *            The SSL {@link Properties}
	 */
	public void setSSLProperties(Properties props) {
		this.sslClientProps = props;
	}

	public boolean isHttpsHostnameVerificationEnabled() {
		return httpsHostnameVerificationEnabled;
	}

	public void setHttpsHostnameVerificationEnabled(boolean httpsHostnameVerificationEnabled) {
		this.httpsHostnameVerificationEnabled = httpsHostnameVerificationEnabled;
	}

	/**
	 * <p>
	 * 返回SSL连接的HostnameVerifier。
	 * </p>
	 * <p>
	 * Returns the HostnameVerifier for the SSL connection.
	 * 
	 * @return the HostnameVerifier for the SSL connection
	 * </p>
	 */
	public HostnameVerifier getSSLHostnameVerifier() {
		return sslHostnameVerifier;
	}

	/**
	 * <p>
	 *	设置SSL连接的HostnameVerifier。注意，它将在连接握手后使用，当主机名验证错误时，您应该使用自己的serlf执行操作。
	 * <p>
	 * 没有默认的HostnameVerifier
	 * </p>
	 * </p>
	 * <p>
	 * Sets the HostnameVerifier for the SSL connection. Note that it will be used
	 * after handshake on a connection and you should do actions by yourserlf when
	 * hostname is verified error.
	 * <p>
	 * There is no default HostnameVerifier
	 * </p>
	 * 
	 * @param hostnameVerifier
	 *            the {@link HostnameVerifier}
	 * </p>
	 */
	public void setSSLHostnameVerifier(HostnameVerifier hostnameVerifier) {
		this.sslHostnameVerifier = hostnameVerifier;
	}

	/**
	 * <p>
	 * 返回客户机和服务器是否应该在重新连接时记住客户机的状态。
	 * </p>
	 * <p>
	 * Returns whether the client and server should remember state for the client
	 * across reconnects.
	 * 
	 * @return the clean session flag
	 * </p>
	 */
	public boolean isCleanSession() {
		return this.cleanSession;
	}

	/**
	 * <p>
	 * 设置客户机和服务器是否应该在重启和重新连接时记住状态。
	 * <ul>
	 * <li>如果设置为false，客户端和服务器将在客户端、服务器和连接重启期间保持状态。保持状态:
	 * <ul>
	 * <li>即使重新启动客户机、服务器或连接，消息传递也将可靠地满足指定的QOS。
	 * <li>服务器将把订阅视为持久的。
	 * </ul>
	 * <li>如果设置为true，客户端和服务器将不会跨客户端、服务器或连接的重启维护状态。这意味着
	 * <ul>
	 * <li>如果重新启动客户机、服务器或连接，则无法维护到指定QOS的消息传递
	 * <li>服务器将把订阅视为非持久的
	 * </ul>
	 * </ul>
	 * 
	 * </p>
	 * <p>
	 * Sets whether the client and server should remember state across restarts and
	 * reconnects.
	 * <ul>
	 * <li>If set to false both the client and server will maintain state across
	 * restarts of the client, the server and the connection. As state is
	 * maintained:
	 * <ul>
	 * <li>Message delivery will be reliable meeting the specified QOS even if the
	 * client, server or connection are restarted.
	 * <li>The server will treat a subscription as durable.
	 * </ul>
	 * <li>If set to true the client and server will not maintain state across
	 * restarts of the client, the server or the connection. This means
	 * <ul>
	 * <li>Message delivery to the specified QOS cannot be maintained if the client,
	 * server or connection are restarted
	 * <li>The server will treat a subscription as non-durable
	 * </ul>
	 * </ul>
	 * 
	 * @param cleanSession
	 *            Set to True to enable cleanSession
	 * </p>
	 */
	public void setCleanSession(boolean cleanSession) {
		this.cleanSession = cleanSession;
	}

	/**
	 * <p>
	 * 返回客户机可能连接到的serverURIs列表
	 * </p>
	 * <p>
	 * Return a list of serverURIs the client may connect to
	 * 
	 * @return the serverURIs or null if not set
	 * </p>
	 */
	public String[] getServerURIs() {
		return serverURIs;
	}

	/**
	 * <p>
	 * 设置客户端可能连接到的一个或多个serverURIs的列表。
	 * <p>
	 * 每个<code>serverURI</code>指定客户机可以连接到的服务器的地址。
	 * 支持两种类型的连接<code>tcp://</code> 用于TCP连接，
	 * 另外一种<code>ssl://</code>用于受保护的TCP连接，例如：
	 * <ul>
	 * <li><code>tcp://localhost:1883</code></li>
	 * <li><code>ssl://localhost:8883</code></li>
	 * </ul>
	 * 如果端口没有指定，则默认的
	 * <code>tcp://</code>连接使用1883端口, <code>ssl://</code> 连接使用8883端口。
	 * <p>
	 * 如果设置了serverURI，那么它将覆盖传递给MQTT客户机构造函数的serverURI参数。
	 * <p>
	 * 当启动连接尝试时，客户机将从列表中的第一个serverURI开始，
	 * 并遍历列表，直到与服务器建立连接为止。
	 * 如果无法连接到任何服务器，则连接尝试失败。
	 * <p>
	 * 指定客户端可以连接到的服务器列表有以下几种用途:
	 * <ol>
	 * <li>高可用性和可靠的消息传递
	 * <p>
	 * 一些MQTT服务器支持高可用性特性，
	 * 其中两个或多个“相等”的MQTT服务器共享状态。
	 * MQTT客户机可以连接到任何“相等的”服务器，
	 * 并确保无论客户机连接到哪台服务器，消息都是可靠地传递的，
	 * 并维护持久订阅。
	 * </p>
	 * <p>
	 * 如果需要持久订阅和/或可靠的消息传递，则必须将cleansession标志设置为false。
	 * </p>
	 * </li>
	 * <li>黑名单
	 * <p>
	 * 可以指定一组不“相等”的服务器(如在high availability选项中)。
	 * 由于服务器之间不共享任何状态，因此不存在可靠的消息传递和持久订阅。
	 * 如果使用黑名单模式，清除会话标志必须设置为true。
	 * </p>
	 * </li>
	 * </ol>
	 * 
	 * @param serverURIs
	 *            供客户使用
	 * 
	 * </p>
	 * <p>
	 * Set a list of one or more serverURIs the client may connect to.
	 * <p>
	 * Each <code>serverURI</code> specifies the address of a server that the client
	 * may connect to. Two types of connection are supported <code>tcp://</code> for
	 * a TCP connection and <code>ssl://</code> for a TCP connection secured by
	 * SSL/TLS. For example:
	 * <ul>
	 * <li><code>tcp://localhost:1883</code></li>
	 * <li><code>ssl://localhost:8883</code></li>
	 * </ul>
	 * If the port is not specified, it will default to 1883 for
	 * <code>tcp://</code>" URIs, and 8883 for <code>ssl://</code> URIs.
	 * <p>
	 * If serverURIs is set then it overrides the serverURI parameter passed in on
	 * the constructor of the MQTT client.
	 * <p>
	 * When an attempt to connect is initiated the client will start with the first
	 * serverURI in the list and work through the list until a connection is
	 * established with a server. If a connection cannot be made to any of the
	 * servers then the connect attempt fails.
	 * <p>
	 * Specifying a list of servers that a client may connect to has several uses:
	 * <ol>
	 * <li>High Availability and reliable message delivery
	 * <p>
	 * Some MQTT servers support a high availability feature where two or more
	 * "equal" MQTT servers share state. An MQTT client can connect to any of the
	 * "equal" servers and be assured that messages are reliably delivered and
	 * durable subscriptions are maintained no matter which server the client
	 * connects to.
	 * </p>
	 * <p>
	 * The cleansession flag must be set to false if durable subscriptions and/or
	 * reliable message delivery is required.
	 * </p>
	 * </li>
	 * <li>Hunt List
	 * <p>
	 * A set of servers may be specified that are not "equal" (as in the high
	 * availability option). As no state is shared across the servers reliable
	 * message delivery and durable subscriptions are not valid. The cleansession
	 * flag must be set to true if the hunt list mode is used
	 * </p>
	 * </li>
	 * </ol>
	 * 
	 * @param serverURIs
	 *            to be used by the client
	 * </p>
	 */
	public void setServerURIs(String[] serverURIs) {
		for (String serverURI : serverURIs) {
			NetworkModuleService.validateURI(serverURI);
		}
		this.serverURIs = serverURIs.clone();
	}

	/**
	 * <p>
	 * 设置MQTT版本。默认操作是连接到3.1.1版本，如果失败，
	 * 则返回到3.1版本。通过分别使用MQTT_VERSION_3_1_1
	 * 或MQTT_VERSION_3_1选项，可以特定地选择版本3.1.1或3.1，
	 * 而不需要后退。
	 * </p>
	 * 
	 * <p>
	 * Sets the MQTT version. The default action is to connect with version 3.1.1,
	 * and to fall back to 3.1 if that fails. Version 3.1.1 or 3.1 can be selected
	 * specifically, with no fall back, by using the MQTT_VERSION_3_1_1 or
	 * MQTT_VERSION_3_1 options respectively.
	 *
	 * @param mqttVersion
	 *            the version of the MQTT protocol.
	 * @throws IllegalArgumentException
	 *             If the MqttVersion supplied is invalid
	 * </p>
	 */
	public void setMqttVersion(int mqttVersion) throws IllegalArgumentException {
		if (mqttVersion != MQTT_VERSION_DEFAULT && mqttVersion != MQTT_VERSION_3_1
				&& mqttVersion != MQTT_VERSION_3_1_1) {
			throw new IllegalArgumentException(
					"An incorrect version was used \"" + mqttVersion + "\". Acceptable version options are "
							+ MQTT_VERSION_DEFAULT + ", " + MQTT_VERSION_3_1 + " and " + MQTT_VERSION_3_1_1 + ".");
		}
		this.mqttVersion = mqttVersion;
	}

	/**
	 * <p>
	 * 返回如果连接丢失，客户机是否会自动尝试重新连接到服务器
	 * </p>
	 * 
	 * <p>
	 * Returns whether the client will automatically attempt to reconnect to the
	 * server if the connection is lost
	 * 
	 * @return the automatic reconnection flag.
	 * </p>
	 */
	public boolean isAutomaticReconnect() {
		return automaticReconnect;
	}

	/**
	 * <p>
	 * 设置如果连接丢失，客户端是否会自动尝试重新连接到服务器。
	 * <ul>
	 * <li>如果设置为false，客户端将不会尝试在连接丢失时自动重新连接到服务器。</li>
	 * <li>如果设置为true，
	 * 在连接丢失的情况下，
	 * 客户机将尝试重新连接到服务器。
	 * 它最初将等待1秒，然后尝试重新连接，
	 * 对于每一次失败的重新连接尝试，延迟将加倍，
	 * 直到2分钟，此时延迟将保持在2分钟。</li>
	 * </ul>
	 * </p>
	 * <p>
	 * Sets whether the client will automatically attempt to reconnect to the server
	 * if the connection is lost.
	 * <ul>
	 * <li>If set to false, the client will not attempt to automatically reconnect
	 * to the server in the event that the connection is lost.</li>
	 * <li>If set to true, in the event that the connection is lost, the client will
	 * attempt to reconnect to the server. It will initially wait 1 second before it
	 * attempts to reconnect, for every failed reconnect attempt, the delay will
	 * double until it is at 2 minutes at which point the delay will stay at 2
	 * minutes.</li>
	 * </ul>
	 * 
	 * @param automaticReconnect
	 *            If set to True, Automatic Reconnect will be enabled
	 * </p>
	 */
	public void setAutomaticReconnect(boolean automaticReconnect) {
		this.automaticReconnect = automaticReconnect;
	}
	
	public int getExecutorServiceTimeout() {
		return executorServiceTimeout;
	}

	/**
	 * <p>
	 * 在强制终止之前，设置executor服务在终止时应该等待的时间(以秒为单位)。除非绝对确定需要更改此值，否则不建议更改此值。
	 * </p>
	 * <p>
	 * Set the time in seconds that the executor service should wait when
	 * terminating before forcefully terminating. It is not recommended to change
	 * this value unless you are absolutely sure that you need to.
	 * 
	 * @param executorServiceTimeout the time in seconds to wait when shutting down.
	 * </p>
	 */
	public void setExecutorServiceTimeout(int executorServiceTimeout) {
		this.executorServiceTimeout = executorServiceTimeout;
	}

	/**
	 * <p>
	 * @return Debug属性
	 * </p>
	 * <p>
	 * @return The Debug Properties
	 * </p>
	 */
	public Properties getDebug() {
		final String strNull = "null";
		Properties p = new Properties();
		p.put("MqttVersion", Integer.valueOf(getMqttVersion()));
		p.put("CleanSession", Boolean.valueOf(isCleanSession()));
		p.put("ConTimeout", Integer.valueOf(getConnectionTimeout()));
		p.put("KeepAliveInterval", Integer.valueOf(getKeepAliveInterval()));
		p.put("UserName", (getUserName() == null) ? strNull : getUserName());
		p.put("WillDestination", (getWillDestination() == null) ? strNull : getWillDestination());
		if (getSocketFactory() == null) {
			p.put("SocketFactory", strNull);
		} else {
			p.put("SocketFactory", getSocketFactory());
		}
		if (getSSLProperties() == null) {
			p.put("SSLProperties", strNull);
		} else {
			p.put("SSLProperties", getSSLProperties());
		}
		return p;
	}

	/**
	 * <p>
	 * 设置WebSocket连接的自定义WebSocket头文件。
	 * </p>
	 * <p>
	 * Sets the Custom WebSocket Headers for the WebSocket Connection.
	 *
	 * @param props
	 *            The custom websocket headers {@link Properties}
	 * </p>
	 */

	public void setCustomWebSocketHeaders(Properties props) {
		this.customWebSocketHeaders = props;
	}

	public Properties getCustomWebSocketHeaders() {
		return customWebSocketHeaders;
	}

	public String toString() {
		return Debug.dumpProperties(getDebug(), "Connection options");
	}
	
}
