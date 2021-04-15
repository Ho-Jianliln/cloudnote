package controller;

import entity.Note;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.SaveText;
import view.Alert;
import view.Stagechange;

import java.io.IOException;
import java.sql.SQLException;

public class AddNote extends Maincontroller{

    @FXML
    private Label savetips;

    @FXML
    private Button saveBut;

    @FXML
    private TextField titlefield;

    @FXML
    private TextArea textfield;

    @FXML
    private ComboBox<String> combo2;

    @FXML
    private Button divideBut;
    private Note newnote=new Note();

    public void initialize() {//添加笔记窗口的初始化
        divide_group2id=0;
        combo2.getItems().add("公开");
        combo2.getItems().add("私密");
        combo2.setValue("公开");
        if(user.getBlacklist().equals("yes"))Alert.send("你已在黑名单内，无法发布笔记");
    }

    @FXML
    void tosave(ActionEvent event) throws SQLException {
        newnote.setAuthor_id(user.getId());
        newnote.setAuthor(user.getName());
        newnote.setTitle(titlefield.getText());
        newnote.setText(textfield.getText());
        if(combo2.getValue().equals("公开")){
            newnote.setAccess("public");
        }else{
            newnote.setAccess("private");
        }
        if(user.getBlacklist().equals("yes")){
            savetips.setText("黑名单内无法发布笔记");
        }else {
            int count = SaveText.addNote(newnote, divide_group2id);
            if (count == 1) savetips.setText("保存成功");
            else savetips.setText("保存失败");
        }
    }

    @FXML
    void todivide(ActionEvent event){
        Stagechange.goto_divide();
    }

}
