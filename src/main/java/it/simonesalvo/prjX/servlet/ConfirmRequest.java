package it.simonesalvo.prjX.servlet;

import it.simonesalvo.prjX.entity.datastore.Request;
import it.simonesalvo.prjX.helper.DatastoreHelper;
import it.simonesalvo.prjX.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Simone Salvo on 31/12/15.
 * www.simonesalvo.it
 */
public class ConfirmRequest extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestHash = JsonUtils.decodeJSON(req.getParameter("rh"), String.class);
        String requestId = JsonUtils.decodeJSON(req.getParameter("ri"), String.class);
        String userEmailhash = JsonUtils.decodeJSON(req.getParameter("umh"), String.class);

        DatastoreHelper datastoreHelper = new DatastoreHelper();
        Request request = datastoreHelper.loadEntity(Request.class, requestId);

        //TODO compare the user email hash decrypted with the user.email in the request.

        if (request.getIdHash().equals(requestHash)){
            if (request.getRequestDate().getTime() - new Date().getTime() < 604800000){
                request.setAccepted(true);
                request.setAcceptedDate(new Date());

                //TODO run the servlet or functions that make the changes after the request is satisfied
            }else{
                request.setRejected(true);
                request.setRejectedDate(new Date());
            }
        }

    }
}
