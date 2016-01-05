package it.simonesalvo.prjX.servlet.user.crud;

import it.simonesalvo.prjX.entity.datastore.ApplicationUser;
import it.simonesalvo.prjX.helper.DatastoreHelper;
import it.simonesalvo.prjX.utils.Constants;
import it.simonesalvo.prjX.utils.JsonUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.util.text.StrongTextEncryptor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Simone Salvo on 19/12/15.
 * www.simonesalvo.it
 */

public class CreateUser extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

        DatastoreHelper datastoreHelper = new DatastoreHelper();
        ApplicationUser user = JsonUtils.decodeJSON(req.getParameter("user"), ApplicationUser.class);
        String randomPwd = RandomStringUtils.random(Integer.parseInt(RandomStringUtils.randomNumeric(2)), true, true);

        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(Constants.APPPWD);
        String myEncryptedText = textEncryptor.encrypt(randomPwd);
        String plainText = textEncryptor.decrypt(myEncryptedText);
        user.setPwd(plainText);
        user.setRegistrationDate(new Date());

        datastoreHelper.storeEntity(user);
        //TODO CreateGood pending request to be accepted
        //TODO send email
        }
    }



