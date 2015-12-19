package it.simonesalvo.prjX.entity.datastore;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.Data;
import lombok.NonNull;
import org.joda.time.DateTime;

/**
 * Created by Simone Salvo on 25/09/2015.
 * www.simonesalvo.it
 */

@Entity
@Cache //TODO meaning???
@Data
public class ApplicationUser {

    @Id
    @NonNull
    private String userId;

    @Index
    @NonNull
    private String userEmail;
    
    @Index
    private boolean disabled;

    @Index
    @NonNull
    private String pwd;

    @Index
    @NonNull
    private DateTime registrationDate;

    @Index
    @NonNull
    private DateTime deletionDate;
}
