package com.karunadakote.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karunadakote.R;
import com.karunadakote.models.PhotoChallenge;
import com.karunadakote.utils.PrefsManager;

import java.util.List;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ViewHolder> {

    public interface OnChallengeClickListener {
        void onTakePhoto(PhotoChallenge challenge);
    }

    private final List<PhotoChallenge> challenges;
    private final PrefsManager prefs;
    private final OnChallengeClickListener listener;

    public ChallengeAdapter(List<PhotoChallenge> challenges, PrefsManager prefs,
                            OnChallengeClickListener listener) {
        this.challenges = challenges;
        this.prefs = prefs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_challenge, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        PhotoChallenge c = challenges.get(position);
        boolean done = prefs.isPhotoChallengeCompleted(c.getId());

        h.tvTitle.setText((done ? "✅ " : "📷 ") + c.getTitle());
        h.tvDesc.setText(c.getDescription());
        h.tvHint.setText("Hint: " + c.getHint());
        h.btnCapture.setText(done ? "Retake Photo" : "Take Photo");
        h.btnCapture.setOnClickListener(v -> listener.onTakePhoto(c));

        h.itemView.setAlpha(done ? 0.75f : 1.0f);
    }

    @Override
    public int getItemCount() { return challenges.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvHint;
        Button btnCapture;
        ViewHolder(View v) {
            super(v);
            tvTitle    = v.findViewById(R.id.tv_challenge_title);
            tvDesc     = v.findViewById(R.id.tv_challenge_desc);
            tvHint     = v.findViewById(R.id.tv_challenge_hint);
            btnCapture = v.findViewById(R.id.btn_capture);
        }
    }
}
