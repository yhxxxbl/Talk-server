package net.yuan.web.Hello.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="TB_MESSAGE")
public class Message {
    public static final int TYPE_STR=1;//文字类型
    public static final int TYPE_PIC=2;//图片类型
    public static final int TYPE_FILE=3;//文件类型
    public static final int TYPE_AUDIO=4;//语音类型
    //主键
    @Id
    @PrimaryKeyJoinColumn
    //主键生成存储的类型
    //id 由代码写入，客户端负责生成来避免复杂的服务器和客户端的映射关系
    //@GeneratedValue(generator = "uuid")
    // 把uuid的生成器定义为uuid2，uuid2是常规的UUID toString
    @GenericGenerator(name="uuid",strategy = "uuid2")
    @Column(updatable = false,nullable = false)
    private String id;

    //内容不允许为空，类型为TEXT
    @Column(nullable = false,columnDefinition = "TEXT")
    private String content;//内容

    @Column
    private String attach;//附件

    //区分接收到的消息
    @Column(nullable = false)
    private int type;//消息类型

    //定义为创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt =LocalDateTime.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt =LocalDateTime.now();

    //不为空
    @ManyToOne(optional = false)
    @JoinColumn(name="sendId")
    private User sender;//发送者
    @Column(nullable = false,updatable = false,insertable = false)
    private String senderId;//这个字段仅仅是为了对应sender的数据库兹端senderId
                            //不允许手动的更新或插入


    //接收者可为空
    @ManyToOne
    @JoinColumn(name="receiverId")
    private User receiver;//接收者
    @Column(updatable = false,insertable = false)
    private String receiverId;


    //一个群可以接受多个消息
    @ManyToOne
    @JoinColumn(name="Id")
    private Group group;
    @Column(updatable = false,insertable = false)
    private String groupId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
