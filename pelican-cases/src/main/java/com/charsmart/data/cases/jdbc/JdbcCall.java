package com.charsmart.data.cases.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author: Wonder
 * @Date: Created on 2021/12/30 下午3:33
 */
public class JdbcCall {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://39.98.36.134:22100/aps-dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";
        try (Connection conn = DriverManager.getConnection(url, "root", "shac");) {
            System.out.println("连接数据库>>>>>");
            try (Statement stmt = conn.createStatement();) {
                ResultSet resultSet = stmt.executeQuery("select user_name from sys_user limit 1");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("user_name"));
                }
                resultSet.close();
            }

        }

    }
}
