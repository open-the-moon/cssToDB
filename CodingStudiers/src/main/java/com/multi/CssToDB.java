package com.multi;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class CssToDB {

    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement pstmt = null;

        String path = "resource/travel.csv";
        String url = "jdbc:mysql://localhost:3306/travel";
        String username = "scott";
        String password = "tiger";
        String sql = "INSERT INTO TRAVEL VALUES(?, ?, ?, ?, ?, ?)";

        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            List<String[]> lists = reader.readAll();
            String[] list = null;

            for (int i = 1; i < lists.size(); i++) {
                pstmt = con.prepareStatement(sql);
                list = lists.get(i);
                pstmt.setInt(1, Integer.parseInt(list[0]));
                pstmt.setString(2, list[1]);
                pstmt.setString(3, list[2]);
                pstmt.setString(4, list[3]);
                pstmt.setString(5, list[4]);
                pstmt.setString(6, list[5]);

                pstmt.executeUpdate();
            }


        } catch (FileNotFoundException e) {
            System.err.println("CSV file not found: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException | CsvException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
