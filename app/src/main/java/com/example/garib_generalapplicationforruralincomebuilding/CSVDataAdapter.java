package com.example.garib_generalapplicationforruralincomebuilding;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CSVDataAdapter extends RecyclerView.Adapter<CSVDataAdapter.ViewHolder> {

    private List<String[]> data;
    private Context context;

    public CSVDataAdapter(Context context, List<String[]> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_csv_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] rowData = data.get(position);
        holder.bindData(rowData);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView statusTextView;
        TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }

        public void bindData(final String[] rowData) {
            // Customize this part to bind the CSV data to your TextViews
            if (rowData.length >= 3) {
                String title = rowData[0];
                final String status = rowData[1];
                String description = rowData[2];

                titleTextView.setText(title);
                statusTextView.setText(status);
                descriptionTextView.setText(description);

                // Set an OnClickListener for the statusTextView to open the URL
                statusTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Open the URL in a web browser
                        openUrlInBrowser(status);
                    }
                });
            }
        }
    }

    private void openUrlInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }
}
