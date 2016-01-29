package it.simonesalvo.prjX.servlet.cron;

import it.simonesalvo.prjX.entity.datastore.ApplicationUser;
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
public class RemoveUser extends HttpServlet {

    /**
     * Two months in days
     */
    private static int TWO_MONTHS = 60;

    // An user disabled two months ago will be removed.

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatastoreHelper datastoreHelper = new DatastoreHelper();
        Map<String, Object> filter = new HashMap<>();
        filter.put("isDisabled =", true);
        List<ApplicationUser> entititesByIndexes = datastoreHelper.getEntititesByIndexes(ApplicationUser.class, filter);

        for ( ApplicationUser u : entititesByIndexes){
            if (u.isDisabled()){
                int dayOfYear_a = DateTime.parse(String.valueOf(u.getDisabledDate().getTime())).getDayOfYear();
                int dayOfYear_b = DateTime.now().getDayOfYear();

                if (dayOfYear_a <= dayOfYear_b - TWO_MONTHS ) {
                        u.setGoodDelete();
                        datastoreHelper.storeEntity(u);
                    
                    // TODO send an email to the user to say that the account has been completely deleted.
                    }
                }
            }
        }
}
