package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.dto.DoctorData;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private List<DoctorData> doctors;

    public DoctorAdapter(List<DoctorData> doctors) {
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoctorData doctor = doctors.get(position);

        holder.textViewFirstName.setText(doctor.getFirstName());
        holder.textViewSecondName.setText(doctor.getSecondName());
        holder.textViewLastName.setText(doctor.getLastName());
        holder.textViewHealthPosition.setText(doctor.getHealthPosition());
        holder.textViewSeniority.setText(String.valueOf(doctor.getSeniority()));
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewFirstName;
        public TextView textViewSecondName;
        public TextView textViewLastName;
        public TextView textViewHealthPosition;
        public TextView textViewSeniority;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFirstName = itemView.findViewById(R.id.textViewFirstName);
            textViewSecondName = itemView.findViewById(R.id.textViewSecondName);
            textViewLastName = itemView.findViewById(R.id.textViewLastName);
            textViewHealthPosition = itemView.findViewById(R.id.textViewHealthPosition);
            textViewSeniority = itemView.findViewById(R.id.textViewSeniority);
        }
    }
    public void updateData(List<DoctorData> newDoctors) {
        doctors.clear();
        doctors.addAll(newDoctors);
        notifyDataSetChanged();
    }
}
