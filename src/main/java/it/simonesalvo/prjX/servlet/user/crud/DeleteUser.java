package it.simonesalvo.prjX.servlet.user.crud;

import it.simonesalvo.prjX.entity.datastore.ApplicationUser;
import it.simonesalvo.prjX.helper.DatastoreHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Simone Salvo on 19/12/15.
 * www.simonesalvo.it
 */

public class DeleteUser extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DatastoreHelper datastoreHelper = new DatastoreHelper();
        Long deletionUserId = Long.valueOf(req.getParameter("deletionUserId"));

        ApplicationUser applicationUser = datastoreHelper.loadEntity(ApplicationUser.class, deletionUserId);
        applicationUser.setGoodDelete();
        datastoreHelper.storeEntity(applicationUser);
   }
}



