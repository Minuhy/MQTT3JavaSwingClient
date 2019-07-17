package top.luckysmile.mqtt.client.data;

/**
 * 设置数据结构类，存设置数据
 * @author minuy
 *
 */
public class MqttSetBeans {
	private int setId;
	private String setName;
	private String Ip;
	private String Port;
	private String Id;
	private String Name;
	private String Passwd;
	private String LWTTopic;
	private String LWTContext;
	private String LWTQos;
	
	private String SubTopic;
	private String SubQos;
	
	private String PubTopic;
	private String PubContext;
	private String PubQos;
	public String getIp() {
		return Ip;
	}
	public void setIp(String ip) {
		Ip = ip;
	}
	public String getPort() {
		return Port;
	}
	public void setPort(String port) {
		Port = port;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPasswd() {
		return Passwd;
	}
	public void setPasswd(String passwd) {
		Passwd = passwd;
	}
	public String getLWTTopic() {
		return LWTTopic;
	}
	public void setLWTTopic(String lWTTopic) {
		LWTTopic = lWTTopic;
	}
	public String getLWTContext() {
		return LWTContext;
	}
	public void setLWTContext(String lWTContext) {
		LWTContext = lWTContext;
	}
	public String getLWTQos() {
		return LWTQos;
	}
	public void setLWTQos(String lWTQos) {
		LWTQos = lWTQos;
	}
	public String getSubTopic() {
		return SubTopic;
	}
	public void setSubTopic(String subTopic) {
		SubTopic = subTopic;
	}
	public String getSubQos() {
		return SubQos;
	}
	public void setSubQos(String subQos) {
		SubQos = subQos;
	}
	public String getPubTopic() {
		return PubTopic;
	}
	public void setPubTopic(String pubTopic) {
		PubTopic = pubTopic;
	}
	public String getPubContext() {
		return PubContext;
	}
	public void setPubContext(String pubContext) {
		PubContext = pubContext;
	}
	public String getPubQos() {
		return PubQos;
	}
	public int getSetId() {
		return setId;
	}
	public void setSetId(int setId) {
		this.setId = setId;
	}
	public String getSetName() {
		return setName;
	}
	public void setSetName(String setName) {
		this.setName = setName;
	}
	public void setPubQos(String pubQos) {
		PubQos = pubQos;
	}
	
}
