package service;

import bean.NoteBean;
import controller.Maincontroller;
import dao.LikeDao;
import javafx.collections.ObservableList;
import view.Alert;

import java.sql.SQLException;

public class Like extends Maincontroller {
    public static void load() throws SQLException {//加载当前用户点赞记录
        LikeDao likedao =new LikeDao();
        liked_noteid=likedao.findliked(user.getId());
    }
    public static void setLike(ObservableList<NoteBean> observableList){
        LikeDao likedao =new LikeDao();
        if(choice==2&&sharenote.getAuthor_id()!= user.getId()){//判断点赞对象是否为本人
            if(liked_noteid.contains(sharenote.getNoteid())){//查询是否有点赞记录
                likedao.like_cancel(sharenote.getNoteid(), user.getId());
                liked_noteid.remove(sharenote.getNoteid());//更新点赞记录
                for(int i=0;i<observableList.size();i++){//更新笔记列表点赞数据
                    if(observableList.get(i).getNoteid()==sharenote.getNoteid()){
                        int pre=observableList.get(i).getLike_count();
                        observableList.get(i).setLike_count(pre-1);
                    }
                }
            }else{
                likedao.like(sharenote.getNoteid(), user.getId());
                liked_noteid.add(sharenote.getNoteid());
                for(int i=0;i<observableList.size();i++){
                    if(observableList.get(i).getNoteid()==sharenote.getNoteid()){
                        int pre=observableList.get(i).getLike_count();
                        observableList.get(i).setLike_count(pre+1);
                    }
                }
            }
        }else{
            Alert.send("不能给自己点赞");
        }
        likedao.closeAll();
    }
}
