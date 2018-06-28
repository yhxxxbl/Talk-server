package net.yuan.web.Hello.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="TB_PUSH_HISTORY")
public class PushHistory {

    //主键
    @Id
    @PrimaryKeyJoinColumn
    //主键生成存储的类型
    @GeneratedValue(generator = "uuid")
    // 把uuid的生成器定义为uuid2，uuid2是常规的UUID toString
    @GenericGenerator(name="uuid",strategy = "uuid2")
    @Column(updatable = false,nullable = false)
    private String id;

    //BLOB是一个大字段类型 比TXT更多
    @Lob
    @Column(nullable = false,columnDefinition = "BLOB")
    private String  entity;

    @Column(nullable = false)
    private int entityType;//推送的实体类型

    //一个接收者可以接受很多消息
    //加载一条推送消息的时候直接加载用户信息
    @ManyToOne(optional = false,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "receiverId")
    private User receiver;//接收者  不允许为空
    @Column(nullable = false,updatable = false,insertable = false)
    private String receiverId;

    //一个接收者可以接受很多消息
    //加载一条推送消息的时候直接加载用户信息
    @ManyToOne(optional = false,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "senderId")
    private User sender;//发送者 可为空
    @Column(updatable = false,insertable = false)
    private String senderId;

    //接收者当前状态下的设备推送ID  User.pushId
    @Column
    private String receiverPushId;

    //定义为创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt =LocalDateTime.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt =LocalDateTime.now();

    @Column
    private LocalDateTime arrivalAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverPushId() {
        return receiverPushId;
    }

    public void setReceiverPushId(String receiverPushId) {
        this.receiverPushId = receiverPushId;
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

    public LocalDateTime getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(LocalDateTime arrivalAt) {
        this.arrivalAt = arrivalAt;
    }
}
