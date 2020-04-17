package com.ahmetgaffaroglu.retrofitrxjava.Service;

import com.ahmetgaffaroglu.retrofitrxjava.Model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {


    //https://api.nomics.com/v1/prices?key=5d87dcf09aff72d858664a38bcd9173f

    @GET("prices?key=5d87dcf09aff72d858664a38bcd9173f")
    Observable<List<CryptoModel>> getData();
    //Call<List<CryptoModel>> getData();  //API den gelecek CryptoModel datayı liste şeklinde getData'ya attık.

}
