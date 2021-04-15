package bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;

public class NoteBean {//存储笔记列表的笔记信息
    private SimpleIntegerProperty noteid=new SimpleIntegerProperty();
    private SimpleIntegerProperty author_id=new SimpleIntegerProperty();
    private SimpleStringProperty author=new SimpleStringProperty();
    private SimpleStringProperty title=new SimpleStringProperty();
    private SimpleIntegerProperty like_count=new SimpleIntegerProperty();
    private SimpleIntegerProperty group2_id =new SimpleIntegerProperty();

    public String toString(){
        return getTitle();
    }
    public int getGroup2_id() {
        return group2_id.get();
    }

    public SimpleIntegerProperty group2_idProperty() {
        return group2_id;
    }

    public void setGroup2_id(int group2_id) {
        this.group2_id.set(group2_id);
    }

    private Date lasttime;

    public NoteBean() {
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public int getNoteid() {
        return noteid.get();
    }

    public SimpleIntegerProperty noteidProperty() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid.set(noteid);
    }

    public int getAuthor_id() {
        return author_id.get();
    }

    public SimpleIntegerProperty author_idProperty() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id.set(author_id);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getLike_count() {
        return like_count.get();
    }

    public SimpleIntegerProperty like_countProperty() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count.set(like_count);
    }

}
