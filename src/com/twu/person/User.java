package com.twu.person;

import com.twu.myException.MyException;
import com.twu.search.TopSearch;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author yuan
 * @create 2020-09-07 11:12
 */
public class User extends Person {
    private int votes;

    public User(String name, int votes) {
        super(name);
        this.votes = votes;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
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
     * 购买热搜
     *
     * @param buyTotSearchesList
     * @param topSearchesList    普通热搜列表，从这里面购买热搜
     */
    public static void buyTopSearch(List<TopSearch> buyTotSearchesList, List<TopSearch> topSearchesList) throws MyException {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入你要购买的热搜名称：");
        String tsName = s.nextLine();
        System.out.println("请输入你要购买的热搜排名：");
        int tsRank = s.nextInt();
        System.out.println("请输入你要购买的热搜金额：");
        int tsMoney = s.nextInt();

        if (tsRank < 1 || tsRank > topSearchesList.size() + 1) {
            System.out.println("排名输入有误");
            throw new MyException("排名输入有误");
        }
        if (tsMoney < 0) {
            System.out.println("金额输入有误");
            throw new MyException("金额输入有误");
        }
        //判断此热搜是否存在
        List<TopSearch> collect = topSearchesList.stream().filter(item -> item.getName().equals(tsName)).collect(Collectors.toList());
        if (collect.size() > 0) {
            //先查看此排名上的热搜是否已经是被购买的热搜
            List<TopSearch> beBuyList = buyTotSearchesList.stream().filter(item -> item.getRank() == tsRank).collect(Collectors.toList());
            int size = beBuyList.size();


            TopSearch willBuyTopSearch = collect.get(0);


            if (size > 0) {
                //  tsRank上的热搜已被购买
                TopSearch willDeleteTopSearch = beBuyList.get(0);
                int money = willDeleteTopSearch.getMoney();
                //如果这次被购买的金额小于上次，则购买失败
                if (tsMoney <= money) {
                    System.out.println("次排名的上一次购买金额为" + money + "，你的金额太少，购买失败");
                    throw new MyException("金额太少，购买失败");
                }
                //金额足够，则执行操作
                //删除原被购买的热搜
                buyTotSearchesList.remove(willDeleteTopSearch);
                topSearchesList.remove(willDeleteTopSearch);
                //添加新热搜替代之前被删除的位置，一次add一次remove
                willBuyTopSearch.setRank(tsRank);
                willBuyTopSearch.setMoney(tsMoney);
                buyTotSearchesList.add(willBuyTopSearch);
                topSearchesList.remove(willBuyTopSearch);
                System.out.println("购买成功");
                return;
            } else {
                // tsRank上的热搜未被购买

                //直接将被购买的热搜放到buyTotSearchesList中
                willBuyTopSearch.setRank(tsRank);
                willBuyTopSearch.setMoney(tsMoney);
                // 一次add一次remove
                buyTotSearchesList.add(willBuyTopSearch);
                //从topSearchesList中删除此热搜
                topSearchesList.remove(willBuyTopSearch);
                System.out.println("购买成功");
                return;
            }
        }
        System.out.println("输入的热搜不存在");
        throw new MyException("输入的热搜不存在");
    }

    /**
     * 给热搜事件投票
     *
     * @param topSearchesList 热搜集合
     * @param user
     */
    public static void voteForTopSearch(List<TopSearch> topSearchesList, User user) throws MyException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你要投票的热搜名称：");
        String tsName = scanner.nextLine();
        //如果输入的名称不正确则给出错误信息
        List<TopSearch> collect = topSearchesList.stream().filter(item -> item.getName().equals(tsName)).collect(Collectors.toList());
        long count = collect.size();
        if (count > 0) {
            int userVotesCount = user.getVotes();
            System.out.println("请输入你要投票的热搜票数：（你目前还有：" + userVotesCount + "）");
            int voteCount = scanner.nextInt();
            if (voteCount < 0 || voteCount > userVotesCount) {
                System.out.println("投票失败");
                throw new MyException("投票数不规范，投票失败");
            }
            //给tsName投voteCount票
            TopSearch topSearch = collect.get(0);
            //区分是否为超级热搜
            if (topSearch.isSuper() == true) {
                //是超级热搜,则是2倍投票数
                topSearch.setPopularity(voteCount * 2 + topSearch.getPopularity());
            } else {
                //普通热搜，投多少是多少
                topSearch.setPopularity(voteCount + topSearch.getPopularity());
            }
            //更新用户票数
            user.setVotes(userVotesCount - voteCount);
            //提示
            System.out.println("投票成功");
            return;
        }
        //给出错误信息
        System.out.println("热搜不存在，请重新输入");
        throw new MyException("热搜不存在");
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
}
