package service;

import bean.MessageBean;
import dao.MessageDao;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.List;

public class Comment {
    private static ObservableList<String> commentList= FXCollections.observableArrayList();
    public static void load(ListView commentListview) throws SQLException {//加载评论列表
        MessageDao md =new MessageDao();
        List<MessageBean> messageList=md.find_comment();
        for(int i=0;i<messageList.size();i++){
            commentList.add(messageList.get(i).getAuthor()+"      "+messageList.get(i).getTime()+"\n"+messageList.get(i).getContext());
        }
        commentListview.setItems(commentList);
        md.closeAll();
    }
    public static void addComment(User user,String context){//发送评论
        MessageDao md =new MessageDao();
        md.add_comment(user, context);
        commentList.add(user.getName()+"\n"+context);
    }
}
