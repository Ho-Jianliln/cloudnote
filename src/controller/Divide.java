package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import service.NoteTree;

import java.sql.SQLException;

public class Divide extends Maincontroller{
    @FXML
    private Label dividetips;

    @FXML
    private TreeView<String> divide_tree;
    private NoteTree nt=new NoteTree();
    public void initialize() throws SQLException {
        nt.load(user.getId(),divide_tree);//加载树形图
    }
    @FXML
    void divide_confirm(ActionEvent event) {
        if(divide_group2id!=0){
            dividetips.setText("分组选择成功");
        }
    }

}
