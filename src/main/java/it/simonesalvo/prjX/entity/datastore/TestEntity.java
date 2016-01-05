package it.simonesalvo.prjX.entity.datastore;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.Data;

/**
 * Created by Simone Salvo on 20/12/15.
 * www.simonesalvo.it
 */

@Entity
@Data

public class TestEntity {

    @Id
    private Long Id;

    @Index
    private String name;

    public TestEntity(String name){
        this.name = name;
    }
}
