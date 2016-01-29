package it.simonesalvo.prjX.servlet.cron;

import it.simonesalvo.prjX.entity.datastore.ApplicationGood;
import it.simonesalvo.prjX.helper.DatastoreHelper;
import org.joda.time.DateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Simone Salvo on 27/12/15.
 * www.simonesalvo.it
 */

// A good that has been disabled more than two month ago will be marked as deleted.
public class RemoveGood extends HttpServlet {

    private static int TWO_MONTHS = 2;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatastoreHelper datastoreHelper = new DatastoreHelper();
        Map<String, Object> filter = new HashMap<>();
        filter.put("isDisabled =", true);
        List<ApplicationGood> entititesByIndexes = datastoreHelper.getEntititesByIndexes(ApplicationGood.class, filter);

        for ( ApplicationGood g : entititesByIndexes){
            if (g.isDisabled()){
                int dayOfYear_a = DateTime.parse(String.valueOf(g.getDisabledDate().getTime())).getDayOfYear();
                int dayOfYear_b = DateTime.now().getDayOfYear();

                if (dayOfYear_a <= dayOfYear_b - TWO_MONTHS ) {
                        g.setGoodDelete();
                        datastoreHelper.storeEntity(g);
                    }
                }

                }
            }
}
