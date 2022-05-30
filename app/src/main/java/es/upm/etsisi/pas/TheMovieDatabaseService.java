package es.upm.etsisi.pas;


import es.upm.etsisi.myapplication.BuildConfig;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDatabaseService {
    String API_KEY = BuildConfig.API_KEY;

    @GET("tv/popular")
    Observable<PeliculasPojo> listPopularTVShows(@Query("api_key") String api_key);
}
