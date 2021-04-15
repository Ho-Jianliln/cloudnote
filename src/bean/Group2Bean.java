package bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Group2Bean {//用于存储树形图的分组数据
    private SimpleIntegerProperty id =new SimpleIntegerProperty();
    private SimpleStringProperty group2_name =new SimpleStringProperty();
    private SimpleIntegerProperty group1_id =new SimpleIntegerProperty();

    public Group2Bean(int id, String group2_name, int group1_id) {
        setId(id);
        setGroup_name(group2_name);
        setGroup1_id(group1_id);
    }

    public List<NoteBean> notelist=new ArrayList<>();

    public Group2Bean(){};
    public String toString(){
        return getGroup_name();
    }
    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getGroup_name() {
        return group2_name.get();
    }

    public SimpleStringProperty group_nameProperty() {
        return group2_name;
    }

    public void setGroup_name(String group_name) {
        this.group2_name.set(group_name);
    }

    public int getGroup1_id() {
        return group1_id.get();
    }

    public SimpleIntegerProperty group1_idProperty() {
        return group1_id;
    }

    public void setGroup1_id(int group1_id) {
        this.group1_id.set(group1_id);
    }

    public List<NoteBean> getNotelist() {
        return notelist;
    }

    public void setNotelist(List<NoteBean> notelist) {
        this.notelist = notelist;
    }
}
