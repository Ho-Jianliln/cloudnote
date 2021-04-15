package entity;

import util.Encrypt;

import java.sql.Date;

public class User {
    private int id;
    private String name;
    private String pass;
    private String key1;//key1存放在properties中，实现记住密码功能
    private String key2;//key2用于数据库密码验证
    private String type;
    private Date lasttime;
    private String blacklist="";

    public void setUser(String name, String pass, String type, String key1) {//pass和key1有一个为空
        this.name = name;
        this.type = type;
        Encrypt encrypt=new Encrypt();
        if(pass!=null&&key1==null){
            this.pass = pass;
            this.key1= encrypt.SHA256(name+pass);
            this.key2= encrypt.SHA512(this.key1+"mynote");
        }
        else{
            this.key1=key1;
            this.key2=encrypt.SHA512(key1+"mynote");
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key1) {
        this.key2 = key2;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }
    public void setPass(String newpass) {
        Encrypt encrypt=new Encrypt();
        this.pass = newpass;
        this.key1= encrypt.SHA256(name+newpass);
        this.key2= encrypt.SHA512(this.key1+"mynote");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLasttime() {
        return lasttime;
    }


    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public String getBlacklist() {return blacklist; }
}
