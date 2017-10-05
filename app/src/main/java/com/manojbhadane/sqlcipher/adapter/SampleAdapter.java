package com.manojbhadane.sqlcipher.adapter;

/**
 * Created by manoj.bhadane on 05-10-2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manojbhadane.sqlcipher.R;
import com.manojbhadane.sqlcipher.model.DataModel;

import java.util.List;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.MyViewHolder>
{

    private List<DataModel> mList;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;

        public MyViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.txt);
        }
    }

    public SampleAdapter(List<DataModel> list)
    {
        this.mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        DataModel model = mList.get(position);
        holder.title.setText((position + 1) + ". " + model.getName());
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}