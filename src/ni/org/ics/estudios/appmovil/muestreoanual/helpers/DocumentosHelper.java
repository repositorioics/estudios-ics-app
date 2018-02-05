package ni.org.ics.estudios.appmovil.muestreoanual.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.Documentos;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.DocumentosId;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.util.Date;

public class DocumentosHelper {
	
	public static ContentValues crearDocumentosContentValues(Documentos newDocumentos){
		ContentValues cv = new ContentValues();
		cv.put(ConstantsDB.CODIGO, newDocumentos.getDocsId().getCodigo());
		if (newDocumentos.getDocsId().getFechaDocumento() != null) cv.put(ConstantsDB.fechaDocumento, newDocumentos.getDocsId().getFechaDocumento().getTime());
		cv.put(ConstantsDB.tipoDoc, newDocumentos.getTipoDoc());
		cv.put(ConstantsDB.documento, newDocumentos.getDocumento());
		cv.put(ConstantsDB.STATUS, newDocumentos.getEstado());
		cv.put(ConstantsDB.fechaRecepcion, newDocumentos.getFechaRecepcion().getTime());
		cv.put(ConstantsDB.USUARIO, newDocumentos.getUsuario());
		cv.put(ConstantsDB.TODAY, newDocumentos.getToday().getTime());
		return cv; 
	}	
	
	
	public static Documentos crearDocumentos(Cursor cursorDocumentos){
		
		Documentos mDocumento = new Documentos();
		DocumentosId mDocumentoId = new DocumentosId();
		mDocumentoId.setCodigo(cursorDocumentos.getInt(cursorDocumentos.getColumnIndex(ConstantsDB.CODIGO)));
		mDocumentoId.setFechaDocumento(new Date(cursorDocumentos.getLong(cursorDocumentos.getColumnIndex(ConstantsDB.fechaDocumento))));
		mDocumento.setDocsId(mDocumentoId);
		mDocumento.setTipoDoc(cursorDocumentos.getString(cursorDocumentos.getColumnIndex(ConstantsDB.tipoDoc)));
		mDocumento.setDocumento(cursorDocumentos.getBlob(cursorDocumentos.getColumnIndex(ConstantsDB.documento)));
		mDocumento.setUsuario(cursorDocumentos.getString(cursorDocumentos.getColumnIndex(ConstantsDB.USUARIO)));
		mDocumento.setEstado(cursorDocumentos.getString(cursorDocumentos.getColumnIndex(ConstantsDB.STATUS)));
		Date fecha = new Date(cursorDocumentos.getLong(cursorDocumentos.getColumnIndex(ConstantsDB.fechaRecepcion)));
		Date fechaReg = new Date(cursorDocumentos.getLong(cursorDocumentos.getColumnIndex(ConstantsDB.TODAY)));
		mDocumento.setFechaRecepcion(fecha);
		mDocumento.setToday(fechaReg);
		return mDocumento;
	}
	
	
public static Documentos crearDocumentos2(Cursor cursorDocumentos){
		
		Documentos mDocumento = new Documentos();
		DocumentosId mDocumentoId = new DocumentosId();
		mDocumentoId.setCodigo(cursorDocumentos.getInt(cursorDocumentos.getColumnIndex(ConstantsDB.CODIGO)));
		mDocumentoId.setFechaDocumento(new Date(cursorDocumentos.getLong(cursorDocumentos.getColumnIndex(ConstantsDB.fechaDocumento))));
		mDocumento.setDocsId(mDocumentoId);
		mDocumento.setTipoDoc(cursorDocumentos.getString(cursorDocumentos.getColumnIndex(ConstantsDB.tipoDoc)));
		//mDocumento.setDocumento(cursorDocumentos.getBlob(cursorDocumentos.getColumnIndex(ConstantsDB.documento)));
		mDocumento.setUsuario(cursorDocumentos.getString(cursorDocumentos.getColumnIndex(ConstantsDB.USUARIO)));
		mDocumento.setEstado(cursorDocumentos.getString(cursorDocumentos.getColumnIndex(ConstantsDB.STATUS)));
		Date fecha = new Date(cursorDocumentos.getLong(cursorDocumentos.getColumnIndex(ConstantsDB.fechaRecepcion)));
		Date fechaReg = new Date(cursorDocumentos.getLong(cursorDocumentos.getColumnIndex(ConstantsDB.TODAY)));
		mDocumento.setFechaRecepcion(fecha);
		mDocumento.setToday(fechaReg);
		return mDocumento;
	}
	
	
}
