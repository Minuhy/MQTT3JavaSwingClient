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
	 * ʹ��Ĭ��ֵ����һ���µ�<code>MqttConnectOptions</code>����
	 * 
	 * Ĭ��ֵ��:
	 * <ul>
	 * <li>������ʱ����Ϊ60��</li>
	 * <li>Ĭ������Ự</li>
	 * <li>��Ϣ�������Լ��Ϊ15��</li>
	 * <li>���ӳ�ʱʱ��Ϊ30��</li>
	 * <li>û������������Ϣ</li>
	 * <li>ʹ�ñ�׼SocketFactory</li>
	 * </ul>
	 * �й���Щֵ�ĸ�����Ϣ������setter�������ҵ���
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
		//��ʼ������MqttConnectOptions����
	}

	/**
	 * <p>
	 * �����������ӵ����롣
	 * @return �������ӵ����롣
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
	 * ��������ʹ�õ����롣
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
	 * ����Ҫ�������ӵ��û�����
	 * @return �������ӵ��û�����
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
	 * ����Ҫ�������ӵ��û�����
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
	 * �������������֮��ȴ������ʱ��(��λΪmillis)
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
	 * ������������֮��ȴ����ʱ��
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
	 * Ϊ�������á�Last Will and Testament��(��������)��
	 * �������ͻ��������ʧȥ��������������ӣ�
	 * ��������ʹ���ṩ����ϸ��Ϣ���Լ�����һ����Ϣ��
	 * 
	 * @param topic
	 *            Ҫ�����������⡣
	 * @param payload
	 *            ��Ϣ���ֽ���Ч���ء�
	 * @param qos
	 *            Ϊ(0,1��2)�ķ�����Ϣ�ķ���������
	 * @param retained
	 *            �Ƿ�������Ϣ��
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
	 * Ϊ�������á�Last Will and Testament��(��������)��
	 * �������ͻ��������ʧȥ��������������ӣ�
	 * ��������ʹ���ṩ����ϸ��Ϣ���Լ�����һ����Ϣ��
	 * 
	 * @param topic
	 *            Ҫ�����������⡣
	 * @param payload
	 *            ��Ϣ���ֽ���Ч���ء�
	 * @param qos
	 *            Ϊ(0,1��2)�ķ�����Ϣ�ķ���������
	 * @param retained
	 *            �Ƿ�������Ϣ��
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
	 * ��֤will�ֶΡ�
	 */
	private void validateWill(String dest, Object payload) {
		if ((dest == null) || (payload == null)) {
			throw new IllegalArgumentException();
		}

		MqttTopic.validate(dest, false/* wildcards NOT allowed */);
	}

	/**
	 * <p>
	 * �����ṩ�Ĳ�������will��Ϣ��
	 * 
	 * @param topic
	 *            ����LWT��Ϣ������
	 * @param msg
	 *            Ҫ���͵�{@link MqttMessage}
	 * @param qos
	 *            ������Ϣ��QoS����
	 * @param retained
	 *            �Ƿ�����ѶϢ
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
		// ��ֹ��will��Ϣ�����κθ���
		willMessage.setMutable(false);
	}

	/**
	 * <p>
	 * ���ء����ֻ�������
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
	 * ����MQTT�汾��
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
	 * ���á�keep alive�������
	 * ���ֵ(����Ϊ��λ)�����˷��ͻ������Ϣ֮������ʱ������
	 * ��ʹ�ͻ����ܹ����������Ƿ��ٿ��ã������صȴ�TCP/IP��ʱ��
	 * �ͻ��˽�ȷ����ÿ��keep alive�ڼ�������һ����Ϣͨ�����紫�䡣
	 * �����ʱ���ڣ����û����������ص���Ϣ���ͻ��˽�����һ���ǳ�С�ġ�ping����Ϣ��
	 * ��������ȷ�ϸ���Ϣ��ֵ0���ÿͻ����е�keepalive����
	 * 
	 * @param keepAliveInterval
	 *            ���(����Ϊ��λ)������ &gt;= 0��
	 * @throws IllegalArgumentException
	 *             ���keepAliveInterval��Ч
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
	 * ���ء�max inflight������û���յ�ȷ�ϵ�����£��������ֻ�ܷ��Ͷ�������Ϣ��
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
	 * ���á�max inflight�������ڸ��������������Ӵ�ֵ��
	 * <p>
	 * Ĭ��ֵ��10
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
	 * �������ӳ�ʱֵ��
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
	 * �������ӳ�ʱֵ��
	 * ���ֵ(����Ϊ��λ)�����˿ͻ����ȴ�������MQTT���������������ӵ����ʱ������
	 * Ĭ�ϳ�ʱʱ��Ϊ30�롣ֵ0���ó�ʱ��������ζ�ſͻ������ȴ��������ӳɹ���ʧ�ܡ�
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
	 * ��������ʱ��ʹ�õ�socket factory������<code>null</code>(���û�����õĻ�)��
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
	 * ����Ҫʹ�õ�<code>SocketFactory</code>��
	 * ������Ӧ�ó���Χ�ƴ��������׽���Ӧ���Լ��Ĳ��ԡ�
	 * ���ʹ��SSL���ӣ�����ʹ��<code>SSLSocketFactory</code>���ṩ�ض���Ӧ�ó���İ�ȫ���á�
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
	 * ���������������(last will and testament, LWT)�����⡣
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
	 * ���ؽ���Ϊ�������(last will and testament, LWT)���͵���Ϣ��
	 * ���صĶ����ǡ�ֻ������
	 * �ڷ��صĶ����ϵ����κΡ�setter�������������׳�<code>IllegalStateException</code>��
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
	 * �������ӵ�SSL���ԡ�
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
	 * ����SSL���ӵ�HostnameVerifier��
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
	 *	����SSL���ӵ�HostnameVerifier��ע�⣬�������������ֺ�ʹ�ã�����������֤����ʱ����Ӧ��ʹ���Լ���serlfִ�в�����
	 * <p>
	 * û��Ĭ�ϵ�HostnameVerifier
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
	 * ���ؿͻ����ͷ������Ƿ�Ӧ������������ʱ��ס�ͻ�����״̬��
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
	 * ���ÿͻ����ͷ������Ƿ�Ӧ������������������ʱ��ס״̬��
	 * <ul>
	 * <li>�������Ϊfalse���ͻ��˺ͷ��������ڿͻ��ˡ������������������ڼ䱣��״̬������״̬:
	 * <ul>
	 * <li>��ʹ���������ͻ����������������ӣ���Ϣ����Ҳ���ɿ�������ָ����QOS��
	 * <li>���������Ѷ�����Ϊ�־õġ�
	 * </ul>
	 * <li>�������Ϊtrue���ͻ��˺ͷ������������ͻ��ˡ������������ӵ�����ά��״̬������ζ��
	 * <ul>
	 * <li>������������ͻ����������������ӣ����޷�ά����ָ��QOS����Ϣ����
	 * <li>���������Ѷ�����Ϊ�ǳ־õ�
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
	 * ���ؿͻ����������ӵ���serverURIs�б�
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
	 * ���ÿͻ��˿������ӵ���һ������serverURIs���б�
	 * <p>
	 * ÿ��<code>serverURI</code>ָ���ͻ����������ӵ��ķ������ĵ�ַ��
	 * ֧���������͵�����<code>tcp://</code> ����TCP���ӣ�
	 * ����һ��<code>ssl://</code>�����ܱ�����TCP���ӣ����磺
	 * <ul>
	 * <li><code>tcp://localhost:1883</code></li>
	 * <li><code>ssl://localhost:8883</code></li>
	 * </ul>
	 * ����˿�û��ָ������Ĭ�ϵ�
	 * <code>tcp://</code>����ʹ��1883�˿�, <code>ssl://</code> ����ʹ��8883�˿ڡ�
	 * <p>
	 * ���������serverURI����ô�������Ǵ��ݸ�MQTT�ͻ������캯����serverURI������
	 * <p>
	 * ���������ӳ���ʱ���ͻ��������б��еĵ�һ��serverURI��ʼ��
	 * �������б�ֱ�����������������Ϊֹ��
	 * ����޷����ӵ��κη������������ӳ���ʧ�ܡ�
	 * <p>
	 * ָ���ͻ��˿������ӵ��ķ������б������¼�����;:
	 * <ol>
	 * <li>�߿����ԺͿɿ�����Ϣ����
	 * <p>
	 * һЩMQTT������֧�ָ߿��������ԣ�
	 * ����������������ȡ���MQTT����������״̬��
	 * MQTT�ͻ����������ӵ��κΡ���ȵġ���������
	 * ��ȷ�����ۿͻ������ӵ���̨����������Ϣ���ǿɿ��ش��ݵģ�
	 * ��ά���־ö��ġ�
	 * </p>
	 * <p>
	 * �����Ҫ�־ö��ĺ�/��ɿ�����Ϣ���ݣ�����뽫cleansession��־����Ϊfalse��
	 * </p>
	 * </li>
	 * <li>������
	 * <p>
	 * ����ָ��һ�鲻����ȡ��ķ�����(����high availabilityѡ����)��
	 * ���ڷ�����֮�䲻�����κ�״̬����˲����ڿɿ�����Ϣ���ݺͳ־ö��ġ�
	 * ���ʹ�ú�����ģʽ������Ự��־��������Ϊtrue��
	 * </p>
	 * </li>
	 * </ol>
	 * 
	 * @param serverURIs
	 *            ���ͻ�ʹ��
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
	 * ����MQTT�汾��Ĭ�ϲ��������ӵ�3.1.1�汾�����ʧ�ܣ�
	 * �򷵻ص�3.1�汾��ͨ���ֱ�ʹ��MQTT_VERSION_3_1_1
	 * ��MQTT_VERSION_3_1ѡ������ض���ѡ��汾3.1.1��3.1��
	 * ������Ҫ���ˡ�
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
	 * ����������Ӷ�ʧ���ͻ����Ƿ���Զ������������ӵ�������
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
	 * ����������Ӷ�ʧ���ͻ����Ƿ���Զ������������ӵ���������
	 * <ul>
	 * <li>�������Ϊfalse���ͻ��˽����᳢�������Ӷ�ʧʱ�Զ��������ӵ���������</li>
	 * <li>�������Ϊtrue��
	 * �����Ӷ�ʧ������£�
	 * �ͻ����������������ӵ���������
	 * ��������ȴ�1�룬Ȼ�����������ӣ�
	 * ����ÿһ��ʧ�ܵ��������ӳ��ԣ��ӳٽ��ӱ���
	 * ֱ��2���ӣ���ʱ�ӳٽ�������2���ӡ�</li>
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
	 * ��ǿ����ֹ֮ǰ������executor��������ֹʱӦ�õȴ���ʱ��(����Ϊ��λ)�����Ǿ���ȷ����Ҫ���Ĵ�ֵ�����򲻽�����Ĵ�ֵ��
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
	 * @return Debug����
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
	 * ����WebSocket���ӵ��Զ���WebSocketͷ�ļ���
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
