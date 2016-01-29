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
 * Created by Simone Salvo on 29/12/15.
 * www.simonesalvo.it
 */

public class DisableGood  extends HttpServlet{
    // The good has to be disabled after a period of 10 days.
    // It can be reinserted manually by the owner user whenever he want

    private static int TEN_DAYS = 10;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatastoreHelper datastoreHelper = new DatastoreHelper();
        Map<String, Object> filter = new HashMap<>();
        filter.put("isDisabled !=", true);
        List<ApplicationGood> entititesByIndexes = datastoreHelper.getEntititesByIndexes(ApplicationGood.class, filter);

        for ( ApplicationGood g : entititesByIndexes){
            if (!g.isDisabled() || !g.isDeleted()){
                if (DateTime.now().minusDays(TEN_DAYS).getMillis() - g.getCreationDate().getTime() >= 0){
                        g.setGoodDisabled();
                        datastoreHelper.storeEntity(g);

                    // TODO send an email to the user in order to say the good will be disabled
                    // TODO because ten days are gone. He can republish again thought his panel
                    }
                }
            }
        }
}
