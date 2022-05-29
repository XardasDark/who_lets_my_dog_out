package com.fhbielefeld.wholetsthedogoutpraesi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listview;

    int[] images = {R.drawable.ananas,R.drawable.apple,R.drawable.banana,R.drawable.cherry,R.drawable.kiwi,R.drawable.orange};
    String[] names = {"Ananas","Apfel","Banane","Kirsche","Kiwi","Orange"};
    String[] desc = {"Was ist das Gegenteil von Ananas? - Anatroken","An apple a day keeps the doctor away","Tage, an denen man plant Bananen zu essen könnte man auch als Bananenplantage bezeichnen", "Was ist klein, rot, glänzt und fährt ständig rauf und runter? Eine Kirsche im Fahrstuhl.","Was ist grün und spielt gut Klavier? - Kiwi Wonder","was ist orange und rollt den Berg hoch? - Eine Wanderriene"};

    List<ItemsModel> ListItems = new ArrayList<>();

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         // Zuweisung der ID der Toolbar an eine Variable
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);

        // Nutze Toolbar als ActionBar
        setSupportActionBar(toolbar);

        listview = findViewById(R.id.listview);

        for(int i = 0; i < names.length; i++){
            ItemsModel itemsModel = new ItemsModel(names[i],desc[i],images[i]);

            ListItems.add(itemsModel);
        }
        customAdapter = new CustomAdapter(ListItems);

        listview.setAdapter(customAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem menuItem = menu.findItem(R.id.search_view);

        SearchView searchView = (SearchView) menuItem.getActionView();
        //searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.search_view){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class CustomAdapter extends BaseAdapter implements Filterable {

        private final List<ItemsModel> itemsModelList;
        private List<ItemsModel> itemsModelListFiltered;

        public CustomAdapter(List<ItemsModel> itemsModelList) {
            this.itemsModelList = itemsModelList;
            this.itemsModelListFiltered = itemsModelList;
        }

        @Override
        public int getCount() {
            return itemsModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint({"ViewHolder", "InflateParams"}) View view = getLayoutInflater().inflate(R.layout.row_items,null);

            ImageView imageView = view.findViewById(R.id.imageView);
            TextView itemName = view.findViewById(R.id.itemName);
            TextView itemDesc = view.findViewById(R.id.itemDesc);

            imageView.setImageResource(itemsModelListFiltered.get(position).getImage());
            itemName.setText(itemsModelListFiltered.get(position).getName());
            itemDesc.setText(itemsModelListFiltered.get(position).getDesc());

            view.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ItemViewActivity.class).putExtra("item",itemsModelListFiltered.get(position))));

            return view;
        }

        @Override
        public Filter getFilter() {

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults filterResults = new FilterResults();

                    if(constraint == null || constraint.length() == 0) {
                        filterResults.count = itemsModelList.size();
                        filterResults.values = itemsModelList;
                    }else{
                        String searchStr = constraint.toString().toLowerCase();
                        Log.d("myTag", searchStr);

                        List<ItemsModel> resultData = new ArrayList<>();

                        for(ItemsModel itemsModel:itemsModelList){
                            if(itemsModel.getName().toLowerCase().contains(searchStr) || itemsModel.getDesc().toLowerCase().contains(searchStr)){
                                resultData.add(itemsModel);
                            }
                            filterResults.count = resultData.size();
                            filterResults.values = resultData;
                            Log.d("myTag", String.valueOf(filterResults.values));
                        }
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                    itemsModelListFiltered = (List<ItemsModel>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }
    }
}
