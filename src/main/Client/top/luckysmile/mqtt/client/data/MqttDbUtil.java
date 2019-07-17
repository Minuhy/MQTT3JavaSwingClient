package top.luckysmile.mqtt.client.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 * SQLite驱动相关，加载驱动，获取连接
 * @author minuy
 *
 */
public class MqttDbUtil {
	//声明连接数据库所需要的相关信息
	private static String driver = "org.sqlite.JDBC";//驱动名
	private static String url = "Mqtt.db";//文件路径

	private MqttDbUtil() {

	}

	//静态的方式加载JDBC驱动
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * 返回一个数据库链接
	 * @return
	 */
	public static Connection getConn() {
		Connection conn = null;
		try {
			//如果不存在就在当前目录下创建
			conn = DriverManager.getConnection("jdbc:sqlite:" + url);
		} catch (SQLException e) {
			System.out.println("数据库连接失败！");
			JOptionPane.showMessageDialog(null, "服务器连接失败");
			e.printStackTrace();
		}
		
		return conn;
	}

	/**
	 * 释放数据库连接信息
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

	//释放连接
	private static void FreeConnection(Connection conn){
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//释放资源
	private static void FreeResultSet(ResultSet rs){
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//释放操作对象
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
