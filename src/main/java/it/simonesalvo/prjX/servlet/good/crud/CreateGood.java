package it.simonesalvo.prjX.servlet.good.crud;

import com.google.gson.reflect.TypeToken;
import it.simonesalvo.prjX.entity.datastore.*;
import it.simonesalvo.prjX.helper.DatastoreHelper;
import it.simonesalvo.prjX.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Simone Salvo on 19/12/15.
 * www.simonesalvo.it
 */

public class CreateGood extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DatastoreHelper datastoreHelper = new DatastoreHelper();

        String goodTitle = req.getParameter("goodTitle");
        LocationInformation goodLocationInformation = JsonUtils.decodeJSON(req.getParameter("locInf"), LocationInformation.class);
        GoodPolicy goodPolicy = JsonUtils.decodeJSON(req.getParameter("goodPolicy"), GoodPolicy.class);
        java.lang.reflect.Type type = new TypeToken<List<GoodCategory>>() {}.getType();
        List<GoodCategory> categoriesList = JsonUtils.decodeJSON(req.getParameter("goodPolicy"), type);
        ApplicationUser applicationUser = datastoreHelper.loadEntity(ApplicationUser.class,
                                                                        req.getParameter("userId"));
        ApplicationGood applicationGood =
                new ApplicationGood(goodTitle, goodLocationInformation, goodPolicy, categoriesList, applicationUser);

        datastoreHelper.storeEntity(applicationGood);

        }
    }



