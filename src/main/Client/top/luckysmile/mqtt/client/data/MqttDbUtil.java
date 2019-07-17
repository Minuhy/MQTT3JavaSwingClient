package top.luckysmile.mqtt.client.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 * SQLite������أ�������������ȡ����
 * @author minuy
 *
 */
public class MqttDbUtil {
	//�����������ݿ�����Ҫ�������Ϣ
	private static String driver = "org.sqlite.JDBC";//������
	private static String url = "Mqtt.db";//�ļ�·��

	private MqttDbUtil() {

	}

	//��̬�ķ�ʽ����JDBC����
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * ����һ�����ݿ�����
	 * @return
	 */
	public static Connection getConn() {
		Connection conn = null;
		try {
			//��������ھ��ڵ�ǰĿ¼�´���
			conn = DriverManager.getConnection("jdbc:sqlite:" + url);
		} catch (SQLException e) {
			System.out.println("���ݿ�����ʧ�ܣ�");
			JOptionPane.showMessageDialog(null, "����������ʧ��");
			e.printStackTrace();
		}
		
		return conn;
	}

	/**
	 * �ͷ����ݿ�������Ϣ
	 * @param rs
	 * @param pstm
	 * @param conn
	 */
	public static void free(ResultSet rs,Statement pstm,Connection conn) {
		FreeConnection(conn);
		FreeStatement(pstm);
		FreeResultSet(rs);
	}

	public static void free(Connection conn){
		FreeConnection(conn);
	}

	public static void free(ResultSet rs,Statement pstm) {
		FreeResultSet(rs);
		FreeStatement(pstm);
	}
	
	public static void free(ResultSet rs,Connection conn) {
		FreeResultSet(rs);
		FreeConnection(conn);
	}


	public static void free(Statement pstm,Connection conn) {
		FreeStatement(pstm);
		FreeConnection(conn);
	}

	//�ͷ�����
	private static void FreeConnection(Connection conn){
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//�ͷ���Դ
	private static void FreeResultSet(ResultSet rs){
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//�ͷŲ�������
	private static void FreeStatement(Statement pstm){
		if(pstm != null) {
			try {
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
