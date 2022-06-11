package es.upm.etsisi.pas.json_libros;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenLibraryService {
    @GET("/trending/now.json")
    Observable<LibrosPOJO> listPopularLibros();
}
