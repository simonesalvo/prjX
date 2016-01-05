package it.simonesalvo.prjX.servlet;

import it.simonesalvo.prjX.entity.datastore.Querystring;
import it.simonesalvo.prjX.helper.DatastoreHelper;
import it.simonesalvo.prjX.utils.JsonUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Simone Salvo on 01/01/16.
 * www.simonesalvo.it
 */
public class Search extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String r = JsonUtils.decodeJSON(req.getParameter("qs"), String.class);
        DatastoreHelper datastoreHelper = new DatastoreHelper();

        if (r != null && !r.isEmpty()) {
            Querystring qs = new Querystring(r);
            datastoreHelper.storeEntity(qs);
        }

        RequestDispatcher rd = req.getRequestDispatcher("/crud/ReadGood");
        rd.forward(req,resp);

    }
}
