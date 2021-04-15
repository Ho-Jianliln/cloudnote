package service;

import bean.MessageBean;
import dao.MessageDao;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class Announcement {
    private static ObservableList<String> announceList= FXCollections.observableArrayList();
    public static void load(User user, ListView listView) throws SQLException {//加载公告列表
        MessageDao md=new MessageDao();
        List<MessageBean> partList=md.find_announce(user.getLasttime());
        for(int i=0;i<partList.size();i++){
            display(partList.get(i).getTitle(),partList.get(i).getContext());
        }
        List<MessageBean> allList=md.find_allannounce();
        for(int i=0;i<allList.size();i++){
            announceList.add(allList.get(i).getTitle()+"                    "+allList.get(i).getTime()+"\n"+allList.get(i).getContext());
        }
        listView.setItems(announceList);
        md.closeAll();
    }
    public static void display(String title , String message){//显示公告弹窗
        Stage window = new Stage();
        window.setTitle("公告:"+title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);
        window.setMinHeight(150);
        Button button = new Button("知道了");
        button.setOnAction(e -> window.close());
        Label label = new Label(message);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label , button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
    public static void add(User user,String title,String context){//添加公告
        MessageDao messagedao=new MessageDao();
        if(messagedao.add_announce(user,title,context)==1){
            announceList.add(title+"\n"+context);
        }
        messagedao.closeAll();
    }
}
