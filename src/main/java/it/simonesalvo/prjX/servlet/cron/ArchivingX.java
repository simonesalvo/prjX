package it.simonesalvo.prjX.servlet.cron;

import it.simonesalvo.prjX.entity.datastore.X;
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

// After two months a baratto will be archived
public class ArchivingX extends HttpServlet {

    private static int TWO_MONTHS = 2;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DatastoreHelper datastoreHelper = new DatastoreHelper();
        Map<String, Object> filter = new HashMap<>();
        filter.put("isArchivied =", false);
        List<X> entititesByIndexes = datastoreHelper.getEntititesByIndexes(X.class, filter);

        for (X x : entititesByIndexes) {
            int monthOfYear_a = DateTime.parse(String.valueOf(x.getXDate().getTime())).getMonthOfYear();
            int dayOfMonth_a = DateTime.parse(String.valueOf(x.getXDate().getTime())).getDayOfMonth();

            int monthOfYear_b = DateTime.now().getMonthOfYear();
            int dayOfMonth_b = DateTime.now().getDayOfMonth();

            if (monthOfYear_a >= monthOfYear_b - TWO_MONTHS) {
                if (dayOfMonth_a >= dayOfMonth_b) {
                    x.setArchivied();
                    datastoreHelper.storeEntity(x);

                    //TODO send an email to the user: your baratto will be visible in the archivied section
                }
            }
        }
    }
}
