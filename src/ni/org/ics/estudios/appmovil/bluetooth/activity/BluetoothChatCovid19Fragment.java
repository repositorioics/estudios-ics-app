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
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import ni.org.ics.estudios.appmovil.MyIcsApplication;
import ni.org.ics.estudios.appmovil.R;
import ni.org.ics.estudios.appmovil.bluetooth.common.logger.Log;
import ni.org.ics.estudios.appmovil.covid19.activities.list.ListaParticipantesCasoCovid19Activity;
import ni.org.ics.estudios.appmovil.database.EstudiosAdapter;
import ni.org.ics.estudios.appmovil.domain.Participante;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.ParticipanteCohorteFamilia;
import ni.org.ics.estudios.appmovil.domain.covid19.CandidatoTransmisionCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.CasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCasoCovid19;
import ni.org.ics.estudios.appmovil.domain.covid19.ParticipanteCovid19;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.appmovil.utils.CasosDBConstants;
import ni.org.ics.estudios.appmovil.utils.Constants;
import ni.org.ics.estudios.appmovil.utils.Covid19DBConstants;
import ni.org.ics.estudios.appmovil.utils.MainDBConstants;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

import java.util.ArrayList;
import java.util.List;


/**
 * This fragment controls Bluetooth to communicate with other devices.
 */
public class BluetoothChatCovid19Fragment extends Fragment {

    private static final String TAG = "BluetoothChatCovid19Fragment";

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
    private CasoCovid19 mCaso;
    private List<Participante> mParticipantes = new ArrayList<Participante>();
    private List<ParticipanteCohorteFamilia> mParticipantesChf = new ArrayList<ParticipanteCohorteFamilia>();
    private List<ParticipanteCovid19> mParticipantesCovid = new ArrayList<ParticipanteCovid19>();
    private List<ParticipanteCasoCovid19> mParticipantesCasos = new ArrayList<ParticipanteCasoCovid19>();
    private int contador = 0;
    private int totalPartEnviar = 0;
    private int totalPartCovEnviar = 0;
    private int totalPartEnviados = 0;
    private int totalProcEnviados = 0;
    private int totalPartCovEnviados = 0;
    private int totalPartCasosEnviar = 0;
    private int totalCasosEnviados = 0;
    private String casoSQL;
    private String codigoCaso;
    private String[] participantesSQL;
    private String[] participantesCovidSQL;
    private String[] participantesChfSQL;
    //private String[] participantesSeroSQL;
    private String[] participantesProcSQL;//procesos
    private String[] participantesCasosSQL;//casos seguimiento
    private String accion;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	String mPass = ((MyIcsApplication) this.getActivity().getApplication()).getPassApp();
		estudiosAdapter = new EstudiosAdapter(this.getActivity(),mPass,false,false);
		mCaso = (CasoCovid19) getActivity().getIntent().getExtras().getSerializable(Constants.CASA);
		accion = getActivity().getIntent().getStringExtra(Constants.ACCION);
		if(mCaso !=null){
			new GetDataCasaTask().execute();
		}
        // If the adapter is null, then Bluetooth is not supported
		participantesSQL = new String[30];
        participantesCovidSQL = new String[30];
		participantesChfSQL = new String[30];
		//participantesSeroSQL = new String[30];
        participantesProcSQL = new String[30];
        participantesCasosSQL = new String[30];
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
            mChatService = null;
        }
        mBluetoothAdapter.disable();
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
        	mSendButton.setText(this.getString(R.string.send_house) + " " + mCaso.getCasa().getCodigoCHF());
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
        			enviarCaso();
                	
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
    
    private void enviarCaso(){
    	String insertCasoSQL;
    	
    	String codigoCaso = "'"+ mCaso.getCodigoCaso() +"'";
    	String casa = "null";
    	if (mCaso.getCasa()!=null) casa = "'"+ mCaso.getCasa().getCodigoCHF()+"'";
    	String inactivo = "'"+ mCaso.getInactivo()+"'";
    	Long fechaIngreso = mCaso.getFechaIngreso().getTime();
    	Long fr = mCaso.getRecordDate().getTime();

    	String userName = null;
    	if(mCaso.getRecordUser()!=null) userName  = "'"+ mCaso.getRecordUser()+"'";
    	String pasivo = null;
    	if(String.valueOf(mCaso.getPasive())!=null) pasivo  = "'"+ mCaso.getPasive()+"'";
    	String deviceId = null;
    	if(mCaso.getDeviceid()!=null) deviceId  = "'"+ mCaso.getDeviceid()+"'";
    	String enviado = null;
    	if(String.valueOf(mCaso.getEstado())!=null) enviado  = "'"+ mCaso.getEstado()+"'";
    	
    	insertCasoSQL = "INSERT INTO covid_casos VALUES ("+
                codigoCaso +","+
                casa +","+
                inactivo +","+
                fechaIngreso +","+
                (mCaso.getFechaInactivo()!=null?mCaso.getFechaInactivo().getTime():"null")+","+
                fr +","+
				userName +","+
				pasivo +","+
				deviceId +","+
				enviado +")";
    	//EMISOR----> Envía Casa
    	sendMessage(insertCasoSQL);
    }
    
    private void enviarParticipante(int cuenta){
    	String insertParticipanteSQL;

        Integer codigo = mParticipantes.get(cuenta).getCodigo();
        String nom1Part = "'"+ mParticipantes.get(cuenta).getNombre1()+"'";
        String nom2Part = null;
        if(mParticipantes.get(cuenta).getNombre2()!=null) nom2Part  = "'"+ mParticipantes.get(cuenta).getNombre2()+"'";
        String ape1Part = "'"+ mParticipantes.get(cuenta).getApellido1() +"'";
        String ape2Part = null;
        if(mParticipantes.get(cuenta).getApellido2()!=null) ape2Part  = "'"+ mParticipantes.get(cuenta).getApellido2() +"'";
        String sexo = "'"+ mParticipantes.get(cuenta).getSexo() +"'";
        Long fn = mParticipantes.get(cuenta).getFechaNac().getTime();
        String nom1Padre = null;
        if(mParticipantes.get(cuenta).getNombre1Padre()!=null) nom1Padre  = "'"+ mParticipantes.get(cuenta).getNombre1Padre()+"'";
        String nom2Padre = null;
        if(mParticipantes.get(cuenta).getNombre2Padre()!=null) nom2Padre  = "'"+ mParticipantes.get(cuenta).getNombre2Padre()+"'";
        String ape1Padre = null;
        if(mParticipantes.get(cuenta).getApellido1Padre()!=null) ape1Padre  = "'"+ mParticipantes.get(cuenta).getApellido1Padre()+"'";
        String ape2Padre = null;
        if(mParticipantes.get(cuenta).getApellido2Padre()!=null) ape2Padre  = "'"+ mParticipantes.get(cuenta).getApellido2Padre()+"'";
        String nom1Madre = null;
        if(mParticipantes.get(cuenta).getNombre1Madre()!=null) nom1Madre  = "'"+ mParticipantes.get(cuenta).getNombre1Madre()+"'";
        String nom2Madre = null;
        if(mParticipantes.get(cuenta).getNombre2Madre()!=null) nom2Madre  = "'"+ mParticipantes.get(cuenta).getNombre2Madre()+"'";
        String ape1Madre = null;
        if(mParticipantes.get(cuenta).getApellido1Madre()!=null) ape1Madre  = "'"+ mParticipantes.get(cuenta).getApellido1Madre()+"'";
        String ape2Madre = null;
        if(mParticipantes.get(cuenta).getApellido2Madre()!=null) ape2Madre  = "'"+ mParticipantes.get(cuenta).getApellido2Madre()+"'";
        Integer codCasa = mParticipantes.get(cuenta).getCasa().getCodigo();
        Long fr = mParticipantes.get(cuenta).getRecordDate().getTime();
        String userName = null;
        if(mParticipantes.get(cuenta).getRecordUser()!=null) userName  = "'"+ mParticipantes.get(cuenta).getRecordUser()+"'";
        String pasivo = null;
        if(String.valueOf(mParticipantes.get(cuenta).getPasive())!=null) pasivo  = "'"+ mParticipantes.get(cuenta).getPasive()+"'";
        String deviceId = null;
        if(mParticipantes.get(cuenta).getDeviceid()!=null) deviceId  = "'"+ mParticipantes.get(cuenta).getDeviceid()+"'";
        String enviado = null;
        if(String.valueOf(mParticipantes.get(cuenta).getEstado())!=null) enviado  = "'"+ mParticipantes.get(cuenta).getEstado()+"'";

        insertParticipanteSQL = "INSERT INTO participantes VALUES ("+
				codigo +","+
				nom1Part +","+
				nom2Part +","+
				ape1Part +","+
				ape2Part +","+
				sexo +","+
				fn +","+
				nom1Padre +","+
				nom2Padre +","+
				ape1Padre +","+
				ape2Padre +","+
				nom1Madre +","+
				nom2Madre +","+
				ape1Madre +","+
				ape2Madre +","+
				codCasa +","+
				fr +","+
				userName +","+
				pasivo +","+
				deviceId +","+
				enviado +")";
    	sendMessage(insertParticipanteSQL);
    }

    private void enviarParticipanteCHF(int cuenta){
        String insertParticipanteChfSQL;

        Integer codigo = mParticipantesChf.get(cuenta).getParticipante().getCodigo();
        String codCasa = mParticipantesChf.get(cuenta).getCasaCHF().getCodigoCHF();
        Long fr = mParticipantesChf.get(cuenta).getRecordDate().getTime();
        String userName = null;
        if(mParticipantesChf.get(cuenta).getRecordUser()!=null) userName  = "'"+ mParticipantesChf.get(cuenta).getRecordUser()+"'";
        String pasivo = null;
        if(String.valueOf(mParticipantesChf.get(cuenta).getPasive())!=null) pasivo  = "'"+ mParticipantesChf.get(cuenta).getPasive()+"'";
        String deviceId = null;
        if(mParticipantes.get(cuenta).getDeviceid()!=null) deviceId  = "'"+ mParticipantesChf.get(cuenta).getDeviceid()+"'";
        String enviado = null;
        if(String.valueOf(mParticipantesChf.get(cuenta).getEstado())!=null) enviado  = "'"+ mParticipantesChf.get(cuenta).getEstado()+"'";

        insertParticipanteChfSQL = "INSERT INTO chf_participantes VALUES ("+
                codigo +","+
                codCasa +","+
                fr +","+
                userName +","+
                pasivo +","+
                deviceId +","+
                enviado +")";
        sendMessage(insertParticipanteChfSQL);
    }

    private void enviarParticipanteCovid(int cuenta){
        //solo uno
        String insertParticipanteChfSQL;
    	
    	Integer codigo = mParticipantesCovid.get(cuenta).getParticipante().getCodigo();
    	Long fr = mParticipantesCovid.get(cuenta).getRecordDate().getTime();
    	String userName = null;
    	if(mParticipantesCovid.get(cuenta).getRecordUser()!=null) userName  = "'"+ mParticipantesCovid.get(cuenta).getRecordUser()+"'";
    	String pasivo = null;
    	if(String.valueOf(mParticipantesCovid.get(cuenta).getPasive())!=null) pasivo  = "'"+ mParticipantesCovid.get(cuenta).getPasive()+"'";
    	String deviceId = null;
    	if(mParticipantes.get(cuenta).getDeviceid()!=null) deviceId  = "'"+ mParticipantesCovid.get(cuenta).getDeviceid()+"'";
    	String enviado = null;
    	if(String.valueOf(mParticipantesCovid.get(cuenta).getEstado())!=null) enviado  = "'"+ mParticipantesCovid.get(cuenta).getEstado()+"'";
    	
    	insertParticipanteChfSQL = "INSERT INTO covid_participantes VALUES ("+
    									codigo +","+
    									fr +","+
    									userName +","+
    									pasivo +","+
    									deviceId +","+
    									enviado +")";
    	sendMessage(insertParticipanteChfSQL);
    }
    
    private void enviarParticipanteProc(int cuenta){
        String insertParticipanteProcSQL;
        ParticipanteProcesos mParticipantesProc = mParticipantes.get(cuenta).getProcesos();
        insertParticipanteProcSQL = "INSERT INTO participantes_procesos VALUES ("+
                mParticipantesProc.getCodigo()+","+
                mParticipantesProc.getEstPart()+",'"+
                mParticipantesProc.getReConsDeng()+"','"+
                mParticipantesProc.getConPto()+"','"+
                mParticipantesProc.getEstudio()+"','"+
                mParticipantesProc.getPbmc()+"','"+
                mParticipantesProc.getConsDeng()+"','"+
                mParticipantesProc.getConsFlu()+"','"+
                mParticipantesProc.getConsChik()+"','"+
                mParticipantesProc.getConmx()+"','"+
                mParticipantesProc.getConmxbhc()+"','"+
                mParticipantesProc.getEncLacMat()+"','"+
                mParticipantesProc.getPesoTalla()+"','"+
                mParticipantesProc.getEncPart()+"','"+
                mParticipantesProc.getEnCasa()+"','"+
                mParticipantesProc.getObsequio()+"','"+
                mParticipantesProc.getConvalesciente()+"','"+
                mParticipantesProc.getInfoVacuna()+"','"+
                mParticipantesProc.getPaxgene()+"','"+
                mParticipantesProc.getRetoma()+"',"+
                mParticipantesProc.getVolRetoma()+","+
                mParticipantesProc.getVolRetomaPbmc()+",null,'"+
                mParticipantesProc.getZika()+"','"+
                mParticipantesProc.getAdn()+"',"+
                mParticipantesProc.getRelacionFam()+","+
                mParticipantesProc.getCuantasPers()+",'"+
                mParticipantesProc.getDatosParto()+"','"+
                mParticipantesProc.getPosZika()+"','"+
                mParticipantesProc.getDatosVisita()+"','"+
                mParticipantesProc.getMi()+"','"+
                mParticipantesProc.getCasaCHF()+"','"+
                mParticipantesProc.getEnCasaChf()+"','"+
                mParticipantesProc.getEnCasaSa()+"','"+
                mParticipantesProc.getEncPartSa()+"','"+
                mParticipantesProc.getTutor()+"','"+
                mParticipantesProc.getCoordenadas()+"',"+
                (mParticipantesProc.getConsSa()!=null?"'"+mParticipantesProc.getConsSa()+"'":"null")+",'"+
                mParticipantesProc.getObsequioChf()+"','"+
                mParticipantesProc.getcDatosParto()+"','"+
                mParticipantesProc.getReConsChf18()+"',"+
                (mParticipantesProc.getPosDengue()!=null?"'"+mParticipantesProc.getPosDengue()+"'":"null")+"," +
                (mParticipantesProc.getMxSuperficie()!=null?"'"+mParticipantesProc.getMxSuperficie()+"'":"null")+","+ //MA2020
                (mParticipantesProc.getMostrarAlfabeto()!=null?"'"+mParticipantesProc.getMostrarAlfabeto()+"'":"null")+"," +
                (mParticipantesProc.getMostrarMadreAlfabeta()!=null?"'"+mParticipantesProc.getMostrarMadreAlfabeta()+"'":"null")+"," +
                (mParticipantesProc.getMostrarPadreAlfabeto()!=null?"'"+mParticipantesProc.getMostrarPadreAlfabeto()+"'":"null")+"," +
                (mParticipantesProc.getMostrarNumParto()!=null?"'"+mParticipantesProc.getMostrarNumParto()+"'":"null")+"," +
                (mParticipantesProc.getAntecedenteTutorCP()!=null?"'"+mParticipantesProc.getAntecedenteTutorCP()+"'":"null")+"," +
                (mParticipantesProc.getConsCovid19()!=null?"'"+mParticipantesProc.getConsCovid19()+"'":"null")+"," +
                (mParticipantesProc.getSubEstudios()!=null?"'"+mParticipantesProc.getSubEstudios()+"'":"null")+"," +
                (mParticipantesProc.getConsChf()!=null?"'"+mParticipantesProc.getConsChf()+"'":"null")+"," +
                (mParticipantesProc.getCuestCovid()!=null?"'"+mParticipantesProc.getCuestCovid()+"'":"null")+"," +
                (mParticipantesProc.getMuestraCovid()!=null?"'"+mParticipantesProc.getMuestraCovid()+"'":"null")+"," +
                (mParticipantesProc.getPosCovid()!=null?"'"+mParticipantesProc.getPosCovid()+"'":"null")+"," +
                "0,null,'"+
                mParticipantesProc.getMovilInfo().getEstado()+"',null,null,null,'"+
                mParticipantesProc.getMovilInfo().getDeviceid()+"',null,null,"+
                mParticipantesProc.getMovilInfo().getToday().getTime()+",'"+
                mParticipantesProc.getMovilInfo().getUsername()+"',0,0,0)";
        sendMessage(insertParticipanteProcSQL);
    }

    private void enviarParticipanteCaso(int cuenta){
        String insertParticipanteCasoSQL;
        insertParticipanteCasoSQL = "INSERT INTO covid_participantes_casos VALUES ('"+
                mParticipantesCasos.get(cuenta).getCodigoCasoParticipante()+"','"+
                mParticipantesCasos.get(cuenta).getCodigoCaso().getCodigoCaso()+"',"+
                mParticipantesCasos.get(cuenta).getParticipante().getCodigo()+",'"+
                mParticipantesCasos.get(cuenta).getEnfermo()+"',"+
                (mParticipantesCasos.get(cuenta).getPositivoPor()!=null?"'"+mParticipantesCasos.get(cuenta).getPositivoPor()+"'":"null")+","+
                (mParticipantesCasos.get(cuenta).getFif()!=null?mParticipantesCasos.get(cuenta).getFif().getTime():"null")+","+
                (mParticipantesCasos.get(cuenta).getFis()!=null?mParticipantesCasos.get(cuenta).getFis().getTime():"null")+",'"+
                mParticipantesCasos.get(cuenta).getConsentimiento()+"',"+
                mParticipantesCasos.get(cuenta).getRecordDate().getTime()+",'"+
                mParticipantesCasos.get(cuenta).getRecordUser()+"','"+
                mParticipantesCasos.get(cuenta).getPasive()+"','"+
                mParticipantesCasos.get(cuenta).getDeviceid()+"','0')";
        sendMessage(insertParticipanteCasoSQL);
    }
    
    private void manageReceiveHandShake(String mensaje){
    	//RECEPTOR---->Recibe Casa
    	if(mensaje.startsWith("INSERT INTO covid_casos")){
    		casoSQL = mensaje;
    		int codigoComienza = 33;
    		int codigoTermina = casoSQL.indexOf(",",0)-1;
    		codigoCaso = casoSQL.substring(codigoComienza, codigoTermina);
    		try { Thread.sleep(500); }
    		catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
    		//RECEPTOR---->Manda mensaje fin de casa y comienza participante
    		sendMessage(this.getString(R.string.start_part));
    	}
    	//EMISOR---->Recibe mensaje de iniciar participante
    	else if(mensaje.equals(this.getString(R.string.start_part))){
        	//EMISOR---->Consulta si hay participantes por enviar
    		if(totalPartEnviar>contador){
    			try { Thread.sleep(500); }
        		catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
    			//EMISOR---->Envía participante
    			enviarParticipante(contador);
                //Pausa para no pegar los dos mensajes
    			try { Thread.sleep(500); }
        		catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
    			//EMISOR---->Envía participante CHF
    			enviarParticipanteCHF(contador);
    			//Aumenta el contador
    			contador++;
    		}
    		//Si no hay participantes inicia procesos
    		else{
    			//EMISOR---->Envia mensaje que terminaron participantes y reinicia contador
    			try { Thread.sleep(500); }
        		catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
    			contador = 0;
    			sendMessage(this.getString(R.string.finished_part));
    		}
    	}
    	//RECEPTOR---->Recibe participante
    	else if(mensaje.startsWith("INSERT INTO participantes ")){
                participantesSQL[totalPartEnviados] = mensaje;
    	}
        //RECEPTOR---->Recibe participante CHF
    	else if(mensaje.startsWith("INSERT INTO chf_participantes ")){
    		participantesChfSQL[totalPartEnviados] = mensaje;
    		totalPartEnviados++;
    		//Pausa
    		try { Thread.sleep(500); }
    		catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
    		////RECEPTOR---->Manda mensaje de enviar otro participante
    		sendMessage(this.getString(R.string.start_part));
    	}
    	/*ACA VAMOS */
        //EMISOR---->Recibe mensaje de iniciar procesos participante
        else if(mensaje.equals(this.getString(R.string.start_partcovid))){
            //EMISOR---->Consulta si hay participantes por enviar
            if(totalPartCovEnviar>contador){
                try { Thread.sleep(500); }
                catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
                //EMISOR---->Envía participante covid
                enviarParticipanteCovid(contador);
                //Aumenta el contador
                contador++;
            }
            //Si no hay procesos participantes covid inicia procesos
            else{
                //EMISOR---->Envia mensaje que terminaron participantes y reinicia contador
                try { Thread.sleep(500); }
                catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
                contador = 0;
                sendMessage(this.getString(R.string.finished_partcovid));
            }
        }//RECEPTOR---->Recibe participante covid
        else if(mensaje.startsWith("INSERT INTO covid_participantes ")){
            participantesCovidSQL[totalPartCovEnviados] = mensaje;
            totalPartCovEnviados++;
            ////RECEPTOR---->Manda mensaje de enviar otro participante proceso
            sendMessage(this.getString(R.string.start_partcovid));
        }
        //RECEPTOR---->Recibe mensaje de que ya no hay participantes
        else if(mensaje.equals(this.getString(R.string.finished_partcovid))){
            //RECEPTOR---->Envia mensaje que inicien participantes procesos
            try { Thread.sleep(500); }
            catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
            sendMessage(this.getString(R.string.start_partproc));
        }
        /*TERMINAMOS*/
        //EMISOR---->Recibe mensaje de iniciar procesos participante
        else if(mensaje.equals(this.getString(R.string.start_partproc))){
            //EMISOR---->Consulta si hay participantes por enviar
            if(totalPartEnviar>contador){
                try { Thread.sleep(500); }
                catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
                //EMISOR---->Envía participante procesos
                enviarParticipanteProc(contador);
                //Aumenta el contador
                contador++;
            }
            //Si no hay procesos participantes inicia seroprevalencia
            else{
                //EMISOR---->Envia mensaje que terminaron participantes y reinicia contador
                try { Thread.sleep(500); }
                catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
                contador = 0;
                sendMessage(this.getString(R.string.finished_partproc));
            }
        }
        //RECEPTOR---->Recibe participante
        else if(mensaje.startsWith("INSERT INTO participantes_procesos")){
            participantesProcSQL[totalProcEnviados] = mensaje;
            totalProcEnviados++;
            ////RECEPTOR---->Manda mensaje de enviar otro participante proceso
            sendMessage(this.getString(R.string.start_partproc));
        }
    	//RECEPTOR---->Recibe mensaje de que ya no hay participantes
    	else if(mensaje.equals(this.getString(R.string.finished_part))){
    		//RECEPTOR---->Envia mensaje que inicien participantes procesos
			try { Thread.sleep(500); }
    		catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
			sendMessage(this.getString(R.string.start_partcovid));
    	}
        //RECEPTOR---->Recibe mensaje de que ya no hay participantes
        else if(mensaje.equals(this.getString(R.string.finished_partproc))){
            //RECEPTOR---->Envia mensaje que inicien participantes de seroprevalencia
            try { Thread.sleep(500); }
            catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
            sendMessage(this.getString(R.string.start_partcasos));
        }
        //EMISOR---->Recibe mensaje de iniciar procesos participante
        else if(mensaje.equals(this.getString(R.string.start_partcasos))){
            //EMISOR---->Consulta si hay casos de participantes por enviar
            if(totalPartCasosEnviar>contador){
                try { Thread.sleep(500); }
                catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
                //EMISOR---->Envía participante procesos
                enviarParticipanteCaso(contador);
                //Aumenta el contador
                contador++;
            }
            //Si no hay casos de participantes terminar proceso
            else{
                //EMISOR---->Envía mensaje de finalizar
                try { Thread.sleep(500); }
                catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
                sendMessage(this.getString(R.string.finished));
                try { Thread.sleep(2000); }
                catch (InterruptedException ex) { android.util.Log.d(TAG, ex.toString()); }
                //finaliza servicio
                if (mChatService != null) {
                    mChatService.stop();
                    mChatService = null;
                    mBluetoothAdapter.disable();
                }
                //Finaliza actividad
                Bundle arguments = new Bundle();
                if (mCaso !=null) arguments.putSerializable(Constants.CASA , mCaso);
                Intent i = new Intent(getActivity(),
                        ListaParticipantesCasoCovid19Activity.class);
                i.putExtras(arguments);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                getActivity().finish();

            }
        }
        //RECEPTOR---->Recibe caso de participante
        else if(mensaje.startsWith("INSERT INTO covid_participantes_casos")){
            participantesCasosSQL[totalCasosEnviados] = mensaje;
            totalCasosEnviados++;
            ////RECEPTOR---->Manda mensaje de enviar otro caso participante
            sendMessage(this.getString(R.string.start_partcasos));
        }
    	//RECEPTOR---->Recibe mensaje de que ya se termino, guarda y finaliza
    	else if(mensaje.equals(this.getString(R.string.finished))){
    		new SaveDataCasaTask().execute();
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
        	String filtroCaso = Covid19DBConstants.codigoCaso + "='"+ mCaso.getCodigoCaso()+"'";
            String filtroCHf = Covid19DBConstants.participante+" in (select participante from "+ Covid19DBConstants.COVID_PARTICIPANTES_CASOS_TABLE+" where "+Covid19DBConstants.codigoCaso+"='"+ mCaso.getCodigoCaso()+"')";
            String filtroPart = MainDBConstants.codigo+" in (select participante from "+ Covid19DBConstants.COVID_PARTICIPANTES_CASOS_TABLE+" where "+Covid19DBConstants.codigoCaso+"='"+ mCaso.getCodigoCaso()+"')";
			try {
				estudiosAdapter.open();
				mParticipantes = estudiosAdapter.getParticipantes(filtroPart, MainDBConstants.codigo);
				mParticipantesChf = estudiosAdapter.getParticipanteCohorteFamilias(filtroCHf, MainDBConstants.participante);
				mParticipantesCovid = estudiosAdapter.getParticipantesCovid19(filtroCHf, MainDBConstants.participante);
                mParticipantesCasos = estudiosAdapter.getParticipantesCasosCovid19(filtroCaso, CasosDBConstants.participante);
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
            totalPartCovEnviar = mParticipantesCovid.size();
            totalPartCasosEnviar = mParticipantesCasos.size();
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
                    if (estudiosAdapter.getCasoCovid19(Covid19DBConstants.codigoCaso + "='"+ codigoCaso +"'",null) == null)
                        estudiosAdapter.insertarCasoCovid19(casoSQL);
				} catch (Exception e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
					error = error + e.getMessage();
				}
                String[] codigosPartici = new String[totalPartEnviados];
				for(int i=0; i<totalPartEnviados;i++){
					try {
                        int codigoComienza = 34;
                        int codigoTermina = participantesSQL[i].indexOf(",",0);
                        String codigoParti = participantesSQL[i].substring(codigoComienza, codigoTermina);
                        codigosPartici[i] = codigoParti;
                        if (estudiosAdapter.getParticipante(MainDBConstants.codigo + "="+ codigoParti, null)==null)
                            estudiosAdapter.insertarParticipante(participantesSQL[i]);
					} catch (Exception e) {
						Log.e(TAG, e.getLocalizedMessage(), e);
						error = error + e.getMessage();
					}
				}
				for(int i=0; i<totalPartEnviados;i++){
					try {
                        if (estudiosAdapter.getParticipanteCohorteFamilia(MainDBConstants.participante + "="+ codigosPartici[i],null) == null)
                            estudiosAdapter.insertarParticipanteCohorteFamilia(participantesChfSQL[i]);
					} catch (Exception e) {
						Log.e(TAG, e.getLocalizedMessage(), e);
						error = error + e.getMessage();
					}
				}
                for(int i=0; i<totalPartCovEnviados;i++){
                    try {
                        int codigoComienza = 40;
                        int codigoTermina = participantesCovidSQL[i].indexOf(",",0);
                        String codigoParti = participantesCovidSQL[i].substring(codigoComienza, codigoTermina);
                        if (estudiosAdapter.getParticipanteCovid19(MainDBConstants.participante + "="+ codigoParti,null) == null)
                            estudiosAdapter.insertarParticipanteCovid19(participantesCovidSQL[i]);
                        CandidatoTransmisionCovid19  candidato = estudiosAdapter.getCandidatoTransmisionCovid19(Covid19DBConstants.participante + " = "+codigoParti + " and " + Covid19DBConstants.consentimiento + " = 'PENDIENTE'", null);
                        if (candidato!=null){
                            candidato.setConsentimiento("ACEPTA");
                            candidato.setEstado('0');
                            estudiosAdapter.editarCandidatoTransmisionCovid19(candidato);
                        }

                    } catch (Exception e) {
                        Log.e(TAG, e.getLocalizedMessage(), e);
                        error = error + e.getMessage();
                    }
                }

                for(int i=0; i<totalProcEnviados;i++){
                    try {
                        int codigoComienza = 43;
                        int codigoTermina = participantesProcSQL[i].indexOf(",",0);
                        String codigoParti = participantesProcSQL[i].substring(codigoComienza, codigoTermina);
                        ParticipanteProcesos procesos = estudiosAdapter.getParticipanteProcesos(ConstantsDB.CODIGO + "=" + codigoParti, null);
                        if (procesos == null) {
                            estudiosAdapter.insertarParticipanteProcesos(participantesProcSQL[i]);
                        }else{
                            String[] partes = participantesProcSQL[i].split(",");
                            String estudios = partes[4].replaceAll("'","");
                            String subEstudio = partes[51].replaceAll("'","");
                            procesos.setEstudio(estudios);
                            procesos.setSubEstudios(subEstudio);
                            procesos.getMovilInfo().setEstado("0");
                            estudiosAdapter.actualizarParticipanteProcesos(procesos);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getLocalizedMessage(), e);
                        error = error + e.getMessage();
                    }
                }
                for(int i=0; i<totalCasosEnviados;i++){
                    try {
                        int codigoComienza = 46;
                        int codigoTermina = participantesCasosSQL[i].indexOf(",",0);
                        String codigoPartCaso = participantesCasosSQL[i].substring(codigoComienza, codigoTermina);
                        if (estudiosAdapter.getParticipanteCasoCovid19(Covid19DBConstants.codigoCasoParticipante + "=" + codigoPartCaso, null) == null)
                            estudiosAdapter.insertarParticipanteCasoCovid19(participantesCasosSQL[i]);
                    } catch (Exception e) {
                        Log.e(TAG, e.getLocalizedMessage(), e);
                        error = error + e.getMessage();
                    }
                }
				String filtro = Covid19DBConstants.codigoCaso + "='"+ codigoCaso +"'";
				mCaso = estudiosAdapter.getCasoCovid19(filtro, null);
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
		        if (mChatService != null) {
		            mChatService.stop();
		            mChatService = null;
		            mBluetoothAdapter.disable();
		        }
				Bundle arguments = new Bundle();
		        if (mCaso !=null) arguments.putSerializable(Constants.CASA , mCaso);
		        Intent i = new Intent(getActivity(),
		        		ListaParticipantesCasoCovid19Activity.class);
		        i.putExtras(arguments);
		        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(i);
	        	getActivity().finish();
			}
		}

}
