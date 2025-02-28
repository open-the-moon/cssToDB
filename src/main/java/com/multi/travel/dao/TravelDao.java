package com.multi.travel.dao;

import com.multi.travel.dto.Travel;
import com.multi.travel.common.DBConnectionMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TravelDao {

    private DBConnectionMgr pool = DBConnectionMgr.getInstance();

    // Connection을 직접 생성하지 않고, Service에서 전달받은 Connection을 사용하도록 변경
    public List<Travel> getTravelsByDistrict(String district, Connection con) {
        List<Travel> travelList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT no, district, title, description, address, phone FROM travel WHERE district = ?";  // SQL문 작성

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, district);  // ?에 district 값 넣기
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Travel travel = new Travel();
                travel.setNo(rs.getInt("no"));
                travel.setDistrict(rs.getString("district"));
                travel.setTitle(rs.getString("title"));
                travel.setDescription(rs.getString("description"));
                travel.setAddress(rs.getString("address"));
                travel.setPhone(rs.getString("phone"));
                travelList.add(travel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // ResultSet과 PreparedStatement 닫기 (Connection은 서비스에서 관리하므로 닫지 않음)
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return travelList;
    }
}