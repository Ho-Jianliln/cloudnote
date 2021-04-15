package service;

import bean.NoteBean;
import controller.Maincontroller;
import dao.NoteDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class NoteList extends Maincontroller{
    public static ObservableList<NoteBean> observablenotelist1 = FXCollections.observableArrayList();//我的笔记列表
    public static ObservableList<NoteBean> observablenotelist2 = FXCollections.observableArrayList();//分享笔记列表
    private static NoteDao notedao = new NoteDao();
    public void load1(TableView<NoteBean> current_notelist, TextField searchby_title1, TableColumn<?, ?> title_column1, TableColumn<?, ?> lasttime_column1,
                      TableColumn<NoteBean,String> like_column1, Label currenttips) throws SQLException {//近期笔记列表初始化
        list1_refresh();
        title_column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        like_column1.setCellValueFactory(new PropertyValueFactory<>("like_count"));
        lasttime_column1.setCellValueFactory(new PropertyValueFactory<>("lasttime"));
        current_notelist.setItems(observablenotelist1);
        current_notelist.getSelectionModel().selectedItemProperty().addListener(//笔记列表选择监听
                (ObservableValue<?extends NoteBean> observable, NoteBean oldValue, NoteBean newValue) -> {
                    mynote.setNoteid(newValue.getNoteid());
                }
        );

        searchby_title1.textProperty().addListener(new ChangeListener<String>() {//我的笔记搜索框监听,为空则刷新为最近笔记
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("")){
                    currenttips.setText("近期笔记");
                    try {
                        list1_refresh();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
    }
    public void load2(TableView<NoteBean> share_notelist, TextField searchby_author2, TextField searchby_title2,TableColumn<?, ?>author_column2, TableColumn<?, ?>like_column2, TableColumn<?, ?> lasttime_column2, TableColumn<?, ?> title_column2) throws SQLException {
        list2_refresh();//分享笔记列表初始化
        author_column2.setCellValueFactory(new PropertyValueFactory<>("author"));
        title_column2.setCellValueFactory(new PropertyValueFactory<>("title"));
        like_column2.setCellValueFactory(new PropertyValueFactory<>("like_count"));
        lasttime_column2.setCellValueFactory(new PropertyValueFactory<>("lasttime"));
        share_notelist.setItems(observablenotelist2);
        share_notelist.getSelectionModel().selectedItemProperty().addListener(//分享笔记列表选择监听
                (ObservableValue<?extends NoteBean> observable, NoteBean oldValue, NoteBean newValue) -> {
                    sharenote.setNoteid(newValue.getNoteid());
                    choseUser.setId(newValue.getAuthor_id());
                }
        );
        searchby_title2.textProperty().addListener(new ChangeListener<String>() {//分享笔记搜索框监听,为空则刷新为所有笔记
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("")&&searchby_author2.getText().equals("")){
                    try {
                        list2_refresh();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
    }
    public static void list1_refresh() throws SQLException {//刷新observablelist
        List<NoteBean> notelist = notedao.search(user.getId(), "", "", "");
        observablenotelist1.clear();
        for (int i = 0; i < notelist.size(); i++) {
            observablenotelist1.add(notelist.get(i));
        }
    }

    public static void list1_refresh2(String title) throws SQLException, InterruptedException {//按标题刷新observablelist
        List<NoteBean> notelist = notedao.search(user.getId(), "", title, "");
        observablenotelist1.clear();
        for (int i = 0; i < notelist.size(); i++) {
            observablenotelist1.add(notelist.get(i));
        }
    }

    public static void list2_refresh() throws SQLException {
        List<NoteBean> notelist = notedao.search(0, "", "", "public");
        observablenotelist2.clear();
        for (int i = 0; i < notelist.size(); i++) {
            observablenotelist2.add(notelist.get(i));
        }
    }

    public static void list2_refresh2(String author, String title) throws SQLException {
        List<NoteBean> notelist = notedao.search(0, author, title, "public");
        observablenotelist2.clear();
        for (int i = 0; i < notelist.size(); i++) {
            observablenotelist2.add(notelist.get(i));
        }
    }
}