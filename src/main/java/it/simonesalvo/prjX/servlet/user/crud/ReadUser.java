package it.simonesalvo.prjX.servlet.user.crud;

import it.simonesalvo.prjX.entity.datastore.ApplicationUser;
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

public class ReadUser extends HttpServlet{

    private static final int MINIMIMUM_MAXIMUM_USER_WITH_AN_EMAIL = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        DatastoreHelper datastoreHelper = new DatastoreHelper();

        String usr = JsonUtils.decodeJSON(req.getParameter("usr"), String.class);
        List<ApplicationUser> entitiesByIndex =
                datastoreHelper.getEntitiesByIndex(ApplicationUser.class, "userEmail = ", usr);

        if (entitiesByIndex.size() == MINIMIMUM_MAXIMUM_USER_WITH_AN_EMAIL){
            resp.getWriter().write(JsonUtils.encodeJSON(entitiesByIndex.get(0)));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}



