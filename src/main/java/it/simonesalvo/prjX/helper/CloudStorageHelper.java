package it.simonesalvo.prjX.helper;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.google.appengine.tools.cloudstorage.*;
import it.simonesalvo.prjX.utils.Constants;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class CloudStorageHelper {

	private static final Logger LOGGER = Logger.getLogger(CloudStorageHelper.class.getName());

	/**
	 * This is the service from which all requests are initiated. The retry and
	 * exponential backoff settings are configured here.
	 */
	private final GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());


	private static Properties properties;
	private static Storage storage;

	private static final String PROJECT_ID_PROPERTY = "project.id";
	private static final String APPLICATION_NAME_PROPERTY = "application.name";
	private static final String ACCOUNT_ID_PROPERTY = "account.id";
	private static final String PRIVATE_KEY_PATH_PROPERTY = "private.key.path";
	private static final String STORAGE_PROPERTIES = "/cloudstorage.properties";

	private static Properties getProperties() throws Exception {

		if (properties == null) {
			properties = new Properties();

			try (InputStream stream = CloudStorageHelper.class.getResourceAsStream(STORAGE_PROPERTIES)) {
				properties.load(stream);
			} catch (IOException e) {
				throw new RuntimeException("cloudstorage.properties must be present in classpath",	e);
			}
		}
		return properties;
	}

	private static Storage getStorage() throws Exception {

		if (storage == null) {

			HttpTransport httpTransport = new NetHttpTransport();
			JsonFactory jsonFactory = new JacksonFactory();

			List<String> scopes = new ArrayList<>();
			scopes.add(StorageScopes.DEVSTORAGE_FULL_CONTROL);

			Credential credential = new GoogleCredential.Builder()
					.setTransport(httpTransport)
					.setJsonFactory(jsonFactory)
					.setServiceAccountId(
							getProperties().getProperty(ACCOUNT_ID_PROPERTY))
					.setServiceAccountPrivateKeyFromP12File(
							new File(getProperties().getProperty(
									PRIVATE_KEY_PATH_PROPERTY)))
					.setServiceAccountScopes(scopes).build();

			storage = new Storage.Builder(httpTransport, jsonFactory,
					credential).setApplicationName(
					getProperties().getProperty(APPLICATION_NAME_PROPERTY))
					.build();
		}

		return storage;
	}



	public boolean deleteFile(String bucketName, String fileName) throws IOException {
		
		if (fileName != null) {
			GcsFilename gcsFileName = new GcsFilename(bucketName, fileName);
			return gcsService.delete(gcsFileName);
		}
		return false;
	}
	
	public boolean uploadFile(String bucketName, String fileName, InputStream is, String contentType) throws IOException {
		
		if (fileName != null) {
			
			GcsFilename gcsFileName = new GcsFilename(bucketName, fileName);
			
			GcsOutputChannel outputChannel;
			
			if(contentType != null){
				
				GcsFileOptions.Builder options_builder = new GcsFileOptions.Builder();
			    options_builder = options_builder.mimeType(contentType);
			  
			    GcsFileOptions options = options_builder.build();  
				
				outputChannel = gcsService
						.createOrReplace(gcsFileName,options);
			}else{
				outputChannel = gcsService
						.createOrReplace(gcsFileName,
								GcsFileOptions.getDefaultInstance());		
			}
		
			// ObjectOutputStream oout = new ObjectOutputStream(Channels.newOutputStream(outputChannel)); 
			 
			 try {
				    copy(is, Channels.newOutputStream(outputChannel));
				} finally {
				    outputChannel.close();
				    is.close();
				}
			 
			 return true;
		}
		return false;
	}
	
	public boolean uploadFileUTF8(String bucketName, String fileName, InputStream is, String contentType) throws IOException {
					
		if (fileName != null) {
			
			GcsFilename gcsFileName = new GcsFilename(bucketName, fileName);
			
			GcsOutputChannel outputChannel = null;
			
			if(contentType != null){
				
				GcsFileOptions.Builder options_builder = new GcsFileOptions.Builder();
			    options_builder = options_builder.mimeType(contentType);
			  
			    GcsFileOptions options = options_builder.build();  
				
				outputChannel = gcsService
						.createOrReplace(gcsFileName,options);
			}else{
				outputChannel = gcsService
						.createOrReplace(gcsFileName,
								GcsFileOptions.getDefaultInstance());		
			}
			
			 try {
				 
				 PrintWriter writer = new PrintWriter(Channels.newWriter(outputChannel, "UTF8"));
				 IOUtils.copy(is, writer,"UTF-8");
				 writer.flush();
				 outputChannel.waitForOutstandingWrites();
				 //copy(is, writer);
				} finally {
				    outputChannel.close();
				    is.close();
				}
			 
			 return true;
		}
		return false;
	}
	
	public Reader readFile(String bucketName, String fileName, long startPosition) throws IOException {
		GcsFilename gcsFileName = new GcsFilename(bucketName, fileName);
		GcsInputChannel inChannel = null;

		inChannel = gcsService.openReadChannel(gcsFileName, 0);
		return Channels.newReader(inChannel, Constants.UTF8_CHARSET);
	}

	
	private static void copy(InputStream input, OutputStream output) throws IOException {
	    byte[] buffer = new byte[1024];
	    int bytesRead = input.read(buffer);
	    while (bytesRead != -1) {
	        output.write(buffer, 0, bytesRead);
	        bytesRead = input.read(buffer);
	    }
	}
}