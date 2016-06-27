package sky.ox.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import sky.ox.R;
import sky.ox.beans.Status;
import sky.ox.beans.StatusType;
import sky.ox.ui.activity.PlayAudioActivity;
import sky.ox.ui.activity.PlayVideoActivity;
import sky.ox.ui.activity.ShowImageTextActivity;
import sky.ox.ui.view.WorkItemView;
import sky.ox.utils.ToastUtil;

/**
 * Created by sky on 6/21/16.
 */
public class UserWorksAdapter extends RecyclerView.Adapter<UserWorksAdapter.WorksViewHolder> {
    private List<Status> statuses = new ArrayList<>();

    @Override
    public WorksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WorksViewHolder(new WorkItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(WorksViewHolder holder, int position) {
        Status status = statuses.get(position);
        if (status.statusType == null) {
            return;
        }
        switch (status.statusType) {
            case StatusType.IMAGE: {
                holder.view.setImage(status.images.get(0));
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ShowImageTextActivity.class);
                        intent.putExtra("status", status);
                        v.getContext().startActivity(intent);
                    }
                });
                break;
            }
            case StatusType.VIDEO: {
                holder.view.setVideo(null);
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), PlayVideoActivity.class);
                        intent.putExtra("status", status);
                        v.getContext().startActivity(intent);
                    }
                });
                break;
            }
            case StatusType.AUDIO: {
                holder.view.setAudio();
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), PlayAudioActivity.class);
                        intent.putExtra("status", status);
                        v.getContext().startActivity(intent);
                    }
                });
                break;
            }
            case StatusType.TEXT: {
                holder.view.setPaper(status.title, status.message);
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ShowImageTextActivity.class);
                        intent.putExtra("status", status);
                        v.getContext().startActivity(intent);
                    }
                });
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    public void setData(List<Status> statuses) {
        if (statuses == null) {
            this.statuses.clear();
            notifyDataSetChanged();
            return;
        }
        this.statuses.clear();
        this.statuses.addAll(statuses);
        notifyDataSetChanged();
    }

    static class WorksViewHolder extends RecyclerView.ViewHolder {
        WorkItemView view;
        public WorksViewHolder(View itemView) {
            super(itemView);
            this.view = (WorkItemView) itemView;
        }
    }
}
