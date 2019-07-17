package com.example.touchpoint.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touchpoint.ContractDetailActivity;
import com.example.touchpoint.IdentificationDetailActivity;
import com.example.touchpoint.R;
import com.example.touchpoint.models.Contract;
import com.example.touchpoint.models.Identification;

import java.util.List;

public class IdentificationAdapter extends  RecyclerView.Adapter<IdentificationAdapter.ContractViewHolder>{


    private List<Identification> identificationList;
    private Context mContext;

    public IdentificationAdapter(List<Identification> contracts, Context context){
        this.identificationList = contracts;
        mContext = context;
    }
    @NonNull
    @Override
    public ContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contract_item, parent, false);
        return new ContractViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractViewHolder holder, int position) {
        final Identification identification = identificationList.get(position);
        holder.textViewId.setText("#"+ identification.getId());

//        SimpleDateFormat format = new SimpleDateFormat();

        holder.textViewDesc.setText("uploaded on " + identification.getCreated_at());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, IdentificationDetailActivity.class);
                intent.putExtra("identification", identification);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return identificationList.size();
    }


    public void  setContractList(List<Identification> items){
        identificationList = items;
        notifyDataSetChanged();
    }

    public class ContractViewHolder extends RecyclerView.ViewHolder{

        TextView textViewId, textViewDesc;
        public ContractViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.item_id);
            textViewDesc = itemView.findViewById(R.id.item_desc);

        }
    }
}
