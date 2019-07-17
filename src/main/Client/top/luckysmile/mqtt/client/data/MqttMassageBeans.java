package top.luckysmile.mqtt.client.data;

/**
 * 消息结构类，用于存消息信息
 * @author minuy
 *
 */
public class MqttMassageBeans {
	private String time;
	private String topic;
	private String context;
	private String qos;
	private String other;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getQos() {
		return qos;
	}
	public void setQos(String qos) {
		this.qos = qos;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}

}
