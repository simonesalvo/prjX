package it.simonesalvo.prjX.entity.datastore;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 * Created by Simone Salvo on 27/12/15.
 * www.simonesalvo.it
 */
@Entity
@Data

public class X {
    @Id
    private Long id;

    @Index
    private Date XDate;

    @Index
    private Date archiviedDate;


    @Index
    private boolean isArchived;

    @Index
    @NonNull
    private ApplicationProposal applicationProposal;


    public void setArchivied() {
        isArchived = true;
        archiviedDate = new Date();
    }
}
