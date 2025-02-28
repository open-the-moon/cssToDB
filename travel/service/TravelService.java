package com.multi.travel.service;

import com.multi.travel.common.DBConnectionMgr;
import com.multi.travel.dao.TravelDao;
import com.multi.travel.dto.Travel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TravelService {

    private final com.multi.travel.dao.TravelDao travelDao;
    Connection con = null;
    DBConnectionMgr dbcp;

    public TravelService() {
        dbcp = DBConnectionMgr.getInstance();

        if (dbcp.getConnectionCount() == 0) {
            try {
                dbcp.setInitOpenConnections(10);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        this.travelDao = new TravelDao();

    }

    public List<Travel> getTravelsByDistrict(String district) {
        Connection con = null;
        try {
            con = dbcp.getConnection();  // DB 연결 가져오기
            return travelDao.getTravelsByDistrict(district, con);  // Connection 넘겨주기
        } catch (Exception e) {
            throw new RuntimeException("권역별 관광지 조회 중 오류 발생", e);
        } finally {
            if (con != null) {
                try {
                    con.close();  // 연결 닫기
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}