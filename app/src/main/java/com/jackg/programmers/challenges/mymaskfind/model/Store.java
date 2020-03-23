package com.jackg.programmers.challenges.mymaskfind.model;

import java.io.Serializable;

public class Store implements Serializable, Comparable {

    private String code,            // 식별 코드
                    name,           // 이름
                    addr,           // 주소
                    type,           // 판매처 유형[약국: '01', 우체국: '02', 농협: '03']
                    stock_at,       // 입고시간
                    remain_stat,    // 재고 상태[100개 이상(녹색): 'plenty' / 30개 이상 100개미만(노랑색): 'some' / 2개 이상 30개 미만(빨강색): 'few' / 1개 이하(회색): 'empty']
                    created_at,     // 데이터 생성 일자
                    dist;           // 거리

    private float lat, lng;         // 좌표[ lat = 위도, lng = 경도]

    private int remain;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStock_at() {
        return stock_at;
    }

    public void setStock_at(String stock_at) {
        this.stock_at = stock_at;
    }

    public String getRemain_stat() {
        return remain_stat;
    }

    public void setRemain_stat(String remain_stat) {
        this.remain_stat = remain_stat;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    @Override
    public int compareTo(Object o) {
        String numStr1 = this.getDist().split(" ")[0];
        String numStr2 = ((Store)o).getDist().split(" ")[0];

        int num1 = Integer.parseInt(numStr1);
        int num2 = Integer.parseInt(numStr2);

        return Integer.compare(num1, num2);
    }
}
