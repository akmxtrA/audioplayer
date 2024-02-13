package com.example.player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class audioAdapter extends ArrayAdapter<audioList> {
    public audioAdapter(Context context, ArrayList<audioList> audio) {
        super(context, 0, audio);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        audioList audio = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        ImageView imageAudio = convertView.findViewById(R.id.imageAudio);
        TextView name = convertView.findViewById(R.id.name);
        TextView executor = convertView.findViewById((R.id.executor));
        imageAudio.setImageResource(audio.getAudioImage());
        name.setText(audio.getName());
        executor.setText(audio.getExecutor());

        return convertView;
    }
}
