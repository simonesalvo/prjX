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

@Entity
@Data
public class GoodPolicy {

    @Id
    private Long Id;

    @Index
    @NonNull
    private String kind;
}
