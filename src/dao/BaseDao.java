package dao;


import util.JDBCutil;

import java.sql.*;

public class BaseDao {
    protected static Connection conn = null;
    protected static PreparedStatement pstmt = null;
    protected static ResultSet rs = null;


    public static void getConnection() {

        try {
            conn = JDBCutil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeAll() {

        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //通用增删改
    public int executeUpdateSQL(String sql, Object[] param) {
        int num = 0;
        try {
            getConnection();
            pstmt = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setObject(i + 1, param[i]);
                }
            }
            num = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return num;
    }
    //通用查询
    public static ResultSet executeQuerySQL(String sql, Object[] param) throws SQLException {
        try {
            getConnection();
            pstmt = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setObject(i + 1, param[i]);
                }
            }
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}

