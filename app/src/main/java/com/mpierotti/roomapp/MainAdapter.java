package com.mpierotti.roomapp;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    //Initialize variable
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    //create constructor
    public MainAdapter(Activity context, List<MainData> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inicializar view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Inicializar la main data
        MainData data = dataList.get(position);
        //Inicializar database
        database = RoomDB.getInstance(context);
        //set text on text view
        holder.textView.setText(data.getText());
        holder.btEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //inicializar main data
                MainData d = dataList.get(holder.getAdapterPosition());
                //get id
                int sID = d.getID();
                //get text
                String sText = d.getText();

                //crear dialog
                Dialog dialog = new Dialog(context);
                //Set content view
                dialog.setContentView(R.layout.dialog_update);
                //inicializar width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //inicializar height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //set layout
                dialog.getWindow().setLayout(width, height);
                //show dialog
                dialog.show();

                //inicializar y asignar variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                Button btUpdate = dialog.findViewById(R.id.bt_update);

                //set text en el edit text
                editText.setText(sText);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // dismiss dialog
                        dialog.dismiss();
                        //get updated text from edit text
                        String uText = editText.getText().toString().trim();
                        //Update text in database
                        database.mainDAO().update(sID, uText);
                        //Notificar cuando la data este actualizada
                        dataList.clear();
                        dataList.addAll(database.mainDAO().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });
    holder.btDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //inicializar main data
            MainData d = dataList.get(holder.getAdapterPosition());
            //delete text from database
            database.mainDAO().delete(d);
            //Notificar cuando la data esté borrada
            int position = holder.getAdapterPosition();
            dataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataList.size());
        }
    });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Inicializar variable
        TextView textView;
        ImageView btEdit, btDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Asignar variables
            textView = itemView.findViewById(R.id.text_view);
            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);

        }
    }
}
