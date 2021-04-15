package controller;

import service.AccountManage;
import view.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;


public class Regist extends Maincontroller  {

    @FXML
    public Button registBut;

    @FXML
    private TextField rgaccountfield;

    @FXML
    private PasswordField rgpwfield;

    @FXML
    private PasswordField rgpwfield2;

    @FXML
    void toRegist(ActionEvent event) throws SQLException {
        String name=rgaccountfield.getText();
        String password1=rgpwfield.getText();
        String password2=rgpwfield2.getText();
        AccountManage.regist(name,password1,password2);
        Stage primaryStage = (Stage) registBut.getScene().getWindow();/*获取当前窗口*/
        primaryStage.close();//关闭当前窗口

    }

    @FXML
    void checkpwlenth(MouseEvent event) {/*再次输密码时，检查密码长度*/
        String password1=rgpwfield.getText();
        int pwlength=password1.length();
        if(pwlength<6)
        {
            Alert.send("请输入至少六位密码");
        }
    }
}

