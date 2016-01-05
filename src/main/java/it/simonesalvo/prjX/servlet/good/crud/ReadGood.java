package it.simonesalvo.prjX.servlet.good.crud;

import com.google.gson.reflect.TypeToken;
import info.debatty.java.stringsimilarity.Levenshtein;
import it.simonesalvo.prjX.application.ApplicationConfig;
import it.simonesalvo.prjX.entity.datastore.ApplicationGood;
import it.simonesalvo.prjX.helper.DatastoreHelper;
import it.simonesalvo.prjX.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Simone Salvo on 19/12/15.
 * www.simonesalvo.it
 */

public class ReadGood extends HttpServlet{


    /**
     * Get by title and/or by category
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

       String titleSearchFor =  req.getParameter("title");
        java.lang.reflect.Type type = new TypeToken<List<String>>() {}.getType();

        List<String> categoriesSearchFor =  JsonUtils.decodeJSON(req.getParameter("category"), type);
        DatastoreHelper datastoreHelper;

        if (titleSearchFor != null || categoriesSearchFor != null ) {
            datastoreHelper = new DatastoreHelper();
            List<ApplicationGood> entityList = null;
            if (categoriesSearchFor.size() > 0) {
                Map<String, Object> fieldConditionMap = new HashMap<>();

                for (String aCategoriesSearchFor : categoriesSearchFor)
                    fieldConditionMap.put("goodCategory.categoryName IN ", aCategoriesSearchFor);

                entityList = datastoreHelper.getEntititesByIndexes(ApplicationGood.class, fieldConditionMap);

            }

            if (titleSearchFor != null){
                Levenshtein l = new Levenshtein();

                if (entityList == null || entityList.isEmpty())
                    entityList = datastoreHelper.listEntitiesOfClass(ApplicationGood.class);

                List<ApplicationGood> finalEntityList = new ArrayList<>();

                for (ApplicationGood e : entityList) {
                   if (l.distance(e.getTitle(), titleSearchFor) <= ApplicationConfig.MAXIMUM_TITLE_DISTANCE)
                    finalEntityList.add(e);
                }
                entityList = finalEntityList;
            }

            resp.getWriter().print(JsonUtils.encodeJSON(entityList));


        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}



