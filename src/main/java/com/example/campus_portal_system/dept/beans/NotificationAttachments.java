package com.example.campus_portal_system.dept.beans;

public class NotificationAttachments {

    private int id;
    private byte[] attachment;

    public NotificationAttachments(int id, byte[] attachment) {
        this.id = id;
        this.attachment = attachment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "NotificationAttachments{" +
                "id=" + id +
                '}';
    }
}
