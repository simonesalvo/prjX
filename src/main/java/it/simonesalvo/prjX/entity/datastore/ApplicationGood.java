package it.simonesalvo.prjX.entity.datastore;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

/**
 * Created by Simone Salvo on 19/12/15.
 * www.simonesalvo.it
 */

@Data
@Entity
public class ApplicationGood {

    public ApplicationGood(String title, LocationInformation locInf,
                           GoodPolicy policy, List<GoodCategory> cat, ApplicationUser usr){
        this.title = title;
        this.locationInformation = locInf;
        this.policy = policy;
        this.goodCategory = cat;
        this.usr = usr;

        creationDate = new Date();
        deletionDate = null;
        disabledDate = null;

        //TODO what about the good images????
    }

    @Id
    private Long id;

    @Index
    @NonNull
    private ApplicationUser usr;

    @Index
    @NonNull
    private String title;

    @Index
    @NonNull
    private Date creationDate;

    @Index
    private boolean isDisabled;

    @Index
    private boolean isDeleted;

    private List<String> images;

    @Index
    @NonNull
    private LocationInformation locationInformation;

    @Index
    private Date deletionDate;

    @Index
    private Date disabledDate;

    @Index
    @NonNull
    private GoodPolicy policy;

    @Index
    private boolean isProposalAccepted;

    @Index
    @NonNull
    private List<GoodCategory> goodCategory;

    public void setGoodDelete(){
        this.deletionDate = new Date();
        this.isDeleted = true;
    }

}
