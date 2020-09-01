package com.example.mdevt.networking.last10Agents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mdevt.R;

import java.util.List;

public class ListViewAdaptor extends ArrayAdapter<Status> {
    private List<Status> sampleList;
    private Context context;

    public ListViewAdaptor(List<Status> sampleList, Context context){
        super(context, R.layout.fetch_last_ten,sampleList);
        this.sampleList=sampleList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View listViewItem=inflater.inflate(R.layout.fetch_last_ten,null,true);
        TextView textViewName=listViewItem.findViewById(R.id.textViewName);
        TextView textViewImageUrl=listViewItem.findViewById(R.id.textViewImageUrl);
        TextView textViewPhone=listViewItem.findViewById(R.id.textViewPhone);
        TextView textViewSurName=listViewItem.findViewById(R.id.textViewSurName);

        Status status=sampleList.get(position);

        textViewName.setText(status.getName());
        textViewImageUrl.setText(status.getImageUrl());
        textViewPhone.setText(status.getPhone());
        textViewSurName.setText(status.getSurName());

        return listViewItem;
    }
}
