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
public class Querystring {

    @Id
    private Long id;

    @Index
    @NonNull
    private String queryString;
}
