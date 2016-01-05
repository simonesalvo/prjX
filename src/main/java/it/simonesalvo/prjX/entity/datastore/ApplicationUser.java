package it.simonesalvo.prjX.entity.datastore;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * Created by Simone Salvo on 25/09/2015.
 * www.simonesalvo.it
 */

@Entity
@Data
public class ApplicationUser {

    @Id
    private Long id;

    @Index
    @NonNull
    private String userEmail;

    @NonNull
    private String telephone;

    @Index
    private boolean isDisabled;

    @Index
    private boolean isDeleted;

    @Index
    private Date lastAccess;

    private String avatar;

    @Nullable
    private LocationInformation locationInformation;

    @Index
    @NonNull
    private String pwd;

    @Index
    @NonNull
    private Date registrationDate;

    @Index
    @NonNull
    private Date disabledDate;

    @Index
    @NonNull
    private Date lastAccessDate;

    @Index
    @NonNull
    private Date deletionDate;

    @Index
    @NonNull
    private UserKind userKind;

    public void setGoodDelete(){
        this.deletionDate = new Date();
        this.isDeleted = true;
    }
}
