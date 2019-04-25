package com.anujupadhyay.smsinfoline.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anujupadhyay.smsinfoline.R;
import com.anujupadhyay.smsinfoline.activity.Notice_List;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    private List<Notice_List> notice_lists;
    private Context context;

    public NoticeAdapter(List<Notice_List> notice_lists, Context context) {
        this.notice_lists = notice_lists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Notice_List noticeList = notice_lists.get(i);
        viewHolder.textHead.setText(noticeList.getNoticeHead());
        viewHolder.textLink.setText(noticeList.getNoticeLink());
        viewHolder.textDesc.setText(noticeList.getNoticeDesc());
        viewHolder.textAdmin.setText(noticeList.getNoticeAdmin());
        viewHolder.textTime.setText(noticeList.getNoticeTime());
    }

    @Override
    public int getItemCount() {
        return notice_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textHead, textLink, textDesc, textAdmin, textTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textHead = itemView.findViewById(R.id.textview_notice_head);
            textLink = itemView.findViewById(R.id.textview_notice_link);
            textDesc = itemView.findViewById(R.id.textview_notice_desc);
            textAdmin = itemView.findViewById(R.id.textview_admin);
            textTime = itemView.findViewById(R.id.textview_notice_times);
        }
    }
}
