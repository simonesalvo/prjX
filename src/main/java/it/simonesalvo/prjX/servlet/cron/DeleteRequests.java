package it.simonesalvo.prjX.servlet.cron;

import it.simonesalvo.prjX.entity.datastore.Request;
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
 * Created by Simone Salvo on 31/12/15.
 * www.simonesalvo.it
 */

// A request will be rejected if is not approved by one week

public class DeleteRequests extends HttpServlet {

    /**
     * Corresponding to 7 days
     */
    private static int ONE_WEEK = 7;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DatastoreHelper datastoreHelper = new DatastoreHelper();
        Map<String, Object> filter = new HashMap<>();
        filter.put("isRejected =", false);
        List<Request> entititesByIndexes = datastoreHelper.getEntititesByIndexes(Request.class, filter);

        for (Request r : entititesByIndexes) {
            int dayOfYear_a = DateTime.parse(String.valueOf(r.getRequestDate().getTime())).getDayOfYear();
            int dayOfYear_b = DateTime.now().getDayOfYear();

            if (dayOfYear_a >= dayOfYear_b - ONE_WEEK) {
                r.setRejected();
                datastoreHelper.storeEntity(r);
            }
        }
    }
}
