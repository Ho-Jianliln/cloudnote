package controller;

import dao.UserDao;
import entity.User;
import javafx.stage.Stage;
import view.Stagechange;

import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.*;
import java.sql.SQLException;
import java.util.Properties;


public class Launcher extends Maincontroller{
    private static boolean perpwm=false;//上次是否记住密码
    private static String pername;//上次记住密码的账户名

    @FXML
    private CheckBox pwmemery;
    @FXML
    private TextField accountfield;
    @FXML
    private Button loginBut;
    @FXML
    private ComboBox<String> combo1;
    @FXML
    private PasswordField passwordfied;
    @FXML
    private Label regist;

    public void initialize() throws IOException {
        combo1.getItems().add("用户");
        combo1.getItems().add("管理员");
        combo1.setValue("用户");
        Properties prop = new Properties();/*读properties提账密*/
        prop.load(new FileReader("user.properties"));
        accountfield.setText(prop.getProperty("name"));
        pername= prop.getProperty("name");
        passwordfied.setText(prop.getProperty("password"));
        if(prop.getProperty("pass_memory").equals("true")){//维持记住密码的设置
            pwmemery.setSelected(true);
            perpwm=true;
        }
    }

    @FXML
    void toRegist(MouseEvent event)  {
        Stagechange.gotorg();
    }

    @FXML
    void pwmemory(ActionEvent event) {

    }

    @FXML
    void login(ActionEvent event) throws IOException, SQLException {
        User user0=new User();//用于验证的实例user0
        String name=accountfield.getText();
        String password=passwordfied.getText();
        String type;
        if(combo1.getValue().equals("用户")){
            type="user";
        }else{
            type="admin";
        }
        String key1 = null;
        if(perpwm&&pername!=null&&pername.equals(name)) {/*如之前记住密码，且账号未改，则验证key1*/
            key1 = password;
            password=null;
        }
        user0.setUser(name,password,type,key1);
        key1=user0.getKey1();

        Properties prop = new Properties();/*读checkbox账密存properties*/
        FileWriter writer=new FileWriter("user.properties");
        if(pwmemery.isSelected()){//如勾选记住密码，保存到properties
            prop.setProperty("name",name);/*properties的存的密码为key1*/
            prop.setProperty("password",key1);
            prop.setProperty("pass_memory","true");
        }else{
            prop.setProperty("name","");
            prop.setProperty("password","");
            prop.setProperty("pass_memory","false");
        }
        prop.store(writer,"");
        writer.close();

        UserDao userdao=new UserDao();
        user=userdao.judgepass(user0);//验证账号密码,验证成功则将用户的信息存到user
        userdao.closeAll();
        if(user.getId()!=0){
            Stagechange.goto_surface(user);/*转入用户主界面和id，关闭登录窗口*/
            Stage stage = (Stage)loginBut.getScene().getWindow();/*关闭登录器*/
            stage.close();
        }
    }
}
