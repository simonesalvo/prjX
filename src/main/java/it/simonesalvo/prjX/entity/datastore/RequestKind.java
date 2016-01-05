package it.simonesalvo.prjX.entity.datastore;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.Data;
import lombok.NonNull;

/**
 * Created by Simone Salvo on 31/12/15.
 * www.simonesalvo.it
 */

@Entity
@Data
public class RequestKind {

    public static final String NEW_USER = "new_user";
    public static final String CHANGE_PWD = "change_pwd";
    public static final String SUBMIT_GOOD = "submit_good";

    @Id
    @Index
    private Long id;

    @Index
    @NonNull
    private String kind;

}
