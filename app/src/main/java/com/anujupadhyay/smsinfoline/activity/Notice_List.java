package com.anujupadhyay.smsinfoline.activity;

public class Notice_List {
    private String NoticeHead;
    private String NoticeLink;
    private String NoticeDesc;
    private String NoticeAdmin;
    private String NoticeTime;

    public Notice_List(String noticeHead, String noticeLink, String noticeDesc, String noticeAdmin, String noticeTime) {
        NoticeHead = noticeHead;
        NoticeLink = noticeLink;
        NoticeDesc = noticeDesc;
        NoticeAdmin = noticeAdmin;
        NoticeTime = noticeTime;
    }

    public String getNoticeHead() {
        return NoticeHead;
    }

    public String getNoticeLink() {
        return NoticeLink;
    }

    public String getNoticeDesc() {
        return NoticeDesc;
    }

    public String getNoticeAdmin() {
        return NoticeAdmin;
    }

    public String getNoticeTime() {
        return NoticeTime;
    }
}
