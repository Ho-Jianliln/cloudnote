package service;

import controller.Regist;
import dao.GroupDao;
import dao.UserDao;
import entity.User;
import view.Alert;

import java.sql.SQLException;

public class AccountManage extends Regist {
    public static void regist(String name,String password1,String password2) throws SQLException {
        if(password1.equals(password2)){
            user.setUser(name,password1,"用户",null);
            UserDao userdao=new UserDao();
            GroupDao groupdao=new GroupDao();
            switch (userdao.adduser(user)){
                case 1:
                    Alert.send("注册成功");
                    int userid=userdao.finduser(user.getName());
                    int group1id=groupdao.addgroup1(userid,"default");
                    int group2id=groupdao.addgroup2(group1id,"default");
                    break;
                case 2:Alert.send("账号已存在");
                    break;
                case 0:Alert.send("注册失败");
            }
            userdao.closeAll();
            groupdao.closeAll();

        }else{
            Alert.send("请输入两次相同的密码");
        }
    }
    public static int resetPass(String oldPass,String newPass) throws SQLException {
        User user0=new User();
        UserDao userdao=new UserDao();
        user0.setUser(user.getName(),oldPass,user.getType(),null);
        return userdao.resetpass(user0,newPass);
    }
}
