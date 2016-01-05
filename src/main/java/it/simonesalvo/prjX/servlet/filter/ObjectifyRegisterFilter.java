package it.simonesalvo.prjX.servlet.filter;

import com.googlecode.objectify.ObjectifyService;
import it.simonesalvo.prjX.utils.DatastoreUtils;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class ObjectifyRegisterFilter extends GenericServlet {

  static {
    for (Class<?> c : DatastoreUtils.getDatastoreClasses()) {
      ObjectifyService.register(c);
    }  }

  @Override
  public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
    //do nothing
  }


}
