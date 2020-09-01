package com.example.mdevt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListViewAdaptor extends ArrayAdapter<Status> {
    private List<Status> sampleList;
    private Context context;

    public ListViewAdaptor(List<Status> sampleList, Context context){
        super(context,R.layout.fetch_last_ten,sampleList);
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

        Status status=sampleList.get(position);

        textViewName.setText(status.getName());
        textViewImageUrl.setText(status.getImageUrl());

        return listViewItem;
    }
}
