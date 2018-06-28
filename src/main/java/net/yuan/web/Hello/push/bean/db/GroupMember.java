package net.yuan.web.Hello.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="TB_GROUP_MEMBER")
public class GroupMember {
    public static final int PERMISSION_TYPE_NONE =5;//默认权限，普通成员
    public static final int PERMISSION_TYPE_ADMIN =6;//管理员
    public static final int PERMISSION_TYPE_ADMIN_SU =7;//创建者

    public static final int NOTIFY_LEVER_INCALID = -1;  //默认不接受（屏蔽群消息）
    public static final int NOTIFY_LEVER_CLOSE =0;  //接收消息不提示（群消息免打扰）
    public static final int NOTIFY_LEVER_NONE = 1; //默认通知级别

    //主键
    @Id
    @PrimaryKeyJoinColumn
    //主键生成存储的类型
    @GeneratedValue(generator = "uuid")
    // 把uuid的生成器定义为uuid2，uuid2是常规的UUID toString
    @GenericGenerator(name="uuid",strategy = "uuid2")
    @Column(updatable = false,nullable = false)
    private String id;

    @Column
    private String alias; //别名

    @Column(nullable = false)
    private int notifyLevel=NOTIFY_LEVER_NONE;//消息通知级别

    @Column(nullable = false)
    private int permissionTye=PERMISSION_TYPE_NONE;//默认权限级别

    //定义为创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt =LocalDateTime.now();

    //定义为更新时间戳，在创建时就已经写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt =LocalDateTime.now();

    //成员对应的信息
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private User user;
    @Column(nullable = false,updatable = false,insertable = false)
    private String userId;

    //成员对应的群信息
    @JoinColumn(name = "groupId")
    @ManyToOne(optional = false,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Group group;
    @Column(nullable = false,updatable = false,insertable = false)
    private String groupId;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(int notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    public int getPermissionTye() {
        return permissionTye;
    }

    public void setPermissionTye(int permissionTye) {
        this.permissionTye = permissionTye;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
