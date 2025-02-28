package com.multi.travel.controller;

import com.multi.travel.service.TravelService;
import com.multi.travel.dto.Travel;

import java.util.List;

public class TravelController {

    private TravelService travelService = new TravelService();

    // 권역별 관광지 조회 메서드
    public void showTravelsByDistrict(String district) {
        List<Travel> travelList = travelService.getTravelsByDistrict(district);

        if (travelList.isEmpty()) {
            System.out.println("해당 권역에 등록된 관광지가 없습니다.");
        } else {
            System.out.println("\n=== " + district + " 지역 관광지 목록 ===");
            for (Travel travel : travelList) {
                System.out.println("[" + travel.getNo() + "] " + travel.getTitle() + " - " + travel.getAddress());
            }
        }
    }
}
