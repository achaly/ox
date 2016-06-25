package sky.ox.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import sky.ox.beans.User;
import sky.ox.ui.activity.UserDetailActivity;
import sky.ox.ui.view.UserItemView;

/**
 * Created by sky on 6/21/16.
 */
public class FolloweeListAdapter extends RecyclerView.Adapter<FolloweeListAdapter.UserViewHolder> {
    private List<User> users = new ArrayList<>();

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(new UserItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.view.setImage(user.getAvatarUrl());
        holder.view.setName(user.getUsername());
//        holder.view.setCategory();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserDetailActivity.class);
                intent.putExtra("user", user);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public void setData(List<User> users) {
        if (users == null || users.size() == 0) {
            return;
        }
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        UserItemView view;

        public UserViewHolder(View itemView) {
            super(itemView);
            this.view = (UserItemView) itemView;
        }
    }
}
