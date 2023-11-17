package com.ssafy.rollinghealer.place.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class AreaBasedList1ApiQueryParams {
    private int numOfRows=10; // 한 페이지 결과 수
    private int pageNo=1; // 페이지 번호
    private String mobileOS="ETC"; // OS 구분
    private String mobileApp="rolling"; // 서비스명(어플명)
    private String _type="json"; // 응답 메세지 형식
    private String listYN; // 목록 구분
    private String arrange="A"; // 정렬 구분
    private String contentTypeId; // 관광 타입 ID
    private String areaCode="1"; // 지역 코드
    private String sigunguCode; // 시군구 코드
    private String cat1; // 대분류
    private String cat2; // 중분류
    private String cat3; // 소분류
    private String modifiedtime; // 수정 시간
}