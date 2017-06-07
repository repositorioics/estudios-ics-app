/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ni.org.ics.estudios.appmovil.bluetooth.activity;

import java.util.ArrayList;
import java.util.List;

import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.bluetooth.common.logger.Log;
import ni.org.ics.estudios.appmovil.cohortefamilia.activities.MenuCasaActivity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.CasaCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


/**
 * This fragment controls Bluetooth to communicate with other devices.
 */
public class BluetoothChatFragment extends Fragment {

    private static final String TAG = "BluetoothChatFragment";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    // Layout Views
    private ListView mConversationView;
    private Button mSendButton;

    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;

    /**
     * Array adapter for the conversation thread
     */
    private ArrayAdapter<String> mConversationArrayAdapter;

    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    /**
     * Member object for the chat services
     */
    private BluetoothChatService mChatService = null;
    
    private EstudiosAdapter estudiosAdapter;
    private CasaCohorteFamilia mCasa;
    private List<ParticipanteCohorteFamilia> mParticipantes = new ArrayList<ParticipanteCohorteFamilia>();
    private int contador = 0;
    private int totalPartEnviar = 0;
    private int totalPartEnviados = 0;
    private String casaSQL;
    private String codigoCHF;
    private String[] participantesSQL;
    private String[] participantesChfSQL;
    private String accion;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
		mCasa = (CasaCohorteFamilia) getActivity().getIntent().getExtras().getSerializable(Constants.CASA);
		accion = getActivity().getIntent().getStringExtra(Constants.ACCION);
		if(mCasa!=null){
			new GetDataCasaTask().execute();
		}
        // If the adapter is null, then Bluetooth is not supported
		participantesSQL = new String[30];
		participantesChfSQL = new String[30];
        if (mBluetoothAdapter == null) {
            FragmentActivity activity = getActivity();
            Toast.makeText(activity, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
            setupChat();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
            mBluetoothAdapter.disable();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bluetooth_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mConversationView = (ListView) view.findViewById(R.id.in);
        mSendButton = (Button) view.findViewById(R.id.button_send);
        if (accion.equals(Constants.SENDING)) {
        	mSendButton.setText(this.getString(R.string.send_house) + " " + mCasa.getCodigoCHF());
        	mSendButton.setEnabled(true);
        }
        else{
        	mSendButton.setText(this.getString(R.string.waiting_data));
        	mSendButton.setEnabled(false);
        }
    }

    /**
     * Set up the UI and background operations for chat.
     */
    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.message);

        mConversationView.setAdapter(mConversationArrayAdapter);


        // Initialize the send button with a listener that for click events
        mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                View view = getView();
                if (null != view) {
                	if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
                        Toast.makeText(getActivity(), R.string.not_connected, Toast.LENGTH_SHORT).show();
                        return;
                    }
        			enviarCasa();
                	
                }
            }
        });

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(getActivity(), mHandler);
    }

    
    /**
     * Makes this device discoverable for 300 seconds (5 minutes).
     */
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }
    

    /**
     * Updates the status on the action bar.
     *
     * @param resId a string resource ID
     */
    private void setStatus(int resId) {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }

    /**
     * Updates the status on the action bar.
     *
     * @param subTitle status
     */
    private void setStatus(CharSequence subTitle) {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
        }
    }

    /**
     * Establish connection with other device
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bluetooth_chat, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.secure_connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return true;
            }
            case R.id.discoverable: {
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
            }
        }
        return false;
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	try{
            FragmentActivity activity = getActivity();
            switch (msg.what) {
                case ConstantsBT.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            mConversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case ConstantsBT.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add(writeMessage);
                    break;
                case ConstantsBT.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    manageReceiveHandShake(readMessage);
                    mConversationArrayAdapter.add(mConnectedDeviceName + ": " +  readMessage);
                    break;
                case ConstantsBT.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(ConstantsBT.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Conectado a "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ConstantsBT.MESSAGE_TOAST:
                    if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(ConstantsBT.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    mConversationArrayAdapter.clear();
                    mSendButton.setText(R.string.send_request);
                    break;
            }
        	}
        	catch(Exception e){
        		
        	}
        }
    };
    
    
    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(getActivity(), R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);
        }
    }
    
    private void enviarCasa(){
    	String insertCasaSQL;
    	insertCasaSQL = "INSERT INTO chf_casas_cohorte_familia VALUES ('"+ 
    							mCasa.getCodigoCHF()+"',"+
    							mCasa.getCasa().getCodigo()+",'"+
    							mCasa.getNombre1JefeFamilia()+"','"+
    							mCasa.getNombre2JefeFamilia()+"','"+
    							mCasa.getApellido1JefeFamilia()+"','"+
    							mCasa.getApellido2JefeFamilia()+"',"+
    							mCasa.getLatitud()+","+
    							mCasa.getLongitud()+","+
    							mCasa.getRecordDate().getTime()+",'"+
    							mCasa.getRecordUser()+"','"+
    							mCasa.getPasive()+"','"+
    							mCasa.getDeviceid()+"','1')";
    	sendMessage(insertCasaSQL);
    }
    
    private void enviarParticipante(int cuenta){
    	String insertParticipanteSQL;
    	insertParticipanteSQL = "INSERT INTO participantes VALUES ("+ 
    							mParticipantes.get(cuenta).getParticipante().getCodigo()+",'"+
    							mParticipantes.get(cuenta).getParticipante().getNombre1()+"','"+
    							mParticipantes.get(cuenta).getParticipante().getNombre2()+"','"+
    							mParticipantes.get(cuenta).getParticipante().getApellido1()+"','"+
    							mParticipantes.get(cuenta).getParticipante().getApellido2()+"','"+
    							mParticipantes.get(cuenta).getParticipante().getSexo()+"',"+
    							mParticipantes.get(cuenta).getParticipante().getFechaNac().getTime()+",'"+
    							mParticipantes.get(cuenta).getParticipante().getNombre1Padre()+"','"+
    							mParticipantes.get(cuenta).getParticipante().getNombre2Padre()+"','"+
    							mParticipantes.get(cuenta).getParticipante().getApellido1Padre()+"','"+
    							mParticipantes.get(cuenta).getParticipante().getApellido2Padre()+"','"+
    							mParticipantes.get(cuenta).getParticipante().getNombre1Madre()+"','"+
    							mParticipantes.get(cuenta).getParticipante().getNombre2Madre()+"','"+
    							mParticipantes.get(cuenta).getParticipante().getApellido1Madre()+"','"+
    							mParticipantes.get(cuenta).getParticipante().getApellido2Madre()+"',"+
    							mParticipantes.get(cuenta).getParticipante().getCasa().getCodigo()+","+
    							mParticipantes.get(cuenta).getParticipante().getRecordDate().getTime()+",'"+
    							mParticipantes.get(cuenta).getRecordUser()+"','"+
    							mParticipantes.get(cuenta).getPasive()+"','"+
    							mParticipantes.get(cuenta).getDeviceid()+"','1')";
    	sendMessage(insertParticipanteSQL);
    }
    
    
    private void enviarParticipanteCHF(int cuenta){
    	String insertParticipanteChfSQL;
    	insertParticipanteChfSQL = "INSERT INTO chf_participantes VALUES ("+
    								mParticipantes.get(cuenta).getParticipante().getCodigo()+","+
    								mParticipantes.get(cuenta).getCasaCHF().getCodigoCHF()+","+
    								mParticipantes.get(cuenta).getRecordDate().getTime()+",'"+
    								mParticipantes.get(cuenta).getRecordUser()+"','"+
    								mParticipantes.get(cuenta).getPasive()+"','"+
    								mParticipantes.get(cuenta).getDeviceid()+"','1')";
    	sendMessage(insertParticipanteChfSQL);
    }
    
    
    private void manageReceiveHandShake(String mensaje){
    	if(mensaje.startsWith("INSERT INTO chf_casas_cohorte_familia")){
    		casaSQL = mensaje;
    		int codigoComienza = 47;
    		int codigoTermina = casaSQL.indexOf(",",0)-1;
    		codigoCHF = casaSQL.substring(codigoComienza, codigoTermina);
    		sendMessage(this.getString(R.string.finished_house));
    		try { Thread.sleep(200); }
    		catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
    	}
    	else if(mensaje.equals(this.getString(R.string.finished_house))){
    		if(totalPartEnviar>contador){
    			enviarParticipante(contador);
    			try { Thread.sleep(200); }
        		catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
    			enviarParticipanteCHF(contador);
    			contador++;
    		}
    		else{
    			sendMessage(this.getString(R.string.finished));
    			mSendButton.setText(this.getString(R.string.send_request));
    			mSendButton.setEnabled(true);
    		}
    		try { Thread.sleep(200); }
    		catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
    	}
    	else if(mensaje.startsWith("INSERT INTO participantes")){
    		participantesSQL[totalPartEnviados] = mensaje;
    		try { Thread.sleep(200); }
    		catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
    	}
    	else if(mensaje.startsWith("INSERT INTO chf_participantes")){
    		participantesChfSQL[totalPartEnviados] = mensaje;
    		totalPartEnviados++;
    		sendMessage(this.getString(R.string.finished_part));
    		try { Thread.sleep(200); }
    		catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
    	}
    	else if(mensaje.equals(this.getString(R.string.finished_part))){
    		if(totalPartEnviar>contador){
    			enviarParticipante(contador);
    			try { Thread.sleep(200); }
        		catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
    			enviarParticipanteCHF(contador);
    			contador++;
    		}
    		else{
    			sendMessage(this.getString(R.string.finished));
    			getActivity().finish();
    		}
    		try { Thread.sleep(200); }
    		catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
    	}
    	else if(mensaje.equals(this.getString(R.string.finished))){
    		new SaveDataCasaTask().execute();
    		mSendButton.setText(this.getString(R.string.send_request));
			mSendButton.setEnabled(true);
    	}
    }
    
    
	// ***************************************
	// Private classes
	// ***************************************
	private class GetDataCasaTask extends AsyncTask<String, Void, String> {
		
		private ProgressDialog nDialog;
		private String filtro = null;	
		@Override
		protected void onPreExecute() {
		    super.onPreExecute();
		    nDialog = new ProgressDialog(getActivity()); 
		    nDialog.setMessage("Abriendo base de datos");
		    nDialog.setTitle("Enviando..");
		    nDialog.setIndeterminate(false);
		    nDialog.setCancelable(true);
		    nDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
        	filtro = MainDBConstants.casaCHF + "="+ mCasa.getCodigoCHF();
			try {
				estudiosAdapter.open();
				mParticipantes = estudiosAdapter.getParticipanteCohorteFamilias(filtro, MainDBConstants.participante);
				estudiosAdapter.close();
				return "exito";
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
			}
		}

		protected void onPostExecute(String resultado) {
			// after the request completes, hide the progress indicator
			nDialog.dismiss();
			totalPartEnviar = mParticipantes.size();
		}
	}
	
	// ***************************************
		// Private classes
		// ***************************************
		private class SaveDataCasaTask extends AsyncTask<String, Void, String> {
			private ProgressDialog nDialog;
			@Override
			protected void onPreExecute() {
			    super.onPreExecute();
			    nDialog = new ProgressDialog(getActivity()); 
			    nDialog.setMessage("Abriendo base de datos");
			    nDialog.setTitle("Guardando..");
			    nDialog.setIndeterminate(false);
			    nDialog.setCancelable(true);
			    nDialog.show();
			}

			@Override
			protected String doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				String error="";
				try {
					estudiosAdapter.open();
				} catch (Exception e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
					error = error + e.getMessage();
				}
				try {
					estudiosAdapter.insertarCasaCohorteFamilia(casaSQL);
				} catch (Exception e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
					error = error + e.getMessage();
				}
				for(int i=0; i<totalPartEnviados;i++){
					try {
						estudiosAdapter.insertarParticipante(participantesSQL[i]);
					} catch (Exception e) {
						Log.e(TAG, e.getLocalizedMessage(), e);
						error = error + e.getMessage();
					}
				}
				for(int i=0; i<totalPartEnviados;i++){
					try {
						estudiosAdapter.insertarParticipanteCohorteFamilia(participantesChfSQL[i]);
					} catch (Exception e) {
						Log.e(TAG, e.getLocalizedMessage(), e);
						error = error + e.getMessage();
					}
				}
				String filtro = MainDBConstants.codigoCHF + "='"+ codigoCHF +"'";
				mCasa = estudiosAdapter.getCasaCohorteFamilia(filtro, null);
				estudiosAdapter.close();
				if (error.equals("")) {
					return getActivity().getString(R.string.success);
				}
				else {
					return error;
				}
			}

			protected void onPostExecute(String resultado) {
				// after the request completes, hide the progress indicator
				nDialog.dismiss();
				Toast.makeText(getActivity(), resultado, Toast.LENGTH_LONG).show();
				Bundle arguments = new Bundle();
		        if (mCasa!=null) arguments.putSerializable(Constants.CASA , mCasa);
		        Intent i = new Intent(getActivity(),
		        		MenuCasaActivity.class);
		        i.putExtras(arguments);
		        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(i);
	        	getActivity().finish();
			}
		}

}
