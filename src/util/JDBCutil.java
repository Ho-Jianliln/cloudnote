package util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCutil {
    private static DataSource ds;
    static{
        Properties properties=new Properties();
        try {
            properties.load(new FileReader("JDBC.properties"));
            ds= DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    public static void close(ResultSet rs, Statement stmt,Connection cn) throws SQLException {
        if(rs!=null){
            rs.close();
        }
        if(stmt!=null){
            stmt.close();
        }
        if(cn!=null){
            cn.close();
        }
    }
    public static void close(Statement stmt,Connection cn) throws SQLException {
        close(null,stmt,cn);
    }
}
