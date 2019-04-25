package com.anujupadhyay.smsinfoline.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;
import com.anujupadhyay.smsinfoline.activity.UserListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private List<UserListItem> listItems;
    private Context context;

    public UserAdapter(List<UserListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_display_layout, viewGroup, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final UserListItem userListItem = listItems.get(i);
        viewHolder.textViewName.setText(userListItem.getName());
        viewHolder.textViewAbout.setText(userListItem.getAbout());
        Picasso.get().load(userListItem.getImages()).placeholder(R.drawable.profile_image).into(viewHolder.circleImageView);

//        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "You want to visit "+userListItem.getName()+" Profile ?", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName, textViewAbout;
        public CircleImageView circleImageView;
       // public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.user_name_id);
            textViewAbout = itemView.findViewById(R.id.user_about_id);
            circleImageView = itemView.findViewById(R.id.user_profile_image_id);
           // linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
