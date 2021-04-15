package controller;

import bean.NoteBean;
import dao.NoteDao;
import dao.UserDao;
import entity.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.AccountManage;
import service.Announcement;
import service.Comment;
import service.Like;
import view.Alert;
import view.Stagechange;

import java.sql.SQLException;
import java.util.List;

public class AdminSurface extends Maincontroller{

    @FXML
    private TextField searchby_title2;

    @FXML
    private TextArea commenfield;

    @FXML
    private TableColumn<?, ?> author_column2;

    @FXML
    private TableColumn<?, ?> like_column2;

    @FXML
    private TableColumn<?, ?> lasttime_column2;

    @FXML
    private ListView<?> annoulist;

    @FXML
    private ListView<?> commentlist;

    @FXML
    private Label resetPasstips;

    @FXML
    private TableView<User> userListView;

    @FXML
    private TextArea announceContext;

    @FXML
    private TextField newPassField;

    @FXML
    private TableColumn<?, ?> title_column2;

    @FXML
    private TextField searchby_author2;
    @FXML
    private TableColumn<?, ?> usernameCol;
    @FXML
    private TableColumn<?, ?> blackListCol;
    @FXML
    private TableColumn<?, ?> lasttimeCol;
    @FXML
    private TableColumn<?, ?> useridCol;
    @FXML
    private TableView<NoteBean> share_notelist;
    @FXML
    private TextField usernameField;

    @FXML
    private TextField announceTitle;

    @FXML
    private TextField oldPassField;
    ObservableList<User> userObservableList= FXCollections.observableArrayList();
    public void initialize() throws SQLException {
        Comment.load(commentlist);
        Like.load();
        nl.load2(share_notelist,searchby_author2,searchby_title2,author_column2,like_column2,lasttime_column2, title_column2);
        userOList_refresh();
        useridCol.setCellValueFactory(new PropertyValueFactory<>("id"));//用户列表初始化
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lasttimeCol.setCellValueFactory(new PropertyValueFactory<>("lasttime"));
        blackListCol.setCellValueFactory(new PropertyValueFactory<>("blacklist"));
        userListView.setItems(userObservableList);//用户列表选择监听
        userListView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends User> observable, User oldValue, User newValue)->{
                    choseUser.setId(newValue.getId());
                }
        );
        usernameField.textProperty().addListener(new ChangeListener<String>() {//用户列表搜索框监听,为空则刷新为所有笔记
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("")&&usernameField.getText().equals("")){
                    try {
                        userOList_refresh();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
    }
    public void userOList_refresh() throws SQLException {
        UserDao userdao=new UserDao();
        List<User> userList=userdao.findusers("");
        userObservableList.clear();
        for(int i=0;i<userList.size();i++){
            userObservableList.add(userList.get(i));
        }
        userdao.closeAll();
    }
    @FXML
    void setBL1(ActionEvent event) {//设置拉黑
        UserDao userdao =new UserDao();
        int judge=userdao.set_black(choseUser.getId());
        if (judge == 1) {//更新用户列表
            for(int i=0;i<userObservableList.size();i++){
                if(userObservableList.get(i).getId()==choseUser.getId())
                    userObservableList.get(i).setBlacklist("yes");
            }
            Alert.send("拉黑成功");
        }
        else Alert.send("拉黑失败");
        userdao.closeAll();
    }

    @FXML
    void offsetBL1(ActionEvent event) {//取消拉黑
        UserDao userdao =new UserDao();
        int judge=userdao.offset_black(choseUser.getId());
        if (judge == 1) {//更新用户列表
            for(int i=0;i<userObservableList.size();i++){
                if(userObservableList.get(i).getId()==choseUser.getId())
                    userObservableList.get(i).setBlacklist("");
            }
            Alert.send("取消拉黑成功");
        }
        else Alert.send("取消拉黑失败");
        userdao.closeAll();
    }

    @FXML
    void search2(ActionEvent event) throws SQLException {//分享空间搜索
        nl.list2_refresh2(searchby_author2.getText(),searchby_title2.getText());
    }

    @FXML
    void opennote2(ActionEvent event) {
        choice =2;
        Stagechange.goto_opennote();
    }

    @FXML
    void give_like(ActionEvent event) {
        choice=2;
        Like.setLike(nl.observablenotelist2);
    }

    @FXML
    void setBL2(ActionEvent event) {
        setBL1(event);
    }

    @FXML
    void deleNote(ActionEvent event) {
        NoteDao notedao =new NoteDao();
        if(notedao.delenote(sharenote.getNoteid())==1){
            for(int i=0;i<nl.observablenotelist2.size();i++){
                if(nl.observablenotelist2.get(i).getNoteid()==sharenote.getNoteid()){
                    nl.observablenotelist2.remove(i);
                }
            }
        }
        notedao.closeAll();
    }

    @FXML
    void offsetBL2(ActionEvent event) {
        offsetBL1(event);
    }
    @FXML
    void searchName(ActionEvent event) throws SQLException {
        String username=usernameField.getText();
        UserDao userdao=new UserDao();
        List<User> userList=userdao.findusers(username);
        userObservableList.clear();
        for(int i=0;i<userList.size();i++){
            userObservableList.add(userList.get(i));
        }
        userdao.closeAll();
    }

    @FXML
    void sendAnnounce(ActionEvent event) {
        Announcement.add(user,announceTitle.getText(),announceContext.getText());
    }

    @FXML
    void send_comment(ActionEvent event) {
        Comment.addComment(user,commenfield.getText());
    }

    @FXML
    void resetPass(ActionEvent event) throws SQLException {
        int judge= AccountManage.resetPass(oldPassField.getText(),newPassField.getText());
        if(judge==0){
            resetPasstips.setText("旧密码错误");
        }else {
            resetPasstips.setText("密码修改成功");
        }
    }

}
