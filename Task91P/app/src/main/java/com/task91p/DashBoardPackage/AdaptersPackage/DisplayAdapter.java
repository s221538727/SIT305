package com.task91p.DashBoardPackage.AdaptersPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.task91p.DataModelPackage.DataModel;
import com.task91p.R;

import java.util.List;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.ViewHolderRecycler>
{
    private List<DataModel> dataList;
    private OnClickListenerInterface listener;

    public DisplayAdapter(List<DataModel> dataList, OnClickListenerInterface listener) {
        this.dataList = dataList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolderRecycler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new ViewHolderRecycler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecycler holder, int position) {
        DataModel data = dataList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolderRecycler extends RecyclerView.ViewHolder {
        private TextView txtPostType;
        private TextView txtName;
        private TextView txtPhone;
        private TextView txtDescription;
        private TextView txtDate;
        private TextView txtLocation;

        public ViewHolderRecycler(@NonNull View itemView) {
            super(itemView);
            txtPostType = itemView.findViewById(R.id.txt_post_type);
            txtName = itemView.findViewById(R.id.txt_name);
            txtPhone = itemView.findViewById(R.id.txt_phone);
            txtDescription = itemView.findViewById(R.id.txt_description);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtLocation = itemView.findViewById(R.id.txt_location);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    DataModel clickedItem = dataList.get(position);
                    listener.onItemClick(clickedItem);
                }
            });
        }

        public void bind(DataModel data) {
            txtPostType.setText(data.getPostType());
            txtName.setText(data.getName());
            txtPhone.setText(data.getPhone());
            txtDescription.setText(data.getDescription());
            txtDate.setText(data.getDate());
            txtLocation.setText(data.getLocation());
        }
    }
}
