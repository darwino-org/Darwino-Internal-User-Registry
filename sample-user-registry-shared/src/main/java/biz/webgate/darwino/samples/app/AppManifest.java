/*!COPYRIGHT HEADER! 
 *
 */

package biz.webgate.darwino.samples.app;

import com.darwino.commons.json.JsonException;
import com.darwino.jsonstore.Database;
import com.darwino.jsonstore.Session;
import com.darwino.jsonstore.extensions.ExtensionRegistry;
import com.darwino.jsonstore.meta.DatabaseFactory;
import com.darwino.platform.DarwinoContext;
import com.darwino.platform.DarwinoManifest;

/**
 * Application Manifest.
 * 
 * @author Philippe Riand
 */
public class AppManifest extends DarwinoManifest {
	
	// This is used by the mobile application to call the remote service
	public static final String MOBILE_PATHINFO	= "sampleuserregistry";
	
	public static Session getSession() throws JsonException {
		return DarwinoContext.get().getSession();
	}

	public static Database getDatabase() throws JsonException {
		return getSession().getDatabase(AppDatabaseDef.DATABASE_NAME);
	}
	

	public AppManifest(Section section) {
		super(section);
	}
	
	@Override
	public String getLabel() {
		return "Sample User Registry";
	}
	
	@Override
	public String getDescription() {
		return "This is an Example Application for the UserResgistration Darwino-Plugin.";
	}
	
	@Override
	public String getMainPageUrl() {
		return "mobile/index.html";
	}
	
	@Override
	public String[] getDatabases() {
		return new String[] { 
			AppDatabaseDef.DATABASE_NAME,
		};
	}
	
	@Override
	public DatabaseFactory getDatabaseFactory() {
		return new AppDatabaseDef();
	}
	
	@Override
	public ExtensionRegistry getExtensionRegistry() {
		return new AppDBBusinessLogic();
	}
}
