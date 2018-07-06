package net.yuan.web.Hello.push.bean.Card;

import com.google.gson.annotations.Expose;
import net.yuan.web.Hello.push.bean.db.User;

import java.time.LocalDateTime;

public class UserCard {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String phone;
    @Expose
    private String desc;
    @Expose
    private String portrait;
    @Expose
    private int sex=0;
    @Expose
    private LocalDateTime modiftAt=LocalDateTime.now();
    @Expose
    private  int follows;//用户粉丝的数量
    @Expose
    private int following;//用户关注人的数量
    @Expose
    private boolean isFollow;

    public UserCard(final User user){
        this.id=user.getId();
        this.name=user.getName();
        this.phone=user.getPhone();
        this.portrait=user.getPortrait();
        this.desc=user.getDescription();
        this.sex=user.getSex();
        this.modiftAt=user.getUpdateAt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public LocalDateTime getModiftAt() {
        return modiftAt;
    }

    public void setModiftAt(LocalDateTime modiftAt) {
        this.modiftAt = modiftAt;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}
