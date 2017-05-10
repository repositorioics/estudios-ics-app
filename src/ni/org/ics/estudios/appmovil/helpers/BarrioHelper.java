package ni.org.ics.estudios.appmovil.helpers;

import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.utils.CatalogosDBConstants;
import android.content.ContentValues;
import android.database.Cursor;

public class BarrioHelper {
	
	public static ContentValues crearBarrioContentValues(Barrio barrio){
		ContentValues cv = new ContentValues();
		cv.put(CatalogosDBConstants.codigo, barrio.getCodigo());
		cv.put(CatalogosDBConstants.nombre, barrio.getNombre());
		return cv; 
	}	
	
	public static Barrio crearBarrio(Cursor cursorBarrio){
		
		Barrio mBarrio = new Barrio();
		mBarrio.setCodigo(cursorBarrio.getInt(cursorBarrio.getColumnIndex(CatalogosDBConstants.codigo)));
		mBarrio.setNombre(cursorBarrio.getString(cursorBarrio.getColumnIndex(CatalogosDBConstants.nombre)));
		return mBarrio;
	}
	
}
