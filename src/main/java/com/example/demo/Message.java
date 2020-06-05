package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Size(min=3,max=25)
    private String topic;

    @Size(min=3, max=240)
    private String msgText;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    public Message() {
        this.user = new User();
    }

    public Message(@Size(min = 3, max = 25) String topic, @Size(min = 3, max = 240) String msgText, User user) {
        super();
        this.topic = topic;
        this.msgText = msgText;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Size(min=3,max=280)
    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
