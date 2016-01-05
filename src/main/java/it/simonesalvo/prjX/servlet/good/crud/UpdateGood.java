package it.simonesalvo.prjX.servlet.good.crud;

import it.simonesalvo.prjX.entity.datastore.ApplicationGood;
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

public class UpdateGood extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DatastoreHelper datastoreHelper = new DatastoreHelper();
        ApplicationGood applicationGood = JsonUtils.decodeJSON(req.getParameter("applicationGood"), ApplicationGood.class);
        ApplicationGood applicationGood_ds= datastoreHelper.loadEntity(ApplicationGood.class, applicationGood.getId());

        if(applicationGood_ds != null){
            datastoreHelper.storeEntity(applicationGood);
        }

    }
}



