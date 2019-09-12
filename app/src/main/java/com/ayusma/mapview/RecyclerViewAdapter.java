package com.ayusma.mapview;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> implements Filterable {

    private List<ItemObject> itemList;
    private  List<ItemObject> itemListCopy;
    private List<ItemObject> searchListFull;
    List<ItemObject> itemListCopy1;
    private Context context;

    public RecyclerViewAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
        searchListFull = itemList;
        itemListCopy = itemList;

    }
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        itemListCopy1 = new ArrayList<>();
        itemListCopy1.addAll(itemListCopy);
        return rcv;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.markerName.setText(itemList.get(position).getName());
        holder.locationname.setText(itemList.get(position).getName2());
        holder.position.setText( itemList.get(position).getPosition());


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ItemObject> filteredList = new ArrayList<>();

            if (charSequence.length() == 0){
                itemList.clear();
                itemList.addAll(itemListCopy1);
            }

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(searchListFull);


            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ItemObject itemObject : searchListFull) {
                    if (itemObject.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(itemObject);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            itemList.clear();
            itemList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}