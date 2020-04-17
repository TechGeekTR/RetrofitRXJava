package com.ahmetgaffaroglu.retrofitrxjava.Model;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {

    @SerializedName("currency") //JSON dan gelecek datayı okuyaiblmem için bunun isminin JSON ın içindeki ile birebir aynı olması lazım
    public String currency;

    @SerializedName("price")
    public String price;

}
