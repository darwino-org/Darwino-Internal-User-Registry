/*!COPYRIGHT HEADER! 
 *
 */

package biz.webgate.darwino.samples.app;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.util.Lic;
import com.darwino.commons.util.StringUtil;
import com.darwino.jsonstore.impl.DatabaseFactoryImpl;
import com.darwino.jsonstore.meta._Database;

import biz.webgate.darwino.userregistry.setup.DbSetup;

/**
 * Database Definition.
 * 
 * @author Philippe Riand
 */
public class AppDatabaseDef extends DatabaseFactoryImpl {

	public static final int DATABASE_VERSION	= 3;
	public static final String DATABASE_NAME	= "sampleUserRegistry";
	
	@Override
	public int getDatabaseVersion(String databaseName) throws JsonException {
		if(!StringUtil.equalsIgnoreCase(databaseName, DATABASE_NAME)) {
			return -1;
		}
		return DATABASE_VERSION;
	}
	
	@Override
	public _Database loadDatabase(String databaseName) throws JsonException {
		if(!StringUtil.equalsIgnoreCase(databaseName, DATABASE_NAME)) {
			return null;
		}
		_Database db = new _Database(DATABASE_NAME, "Sample User Registry", DATABASE_VERSION);

		db.setReplicationEnabled(true);
		
		// Instance are only available with the enterprise edition
		db.setInstanceEnabled(Lic.isEnterpriseEdition());
		
		// Customize the default stores, if desired...
//		{
//			_Store _def = db.getStore(Database.STORE_DEFAULT);
//			_def.setFtSearchEnabled(true);
//			_FtSearch ft = (_FtSearch) _def.setFTSearch(new _FtSearch());
//			ft.setFields("$");
//		}

		// Store...
//		{
//			_Store store = db.addStore("MyStore");
//			store.setLabel("My Store");
//			store.setFtSearchEnabled(true);
//			
//			// Search the whole document (all fields)
//			_FtSearch ft = (_FtSearch) store.setFTSearch(new _FtSearch());
//			ft.setFields("$");
//		}
		DbSetup.INSTANCE.setupUserStore(db);

		return db;
	}
}
