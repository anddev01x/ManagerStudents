package com.techja.managerstudents.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.techja.managerstudents.R;
import com.techja.managerstudents.model.StudentEntity;
import com.techja.managerstudents.my_interface.iClickItemStudentListener;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<StudentEntity> listStudents;
    private iClickItemStudentListener iClickItemStudentListener;

    public StudentAdapter(List<StudentEntity> listStudents, iClickItemStudentListener listener) {
        this.listStudents = listStudents;
        this.iClickItemStudentListener = listener;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student,
                parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int i) {
        final StudentEntity student = listStudents.get(i);
        if (student == null) {
            return;
        }
        holder.tvStudentId.setText(student.getIdStudent());
        holder.tvStudentName.setText(student.getFullName());
        holder.tvStudentBirthdate.setText(student.getBirthdate());
        holder.tvIdClass.setText(student.getIdClass());

        holder.cardView.setOnClickListener(view -> iClickItemStudentListener.onClickItemStudent(student));
    }

    @Override
    public int getItemCount() {
        if (listStudents != null) {
            return listStudents.size();
        }
        return 0;
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvStudentId;
        private final TextView tvStudentName;
        private final CardView cardView;
        private final TextView tvStudentBirthdate;
        private final TextView tvIdClass;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.layout_item_student);
            tvStudentId = itemView.findViewById(R.id.tv_id);
            tvStudentName = itemView.findViewById(R.id.tv_name);
            tvStudentBirthdate = itemView.findViewById(R.id.tv_birthdate);
            tvIdClass = itemView.findViewById(R.id.tv_idClass);
        }
    }
}
