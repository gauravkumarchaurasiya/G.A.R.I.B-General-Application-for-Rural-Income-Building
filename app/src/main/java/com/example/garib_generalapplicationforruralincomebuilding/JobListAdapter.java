package com.example.garib_generalapplicationforruralincomebuilding;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garib_generalapplicationforruralincomebuilding.Job;
import com.example.garib_generalapplicationforruralincomebuilding.R;

import java.util.List;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobViewHolder> {

    private List<Job> jobList; // List to hold job data
    private OnItemClickListener onItemClickListener; // Interface for item click

    public interface OnItemClickListener {
        void onItemClick(String url);
    }

    public void setJobs(List<Job> jobs) {
        this.jobList = jobs;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_list_item, parent, false); // Create a custom layout for job items
        return new JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.companyNameTextView.setText(job.getCompany_name());
        holder.jobTitleTextView.setText(job.getRole());
        holder.jobLocationTextView.setText(job.getLocation());

        // Set the Title_URL to the TextView
        holder.titleUrlTextView.setText(job.getTitle_URL());

        // Open URL when the item is clicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(job.getTitle_URL());
                }
            }
        });

        // Handle Title_URL click to open in a web browser
        holder.titleUrlTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = job.getTitle_URL();
                if (url != null && !url.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList != null ? jobList.size() : 0;
    }

    // ViewHolder class to hold references to views
    public static class JobViewHolder extends RecyclerView.ViewHolder {
        public TextView companyNameTextView;
        public TextView jobTitleTextView;
        public TextView jobLocationTextView;
        public TextView titleUrlTextView;

        public JobViewHolder(View itemView) {
            super(itemView);
            companyNameTextView = itemView.findViewById(R.id.companyNameTextView);
            jobTitleTextView = itemView.findViewById(R.id.jobTitleTextView);
            jobLocationTextView = itemView.findViewById(R.id.jobLocationTextView);
            titleUrlTextView = itemView.findViewById(R.id.titleUrlTextView);
        }
    }
}
