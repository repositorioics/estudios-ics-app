package ni.org.ics.estudios.appmovil.muestreoanual.tasks;

import android.content.Context;
import android.util.Log;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.users.Authority;
import ni.org.ics.estudios.appmovil.domain.users.UserPermissions;
import ni.org.ics.estudios.appmovil.domain.users.UserSistema;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class DownloadUsersRolesTask extends DownloadTask {

	private Boolean permiso;
	private String error = null;
	private String url = null;
	private String username = null;
	private String password = null;
	private List<UserSistema> usuarios = null;
	private List<Authority> roles = null;
    private List<UserPermissions> permisos = null;

    private EstudiosAdapter ca = null;

    private final Context mContext;

    public DownloadUsersRolesTask(Context context) {
        mContext = context;
    }

	@Override
	protected String doInBackground(String... values) {

		url = values[0];
		username = values[1];
		password = values[2];

        ca = new EstudiosAdapter(mContext, password, false, false);
        ca.open();
		try {
			permiso = checkRole();
			if(permiso){
				try {
					error = descargarUsuarios();
				} catch (Exception e) {
					// Regresa error al descargar
					e.printStackTrace();
					return e.getLocalizedMessage();
				}

				if (usuarios != null){
					ca.borrarTodosUsuarios();
					try {
						addUsuarios(usuarios);
					} catch (Exception e) {
						// Regresa error al insertar
						e.printStackTrace();
						return e.getLocalizedMessage();
					}
				}

				try {
					error = descargarRoles();
				} catch (Exception e) {
					// Regresa error al descargar
					e.printStackTrace();
					return e.getLocalizedMessage();
				}

				if (roles != null){
					ca.borrarTodosRoles();
					try {
						addRoles(roles);
					} catch (Exception e) {
						// Regresa error al insertar
						e.printStackTrace();
						return e.getLocalizedMessage();
					}
				}

                try {
                    error = descargarPermisosUsuarios();
                } catch (Exception e) {
                    // Regresa error al descargar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }

                if (permisos != null){
                    // open db and clean entries
                    //ca.open();
                    ca.borrarTodosPermisos();
                    // download and insert barrios
                    try {
                        addPermisos(permisos);
                    } catch (Exception e) {
                        // Regresa error al insertar
                        e.printStackTrace();
                        return e.getLocalizedMessage();
                    }
                    // close db and stream
                    //ca.close();
                }
			}
			else{
				return "No tiene permiso";
			}
		} catch (Exception e) {
			// Regresa error al descargar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
        finally {
            ca.close();
        }
        return error;
	}

	// url, username, password
	protected Boolean checkRole() throws Exception {
		try {
			// The URL for making the GET request
			final String urlRequest = url + "/movil/role/{username}";

			// Set the Accept header for "application/json"
			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
			HttpHeaders requestHeaders = new HttpHeaders();
			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
			requestHeaders.setAccept(acceptableMediaTypes);
			requestHeaders.setAuthorization(authHeader);

			// Populate the headers in an HttpEntity object to use for the request
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

			// Perform the HTTP GET request
			ResponseEntity<Boolean> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
					Boolean.class, username);

			return responseEntity.getBody();

		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			return false;	
		}
	}

	private void addUsuarios(List<UserSistema> usuarios) throws Exception {


		int v = usuarios.size();

		ListIterator<UserSistema> iter = usuarios.listIterator();

		while (iter.hasNext()){
            UserSistema usuario = iter.next();
			if (!ca.existeUsuario(usuario.getUsername())){
				ca.crearUsuario(usuario);
			}
			else{
				ca.actualizarUsuario(usuario);
			}
			publishProgress("Usuarios", Integer.valueOf(iter.nextIndex()).toString(), Integer
					.valueOf(v).toString());
		}

	}

	// url, username, password
	protected String descargarUsuarios() throws Exception {
		try {
			// The URL for making the GET request
			final String urlRequest = url + "/movil/usuarios";

			// Set the Accept header for "application/json"
			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
			HttpHeaders requestHeaders = new HttpHeaders();
			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
			requestHeaders.setAccept(acceptableMediaTypes);
			requestHeaders.setAuthorization(authHeader);

			// Populate the headers in an HttpEntity object to use for the request
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

			// Perform the HTTP GET request
			ResponseEntity<UserSistema[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    UserSistema[].class);

			// convert the array to a list and return it
			usuarios = Arrays.asList(responseEntity.getBody());
			return null;

		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			return e.getLocalizedMessage();	
		}
	}

	private void addRoles(List<Authority> roles) throws Exception {

		int v = roles.size();

		ListIterator<Authority> iter = roles.listIterator();

		while (iter.hasNext()){
			ca.crearRol(iter.next());
			publishProgress("Roles", Integer.valueOf(iter.nextIndex()).toString(), Integer
					.valueOf(v).toString());
		}

	}

	// url, username, password
	protected String descargarRoles() throws Exception {
		try {
			// The URL for making the GET request
			final String urlRequest = url + "/movil/roles";

			// Set the Accept header for "application/json"
			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
			HttpHeaders requestHeaders = new HttpHeaders();
			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
			requestHeaders.setAccept(acceptableMediaTypes);
			requestHeaders.setAuthorization(authHeader);

			// Populate the headers in an HttpEntity object to use for the request
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

			// Perform the HTTP GET request
			ResponseEntity<Authority[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
					Authority[].class);

			// convert the array to a list and return it
			roles = Arrays.asList(responseEntity.getBody());
			return null;

		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			return e.getLocalizedMessage();	
		}
	}

    private void addPermisos(List<UserPermissions> permisos) throws Exception {

        int v = permisos.size();

        ListIterator<UserPermissions> iter = permisos.listIterator();

        while (iter.hasNext()){
            ca.crearPermisosUsuario(iter.next());
            publishProgress("Permisos", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    // url, username, password
    protected String descargarPermisosUsuarios() throws Exception {
        try {
            // The URL for making the GET request
            final String urlRequest = url + "/movil/permisos";

            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);

            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

            // Perform the HTTP GET request
            ResponseEntity<UserPermissions[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    UserPermissions[].class);

            // convert the array to a list and return it
            permisos = Arrays.asList(responseEntity.getBody());
            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
}
