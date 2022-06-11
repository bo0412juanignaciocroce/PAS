
package es.upm.etsisi.pas.json_libros;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class LibrosPOJO {

    @SerializedName("works")
    @Expose
    private List<Work> works = null;

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }

}
