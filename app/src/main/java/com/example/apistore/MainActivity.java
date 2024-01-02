package com.example.apistore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    public ListView lvproductos;
    public interface StoreAPI {
        @GET("/products/")
        Call<StoreResponse> getStore();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvproductos = findViewById(R.id.lvproductos);

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        StoreAPI api = retrofit.create(StoreAPI.class);

        Call<StoreResponse> call = api.getStore();

        List<Item> items = null;

        ProductoAdaptador adaptador = new ProductoAdaptador(this, items);
        lvproductos.setAdapter((ListAdapter) adaptador);

        lvproductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = items.get(position);
                Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
            }
        });

        class ProductoAdaptador extends ArrayAdapter<Item> {
            public ProductoAdaptador(Context context, List<Item> items) {
                super(context, 0, items);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return convertView;
            }
        }
    }


    public class StoreResponse {
        private List<Item> items;

        public StoreResponse(List<Item> items) {
            this.items = items;
        }

        public List<Item> getItems() {
            return items;
        }
    }
    public class Item {
        private String name;
        private String description;
        private String price;
        private String image;

        public Item(String name, String description, String price, String image) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getPrice() {
            return price;
        }

        public String getImage() {
            return image;
        }
    }
}
