package ni.org.ics.estudios.appmovil.helpers;

import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import android.content.ContentValues;
import android.database.Cursor;

public class MessageResourceHelper {
	
	public static ContentValues crearMessageResourceValues(MessageResource barrio){
		ContentValues cv = new ContentValues();
		cv.put(CatalogosDBConstants.messageKey, barrio.getMessageKey());
		cv.put(CatalogosDBConstants.catRoot, barrio.getCatRoot());
		cv.put(CatalogosDBConstants.catKey, barrio.getCatKey());
		cv.put(MainDBConstants.pasive, String.valueOf(barrio.getPasive()));
		cv.put(CatalogosDBConstants.isCat, String.valueOf(barrio.getIsCat()));
		cv.put(CatalogosDBConstants.order, barrio.getOrder());
		cv.put(CatalogosDBConstants.spanish, barrio.getSpanish());
		cv.put(CatalogosDBConstants.english, barrio.getEnglish());
		return cv; 
	}	
	
	public static MessageResource crearMessageResource(Cursor cursorMessageResource){
		
		MessageResource mMessageResource = new MessageResource();
		mMessageResource.setMessageKey(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(CatalogosDBConstants.messageKey)));
		mMessageResource.setCatRoot(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(CatalogosDBConstants.catRoot)));
		mMessageResource.setCatKey(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(CatalogosDBConstants.catKey)));
		mMessageResource.setPasive(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mMessageResource.setPasive(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mMessageResource.setOrder(cursorMessageResource.getInt(cursorMessageResource.getColumnIndex(CatalogosDBConstants.order)));
		mMessageResource.setSpanish(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(CatalogosDBConstants.spanish)));
		mMessageResource.setEnglish(cursorMessageResource.getString(cursorMessageResource.getColumnIndex(CatalogosDBConstants.english)));
		return mMessageResource;
	}

	public static void fillMessageResourceStatement(SQLiteStatement stat, MessageResource messageResource) {
		stat.bindString(1, messageResource.getMessageKey());
		bindString(stat,2, messageResource.getCatRoot());
		bindString(stat,3, messageResource.getCatKey());
		bindString(stat,4, String.valueOf(messageResource.getIsCat()));
		stat.bindLong(5, messageResource.getOrder());
		bindString(stat,6, messageResource.getSpanish());
		bindString(stat,7, messageResource.getEnglish());
		bindString(stat,8, String.valueOf(messageResource.getPasive()));
	}

	public static void bindString(SQLiteStatement stat, int index, String value){
		if (value == null) {
			stat.bindNull(index);
		} else {
			stat.bindString(index, value);
		}
	}

}
