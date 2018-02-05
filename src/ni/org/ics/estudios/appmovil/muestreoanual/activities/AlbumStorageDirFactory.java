package ni.org.ics.estudios.appmovil.muestreoanual.activities;

import java.io.File;

abstract class AlbumStorageDirFactory {
	public abstract File getAlbumStorageDir(String albumName);
}
