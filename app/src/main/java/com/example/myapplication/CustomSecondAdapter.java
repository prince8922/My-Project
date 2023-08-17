package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomSecondAdapter extends BaseAdapter {

    Context context;
    ArrayList<CustomList> arrayList;


    public CustomSecondAdapter(CustomListViewSecondActivity customListViewSecondActivity, ArrayList<CustomList> arrayList) {
        this.context=customListViewSecondActivity;
        this.arrayList=arrayList;


    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_list,null);

        ImageView imageView = view.findViewById(R.id.custom_list_image);
        TextView name = view.findViewById(R.id.custom_list_name);
        TextView price = view.findViewById(R.id.custom_list_price);

        name.setText(arrayList.get(i).getName());
        price.setText(context.getResources().getString(R.string.price_symbol)+arrayList.get(i).getPrice());
        imageView.setImageResource(arrayList.get(i).getImage());


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(context,arrayList.get(i).getName()+"Image Click");

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new CommonMethod(context,arrayList.get(i).getName());
                Intent intent=new Intent(context,DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("IMAGE",arrayList.get(i).getImage());
                bundle.putString("NAME",arrayList.get(i).getName());
                bundle.putString("PRICE",arrayList.get(i).getPrice());
                bundle.putString("DESC",arrayList.get(i).getDescriptionArray());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
