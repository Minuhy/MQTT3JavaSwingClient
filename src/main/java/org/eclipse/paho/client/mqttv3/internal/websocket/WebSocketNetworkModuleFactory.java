/*
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 */
package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;
import org.eclipse.paho.client.mqttv3.internal.NetworkModule;
import org.eclipse.paho.client.mqttv3.spi.NetworkModuleFactory;

public class WebSocketNetworkModuleFactory implements NetworkModuleFactory {

	@Override
	public Set<String> getSupportedUriSchemes() {
		return Collections.unmodifiableSet(new HashSet<>(Arrays.asList("ws")));
	}

	@Override
	public void validateURI(URI brokerUri) throws IllegalArgumentException {
		// so specific requirements so far
	}

	@Override
	public NetworkModule createNetworkModule(URI brokerUri, MqttConnectOptions options, String clientId)
			throws MqttException
	{
		String host = brokerUri.getHost();
		int port = brokerUri.getPort(); // -1 if not defined
		if (port == -1) {
			port = 80;
		}
		SocketFactory factory = options.getSocketFactory();
		if (factory == null) {
			factory = SocketFactory.getDefault();
		} else if (factory instanceof SSLSocketFactory) {
			throw ExceptionHelper.createMqttException(MqttException.REASON_CODE_SOCKET_FACTORY_MISMATCH);
		}
		WebSocketNetworkModule netModule = new WebSocketNetworkModule(factory, brokerUri.toString(), host, port,
				clientId, options.getCustomWebSocketHeaders());
		netModule.setConnectTimeout(options.getConnectionTimeout());
		return netModule;
	}
}
