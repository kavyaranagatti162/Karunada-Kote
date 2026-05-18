package com.karunadakote.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karunadakote.R;
import com.karunadakote.models.Fort;
import com.karunadakote.models.FortPoint;
import com.karunadakote.utils.PrefsManager;

import java.util.List;

public class FortListAdapter extends RecyclerView.Adapter<FortListAdapter.ViewHolder> {

    public interface OnFortClickListener {
        void onFortClick(Fort fort);
    }

    private final List<Fort> forts;
    private final PrefsManager prefs;
    private final OnFortClickListener listener;

    public FortListAdapter(List<Fort> forts, PrefsManager prefs, OnFortClickListener listener) {
        this.forts = forts;
        this.prefs = prefs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_fort, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        Fort fort = forts.get(position);

        h.tvName.setText(fort.getName());
        h.tvNameKn.setText(fort.getNameKannada());
        h.tvDynasty.setText(fort.getDynasty() + " • " + fort.getBuiltYear());

        // Count visited
        int visited = 0, total = fort.getPoints().size();
        for (FortPoint p : fort.getPoints()) {
            if (prefs.isPointVisited(p.getId())) visited++;
        }
        h.tvPoints.setText(visited + "/" + total + " landmarks");
        h.progressBar.setMax(total);
        h.progressBar.setProgress(visited);

        h.itemView.setOnClickListener(v -> listener.onFortClick(fort));
    }

    @Override
    public int getItemCount() { return forts.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvNameKn, tvDynasty, tvPoints;
        ProgressBar progressBar;

        ViewHolder(View v) {
            super(v);
            tvName     = v.findViewById(R.id.tv_item_fort_name);
            tvNameKn   = v.findViewById(R.id.tv_item_fort_name_kn);
            tvDynasty  = v.findViewById(R.id.tv_item_fort_dynasty);
            tvPoints   = v.findViewById(R.id.tv_item_fort_points);
            progressBar = v.findViewById(R.id.pb_fort_progress);
        }
    }
}
