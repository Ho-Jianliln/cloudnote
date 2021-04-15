package controller;

import entity.Note;
import entity.User;
import service.NoteList;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class Maincontroller {
    public static User user=new User();//当前用户的信息
    public static Note mynote =new Note();//“我的笔记”选中的笔记信息
    public static Note sharenote=new Note();//“分享空间”选中的笔记信息
    public static NoteList nl=new NoteList();//提供笔记列表的服务类
    public static int choice =0;//1:打开选中的mynote 2:打开选中的sharenote
    public static Set<Integer> liked_noteid;//当前用户点赞记录
    public static int divide_group2id=0;//选择的分组
    public static User choseUser =new User();//管理员选中的用户
}
