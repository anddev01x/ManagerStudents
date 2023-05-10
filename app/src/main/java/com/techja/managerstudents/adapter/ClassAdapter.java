package com.techja.managerstudents.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.techja.managerstudents.R;
import com.techja.managerstudents.model.ClassroomEntity;
import com.techja.managerstudents.my_interface.iClickItemClassListener;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private List<ClassroomEntity> listClass;
    private final iClickItemClassListener iClickItemClassListener;

    @SuppressLint("NotifyDataSetChanged")
    public ClassAdapter(List<ClassroomEntity> listClass, iClickItemClassListener listener) {
        this.listClass = listClass;
        this.iClickItemClassListener = listener;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFilterList(List<ClassroomEntity> filterList) {
        this.listClass = filterList;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setSortItem(List<ClassroomEntity> sortItem) {
        this.listClass = sortItem;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class,
                parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassroomEntity classroom = listClass.get(position);
        if (classroom == null) {
            return;
        }
        holder.tvIdClass.setText(classroom.getIdClass());
        holder.tvNameClass.setText(classroom.getNameClass());
        holder.tvLecturer.setText(classroom.getNameLecturers());

        holder.cardView.setOnClickListener(view -> iClickItemClassListener.onClickItemClass(classroom));

    }

    @Override
    public int getItemCount() {
        if (listClass != null) {
            return listClass.size();
        }
        return 0;
    }



    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvIdClass;
        private final TextView tvNameClass;
        private final CardView cardView;
        private final TextView tvLecturer;


        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.layout_item_class);
            tvIdClass = itemView.findViewById(R.id.tv_keyIdClass);
            tvNameClass = itemView.findViewById(R.id.tv_keyNameClass);
            tvLecturer = itemView.findViewById(R.id.tv_keyLec);

        }
    }
}