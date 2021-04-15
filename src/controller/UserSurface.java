package controller;
import bean.NoteBean;
import dao.NoteDao;
import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import service.*;
import view.Stagechange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class UserSurface extends Maincontroller{
    @FXML
    private TextArea commenfield;
    @FXML
    private Label resetPasstips;
    @FXML
    private TextField newPassField;
    @FXML
    private TextField oldPassField;
    @FXML
    public TextField searchby_author2;
    @FXML
    public TextField searchby_title2;
    @FXML
    public TextField searchby_title1;
    @FXML
    private ListView<?> commentlist;
    @FXML
    private ListView<?> annoulist;
    @FXML
    private TreeView<String> mynotetree;
    @FXML
    private TableView<NoteBean> current_notelist;
    @FXML
    private TableView<NoteBean> share_notelist;
    @FXML
    public Label currenttips;
    @FXML
    public TableColumn<NoteBean,String> like_column1;
    @FXML
    public TableColumn<?, ?> title_column1;
    @FXML
    public TableColumn<?, ?> lasttime_column1;
    @FXML
    public TableColumn<?, ?> author_column2;
    @FXML
    public TableColumn<?, ?> like_column2;
    @FXML
    public TableColumn<?, ?> lasttime_column2;
    @FXML
    public TableColumn<?, ?> title_column2;

    private NoteTree nt=new NoteTree();
    public void initialize() throws SQLException {
        nt.load(user.getId(), mynotetree);//我的笔记树状图初始化
        nl.load1( current_notelist, searchby_title1, title_column1,  lasttime_column1, like_column1, currenttips);//笔记列表初始化
        nl.load2(share_notelist,searchby_author2,searchby_title2,author_column2,like_column2,lasttime_column2, title_column2);
        Announcement.load(user,annoulist);
        Like.load();
        UserDao userdao=new UserDao();
        userdao.updatetime(user.getId());//更新用户最新登录时间
        userdao.closeAll();
        Comment.load(commentlist);
    }

    @FXML
    void search1(ActionEvent event) throws SQLException, InterruptedException {//搜索我的笔记
        if(searchby_title1.getText().equals("")){
            currenttips.setText("近期笔记");
            nl.list1_refresh();
        }else{
            currenttips.setText("");
            nl.list1_refresh2(searchby_title1.getText());
        }
    }
    @FXML
    void search2(ActionEvent event) throws SQLException {
        nl.list2_refresh2(searchby_author2.getText(),searchby_title2.getText());
    }
    @FXML
    void give_like(ActionEvent event) {
        choice=2;
        Like.setLike(nl.observablenotelist2);
    }
    @FXML
    void add_note(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(Stagechange.class.getResource("AddNote.fxml")));
        stage.setTitle("添加笔记");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    nl.list1_refresh();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    @FXML
    void modifi_note(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(Stagechange.class.getResource("ModifiNote.fxml")));
        stage.setTitle("修改笔记");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    nl.list1_refresh();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    @FXML
    void dele_note(ActionEvent event) throws SQLException {
        NoteDao notedao=new NoteDao();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"请确认是否删除",new ButtonType("取消", ButtonBar.ButtonData.NO),
                new ButtonType("确定", ButtonBar.ButtonData.YES));
        alert.setTitle("确认");
        Optional<ButtonType> _buttonType = alert.showAndWait();
        if(_buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)){
            if(notedao.delenote(mynote.getNoteid())==1){
                view.Alert.send("删除成功");
            }else{
                view.Alert.send("删除失败");
            }
        }
        nl.list1_refresh();
    }

    @FXML
    void opennote(ActionEvent event) {
        choice =1;
        Stagechange.goto_opennote();
    }
    @FXML
    void opennote2(ActionEvent event) {
        choice =2;
        Stagechange.goto_opennote();
    }

    @FXML
    void send_comment(ActionEvent event) {
        Comment.addComment(user,commenfield.getText());
    }
    @FXML
    void resetPass(ActionEvent event) throws SQLException {
        int judge=AccountManage.resetPass(oldPassField.getText(),newPassField.getText());
        if(judge==0){
            resetPasstips.setText("旧密码错误");
        }else {
            resetPasstips.setText("密码修改成功");
        }
    }
}
