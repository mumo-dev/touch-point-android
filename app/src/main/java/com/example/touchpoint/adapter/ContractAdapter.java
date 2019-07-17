package com.example.touchpoint.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touchpoint.ContractActivity;
import com.example.touchpoint.ContractDetailActivity;
import com.example.touchpoint.R;
import com.example.touchpoint.models.Contract;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

public class ContractAdapter  extends  RecyclerView.Adapter<ContractAdapter.ContractViewHolder>{


    private List<Contract> contractList;
    private Context mContext;

    public ContractAdapter(List<Contract> contracts, Context context){
        this.contractList = contracts;
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
        final Contract contract = contractList.get(position);
        holder.textViewId.setText("#"+ contract.getId());

//        SimpleDateFormat format = new SimpleDateFormat();

        holder.textViewDesc.setText("uploaded on " + contract.getCreated_at());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ContractDetailActivity.class);
                intent.putExtra("contract", contract);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contractList.size();
    }


    public void  setContractList(List<Contract> items){
        contractList = items;
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
