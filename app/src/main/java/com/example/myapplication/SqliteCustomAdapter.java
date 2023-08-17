package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SqliteCustomAdapter extends RecyclerView.Adapter<SqliteCustomAdapter.MyHolder> {

    Context context;
    ArrayList<SqliteCustomList> arrayList;
    SQLiteDatabase db;

    public SqliteCustomAdapter(Context context, ArrayList<SqliteCustomList> arrayList) {
        this.context=context;
        this.arrayList=arrayList;

        db = context.openOrCreateDatabase("MYAPP", MODE_PRIVATE, null);
        String createTable = "create table if not exists record(STUDENTNAME VARCHAR(50),EMAILID VARCHAR(100),CONTACTNO INT(10))";
        db.execSQL(createTable);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview,parent,false);
        return new MyHolder(view);
    }
    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name,email,contact;
       // FloatingActionButton delete;
        ImageView delete,edit;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.custom_recyclerview_name);
            email = itemView.findViewById(R.id.custom_recyclerview_email);
            contact = itemView.findViewById(R.id.custom_recyclerview_contact);
            delete=itemView.findViewById(R.id.custom_recyclerview_delete);
            edit=itemView.findViewById(R.id.custom_recyclerview_edit);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getName());
        
        holder.email.setText(arrayList.get(position).getEmail());
        holder.contact.setText(arrayList.get(position).getContact());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteQuery = "delete from record WHERE CONTACTNO='"+arrayList.get(position).getContact()+"'";
                db.execSQL(deleteQuery);
                new CommonMethod(context,"Student Record "+arrayList.get(position).getContact()+" Deleted Successfully");
                arrayList.remove(position);
                notifyDataSetChanged();

            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,SqliteDatabaseActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("type","Edit");
                bundle.putString("NAME",arrayList.get(position).getName());
                bundle.putString("EMAIL",arrayList.get(position).getEmail());
                bundle.putString("CONTACT",arrayList.get(position).getContact());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
