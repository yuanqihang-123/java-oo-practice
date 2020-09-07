package com.twu.person;

import com.twu.search.TopSearch;

import java.util.List;
import java.util.Scanner;

/**
 * @author yuan
 * @create 2020-09-07 11:12
 */
public class Admin extends Person{
    public Admin(String name) {
        super(name);
    }

    /**
     * 查看当前存在的热搜
     *
     * @param topSearchesList 热搜集合
     */
    public static void viewTopSearches(List<TopSearch> topSearchesList) {
        for (int i = 0; i < topSearchesList.size(); i++) {
            TopSearch topSearch = topSearchesList.get(i);
            System.out.println(i + 1 + " " + topSearch.getName() + " " + topSearch.getPopularity());
        }
    }

    /**
     * 添加普通热搜
     *
     * @param topSearchesList
     */
    public static void addTopSearch(List<TopSearch> topSearchesList) {
        Scanner s = new Scanner(System.in);

        //创建topSearch
        System.out.println("请输入你要添加的热搜事件的名字：");
        String tsName = s.nextLine();
        TopSearch topSearch = new TopSearch(tsName);
        //添加topSearch
        topSearchesList.add(topSearch);
        /*//给热搜的rank赋值
        topSearch.setRank(topSearchesList.size());*/
        //给出提示
        System.out.println("添加成功");
    }

    /**
     * 添加超级热搜
     * @param topSearchesList
     */
    public static void addSuperTopSearch(List<TopSearch> topSearchesList) {
        Scanner s = new Scanner(System.in);

        //创建topSearch
        System.out.println("请输入你要添加的超级热搜事件的名字：");
        String tsName = s.nextLine();
        TopSearch topSearch = new TopSearch(tsName,true);
        //添加topSearch
        topSearchesList.add(topSearch);
        /*//给热搜的rank赋值
        topSearch.setRank(topSearchesList.size());*/
        //给出提示
        System.out.println("添加成功");
    }
}
