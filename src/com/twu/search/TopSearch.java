package com.twu.search;


public class TopSearch {
//    排名，名字，热度，被购买的标志(处于buyTotSearchesList中)，被购买的金额，是否超级热搜，
    private int rank = 0;   //排名，普通热搜push时会给rank赋值，超级热搜创建时赋值
    private String name;    //名字
    private int popularity = 0; //热度

    private int money = 0;  //被购买的金额
    private boolean isSuper = false;    //是否超级热搜

    public TopSearch(String name) {
        this.name = name;
    }

    public TopSearch(String name, boolean isSuper) {
        this.name = name;
        this.isSuper = isSuper;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isSuper() {
        return isSuper;
    }

    public void setSuper(boolean aSuper) {
        isSuper = aSuper;
    }

}