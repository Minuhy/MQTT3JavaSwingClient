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
 * ���ݿ⽻��
 * @author minuy
 *
 */
public class MqttDAO {
	
	public boolean SaveSession(MqttMassageBeans massage) {
		boolean yes = false;
		SQLiteTest("mqtt_session");
		//���ݿ����
		String sql = "INSERT INTO \"main\".\"mqtt_session\" (\"time\", \"topic\", \"context\", \"qos\", \"other\") VALUES (?, ?, ?, ?, ?)";
		//������ݿ�����
		Connection conn = MqttDbUtil.getConn();
		//�����������
		PreparedStatement pstm = null;
		try {
			//��ò������
			pstm = conn.prepareStatement(sql);
			//ƴ�����
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
			
			//ִ�����
			yes = pstm.execute();

		} catch (SQLException e) {
			System.out.println(e.toString());
		}finally {
			//һ��Ҫ�����£��ͷ�����
			MqttDbUtil.free(pstm,conn);
		}
		
		return yes;
	}

	/**
	 * �������õ����ݿ���
	 * @param set ����
	 * @return �ɰ�
	 */
	public boolean SaveSet(MqttSetBeans set) {
		boolean yes = false;
		SQLiteTest("mqtt_set");
		String name = new GeneratePassword().get(8);
		//���ݿ����
		String sql = "INSERT INTO \"main\".\"mqtt_set\" (\"set_name\", \"clientid\", \"ip\", \"port\", \"name\", \"passwd\", \"lwt_topic\", \"lwt_qos\", \"lwt_context\", \"sub_topic\", \"sub_qos\", \"pub_topic\", \"pub_context\", \"pub_qos\") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		//������ݿ�����
		Connection conn = MqttDbUtil.getConn();
		//�����������
		PreparedStatement pstm = null;
		try {
			//��ò������
			pstm = conn.prepareStatement(sql);
			//ƴ�����
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
			
			//ִ�����
			yes = pstm.execute();

		} catch (SQLException e) {
			System.out.println(e.toString());
		}finally {
			//һ��Ҫ�����£��ͷ�����
			MqttDbUtil.free(pstm,conn);
		}
		
		return yes;
	}

	/**
	 * 20150416 �ж�sqlite���Ƿ���ڱ�
	 * author:zhouyun ,date:20150416
	 * @param dbFile db�ļ�ȫ·��
	 * @param tableName ��Ҫ��ѯ�ı���
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
			//һ��Ҫ�����£��ͷ�����
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
			//һ��Ҫ�����£��ͷ�����
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
		//���ݿ����
		String sql = "select * from mqtt_set";
		//������ݿ�����
		Connection conn = MqttDbUtil.getConn();
		//�����������
		PreparedStatement pstm = null;
		try {
			//��ò������
			pstm = conn.prepareStatement(sql);
			//����SQL����ȡ����
			list = getDataBySQL(pstm);
			//��ȡ
			if(list.size() > 0) {
				set = list.get(list.size()-1);
			}

		} catch (SQLException e) {
			System.out.println(e.toString());
		}finally {
			//һ��Ҫ�����£��ͷ�����
			MqttDbUtil.free(pstm,conn);
		}

		return set;
	}


	public List<MqttSetBeans> getDataBySQL(PreparedStatement pstm){
		List<MqttSetBeans> list = new ArrayList<MqttSetBeans>();
		//������������
		ResultSet rs = null;
		//����һ��BookInfoBeans����
		MqttSetBeans mqttSet = null;
		try {
			//��ò�������
			rs = pstm.executeQuery();
			//ѭ�������б�rs.next()����falseʱ��ʾ���ݴ�����
			while(rs.next()) {
				//�½�һ��AdministratorBeans�����Դ�����
				mqttSet = new MqttSetBeans();
				//�����ֶ�����ȡ�������
				mqttSet.setId(rs.getString("clientid"));
				mqttSet.setIp(rs.getString("ip"));
				mqttSet.setLWTContext(rs.getString("lwt_context"));//��������
				mqttSet.setLWTQos(rs.getString("lwt_qos"));//��������
				mqttSet.setLWTTopic(rs.getString("lwt_topic"));//��������
				mqttSet.setName(rs.getString("name"));//�˺�
				mqttSet.setPasswd(rs.getString("passwd"));//����
				mqttSet.setPort(rs.getString("port"));//�˿ں�
				mqttSet.setPubContext(rs.getString("pub_context"));//��������
				mqttSet.setPubQos(rs.getString("pub_qos"));//��������
				mqttSet.setPubTopic(rs.getString("pub_topic"));//��������
				mqttSet.setSubQos(rs.getString("sub_qos"));//��������
				mqttSet.setSubTopic(rs.getString("sub_topic"));//��������

				//������ѹ���б�
				list.add(mqttSet);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally {
			//һ��Ҫ�����£��ͷ�����
			MqttDbUtil.free(rs, pstm);
		}
		return list;
	}
}

