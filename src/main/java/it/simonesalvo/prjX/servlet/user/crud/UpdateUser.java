package it.simonesalvo.prjX.servlet.user.crud;

import it.simonesalvo.prjX.entity.datastore.ApplicationUser;
import it.simonesalvo.prjX.helper.DatastoreHelper;
import it.simonesalvo.prjX.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Simone Salvo on 19/12/15.
 * www.simonesalvo.it
 */

public class UpdateUser extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DatastoreHelper datastoreHelper = new DatastoreHelper();
        ApplicationUser applicationUser = JsonUtils.decodeJSON(req.getParameter("applicationGood"), ApplicationUser.class);
        ApplicationUser applicationUser_ds= datastoreHelper.loadEntity(ApplicationUser.class, applicationUser.getId());

        if(applicationUser_ds != null){
            datastoreHelper.storeEntity(applicationUser);
        }
    }
}



