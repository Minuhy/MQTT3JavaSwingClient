package top.luckysmile.mqtt.client.data;
/**
 * 这里创建表，存储着表的信息
 * @author minuy
 *
 */
public class SqlCreatTable {
	private String setTable = "create table mqtt_set(id integer primary key autoincrement ,set_name	text not null,clientid char(65535) ,ip char(128) ,port char(5) ,name char(65535) ,passwd char(65535) ,lwt_topic text ,lwt_qos char(1) default '0' check (lwt_qos in ('0','1','2')) ,lwt_context text ,sub_topic text ,sub_qos char(1) default '0' check (sub_qos in ('0','1','2')) ,pub_topic text ,pub_context text ,pub_qos char(1) default '0' check (pub_qos in ('0','1','2')))";
	private String sessionTable = "CREATE TABLE \"mqtt_session\" (\"id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\"time\" TEXT,\"topic\" TEXT,\"context\" TEXT,\"qos\" TEXT,\"other\" TEXT)";
	/**
	 * 创建设置表
	 * @return SQLite语句
	 */
	public String getSetTable() {
		return setTable;
	}
	
	/**
	 * 创建会话记录表
	 * @return SQLite语句
	 */
	public String getSessionTable() {
		return sessionTable;
	}
}
