package com.twu;

import com.twu.myException.MyException;
import com.twu.person.Admin;
import com.twu.person.User;
import com.twu.search.TopSearch;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    //    当前使用系统的用户
    private static List<User> persons = new ArrayList<>();
    //    存放所有热搜的数组
    private static List<TopSearch> topSearchesList = new ArrayList<>();
    // 存放被购买热搜的数组
    private static List<TopSearch> buyTotSearchesList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (true) {
            //用户选择循环:
            System.out.println("欢迎来到热搜排行榜，你是？\n1.用户\n2.管理员\n3.退出");
            int userChoose = s.nextInt();
            if (userChoose == 3) break;
            switch (userChoose) {
                case 1:
                    userFunction();
                    break;
                case 2:
                    adminFunction();
                    break;
            }

        }
        //当用户点击退出时
        System.out.println("谢谢使用热搜排行榜！");
        System.exit(0);
    }


    private static void userFunction() {
        Scanner s = new Scanner(System.in);

        //通过昵称进行用户的区分(区分用户剩余票数)
        System.out.println("请输入你的昵称：");
        String nickName = s.nextLine();

        User user = null;
        //记录普通用户信息
        if (persons.size() == 0) {
            user = new User(nickName, 10);
            persons.add(user);
        } else {
            List<User> collect = persons.stream().filter(item -> item.getName().equals(nickName)).collect(Collectors.toList());
            if (collect.size() > 0) {
                //说明找到了
                user = collect.get(0);
            } else {
                user = new User(nickName, 10);
                persons.add(user);
            }

        }


        //程序开始
        while (true) {
            System.out.println("您好，" + nickName + "，您可以：\n1.查看热搜排行榜\n2.给热搜事件投票\n3.购买热搜\n4.添加热搜\n5.退出");
            int userevent = s.nextInt();
            if (userevent == 5) break;
            switch (userevent) {
                case 1:
                    User.viewTopSearches(topSearchesList);
                    break;
                case 2:
                    try {
                        User.voteForTopSearch(topSearchesList, user);
                    } catch (MyException e) {
                        //这里的异常为输入不规范导致，避免操作未成功时还调用sortTopSearches方法，故这里啥也不做
                        break;
                    }
                    //没有异常才进行排序操作
                    sortTopSearches(buyTotSearchesList, topSearchesList);
                    break;
                case 3:
                    try {
                        User.buyTopSearch(buyTotSearchesList, topSearchesList);
                    } catch (MyException e) {
                        //同上
                        break;
                    }
                    //没有异常才进行排序操作
                    sortTopSearches(buyTotSearchesList, topSearchesList);
                    break;
                case 4:
                    User.addTopSearch(topSearchesList);
                    break;
            }
        }

    }


    private static void adminFunction() {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入你的昵称：");
        String nickName = s.nextLine();
        System.out.println("请输入你的密码：");
        String pwd = s.nextLine();
        if (!(nickName.equals("admin") && pwd.equals("123"))) {
            System.out.println("用户名或者密码错误");
            return;
        }
        while (true) {
            System.out.println("您好，admin，您可以：\n1.查看热搜排行榜\n2.添加热搜\n3.添加超级热搜\n4.退出");
            int adminevent = s.nextInt();
            if (adminevent == 4) break;
            switch (adminevent) {
                case 1:
                    Admin.viewTopSearches(topSearchesList);
                    break;
                case 2:
                    Admin.addTopSearch(topSearchesList);
                    break;
                case 3:
                    Admin.addSuperTopSearch(topSearchesList);
                    break;

            }
        }
    }

    /**
     * 对热搜进行排序
     *
     * @param buyTotSearchesList 被购买的热搜
     * @param topSearchesList    普通热搜
     */
    private static void sortTopSearches(List<TopSearch> buyTotSearchesList, List<TopSearch> topSearchesList) {

        List<TopSearch> collect = new ArrayList<>();
        //对普通的热搜排序
        if (topSearchesList.size() > 0) {
            //根据热度进行排序
            collect = topSearchesList.stream().sorted(Comparator.comparing(TopSearch::getPopularity).reversed()).collect(Collectors.toList());

        }
        //把购买的热搜插入到相应的排名位置
        for (int i = 0; i < buyTotSearchesList.size(); i++) {
            TopSearch topSearch = buyTotSearchesList.get(i);
            // 如topSearchesList有三个元素，但是rank可以是1，2，3，4
            int index = topSearch.getRank() - 1;
            if (index < collect.size()) {
                collect.add(index, topSearch);
            } else {
                //在末尾插
                collect.add(topSearch);
            }

        }
        Main.topSearchesList = collect;
    }
}
