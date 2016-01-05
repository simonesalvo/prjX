package it.simonesalvo.prjX.entity.datastore;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

/**
 * Created by Simone Salvo on 27/12/15.
 * www.simonesalvo.it
 */

@Entity
@Data
public class ApplicationProposal {

    @Id
    private Long id;

    @Index
    @NonNull
    private Date proposalDate;

    @Index
    @NonNull
    private ApplicationUser userA;

    @Index
    @NonNull
    private ApplicationUser userB;

    @Index
    @NonNull
    private ApplicationGood referringGood;

    @Nullable
    private List<ApplicationGood> proposingGoods;

    @Nullable
    private String msg;

}
