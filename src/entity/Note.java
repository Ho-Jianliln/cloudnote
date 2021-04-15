package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Note {
    private int noteid;
    private int author_id;
    private String author;
    private String title;
    private String text;
    private int like_count;
    private String access;
    private String blacklist;
    private Date lasttime;

    public Note(){

    }

    public Note(int noteid, int author_id, String author, String title, String text, int like_count, String access, String blacklist, Date lasttime) {
        this.noteid = noteid;
        this.author_id = author_id;
        this.author = author;
        this.title = title;
        this.text = text;
        this.lasttime = lasttime;
        this.like_count = like_count;
        this.access = access;
        this.blacklist = blacklist;
    }
    public void getResultset(ResultSet rs) throws SQLException {
        if(rs.next()){
            noteid = rs.getInt("noteid");
            author_id = rs.getInt("author_id");
            author = rs.getString("author");
            title = rs.getString("title");
            text = rs.getString("text");
            like_count = rs.getInt(6);
            access = rs.getString(7);
            blacklist = rs.getString(8);
            lasttime = rs.getDate(9);
        }
        else System.out.println("rs empty");
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }
}
