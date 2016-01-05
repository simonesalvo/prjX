package it.simonesalvo.prjX.entity.datastore;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.Data;

import java.util.Date;

/**
 * Created by Simone Salvo on 31/12/15.
 * www.simonesalvo.it
 */

@Entity
@Data
/**
 * Sample of request are:
 * - The user x would like change the password
 * - The user y would like DeleteGood a own good
 * - The user z would like DeleteGood it self
 * etc etc...
 * A request has to be approved in a due date by clicking in the link.
 * Just a registered user can do a request.
 */
public class Request {

    @Id
    @Index
    private Long id;

    @Index
    private String idHash;

    @Index
    private RequestKind kind;

    @Index
    private Date requestDate;

    @Index
    private boolean isRejected;

    @Index
    private Date rejectedDate;

    @Index
    private Date acceptedDate;

    @Index
    private boolean isAccepted;

    @Index
    private ApplicationUser applicationUser;

}