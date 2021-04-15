package controller;

import dao.NoteDao;
import entity.Note;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import service.SaveText;
import view.Alert;
import view.Stagechange;

import java.sql.SQLException;

public class ModifiNote extends Maincontroller{

    @FXML
    private Label savetips;

    @FXML
    private Button saveBut;

    @FXML
    private TextField titlefield;

    @FXML
    private TextArea textfield;

    @FXML
    private ComboBox<String> combo3;

    @FXML
    private Button divideBut;

    Note note2=new Note();//待操作的笔记

    public void initialize() throws SQLException {//修改笔记窗口初始化
        NoteDao notedao=new NoteDao();
        divide_group2id=0;
        combo3.getItems().add("公开");
        combo3.getItems().add("私密");
        note2= notedao.opennote(mynote.getNoteid());//加载笔记，权限信息
        titlefield.setText(note2.getTitle());
        textfield.setText(note2.getText());
        if(note2.getAccess().equals("public")){
            combo3.setValue("公开");
        }else{
            combo3.setValue("私密");
        }
        if(user.getBlacklist().equals("yes"))Alert.send("你已在黑名单内，无法发布笔记");
        notedao.closeAll();
    }
    @FXML
    void tosave(ActionEvent event) throws SQLException {
        note2.setTitle(titlefield.getText());
        note2.setText(textfield.getText());
        if(combo3.getValue().equals("公开")){
            note2.setAccess("public");
        }else{
            note2.setAccess("private");
        }
        if(user.getBlacklist().equals("yes")){
            savetips.setText("黑名单内无法发布笔记");
        }else {
            int count = SaveText.modifiNote(note2, divide_group2id);//保存笔记
            if (count == 1) savetips.setText("修改成功");
            else savetips.setText("修改失败");
        }
    }

    @FXML
    void todivide(ActionEvent event) {
        Stagechange.goto_divide();
    }

}
