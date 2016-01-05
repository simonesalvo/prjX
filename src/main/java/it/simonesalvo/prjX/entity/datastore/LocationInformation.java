package it.simonesalvo.prjX.entity.datastore;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.Data;
import lombok.NonNull;

/**
 * Created by Simone Salvo on 27/12/15.
 * www.simonesalvo.it
 */
@Data
@Entity
public class LocationInformation {

    public LocationInformation(String jsonLocInf)
    {
        // Create a LocationInformation from a json
        // TODO
    }

    @Id
    private Long id;

    @Index
    @NonNull
    private Long latitude;

    @Index
    @NonNull
    private Long longitude;

    @Index
    @NonNull
    private String city;

    @Index
    @NonNull
    private String country;

    @Index
    @NonNull
    private String address;

    @Index
    @NonNull
    private String zipCode;

}
