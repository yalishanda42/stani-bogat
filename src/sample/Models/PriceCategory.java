package sample.Models;

public enum PriceCategory {
    LVL_50(50),
    LVL_100(100),
    LVL_200(200),
    LVL_300(300),
    LVL_500(500),
    LVL_700(700),
    LVL_1000(1_000),
    LVL_1500(1_500),
    LVL_2000(2_000),
    LVL_2500(2_500),
    LVL_5000(5_000),
    LVL_10000(10_000),
    LVL_15000(15_000),
    LVL_20000(20_000),
    LVL_100000(10_0000);

    PriceCategory(int price) {
    }
}
