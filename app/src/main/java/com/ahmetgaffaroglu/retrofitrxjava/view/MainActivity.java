package com.ahmetgaffaroglu.retrofitrxjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;

import com.ahmetgaffaroglu.retrofitrxjava.Adapter.RecyclerViewAdapter;
import com.ahmetgaffaroglu.retrofitrxjava.Model.CryptoModel;
import com.ahmetgaffaroglu.retrofitrxjava.R;
import com.ahmetgaffaroglu.retrofitrxjava.Service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    CompositeDisposable compositeDisposable; // tek kullanımlık obje yapmaya yarıyor. Hafıza da çok yer kaplamıyor.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://api.nomics.com/v1/prices?key=5d87dcf09aff72d858664a38bcd9173f


        recyclerView = findViewById(R.id.recyclerView);


        //Retrofit & JSON

        Gson gson = new GsonBuilder().setLenient().create(); // JSON oluşturduk.

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // retrofitle rxjava yı beraber çalıştırmaya yarıyor.
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();

    }


    private void loadData(){

        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class); //Service i oluşturduk. retrofi bizden servisin sınıfını istedi onu ekledik.

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(cryptoAPI.getData()
        .subscribeOn(Schedulers.io())  // Hangi thread de kayıt olma işleminin yapılacağı
        .observeOn(AndroidSchedulers.mainThread())    // Alınan sonuçları main thread de göstereceğiz
        .subscribe(this::handleResponse));  // Alınan sonucu nerde ele alacağız.

        /*
        Call<List<CryptoModel>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() { //Asenkron bir şekilde callback ları çağırıyor.
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()){
                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList);

                    //RecyclerView
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);


                    //for (CryptoModel cryptoModels: cryptoModels){
                    //    System.out.println(cryptoModels.currency + ", "+ cryptoModels.price);
                    //}
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
*/

    }
    private void handleResponse(List<CryptoModel> cryptoModelList){


        cryptoModels = new ArrayList<>(cryptoModelList);

        //RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear(); //programı kaparken arkada kalan datayı silmeye yarıyor. memory managment için daha ideal
    }
}
