package org.armandosalazar.aseapplication.network;

import org.armandosalazar.aseapplication.model.Person;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public interface PeopleService {
    @GET("/api/people")
    Observable<List<Person>> getPeople();

    Retrofit retrofit = RetrofitClient.get();
}
