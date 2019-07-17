package top.luckysmile.mqtt.client.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import top.luckysmile.mqtt.client.server.GeneratePassword;
/**
 * 数据库交互
 * @author minuy
 *
 */
public class MqttDAO {
	
	public boolean SaveSession(MqttMassageBeans massage) {
		boolean yes = false;
		SQLiteTest("mqtt_session");
		//数据库语句
		String sql = "INSERT INTO \"main\".\"mqtt_session\" (\"time\", \"topic\", \"context\", \"qos\", \"other\") VALUES (?, ?, ?, ?, ?)";
		//获得数据库连接
		Connection conn = MqttDbUtil.getConn();
		//声明句柄对象
		PreparedStatement pstm = null;
		try {
			//获得操作句柄
			pstm = conn.prepareStatement(sql);
			//拼接语句
			if(massage.getTime() == null) {
				massage.setTime("");
			}
			pstm.setString(1, massage.getTime());
			
			if(massage.getTopic() == null) {
				massage.setTopic("");
			}
			pstm.setString(2, massage.getTopic());
			
			if(massage.getContext() == null) {
				massage.setContext("");
			}
			pstm.setString(3, massage.getContext());
			
			if(massage.getQos() == null) {
				massage.setQos("");
			}
			pstm.setString(4, massage.getQos());
			
			if(massage.getOther() == null) {
				massage.setOther("");
			}
			pstm.setString(5, massage.getOther());
			
			//执行语句
			yes = pstm.execute();

		} catch (SQLException e) {
			System.out.println(e.toString());
		}finally {
			//一定要做的事，释放连接
			MqttDbUtil.free(pstm,conn);
		}
		
		return yes;
	}

	/**
	 * 保存设置到数据库中
	 * @param set 设置
	 * @return 成败
	 */
	public boolean SaveSet(MqttSetBeans set) {
		boolean yes = false;
		SQLiteTest("mqtt_set");
		String name = new GeneratePassword().get(8);
		//数据库语句
		String sql = "INSERT INTO \"main\".\"mqtt_set\" (\"set_name\", \"clientid\", \"ip\", \"port\", \"name\", \"passwd\", \"lwt_topic\", \"lwt_qos\", \"lwt_context\", \"sub_topic\", \"sub_qos\", \"pub_topic\", \"pub_context\", \"pub_qos\") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		//获得数据库连接
		Connection conn = MqttDbUtil.getConn();
		//声明句柄对象
		PreparedStatement pstm = null;
		try {
			//获得操作句柄
			pstm = conn.prepareStatement(sql);
			//拼接语句
			pstm.setString(1, name);
			if(set.getId() == null) {
				set.setId("");
			}
			pstm.setString(2, set.getId());
			
			if(set.getIp() == null) {
				set.setIp("");
			}
			pstm.setString(3, set.getIp());
			
			if(set.getPort() == null) {
				set.setPort("");
			}
			pstm.setString(4, set.getPort());
			
			if(set.getName() == null) {
				set.setName("");
			}
			pstm.setString(5, set.getName());
			
			if(set.getPasswd() == null) {
				set.setPasswd("");
			}
			pstm.setString(6, set.getPasswd());
			
			if(set.getLWTTopic() == null) {
				set.setLWTTopic("");
			}
			pstm.setString(7, set.getLWTTopic());
			
			if(set.getLWTQos() == null) {
				set.setLWTQos("");
			}
			pstm.setString(8, set.getLWTQos());
			
			if(set.getLWTContext() == null) {
				set.setLWTContext("");
			}
			pstm.setString(9, set.getLWTContext());
			
			if(set.getSubTopic() == null) {
				set.setSubTopic("");
			}
			pstm.setString(10, set.getSubTopic());
			
			if(set.getSubQos() == null) {
				set.setSubQos("");
			}
			pstm.setString(11, set.getSubQos());
			
			if(set.getPubTopic() == null) {
				set.setPubTopic("");
			}
			pstm.setString(12, set.getPubTopic());
			
			if(set.getPubContext() == null) {
				set.setPubContext("");
			}
			pstm.setString(13, set.getPubContext());
			
			if(set.getPubQos() == null) {
				set.setPubQos("");
			}
			pstm.setString(14, set.getPubQos());
			
			//执行语句
			yes = pstm.execute();

		} catch (SQLException e) {
			System.out.println(e.toString());
		}finally {
			//一定要做的事，释放连接
			MqttDbUtil.free(pstm,conn);
		}
		
		return yes;
	}

	/**
	 * 20150416 判断sqlite中是否存在表
	 * author:zhouyun ,date:20150416
	 * @param dbFile db文件全路径
	 * @param tableName 需要查询的表名
	 * @return
	 */
	private boolean sqliteTabbleIsExist(String tableName){
		boolean flag = false;
		Connection conn = null;
		ResultSet set = null;
		Statement state = null;
		try {
			conn = MqttDbUtil.getConn();
			String sql = "select count(*) from sqlite_master where type ='table' and name ='"+tableName.trim()+"' ";
			state = conn.createStatement();
			set = state.executeQuery(sql);
			int count = set.getInt(1);
			if(count>0){
				flag = true;
			}
		} catch (Exception e) {
			System.err.println(e);
		}finally{
			//一定要做的事，释放连接
			MqttDbUtil.free(set,conn);
			try {
				if(state != null){
					state.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}


	private void SQLiteTest(String Table) {
		if(!sqliteTabbleIsExist(Table)) {
			SqliteCreateTable(Table);
		}
	}


	private void SqliteCreateTable(String table) {

		String sql = null;

		if(table.equals("mqtt_set")) {
			sql = new SqlCreatTable().getSetTable();
		}
		if(table.equals("mqtt_session")) {
			sql = new SqlCreatTable().getSessionTable();
		}

		Connection conn = null;
		ResultSet set = null;
		Statement state = null;
		try {
			conn = MqttDbUtil.getConn();
			state = conn.createStatement();
			set = state.executeQuery(sql);
		} catch (Exception e) {
			System.err.println(e);
		}finally{
			//一定要做的事，释放连接
			MqttDbUtil.free(set,conn);
			try {
				if(state != null){
					state.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public MqttSetBeans getSeting(){

		SQLiteTest("mqtt_set");

		List<MqttSetBeans> list = new ArrayList<MqttSetBeans>();
		MqttSetBeans set = null;
		//数据库语句
		String sql = "select * from mqtt_set";
		//获得数据库连接
		Connection conn = MqttDbUtil.getConn();
		//声明句柄对象
		PreparedStatement pstm = null;
		try {
			//获得操作句柄
			pstm = conn.prepareStatement(sql);
			//根据SQL语句获取数据
			list = getDataBySQL(pstm);
			//提取
			if(list.size() > 0) {
				set = list.get(list.size()-1);
			}

		} catch (SQLException e) {
			System.out.println(e.toString());
		}finally {
			//一定要做的事，释放连接
			MqttDbUtil.free(pstm,conn);
		}

		return set;
	}


	public List<MqttSetBeans> getDataBySQL(PreparedStatement pstm){
		List<MqttSetBeans> list = new ArrayList<MqttSetBeans>();
		//声明操作对象
		ResultSet rs = null;
		//声明一个BookInfoBeans对象
		MqttSetBeans mqttSet = null;
		try {
			//获得操作对象
			rs = pstm.executeQuery();
			//循环整个列表，rs.next()返回false时表示数据处理完
			while(rs.next()) {
				//新建一个AdministratorBeans对象以存数据
				mqttSet = new MqttSetBeans();
				//根据字段名获取相关数据
				mqttSet.setId(rs.getString("clientid"));
				mqttSet.setIp(rs.getString("ip"));
				mqttSet.setLWTContext(rs.getString("lwt_context"));//遗言内容
				mqttSet.setLWTQos(rs.getString("lwt_qos"));//遗言质量
				mqttSet.setLWTTopic(rs.getString("lwt_topic"));//遗言主题
				mqttSet.setName(rs.getString("name"));//账号
				mqttSet.setPasswd(rs.getString("passwd"));//密码
				mqttSet.setPort(rs.getString("port"));//端口号
				mqttSet.setPubContext(rs.getString("pub_context"));//发布内容
				mqttSet.setPubQos(rs.getString("pub_qos"));//发布质量
				mqttSet.setPubTopic(rs.getString("pub_topic"));//发布主题
				mqttSet.setSubQos(rs.getString("sub_qos"));//订阅质量
				mqttSet.setSubTopic(rs.getString("sub_topic"));//订阅主题

				//把数据压入列表
				list.add(mqttSet);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally {
			//一定要做的事，释放连接
			MqttDbUtil.free(rs, pstm);
		}
		return list;
	}
}

