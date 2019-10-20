package ua.kpi.bis_lab_1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ua.kpi.bis_lab_1.R;
import ua.kpi.bis_lab_1.model.User;
import ua.kpi.bis_lab_1.service.ItemTouchHelperAdapter;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private Context context;
    private List<User> itemList;
    private UserListAdapter.AdapterListener listener;

    public UserListAdapter(Context context, List<User> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public interface AdapterListener {
        void onDeleteItem(Integer position);
        void onItemClickListener(View v, Integer position);
    }

    public void setListener(UserListAdapter.AdapterListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView login;

        public ViewHolder(View itemView) {
            super(itemView);
            login = itemView.findViewById(R.id.login);
        }
    }

    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserListAdapter.ViewHolder holder, final int position) {
        holder.login.setText(itemList.get(position).getLogin());
        holder.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onItemDismiss(int position) {

    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    public void update(List<User> listItems) {
        this.itemList = listItems;
        notifyDataSetChanged();
    }
}
