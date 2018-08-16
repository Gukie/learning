package com.lokia.jdbc;

import com.lokia.DateTimeUtils;

import java.sql.*;
import java.util.Date;
import java.util.Properties;

/**
 * refer:
 * 1. http://chenjc-it.iteye.com/blog/1455688
 * 2. http://www.vogella.com/tutorials/MySQLJava/article.html
 * <p>
 * <p>
 * 主要需要做以下几件事：
 * 1. 加载 数据库的driver
 * 2. 建立连接
 * 3. 创建Statement，执行对应的SQL(每一个Statement为一次数据库执行请求)
 * 4. 释放资源
 * </p>
 *
 * @author gushu
 * @data 2018/8/16
 */
public class JdbcOperationTest {

    static String url = "jdbc:mysql://localhost:3306/building";
    static String user = "root";
    static String password = "root";

    public static void main(String[] args) {

        Connection conn = null;
        ResultSet resultSet = null;
        Statement selectStatement = null;
        PreparedStatement updateStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties properties = new Properties();
            properties.put("user",user);
            properties.put("password",password);
            /**
             *需要设置 timezone，否则会报： Caused by: com.mysql.cj.exceptions.InvalidConnectionAttributeException: The server time zone value 'ÖÐ¹ú±ê×¼Ê±¼ä' is unrecognized or represents more than one time zon
             *
             * 参数的名字可以从 com.mysql.cj.conf.PropertyDefinitions#PNAME_serverTimezone 知道
             */
            properties.put("serverTimezone","GMT+08");
            conn = DriverManager.getConnection(url.trim(), properties); // 使用 url.trim的方式，以免url中含有空格，造成找半天找不到的问题所在的囧况



            // select
            selectStatement = conn.createStatement();
            selectStatement.execute("SELECT * FROM building ORDER BY gmt_created DESC limit 10");

            resultSet = selectStatement.getResultSet();
            printResultSet(resultSet);


            // update
            String id = "BD15101103675353";
            updateStatement = conn.prepareStatement("UPDATE building SET gmt_modified = NOW() WHERE id = ?");
            updateStatement.setString(1,id);
            int affected = updateStatement.executeUpdate();
            System.out.println("updated rows:"+affected);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (selectStatement != null) {
                    selectStatement.close();
                }
                if(updateStatement !=null){
                    updateStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String location = resultSet.getString("location");
                Date gmtCreated = resultSet.getDate("gmt_created");

                System.out.println(id + "," + name + "," + location + "," + DateTimeUtils.getFormattedStr(gmtCreated));
            }
        }
    }
}
