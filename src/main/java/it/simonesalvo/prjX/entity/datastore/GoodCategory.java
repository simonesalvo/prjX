package it.simonesalvo.prjX.entity.datastore;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

/**
 * Created by Simone Salvo on 29/12/15.
 * www.simonesalvo.it
 */

@Entity
@Data

public class GoodCategory {
    @Id
    private Long id;

    @Index
    @NonNull
    private String categoryName;

    /**
     * Instanciate a list of category from a json.
     * The list can contain one or more categories.
     * @param catList list of categories in json
     * @return
     */
    public static List<GoodCategory> getCategoriesList(String catList) {

        return null;
    }
}
