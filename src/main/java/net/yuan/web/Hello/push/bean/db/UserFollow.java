package net.yuan.web.Hello.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/***
 * 用户关系的model
 * 用于用户直接进行好友的实现
 */
@Entity
@Table(name="TB_USER_FOLLOW")
public class UserFollow {

    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    @Column(updatable = false,nullable = false)
    private String id;

    //定义一个发起人
    //多对一  每一次关注都是一条记录
    //你可以创建很多个关注的信息，即多对一
    //User对应多个UserFollow
    //optional 不可选，必须储存，一条关注记录一定要有一个“你”
    @ManyToOne(optional = false)
    //定义关联的表字段名originId 对应的是User.id
    @JoinColumn(name = "originId")
    private User origin;

    //把这个列提取到Model中，不允许更新插入，不允许更新
    @Column(nullable = false,updatable = false,insertable = false)
    private String originId;

    //定义关注的目标-你关注的某个人
    //一个人可以被很多个人关注
    @ManyToOne(optional = false)
    @JoinColumn(name = "targetId")
    private User target;

    //把这个列提取到Model中，不允许更新插入，不允许更新
    @Column(nullable = false,updatable = false,insertable = false)
    private String targetId;

    @Column
    private String alias; //对别人的备注

    //定义为创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt =LocalDateTime.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt =LocalDateTime.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) {
        this.origin = origin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
