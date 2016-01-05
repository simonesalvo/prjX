package it.simonesalvo.prjX.servlet;

import it.simonesalvo.prjX.entity.datastore.Request;
import it.simonesalvo.prjX.entity.datastore.RequestKind;
import it.simonesalvo.prjX.helper.DatastoreHelper;
import it.simonesalvo.prjX.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Simone Salvo on 31/12/15.
 * www.simonesalvo.it
 */
public class CreateRequest extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatastoreHelper datastoreHelper = new DatastoreHelper();

        Request request = new Request();

        // Rk: requestKind
        String rk = JsonUtils.decodeJSON(req.getParameter("rk"), String.class);

        if (rk.toUpperCase().equals(RequestKind.NEW_USER)){
            request.setKind(new RequestKind(RequestKind.NEW_USER));
        }
        else if (rk.toUpperCase().equals(RequestKind.CHANGE_PWD)){
            request.setKind(new RequestKind(RequestKind.CHANGE_PWD));

        }
        else if (rk.toUpperCase().equals(RequestKind.SUBMIT_GOOD)){
            request.setKind(new RequestKind(RequestKind.SUBMIT_GOOD));

        }

        //TODO think and add other cases

        datastoreHelper.storeEntity(request);
    }
}
