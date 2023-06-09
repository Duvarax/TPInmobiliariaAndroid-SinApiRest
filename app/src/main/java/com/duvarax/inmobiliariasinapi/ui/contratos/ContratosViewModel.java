package com.duvarax.inmobiliariasinapi.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.duvarax.inmobiliariasinapi.modelo.Inmueble;
import com.duvarax.inmobiliariasinapi.request.ApiClientRetrofit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Inmueble>> listaInmueblesMutable;

    public ContratosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Inmueble>> setInmuebles(){
        if(listaInmueblesMutable == null){
            listaInmueblesMutable = new MutableLiveData<>();
        }
        return listaInmueblesMutable;
    }

    public void setListaInmueblesMutable(){
        SharedPreferences sp = context.getSharedPreferences("token.xml", -1);
        String token = sp.getString("token", "");
        ApiClientRetrofit.EndPointInmobiliaria end = ApiClientRetrofit.getEndpointInmobiliaria();
        Call<List<Inmueble>> callInmuebles = end.obtenerInmueblesAlquilados(token);
        callInmuebles.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        listaInmueblesMutable.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {

            }
        });
    }


    // TODO: Implement the ViewModel
}