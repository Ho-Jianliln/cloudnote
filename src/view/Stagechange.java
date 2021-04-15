package view;

import entity.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Stagechange {
    public static void gotorg(){
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(Stagechange.class.getResource("Regist.fxml")));
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
    };
    public static void goto_surface(User user){
        if(user.getType().equals("user")){
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(Stagechange.class.getResource("UserSurface.fxml")));
                stage.setTitle("云笔记  welcome "+user.getName());
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(user.getType().equals("admin")){
            try {
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(Stagechange.class.getResource("AdminSurface.fxml")));
                stage.setTitle("云笔记管理  welcome "+user.getName());
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void goto_opennote(){
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(FXMLLoader.load(Stagechange.class.getResource("OpenNote.fxml")));
            stage.setTitle("查看笔记");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void goto_divide(){
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(FXMLLoader.load(Stagechange.class.getResource("Divide.fxml")));
            stage.setTitle("设置分组");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
