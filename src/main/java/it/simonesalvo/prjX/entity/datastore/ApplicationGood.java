package it.simonesalvo.prjX.entity.datastore;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.Data;
import lombok.NonNull;
import org.joda.time.DateTime;

/**
 * Created by Simone Salvo on 19/12/15.
 * www.simonesalvo.it
 */

@Data
@Entity
public class ApplicationGood {

    @Id
    @NonNull
    private String id;

    @Index
    @NonNull
    private String title;

    @Index
    @NonNull
    private DateTime creationDate;

    @Index
    @NonNull
    private boolean isDisabled;

    @Index
    @NonNull
    private boolean isRemoved;

    @Index
    @NonNull
    private DateTime deletionDate;


}
