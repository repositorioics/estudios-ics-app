package ni.org.ics.estudios.appmovil.database;

/**
 * Adaptador de la base de datos Cohorte
 * 
 * @author William Aviles
 */

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sqlcipher.database.SQLiteStatement;
import ni.org.ics.estudios.appmovil.catalogs.Barrio;
import ni.org.ics.estudios.appmovil.catalogs.Estudio;
import ni.org.ics.estudios.appmovil.catalogs.MessageResource;
import ni.org.ics.estudios.appmovil.domain.*;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.*;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.Muestra;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.casos.*;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.*;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaCasa;
import ni.org.ics.estudios.appmovil.domain.cohortefamilia.encuestas.EncuestaParticipante;
import ni.org.ics.estudios.appmovil.domain.covid19.*;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.ParticipanteCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.SintomasVisitaCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaCasoUO1;
import ni.org.ics.estudios.appmovil.domain.influenzauo1.VisitaVacunaUO1;
import ni.org.ics.estudios.appmovil.domain.muestreoanual.*;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaCasaSA;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.EncuestaParticipanteSA;
import ni.org.ics.estudios.appmovil.domain.seroprevalencia.ParticipanteSeroprevalencia;
import ni.org.ics.estudios.appmovil.domain.users.Authority;
import ni.org.ics.estudios.appmovil.domain.users.UserPermissions;
import ni.org.ics.estudios.appmovil.domain.users.UserSistema;
import ni.org.ics.estudios.appmovil.entomologia.constants.EntomologiaBConstants;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioHogar;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioHogarPoblacion;
import ni.org.ics.estudios.appmovil.entomologia.domain.CuestionarioPuntoClave;
import ni.org.ics.estudios.appmovil.entomologia.helpers.EntomologiaHelper;
import ni.org.ics.estudios.appmovil.helpers.*;
import ni.org.ics.estudios.appmovil.helpers.chf.casos.*;
import ni.org.ics.estudios.appmovil.helpers.covid19.*;
import ni.org.ics.estudios.appmovil.helpers.influenzauo1.ParticipanteCasoUO1Helper;
import ni.org.ics.estudios.appmovil.helpers.influenzauo1.SintomasVisitaCasoUO1Helper;
import ni.org.ics.estudios.appmovil.helpers.influenzauo1.VisitaCasoUO1Helper;
import ni.org.ics.estudios.appmovil.helpers.influenzauo1.VisitaVacunaUO1Helper;
import ni.org.ics.estudios.appmovil.helpers.seroprevalencia.EncuestaCasaSAHelper;
import ni.org.ics.estudios.appmovil.helpers.seroprevalencia.EncuestaParticipanteSAHelper;
import ni.org.ics.estudios.appmovil.helpers.seroprevalencia.ParticipanteSeroprevalenciaHelper;
import ni.org.ics.estudios.appmovil.muestreoanual.helpers.DatosPartoBBHelper;
import ni.org.ics.estudios.appmovil.muestreoanual.helpers.DocumentosHelper;
import ni.org.ics.estudios.appmovil.muestreoanual.helpers.NewVacunaHelper;
import ni.org.ics.estudios.appmovil.utils.*;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteQueryBuilder;
import ni.org.ics.estudios.appmovil.utils.muestreoanual.ConstantsDB;

public class EstudiosAdapter {

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mContext;
	private final String mPassword;
	private final boolean mFromServer;
	private final boolean mCleanDb;
	

	public EstudiosAdapter(Context context, String password, boolean fromServer, boolean cleanDb) {
		mContext = context;
		mPassword = password;
		mFromServer = fromServer;
		mCleanDb = cleanDb;
	}
	
	private static class DatabaseHelper extends EstudiosSQLiteOpenHelper {

		DatabaseHelper(Context context, String password, boolean fromServer, boolean cleanDb) {
			super(FileUtils.DATABASE_PATH, MainDBConstants.DATABASE_NAME, MainDBConstants.DATABASE_VERSION, context,
					password, fromServer, cleanDb);
			createStorage();
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(MainDBConstants.CREATE_USER_TABLE);
			db.execSQL(MainDBConstants.CREATE_ROLE_TABLE);
			db.execSQL(CatalogosDBConstants.CREATE_MESSAGES_TABLE);
			db.execSQL(CatalogosDBConstants.CREATE_BARRIO_TABLE);
			db.execSQL(CatalogosDBConstants.CREATE_ESTUDIO_TABLE);
			db.execSQL(MainDBConstants.CREATE_CASA_TABLE); 
			db.execSQL(MainDBConstants.CREATE_VISITA_TABLE);
			db.execSQL(MainDBConstants.CREATE_PRETAMIZAJE_TABLE);
            db.execSQL(MainDBConstants.CREATE_CASA_CHF_TABLE);
            db.execSQL(MainDBConstants.CREATE_PARTICIPANTE_TALBE);
            db.execSQL(MainDBConstants.CREATE_PARTICIPANTE_CHF_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_CASA_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_PARTOBB_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_PARTICIPANTE_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_PESOTALLA_TABLE);
            db.execSQL(MainDBConstants.CREATE_CARTACONSENTIMIENTO_TABLE);
            db.execSQL(EncuestasDBConstants.CREATE_ENCUESTA_LACTANCIAMAT_TABLE);
            db.execSQL(MuestrasDBConstants.CREATE_MUESTRA_TABLE);
            db.execSQL(MainDBConstants.CREATE_AREA_AMBIENTE_TABLE);
            db.execSQL(MainDBConstants.CREATE_CAMA_TABLE);
            db.execSQL(MainDBConstants.CREATE_PERSONACAMA_TABLE);
            db.execSQL(MainDBConstants.CREATE_TAMIZAJE_TABLE);
            db.execSQL(MainDBConstants.CREATE_CUARTO_TABLE);
            db.execSQL(SeroprevalenciaDBConstants.CREATE_PARTICIPANTESA_TABLE);
            db.execSQL(SeroprevalenciaDBConstants.CREATE_ENCUESTA_CASASA_TABLE);
            db.execSQL(SeroprevalenciaDBConstants.CREATE_ENCUESTA_PARTICIPANTESA_TABLE);
            db.execSQL(MainDBConstants.CREATE_TELEFONO_CONTACTO_TABLE);
            db.execSQL(MuestrasDBConstants.CREATE_RECEPCION_MUESTRA_TABLE);
            db.execSQL(CasosDBConstants.CREATE_CASAS_CASOS_TABLE);
            db.execSQL(CasosDBConstants.CREATE_PARTICIPANTES_CASOS_TABLE);
            db.execSQL(CasosDBConstants.CREATE_VISITAS_CASOS_TABLE);
            db.execSQL(CasosDBConstants.CREATE_CONTACTOS_CASOS_TABLE);
            db.execSQL(CasosDBConstants.CREATE_SINTOMAS_CASOS_TABLE);
            db.execSQL(CasosDBConstants.CREATE_VISITAS_FALLIDAS_CASOS_TABLE);
            db.execSQL(CasosDBConstants.CREATE_VISITAS_FINALES_CASOS_TABLE);

            //Muestreo anual
            db.execSQL(ConstantsDB.CREATE_ENCCASA_TABLE);
            db.execSQL(ConstantsDB.CREATE_ENCPART_TABLE);
            db.execSQL(ConstantsDB.CREATE_ENCLAC_TABLE);
            db.execSQL(ConstantsDB.CREATE_PT_TABLE);
            db.execSQL(ConstantsDB.CREATE_MUESTRA_TABLE);
            db.execSQL(ConstantsDB.CREATE_OB_TABLE);
            db.execSQL(ConstantsDB.CREATE_VIS_TABLE);
            db.execSQL(ConstantsDB.CREATE_BHC_TABLE);
            db.execSQL(ConstantsDB.CREATE_SERO_TABLE);
            db.execSQL(ConstantsDB.CREATE_TPBMC_TABLE);
            db.execSQL(ConstantsDB.CREATE_TRB_TABLE);
            db.execSQL(ConstantsDB.CREATE_PIN_TABLE);
            db.execSQL(ConstantsDB.CREATE_RND_TABLE);
            db.execSQL(ConstantsDB.CREATE_ENCSAT_TABLE);
            db.execSQL(ConstantsDB.CREATE_CAMBCASA_TABLE);
            db.execSQL(ConstantsDB.CREATE_PARTPROC_TABLE);
            db.execSQL(ConstantsDB.CREATE_USER_PERM_TABLE);
            db.execSQL(ConstantsDB.CREATE_DAT_VIS_TABLE);
            db.execSQL(ConstantsDB.CREATE_DATOSPARTOBB_TABLE);
            db.execSQL(ConstantsDB.CREATE_NEWVAC_TABLE);
            db.execSQL(ConstantsDB.CREATE_DOCS_TABLE);

            db.execSQL(MainDBConstants.CREATE_CONTACTO_PARTICIPANTE_TABLE);
            //reconsentimiento dengue 2018
            db.execSQL(MainDBConstants.CREATE_VISITAPART_TABLE);
            db.execSQL(MainDBConstants.CREATE_DATOS_COORDENADAS_TABLE);
            db.execSQL(MainDBConstants.CREATE_ENFCRONICA_TABLE);

            db.execSQL(MainDBConstants.CREATE_OBSEQUIOS_TABLE);
            //Muestras de superficie
            db.execSQL(MuestrasDBConstants.CREATE_MUESTRA_SUPERFICIE_TABLE);
            //UO1
			db.execSQL(InfluenzaUO1DBConstants.CREATE_UO1_PARTICIPANTES_CASOS_TABLE);
			db.execSQL(InfluenzaUO1DBConstants.CREATE_UO1_VISITAS_CASOS_TABLE);
			db.execSQL(InfluenzaUO1DBConstants.CREATE_UO1_VISITAS_VACUNAS_TABLE);
			db.execSQL(InfluenzaUO1DBConstants.CREATE_UO1_SINTOMAS_VISITA_CASO_TABLE);
			//sensores casos seguimiento
			db.execSQL(CasosDBConstants.CREATE_SENSORES_CASOS_TABLE);
			//Covid19
			db.execSQL(Covid19DBConstants.CREATE_PARTICIPANTE_COVID_TABLE);
			db.execSQL(Covid19DBConstants.CREATE_COVID_CASOS_TABLE);
			db.execSQL(Covid19DBConstants.CREATE_COVID_PARTICIPANTES_CASOS_TABLE);
			db.execSQL(Covid19DBConstants.CREATE_COVID_VISITAS_CASOS_TABLE);
			db.execSQL(Covid19DBConstants.CREATE_COVID_VISITAS_FALLIDAS_CASOS_TABLE);
			db.execSQL(Covid19DBConstants.CREATE_COVID_CANDIDATO_TRANSMISION_TABLE);
			db.execSQL(Covid19DBConstants.CREATE_COVID_SINTOMAS_VISITA_CASO_TABLE);
			db.execSQL(Covid19DBConstants.CREATE_COVID_DATOS_AISLAMIENTO_VC_TABLE);
			db.execSQL(Covid19DBConstants.CREATE_COVID_VISITA_FINAL_CASO_TABLE);
			db.execSQL(Covid19DBConstants.CREATE_COVID_SINT_VISITA_FINAL_CASO_TABLE);
			//MA Adicional covid19 CHF
			db.execSQL(Covid19DBConstants.CREATE_COVID_CUESTIONARIO_TABLE);
			db.execSQL(Covid19DBConstants.CREATE_COVID_OTROS_POSITIVOS_TABLE);
			//Entomologia 2022
			db.execSQL(EntomologiaBConstants.CREATE_ENTO_CUESTIONARIO_HOGAR_TABLE);
			db.execSQL(EntomologiaBConstants.CREATE_ENTO_CUESTIONARIO_HOGAR_POB_TABLE);
			db.execSQL(EntomologiaBConstants.CREATE_ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE);

			/*Nueva tabla perimetroabdominal fecha creacion 27/01/2023 Ing. Santiago Carballo*/
			db.execSQL(ConstantsDB.CREATE_PAD_ABDOMINAL_TABLE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			onCreate(db);
			if(oldVersion==0) return;
			if(oldVersion==1){
                db.execSQL("ALTER TABLE " + MainDBConstants.VISITA_TABLE + " ADD COLUMN " + MainDBConstants.otraRazonVisitaNoExitosa + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.PRETAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.otraRazonNoAceptaTamizajeCasa + " text");
			}
            if(oldVersion==3){
                db.execSQL("ALTER TABLE " + CasosDBConstants.VISITAS_FALLIDAS_CASOS_TABLE + " ADD COLUMN " + CasosDBConstants.visita + " text");
            }
            if(oldVersion==4){
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.pretermino + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.cohorte + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.enfermedadInmuno + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.cualEnfermedad + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.tratamiento + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.cualTratamiento + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.diagDengue + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.fechaDiagDengue + " date");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.hospDengue + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.fechaHospDengue + " date");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.tiempoResidencia + " text");

            }
            if (oldVersion==5){
                db.execSQL(MainDBConstants.CREATE_CONTACTO_PARTICIPANTE_TABLE);
            }
            if (oldVersion==6){
                db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.VOLRETOMAPBMC + " real");
                db.execSQL("DROP TABLE " + SeroprevalenciaDBConstants.PARTICIPANTESA_TABLE);
                db.execSQL(SeroprevalenciaDBConstants.CREATE_PARTICIPANTESA_TABLE);
                db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.consSa + " text");
            }
            //reconsentimiento dengue 2018
            if (oldVersion==7){
                db.execSQL("DROP TABLE " + MainDBConstants.CONTACTO_PARTICIPANTE_TABLE);
                db.execSQL(MainDBConstants.CREATE_CONTACTO_PARTICIPANTE_TABLE);
                db.execSQL(MainDBConstants.CREATE_VISITAPART_TABLE);

                db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.otroMotivoRechazoParteA + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.motivoRechazoParteDExt + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.otroMotivoRechazoParteDExt + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.mismoTutor + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.motivoDifTutor + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.otroMotivoDifTutor + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.otraRelacionFamTutor + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.verifTutor + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.reconsentimiento + " text");

                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.tipoVivienda + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.otraEnfCronica + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.enfCronicaAnio + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.enfCronicaMes + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.otroTx + " text");
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.autorizaSupervisor + " text");
            }
            if (oldVersion==8){
                db.execSQL("DROP TABLE " + MainDBConstants.TAMIZAJE_TABLE);
                db.execSQL(MainDBConstants.CREATE_TAMIZAJE_TABLE);
                db.execSQL(MainDBConstants.CREATE_ENFCRONICA_TABLE);
            }
            if (oldVersion==9) {
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.enfermedadCronica + " text");
                db.execSQL("ALTER TABLE " + CasosDBConstants.SINTOMAS_CASOS_TABLE + " ADD COLUMN " + CasosDBConstants.respiracionRapida + " text");
            }
            if (oldVersion==10) {
                db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.codigoParticipanteRecon + " integer");
            }
            if (oldVersion==11){
                db.execSQL("ALTER TABLE " + ConstantsDB.DATOSPARTOBB_TABLE + " ADD COLUMN " + ConstantsDB.tallaBB_sndr + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.DATOSPARTOBB_TABLE + " ADD COLUMN " + ConstantsDB.tallaBB + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.DATOSPARTOBB_TABLE + " ADD COLUMN " + ConstantsDB.docMedTallaBB_sn + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.DATOSPARTOBB_TABLE + " ADD COLUMN " + ConstantsDB.docMedTallaBB + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.DATOSPARTOBB_TABLE + " ADD COLUMN " + ConstantsDB.otroDocMedTallaBB + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.DATOSPARTOBB_TABLE + " ADD COLUMN " + ConstantsDB.vacFluMadre_sn + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.DATOSPARTOBB_TABLE + " ADD COLUMN " + ConstantsDB.fechaVacInf + " date");
                db.execSQL("ALTER TABLE " + ConstantsDB.DATOSPARTOBB_TABLE + " ADD COLUMN " + ConstantsDB.docMedFecVacInfMadre_sn + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.DATOSPARTOBB_TABLE + " ADD COLUMN " + ConstantsDB.docMedFecVacInfMadre + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.DATOSPARTOBB_TABLE + " ADD COLUMN " + ConstantsDB.otroDocMedFecVacInfMadre + " text");
            }
            if (oldVersion==12){
                db.execSQL(MainDBConstants.CREATE_OBSEQUIOS_TABLE);
            }
            if (oldVersion==13){
                db.execSQL("ALTER TABLE " + ConstantsDB.ENC_PART_TABLE + " ADD COLUMN " + ConstantsDB.vacunaInfluenzaMes + " integer");
                db.execSQL("ALTER TABLE " + ConstantsDB.ENC_PART_TABLE + " ADD COLUMN " + ConstantsDB.vacunaInfluenzaCSSF + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.ENC_PART_TABLE + " ADD COLUMN " + ConstantsDB.vacunaInfluenzaOtro + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.ENC_PART_TABLE + " ADD COLUMN " + ConstantsDB.nombreCDI + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.ENC_PART_TABLE + " ADD COLUMN " + ConstantsDB.direccionCDI + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.OBSEQUIOCHF + " text");
            }
            if (oldVersion==14){
                db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.cDatosParto + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.reConsChf18 + " text");
            }
            if (oldVersion==15) {
                db.execSQL("ALTER TABLE " + ConstantsDB.DATOSPARTOBB_TABLE + " ADD COLUMN " + ConstantsDB.docMedFUM_sn + " text");
            }
            if (oldVersion==16) {
                db.execSQL("ALTER TABLE " + ConstantsDB.MUESTRA_TABLE + " ADD COLUMN " + ConstantsDB.hd_sn + " text");
                db.execSQL("ALTER TABLE " + ConstantsDB.MUESTRA_TABLE + " ADD COLUMN " + ConstantsDB.hdPorqueNo + " text");
            }
            if (oldVersion==17) {
                db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.posDengue + " text");
            }
            if (oldVersion==18){
                db.execSQL(MuestrasDBConstants.CREATE_MUESTRA_SUPERFICIE_TABLE);
                db.execSQL("DROP TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE);
                db.execSQL(MainDBConstants.CREATE_CARTACONSENTIMIENTO_TABLE);
                db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.mxSuperficie + " text");
            }
            if (oldVersion==19){
                db.execSQL("ALTER TABLE " + MainDBConstants.VISITAPART_TABLE + " ADD COLUMN " + MainDBConstants.estudio + " text");
            }
            if (oldVersion==20){
				//UO1
				db.execSQL(InfluenzaUO1DBConstants.CREATE_UO1_PARTICIPANTES_CASOS_TABLE);
				db.execSQL(InfluenzaUO1DBConstants.CREATE_UO1_VISITAS_CASOS_TABLE);
			}
			if (oldVersion==21){
				//continuación UO1
				db.execSQL(InfluenzaUO1DBConstants.CREATE_UO1_VISITAS_VACUNAS_TABLE);
			}
			if (oldVersion==22){
				db.execSQL("ALTER TABLE " + InfluenzaUO1DBConstants.UO1_VISITAS_CASOS_TABLE + " ADD COLUMN " + InfluenzaUO1DBConstants.lugar + " text");
			}
			if (oldVersion==23){
				db.execSQL("ALTER TABLE " + CasosDBConstants.SINTOMAS_CASOS_TABLE + " ADD COLUMN " + CasosDBConstants.fiebreIntensidad + " text");
				db.execSQL("ALTER TABLE " + CasosDBConstants.SINTOMAS_CASOS_TABLE + " ADD COLUMN " + CasosDBConstants.dolorCabezaIntensidad + " text");
				db.execSQL("ALTER TABLE " + CasosDBConstants.SINTOMAS_CASOS_TABLE + " ADD COLUMN " + CasosDBConstants.dolorArticularIntensidad + " text");
				db.execSQL("ALTER TABLE " + CasosDBConstants.SINTOMAS_CASOS_TABLE + " ADD COLUMN " + CasosDBConstants.dolorMuscularIntensidad + " text");
				db.execSQL("ALTER TABLE " + CasosDBConstants.SINTOMAS_CASOS_TABLE + " ADD COLUMN " + CasosDBConstants.secrecionNasalIntensidad + " text");
				db.execSQL("ALTER TABLE " + CasosDBConstants.SINTOMAS_CASOS_TABLE + " ADD COLUMN " + CasosDBConstants.tosIntensidad + " text");
				db.execSQL("ALTER TABLE " + CasosDBConstants.SINTOMAS_CASOS_TABLE + " ADD COLUMN " + CasosDBConstants.dolorGargantaIntensidad + " text");
			}
			if (oldVersion==24){
				//continuación UO1
				db.execSQL(InfluenzaUO1DBConstants.CREATE_UO1_SINTOMAS_VISITA_CASO_TABLE);
			}
			if (oldVersion==25){
				db.execSQL("ALTER TABLE " + ConstantsDB.VIS_TABLE + " ADD COLUMN " + ConstantsDB.estudiaSN + " text");
				db.execSQL("ALTER TABLE " + ConstantsDB.VIS_TABLE + " ADD COLUMN " + ConstantsDB.nEscuela + " text");
				db.execSQL("ALTER TABLE " + ConstantsDB.VIS_TABLE + " ADD COLUMN " + ConstantsDB.otraEscuela + " text");
				db.execSQL("ALTER TABLE " + ConstantsDB.VIS_TABLE + " ADD COLUMN " + ConstantsDB.turno + " text");
			}
			if (oldVersion==26){
				//sensores casos seguimiento
				db.execSQL(CasosDBConstants.CREATE_SENSORES_CASOS_TABLE);
				db.execSQL("ALTER TABLE " + ConstantsDB.VIS_TABLE + " ADD COLUMN " + ConstantsDB.otroMotNoVisita + " text");
				db.execSQL("ALTER TABLE " + ConstantsDB.PT_TABLE + " ADD COLUMN " + ConstantsDB.estudiosAct + " text");
				db.execSQL("ALTER TABLE " + MainDBConstants.AREA_AMBIENTE_TABLE + " ADD COLUMN " + MainDBConstants.numeroCuarto + " text");
			}
			if (oldVersion==27){
				db.execSQL("ALTER TABLE " + ConstantsDB.MUESTRA_TABLE + " ADD COLUMN " + ConstantsDB.tuboPax + " integer");
			}
			if (oldVersion==28){
				db.execSQL("ALTER TABLE " + ConstantsDB.ENC_CASA_TABLE + " ADD COLUMN " + ConstantsDB.participante + " integer");
			}
			if (oldVersion==29){
				db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.consCovid19 + " text");
				db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.subEstudios + " text");
				db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.aceptaParteE + " text");
				db.execSQL(Covid19DBConstants.CREATE_PARTICIPANTE_COVID_TABLE);
			}
			if (oldVersion==30){
				db.execSQL(Covid19DBConstants.CREATE_COVID_CASOS_TABLE);
				db.execSQL(Covid19DBConstants.CREATE_COVID_PARTICIPANTES_CASOS_TABLE);
				db.execSQL(Covid19DBConstants.CREATE_COVID_VISITAS_CASOS_TABLE);
				db.execSQL(Covid19DBConstants.CREATE_COVID_VISITAS_FALLIDAS_CASOS_TABLE);
				db.execSQL(Covid19DBConstants.CREATE_COVID_CANDIDATO_TRANSMISION_TABLE);
				db.execSQL(Covid19DBConstants.CREATE_COVID_SINTOMAS_VISITA_CASO_TABLE);
				db.execSQL(Covid19DBConstants.CREATE_COVID_DATOS_AISLAMIENTO_VC_TABLE);
			}
			if (oldVersion==31){
				db.execSQL("ALTER TABLE " + Covid19DBConstants.COVID_CANDIDATO_TRANSMISION_TABLE + " ADD COLUMN " + Covid19DBConstants.fechaIngreso + " date");
			}
			if (oldVersion==32) {
				db.execSQL(Covid19DBConstants.CREATE_COVID_VISITA_FINAL_CASO_TABLE);
				db.execSQL(Covid19DBConstants.CREATE_COVID_SINT_VISITA_FINAL_CASO_TABLE);
			}
			if (oldVersion==33){
				db.execSQL(Covid19DBConstants.CREATE_COVID_CUESTIONARIO_TABLE);
				db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.consChf + " text");
				db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.cuestCovid + " text");
				db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.muestraCovid + " text");
				db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.motivoRechazoParteE + " text");
				db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.otroMotivoRechazoParteE + " text");
			}
			if (oldVersion==34){
				db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.posCovid + " text");
			}
			if (oldVersion==35){
				db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.consDenParteE + " text");
				db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.mxDenParteE + " text");
				db.execSQL("ALTER TABLE " + MainDBConstants.TAMIZAJE_TABLE + " ADD COLUMN " + MainDBConstants.tipoAsentimiento + " text");
			}
			if (oldVersion==36){
				db.execSQL("ALTER TABLE " + CasosDBConstants.PARTICIPANTES_CASOS_TABLE + " ADD COLUMN " + CasosDBConstants.fis + " text");
			}
			if (oldVersion==37){
				db.execSQL("ALTER TABLE " + Covid19DBConstants.COVID_CUESTIONARIO_TABLE + " ADD COLUMN " + Covid19DBConstants.e1Embarazada + " text");
				db.execSQL("ALTER TABLE " + Covid19DBConstants.COVID_CUESTIONARIO_TABLE + " ADD COLUMN " + Covid19DBConstants.e1RecuerdaSemanasEmb + " text");
				db.execSQL("ALTER TABLE " + Covid19DBConstants.COVID_CUESTIONARIO_TABLE + " ADD COLUMN " + Covid19DBConstants.e1SemanasEmbarazo + " integer");
				db.execSQL("ALTER TABLE " + Covid19DBConstants.COVID_CUESTIONARIO_TABLE + " ADD COLUMN " + Covid19DBConstants.e1FinalEmbarazo + " text");
				db.execSQL("ALTER TABLE " + Covid19DBConstants.COVID_CUESTIONARIO_TABLE + " ADD COLUMN " + Covid19DBConstants.e1OtroFinalEmbarazo + " text");
				db.execSQL("ALTER TABLE " + Covid19DBConstants.COVID_CUESTIONARIO_TABLE + " ADD COLUMN " + Covid19DBConstants.e1DabaPecho + " text");
				db.execSQL("ALTER TABLE " + Covid19DBConstants.COVID_CUESTIONARIO_TABLE + " ADD COLUMN " + Covid19DBConstants.trabajadorSalud + " text");
			}
			if (oldVersion==38) {
				db.execSQL("ALTER TABLE " + Covid19DBConstants.COVID_CUESTIONARIO_TABLE + " ADD COLUMN " + Covid19DBConstants.periodoSintomas + " text");
			}
			if (oldVersion==39) {
				db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.informacionRetiro + " text");
			}
			if (oldVersion==40) {
				db.execSQL("ALTER TABLE " + Covid19DBConstants.COVID_CANDIDATO_TRANSMISION_TABLE + " ADD COLUMN " + Covid19DBConstants.indice + " text");
				db.execSQL(Covid19DBConstants.CREATE_COVID_OTROS_POSITIVOS_TABLE);
			}
			if (oldVersion==41) {
				db.execSQL("ALTER TABLE " + Covid19DBConstants.COVID_CUESTIONARIO_TABLE + " ADD COLUMN " + Covid19DBConstants.fechaEventoEnfermoPostVac + " text");
			}
			if (oldVersion==42) {
				db.execSQL("ALTER TABLE " + InfluenzaUO1DBConstants.UO1_PARTICIPANTES_CASOS_TABLE + " ADD COLUMN " + InfluenzaUO1DBConstants.fis + " date");
				db.execSQL("ALTER TABLE " + CasosDBConstants.PARTICIPANTES_CASOS_TABLE + " ADD COLUMN " + CasosDBConstants.positivoPor + " text");
			}
			if (oldVersion==43) {
				db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.aceptaParteF + " text");
				db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.motivoRechazoParteF + " text");
				db.execSQL("ALTER TABLE " + MainDBConstants.CARTA_CONSENTIMIENTO_TABLE + " ADD COLUMN " + MainDBConstants.otroMotivoRechazoParteF + " text");
			}
			if (oldVersion==44) {
				//Entomologia 2022
				db.execSQL(EntomologiaBConstants.CREATE_ENTO_CUESTIONARIO_HOGAR_TABLE);
				db.execSQL(EntomologiaBConstants.CREATE_ENTO_CUESTIONARIO_HOGAR_POB_TABLE);
			}
			if (oldVersion==45) {
				//sigue Entomologia 2022
				db.execSQL(EntomologiaBConstants.CREATE_ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE);
			}
			if (oldVersion==46) {
				/*Nueva tabla perimetroabdominal fecha creacion 27/01/2023 Ing. Santiago Carballo*/
				db.execSQL(ConstantsDB.CREATE_PAD_ABDOMINAL_TABLE);
				db.execSQL("ALTER TABLE " + ConstantsDB.PART_PROCESOS_TABLE + " ADD COLUMN " + ConstantsDB.PABDOMINAL + " text");
				db.execSQL("ALTER TABLE " + ConstantsDB.USER_PERM_TABLE + " ADD COLUMN " + ConstantsDB.U_PABDOMINAL + " text");
			}
		}
	}

	public EstudiosAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mContext,mPassword,mFromServer,mCleanDb);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}
	
	/**
	 * Crea un cursor desde la base de datos
	 * 
	 * @return cursor
	 */
	public Cursor crearCursor(String tabla, String whereString, String projection[], String ordenString) throws SQLException {
		Cursor c = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(tabla);
		c = qb.query(mDb,projection,whereString,null,null,null,ordenString);
		return c;
	}

	public static boolean createStorage() {
		return FileUtils.createFolder(FileUtils.DATABASE_PATH);
	}
	
	/**
	 * Metodos para usuarios en la base de datos
	 * 
	 * @param user
	 *            Objeto Usuario que contiene la informacion
	 *
	 */
	//Crear nuevo usuario en la base de datos
	public void crearUsuario(UserSistema user) {
		ContentValues cv = UserSistemaHelper.crearUserSistemaContentValues(user);
		mDb.insertOrThrow(MainDBConstants.USER_TABLE, null, cv);
	}
	//Editar usuario existente en la base de datos
	public boolean editarUsuario(UserSistema user) {
		ContentValues cv = UserSistemaHelper.crearUserSistemaContentValues(user);
		return mDb.update(MainDBConstants.USER_TABLE, cv, MainDBConstants.username + "='" 
				+ user.getUsername()+"'", null) > 0;
	}
	//Limpiar la tabla de usuarios de la base de datos
	public boolean borrarUsuarios() {
		return mDb.delete(MainDBConstants.USER_TABLE, null, null) > 0;
	}
	//Obtener un usuario de la base de datos
	public UserSistema getUsuario(String filtro, String orden) throws SQLException {
		UserSistema mUser = null;
		Cursor cursorUser = crearCursor(MainDBConstants.USER_TABLE, filtro, null, orden);
		if (cursorUser != null && cursorUser.getCount() > 0) {
			cursorUser.moveToFirst();
			mUser=UserSistemaHelper.crearUserSistema(cursorUser);
		}
		if (!cursorUser.isClosed()) cursorUser.close();
		return mUser;
	}
	//Obtener una lista de usuarios de la base de datos
	public List<UserSistema> getUsuarios(String filtro, String orden) throws SQLException {
		List<UserSistema> mUsuarios = new ArrayList<UserSistema>();
		Cursor cursorUsuarios = crearCursor(MainDBConstants.USER_TABLE, filtro, null, orden);
		if (cursorUsuarios != null && cursorUsuarios.getCount() > 0) {
			cursorUsuarios.moveToFirst();
			mUsuarios.clear();
			do{
				UserSistema mUser = null;
				mUser = UserSistemaHelper.crearUserSistema(cursorUsuarios);
				mUsuarios.add(mUser);
			} while (cursorUsuarios.moveToNext());
		}
		if (!cursorUsuarios.isClosed()) cursorUsuarios.close();
		return mUsuarios;
	}
	
	/**
	 * Metodos para roles en la base de datos
	 * 
	 * @param rol
	 *            Objeto Authority que contiene la informacion
	 *
	 */
	//Crear nuevo rol en la base de datos
	public void crearRol(Authority rol) {
		ContentValues cv = UserSistemaHelper.crearRolValues(rol);
		mDb.insertOrThrow(MainDBConstants.ROLE_TABLE, null, cv);
	}
	//Limpiar la tabla de roles de la base de datos
	public boolean borrarRoles() {
		return mDb.delete(MainDBConstants.ROLE_TABLE, null, null) > 0;
	}
	//Verificar un rol de usuario
	public Boolean buscarRol(String username, String Rol) throws SQLException {
		Cursor c = mDb.query(true, MainDBConstants.ROLE_TABLE, null,
				MainDBConstants.username + "='" + username + "' and " + MainDBConstants.role + "='" + Rol + "'" , null, null, null, null, null);
		boolean result = c != null && c.getCount()>0; 
		c.close();
		return result;
	}

    //Obtener los permisos de un usuario de la base de datos
    public UserPermissions getPermisosUsuario(String filtro, String orden) throws SQLException {
        UserPermissions mUser = null;
        Cursor cursorUser = crearCursor(ConstantsDB.USER_PERM_TABLE, filtro, null, orden);
        if (cursorUser != null && cursorUser.getCount() > 0) {
            cursorUser.moveToFirst();
            mUser=UserSistemaHelper.crearUserPermissions(cursorUser);
        }
        if (!cursorUser.isClosed()) cursorUser.close();
        return mUser;
    }
	
	/**
	 * Metodos para barrios en la base de datos
	 * 
	 * @param barrio
	 *            Objeto Barrio que contiene la informacion
	 *
	 */
	//Crear nuevo barrio en la base de datos
	public void crearBarrio(Barrio barrio) {
		ContentValues cv = BarrioHelper.crearBarrioContentValues(barrio);
		mDb.insertOrThrow(CatalogosDBConstants.BARRIO_TABLE, null, cv);
	}
	//Editar barrio existente en la base de datos
	public boolean editarBarrio(Barrio barrio) {
		ContentValues cv = BarrioHelper.crearBarrioContentValues(barrio);
		return mDb.update(CatalogosDBConstants.BARRIO_TABLE , cv, MainDBConstants.codigo + "=" 
				+ barrio.getCodigo(), null) > 0;
	}

	//Limpiar la tabla de barrios de la base de datos
	public boolean borrarBarrios() {
		return mDb.delete(CatalogosDBConstants.BARRIO_TABLE, null, null) > 0;
	}

	//Obtener un barrio de la base de datos
	public Barrio getBarrio(String filtro, String orden) throws SQLException {
		Barrio mBarrio = null;
		Cursor cursorBarrio = crearCursor(CatalogosDBConstants.BARRIO_TABLE , filtro, null, orden);
		if (cursorBarrio != null && cursorBarrio.getCount() > 0) {
			cursorBarrio.moveToFirst();
			mBarrio=BarrioHelper.crearBarrio(cursorBarrio);
		}
		if (!cursorBarrio.isClosed()) cursorBarrio.close();
		return mBarrio;
	}

	//Obtener una lista de barrios de la base de datos
	public List<Barrio> getBarrios(String filtro, String orden) throws SQLException {
		List<Barrio> mBarrios = new ArrayList<Barrio>();
		Cursor cursorBarrios = crearCursor(CatalogosDBConstants.BARRIO_TABLE, filtro, null, orden);
		if (cursorBarrios != null && cursorBarrios.getCount() > 0) {
			cursorBarrios.moveToFirst();
			mBarrios.clear();
			do{
				Barrio mBarrio = null;
				mBarrio = BarrioHelper.crearBarrio(cursorBarrios);
				mBarrios.add(mBarrio);
			} while (cursorBarrios.moveToNext());
		}
		if (!cursorBarrios.isClosed()) cursorBarrios.close();
		return mBarrios;
	}
	
	
	/**
	 * Metodos para estudios en la base de datos
	 * 
	 * @param estudio
	 *            Objeto Estudio que contiene la informacion
	 *
	 */
	//Crear nuevo estudio en la base de datos
	public void crearEstudio(Estudio estudio) {
		ContentValues cv = EstudiosHelper.crearEstudioContentValues(estudio);
		mDb.insertOrThrow(CatalogosDBConstants.ESTUDIO_TABLE, null, cv);
	}
	//Editar estudio existente en la base de datos
	public boolean editarEstudio(Estudio estudio) {
		ContentValues cv = EstudiosHelper.crearEstudioContentValues(estudio);
		return mDb.update(CatalogosDBConstants.ESTUDIO_TABLE , cv, MainDBConstants.codigo + "=" 
				+ estudio.getCodigo(), null) > 0;
	}
	//Limpiar la tabla de estudios de la base de datos
	public boolean borrarEstudios() {
		return mDb.delete(CatalogosDBConstants.ESTUDIO_TABLE, null, null) > 0;
	}
	//Obtener un estudio de la base de datos
	public Estudio getEstudio(String filtro, String orden) throws SQLException {
		Estudio mEstudio = null;
		Cursor cursorEstudio = crearCursor(CatalogosDBConstants.ESTUDIO_TABLE , filtro, null, orden);
		if (cursorEstudio != null && cursorEstudio.getCount() > 0) {
			cursorEstudio.moveToFirst();
			mEstudio=EstudiosHelper.crearEstudio(cursorEstudio);
		}
		if (!cursorEstudio.isClosed()) cursorEstudio.close();
		return mEstudio;
	}
	//Obtener una lista de estudios de la base de datos
	public List<Estudio> getEstudios(String filtro, String orden) throws SQLException {
		List<Estudio> mEstudios = new ArrayList<Estudio>();
		Cursor cursorEstudios = crearCursor(CatalogosDBConstants.ESTUDIO_TABLE, filtro, null, orden);
		if (cursorEstudios != null && cursorEstudios.getCount() > 0) {
			cursorEstudios.moveToFirst();
			mEstudios.clear();
			do{
				Estudio mEstudio = null;
				mEstudio = EstudiosHelper.crearEstudio(cursorEstudios);
				mEstudios.add(mEstudio);
			} while (cursorEstudios.moveToNext());
		}
		if (!cursorEstudios.isClosed()) cursorEstudios.close();
		return mEstudios;
	}	
	
	
	/**
	 * Metodos para casas en la base de datos
	 * 
	 * @param casa
	 *            Objeto Casa que contiene la informacion
	 *
	 */
	//Crear nuevo Casa en la base de datos
	public void crearCasa(Casa casa) {
		ContentValues cv = CasaHelper.crearCasaContentValues(casa);
		mDb.insertOrThrow(MainDBConstants.CASA_TABLE, null, cv);
	}
	//Editar Casa existente en la base de datos
	public boolean editarCasa(Casa casa) {
		ContentValues cv = CasaHelper.crearCasaContentValues(casa);
		return mDb.update(MainDBConstants.CASA_TABLE , cv, MainDBConstants.codigo + "=" 
				+ casa.getCodigo(), null) > 0;
	}
	//Limpiar la tabla de casas de la base de datos
	public boolean borrarCasas() {
		return mDb.delete(MainDBConstants.CASA_TABLE, null, null) > 0;
	}
	//Obtener un casa de la base de datos
	public Casa getCasa(String filtro, String orden) throws SQLException {
		Casa mCasa = null;
		Cursor cursorCasa = crearCursor(MainDBConstants.CASA_TABLE , filtro, null, orden);
		if (cursorCasa != null && cursorCasa.getCount() > 0) {
			cursorCasa.moveToFirst();
			mCasa=CasaHelper.crearCasa(cursorCasa);
			Barrio barrio = this.getBarrio(MainDBConstants.codigo + "=" +cursorCasa.getInt(cursorCasa.getColumnIndex(MainDBConstants.barrio)), orden);
			mCasa.setBarrio(barrio);
		}
		if (!cursorCasa.isClosed()) cursorCasa.close();
		return mCasa;
	}
	//Obtener una lista de casas de la base de datos
	public List<Casa> getCasas(String filtro, String orden) throws SQLException {
		List<Casa> mCasas = new ArrayList<Casa>();
		Cursor cursorCasas = crearCursor(MainDBConstants.CASA_TABLE, filtro, null, orden);
		if (cursorCasas != null && cursorCasas.getCount() > 0) {
			cursorCasas.moveToFirst();
			mCasas.clear();
			do{
				Casa mCasa = null;
				mCasa = CasaHelper.crearCasa(cursorCasas);
				Barrio barrio = this.getBarrio(MainDBConstants.codigo + "=" +cursorCasas.getInt(cursorCasas.getColumnIndex(MainDBConstants.barrio)), orden);
				mCasa.setBarrio(barrio);
				mCasas.add(mCasa);
			} while (cursorCasas.moveToNext());
		}
		if (!cursorCasas.isClosed()) cursorCasas.close();
		return mCasas;
	}
	
	
	/**
	 * Metodos para mensajes en la base de datos
	 * 
	 * @param mensaje
	 *            Objeto MessageResource que contiene la informacion
	 *
	 */
	//Crear nuevo MessageResource en la base de datos
	public void crearMessageResource(MessageResource mensaje) {
		ContentValues cv = MessageResourceHelper.crearMessageResourceValues(mensaje);
		mDb.insertOrThrow(CatalogosDBConstants.MESSAGES_TABLE, null, cv);
	}
	//Editar MessageResource existente en la base de datos
	public boolean editarMessageResource(MessageResource mensaje) {
		ContentValues cv = MessageResourceHelper.crearMessageResourceValues(mensaje);
		return mDb.update(CatalogosDBConstants.MESSAGES_TABLE , cv, CatalogosDBConstants.messageKey + "='" 
				+ mensaje.getMessageKey() + "'", null) > 0;
	}
	//Limpiar la tabla de MessageResource de la base de datos
	public boolean borrarMessageResource() {
		return mDb.delete(CatalogosDBConstants.MESSAGES_TABLE, null, null) > 0;
	}
	//Obtener un MessageResource de la base de datos
	public MessageResource getMessageResource(String filtro, String orden) throws SQLException {
		MessageResource mMessageResource = null;
		Cursor cursorMessageResource = crearCursor(CatalogosDBConstants.MESSAGES_TABLE , filtro, null, orden);
		if (cursorMessageResource != null && cursorMessageResource.getCount() > 0) {
			cursorMessageResource.moveToFirst();
			mMessageResource=MessageResourceHelper.crearMessageResource(cursorMessageResource);
		}
		if (!cursorMessageResource.isClosed()) cursorMessageResource.close();
		return mMessageResource;
	}
	//Obtener una lista de MessageResource de la base de datos
	public List<MessageResource> getMessageResources(String filtro, String orden) throws SQLException {
		List<MessageResource> mMessageResources = new ArrayList<MessageResource>();
		Cursor cursorMessageResources = crearCursor(CatalogosDBConstants.MESSAGES_TABLE, filtro, null, orden);
		if (cursorMessageResources != null && cursorMessageResources.getCount() > 0) {
			cursorMessageResources.moveToFirst();
			mMessageResources.clear();
			do{
				MessageResource mMessageResource = null;
				mMessageResource = MessageResourceHelper.crearMessageResource(cursorMessageResources);
				mMessageResources.add(mMessageResource);
			} while (cursorMessageResources.moveToNext());
		}
		if (!cursorMessageResources.isClosed()) cursorMessageResources.close();
		return mMessageResources;
	}

    //Obtener una lista de MessageResource de la base de datos
    public String[] getSpanishMessageResources(String filtro, String orden) throws SQLException {
        Cursor cursorMessageResources = crearCursor(CatalogosDBConstants.MESSAGES_TABLE, filtro, null, orden);
        String[] mMessageResources = null;
        if (cursorMessageResources != null && cursorMessageResources.getCount() > 0) {
            cursorMessageResources.moveToFirst();
            mMessageResources = new String[cursorMessageResources.getCount()];
            int indice = 0;
            do{
                MessageResource mMessageResource;
                mMessageResource = MessageResourceHelper.crearMessageResource(cursorMessageResources);
                mMessageResources[indice]= mMessageResource.getSpanish();
                indice++;
            } while (cursorMessageResources.moveToNext());
        }
        if (!cursorMessageResources.isClosed()) cursorMessageResources.close();
        return mMessageResources;
    }
	/**
	 * Metodos para visitas en la base de datos
	 * 
	 * @param visitaTerreno
	 *            Objeto VisitaTereno que contiene la informacion
	 *
	 */
	//Crear nuevo VisitaTerreno en la base de datos
	public void crearVisitaTereno(ni.org.ics.estudios.appmovil.domain.VisitaTerreno visitaTerreno) {
		ContentValues cv = VisitaTerrenoHelper.crearVisitaTerrenoContentValues(visitaTerreno);
		mDb.insertOrThrow(MainDBConstants.VISITA_TABLE, null, cv);
	}
	//Editar VisitaTerreno existente en la base de datos
	public boolean editarVisitaTerreno(ni.org.ics.estudios.appmovil.domain.VisitaTerreno visitaTerreno) {
		ContentValues cv = VisitaTerrenoHelper.crearVisitaTerrenoContentValues(visitaTerreno);
		return mDb.update(MainDBConstants.VISITA_TABLE , cv, MainDBConstants.codigoVisita + "='" 
				+ visitaTerreno.getCodigoVisita()+ "'", null) > 0;
	}
	//Limpiar la tabla de VisitaTerreno de la base de datos
	public boolean borrarVisitasTerreno() {
		return mDb.delete(MainDBConstants.VISITA_TABLE, null, null) > 0;
	}
	//Obtener un VisitaTerreno de la base de datos
	public ni.org.ics.estudios.appmovil.domain.VisitaTerreno getVisitaTerreno(String filtro, String orden) throws SQLException {
        ni.org.ics.estudios.appmovil.domain.VisitaTerreno mVisitaTerreno = null;
		Cursor cursorVisitaTerreno = crearCursor(MainDBConstants.VISITA_TABLE , filtro, null, orden);
		if (cursorVisitaTerreno != null && cursorVisitaTerreno.getCount() > 0) {
			cursorVisitaTerreno.moveToFirst();
			mVisitaTerreno=VisitaTerrenoHelper.crearVisitaTerreno(cursorVisitaTerreno);
			Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorVisitaTerreno.getInt(cursorVisitaTerreno.getColumnIndex(MainDBConstants.casa)), null);
			mVisitaTerreno.setCasa(casa);
		}
		if (!cursorVisitaTerreno.isClosed()) cursorVisitaTerreno.close();
		return mVisitaTerreno;
	}
	//Obtener una lista de VisitaTerreno de la base de datos
	public List<ni.org.ics.estudios.appmovil.domain.VisitaTerreno> getVisitasTerreno(String filtro, String orden) throws SQLException {
		List<ni.org.ics.estudios.appmovil.domain.VisitaTerreno> mVisitasTerreno = new ArrayList<ni.org.ics.estudios.appmovil.domain.VisitaTerreno>();
		Cursor cursorVisitasTerreno = crearCursor(MainDBConstants.VISITA_TABLE, filtro, null, orden);
		if (cursorVisitasTerreno != null && cursorVisitasTerreno.getCount() > 0) {
			cursorVisitasTerreno.moveToFirst();
			mVisitasTerreno.clear();
			do{
                ni.org.ics.estudios.appmovil.domain.VisitaTerreno mVisitaTerreno = null;
				mVisitaTerreno = VisitaTerrenoHelper.crearVisitaTerreno(cursorVisitasTerreno);
				Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorVisitasTerreno.getInt(cursorVisitasTerreno.getColumnIndex(MainDBConstants.casa)), null);
				mVisitaTerreno.setCasa(casa);
				mVisitasTerreno.add(mVisitaTerreno);
			} while (cursorVisitasTerreno.moveToNext());
		}
		if (!cursorVisitasTerreno.isClosed()) cursorVisitasTerreno.close();
		return mVisitasTerreno;
	}	
	
	/**
	 * Metodos para pretamizajes en la base de datos
	 * 
	 * @param preTamizaje
	 *            Objeto PreTamizaje que contiene la informacion
	 *
	 */
	//Crear nuevo PreTamizaje en la base de datos
	public void crearPreTamizaje(PreTamizaje preTamizaje) {
		ContentValues cv = PreTamizajeHelper.crearPreTamizajeContentValues(preTamizaje);
		mDb.insertOrThrow(MainDBConstants.PRETAMIZAJE_TABLE, null, cv);
	}
	//Editar PreTamizaje existente en la base de datos
	public boolean editarPreTamizaje(PreTamizaje preTamizaje) {
		ContentValues cv = PreTamizajeHelper.crearPreTamizajeContentValues(preTamizaje);
		return mDb.update(MainDBConstants.PRETAMIZAJE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ preTamizaje.getCodigo()+ "'", null) > 0;
	}
	//Limpiar la tabla de PreTamizaje de la base de datos
	public boolean borrarPreTamizajes() {
		return mDb.delete(MainDBConstants.PRETAMIZAJE_TABLE, null, null) > 0;
	}
	//Obtener un PreTamizaje de la base de datos
	public PreTamizaje getPreTamizaje(String filtro, String orden) throws SQLException {
		PreTamizaje mPreTamizaje = null;
		Cursor cursorPreTamizaje = crearCursor(MainDBConstants.PRETAMIZAJE_TABLE , filtro, null, orden);
		if (cursorPreTamizaje != null && cursorPreTamizaje.getCount() > 0) {
			cursorPreTamizaje.moveToFirst();
			mPreTamizaje=PreTamizajeHelper.crearPreTamizaje(cursorPreTamizaje);
			Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorPreTamizaje.getInt(cursorPreTamizaje.getColumnIndex(MainDBConstants.casa)), null);
			mPreTamizaje.setCasa(casa);
			Estudio estudio = this.getEstudio(MainDBConstants.codigo + "=" +cursorPreTamizaje.getInt(cursorPreTamizaje.getColumnIndex(MainDBConstants.estudio)), null);
			mPreTamizaje.setEstudio(estudio);
		}
		if (!cursorPreTamizaje.isClosed()) cursorPreTamizaje.close();
		return mPreTamizaje;
	}
	//Obtener una lista de PreTamizaje de la base de datos
	public List<PreTamizaje> getPreTamizajes(String filtro, String orden) throws SQLException {
		List<PreTamizaje> mPreTamizajes = new ArrayList<PreTamizaje>();
		Cursor cursorPreTamizajes = crearCursor(MainDBConstants.PRETAMIZAJE_TABLE, filtro, null, orden);
		if (cursorPreTamizajes != null && cursorPreTamizajes.getCount() > 0) {
			cursorPreTamizajes.moveToFirst();
			mPreTamizajes.clear();
			do{
				PreTamizaje mPreTamizaje = null;
				mPreTamizaje = PreTamizajeHelper.crearPreTamizaje(cursorPreTamizajes);
				Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorPreTamizajes.getInt(cursorPreTamizajes.getColumnIndex(MainDBConstants.casa)), null);
				mPreTamizaje.setCasa(casa);
				Estudio estudio = this.getEstudio(MainDBConstants.codigo + "=" +cursorPreTamizajes.getInt(cursorPreTamizajes.getColumnIndex(MainDBConstants.estudio)), null);
				mPreTamizaje.setEstudio(estudio);
				mPreTamizajes.add(mPreTamizaje);
			} while (cursorPreTamizajes.moveToNext());
		}
		if (!cursorPreTamizajes.isClosed()) cursorPreTamizajes.close();
		return mPreTamizajes;
	}

	/**
	 * Metodos para CasaCohorteFamilia en la base de datos
	 * 
	 * @param casaCHF
	 *            Objeto CasaCohorteFamilia que contiene la informacion
	 *
	 */
	//Crear nuevo CasaCohorteFamilia en la base de datos
	public void crearCasaCohorteFamilia(CasaCohorteFamilia casaCHF) {
		ContentValues cv = CasaCohorteFamiliaHelper.crearCasaCHFontentValues(casaCHF);
		mDb.insertOrThrow(MainDBConstants.CASA_CHF_TABLE, null, cv);
	}
	//Crear nuevo CasaCohorteFamilia en la base de datos desde otro equipo
	public void insertarCasaCohorteFamilia(String casaSQL) {
		mDb.execSQL(casaSQL);
	}
	//Editar CasaCohorteFamilia existente en la base de datos
	public boolean editarCasaCohorteFamilia(CasaCohorteFamilia casaCHF) {
		ContentValues cv = CasaCohorteFamiliaHelper.crearCasaCHFontentValues(casaCHF);
		return mDb.update(MainDBConstants.CASA_CHF_TABLE , cv, MainDBConstants.codigoCHF + "='"
				+ casaCHF.getCodigoCHF()+ "'", null) > 0;
	}
	//Limpiar la tabla de CasaCohorteFamilia de la base de datos
	public boolean borrarCasaCohorteFamilias() {
		return mDb.delete(MainDBConstants.CASA_CHF_TABLE, null, null) > 0;
	}
	//Obtener una CasaCohorteFamilia de la base de datos
	public CasaCohorteFamilia getCasaCohorteFamilia(String filtro, String orden) throws SQLException {
		CasaCohorteFamilia mCasaCohorteFamilia = null;
		Cursor cursorCasaCohorteFamilia = crearCursor(MainDBConstants.CASA_CHF_TABLE , filtro, null, orden);
		if (cursorCasaCohorteFamilia != null && cursorCasaCohorteFamilia.getCount() > 0) {
			cursorCasaCohorteFamilia.moveToFirst();
			mCasaCohorteFamilia=CasaCohorteFamiliaHelper.crearCasaCHF(cursorCasaCohorteFamilia);
			Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorCasaCohorteFamilia.getInt(cursorCasaCohorteFamilia.getColumnIndex(MainDBConstants.casa)), null);
			mCasaCohorteFamilia.setCasa(casa);
		}
		if (!cursorCasaCohorteFamilia.isClosed()) cursorCasaCohorteFamilia.close();
		return mCasaCohorteFamilia;
	}
	//Obtener una lista de CasaCohorteFamilia de la base de datos
	public List<CasaCohorteFamilia> getCasaCohorteFamilias(String filtro, String orden) throws SQLException {
		List<CasaCohorteFamilia> mCasaCohorteFamilias = new ArrayList<CasaCohorteFamilia>();
		Cursor cursorCasaCohorteFamilia = crearCursor(MainDBConstants.CASA_CHF_TABLE, filtro, null, orden);
		if (cursorCasaCohorteFamilia != null && cursorCasaCohorteFamilia.getCount() > 0) {
			cursorCasaCohorteFamilia.moveToFirst();
			mCasaCohorteFamilias.clear();
			do{
				CasaCohorteFamilia mCasaCohorteFamilia = null;
				mCasaCohorteFamilia = CasaCohorteFamiliaHelper.crearCasaCHF(cursorCasaCohorteFamilia);
				Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorCasaCohorteFamilia.getInt(cursorCasaCohorteFamilia.getColumnIndex(MainDBConstants.casa)), null);
				mCasaCohorteFamilia.setCasa(casa);
				mCasaCohorteFamilias.add(mCasaCohorteFamilia);
			} while (cursorCasaCohorteFamilia.moveToNext());
		}
		if (!cursorCasaCohorteFamilia.isClosed()) cursorCasaCohorteFamilia.close();
		return mCasaCohorteFamilias;
	}

    /**
     * C
     * @param codCasa
     * @return
     */
    public Integer countCasasChfByCasa(Integer codCasa) {
        int total = 0;
        Cursor c = crearCursor(MainDBConstants.CASA_CHF_TABLE,MainDBConstants.casa + "=" + codCasa,null,null);
        if (c != null) {
            total = c.getCount();
        }
        if (c!=null && !c.isClosed()) c.close();
        return total;
    }
	
	/**
	 * Metodos para participantes en la base de datos
	 * 
	 * @param participante
	 *            Objeto Participante que contiene la informacion
	 *
	 */
	//Crear nuevo Participante en la base de datos
	public void crearParticipante(Participante participante) {
		ContentValues cv = ParticipanteHelper.crearParticipanteContentValues(participante);
		mDb.insertOrThrow(MainDBConstants.PARTICIPANTE_TABLE, null, cv);
	}
	//Crear nuevo Participante en la base de datos desde otro equipo
	public void insertarParticipante(String participanteSQL) {
		mDb.execSQL(participanteSQL);
	}
	//Editar Participante existente en la base de datos
	public boolean editarParticipante(Participante participante) {
		ContentValues cv = ParticipanteHelper.crearParticipanteContentValues(participante);
		return mDb.update(MainDBConstants.PARTICIPANTE_TABLE , cv, MainDBConstants.codigo + "=" 
				+ participante.getCodigo(), null) > 0;
	}
	//Limpiar la tabla de Participante de la base de datos
	public boolean borrarParticipantes() {
		return mDb.delete(MainDBConstants.PARTICIPANTE_TABLE, null, null) > 0;
	}
	//Obtener una Participante de la base de datos
	public Participante getParticipante(String filtro, String orden) throws SQLException {
		Participante mParticipante = null;
		Cursor cursorParticipante = crearCursor(MainDBConstants.PARTICIPANTE_TABLE , filtro, null, orden);
		if (cursorParticipante != null && cursorParticipante.getCount() > 0) {
			cursorParticipante.moveToFirst();
			mParticipante=ParticipanteHelper.crearParticipante(cursorParticipante);
			Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorParticipante.getInt(cursorParticipante.getColumnIndex(MainDBConstants.casa)), null);

            ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
            mParticipante.setProcesos(procesos);

			mParticipante.setCasa(casa);
		}
		if (!cursorParticipante.isClosed()) cursorParticipante.close();
		return mParticipante;
	}
	//Obtener una lista de Participante de la base de datos
	public List<Participante> getParticipantes(String filtro, String orden) throws SQLException {
		List<Participante> mParticipantes = new ArrayList<Participante>();
		Cursor cursorParticipante = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, filtro, null, orden);
		if (cursorParticipante != null && cursorParticipante.getCount() > 0) {
			cursorParticipante.moveToFirst();
			mParticipantes.clear();
			do{
				Participante mParticipante = null;
				mParticipante = ParticipanteHelper.crearParticipante(cursorParticipante);
				Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorParticipante.getInt(cursorParticipante.getColumnIndex(MainDBConstants.casa)), null);
				mParticipante.setCasa(casa);

                ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
                mParticipante.setProcesos(procesos);

				mParticipantes.add(mParticipante);
			} while (cursorParticipante.moveToNext());
		}
		if (!cursorParticipante.isClosed()) cursorParticipante.close();
		return mParticipantes;
	}

	//Obtener una lista de Participante de la base de datos
	public List<Participante> getParticipantesActivos(String filtro, String orden) throws SQLException {
		List<Participante> mParticipantes = new ArrayList<Participante>();
		Cursor cursorParticipante = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, filtro, null, orden);
		if (cursorParticipante != null && cursorParticipante.getCount() > 0) {
			cursorParticipante.moveToFirst();
			mParticipantes.clear();
			do{
				Participante mParticipante = null;
				mParticipante = ParticipanteHelper.crearParticipante(cursorParticipante);
				Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorParticipante.getInt(cursorParticipante.getColumnIndex(MainDBConstants.casa)), null);
				mParticipante.setCasa(casa);

				ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
				if (procesos != null && procesos.getEstPart()==1) {
					mParticipante.setProcesos(procesos);
					mParticipantes.add(mParticipante);
				}
			} while (cursorParticipante.moveToNext());
		}
		if (!cursorParticipante.isClosed()) cursorParticipante.close();
		return mParticipantes;
	}

    /**
     * Obtiene Lista todas las participantes buscando por nombre
     *
     * @return lista con participantes
     */
    public List<Participante> getListaParticipantesName(String name) throws SQLException {
        Cursor participantes = null;
        List<Participante> mParticipantes = new ArrayList<Participante>();
        participantes = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, MainDBConstants.nombre1 + " LIKE '%" + name + "%' OR "+ ConstantsDB.nombre2 + " LIKE '%" + name + "%'", null, null);
        //participantes = mDb.query(true, ConstantsDB.PART_TABLE, null,
                //MainDBConstants.nombre1 + " LIKE '%" + name + "%' OR "+ ConstantsDB.nombre2 + " LIKE '%" + name + "%'", null, null, null, null, null);

        if (participantes != null && participantes.getCount() > 0) {
            participantes.moveToFirst();
            mParticipantes.clear();
            do{
                Participante mParticipante = null;
                mParticipante = ParticipanteHelper.crearParticipante(participantes);

                Casa casa = this.getCasa(MainDBConstants.codigo + "=" +participantes.getInt(participantes.getColumnIndex(MainDBConstants.casa)), null);
                mParticipante.setCasa(casa);

                ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
                mParticipante.setProcesos(procesos);

                mParticipantes.add(mParticipante);
            } while (participantes.moveToNext());
        }
        if (!participantes.isClosed()) participantes.close();
        return mParticipantes;
    }

    /**
     * Obtiene Lista todas las participantes buscando por nombre
     *
     * @return lista con participantes
     */
    public List<Participante> getListaParticipantesLastName(String lastname) throws SQLException {
        Cursor participantes = null;
        List<Participante> mParticipantes = new ArrayList<Participante>();
        participantes = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, MainDBConstants.apellido1 + " LIKE '%" + lastname + "%' OR "+ MainDBConstants.apellido2 + " LIKE '%" + lastname + "%'", null, null);
        //participantes = mDb.query(true, ConstantsDB.PART_TABLE, null,
        //MainDBConstants.nombre1 + " LIKE '%" + name + "%' OR "+ ConstantsDB.nombre2 + " LIKE '%" + name + "%'", null, null, null, null, null);

        if (participantes != null && participantes.getCount() > 0) {
            participantes.moveToFirst();
            mParticipantes.clear();
            do{
                Participante mParticipante = null;
                mParticipante = ParticipanteHelper.crearParticipante(participantes);

                Casa casa = this.getCasa(MainDBConstants.codigo + "=" +participantes.getInt(participantes.getColumnIndex(MainDBConstants.casa)), null);
                mParticipante.setCasa(casa);

                ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
                mParticipante.setProcesos(procesos);

                mParticipantes.add(mParticipante);
            } while (participantes.moveToNext());
        }
        if (!participantes.isClosed()) participantes.close();
        return mParticipantes;
    }

    /**
     * Metodos para ContactoParticipante en la base de datos
     */
     //Crear nuevo Participante en la base de datos
    public void crearContactoParticipante(ContactoParticipante contactoParticipante) {
        ContentValues cv = ParticipanteHelper.crearContactoParticipanteContentValues(contactoParticipante);
        mDb.insertOrThrow(MainDBConstants.CONTACTO_PARTICIPANTE_TABLE, null, cv);
    }

    //Editar Participante existente en la base de datos
    public boolean editarContactoParticipante(ContactoParticipante contactoParticipante) {
        ContentValues cv = ParticipanteHelper.crearContactoParticipanteContentValues(contactoParticipante);
        return mDb.update(MainDBConstants.CONTACTO_PARTICIPANTE_TABLE , cv, MainDBConstants.id + "='"
                + contactoParticipante.getId() + "'", null) > 0;
    }
    //Limpiar la tabla de Participante de la base de datos
    public boolean borrarContactosParticipantes() {
        return mDb.delete(MainDBConstants.CONTACTO_PARTICIPANTE_TABLE, null, null) > 0;
    }

    //Obtener una lista de Participante de la base de datos
    public List<ContactoParticipante> getContactosParticipantes(String filtro, String orden) throws SQLException {
        List<ContactoParticipante> mParticipantes = new ArrayList<ContactoParticipante>();
        Cursor cursorParticipante = crearCursor(MainDBConstants.CONTACTO_PARTICIPANTE_TABLE, filtro, null, orden);
        if (cursorParticipante != null && cursorParticipante.getCount() > 0) {
            cursorParticipante.moveToFirst();
            mParticipantes.clear();
            do{
                ContactoParticipante mContacto = null;
                mContacto = ParticipanteHelper.crearContactoParticipante(cursorParticipante);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorParticipante.getInt(cursorParticipante.getColumnIndex(MainDBConstants.participante)), null);
                mContacto.setParticipante(participante);

                Barrio barrio = this.getBarrio(CatalogosDBConstants.codigo+"="+cursorParticipante.getInt(cursorParticipante.getColumnIndex(MainDBConstants.barrio)), null);
                mContacto.setBarrio(barrio);

                mParticipantes.add(mContacto);
            } while (cursorParticipante.moveToNext());
        }
        if (!cursorParticipante.isClosed()) cursorParticipante.close();
        return mParticipantes;
    }

    /**
     * Inserta un participantes_procesos en la base de datos
     *
     * @param participante
     *            Objeto Participantes_procesos que contiene la informacion
     *
     */
    public void crearParticipanteProcesos(ParticipanteProcesos participante) {
        ContentValues cv = new ContentValues();
        cv = ParticipanteHelper.crearParticipanteProcesos(participante);
        mDb.insertOrThrow(ConstantsDB.PART_PROCESOS_TABLE, null, cv);
    }

    //Crear nuevo ParticipanteProceso en la base de datos desde otro equipo
    public void insertarParticipanteProcesos(String participanteProcesosSQL) {
        mDb.execSQL(participanteProcesosSQL);
    }

    /**
     * Actualiza un participante en la base de datos.
     *
     * @param participante
     *            Objeto que contiene la info
     * @return verdadero o falso
     */
    public boolean actualizarParticipanteProcesos(ParticipanteProcesos participante) {
        ContentValues cv = ParticipanteHelper.crearParticipanteProcesos(participante);
        return mDb.update(ConstantsDB.PART_PROCESOS_TABLE, cv, ConstantsDB.CODIGO + "="
                + participante.getCodigo(), null) > 0;
    }

    //Obtener un participante procesos de la base de datos
    public ParticipanteProcesos getParticipanteProcesos(String filtro, String orden) throws SQLException {
        ParticipanteProcesos mPartProc = null;
        Cursor cursor = crearCursor(ConstantsDB.PART_PROCESOS_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mPartProc=ParticipanteHelper.crearParticipanteProcesos(cursor);
        }
        if (!cursor.isClosed()) cursor.close();
        return mPartProc;
    }

    //Obtener una lista de Participante de la base de datos
    public List<ParticipanteProcesos> getParticipantesProc(String filtro, String orden) throws SQLException {
        List<ParticipanteProcesos> mParticipantesProc = new ArrayList<ParticipanteProcesos>();
        Cursor cursorParticipante = crearCursor(ConstantsDB.PART_PROCESOS_TABLE, filtro, null, orden);
        if (cursorParticipante != null && cursorParticipante.getCount() > 0) {
            cursorParticipante.moveToFirst();
            mParticipantesProc.clear();
            do{
                ParticipanteProcesos mParticipante = null;
                mParticipante = ParticipanteHelper.crearParticipanteProcesos(cursorParticipante);
                mParticipantesProc.add(mParticipante);
            } while (cursorParticipante.moveToNext());
        }
        if (cursorParticipante!=null && !cursorParticipante.isClosed()) cursorParticipante.close();
        return mParticipantesProc;
    }

    public boolean existenParticipantesEnEstudio(Integer casa, String estudio){
		List<ParticipanteProcesos> mParticipantesProc = new ArrayList<ParticipanteProcesos>();
		//participantes activos que pertenecen a la casa y tienen el estudio consentido
		String filtro = ConstantsDB.CODIGO+" in ( select "+MainDBConstants.codigo+" from "+MainDBConstants.PARTICIPANTE_TABLE+" where "+MainDBConstants.casa+" = "+casa+") " +
				"and "+ConstantsDB.ESTUDIO+" like '%"+estudio+"%' " +
				"and "+ConstantsDB.ESTADO_PAR+" = 1";
		Cursor cursorParticipante = crearCursor(ConstantsDB.PART_PROCESOS_TABLE, filtro, null, null);
		return (cursorParticipante != null && cursorParticipante.getCount() > 0);
	}

	/**
	 * Metodos para ParticipanteCohorteFamilia en la base de datos
	 * 
	 * @param participanteCohorteFamilia
	 *            Objeto ParticipanteCohorteFamilia que contiene la informacion
	 *
	 */
	//Crear nuevo ParticipanteCohorteFamilia en la base de datos
	public void crearParticipanteCohorteFamilia(ParticipanteCohorteFamilia participanteCohorteFamilia) {
		ContentValues cv = ParticipanteCohorteFamiliaHelper.crearParticipanteCohorteFamiliaContentValues(participanteCohorteFamilia);
		mDb.insertOrThrow(MainDBConstants.PARTICIPANTE_CHF_TABLE, null, cv);
	}
	//Crear nuevo ParticipanteCohorteFamilia en la base de datos desde otro equipo
	public void insertarParticipanteCohorteFamilia(String participanteCohorteFamiliaSQL) {
		mDb.execSQL(participanteCohorteFamiliaSQL);
	}
	//Editar ParticipanteCohorteFamilia existente en la base de datos
	public boolean editarParticipanteCohorteFamilia(ParticipanteCohorteFamilia participanteCohorteFamilia) {
		ContentValues cv = ParticipanteCohorteFamiliaHelper.crearParticipanteCohorteFamiliaContentValues(participanteCohorteFamilia);
		return mDb.update(MainDBConstants.PARTICIPANTE_CHF_TABLE , cv, MainDBConstants.participante + "="
				+ participanteCohorteFamilia.getParticipante().getCodigo(), null) > 0;
	}
	//Limpiar la tabla de ParticipanteCohorteFamilia de la base de datos
	public boolean borrarParticipanteCohorteFamilias() {
		return mDb.delete(MainDBConstants.PARTICIPANTE_CHF_TABLE, null, null) > 0;
	}
	//Obtener una ParticipanteCohorteFamilia de la base de datos
	public ParticipanteCohorteFamilia getParticipanteCohorteFamilia(String filtro, String orden) throws SQLException {
		ParticipanteCohorteFamilia mParticipanteCohorteFamilia = null;
		Cursor cursorParticipanteCohorteFamilia = crearCursor(MainDBConstants.PARTICIPANTE_CHF_TABLE , filtro, null, orden);
		if (cursorParticipanteCohorteFamilia != null && cursorParticipanteCohorteFamilia.getCount() > 0) {
			cursorParticipanteCohorteFamilia.moveToFirst();
			mParticipanteCohorteFamilia=ParticipanteCohorteFamiliaHelper.crearParticipanteCohorteFamilia(cursorParticipanteCohorteFamilia);
			CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" +cursorParticipanteCohorteFamilia.getInt(cursorParticipanteCohorteFamilia.getColumnIndex(MainDBConstants.casaCHF)), null);
			mParticipanteCohorteFamilia.setCasaCHF(cchf);
			Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorParticipanteCohorteFamilia.getInt(cursorParticipanteCohorteFamilia.getColumnIndex(MainDBConstants.participante)), null);
			mParticipanteCohorteFamilia.setParticipante(participante);
		}
		if (!cursorParticipanteCohorteFamilia.isClosed()) cursorParticipanteCohorteFamilia.close();
		return mParticipanteCohorteFamilia;
	}
    //Obtener una lista de ParticipanteCohorteFamilia de la base de datos
    public ArrayList<ParticipanteCohorteFamilia> getParticipanteCohorteFamilias(String filtro, String orden) throws SQLException {
        ArrayList<ParticipanteCohorteFamilia> mParticipanteCohorteFamilias = new ArrayList<ParticipanteCohorteFamilia>();
        Cursor cursorParticipanteCohorteFamilia = crearCursor(MainDBConstants.PARTICIPANTE_CHF_TABLE, filtro, null, orden);
        if (cursorParticipanteCohorteFamilia != null && cursorParticipanteCohorteFamilia.getCount() > 0) {
            cursorParticipanteCohorteFamilia.moveToFirst();
            mParticipanteCohorteFamilias.clear();
            do{
                ParticipanteCohorteFamilia mParticipanteCohorteFamilia = null;
                mParticipanteCohorteFamilia = ParticipanteCohorteFamiliaHelper.crearParticipanteCohorteFamilia(cursorParticipanteCohorteFamilia);
                CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" +cursorParticipanteCohorteFamilia.getInt(cursorParticipanteCohorteFamilia.getColumnIndex(MainDBConstants.casaCHF)), null);
                mParticipanteCohorteFamilia.setCasaCHF(cchf);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorParticipanteCohorteFamilia.getInt(cursorParticipanteCohorteFamilia.getColumnIndex(MainDBConstants.participante)), null);
                //mostrar solo registros con contraparte en la tabla de participantes y que esten activos
                if (participante!=null && participante.getProcesos()!=null && participante.getProcesos().getEstPart().equals(1)) {
                    mParticipanteCohorteFamilia.setParticipante(participante);
                    mParticipanteCohorteFamilias.add(mParticipanteCohorteFamilia);
                }
            } while (cursorParticipanteCohorteFamilia.moveToNext());
        }
        if (!cursorParticipanteCohorteFamilia.isClosed()) cursorParticipanteCohorteFamilia.close();
        return mParticipanteCohorteFamilias;
    }
    
	/**
	 * Metodos para tamizajes en la base de datos
	 * 
	 * @param tamizaje
	 *            Objeto Tamizaje que contiene la informacion
	 *
	 */
	//Crear nuevo Tamizaje en la base de datos
	public void crearTamizaje(Tamizaje tamizaje) {
		ContentValues cv = TamizajeHelper.crearTamizajeContentValues(tamizaje);
		mDb.insertOrThrow(MainDBConstants.TAMIZAJE_TABLE, null, cv);
	}
	//Editar Tamizaje existente en la base de datos
	public boolean editarTamizaje(Tamizaje tamizaje) {
		ContentValues cv = TamizajeHelper.crearTamizajeContentValues(tamizaje);
		return mDb.update(MainDBConstants.TAMIZAJE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ tamizaje.getCodigo()+ "'", null) > 0;
	}
	//Limpiar la tabla de Tamizaje de la base de datos
	public boolean borrarTamizajes() {
		return mDb.delete(MainDBConstants.TAMIZAJE_TABLE, null, null) > 0;
	}
	//Obtener un Tamizaje de la base de datos
	public Tamizaje getTamizaje(String filtro, String orden) throws SQLException {
		Tamizaje mTamizaje = null;
		Cursor cursorTamizaje = crearCursor(MainDBConstants.TAMIZAJE_TABLE , filtro, null, orden);
		if (cursorTamizaje != null && cursorTamizaje.getCount() > 0) {
			cursorTamizaje.moveToFirst();
			mTamizaje=TamizajeHelper.crearTamizaje(cursorTamizaje);
			Estudio estudio = this.getEstudio(MainDBConstants.codigo + "=" +cursorTamizaje.getInt(cursorTamizaje.getColumnIndex(MainDBConstants.estudio)), null);
			mTamizaje.setEstudio(estudio);
		}
		if (!cursorTamizaje.isClosed()) cursorTamizaje.close();
		return mTamizaje;
	}
	//Obtener una lista de Tamizaje de la base de datos
	public List<Tamizaje> getTamizajes(String filtro, String orden) throws SQLException {
		List<Tamizaje> mTamizajes = new ArrayList<Tamizaje>();
		Cursor cursorTamizajes = crearCursor(MainDBConstants.TAMIZAJE_TABLE, filtro, null, orden);
		if (cursorTamizajes != null && cursorTamizajes.getCount() > 0) {
			cursorTamizajes.moveToFirst();
			mTamizajes.clear();
			do{
				Tamizaje mTamizaje = null;
				mTamizaje = TamizajeHelper.crearTamizaje(cursorTamizajes);
				Estudio estudio = this.getEstudio(MainDBConstants.codigo + "=" +cursorTamizajes.getInt(cursorTamizajes.getColumnIndex(MainDBConstants.estudio)), null);
				mTamizaje.setEstudio(estudio);
				mTamizajes.add(mTamizaje);
			} while (cursorTamizajes.moveToNext());
		}
		if (!cursorTamizajes.isClosed()) cursorTamizajes.close();
		return mTamizajes;
	}
	
	/**
	 * Metodos para carta de consentimiento en la base de datos
	 * 
	 * @param cartaConsentimiento
	 *            Objeto CartaConsentimiento que contiene la informacion
	 *
	 */
	//Crear nuevo CartaConsentimiento en la base de datos
	public void crearCartaConsentimiento(CartaConsentimiento cartaConsentimiento) {
		ContentValues cv = CartaConsentimientoHelper.crearCartaConsentimientoContentValues(cartaConsentimiento);
		mDb.insertOrThrow(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, null, cv);
	}
    //Editar EncuestasCasa existente en la base de datos
    public boolean editarCartaConsentimiento(CartaConsentimiento cartaConsentimiento) {
        ContentValues cv = CartaConsentimientoHelper.crearCartaConsentimientoContentValues(cartaConsentimiento);
        return mDb.update(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, cv, MainDBConstants.codigo + "='"
                + cartaConsentimiento.getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de Tamizaje de la base de datos
    public boolean borrarCartasConsentimiento() {
        return mDb.delete(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, null, null) > 0;
    }

    //Obtener una lista de ParticipanteCohorteFamilia de la base de datos
    public CartaConsentimiento getCartaConsentimientos(String filtro, String orden) throws SQLException {
        CartaConsentimiento cartaConsentimiento = null;
        Cursor cursorCarta = crearCursor(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE , filtro, null, orden);
        if (cursorCarta != null && cursorCarta.getCount() > 0) {
            cursorCarta.moveToFirst();
            cartaConsentimiento=CartaConsentimientoHelper.crearCartaConsentimiento(cursorCarta);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorCarta.getInt(cursorCarta.getColumnIndex(MainDBConstants.participante)), null);
            cartaConsentimiento.setParticipante(participante);
            Tamizaje tamizaje = this.getTamizaje(MainDBConstants.codigo + "='" +cursorCarta.getString(cursorCarta.getColumnIndex(MainDBConstants.tamizaje))+"'", null);
            cartaConsentimiento.setTamizaje(tamizaje);
        }
        if (!cursorCarta.isClosed()) cursorCarta.close();
        return cartaConsentimiento;
    }
    //Obtener una lista de ParticipanteCohorteFamilia de la base de datos
    public ArrayList<CartaConsentimiento> getCartasConsentimientos(String filtro, String orden) throws SQLException {
        ArrayList<CartaConsentimiento> mParticipanteCohorteFamilias = new ArrayList<CartaConsentimiento>();
        Cursor cursorCarta = crearCursor(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, filtro, null, orden);
        if (cursorCarta != null && cursorCarta.getCount() > 0) {
            cursorCarta.moveToFirst();
            mParticipanteCohorteFamilias.clear();
            do{
                CartaConsentimiento cartaConsentimiento = null;
                cartaConsentimiento = CartaConsentimientoHelper.crearCartaConsentimiento(cursorCarta);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorCarta.getInt(cursorCarta.getColumnIndex(MainDBConstants.participante)), null);
                cartaConsentimiento.setParticipante(participante);
                Tamizaje tamizaje = this.getTamizaje(MainDBConstants.codigo + "='" +cursorCarta.getString(cursorCarta.getColumnIndex(MainDBConstants.tamizaje))+"'", null);
                cartaConsentimiento.setTamizaje(tamizaje);
                mParticipanteCohorteFamilias.add(cartaConsentimiento);
            } while (cursorCarta.moveToNext());
        }
        if (!cursorCarta.isClosed()) cursorCarta.close();
        return mParticipanteCohorteFamilias;
    }

    /**
     * Metodos para EncuestaCasa en la base de datos
     *
     * @param encuestaCasa
     *            Objeto EncuestasCasa que contiene la informacion
     *
     */
    //Crear nuevo EncuestasCasa en la base de datos
    public void crearEncuestaCasa(EncuestaCasa encuestaCasa) {
        ContentValues cv = EncuestaCasaHelper.crearEncuestaCasaContentValues(encuestaCasa);
        mDb.insertOrThrow(EncuestasDBConstants.ENCUESTA_CASA_TABLE, null, cv);
    }
    //Editar EncuestasCasa existente en la base de datos
    public boolean editarEncuestaCasa(EncuestaCasa encuestaCasa) {
        ContentValues cv = EncuestaCasaHelper.crearEncuestaCasaContentValues(encuestaCasa);
        return mDb.update(EncuestasDBConstants.ENCUESTA_CASA_TABLE, cv, EncuestasDBConstants.casa_chf + "='"
                + encuestaCasa.getCasa().getCodigoCHF() + "'", null) > 0;
    }
    //Limpiar la tabla de EncuestasCasa de la base de datos
    public boolean borrarEncuestaCasas() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_CASA_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaCasa de la base de datos
    public EncuestaCasa getEncuestaCasa(String filtro, String orden) throws SQLException {
        EncuestaCasa mEncuestaCasa = null;
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_CASA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestaCasa=EncuestaCasaHelper.crearEncuestaCasa(cursor);
            CasaCohorteFamilia casaCohorteFamilia = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.casa_chf)), null);
            if (casaCohorteFamilia != null) mEncuestaCasa.setCasa(casaCohorteFamilia);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestaCasa;
    }
    //Obtener una lista de EncuestasCasa de la base de datos
    public List<EncuestaCasa> getEncuestaCasas(String filtro, String orden) throws SQLException {
        List<EncuestaCasa> mEncuestas = new ArrayList<EncuestaCasa>();
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_CASA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestas.clear();
            do{
                EncuestaCasa mEncuesta = null;
                mEncuesta = EncuestaCasaHelper.crearEncuestaCasa(cursor);
                CasaCohorteFamilia casaCohorteFamilia = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.casa_chf)), null);
                if (casaCohorteFamilia != null) mEncuesta.setCasa(casaCohorteFamilia);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
    }

    /**
     * Metodos para EncuestaDatosPartoBB en la base de datos
     *
     * @param encuestaDatosPartoBB
     *            Objeto EncuestasDatosPartoBB que contiene la informacion
     *
     */
    //Crear nuevo EncuestasDatosPartoBB en la base de datos
    public void crearEncuestasDatosPartoBB(EncuestaDatosPartoBB encuestaDatosPartoBB) {
        ContentValues cv = EncuestaDatosPartoBBHelper.crearEncuestaDatosPartoBBContentValues(encuestaDatosPartoBB);
        mDb.insertOrThrow(EncuestasDBConstants.ENCUESTA_PARTOBB_TABLE, null, cv);
    }
    //Editar EncuestasDatosPartoBB existente en la base de datos
    public boolean editarEncuestasDatosPartoBB(EncuestaDatosPartoBB encuestaDatosPartoBB) {
        ContentValues cv = EncuestaDatosPartoBBHelper.crearEncuestaDatosPartoBBContentValues(encuestaDatosPartoBB);
        return mDb.update(EncuestasDBConstants.ENCUESTA_PARTOBB_TABLE, cv, EncuestasDBConstants.participante + "="
                + encuestaDatosPartoBB.getParticipante().getParticipante().getCodigo(), null) > 0;
    }
    //Limpiar la tabla de EncuestasDatosPartoBB de la base de datos
    public boolean borrarEncuestasDatosPartoBBs() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_PARTOBB_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaDatosPartoBB de la base de datos
    public EncuestaDatosPartoBB getEncuestasDatosPartoBB(String filtro, String orden) throws SQLException {
        EncuestaDatosPartoBB mEncuestasDatosPartoBB = null;
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PARTOBB_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestasDatosPartoBB=EncuestaDatosPartoBBHelper.crearEncuestaDatosPartoBB(cursor);
            ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
            if (participanteCohorteFamilia != null) mEncuestasDatosPartoBB.setParticipante(participanteCohorteFamilia);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestasDatosPartoBB;
    }
    //Obtener una lista de EncuestasDatosPartoBB de la base de datos
    public List<EncuestaDatosPartoBB> getEncuestasDatosPartoBBs(String filtro, String orden) throws SQLException {
        List<EncuestaDatosPartoBB> mEncuestas = new ArrayList<EncuestaDatosPartoBB>();
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PARTOBB_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestas.clear();
            do{
                EncuestaDatosPartoBB mEncuesta = null;
                mEncuesta = EncuestaDatosPartoBBHelper.crearEncuestaDatosPartoBB(cursor);
                ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
                if (participanteCohorteFamilia != null) mEncuesta.setParticipante(participanteCohorteFamilia);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
    }

    /**
     * Metodos para EncuestaParticipante en la base de datos
     *
     * @param encuestaParticipante
     *            Objeto EncuestasParticipante que contiene la informacion
     *
     */
    //Crear nuevo EncuestasParticipante en la base de datos
    public void crearEncuestasParticipante(EncuestaParticipante encuestaParticipante) {
        ContentValues cv = EncuestaParticipanteHelper.crearEncuestaParticipanteContentValues(encuestaParticipante);
        mDb.insertOrThrow(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, null, cv);
    }
    //Editar EncuestasParticipante existente en la base de datos
    public boolean editarEncuestasParticipante(EncuestaParticipante encuestaParticipante) {
        ContentValues cv = EncuestaParticipanteHelper.crearEncuestaParticipanteContentValues(encuestaParticipante);
        return mDb.update(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, cv, EncuestasDBConstants.participante + "="
                + encuestaParticipante.getParticipante().getParticipante().getCodigo(), null) > 0;
    }
    //Limpiar la tabla de EncuestasParticipante de la base de datos
    public boolean borrarEncuestasParticipantes() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaParticipante de la base de datos
    public EncuestaParticipante getEncuestasParticipante(String filtro, String orden) throws SQLException {
        EncuestaParticipante mEncuestasParticipante = null;
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestasParticipante=EncuestaParticipanteHelper.crearEncuestaParticipante(cursor);
            ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
            if (participanteCohorteFamilia != null) mEncuestasParticipante.setParticipante(participanteCohorteFamilia);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestasParticipante;
    }
    //Obtener una lista de EncuestasParticipante de la base de datos
    public List<EncuestaParticipante> getEncuestasParticipantes(String filtro, String orden) throws SQLException {
        List<EncuestaParticipante> mEncuestas = new ArrayList<EncuestaParticipante>();
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestas.clear();
            do{
                EncuestaParticipante mEncuesta = null;
                mEncuesta = EncuestaParticipanteHelper.crearEncuestaParticipante(cursor);
                ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
                if (participanteCohorteFamilia != null) mEncuesta.setParticipante(participanteCohorteFamilia);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
    }

    /**
     * Metodos para EncuestaPesoTalla en la base de datos
     *
     * @param encuestaPesoTalla
     *            Objeto EncuestasPesoTalla que contiene la informacion
     *
     */
    //Crear nuevo EncuestasPesoTalla en la base de datos
    public void crearEncuestasPesoTalla(EncuestaPesoTalla encuestaPesoTalla) {
        ContentValues cv = EncuestaPesoTallaHelper.crearEncuestaPesoTallaContentValues(encuestaPesoTalla);
        mDb.insertOrThrow(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, null, cv);
    }
    //Editar EncuestasPesoTalla existente en la base de datos
    public boolean editarEncuestasPesoTalla(EncuestaPesoTalla encuestaPesoTalla) {
        ContentValues cv = EncuestaPesoTallaHelper.crearEncuestaPesoTallaContentValues(encuestaPesoTalla);
        return mDb.update(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, cv, EncuestasDBConstants.participante + "="
                + encuestaPesoTalla.getParticipante().getParticipante().getCodigo(), null) > 0;
    }
    //Limpiar la tabla de EncuestasPesoTalla de la base de datos
    public boolean borrarEncuestasPesoTallas() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaPesoTalla de la base de datos
    public EncuestaPesoTalla getEncuestasPesoTalla(String filtro, String orden) throws SQLException {
        EncuestaPesoTalla mEncuestasPesoTalla = null;
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestasPesoTalla=EncuestaPesoTallaHelper.crearEncuestaPesoTalla(cursor);
            ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
            if (participanteCohorteFamilia != null) mEncuestasPesoTalla.setParticipante(participanteCohorteFamilia);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestasPesoTalla;
    }
    //Obtener una lista de EncuestasPesoTalla de la base de datos
    public List<EncuestaPesoTalla> getEncuestasPesoTallas(String filtro, String orden) throws SQLException {
        List<EncuestaPesoTalla> mEncuestas = new ArrayList<EncuestaPesoTalla>();
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestas.clear();
            do{
                EncuestaPesoTalla mEncuesta = null;
                mEncuesta = EncuestaPesoTallaHelper.crearEncuestaPesoTalla(cursor);
                ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
                if (participanteCohorteFamilia != null) mEncuesta.setParticipante(participanteCohorteFamilia);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
    }

    /**
     * Metodos para EncuestaLactanciaMaterna en la base de datos
     *
     * @param encuestaLactanciaMaterna
     *            Objeto EncuestasLactanciaMaterna que contiene la informacion
     *
     */
    //Crear nuevo EncuestasLactanciaMaterna en la base de datos
    public void crearEncuestasLactanciaMaterna(EncuestaLactanciaMaterna encuestaLactanciaMaterna) {
        ContentValues cv = EncuestaLactanciaMatHelper.crearEncuestaLactanciaMaternaContentValues(encuestaLactanciaMaterna);
        mDb.insertOrThrow(EncuestasDBConstants.ENCUESTA_LACTANCIAMAT_TABLE, null, cv);
    }
    //Editar EncuestasLactanciaMaterna existente en la base de datos
    public boolean editarEncuestasLactanciaMaterna(EncuestaLactanciaMaterna encuestaLactanciaMaterna) {
        ContentValues cv = EncuestaLactanciaMatHelper.crearEncuestaLactanciaMaternaContentValues(encuestaLactanciaMaterna);
        return mDb.update(EncuestasDBConstants.ENCUESTA_LACTANCIAMAT_TABLE, cv, EncuestasDBConstants.participante + "="
                + encuestaLactanciaMaterna.getParticipante().getParticipante().getCodigo(), null) > 0;
    }
    //Limpiar la tabla de EncuestasLactanciaMaterna de la base de datos
    public boolean borrarEncuestasLactanciaMaternas() {
        return mDb.delete(EncuestasDBConstants.ENCUESTA_LACTANCIAMAT_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaLactanciaMaterna de la base de datos
    public EncuestaLactanciaMaterna getEncuestasLactanciaMaterna(String filtro, String orden) throws SQLException {
        EncuestaLactanciaMaterna mEncuestasLactanciaMaterna = null;
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_LACTANCIAMAT_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestasLactanciaMaterna=EncuestaLactanciaMatHelper.crearEncuestaLactanciaMaterna(cursor);
            ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
            if (participanteCohorteFamilia != null) mEncuestasLactanciaMaterna.setParticipante(participanteCohorteFamilia);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestasLactanciaMaterna;
    }
    //Obtener una lista de EncuestasLactanciaMaterna de la base de datos
    public List<EncuestaLactanciaMaterna> getEncuestasLactanciaMaternas(String filtro, String orden) throws SQLException {
        List<EncuestaLactanciaMaterna> mEncuestas = new ArrayList<EncuestaLactanciaMaterna>();
        Cursor cursor = crearCursor(EncuestasDBConstants.ENCUESTA_LACTANCIAMAT_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestas.clear();
            do{
                EncuestaLactanciaMaterna mEncuesta = null;
                mEncuesta = EncuestaLactanciaMatHelper.crearEncuestaLactanciaMaterna(cursor);
                ParticipanteCohorteFamilia participanteCohorteFamilia = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(EncuestasDBConstants.participante)), null);
                if (participanteCohorteFamilia != null) mEncuesta.setParticipante(participanteCohorteFamilia);
                mEncuestas.add(mEncuesta);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestas;
    }

    /**
     * Metodos para Muestra en la base de datos
     *
     * @param muestra
     *            Objeto Muestras que contiene la informacion
     *
     */
    //Crear nuevo Muestras en la base de datos
    public void crearMuestras(Muestra muestra) {
        ContentValues cv = MuestraHelper.crearMuestraContentValues(muestra);
        mDb.insertOrThrow(MuestrasDBConstants.MUESTRA_TABLE, null, cv);
    }
    //Editar Muestras existente en la base de datos
    public boolean editarMuestras(Muestra muestra) {
        ContentValues cv = MuestraHelper.crearMuestraContentValues(muestra);
        return mDb.update(MuestrasDBConstants.MUESTRA_TABLE, cv, MuestrasDBConstants.codigo + "='"
                + muestra.getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de Muestras de la base de datos
    public boolean borrarMuestras() {
        return mDb.delete(MuestrasDBConstants.MUESTRA_TABLE, null, null) > 0;
    }
    //Limpiar la tabla de Muestras Transmision de la base de datos
	public boolean borrarMuestrasTx() {
		return mDb.delete(MuestrasDBConstants.MUESTRA_TABLE, MuestrasDBConstants.proposito + "='3'" , null) > 0;
	}
    //Obtener una Muestra de la base de datos
    public Muestra getMuestra(String filtro, String orden) throws SQLException {
        Muestra mMuestras = null;
        Cursor cursor = crearCursor(MuestrasDBConstants.MUESTRA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mMuestras=MuestraHelper.crearMuestra(cursor);
            Participante participante = this.getParticipante(MuestrasDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.participante)), null);
            if (participante != null) mMuestras.setParticipante(participante);
        }
        if (!cursor.isClosed()) cursor.close();
        return mMuestras;
    }
    //Obtener una lista de Muestras de la base de datos
    public List<Muestra> getMuestras(String filtro, String orden) throws SQLException {
        List<Muestra> mMuestras = new ArrayList<Muestra>();
        Cursor cursor = crearCursor(MuestrasDBConstants.MUESTRA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mMuestras.clear();
            do{
                Muestra mMuestra = null;
                mMuestra = MuestraHelper.crearMuestra(cursor);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.participante)), null);
                if (participante != null) mMuestra.setParticipante(participante);
                mMuestras.add(mMuestra);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mMuestras;
    }

	/**
	 * Metodos para Habitaciones en la base de datos
	 * 
	 * @param habitacion
	 *            Objeto Habitacion que contiene la informacion
	 *
	 */
	//Crear nuevo Habitacion en la base de datos
	public void crearHabitacion(Habitacion habitacion) {
		ContentValues cv = AreaAmbienteHelper.crearHabitacionContentValues(habitacion);
		mDb.insertOrThrow(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar Habitacion existente en la base de datos
	public boolean editarHabitacion(Habitacion habitacion) {
		ContentValues cv = AreaAmbienteHelper.crearHabitacionContentValues(habitacion);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ habitacion.getCodigo()+ "'", null) > 0;
	}
	//Limpiar la tabla de AREA_AMBIENTE_TABLE de la base de datos
	public boolean borrarAreasAmbiente() {
		return mDb.delete(MainDBConstants.AREA_AMBIENTE_TABLE, null, null) > 0;
	}
	//Obtener un Habitacion de la base de datos
	public Habitacion getHabitacion(String filtro, String orden) throws SQLException {
		Habitacion mHabitacion = null;
		Cursor cursorHabitacion = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorHabitacion != null && cursorHabitacion.getCount() > 0) {
			cursorHabitacion.moveToFirst();
			mHabitacion=AreaAmbienteHelper.crearHabitacion(cursorHabitacion);
			CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorHabitacion.getString(cursorHabitacion.getColumnIndex(MainDBConstants.casa))+"'", null);
			mHabitacion.setCasa(casa);
		}
		if (!cursorHabitacion.isClosed()) cursorHabitacion.close();
		return mHabitacion;
	}
	//Obtener una lista de Habitacion de la base de datos
	public List<Habitacion> getHabitaciones(String filtro, String orden) throws SQLException {
		List<Habitacion> mHabitaciones = new ArrayList<Habitacion>();
		Cursor cursorHabitaciones = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorHabitaciones != null && cursorHabitaciones.getCount() > 0) {
			cursorHabitaciones.moveToFirst();
			mHabitaciones.clear();
			do{
				Habitacion mHabitacion = null;
				mHabitacion = AreaAmbienteHelper.crearHabitacion(cursorHabitaciones);
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorHabitaciones.getString(cursorHabitaciones.getColumnIndex(MainDBConstants.casa))+"'", null);
				mHabitacion.setCasa(casa);
				mHabitaciones.add(mHabitacion);
			} while (cursorHabitaciones.moveToNext());
		}
		if (!cursorHabitaciones.isClosed()) cursorHabitaciones.close();
		return mHabitaciones;
	}   
	
	/**
	 * Metodos para Camas en la base de datos
	 * 
	 * @param cama
	 *            Objeto Cama que contiene la informacion
	 *
	 */
	//Crear nuevo Cama en la base de datos
	public void crearCama(Cama cama) {
		ContentValues cv = CamasHelper.crearCamaContentValues(cama);
		mDb.insertOrThrow(MainDBConstants.CAMA_TABLE, null, cv);
	}
	//Editar Cama existente en la base de datos
	public boolean editarCama(Cama cama) {
		ContentValues cv = CamasHelper.crearCamaContentValues(cama);
		return mDb.update(MainDBConstants.CAMA_TABLE , cv, MainDBConstants.codigoCama + "='" 
				+ cama.getCodigoCama()+ "'", null) > 0;
	}
	//Limpiar la tabla de Camas de la base de datos
	public boolean borrarCamas() {
		return mDb.delete(MainDBConstants.CAMA_TABLE, null, null) > 0;
	}
	//Obtener un Cama de la base de datos
	public Cama getCama(String filtro, String orden) throws SQLException {
		Cama mCama = null;
		Cursor cursorCama = crearCursor(MainDBConstants.CAMA_TABLE , filtro, null, orden);
		if (cursorCama != null && cursorCama.getCount() > 0) {
			cursorCama.moveToFirst();
			mCama=CamasHelper.crearCama(cursorCama);
			Cuarto cuarto = this.getCuarto(MainDBConstants.codigo + "='" + cursorCama.getString(cursorCama.getColumnIndex(MainDBConstants.habitacion)) + "'", null);
			mCama.setCuarto(cuarto);
		}
		if (!cursorCama.isClosed()) cursorCama.close();
		return mCama;
	}
	//Obtener una lista de Cama de la base de datos
	public List<Cama> getCamas(String filtro, String orden) throws SQLException {
		List<Cama> mCamas = new ArrayList<Cama>();
		Cursor cursorCamas = crearCursor(MainDBConstants.CAMA_TABLE, filtro, null, orden);
		if (cursorCamas != null && cursorCamas.getCount() > 0) {
			cursorCamas.moveToFirst();
			mCamas.clear();
			do{
				Cama mCama = null;
				mCama = CamasHelper.crearCama(cursorCamas);
				Cuarto cuarto = this.getCuarto(MainDBConstants.codigo + "='" + cursorCamas.getString(cursorCamas.getColumnIndex(MainDBConstants.habitacion)) + "'", null);
				mCama.setCuarto(cuarto);
				mCamas.add(mCama);
			} while (cursorCamas.moveToNext());
		}
		if (!cursorCamas.isClosed()) cursorCamas.close();
		return mCamas;
	}
	
	
	/**
	 * Metodos para PersonaCama en la base de datos
	 * 
	 * @param personacama
	 *            Objeto PersonaCama que contiene la informacion
	 *
	 */
	//Crear nuevo PersonaCama en la base de datos
	public void crearPersonaCama(PersonaCama personacama) {
		ContentValues cv = CamasHelper.crearPersonaCamaContentValues(personacama);
		mDb.insertOrThrow(MainDBConstants.PERSONACAMA_TABLE, null, cv);
	}
	//Editar Cama existente en la base de datos
	public boolean editarPersonaCama(PersonaCama personacama) {
		ContentValues cv = CamasHelper.crearPersonaCamaContentValues(personacama);
		return mDb.update(MainDBConstants.PERSONACAMA_TABLE , cv, MainDBConstants.codigoPersona + "='" 
				+ personacama.getCodigoPersona()+ "'", null) > 0;
	}
	//Limpiar la tabla de PersonaCama de la base de datos
	public boolean borrarPersonasCama() {
		return mDb.delete(MainDBConstants.PERSONACAMA_TABLE, null, null) > 0;
	}
	//Obtener un PersonaCama de la base de datos
	public PersonaCama getPersonaCama(String filtro, String orden) throws SQLException {
		PersonaCama mPersonaCama = null;
		Cursor cursorPersonaCama = crearCursor(MainDBConstants.PERSONACAMA_TABLE , filtro, null, orden);
		if (cursorPersonaCama != null && cursorPersonaCama.getCount() > 0) {
			cursorPersonaCama.moveToFirst();
			mPersonaCama=CamasHelper.crearPersonaCama(cursorPersonaCama);
			Cama cama = this.getCama(MainDBConstants.codigoCama + "='" + cursorPersonaCama.getString(cursorPersonaCama.getColumnIndex(MainDBConstants.cama)) + "'", null);
			Participante part = this.getParticipante(MainDBConstants.codigo + "=" + cursorPersonaCama.getString(cursorPersonaCama.getColumnIndex(MainDBConstants.participante)), null);
			mPersonaCama.setCama(cama);
			if (part!= null) mPersonaCama.setParticipante(part);
		}
		if (!cursorPersonaCama.isClosed()) cursorPersonaCama.close();
		return mPersonaCama;
	}
	//Obtener una lista de PersonaCama de la base de datos
	public List<PersonaCama> getPersonasCama(String filtro, String orden) throws SQLException {
		List<PersonaCama> mPersonasCama = new ArrayList<PersonaCama>();
		Cursor cursorPersonasCama = crearCursor(MainDBConstants.PERSONACAMA_TABLE, filtro, null, orden);
		if (cursorPersonasCama != null && cursorPersonasCama.getCount() > 0) {
			cursorPersonasCama.moveToFirst();
			mPersonasCama.clear();
			do{
				PersonaCama mPersonaCama = null;
				mPersonaCama = CamasHelper.crearPersonaCama(cursorPersonasCama);
				Cama cama = this.getCama(MainDBConstants.codigoCama + "='" + cursorPersonasCama.getString(cursorPersonasCama.getColumnIndex(MainDBConstants.cama)) + "'", null);
				Participante part = this.getParticipante(MainDBConstants.codigo + "=" + cursorPersonasCama.getString(cursorPersonasCama.getColumnIndex(MainDBConstants.participante)), null);
				if (part!= null) mPersonaCama.setParticipante(part);
				mPersonaCama.setCama(cama);
				mPersonasCama.add(mPersonaCama);
			} while (cursorPersonasCama.moveToNext());
		}
		if (!cursorPersonasCama.isClosed()) cursorPersonasCama.close();
		return mPersonasCama;
	}
	
	/**
	 * Metodos para AreaAmbiente en la base de datos
	 * 
	 * @param area
	 *            Objeto AreaAmbiente que contiene la informacion
	 *
	 */
	//Crear nuevo AreaAmbiente en la base de datos
	public void crearAreaAmbiente(AreaAmbiente area) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(area);
		mDb.insertOrThrow(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar AreaAmbiente existente en la base de datos
	public boolean editarAreaAmbiente(AreaAmbiente area) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(area);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ area.getCodigo()+ "'", null) > 0;
	}

	//Obtener un AreaAmbiente de la base de datos
	public AreaAmbiente getAreaAmbiente(String filtro, String orden) throws SQLException {
		AreaAmbiente mAreaAmbiente = null;
		Cursor cursorAreaAmbiente = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorAreaAmbiente != null && cursorAreaAmbiente.getCount() > 0) {
			cursorAreaAmbiente.moveToFirst();
			mAreaAmbiente=AreaAmbienteHelper.crearAreaAmbiente(cursorAreaAmbiente);
			CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorAreaAmbiente.getString(cursorAreaAmbiente.getColumnIndex(MainDBConstants.casa))+"'", null);
			mAreaAmbiente.setCasa(casa);
		}
		if (!cursorAreaAmbiente.isClosed()) cursorAreaAmbiente.close();
		return mAreaAmbiente;
	}
	//Obtener una lista de AreaAmbiente de la base de datos
	public List<AreaAmbiente> getAreasAmbiente(String filtro, String orden) throws SQLException {
		List<AreaAmbiente> mAreasAmbiente = new ArrayList<AreaAmbiente>();
		Cursor cursorAreasAmbiente = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorAreasAmbiente != null && cursorAreasAmbiente.getCount() > 0) {
			cursorAreasAmbiente.moveToFirst();
			mAreasAmbiente.clear();
			do{
				AreaAmbiente mAreaAmbiente = null;
				mAreaAmbiente = AreaAmbienteHelper.crearAreaAmbiente(cursorAreasAmbiente);
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorAreasAmbiente.getString(cursorAreasAmbiente.getColumnIndex(MainDBConstants.casa))+"'", null);
				mAreaAmbiente.setCasa(casa);
				mAreasAmbiente.add(mAreaAmbiente);
			} while (cursorAreasAmbiente.moveToNext());
		}
		if (!cursorAreasAmbiente.isClosed()) cursorAreasAmbiente.close();
		return mAreasAmbiente;
	}  
	
	/**
	 * Metodos para Banio en la base de datos
	 * 
	 * @param banio
	 *            Objeto Banio que contiene la informacion
	 *
	 */
	//Crear nuevo Banio en la base de datos
	public void crearBanio(Banio banio) {
		ContentValues cv = AreaAmbienteHelper.crearBanioContentValues(banio);
		mDb.insertOrThrow(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar Banio existente en la base de datos
	public boolean editarBanio(Banio banio) {
		ContentValues cv = AreaAmbienteHelper.crearBanioContentValues(banio);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ banio.getCodigo()+ "'", null) > 0;
	}

	//Obtener un Banio de la base de datos
	public Banio getBanio(String filtro, String orden) throws SQLException {
		Banio mBanio = null;
		Cursor cursorBanio = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorBanio != null && cursorBanio.getCount() > 0) {
			cursorBanio.moveToFirst();
			mBanio=AreaAmbienteHelper.crearBanio(cursorBanio);
			AreaAmbiente area = this.getAreaAmbiente(MainDBConstants.codigo + "='" + cursorBanio.getString(cursorBanio.getColumnIndex(MainDBConstants.areaAmbiente))+ "'", null);
			if(area != null){
				mBanio.setAreaAmbiente(area);
				mBanio.setCasa(area.getCasa());
			}
			else{
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorBanio.getString(cursorBanio.getColumnIndex(MainDBConstants.casa))+"'", null);
				mBanio.setCasa(casa);
			}
		}
		if (!cursorBanio.isClosed()) cursorBanio.close();
		return mBanio;
	}
	//Obtener una lista de Banio de la base de datos
	public List<Banio> getBanios(String filtro, String orden) throws SQLException {
		List<Banio> mBanios = new ArrayList<Banio>();
		Cursor cursorBanios = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorBanios != null && cursorBanios.getCount() > 0) {
			cursorBanios.moveToFirst();
			mBanios.clear();
			do{
				Banio mBanio = null;
				mBanio = AreaAmbienteHelper.crearBanio(cursorBanios);
				AreaAmbiente area = this.getAreaAmbiente(MainDBConstants.codigo + "='" + cursorBanios.getString(cursorBanios.getColumnIndex(MainDBConstants.areaAmbiente))+ "'", null);
				if(area != null){
					mBanio.setAreaAmbiente(area);
					mBanio.setCasa(area.getCasa());
					
				}
				else{
					CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorBanios.getString(cursorBanios.getColumnIndex(MainDBConstants.casa))+"'", null);
					mBanio.setCasa(casa);
				}
				mBanios.add(mBanio);
			} while (cursorBanios.moveToNext());
		}
		if (!cursorBanios.isClosed()) cursorBanios.close();
		return mBanios;
	}  
	
	/**
	 * Metodos para sala en la base de datos
	 * 
	 * @param sala
	 *            Objeto Sala que contiene la informacion
	 *
	 */
	//Crear nuevo Sala en la base de datos
	public void crearSala(Sala sala) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(sala);
		mDb.insertOrThrow(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar Sala existente en la base de datos
	public boolean editarSala(Sala sala) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(sala);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ sala.getCodigo()+ "'", null) > 0;
	}

	//Obtener un Sala de la base de datos
	public Sala getSala(String filtro, String orden) throws SQLException {
		Sala mSala = null;
		Cursor cursorSala = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorSala != null && cursorSala.getCount() > 0) {
			cursorSala.moveToFirst();
			mSala= AreaAmbienteHelper.crearSala(cursorSala);
			CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorSala.getString(cursorSala.getColumnIndex(MainDBConstants.casa))+"'", null);
			mSala.setCasa(casa);
		}
		if (!cursorSala.isClosed()) cursorSala.close();
		return mSala;
	}
	//Obtener una lista de Sala de la base de datos
	public List<Sala> getSalas(String filtro, String orden) throws SQLException {
		List<Sala> mSalas = new ArrayList<Sala>();
		Cursor cursorSalas = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorSalas != null && cursorSalas.getCount() > 0) {
			cursorSalas.moveToFirst();
			mSalas.clear();
			do{
				Sala mSala = null;
				mSala = AreaAmbienteHelper.crearSala(cursorSalas);
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorSalas.getString(cursorSalas.getColumnIndex(MainDBConstants.casa))+"'", null);
				mSala.setCasa(casa);
				mSalas.add(mSala);
			} while (cursorSalas.moveToNext());
		}
		if (!cursorSalas.isClosed()) cursorSalas.close();
		return mSalas;
	}  
	
	/**
	 * Metodos para Cocina en la base de datos
	 * 
	 * @param cocina
	 *            Objeto Cocina que contiene la informacion
	 *
	 */
	//Crear nuevo Cocina en la base de datos
	public void crearCocina(Cocina cocina) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(cocina);
		mDb.insertOrThrow(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar Cocina existente en la base de datos
	public boolean editarCocina(Cocina cocina) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(cocina);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ cocina.getCodigo()+ "'", null) > 0;
	}

	//Obtener un Cocina de la base de datos
	public Cocina getCocina(String filtro, String orden) throws SQLException {
		Cocina mCocina = null;
		Cursor cursorCocina = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorCocina != null && cursorCocina.getCount() > 0) {
			cursorCocina.moveToFirst();
			mCocina=AreaAmbienteHelper.crearCocina(cursorCocina);
			CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorCocina.getString(cursorCocina.getColumnIndex(MainDBConstants.casa))+"'", null);
			mCocina.setCasa(casa);
		}
		if (!cursorCocina.isClosed()) cursorCocina.close();
		return mCocina;
	}
	//Obtener una lista de Cocina de la base de datos
	public List<Cocina> getCocinas(String filtro, String orden) throws SQLException {
		List<Cocina> mCocinas = new ArrayList<Cocina>();
		Cursor cursorCocinas = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorCocinas != null && cursorCocinas.getCount() > 0) {
			cursorCocinas.moveToFirst();
			mCocinas.clear();
			do{
				Cocina mCocina = null;
				mCocina =AreaAmbienteHelper.crearCocina(cursorCocinas);
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorCocinas.getString(cursorCocinas.getColumnIndex(MainDBConstants.casa))+"'", null);
				mCocina.setCasa(casa);
				mCocinas.add(mCocina);
			} while (cursorCocinas.moveToNext());
		}
		if (!cursorCocinas.isClosed()) cursorCocinas.close();
		return mCocinas;
	}  
	
	/**
	 * Metodos para Comedor en la base de datos
	 * 
	 * @param comedor
	 *            Objeto Comedor que contiene la informacion
	 *
	 */
	//Crear nuevo Comedor en la base de datos
	public void crearComedor(Comedor comedor) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(comedor);
		mDb.insertOrThrow(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar Comedor existente en la base de datos
	public boolean editarComedor(Comedor comedor) {
		ContentValues cv = AreaAmbienteHelper.crearAreaAmbienteContentValues(comedor);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ comedor.getCodigo()+ "'", null) > 0;
	}

	//Obtener un Comedor de la base de datos
	public Comedor getComedor(String filtro, String orden) throws SQLException {
		Comedor mComedor = null;
		Cursor cursorComedor = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorComedor != null && cursorComedor.getCount() > 0) {
			cursorComedor.moveToFirst();
			mComedor=AreaAmbienteHelper.crearComedor(cursorComedor);
			CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorComedor.getString(cursorComedor.getColumnIndex(MainDBConstants.casa))+"'", null);
			mComedor.setCasa(casa);
		}
		if (!cursorComedor.isClosed()) cursorComedor.close();
		return mComedor;
	}
	//Obtener una lista de Comedor de la base de datos
	public List<Comedor> getComedores(String filtro, String orden) throws SQLException {
		List<Comedor> mComedores = new ArrayList<Comedor>();
		Cursor cursorComedores = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorComedores != null && cursorComedores.getCount() > 0) {
			cursorComedores.moveToFirst();
			mComedores.clear();
			do{
				Comedor mComedor = null;
				mComedor = AreaAmbienteHelper.crearComedor(cursorComedores);
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorComedores.getString(cursorComedores.getColumnIndex(MainDBConstants.casa))+"'", null);
				mComedor.setCasa(casa);
				mComedores.add(mComedor);
			} while (cursorComedores.moveToNext());
		}
		if (!cursorComedores.isClosed()) cursorComedores.close();
		return mComedores;
	}  

	/**
	 * Metodos para Ventana en la base de datos
	 * 
	 * @param ventana
	 *            Objeto Ventana que contiene la informacion
	 *
	 */
	//Crear nuevo Ventana en la base de datos
	public void crearVentana(Ventana ventana) {
		ContentValues cv = AreaAmbienteHelper.crearVentanaContentValues(ventana);
		mDb.insertOrThrow(MainDBConstants.AREA_AMBIENTE_TABLE, null, cv);
	}
	//Editar Ventana existente en la base de datos
	public boolean editarVentana(Ventana ventana) {
		ContentValues cv = AreaAmbienteHelper.crearVentanaContentValues(ventana);
		return mDb.update(MainDBConstants.AREA_AMBIENTE_TABLE , cv, MainDBConstants.codigo + "='" 
				+ ventana.getCodigo()+ "'", null) > 0;
	}

	//Obtener un Ventana de la base de datos
	public Ventana getVentana(String filtro, String orden) throws SQLException {
		Ventana mVentana = null;
		Cursor cursorVentana = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE , filtro, null, orden);
		if (cursorVentana != null && cursorVentana.getCount() > 0) {
			cursorVentana.moveToFirst();
			mVentana=AreaAmbienteHelper.crearVentana(cursorVentana);
			AreaAmbiente area = this.getAreaAmbiente(MainDBConstants.codigo + "='" + cursorVentana.getString(cursorVentana.getColumnIndex(MainDBConstants.areaAmbiente))+ "'", null);
			if(area != null){
				mVentana.setAreaAmbiente(area);
				mVentana.setCasa(area.getCasa());
			}
		}
		if (!cursorVentana.isClosed()) cursorVentana.close();
		return mVentana;
	}
	//Obtener una lista de Ventana de la base de datos
	public List<Ventana> getVentanas(String filtro, String orden) throws SQLException {
		List<Ventana> mVentanas = new ArrayList<Ventana>();
		Cursor cursorVentanas = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, filtro, null, orden);
		if (cursorVentanas != null && cursorVentanas.getCount() > 0) {
			cursorVentanas.moveToFirst();
			mVentanas.clear();
			do{
				Ventana mVentana = null;
				mVentana = AreaAmbienteHelper.crearVentana(cursorVentanas);
				AreaAmbiente area = this.getAreaAmbiente(MainDBConstants.codigo + "='" + cursorVentanas.getString(cursorVentanas.getColumnIndex(MainDBConstants.areaAmbiente))+ "'", null);
				Banio banio = this.getBanio(MainDBConstants.codigo + "='" + cursorVentanas.getString(cursorVentanas.getColumnIndex(MainDBConstants.areaBanio))+ "'", null);

				if(area != null){
					mVentana.setAreaAmbiente(area);
					mVentana.setCasa(area.getCasa());
				}
				if(banio != null){
					mVentana.setAreaBanio(banio);
					mVentana.setCasa(banio.getAreaAmbiente().getCasa());
				}

				mVentanas.add(mVentana);
			} while (cursorVentanas.moveToNext());
		}
		if (!cursorVentanas.isClosed()) cursorVentanas.close();
		return mVentanas;
	}  
	
	
	/**
	 * Metodos para Cuartos en la base de datos
	 * 
	 * @param cuarto
	 *            Objeto Cuarto que contiene la informacion
	 *
	 */
	//Crear nuevo Cuarto en la base de datos
	public void crearCuarto(Cuarto cuarto) {
		ContentValues cv = CuartoHelper.crearCuartoContentValues(cuarto);
		mDb.insertOrThrow(MainDBConstants.CUARTO_TABLE, null, cv);
	}
	//Editar Cuarto existente en la base de datos
	public boolean editarCuarto(Cuarto cuarto) {
		ContentValues cv = CuartoHelper.crearCuartoContentValues(cuarto);
		return mDb.update(MainDBConstants.CUARTO_TABLE , cv, MainDBConstants.codigo + "='" 
				+ cuarto.getCodigo()+ "'", null) > 0;
	}
	//Limpiar la tabla de cuartos de la base de datos
	public boolean borrarCuartos() {
		return mDb.delete(MainDBConstants.CUARTO_TABLE, null, null) > 0;
	}
	//Obtener un Cuarto de la base de datos
	public Cuarto getCuarto(String filtro, String orden) throws SQLException {
		Cuarto mCuarto = null;
		Cursor cursorCuarto = crearCursor(MainDBConstants.CUARTO_TABLE , filtro, null, orden);
		if (cursorCuarto != null && cursorCuarto.getCount() > 0) {
			cursorCuarto.moveToFirst();
			mCuarto=CuartoHelper.crearCuarto(cursorCuarto);
			CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorCuarto.getString(cursorCuarto.getColumnIndex(MainDBConstants.casa))+"'", null);
			mCuarto.setCasa(casa);
		}
		if (!cursorCuarto.isClosed()) cursorCuarto.close();
		return mCuarto;
	}
	//Obtener una lista de Cuarto de la base de datos
	public List<Cuarto> getCuartos(String filtro, String orden) throws SQLException {
		List<Cuarto> mCuartos = new ArrayList<Cuarto>();
		Cursor cursorCuartos = crearCursor(MainDBConstants.CUARTO_TABLE, filtro, null, orden);
		if (cursorCuartos != null && cursorCuartos.getCount() > 0) {
			cursorCuartos.moveToFirst();
			mCuartos.clear();
			do{
				Cuarto mCuarto = null;
				mCuarto = CuartoHelper.crearCuarto(cursorCuartos);
				CasaCohorteFamilia casa = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" +cursorCuartos.getString(cursorCuartos.getColumnIndex(MainDBConstants.casa))+"'", null);
				mCuarto.setCasa(casa);
				mCuartos.add(mCuarto);
			} while (cursorCuartos.moveToNext());
		}
		if (!cursorCuartos.isClosed()) cursorCuartos.close();
		return mCuartos;
	}

    /**
     * Metodos para ParticipanteSeroprevalencia en la base de datos
     *
     * @param participanteSeroprevalencia
     *            Objeto ParticipanteSeroprevalencias que contiene la informacion
     *
     */
    //Crear nuevo ParticipanteSeroprevalencias en la base de datos
    public void crearParticipanteSeroprevalencia(ParticipanteSeroprevalencia participanteSeroprevalencia) {
        ContentValues cv = ParticipanteSeroprevalenciaHelper.crearParticipanteSeroprevalenciaContentValues(participanteSeroprevalencia);
        mDb.insertOrThrow(SeroprevalenciaDBConstants.PARTICIPANTESA_TABLE, null, cv);
    }
	//Crear nuevo ParticipanteSeroprevalencia en la base de datos desde otro equipo
	public void insertarParticipanteSeroprevalencia(String participanteSeroprevalenciaSQL) {
		mDb.execSQL(participanteSeroprevalenciaSQL);
	}
    //Editar ParticipanteSeroprevalencia existente en la base de datos
    public boolean editarParticipanteSeroprevalencia(ParticipanteSeroprevalencia participanteSeroprevalencia) {
        ContentValues cv = ParticipanteSeroprevalenciaHelper.crearParticipanteSeroprevalenciaContentValues(participanteSeroprevalencia);
        return mDb.update(SeroprevalenciaDBConstants.PARTICIPANTESA_TABLE, cv, SeroprevalenciaDBConstants.participante + "='"
                + participanteSeroprevalencia.getParticipante().getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de ParticipanteSeroprevalencia de la base de datos
    public boolean borrarParticipanteSeroprevalencia() {
        return mDb.delete(SeroprevalenciaDBConstants.PARTICIPANTESA_TABLE, null, null) > 0;
    }
    //Obtener una ParticipanteSeroprevalencia de la base de datos
    public ParticipanteSeroprevalencia getParticipanteSeroprevalencia(String filtro, String orden) throws SQLException {
        ParticipanteSeroprevalencia mParticipanteSeroprevalencia = null;
        Cursor cursor = crearCursor(SeroprevalenciaDBConstants.PARTICIPANTESA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mParticipanteSeroprevalencia= ParticipanteSeroprevalenciaHelper.crearParticipanteSeroprevalencia(cursor);
            CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.casaCHF)), null);
            mParticipanteSeroprevalencia.setCasaCHF(cchf);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.participante)), null);
            mParticipanteSeroprevalencia.setParticipante(participante);
        }
        if (!cursor.isClosed()) cursor.close();
        return mParticipanteSeroprevalencia;
    }
    //Obtener una lista de ParticipanteSeroprevalencia de la base de datos
    public List<ParticipanteSeroprevalencia> getParticipantesSeroprevalencia(String filtro, String orden) throws SQLException {
        List<ParticipanteSeroprevalencia> mParticipanteSeroprevalencias = new ArrayList<ParticipanteSeroprevalencia>();
        Cursor cursor = crearCursor(SeroprevalenciaDBConstants.PARTICIPANTESA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mParticipanteSeroprevalencias.clear();
            do{
                ParticipanteSeroprevalencia mParticipanteSeroprevalencia = null;
                mParticipanteSeroprevalencia = ParticipanteSeroprevalenciaHelper.crearParticipanteSeroprevalencia(cursor);
                CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.casaCHF)), null);
                mParticipanteSeroprevalencia.setCasaCHF(cchf);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.participante)), null);
                mParticipanteSeroprevalencia.setParticipante(participante);
                mParticipanteSeroprevalencias.add(mParticipanteSeroprevalencia);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mParticipanteSeroprevalencias;
    }

    /**
     * Metodos para EncuestaCasaSA en la base de datos
     *
     * @param encuestaCasaSA
     *            Objeto EncuestaCasaSA que contiene la informacion
     *
     */
    //Crear nuevo EncuestaCasaSA en la base de datos
    public void crearEncuestaCasaSA(EncuestaCasaSA encuestaCasaSA) {
        ContentValues cv = EncuestaCasaSAHelper.crearEncuestaCasaSAContentValues(encuestaCasaSA);
        mDb.insertOrThrow(SeroprevalenciaDBConstants.ENCUESTA_CASASA_TABLE, null, cv);
    }
    //Editar EncuestaCasaSA existente en la base de datos
    public boolean editarEncuestaCasaSA(EncuestaCasaSA encuestaCasaSA) {
        ContentValues cv = EncuestaCasaSAHelper.crearEncuestaCasaSAContentValues(encuestaCasaSA);
        return mDb.update(SeroprevalenciaDBConstants.ENCUESTA_CASASA_TABLE, cv, SeroprevalenciaDBConstants.codigo + "='"
                + encuestaCasaSA.getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de EncuestaCasaSA de la base de datos
    public boolean borrarEncuestaCasaSA() {
        return mDb.delete(SeroprevalenciaDBConstants.ENCUESTA_CASASA_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaCasaSA de la base de datos
    public EncuestaCasaSA getEncuestaCasaSA(String filtro, String orden) throws SQLException {
        EncuestaCasaSA mEncuestaCasaSA = null;
        Cursor cursor = crearCursor(SeroprevalenciaDBConstants.ENCUESTA_CASASA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestaCasaSA= EncuestaCasaSAHelper.crearEncuestaCasaSA(cursor);
            Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.casa)), null);
            mEncuestaCasaSA.setCasa(casa);
            CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.casaCHF)), null);
            mEncuestaCasaSA.setCasaChf(cchf);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestaCasaSA;
    }
    //Obtener una lista de EncuestaCasaSA de la base de datos
    public List<EncuestaCasaSA> getEncuestasCasaSA(String filtro, String orden) throws SQLException {
        List<EncuestaCasaSA> mEncuestaCasaSAs = new ArrayList<EncuestaCasaSA>();
        Cursor cursor = crearCursor(SeroprevalenciaDBConstants.ENCUESTA_CASASA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestaCasaSAs.clear();
            do{
                EncuestaCasaSA mEncuestaCasaSA = null;
                mEncuestaCasaSA = EncuestaCasaSAHelper.crearEncuestaCasaSA(cursor);
                Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.casa)), null);
                mEncuestaCasaSA.setCasa(casa);
                CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "=" + cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.casaCHF)), null);
                mEncuestaCasaSA.setCasaChf(cchf);
                mEncuestaCasaSAs.add(mEncuestaCasaSA);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestaCasaSAs;
    }

    /**
     * Metodos para EncuestaParticipanteSA en la base de datos
     *
     * @param encuestaParticipanteSA
     *            Objeto EncuestaParticipanteSA que contiene la informacion
     *
     */
    //Crear nuevo EncuestaParticipanteSA en la base de datos
    public void crearEncuestaParticipanteSA(EncuestaParticipanteSA encuestaParticipanteSA) {
        ContentValues cv = EncuestaParticipanteSAHelper.crearEncuestaParticipanteSAContentValues(encuestaParticipanteSA);
        mDb.insertOrThrow(SeroprevalenciaDBConstants.ENCUESTA_PARTICIPANTESA_TABLE, null, cv);
    }
    //Editar EncuestaParticipanteSA existente en la base de datos
    public boolean editarEncuestaParticipanteSA(EncuestaParticipanteSA encuestaParticipanteSA) {
        ContentValues cv = EncuestaParticipanteSAHelper.crearEncuestaParticipanteSAContentValues(encuestaParticipanteSA);
        return mDb.update(SeroprevalenciaDBConstants.ENCUESTA_PARTICIPANTESA_TABLE, cv, SeroprevalenciaDBConstants.codigo + "='"
                + encuestaParticipanteSA.getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de EncuestaParticipanteSA de la base de datos
    public boolean borrarEncuestaParticipanteSA() {
        return mDb.delete(SeroprevalenciaDBConstants.ENCUESTA_PARTICIPANTESA_TABLE, null, null) > 0;
    }
    //Obtener una EncuestaParticipanteSA de la base de datos
    public EncuestaParticipanteSA getEncuestaParticipanteSA(String filtro, String orden) throws SQLException {
        EncuestaParticipanteSA mEncuestaParticipanteSA = null;
        Cursor cursor = crearCursor(SeroprevalenciaDBConstants.ENCUESTA_PARTICIPANTESA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestaParticipanteSA= EncuestaParticipanteSAHelper.crearEncuestaParticipanteSA(cursor);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.participante)), null);
            mEncuestaParticipanteSA.setParticipante(participante);
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestaParticipanteSA;
    }
    //Obtener una lista de EncuestaParticipanteSA de la base de datos
    public List<EncuestaParticipanteSA> getEncuestasParticipanteSA(String filtro, String orden) throws SQLException {
        List<EncuestaParticipanteSA> mEncuestasParticipanteSA = new ArrayList<EncuestaParticipanteSA>();
        Cursor cursor = crearCursor(SeroprevalenciaDBConstants.ENCUESTA_PARTICIPANTESA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mEncuestasParticipanteSA.clear();
            do{
                EncuestaParticipanteSA mEncuestaParticipanteSA = null;
                mEncuestaParticipanteSA = EncuestaParticipanteSAHelper.crearEncuestaParticipanteSA(cursor);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(SeroprevalenciaDBConstants.participante)), null);
                mEncuestaParticipanteSA.setParticipante(participante);
                mEncuestasParticipanteSA.add(mEncuestaParticipanteSA);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mEncuestasParticipanteSA;
    }

	/**
	 * Metodos para Telefonos en la base de datos
	 * 
	 * @param tel
	 *            Objeto TelefonoContacto que contiene la informacion
	 *
	 */
	//Crear nuevo TelefonoContacto en la base de datos
	public void crearTelefonoContacto(TelefonoContacto tel) {
		ContentValues cv = TelefonoContactoHelper.crearTelefContactoContentValues(tel);
		mDb.insertOrThrow(MainDBConstants.TELEFONO_CONTACTO_TABLE, null, cv);
	}
	//Editar TelefonoContacto existente en la base de datos
	public boolean editarTelefonoContacto(TelefonoContacto tel) {
		ContentValues cv = TelefonoContactoHelper.crearTelefContactoContentValues(tel);
		return mDb.update(MainDBConstants.TELEFONO_CONTACTO_TABLE , cv, MainDBConstants.id + "='" 
				+ tel.getId()+ "'", null) > 0;
	}
	//Limpiar la tabla de TelefonoContacto de la base de datos
	public boolean borrarTelefonoContacto() {
		return mDb.delete(MainDBConstants.TELEFONO_CONTACTO_TABLE, null, null) > 0;
	}
	//Obtener un TelefonoContacto de la base de datos
	public TelefonoContacto getTelefonoContacto(String filtro, String orden) throws SQLException {
		TelefonoContacto mTelefonoContacto = null;
		Cursor cursorTelefonoContacto = crearCursor(MainDBConstants.TELEFONO_CONTACTO_TABLE , filtro, null, orden);
		if (cursorTelefonoContacto != null && cursorTelefonoContacto.getCount() > 0) {
			cursorTelefonoContacto.moveToFirst();
			mTelefonoContacto=TelefonoContactoHelper.crearTelefonoContacto(cursorTelefonoContacto);
			Casa casa = this.getCasa(MainDBConstants.codigo + "='" +cursorTelefonoContacto.getString(cursorTelefonoContacto.getColumnIndex(MainDBConstants.casa))+"'", null);
			mTelefonoContacto.setCasa(casa);
			Participante part = this.getParticipante(MainDBConstants.codigo + "='" +cursorTelefonoContacto.getString(cursorTelefonoContacto.getColumnIndex(MainDBConstants.participante))+"'", null);
			mTelefonoContacto.setParticipante(part);
		}
		if (!cursorTelefonoContacto.isClosed()) cursorTelefonoContacto.close();
		return mTelefonoContacto;
	}
	//Obtener una lista de TelefonoContacto de la base de datos
	public List<TelefonoContacto> getTelefonosContacto(String filtro, String orden) throws SQLException {
		List<TelefonoContacto> mTelefonosContacto = new ArrayList<TelefonoContacto>();
		Cursor cursorTelefonosContacto = crearCursor(MainDBConstants.TELEFONO_CONTACTO_TABLE, filtro, null, orden);
		if (cursorTelefonosContacto != null && cursorTelefonosContacto.getCount() > 0) {
			cursorTelefonosContacto.moveToFirst();
			mTelefonosContacto.clear();
			do{
				TelefonoContacto mTelefonoContacto = null;
				mTelefonoContacto = TelefonoContactoHelper.crearTelefonoContacto(cursorTelefonosContacto);
				Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorTelefonosContacto.getInt(cursorTelefonosContacto.getColumnIndex(MainDBConstants.casa)), null);
				mTelefonoContacto.setCasa(casa);
				Participante part = this.getParticipante(MainDBConstants.codigo + "=" +cursorTelefonosContacto.getInt(cursorTelefonosContacto.getColumnIndex(MainDBConstants.participante)), null);
				mTelefonoContacto.setParticipante(part);
				mTelefonosContacto.add(mTelefonoContacto);
			} while (cursorTelefonosContacto.moveToNext());
		}
		if (!cursorTelefonosContacto.isClosed()) cursorTelefonosContacto.close();
		return mTelefonosContacto;
	}


    //Crear nuevo recepcionMuestra en la base de datos
    public void crearRecepcionMuestra(RecepcionMuestra recepcionMuestra) throws Exception {
        ContentValues cv = RecepcionMuestraHelper.crearRecepcionMuestraContentValues(recepcionMuestra);
        mDb.insertOrThrow(MuestrasDBConstants.RECEPCION_MUESTRA_TABLE, null, cv);
    }

    //Editar recepcionMuestra existente en la base de datos
    public boolean editarRecepcionMuestra(RecepcionMuestra recepcionMuestra) throws Exception{
        ContentValues cv = RecepcionMuestraHelper.crearRecepcionMuestraContentValues(recepcionMuestra);
        return mDb.update(MuestrasDBConstants.RECEPCION_MUESTRA_TABLE , cv, MuestrasDBConstants.codigo + "='"
                + recepcionMuestra.getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de recepción de muestras de la base de datos
    public boolean borrarRecepcionMuestras() {
        return mDb.delete(MuestrasDBConstants.RECEPCION_MUESTRA_TABLE, null, null) > 0;
    }
    //Obtener un recepcionMuestra de la base de datos
    public RecepcionMuestra getRecepcionMuestra(String filtro, String orden) throws SQLException {
        RecepcionMuestra mRecepcion = null;
        Cursor cursor = crearCursor(MuestrasDBConstants.RECEPCION_MUESTRA_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mRecepcion=RecepcionMuestraHelper.crearRecepcionMuestra(cursor);
        }
        if (!cursor.isClosed()) cursor.close();
        return mRecepcion;
    }
    //Obtener una lista de recepción de muestras de la base de datos
    public List<RecepcionMuestra> getRecepcionMuestras(String filtro, String orden) throws SQLException {
        List<RecepcionMuestra> mRecepciones = new ArrayList<RecepcionMuestra>();
        Cursor cursor = crearCursor(MuestrasDBConstants.RECEPCION_MUESTRA_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mRecepciones.clear();
            do{
                RecepcionMuestra mRecepcion = null;
                mRecepcion = RecepcionMuestraHelper.crearRecepcionMuestra(cursor);
                mRecepciones.add(mRecepcion);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mRecepciones;
    }
    
  //Crear nuevo CasaCohorteFamiliaCaso en la base de datos
    public void crearCasaCohorteFamiliaCaso(CasaCohorteFamiliaCaso casacaso) throws Exception {
        ContentValues cv = CasaCohorteFamiliaCasoHelper.crearCasaCohorteFamiliaCasoContentValues(casacaso);
        mDb.insertOrThrow(CasosDBConstants.CASAS_CASOS_TABLE, null, cv);
    }

    //Editar CasaCohorteFamiliaCaso existente en la base de datos
    public boolean editarCasaCohorteFamiliaCaso(CasaCohorteFamiliaCaso casacaso) throws Exception{
        ContentValues cv = CasaCohorteFamiliaCasoHelper.crearCasaCohorteFamiliaCasoContentValues(casacaso);
        return mDb.update(CasosDBConstants.CASAS_CASOS_TABLE , cv, CasosDBConstants.codigoCaso + "='"
                + casacaso.getCodigoCaso() + "'", null) > 0;
    }
    //Limpiar la tabla de CasaCohorteFamiliaCaso de la base de datos
    public boolean borrarCasaCohorteFamiliaCaso() {
        return mDb.delete(CasosDBConstants.CASAS_CASOS_TABLE, null, null) > 0;
    }
    //Obtener un CasaCohorteFamiliaCaso de la base de datos
    public CasaCohorteFamiliaCaso getCasaCohorteFamiliaCaso(String filtro, String orden) throws SQLException {
    	CasaCohorteFamiliaCaso mCasaCohorteFamiliaCaso = null;
        Cursor cursor = crearCursor(CasosDBConstants.CASAS_CASOS_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCasaCohorteFamiliaCaso=CasaCohorteFamiliaCasoHelper.crearCasaCohorteFamiliaCaso(cursor);
            CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" + cursor.getString(cursor.getColumnIndex(MainDBConstants.casa)) +"'", null);
            mCasaCohorteFamiliaCaso.setCasa(cchf);
        }
        if (!cursor.isClosed()) cursor.close();
        return mCasaCohorteFamiliaCaso;
    }
    //Obtener una lista de CasaCohorteFamiliaCaso de la base de datos
    public List<CasaCohorteFamiliaCaso> getCasaCohorteFamiliaCasos(String filtro, String orden) throws SQLException {
        List<CasaCohorteFamiliaCaso> mCasaCohorteFamiliaCasos = new ArrayList<CasaCohorteFamiliaCaso>();
        Cursor cursor = crearCursor(CasosDBConstants.CASAS_CASOS_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCasaCohorteFamiliaCasos.clear();
            do{
            	CasaCohorteFamiliaCaso mCasaCohorteFamiliaCaso = null;
                mCasaCohorteFamiliaCaso = CasaCohorteFamiliaCasoHelper.crearCasaCohorteFamiliaCaso(cursor);
                CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" + cursor.getString(cursor.getColumnIndex(MainDBConstants.casa)) +"'", null);
                mCasaCohorteFamiliaCaso.setCasa(cchf);
                mCasaCohorteFamiliaCasos.add(mCasaCohorteFamiliaCaso);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mCasaCohorteFamiliaCasos;
    }
    
    //Crear nuevo ParticipanteCohorteFamiliaCaso en la base de datos
    public void crearParticipanteCohorteFamiliaCaso(ParticipanteCohorteFamiliaCaso partcaso) throws Exception {
        ContentValues cv = ParticipanteCohorteFamiliaCasoHelper.crearParticipanteCohorteFamiliaCasoContentValues(partcaso);
        mDb.insertOrThrow(CasosDBConstants.PARTICIPANTES_CASOS_TABLE, null, cv);
    }

    //Crear nuevo ParticipanteCohorteFamiliaCaso en la base de datos desde otro equipo
    public void insertarParticipanteCohorteFamiliaCaso(String participanteCohorteFamiliaCasoSQL) {
        mDb.execSQL(participanteCohorteFamiliaCasoSQL);
    }

    //Editar ParticipanteCohorteFamiliaCaso existente en la base de datos
    public boolean editarParticipanteCohorteFamiliaCaso(ParticipanteCohorteFamiliaCaso partcaso) throws Exception {
        ContentValues cv = ParticipanteCohorteFamiliaCasoHelper.crearParticipanteCohorteFamiliaCasoContentValues(partcaso);
        return mDb.update(CasosDBConstants.PARTICIPANTES_CASOS_TABLE , cv, CasosDBConstants.codigoCasoParticipante + "='"
                + partcaso.getCodigoCasoParticipante() + "'", null) > 0;
    }
    //Limpiar la tabla de ParticipanteCohorteFamiliaCaso de la base de datos
    public boolean borrarParticipanteCohorteFamiliaCaso() {
        return mDb.delete(CasosDBConstants.PARTICIPANTES_CASOS_TABLE, null, null) > 0;
    }
    //Obtener un ParticipanteCohorteFamiliaCaso de la base de datos
    public ParticipanteCohorteFamiliaCaso getParticipanteCohorteFamiliaCaso(String filtro, String orden) throws SQLException {
    	ParticipanteCohorteFamiliaCaso mParticipanteCohorteFamiliaCaso = null;
        Cursor cursor = crearCursor(CasosDBConstants.PARTICIPANTES_CASOS_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mParticipanteCohorteFamiliaCaso=ParticipanteCohorteFamiliaCasoHelper.crearParticipanteCohorteFamiliaCaso(cursor);
            CasaCohorteFamiliaCaso caso = this.getCasaCohorteFamiliaCaso(CasosDBConstants.codigoCaso + "='" +cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoCaso))+"'", null);
            mParticipanteCohorteFamiliaCaso.setCodigoCaso(caso);
            ParticipanteCohorteFamilia participante = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" +cursor.getInt(cursor.getColumnIndex(CasosDBConstants.participante)), null);
            mParticipanteCohorteFamiliaCaso.setParticipante(participante);
        }
        if (!cursor.isClosed()) cursor.close();
        return mParticipanteCohorteFamiliaCaso;
    }
    //Obtener una lista de ParticipanteCohorteFamiliaCaso de la base de datos
    public List<ParticipanteCohorteFamiliaCaso> getParticipanteCohorteFamiliaCasos(String filtro, String orden) throws SQLException {
        List<ParticipanteCohorteFamiliaCaso> mParticipanteCohorteFamiliaCasos = new ArrayList<ParticipanteCohorteFamiliaCaso>();
        Cursor cursor = crearCursor(CasosDBConstants.PARTICIPANTES_CASOS_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mParticipanteCohorteFamiliaCasos.clear();
            do{
            	ParticipanteCohorteFamiliaCaso mParticipanteCohorteFamiliaCaso = null;
                mParticipanteCohorteFamiliaCaso = ParticipanteCohorteFamiliaCasoHelper.crearParticipanteCohorteFamiliaCaso(cursor);
                CasaCohorteFamiliaCaso caso = this.getCasaCohorteFamiliaCaso(CasosDBConstants.codigoCaso + "='" +cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoCaso))+"'", null);
                mParticipanteCohorteFamiliaCaso.setCodigoCaso(caso);
                ParticipanteCohorteFamilia participante = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" +cursor.getInt(cursor.getColumnIndex(CasosDBConstants.participante)), null);
                mParticipanteCohorteFamiliaCaso.setParticipante(participante);
                mParticipanteCohorteFamiliaCasos.add(mParticipanteCohorteFamiliaCaso);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mParticipanteCohorteFamiliaCasos;
    }
    //Obtener una lista de datos con ParticipanteCohorteFamiliaCaso de la base de datos
    public List<ParticipanteCohorteFamiliaCasoData> getParticipanteCohorteFamiliaCasosDatos(String filtro, String orden) throws SQLException {
    	List<ParticipanteCohorteFamiliaCasoData> mParticipanteCohorteFamiliaCasosDatos = new ArrayList<ParticipanteCohorteFamiliaCasoData>();
        Cursor cursor = crearCursor(CasosDBConstants.PARTICIPANTES_CASOS_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mParticipanteCohorteFamiliaCasosDatos.clear();
            do{
            	ParticipanteCohorteFamiliaCasoData pd = new ParticipanteCohorteFamiliaCasoData();
            	ParticipanteCohorteFamiliaCaso mParticipanteCohorteFamiliaCaso = null;
                mParticipanteCohorteFamiliaCaso = ParticipanteCohorteFamiliaCasoHelper.crearParticipanteCohorteFamiliaCaso(cursor);
                CasaCohorteFamiliaCaso caso = this.getCasaCohorteFamiliaCaso(CasosDBConstants.codigoCaso + "='" +cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoCaso))+"'", null);
                mParticipanteCohorteFamiliaCaso.setCodigoCaso(caso);
                ParticipanteCohorteFamilia participante = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" +cursor.getInt(cursor.getColumnIndex(CasosDBConstants.participante)), null);
                //mostrar solo registros con contraparte en la tabla de participantes y que esten activos
                if (participante!=null && participante.getParticipante()!=null && participante.getParticipante().getProcesos().getEstPart().equals(1)) {
                    mParticipanteCohorteFamiliaCaso.setParticipante(participante);
                    pd.setParticipante(mParticipanteCohorteFamiliaCaso);
                    List<VisitaSeguimientoCaso> mVisitaSeguimientoCasos = new ArrayList<VisitaSeguimientoCaso>();
                    mVisitaSeguimientoCasos = getVisitaSeguimientoCasos(CasosDBConstants.codigoCasoParticipante + " = '" + mParticipanteCohorteFamiliaCaso.getCodigoCasoParticipante() + "'", MainDBConstants.fechaVisita);
                    pd.setNumVisitas(mVisitaSeguimientoCasos.size());
                    if (mVisitaSeguimientoCasos.size() > 0)
                        pd.setFechaUltimaVisita(mVisitaSeguimientoCasos.get(mVisitaSeguimientoCasos.size() - 1).getFechaVisita());
                    mParticipanteCohorteFamiliaCasosDatos.add(pd);
                }
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        
        return mParticipanteCohorteFamiliaCasosDatos;
    }
    
    //Crear nuevo VisitaSeguimientoCaso en la base de datos
    public void crearVisitaSeguimientoCaso(VisitaSeguimientoCaso visitacaso) throws Exception {
        ContentValues cv = VisitaSeguimientoCasoHelper.crearVisitaSeguimientoCasoContentValues(visitacaso);
        mDb.insertOrThrow(CasosDBConstants.VISITAS_CASOS_TABLE, null, cv);
    }

    //Editar VisitaSeguimientoCaso existente en la base de datos
    public boolean editarVisitaSeguimientoCaso(VisitaSeguimientoCaso visitacaso) throws Exception{
        ContentValues cv = VisitaSeguimientoCasoHelper.crearVisitaSeguimientoCasoContentValues(visitacaso);
        return mDb.update(CasosDBConstants.VISITAS_CASOS_TABLE , cv, CasosDBConstants.codigoCasoVisita + "='"
                + visitacaso.getCodigoCasoVisita() + "'", null) > 0;
    }
    //Limpiar la tabla de VisitaSeguimientoCaso de la base de datos
    public boolean borrarVisitaSeguimientoCaso() {
        return mDb.delete(CasosDBConstants.VISITAS_CASOS_TABLE, null, null) > 0;
    }
    //Seleccionar ultima visita de seguimiento
    public Integer selectUltimaVisitaSeguimientoCaso(String codigoParticipanteCaso) {
    	Integer vis = 0;
    	Cursor cursor = mDb.rawQuery("select max(visita) from chf_visitas_casos where codigoCasoParticipante = '"+codigoParticipanteCaso+"'", null);
    	if (cursor != null && cursor.getCount() > 0) {
    		cursor.moveToFirst();
    		vis = cursor.getInt(0);
    	}
    	if (!cursor.isClosed()) cursor.close();
        return vis;
    }
    //Obtener un VisitaSeguimientoCaso de la base de datos
    public VisitaSeguimientoCaso getVisitaSeguimientoCaso(String filtro, String orden) throws SQLException {
    	VisitaSeguimientoCaso mVisitaSeguimientoCaso = null;
        Cursor cursor = crearCursor(CasosDBConstants.VISITAS_CASOS_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mVisitaSeguimientoCaso=VisitaSeguimientoCasoHelper.crearVisitaSeguimientoCaso(cursor);
            ParticipanteCohorteFamiliaCaso caso = this.getParticipanteCohorteFamiliaCaso(CasosDBConstants.codigoCasoParticipante + "='" +cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoCasoParticipante)) +"'", null);
            mVisitaSeguimientoCaso.setCodigoParticipanteCaso(caso);
        }
        if (!cursor.isClosed()) cursor.close();
        return mVisitaSeguimientoCaso;
    }
    //Obtener una lista de VisitaSeguimientoCaso de la base de datos
    public List<VisitaSeguimientoCaso> getVisitaSeguimientoCasos(String filtro, String orden) throws SQLException {
        List<VisitaSeguimientoCaso> mVisitaSeguimientoCasos = new ArrayList<VisitaSeguimientoCaso>();
        Cursor cursor = crearCursor(CasosDBConstants.VISITAS_CASOS_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mVisitaSeguimientoCasos.clear();
            do{
            	VisitaSeguimientoCaso mVisitaSeguimientoCaso = null;
                mVisitaSeguimientoCaso = VisitaSeguimientoCasoHelper.crearVisitaSeguimientoCaso(cursor);
                ParticipanteCohorteFamiliaCaso caso = this.getParticipanteCohorteFamiliaCaso(CasosDBConstants.codigoCasoParticipante + "='" +cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoCasoParticipante)) +"'", null);
                mVisitaSeguimientoCaso.setCodigoParticipanteCaso(caso);
                mVisitaSeguimientoCasos.add(mVisitaSeguimientoCaso);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mVisitaSeguimientoCasos;
    }
    
    
    //Crear nuevo VisitaFallidaCaso en la base de datos
    public void crearVisitaFallidaCaso(VisitaFallidaCaso visitaFallida) throws Exception {
        ContentValues cv = VisitaFallidaCasoHelper.crearVisitaFallidaCasoContentValues(visitaFallida);
        mDb.insertOrThrow(CasosDBConstants.VISITAS_FALLIDAS_CASOS_TABLE, null, cv);
    }

    //Editar VisitaFallidaCaso existente en la base de datos
    public boolean editarVisitaFallidaCaso(VisitaFallidaCaso visitaFallida) throws Exception{
        ContentValues cv = VisitaFallidaCasoHelper.crearVisitaFallidaCasoContentValues(visitaFallida);
        return mDb.update(CasosDBConstants.VISITAS_FALLIDAS_CASOS_TABLE , cv, CasosDBConstants.codigoFallaVisita + "='"
                + visitaFallida.getCodigoFallaVisita() + "'", null) > 0;
    }
    //Limpiar la tabla de VisitaFallidaCaso de la base de datos
    public boolean borrarVisitaFallidaCaso() {
        return mDb.delete(CasosDBConstants.VISITAS_FALLIDAS_CASOS_TABLE, null, null) > 0;
    }
    
    //Obtener un VisitaFallidaCaso de la base de datos
    public VisitaFallidaCaso getVisitaFallidaCaso(String filtro, String orden) throws SQLException {
    	VisitaFallidaCaso mVisitaFallidaCaso = null;
        Cursor cursor = crearCursor(CasosDBConstants.VISITAS_FALLIDAS_CASOS_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mVisitaFallidaCaso=VisitaFallidaCasoHelper.crearVisitaFallidaCaso(cursor);
            ParticipanteCohorteFamiliaCaso caso = this.getParticipanteCohorteFamiliaCaso(CasosDBConstants.codigoCasoParticipante + "='" +cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoParticipanteCaso)) +"'", null);
            mVisitaFallidaCaso.setCodigoParticipanteCaso(caso);
        }
        if (!cursor.isClosed()) cursor.close();
        return mVisitaFallidaCaso;
    }
    //Obtener una lista de VisitaFallidaCaso de la base de datos
    public List<VisitaFallidaCaso> getVisitaFallidaCasos(String filtro, String orden) throws SQLException {
        List<VisitaFallidaCaso> mVisitaFallidaCasos = new ArrayList<VisitaFallidaCaso>();
        Cursor cursor = crearCursor(CasosDBConstants.VISITAS_FALLIDAS_CASOS_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mVisitaFallidaCasos.clear();
            do{
            	VisitaFallidaCaso mVisitaFallidaCaso = null;
                mVisitaFallidaCaso = VisitaFallidaCasoHelper.crearVisitaFallidaCaso(cursor);
                ParticipanteCohorteFamiliaCaso caso = this.getParticipanteCohorteFamiliaCaso(CasosDBConstants.codigoCasoParticipante + "='" +cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoParticipanteCaso)) +"'", null);
                mVisitaFallidaCaso.setCodigoParticipanteCaso(caso);
                mVisitaFallidaCasos.add(mVisitaFallidaCaso);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mVisitaFallidaCasos;
    }
    
    
    //Crear nuevo VisitaSeguimientoCasoSintomas en la base de datos
    public void crearVisitaSeguimientoCasoSintomas(VisitaSeguimientoCasoSintomas visitaSintomas) throws Exception {
        ContentValues cv = VisitaSeguimientoCasoSintomasHelper.crearVisitaSeguimientoCasoSintomasContentValues(visitaSintomas);
        mDb.insertOrThrow(CasosDBConstants.SINTOMAS_CASOS_TABLE, null, cv);
    }

    //Editar VisitaSeguimientoCasoSintomas existente en la base de datos
    public boolean editarVisitaSeguimientoCasoSintomas(VisitaSeguimientoCasoSintomas visitaSintomas) throws Exception{
        ContentValues cv = VisitaSeguimientoCasoSintomasHelper.crearVisitaSeguimientoCasoSintomasContentValues(visitaSintomas);
        return mDb.update(CasosDBConstants.SINTOMAS_CASOS_TABLE , cv, CasosDBConstants.codigoCasoSintoma + "='"
                + visitaSintomas.getCodigoCasoSintoma() + "'", null) > 0;
    }
    //Limpiar la tabla de VisitaSeguimientoCasoSintomas de la base de datos
    public boolean borrarVisitaSeguimientoCasoSintomas() {
        return mDb.delete(CasosDBConstants.SINTOMAS_CASOS_TABLE, null, null) > 0;
    }
    
    //Obtener un VisitaSeguimientoCasoSintomas de la base de datos
    public VisitaSeguimientoCasoSintomas getVisitaSeguimientoCasoSintomas(String filtro, String orden) throws SQLException {
    	VisitaSeguimientoCasoSintomas mVisitaSeguimientoCasoSintomas = null;
        Cursor cursor = crearCursor(CasosDBConstants.SINTOMAS_CASOS_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mVisitaSeguimientoCasoSintomas=VisitaSeguimientoCasoSintomasHelper.crearVisitaSeguimientoCasoSintomas(cursor);
            VisitaSeguimientoCaso caso = this.getVisitaSeguimientoCaso(CasosDBConstants.codigoCasoVisita + "='" +cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoCasoVisita)) +"'", null);
            mVisitaSeguimientoCasoSintomas.setCodigoVisitaCaso(caso);
        }
        if (!cursor.isClosed()) cursor.close();
        return mVisitaSeguimientoCasoSintomas;
    }
    //Obtener una lista de VisitaSeguimientoCasoSintomas de la base de datos
    public List<VisitaSeguimientoCasoSintomas> getVisitaSeguimientoCasosSintomas(String filtro, String orden) throws SQLException {
        List<VisitaSeguimientoCasoSintomas> mVisitaSeguimientoCasosSintomas = new ArrayList<VisitaSeguimientoCasoSintomas>();
        Cursor cursor = crearCursor(CasosDBConstants.SINTOMAS_CASOS_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mVisitaSeguimientoCasosSintomas.clear();
            do{
            	VisitaSeguimientoCasoSintomas mVisitaSeguimientoCasoSintomas = null;
                mVisitaSeguimientoCasoSintomas = VisitaSeguimientoCasoSintomasHelper.crearVisitaSeguimientoCasoSintomas(cursor);
                VisitaSeguimientoCaso caso = this.getVisitaSeguimientoCaso(CasosDBConstants.codigoCasoVisita + "='" +cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoCasoVisita)) +"'", null);
                mVisitaSeguimientoCasoSintomas.setCodigoVisitaCaso(caso);
                mVisitaSeguimientoCasosSintomas.add(mVisitaSeguimientoCasoSintomas);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mVisitaSeguimientoCasosSintomas;
    }
    
    
    //Crear nuevo FormularioContactoCaso en la base de datos
    public void crearFormularioContactoCaso(FormularioContactoCaso contacto) throws Exception {
        ContentValues cv = FormularioContactoCasoHelper.crearFormularioContactoCasoContentValues(contacto);
        mDb.insertOrThrow(CasosDBConstants.CONTACTOS_CASOS_TABLE, null, cv);
    }

    //Editar FormularioContactoCaso existente en la base de datos
    public boolean editarFormularioContactoCaso(FormularioContactoCaso contacto) throws Exception{
        ContentValues cv = FormularioContactoCasoHelper.crearFormularioContactoCasoContentValues(contacto);
        return mDb.update(CasosDBConstants.CONTACTOS_CASOS_TABLE , cv, CasosDBConstants.codigoCasoContacto + "='"
                + contacto.getCodigoCasoContacto() + "'", null) > 0;
    }
    //Limpiar la tabla de FormularioContactoCaso de la base de datos
    public boolean borrarFormularioContactoCaso() {
        return mDb.delete(CasosDBConstants.CONTACTOS_CASOS_TABLE, null, null) > 0;
    }
    
    //Obtener un FormularioContactoCaso de la base de datos
    public FormularioContactoCaso getFormularioContactoCaso(String filtro, String orden) throws SQLException {
    	FormularioContactoCaso mFormularioContactoCaso = null;
        Cursor cursor = crearCursor(CasosDBConstants.CONTACTOS_CASOS_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mFormularioContactoCaso=FormularioContactoCasoHelper.crearFormularioContactoCaso(cursor);
            VisitaSeguimientoCaso caso = this.getVisitaSeguimientoCaso(CasosDBConstants.codigoCasoVisita + "='" +cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoVisitaCaso)) +"'", null);
            mFormularioContactoCaso.setCodigoVisitaCaso(caso);
            ParticipanteCohorteFamilia participante = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" +cursor.getInt(cursor.getColumnIndex(CasosDBConstants.partContacto)), null);
            mFormularioContactoCaso.setPartContacto(participante);
        }
        if (!cursor.isClosed()) cursor.close();
        return mFormularioContactoCaso;
    }
    //Obtener una lista de FormularioContactoCaso de la base de datos
    public List<FormularioContactoCaso> getFormularioContactoCasos(String filtro, String orden) throws SQLException {
        List<FormularioContactoCaso> mFormularioContactoCasos = new ArrayList<FormularioContactoCaso>();
        Cursor cursor = crearCursor(CasosDBConstants.CONTACTOS_CASOS_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mFormularioContactoCasos.clear();
            do{
            	FormularioContactoCaso mFormularioContactoCaso = null;
                mFormularioContactoCaso = FormularioContactoCasoHelper.crearFormularioContactoCaso(cursor);
                VisitaSeguimientoCaso caso = this.getVisitaSeguimientoCaso(CasosDBConstants.codigoCasoVisita + "='" +cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoVisitaCaso)) +"'", null);
                mFormularioContactoCaso.setCodigoVisitaCaso(caso);
                ParticipanteCohorteFamilia participante = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" +cursor.getInt(cursor.getColumnIndex(CasosDBConstants.partContacto)), null);
                mFormularioContactoCaso.setPartContacto(participante);
                mFormularioContactoCasos.add(mFormularioContactoCaso);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mFormularioContactoCasos;
    }
    
    //Crear nuevo VisitaFinalCaso en la base de datos
    public void crearVisitaFinalCaso(VisitaFinalCaso visitaFinal) throws Exception {
        ContentValues cv = VisitaFinalCasoHelper.crearVisitaFinalCasoContentValues(visitaFinal);
        mDb.insertOrThrow(CasosDBConstants.VISITAS_FINALES_CASOS_TABLE, null, cv);
    }

    //Editar VisitaFinalCaso existente en la base de datos
    public boolean editarVisitaFinalCaso(VisitaFinalCaso visitaFinal) throws Exception{
        ContentValues cv = VisitaFinalCasoHelper.crearVisitaFinalCasoContentValues(visitaFinal);
        return mDb.update(CasosDBConstants.VISITAS_FINALES_CASOS_TABLE , cv, CasosDBConstants.codigoParticipanteCaso + "='"
                + visitaFinal.getCodigoParticipanteCaso().getCodigoCasoParticipante() + "'", null) > 0;
    }
    //Limpiar la tabla de VisitaFinalCaso de la base de datos
    public boolean borrarVisitaFinalCaso() {
        return mDb.delete(CasosDBConstants.VISITAS_FINALES_CASOS_TABLE, null, null) > 0;
    }
    
    //Obtener un VisitaFinalCaso de la base de datos
    public VisitaFinalCaso getVisitaFinalCaso(String filtro, String orden) throws SQLException {
    	VisitaFinalCaso mVisitaFinalCaso = null;
        Cursor cursor = crearCursor(CasosDBConstants.VISITAS_FINALES_CASOS_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mVisitaFinalCaso=VisitaFinalCasoHelper.crearVisitaFinalCaso(cursor);
            ParticipanteCohorteFamiliaCaso caso = this.getParticipanteCohorteFamiliaCaso(CasosDBConstants.codigoCasoParticipante + "='" +cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoParticipanteCaso)) +"'", null);
            mVisitaFinalCaso.setCodigoParticipanteCaso(caso);
        }
        if (!cursor.isClosed()) cursor.close();
        return mVisitaFinalCaso;
    }
    //Obtener una lista de VisitaFinalCaso de la base de datos
    public List<VisitaFinalCaso> getVisitaFinalCasos(String filtro, String orden) throws SQLException {
        List<VisitaFinalCaso> mVisitaFinalCasos = new ArrayList<VisitaFinalCaso>();
        Cursor cursor = crearCursor(CasosDBConstants.VISITAS_FINALES_CASOS_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mVisitaFinalCasos.clear();
            do{
            	VisitaFinalCaso mVisitaFinalCaso = null;
                mVisitaFinalCaso = VisitaFinalCasoHelper.crearVisitaFinalCaso(cursor);
                ParticipanteCohorteFamiliaCaso caso = this.getParticipanteCohorteFamiliaCaso(CasosDBConstants.codigoCasoParticipante + "='" +cursor.getString(cursor.getColumnIndex(CasosDBConstants.codigoParticipanteCaso)) +"'", null);
                mVisitaFinalCaso.setCodigoParticipanteCaso(caso);
                mVisitaFinalCasos.add(mVisitaFinalCaso);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mVisitaFinalCasos;
    }
    
    public Boolean verificarData() throws SQLException{
		Cursor c = null;
		c = crearCursor(MainDBConstants.CASA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.CASA_CHF_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.PARTICIPANTE_CHF_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		/*c = crearCursor(EncuestasDBConstants.ENCUESTA_CASA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();*/
		c = crearCursor(MainDBConstants.AREA_AMBIENTE_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.CUARTO_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.CAMA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.PERSONACAMA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		/*c = crearCursor(EncuestasDBConstants.ENCUESTA_PARTICIPANTE_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(EncuestasDBConstants.ENCUESTA_PARTOBB_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(EncuestasDBConstants.ENCUESTA_PESOTALLA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(EncuestasDBConstants.ENCUESTA_LACTANCIAMAT_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		*/

		c = crearCursor(MuestrasDBConstants.MUESTRA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.VISITA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(MainDBConstants.TELEFONO_CONTACTO_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(SeroprevalenciaDBConstants.PARTICIPANTESA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(SeroprevalenciaDBConstants.ENCUESTA_CASASA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(SeroprevalenciaDBConstants.ENCUESTA_PARTICIPANTESA_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(CasosDBConstants.CASAS_CASOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(CasosDBConstants.PARTICIPANTES_CASOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(CasosDBConstants.VISITAS_CASOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(CasosDBConstants.VISITAS_FALLIDAS_CASOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(CasosDBConstants.SINTOMAS_CASOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(CasosDBConstants.CONTACTOS_CASOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(CasosDBConstants.VISITAS_FINALES_CASOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
        //MA 2018
        c = crearCursor(ConstantsDB.ENC_CASA_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.ENC_PART_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.MUESTRA_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.PART_PROCESOS_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.LACT_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.PT_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.OB_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.VIS_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.BHC_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.SERO_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.PIN_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.NO_DATA_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.TPBMC_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.TRB_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.ENC_SAT_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.CAMB_CASA_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.NEWVAC_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.DATOSPARTOBB_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.DOCS_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(ConstantsDB.DAT_VIS_TABLE, ConstantsDB.STATUS + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        //reconsentimiento dengue 2018
        c = crearCursor(MainDBConstants.CONTACTO_PARTICIPANTE_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.DATOS_COORDENADAS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.VISITAPART_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.OBSEQUIOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MuestrasDBConstants.MUESTRA_SUPERFICIE_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.CARTA_CONSENTIMIENTO_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
        c = crearCursor(MainDBConstants.TAMIZAJE_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
        if (c != null && c.getCount()>0) {c.close();return true;}
        c.close();
		c = crearCursor(InfluenzaUO1DBConstants.UO1_VISITAS_CASOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(InfluenzaUO1DBConstants.UO1_VISITAS_VACUNAS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(InfluenzaUO1DBConstants.UO1_SINTOMAS_VISITA_CASO_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(Covid19DBConstants.PARTICIPANTE_COVID_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(Covid19DBConstants.COVID_CASOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(Covid19DBConstants.COVID_PARTICIPANTES_CASOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(Covid19DBConstants.COVID_VISITAS_CASOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(Covid19DBConstants.COVID_VISITAS_FALLIDAS_CASOS_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(Covid19DBConstants.COVID_CANDIDATO_TRANSMISION_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(Covid19DBConstants.COVID_SINTOMAS_VISITA_CASO_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(Covid19DBConstants.COVID_CUESTIONARIO_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();

		return false;
	}

	public Boolean verificarDataEnto() throws SQLException{
		Cursor c = null;
		c = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_POB_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		c = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE, MainDBConstants.estado + "='"  + Constants.STATUS_NOT_SUBMITTED+ "'", null, null);
		if (c != null && c.getCount()>0) {c.close();return true;}
		c.close();
		return false;
	}

	public Boolean verificarProcesosPendientesEnvio() throws SQLException {
		Cursor c = null;
		c = crearCursor(ConstantsDB.PART_PROCESOS_TABLE, MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'", null, null);
		if (c != null && c.getCount() > 0) {
			c.close();
			return true;
		}
		c.close();
		return false;
	}

    /****************************************************************************
     * MUESTREO ANUAL
     */
    /**METODOS PARA CASAS**/
    /**
     * Busca una casa de la base de datos
     *
     * @return casa
     */

    public Cursor buscarCasa(Integer cod_casa) throws SQLException {
        Cursor c = null;
        c = mDb.query(true, MainDBConstants.CASA_TABLE, null,
                MainDBConstants.codigo + "=" + cod_casa, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**METODOS PARA PARTICIPANTES**/
    /**
     * Busca un participante de la base de datos
     *
     * @return participante
     */

    public Cursor buscarParticipante(Integer codigo) throws SQLException {
        Cursor c = null;
        c = mDb.query(true, MainDBConstants.PARTICIPANTE_TABLE, null,
                MainDBConstants.codigo + "=" + codigo, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**METODOS PARA ENCUESTAS CASA**/

    /**
     * Inserta una encuesta de casa en la base de datos
     *
     * @param enccasa casa
     *            Objeto EncuestaCasa que contiene la informacion
     *
     */
    public void crearEncuestaCasa(ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa enccasa) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.codigo, enccasa.getCodigo());
        cv.put(ConstantsDB.COD_CASA, enccasa.getCodCasa());
        cv.put(ConstantsDB.codigoCHF, enccasa.getCodCasaChf());
        cv.put(ConstantsDB.FECHA_ENC_CASA, enccasa.getFechaEncCasa().getTime());
        cv.put(ConstantsDB.CVIVEN1, enccasa.getCvivencasa1());
        cv.put(ConstantsDB.CVIVEN2, enccasa.getCvivencasa2());
        cv.put(ConstantsDB.CVIVEN3, enccasa.getCvivencasa3());
        cv.put(ConstantsDB.CVIVEN4, enccasa.getCvivencasa4());
        cv.put(ConstantsDB.CVIVEN5, enccasa.getCvivencasa5());
        cv.put(ConstantsDB.CVIVEN6, enccasa.getCvivencasa6());
        cv.put(ConstantsDB.CVIVEN7, enccasa.getCvivencasa7());
        cv.put(ConstantsDB.CCUARTOS, enccasa.getCcuartos());
        cv.put(ConstantsDB.GRIFO, enccasa.getGrifo());
        cv.put(ConstantsDB.GRIFOCOM, enccasa.getGrifoComSN());
        cv.put(ConstantsDB.HORASAGUA, enccasa.gethorasagua());
        cv.put(ConstantsDB.MCASA, enccasa.getMcasa());
        cv.put(ConstantsDB.OCASA, enccasa.getOcasa());
        cv.put(ConstantsDB.PISO, enccasa.getPiso());
        cv.put(ConstantsDB.OPISO, enccasa.getOpiso());
        cv.put(ConstantsDB.TECHO, enccasa.getTecho());
        cv.put(ConstantsDB.OTECHO, enccasa.getOtecho());
        cv.put(ConstantsDB.CPROPIA, enccasa.getCpropia());
        cv.put(ConstantsDB.ABANICOS, enccasa.getCabanicos());
        cv.put(ConstantsDB.TVS, enccasa.getCtelevisores());
        cv.put(ConstantsDB.REFRI, enccasa.getCrefrigeradores());
        cv.put(ConstantsDB.MOTO, enccasa.getMoto());
        cv.put(ConstantsDB.CARRO, enccasa.getCarro());
        cv.put(ConstantsDB.LENA, enccasa.getCocinalena());
        cv.put(ConstantsDB.ANIMALES, enccasa.getAnimalesSN());
        cv.put(ConstantsDB.POLLOS, enccasa.getPollos());
        cv.put(ConstantsDB.POLLOSCASA, enccasa.getPolloscasa());
        cv.put(ConstantsDB.PATOS, enccasa.getPatos());
        cv.put(ConstantsDB.PATOSCASA, enccasa.getPatoscasa());
        cv.put(ConstantsDB.PERROS, enccasa.getPerros());
        cv.put(ConstantsDB.PERROSCASA, enccasa.getPerroscasa());
        cv.put(ConstantsDB.GATOS, enccasa.getGatos());
        cv.put(ConstantsDB.GATOSCASA, enccasa.getGatoscasa());
        cv.put(ConstantsDB.CERDOS, enccasa.getCerdos());
        cv.put(ConstantsDB.CERDOSCASA, enccasa.getCerdoscasa());
        cv.put(ConstantsDB.otrorecurso1, enccasa.getOtrorecurso1());
        cv.put(ConstantsDB.otrorecurso2, enccasa.getOtrorecurso2());
        //CHF + NUEVAS PREGUNTAS MA2018
        cv.put(ConstantsDB.viveEmbEnCasa, enccasa.getViveEmbEnCasa());
        cv.put(ConstantsDB.CANTIDAD_CUARTOS, enccasa.getCantidadCuartos());
        cv.put(ConstantsDB.ALMACENA_AGUA, enccasa.getAlmacenaAgua());
        cv.put(ConstantsDB.ALMACENA_EN_BARRILES, enccasa.getAlmacenaEnBarriles());
        cv.put(ConstantsDB.NUMERO_BARRILES, enccasa.getNumeroBarriles());
        cv.put(ConstantsDB.BARRILES_TAPADOS, enccasa.getBarrilesTapados());
        cv.put(ConstantsDB.BARRILES_CON_ABATE, enccasa.getBarrilesConAbate());
        cv.put(ConstantsDB.ALMACENA_EN_TANQUES, enccasa.getAlmacenaEnTanques());
        cv.put(ConstantsDB.NUMERO_TANQUES, enccasa.getNumeroTanques());
        cv.put(ConstantsDB.TANQUES_TAPADOS, enccasa.getTanquesTapados());
        cv.put(ConstantsDB.TANQUES_CON_ABATE, enccasa.getTanquesConAbate());
        cv.put(ConstantsDB.ALMACENA_EN_PILAS, enccasa.getAlmacenaEnPilas());
        cv.put(ConstantsDB.NUMERO_PILAS, enccasa.getNumeroPilas());
        cv.put(ConstantsDB.PILAS_TAPADAS, enccasa.getPilasTapadas());
        cv.put(ConstantsDB.PILAS_CON_ABATE, enccasa.getPilasConAbate());
        cv.put(ConstantsDB.ALMACENA_EN_OTROSRECIP, enccasa.getAlmacenaEnOtrosrecip());
        cv.put(ConstantsDB.DESC_OTROS_RECIPIENTES, enccasa.getDescOtrosRecipientes());
        cv.put(ConstantsDB.NUMERO_OTROS_RECIPIENTES, enccasa.getNumeroOtrosRecipientes());
        cv.put(ConstantsDB.OTROS_RECIP_TAPADOS, enccasa.getOtrosRecipTapados());
        cv.put(ConstantsDB.OTROSRECIP_CON_ABATE, enccasa.getOtrosrecipConAbate());
        cv.put(ConstantsDB.UBICACION_LAVANDERO, enccasa.getUbicacionLavandero());
        cv.put(ConstantsDB.TIENE_ABANICO, enccasa.getTieneAbanico());
        cv.put(ConstantsDB.TIENE_TELEVISOR, enccasa.getTieneTelevisor());
        cv.put(ConstantsDB.TIENE_REFRIGERADOR_FREEZER, enccasa.getTieneRefrigeradorFreezer());
        cv.put(ConstantsDB.TIENE_AIRE_ACONDICIONADO, enccasa.getTieneAireAcondicionado());
        cv.put(ConstantsDB.FUNCIONAMIENTO_AIRE, enccasa.getFuncionamientoAire());
        cv.put(ConstantsDB.opcFabCarro, enccasa.getOpcFabCarro());
        cv.put(ConstantsDB.yearNow, enccasa.getYearNow());
        cv.put(ConstantsDB.yearFabCarro, enccasa.getYearFabCarro());
        cv.put(ConstantsDB.TIENE_MICROBUS, enccasa.getTieneMicrobus());
        cv.put(ConstantsDB.TIENE_CAMIONETA, enccasa.getTieneCamioneta());
        cv.put(ConstantsDB.TIENE_CAMION, enccasa.getTieneCamion());
        cv.put(ConstantsDB.TIENE_OTRO_MEDIO_TRANS, enccasa.getTieneOtroMedioTrans());
        cv.put(ConstantsDB.DESC_OTRO_MEDIO_TRANS, enccasa.getDescOtroMedioTrans());
        cv.put(ConstantsDB.COCINA_CON_LENIA, enccasa.getCocinaConLenia());
        cv.put(ConstantsDB.UBICACION_COCINA_LENIA, enccasa.getUbicacionCocinaLenia());
        cv.put(ConstantsDB.PERIODICIDAD_COCINA_LENIA, enccasa.getPeriodicidadCocinaLenia());
        cv.put(ConstantsDB.NUM_DIARIO_COCINA_LENIA, enccasa.getNumDiarioCocinaLenia());
        cv.put(ConstantsDB.NUM_SEMANAL_COCINA_LENIA, enccasa.getNumSemanalCocinaLenia());
        cv.put(ConstantsDB.NUM_QUINCENAL_COCINA_LENIA, enccasa.getNumQuincenalCocinaLenia());
        cv.put(ConstantsDB.NUM_MENSUAL_COCINA_LENIA, enccasa.getNumMensualCocinaLenia());
        cv.put(ConstantsDB.TIENE_GALLINAS, enccasa.getTieneGallinas());
        cv.put(ConstantsDB.TIENE_PATOS, enccasa.getTienePatos());
        cv.put(ConstantsDB.TIENE_PERROS, enccasa.getTienePerros());
        cv.put(ConstantsDB.TIENE_GATOS, enccasa.getTieneGatos());
        cv.put(ConstantsDB.TIENE_CERDOS, enccasa.getTieneCerdos());
        cv.put(ConstantsDB.PERS_FUMA_DENTRO_CASA, enccasa.getPersFumaDentroCasa());
        cv.put(ConstantsDB.MADRE_FUMA, enccasa.getMadreFuma());
        cv.put(ConstantsDB.CANT_CIGARRILLOS_MADRE, enccasa.getCantCigarrillosMadre());
        cv.put(ConstantsDB.PADRE_FUMA, enccasa.getPadreFuma());
        cv.put(ConstantsDB.CANT_CIGARRILLOS_PADRE, enccasa.getCantCigarrillosPadre());
        cv.put(ConstantsDB.OTROS_FUMAN, enccasa.getOtrosFuman());
        cv.put(ConstantsDB.CANT_OTROS_FUMAN, enccasa.getCantOtrosFuman());
        cv.put(ConstantsDB.CANT_CIGARRILLOS_OTROS, enccasa.getCantCigarrillosOtros());
        cv.put(ConstantsDB.servRecolBasura, enccasa.getServRecolBasura());
        cv.put(ConstantsDB.frecServRecolBasura, enccasa.getFrecServRecolBasura());
        cv.put(ConstantsDB.llantasOtrosContConAgua, enccasa.getLlantasOtrosContConAgua());
        cv.put(ConstantsDB.opcFabMicrobus, enccasa.getOpcFabMicrobus());
        cv.put(ConstantsDB.yearFabMicrobus, enccasa.getYearFabMicrobus());
        cv.put(ConstantsDB.opcFabCamioneta, enccasa.getOpcFabCamioneta());
        cv.put(ConstantsDB.yearFabCamioneta, enccasa.getYearFabCamioneta());
        cv.put(ConstantsDB.opcFabCamion, enccasa.getOpcFabCamion());
        cv.put(ConstantsDB.yearFabCamion, enccasa.getYearFabCamion());
        cv.put(ConstantsDB.opcFabOtroMedioTrans, enccasa.getOpcFabOtroMedioTrans());
        cv.put(ConstantsDB.yearFabOtroMedioTrans, enccasa.getYearFabOtroMedioTrans());
        //MA2020
		cv.put(ConstantsDB.cambiadoCasa, enccasa.getCambiadoCasa());
		cv.put(ConstantsDB.remodelacionCasa, enccasa.getRemodelacionCasa());
		cv.put(ConstantsDB.tieneVehiculo, enccasa.getTieneVehiculo());
		cv.put(ConstantsDB.participante, enccasa.getParticipante());

        cv.put(ConstantsDB.ID_INSTANCIA, enccasa.getMovilInfo().getIdInstancia());
        cv.put(ConstantsDB.FILE_PATH, enccasa.getMovilInfo().getInstancePath());
        cv.put(ConstantsDB.STATUS, enccasa.getMovilInfo().getEstado());
        cv.put(ConstantsDB.WHEN_UPDATED, enccasa.getMovilInfo().getUltimoCambio());
        cv.put(ConstantsDB.START, enccasa.getMovilInfo().getStart());
        cv.put(ConstantsDB.END, enccasa.getMovilInfo().getEnd());
        cv.put(ConstantsDB.DEVICE_ID, enccasa.getMovilInfo().getDeviceid());
        cv.put(ConstantsDB.SIM_SERIAL, enccasa.getMovilInfo().getSimserial());
        cv.put(ConstantsDB.PHONE_NUMBER, enccasa.getMovilInfo().getPhonenumber());
        cv.put(ConstantsDB.TODAY, enccasa.getMovilInfo().getToday().getTime());
        cv.put(ConstantsDB.USUARIO, enccasa.getMovilInfo().getUsername());
        cv.put(ConstantsDB.DELETED, enccasa.getMovilInfo().getEliminado());
        cv.put(ConstantsDB.REC1, enccasa.getMovilInfo().getRecurso1());
        cv.put(ConstantsDB.REC2, enccasa.getMovilInfo().getRecurso2());
        mDb.insertOrThrow(ConstantsDB.ENC_CASA_TABLE, null, cv);
    }

    /**
     * Borra todas las encuestas de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTodasEncCasas() {
        return mDb.delete(ConstantsDB.ENC_CASA_TABLE, null, null) > 0;
    }

    /**METODOS PARA ENCUESTAS PARTICIPANTES**/

    /**
     * Inserta una encuesta de participante en la base de datos
     *
     * @param encpar participante
     *            Objeto EncuestaParticipante que contiene la informacion
     *
     */
    public void crearEncuestaParticipante(ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante encpar) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.CODIGO, encpar.getEpId().getCodigo());
        cv.put(ConstantsDB.FECHA_ENC_PAR, encpar.getEpId().getFechaEncPar().getTime());
        cv.put(ConstantsDB.FIEBRE, encpar.getFiebre());
        cv.put(ConstantsDB.TFIEBRE, encpar.getTiemFieb());
        cv.put(ConstantsDB.LUGCONS, encpar.getLugarCons());
        cv.put(ConstantsDB.NOCS, encpar.getNoCs());
        cv.put(ConstantsDB.AUTOMED, encpar.getAutomed());
        cv.put(ConstantsDB.ESC, encpar.getEscuela());
        cv.put(ConstantsDB.GRADO, encpar.getGrado());
        cv.put(ConstantsDB.TURNO, encpar.getTurno());
        cv.put(ConstantsDB.NESC, encpar.getnEscuela());
        cv.put(ConstantsDB.OESC, encpar.getOtraEscuela());
        cv.put(ConstantsDB.CUIDAN, encpar.getCuidan());
        cv.put(ConstantsDB.CCUIDAN, encpar.getCuantosCuidan());
        cv.put(ConstantsDB.CQVIVE, encpar.getCqVive());
        cv.put(ConstantsDB.LUGPARTO, encpar.getLugarPart());
        cv.put(ConstantsDB.PAPAALF, encpar.getPapaAlf());
        cv.put(ConstantsDB.PAPANIVEL, encpar.getPapaNivel());
        cv.put(ConstantsDB.PAPATRA, encpar.getPapaTra());
        cv.put(ConstantsDB.PAPATIPOT, encpar.getPapaTipoTra());
        cv.put(ConstantsDB.MAMAALF, encpar.getMamaAlf());
        cv.put(ConstantsDB.MAMANIVEL, encpar.getMamaNivel());
        cv.put(ConstantsDB.MAMATRA, encpar.getMamaTra());
        cv.put(ConstantsDB.MAMATIPOT, encpar.getMamaTipoTra());
        cv.put(ConstantsDB.COMPARTEHAB, encpar.getComparteHab());
        cv.put(ConstantsDB.HAB1, encpar.getHab1());
        cv.put(ConstantsDB.HAB2, encpar.getHab2());
        cv.put(ConstantsDB.HAB3, encpar.getHab3());
        cv.put(ConstantsDB.HAB4, encpar.getHab4());
        cv.put(ConstantsDB.HAB5, encpar.getHab5());
        cv.put(ConstantsDB.HAB6, encpar.getHab6());
        cv.put(ConstantsDB.COMPARTECAMA, encpar.getComparteCama());
        cv.put(ConstantsDB.CAMA1, encpar.getCama1());
        cv.put(ConstantsDB.CAMA2, encpar.getCama2());
        cv.put(ConstantsDB.CAMA3, encpar.getCama3());
        cv.put(ConstantsDB.CAMA4, encpar.getCama4());
        cv.put(ConstantsDB.CAMA5, encpar.getCama5());
        cv.put(ConstantsDB.CAMA6, encpar.getCama6());
        cv.put(ConstantsDB.ASMA, encpar.getAsma());
        cv.put(ConstantsDB.SILB12, encpar.getSilb12m());
        cv.put(ConstantsDB.SIT1, encpar.getSitResf());
        cv.put(ConstantsDB.SIT2, encpar.getSitEjer());
        cv.put(ConstantsDB.SILB01, encpar.getSilbMesPas());
        cv.put(ConstantsDB.DIFHAB, encpar.getDifHablar());
        cv.put(ConstantsDB.VECDIFHAB, encpar.getVecHablar());
        cv.put(ConstantsDB.DIFDOR, encpar.getDifDormir());
        cv.put(ConstantsDB.SUENOPER, encpar.getSuenoPer());
        cv.put(ConstantsDB.TOS12, encpar.getTos12m());
        cv.put(ConstantsDB.VECESTOS, encpar.getVecesTos());
        cv.put(ConstantsDB.TOS3DIAS, encpar.getTos3Dias());
        cv.put(ConstantsDB.HOSP12M, encpar.getHosp12m());
        cv.put(ConstantsDB.MED12M, encpar.getMed12m());
        cv.put(ConstantsDB.DEP12M, encpar.getDep12m());
        cv.put(ConstantsDB.CRISIS, encpar.getCrisis());
        cv.put(ConstantsDB.FRECASMA, encpar.getFrecAsma());
        cv.put(ConstantsDB.FUMA, encpar.getFumaSN());
        cv.put(ConstantsDB.QUIENFUMA, encpar.getQuienFuma());
        cv.put(ConstantsDB.CIGMADRE, encpar.getCantCigarrosMadre());
        cv.put(ConstantsDB.CIGOTRO, encpar.getCantCigarrosOtros());
        cv.put(ConstantsDB.CIGPADRE, encpar.getCantCigarrosPadre());

        cv.put(ConstantsDB.rash, encpar.getRash());
        cv.put(ConstantsDB.mesActual, encpar.getMesActual());
        cv.put(ConstantsDB.yearActual, encpar.getYearActual());
        cv.put(ConstantsDB.rash_year, encpar.getRash_year());
        cv.put(ConstantsDB.rash_mes, encpar.getRash_mes());
        cv.put(ConstantsDB.rash_mesact, encpar.getRash_mesact());
        cv.put(ConstantsDB.rashCara, encpar.getRashCara());
        cv.put(ConstantsDB.rashMiembrosSup, encpar.getRashMiembrosSup());
        cv.put(ConstantsDB.rashTorax, encpar.getRashTorax());
        cv.put(ConstantsDB.rashAbdomen, encpar.getRashAbdomen());
        cv.put(ConstantsDB.rashMiembrosInf, encpar.getRashMiembrosInf());
        cv.put(ConstantsDB.rashDias, encpar.getRashDias());
        cv.put(ConstantsDB.ojoRojo, encpar.getOjoRojo());
        cv.put(ConstantsDB.ojoRojo_year, encpar.getOjoRojo_year());
        cv.put(ConstantsDB.ojoRojo_mes, encpar.getOjoRojo_mes());
        cv.put(ConstantsDB.ojoRojo_mesact, encpar.getOjoRojo_mesact());
        cv.put(ConstantsDB.ojoRojo_Dias, encpar.getOjoRojo_Dias());
        cv.put(ConstantsDB.hormigueo, encpar.getHormigueo());
        cv.put(ConstantsDB.hormigueo_year, encpar.getHormigueo_year());
        cv.put(ConstantsDB.hormigueo_mes, encpar.getHormigueo_mes());
        cv.put(ConstantsDB.hormigueo_mesact, encpar.getHormigueo_mesact());
        cv.put(ConstantsDB.hormigueo_Dias, encpar.getHormigueo_Dias());
        cv.put(ConstantsDB.consultaRashHormigueo, encpar.getConsultaRashHormigueo());
        cv.put(ConstantsDB.uSaludRashHormigueo, encpar.getuSaludRashHormigueo());
        cv.put(ConstantsDB.cSaludRashHormigueo, encpar.getcSaludRashHormigueo());
        cv.put(ConstantsDB.oCSRashHormigueo, encpar.getoCSRashHormigueo());
        cv.put(ConstantsDB.pSRashHormigueo, encpar.getpSRashHormigueo());
        cv.put(ConstantsDB.oPSRashHormigueo, encpar.getoPSRashHormigueo());
        cv.put(ConstantsDB.diagRashHormigueo, encpar.getDiagRashHormigueo());
        cv.put(ConstantsDB.chPapaMama, encpar.getChPapaMama());
        if (encpar.getFechana_papa() != null){
            cv.put(ConstantsDB.fechana_papa, encpar.getFechana_papa().getTime());
        }
        cv.put(ConstantsDB.cal_edad_papa, encpar.getCal_edad_papa());
        cv.put(ConstantsDB.cal_edad2_papa, encpar.getCal_edad2_papa());
        cv.put(ConstantsDB.nombpapa1, encpar.getNombpapa1());
        cv.put(ConstantsDB.nombpapa2, encpar.getNombpapa2());
        cv.put(ConstantsDB.apellipapa1, encpar.getApellipapa1());
        cv.put(ConstantsDB.apellipapa2, encpar.getApellipapa2());
        if (encpar.getFechana_mama() != null){
            cv.put(ConstantsDB.fechana_mama, encpar.getFechana_mama().getTime());
        }
        cv.put(ConstantsDB.cal_edad_mama, encpar.getCal_edad_mama());
        cv.put(ConstantsDB.cal_edad2_mama, encpar.getCal_edad2_mama());
        cv.put(ConstantsDB.nombmama1, encpar.getNombmama1());
        cv.put(ConstantsDB.nombmama2, encpar.getNombmama2());
        cv.put(ConstantsDB.apellimama1, encpar.getApellimama1());
        cv.put(ConstantsDB.apellimama2, encpar.getApellimama2());
        cv.put(ConstantsDB.otrorecurso1, encpar.getOtrorecurso1());
        cv.put(ConstantsDB.otrorecurso2, encpar.getOtrorecurso2());

        //CHF + NUEVAS PREGUNTAS MA2018
        cv.put(ConstantsDB.EMANCIPADO, encpar.getEmancipado());
        cv.put(ConstantsDB.descEnmancipado, encpar.getDescEnmancipado());
        cv.put(ConstantsDB.otraDescEmanc, encpar.getOtraDescEmanc());
        cv.put(ConstantsDB.EMBARAZADA, encpar.getEmbarazada());
        cv.put(ConstantsDB.SEMANAS_EMBARAZO, encpar.getSemanasEmbarazo());
        cv.put(ConstantsDB.ALFABETO, encpar.getAlfabeto());
        cv.put(ConstantsDB.NIVEL_EDUCACION, encpar.getNivelEducacion());
        cv.put(ConstantsDB.TRABAJA, encpar.getTrabaja());
        cv.put(ConstantsDB.TIPO_TRABAJO, encpar.getTipoTrabajo());
        cv.put(ConstantsDB.OCUPACION_ACTUAL, encpar.getOcupacionActual());
        cv.put(ConstantsDB.NINO_ASISTE_ESCUELA, encpar.getNinoAsisteEscuela());
        cv.put(ConstantsDB.GRADO_ESTUDIA_NINO, encpar.getGradoEstudiaNino());
        cv.put(ConstantsDB.NINO_TRABAJA, encpar.getNinoTrabaja());
        cv.put(ConstantsDB.OCUPACION_ACTUAL_NINO, encpar.getOcupacionActualNino());
        cv.put(ConstantsDB.PADRE_ESTUDIO, encpar.getPadreEstudio());
        cv.put(ConstantsDB.CODIGO_PADRE_ESTUDIO, encpar.getCodigoPadreEstudio());
        cv.put(ConstantsDB.PADRE_ALFABETO, encpar.getPadreAlfabeto());
        cv.put(ConstantsDB.TRABAJA_PADRE, encpar.getTrabajaPadre());
        cv.put(ConstantsDB.MADRE_ESTUDIO, encpar.getMadreEstudio());
        cv.put(ConstantsDB.CODIGO_MADRE_ESTUDIO, encpar.getCodigoMadreEstudio());
        cv.put(ConstantsDB.MADRE_ALFABETA, encpar.getMadreAlfabeta());
        cv.put(ConstantsDB.TRABAJA_MADRE, encpar.getTrabajaMadre());
        cv.put(ConstantsDB.FUMACHF, encpar.getFuma());
        cv.put(ConstantsDB.PERIODICIDAD_FUNA, encpar.getPeriodicidadFuna());
        cv.put(ConstantsDB.CANTIDAD_CIGARRILLOS, encpar.getCantidadCigarrillos());
        cv.put(ConstantsDB.FUMA_DENTRO_CASA, encpar.getFumaDentroCasa());
        cv.put(ConstantsDB.TUBERCULOSIS_PULMONAR_ACTUAL, encpar.getTuberculosisPulmonarActual());
        cv.put(ConstantsDB.FECHA_DIAG_TUBPUL_ACTUAL, encpar.getFechaDiagTubpulActual());
        cv.put(ConstantsDB.TRATAMIENTO_TUBPUL_ACTUAL, encpar.getTratamientoTubpulActual());
        cv.put(ConstantsDB.COMPLETO_TRATAMIENTO_TUBPUL_ACTUAL, encpar.getCompletoTratamientoTubpulActual());
        cv.put(ConstantsDB.TUBERCULOSIS_PULMONAR_PASADO, encpar.getTuberculosisPulmonarPasado());
        cv.put(ConstantsDB.FECHA_DIAG_TUBPUL_PASADO_DES, encpar.getFechaDiagTubpulPasadoDes());
        cv.put(ConstantsDB.FECHA_DIAG_TUBPUL_PASADO, encpar.getFechaDiagTubpulPasado());
        cv.put(ConstantsDB.TRATAMIENTO_TUBPUL_PASADO, encpar.getTratamientoTubpulPasado());
        cv.put(ConstantsDB.COMPLETO_TRATAMIENTO_TUBPUL_PAS, encpar.getCompletoTratamientoTubpulPas());
        cv.put(ConstantsDB.ALERGIA_RESPIRATORIA, encpar.getAlergiaRespiratoria());
        cv.put(ConstantsDB.CARDIOPATIA, encpar.getCardiopatia());
        cv.put(ConstantsDB.ENFERM_PULMONAR_OBST_CRONICA, encpar.getEnfermPulmonarObstCronica());
        cv.put(ConstantsDB.DIABETES, encpar.getDiabetes());
        cv.put(ConstantsDB.PRESION_ALTA, encpar.getPresionAlta());
        cv.put(ConstantsDB.TOS_SIN_FIEBRE_RESFRIADO, encpar.getTosSinFiebreResfriado());
        cv.put(ConstantsDB.CANT_ENFERM_CUADROS_RESP, encpar.getCantEnfermCuadrosResp());
        cv.put(ConstantsDB.OTRAS_ENFERMEDADES, encpar.getOtrasEnfermedades());
        cv.put(ConstantsDB.DESC_OTRAS_ENFERMEDADES, encpar.getDescOtrasEnfermedades());
        cv.put(ConstantsDB.VACUNA_INFLUENZA, encpar.getVacunaInfluenza());
        cv.put(ConstantsDB.ANIO_VACUNA_INFLUENZA, encpar.getAnioVacunaInfluenza());
        cv.put(ConstantsDB.rash6m, encpar.getRash6m());
        cv.put(ConstantsDB.ojoRojo6m, encpar.getOjoRojo6m());
        cv.put(ConstantsDB.estudiosAct, encpar.getEstudiosAct());
        //MA 2019
        cv.put(ConstantsDB.vacunaInfluenzaMes, encpar.getVacunaInfluenzaMes());
        cv.put(ConstantsDB.vacunaInfluenzaCSSF, encpar.getVacunaInfluenzaCSSF());
        cv.put(ConstantsDB.vacunaInfluenzaOtro, encpar.getVacunaInfluenzaOtro());
        cv.put(ConstantsDB.nombreCDI, encpar.getNombreCDI());
        cv.put(ConstantsDB.direccionCDI, encpar.getDireccionCDI());
        //MA2020
		cv.put(ConstantsDB.otroLugarCuidan, encpar.getOtroLugarCuidan());
		cv.put(ConstantsDB.enfermedadCronica, encpar.getEnfermedadCronica());
		cv.put(ConstantsDB.tenidoDengue, encpar.getTenidoDengue());
		cv.put(ConstantsDB.unidadSaludDengue, encpar.getUnidadSaludDengue());
		cv.put(ConstantsDB.centroSaludDengue, encpar.getCentroSaludDengue());
		cv.put(ConstantsDB.otroCentroSaludDengue, encpar.getOtroCentroSaludDengue());
		cv.put(ConstantsDB.puestoSaludDengue, encpar.getPuestoSaludDengue());
		cv.put(ConstantsDB.otroPuestoSaludDengue, encpar.getOtroPuestoSaludDengue());
		cv.put(ConstantsDB.hospitalDengue, encpar.getHospitalDengue());
		cv.put(ConstantsDB.otroHospitalDengue, encpar.getOtroHospitalDengue());
		cv.put(ConstantsDB.hospitalizadoDengue, encpar.getHospitalizadoDengue());
		cv.put(ConstantsDB.ambulatorioDengue, encpar.getAmbulatorioDengue());
		cv.put(ConstantsDB.diagMedicoDengue, encpar.getDiagMedicoDengue());
		cv.put(ConstantsDB.rashUA, encpar.getRashUA());
		cv.put(ConstantsDB.consultaRashUA, encpar.getConsultaRashUA());

        cv.put(ConstantsDB.ID_INSTANCIA, encpar.getMovilInfo().getIdInstancia());
        cv.put(ConstantsDB.FILE_PATH, encpar.getMovilInfo().getInstancePath());
        cv.put(ConstantsDB.STATUS, encpar.getMovilInfo().getEstado());
        cv.put(ConstantsDB.WHEN_UPDATED, encpar.getMovilInfo().getUltimoCambio());
        cv.put(ConstantsDB.START, encpar.getMovilInfo().getStart());
        cv.put(ConstantsDB.END, encpar.getMovilInfo().getEnd());
        cv.put(ConstantsDB.DEVICE_ID, encpar.getMovilInfo().getDeviceid());
        cv.put(ConstantsDB.SIM_SERIAL, encpar.getMovilInfo().getSimserial());
        cv.put(ConstantsDB.PHONE_NUMBER, encpar.getMovilInfo().getPhonenumber());
        cv.put(ConstantsDB.TODAY, encpar.getMovilInfo().getToday().getTime());
        cv.put(ConstantsDB.USUARIO, encpar.getMovilInfo().getUsername());
        cv.put(ConstantsDB.DELETED, encpar.getMovilInfo().getEliminado());
        cv.put(ConstantsDB.REC1, encpar.getMovilInfo().getRecurso1());
        cv.put(ConstantsDB.REC2, encpar.getMovilInfo().getRecurso2());
        mDb.insertOrThrow(ConstantsDB.ENC_PART_TABLE, null, cv);
    }

    /**
     * Borra todas las encuestas de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTodasEncParticipantes() {
        return mDb.delete(ConstantsDB.ENC_PART_TABLE, null, null) > 0;
    }

    /**METODOS PARA ENCUESTAS LACTANCIA**/

    /**
     * Inserta una encuesta de lactancia en la base de datos
     *
     * @param enclac lactancia
     *            Objeto LactanciaMaterna que contiene la informacion
     *
     */
    public void crearLactanciaMaterna(LactanciaMaterna enclac) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.CODIGO, enclac.getLmId().getCodigo());
        cv.put(ConstantsDB.FECHA_ENC_LACT, enclac.getLmId().getFechaEncLM().getTime());
        cv.put(ConstantsDB.EDAD, enclac.getEdad());
        cv.put(ConstantsDB.DIOPECHO, enclac.getDioPecho());
        cv.put(ConstantsDB.TIEMPECHO, enclac.getTiemPecho());
        cv.put(ConstantsDB.MESDIOPECHO, enclac.getMesDioPecho());
        cv.put(ConstantsDB.PECHOEXC, enclac.getPechoExc());
        cv.put(ConstantsDB.PECHOEXCANT, enclac.getPechoExcAntes());
        cv.put(ConstantsDB.TPECHOEXCANT, enclac.getTiempPechoExcAntes());
        cv.put(ConstantsDB.MPECHOEXCANT, enclac.getMestPechoExc());
        cv.put(ConstantsDB.FORMALIM, enclac.getFormAlim());
        cv.put(ConstantsDB.OTRALIM, enclac.getOtraAlim());
        cv.put(ConstantsDB.EDADLIQDP, enclac.getEdadLiqDistPecho());
        cv.put(ConstantsDB.MESLIQDL, enclac.getMesDioLiqDisLeche());
        cv.put(ConstantsDB.EDADLIQDL, enclac.getEdadLiqDistLeche());
        cv.put(ConstantsDB.MESLIQDP, enclac.getMesDioLiqDisPecho());
        cv.put(ConstantsDB.EDADALIMS, enclac.getEdAlimSolidos());
        cv.put(ConstantsDB.MESALIMS, enclac.getMesDioAlimSol());
        cv.put(ConstantsDB.otrorecurso1, enclac.getOtrorecurso1());
        cv.put(ConstantsDB.otrorecurso2, enclac.getOtrorecurso2());
        cv.put(ConstantsDB.ID_INSTANCIA, enclac.getMovilInfo().getIdInstancia());
        cv.put(ConstantsDB.FILE_PATH, enclac.getMovilInfo().getInstancePath());
        cv.put(ConstantsDB.STATUS, enclac.getMovilInfo().getEstado());
        cv.put(ConstantsDB.WHEN_UPDATED, enclac.getMovilInfo().getUltimoCambio());
        cv.put(ConstantsDB.START, enclac.getMovilInfo().getStart());
        cv.put(ConstantsDB.END, enclac.getMovilInfo().getEnd());
        cv.put(ConstantsDB.DEVICE_ID, enclac.getMovilInfo().getDeviceid());
        cv.put(ConstantsDB.SIM_SERIAL, enclac.getMovilInfo().getSimserial());
        cv.put(ConstantsDB.PHONE_NUMBER, enclac.getMovilInfo().getPhonenumber());
        cv.put(ConstantsDB.TODAY, enclac.getMovilInfo().getToday().getTime());
        cv.put(ConstantsDB.USUARIO, enclac.getMovilInfo().getUsername());
        cv.put(ConstantsDB.DELETED, enclac.getMovilInfo().getEliminado());
        cv.put(ConstantsDB.REC1, enclac.getMovilInfo().getRecurso1());
        cv.put(ConstantsDB.REC2, enclac.getMovilInfo().getRecurso2());
        mDb.insertOrThrow(ConstantsDB.LACT_TABLE, null, cv);
    }

    /**
     * Borra todas las encuestas de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTodasLactanciaMaterna() {
        return mDb.delete(ConstantsDB.LACT_TABLE, null, null) > 0;
    }

    /**METODOS PARA PT**/

    /**
     * Inserta un pt en la base de datos
     *
     * @param pt
     *            Objeto PesoyTalla que contiene la informacion
     *
     */
    public void crearPT(PesoyTalla pt) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.CODIGO, pt.getPtId().getCodigo());
        cv.put(ConstantsDB.FECHA_PT, pt.getPtId().getFechaPT().getTime());
        cv.put(ConstantsDB.PESO1, pt.getPeso1());
        cv.put(ConstantsDB.PESO2, pt.getPeso2());
        cv.put(ConstantsDB.PESO3, pt.getPeso3());
        cv.put(ConstantsDB.TALLA1, pt.getTalla1());
        cv.put(ConstantsDB.TALLA2, pt.getTalla2());
        cv.put(ConstantsDB.TALLA3, pt.getTalla3());
        cv.put(ConstantsDB.IMC1, pt.getImc1());
        cv.put(ConstantsDB.IMC2, pt.getImc2());
        cv.put(ConstantsDB.IMC3, pt.getImc3());
        cv.put(ConstantsDB.DIFPESO, pt.getDifPeso());
        cv.put(ConstantsDB.DIFTALLA, pt.getDifTalla());
        cv.put(ConstantsDB.tomoMedidaSn, pt.getTomoMedidaSn());
        cv.put(ConstantsDB.razonNoTomoMedidas, pt.getRazonNoTomoMedidas());
		cv.put(ConstantsDB.estudiosAct, pt.getEstudiosAct());//MA2020
        cv.put(ConstantsDB.otrorecurso1, pt.getOtrorecurso1());
        cv.put(ConstantsDB.otrorecurso2, pt.getOtrorecurso2());
        cv.put(ConstantsDB.ID_INSTANCIA, pt.getMovilInfo().getIdInstancia());
        cv.put(ConstantsDB.FILE_PATH, pt.getMovilInfo().getInstancePath());
        cv.put(ConstantsDB.STATUS, pt.getMovilInfo().getEstado());
        cv.put(ConstantsDB.WHEN_UPDATED, pt.getMovilInfo().getUltimoCambio());
        cv.put(ConstantsDB.START, pt.getMovilInfo().getStart());
        cv.put(ConstantsDB.END, pt.getMovilInfo().getEnd());
        cv.put(ConstantsDB.DEVICE_ID, pt.getMovilInfo().getDeviceid());
        cv.put(ConstantsDB.SIM_SERIAL, pt.getMovilInfo().getSimserial());
        cv.put(ConstantsDB.PHONE_NUMBER, pt.getMovilInfo().getPhonenumber());
        cv.put(ConstantsDB.TODAY, pt.getMovilInfo().getToday().getTime());
        cv.put(ConstantsDB.USUARIO, pt.getMovilInfo().getUsername());
        cv.put(ConstantsDB.DELETED, pt.getMovilInfo().getEliminado());
        cv.put(ConstantsDB.REC1, pt.getMovilInfo().getRecurso1());
        cv.put(ConstantsDB.REC2, pt.getMovilInfo().getRecurso2());
        mDb.insertOrThrow(ConstantsDB.PT_TABLE, null, cv);
    }

    /**
     * Borra todas las mediciones de pt de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTodasPT() {
        return mDb.delete(ConstantsDB.PT_TABLE, null, null) > 0;
    }

    /**METODOS PARA OB**/

    /**
     * Inserta un obsequio en la base de datos
     *
     * @param ob
     *            Objeto Obsequio que contiene la informacion
     *
     */
    public void crearOB(Obsequio ob) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.CODIGO, ob.getObId().getCodigo());
        cv.put(ConstantsDB.FECHA_OB, ob.getObId().getFechaEntrega().getTime());
        cv.put(ConstantsDB.OBSEQ, ob.getObseqSN());
        cv.put(ConstantsDB.CARNET, ob.getCarnetSN());
        cv.put(ConstantsDB.PERRETIRA, ob.getPersRecCarnet());
        cv.put(ConstantsDB.PERRETIRAREL, ob.getRelacionFam());
        cv.put(ConstantsDB.PERRETIRAOREL, ob.getOtroRelacionFam());
        cv.put(ConstantsDB.OBS_TEL, ob.getTelefono());
        cv.put(ConstantsDB.CDOM, ob.getCmDomicilio());
        cv.put(ConstantsDB.BARRIO, ob.getBarrio());
        cv.put(ConstantsDB.DIRECCION, ob.getDire());
        cv.put(ConstantsDB.OBS, ob.getObservaciones());
        cv.put(ConstantsDB.otrorecurso1, ob.getOtrorecurso1());
        cv.put(ConstantsDB.ID_INSTANCIA, ob.getMovilInfo().getIdInstancia());
        cv.put(ConstantsDB.FILE_PATH, ob.getMovilInfo().getInstancePath());
        cv.put(ConstantsDB.STATUS, ob.getMovilInfo().getEstado());
        cv.put(ConstantsDB.WHEN_UPDATED, ob.getMovilInfo().getUltimoCambio());
        cv.put(ConstantsDB.START, ob.getMovilInfo().getStart());
        cv.put(ConstantsDB.END, ob.getMovilInfo().getEnd());
        cv.put(ConstantsDB.DEVICE_ID, ob.getMovilInfo().getDeviceid());
        cv.put(ConstantsDB.SIM_SERIAL, ob.getMovilInfo().getSimserial());
        cv.put(ConstantsDB.PHONE_NUMBER, ob.getMovilInfo().getPhonenumber());
        cv.put(ConstantsDB.TODAY, ob.getMovilInfo().getToday().getTime());
        cv.put(ConstantsDB.USUARIO, ob.getMovilInfo().getUsername());
        cv.put(ConstantsDB.DELETED, ob.getMovilInfo().getEliminado());
        cv.put(ConstantsDB.REC1, ob.getMovilInfo().getRecurso1());
        cv.put(ConstantsDB.REC2, ob.getMovilInfo().getRecurso2());
        mDb.insertOrThrow(ConstantsDB.OB_TABLE, null, cv);
    }

    /**
     * Borra todos los obsequios de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTodosOB() {
        return mDb.delete(ConstantsDB.OB_TABLE, null, null) > 0;
    }


    /**METODOS PARA VISITAS**/

    /**
     * Inserta una visita en la base de datos
     *
     * @param visita
     *            Objeto VisitaTerreno que contiene la informacion
     *
     */
    public void crearDatosVisita(DatosVisitaTerreno visita) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.CODIGO, visita.getVisitaId().getCodigo());
        cv.put(ConstantsDB.FECHA_VISITA, visita.getVisitaId().getFechaVisita().getTime());
        cv.put(ConstantsDB.COD_CASA, visita.getCodCasa());
        cv.put(ConstantsDB.CDOM_VIS, visita.getcDom());
        cv.put(ConstantsDB.BARRIO_VIS, visita.getBarrio());
        cv.put(ConstantsDB.MANZ_VIS, visita.getManzana());
        cv.put(ConstantsDB.DIRE_VIS, visita.getDireccion());
        cv.put(ConstantsDB.COORD_VIS, visita.getCoordenadas());
        cv.put(ConstantsDB.LAT_VIS, visita.getLatitud());
        cv.put(ConstantsDB.LON_VIS, visita.getLongitud());
        cv.put(ConstantsDB.telefonoClasif1, visita.getTelefonoClasif1());
        cv.put(ConstantsDB.telefonoConv1, visita.getTelefonoConv1());
        cv.put(ConstantsDB.telefonoCel1, visita.getTelefonoCel1());
        cv.put(ConstantsDB.telefonoEmpresa1, visita.getTelefonoEmpresa1());
        cv.put(ConstantsDB.telefono2SN, visita.getTelefono2SN());
        cv.put(ConstantsDB.telefonoClasif2, visita.getTelefonoClasif2());
        cv.put(ConstantsDB.telefonoConv2, visita.getTelefonoConv2());
        cv.put(ConstantsDB.telefonoCel2, visita.getTelefonoCel2());
        cv.put(ConstantsDB.telefonoEmpresa2, visita.getTelefonoEmpresa2());
        cv.put(ConstantsDB.telefono3SN, visita.getTelefono3SN());
        cv.put(ConstantsDB.telefonoClasif3, visita.getTelefonoClasif3());
        cv.put(ConstantsDB.telefonoConv3, visita.getTelefonoConv3());
        cv.put(ConstantsDB.telefonoCel3, visita.getTelefonoCel3());
        cv.put(ConstantsDB.telefonoEmpresa3, visita.getTelefonoEmpresa3());
        cv.put(ConstantsDB.telefono4SN, visita.getTelefono4SN());
        cv.put(ConstantsDB.telefonoClasif4, visita.getTelefonoClasif4());
        cv.put(ConstantsDB.telefonoConv4, visita.getTelefonoConv4());
        cv.put(ConstantsDB.telefonoCel4, visita.getTelefonoCel4());
        cv.put(ConstantsDB.telefonoEmpresa4, visita.getTelefonoEmpresa4());
        cv.put(ConstantsDB.candidatoNI, visita.getCandidatoNI());
        cv.put(ConstantsDB.nombreCandNI1, visita.getNombreCandNI1());
        cv.put(ConstantsDB.nombreCandNI2, visita.getNombreCandNI2());
        cv.put(ConstantsDB.apellidoCandNI1, visita.getApellidoCandNI1());
        cv.put(ConstantsDB.apellidoCandNI2, visita.getApellidoCandNI2());
        cv.put(ConstantsDB.nombreptTutorCandNI, visita.getNombreptTutorCandNI());
        cv.put(ConstantsDB.nombreptTutorCandNI2, visita.getNombreptTutorCandNI2());
        cv.put(ConstantsDB.apellidoptTutorCandNI, visita.getApellidoptTutorCandNI());
        cv.put(ConstantsDB.apellidoptTutorCandNI2, visita.getApellidoptTutorCandNI2());
        cv.put(ConstantsDB.relacionFamCandNI, visita.getRelacionFamCandNI());
        cv.put(ConstantsDB.otraRelacionFamCandNI, visita.getOtraRelacionFamCandNI());



        cv.put(ConstantsDB.otrorecurso1, visita.getOtrorecurso1());
        cv.put(ConstantsDB.otrorecurso2, visita.getOtrorecurso2());
        cv.put(ConstantsDB.ID_INSTANCIA, visita.getMovilInfo().getIdInstancia());
        cv.put(ConstantsDB.FILE_PATH, visita.getMovilInfo().getInstancePath());
        cv.put(ConstantsDB.STATUS, visita.getMovilInfo().getEstado());
        cv.put(ConstantsDB.WHEN_UPDATED, visita.getMovilInfo().getUltimoCambio());
        cv.put(ConstantsDB.START, visita.getMovilInfo().getStart());
        cv.put(ConstantsDB.END, visita.getMovilInfo().getEnd());
        cv.put(ConstantsDB.DEVICE_ID, visita.getMovilInfo().getDeviceid());
        cv.put(ConstantsDB.SIM_SERIAL, visita.getMovilInfo().getSimserial());
        cv.put(ConstantsDB.PHONE_NUMBER, visita.getMovilInfo().getPhonenumber());
        cv.put(ConstantsDB.TODAY, visita.getMovilInfo().getToday().getTime());
        cv.put(ConstantsDB.USUARIO, visita.getMovilInfo().getUsername());
        cv.put(ConstantsDB.DELETED, visita.getMovilInfo().getEliminado());
        cv.put(ConstantsDB.REC1, visita.getMovilInfo().getRecurso1());
        cv.put(ConstantsDB.REC2, visita.getMovilInfo().getRecurso2());
        mDb.insertOrThrow(ConstantsDB.DAT_VIS_TABLE, null, cv);
    }

    /**
     * Borra todas las DatosVisitaTerreno de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTodasDatosVisitaTerrenos() {
        return mDb.delete(ConstantsDB.DAT_VIS_TABLE, null, null) > 0;
    }


    /**METODOS PARA VISITAS**/

    /**
     * Inserta una visita en la base de datos
     *
     * @param visita
     *            Objeto VisitaTerreno que contiene la informacion
     *
     */
    public void crearVisita(ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno visita) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.CODIGO, visita.getVisitaId().getCodigo());
        cv.put(ConstantsDB.FECHA_VISITA, visita.getVisitaId().getFechaVisita().getTime());
        cv.put(ConstantsDB.VISITASN, visita.getVisitaSN());
        cv.put(ConstantsDB.MOTNOVIS, visita.getMotNoVisita());
        cv.put(ConstantsDB.ACOMP_VIS, visita.getAcomp());
        cv.put(ConstantsDB.REL_VIS, visita.getRelacionFam());
        cv.put(ConstantsDB.ASENT_VIS, visita.getAsentimiento());
        cv.put(ConstantsDB.otraRelacionFam, visita.getOtraRelacionFam());
        cv.put(ConstantsDB.carnetSN, visita.getCarnetSN());
		//para peso y talla muestreo 2020
		cv.put(ConstantsDB.estudiaSN, visita.getEstudiaSN());
		cv.put(ConstantsDB.nEscuela, visita.getnEscuela());
		cv.put(ConstantsDB.otraEscuela, visita.getOtraEscuela());
		cv.put(ConstantsDB.turno, visita.getTurno());
		cv.put(ConstantsDB.otroMotNoVisita, visita.getOtroMotNoVisita());//MA2020

        cv.put(ConstantsDB.otrorecurso1, visita.getOtrorecurso1());
        cv.put(ConstantsDB.otrorecurso2, visita.getOtrorecurso2());
        cv.put(ConstantsDB.ID_INSTANCIA, visita.getMovilInfo().getIdInstancia());
        cv.put(ConstantsDB.FILE_PATH, visita.getMovilInfo().getInstancePath());
        cv.put(ConstantsDB.STATUS, visita.getMovilInfo().getEstado());
        cv.put(ConstantsDB.WHEN_UPDATED, visita.getMovilInfo().getUltimoCambio());
        cv.put(ConstantsDB.START, visita.getMovilInfo().getStart());
        cv.put(ConstantsDB.END, visita.getMovilInfo().getEnd());
        cv.put(ConstantsDB.DEVICE_ID, visita.getMovilInfo().getDeviceid());
        cv.put(ConstantsDB.SIM_SERIAL, visita.getMovilInfo().getSimserial());
        cv.put(ConstantsDB.PHONE_NUMBER, visita.getMovilInfo().getPhonenumber());
        cv.put(ConstantsDB.TODAY, visita.getMovilInfo().getToday().getTime());
        cv.put(ConstantsDB.USUARIO, visita.getMovilInfo().getUsername());
        cv.put(ConstantsDB.DELETED, visita.getMovilInfo().getEliminado());
        cv.put(ConstantsDB.REC1, visita.getMovilInfo().getRecurso1());
        cv.put(ConstantsDB.REC2, visita.getMovilInfo().getRecurso2());
        mDb.insertOrThrow(ConstantsDB.VIS_TABLE, null, cv);
    }

    /**
     * Borra todas las VisitaTerreno de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTodasVisitaTerrenos() {
        return mDb.delete(ConstantsDB.VIS_TABLE, null, null) > 0;
    }

    /**METODOS PARA RECEPCION BHC**/

    /**
     * Inserta una rec bhc en la base de datos
     *
     * @param recbhc
     *            Objeto RecepcionBHC que contiene la informacion
     *
     */
    public void crearRecepcionBHC(RecepcionBHC recbhc) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.CODIGO, recbhc.getRecBhcId().getCodigo());
        cv.put(ConstantsDB.FECHA_BHC, recbhc.getRecBhcId().getFechaRecBHC().getTime());
        cv.put(ConstantsDB.PAXGENE, recbhc.getPaxgene());
        cv.put(ConstantsDB.VOLBHC, recbhc.getVolumen());
        cv.put(ConstantsDB.LUGAR, recbhc.getLugar());
        cv.put(ConstantsDB.OBSBHC, recbhc.getObservacion());
        cv.put(ConstantsDB.USUARIO, recbhc.getUsername());
        cv.put(ConstantsDB.STATUS, recbhc.getEstado());
        cv.put(ConstantsDB.TODAY, recbhc.getFecreg().getTime());
        mDb.insertOrThrow(ConstantsDB.BHC_TABLE, null, cv);
    }

    /**
     * Borra todas las RecepcionBHC de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarRecepcionBHC() {
        return mDb.delete(ConstantsDB.BHC_TABLE, null, null) > 0;
    }

    /**
     * Busca una RecepcionBHC de la base de datos
     *
     * @return RecepcionBHC
     */

    public Cursor buscarRecepcionBHC(Integer codigo, Date fecha_bhc) throws SQLException {
        Cursor c = null;
        c = mDb.query(true, ConstantsDB.BHC_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo + " and " +
                        ConstantsDB.FECHA_BHC + "=" + fecha_bhc.getTime(), null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Lista todas las RecepcionBHC de la base de datos
     *
     * @return dataset con RecepcionBHC
     */
    public Cursor obtenerRecepcionBHC(Date today) throws SQLException {
        Cursor c = null;
        c = mDb.query(true, ConstantsDB.BHC_TABLE, null,
                ConstantsDB.FECHA_BHC + "=" + today.getTime(), null, null, null, ConstantsDB.TODAY + " Desc", null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**METODOS PARA RECEPCION SERO**/

    /**
     * Inserta una rec sero en la base de datos
     *
     * @param recsero
     *            Objeto RecepcionSero que contiene la informacion
     *
     */
    public void crearRecepcionSero(RecepcionSero recsero) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.ID, recsero.getId());
        cv.put(ConstantsDB.CODIGO, recsero.getCodigo());
        cv.put(ConstantsDB.FECHA_SERO, recsero.getFechaRecSero().getTime());
        cv.put(ConstantsDB.VOLSERO, recsero.getVolumen());
        cv.put(ConstantsDB.LUGAR, recsero.getLugar());
        cv.put(ConstantsDB.OBSSERO, recsero.getObservacion());
        cv.put(ConstantsDB.USUARIO, recsero.getUsername());
        cv.put(ConstantsDB.STATUS, recsero.getEstado());
        cv.put(ConstantsDB.TODAY, recsero.getFecreg().getTime());
        mDb.insertOrThrow(ConstantsDB.SERO_TABLE, null, cv);
    }

    /**
     * Borra todas las RecepcionSero de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarRecepcionSero() {
        return mDb.delete(ConstantsDB.SERO_TABLE, null, null) > 0;
    }

    /**
     * Busca una RecepcionSero de la base de datos
     *
     * @return RecepcionSero
     */

    public Cursor buscarRecepcionSero(Integer codigo, Date fecha_sero) throws SQLException {
        Cursor c = null;
        c = mDb.query(true, ConstantsDB.SERO_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo + " and " +
                        ConstantsDB.FECHA_SERO + "=" + fecha_sero.getTime(), null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Lista todas las RecepcionSero de la base de datos
     *
     * @return dataset con RecepcionSero
     */
    public Cursor obtenerRecepcionSero(Date today) throws SQLException {
        Cursor c = null;
        c = mDb.query(true, ConstantsDB.SERO_TABLE, null,
                ConstantsDB.FECHA_SERO + "=" + today.getTime(), null, null, null, ConstantsDB.TODAY + " Desc", null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**METODOS PARA TEMP PBMC**/

    /**
     * Inserta una temp pbmc en la base de datos
     *
     * @param temppbmc
     *            Objeto TempPbmc que contiene la informacion
     *
     */
    public void crearTempPbmc(TempPbmc temppbmc) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.RECURSO, temppbmc.getTempPbmcId().getRecurso());
        cv.put(ConstantsDB.FECHA_TEMP, temppbmc.getTempPbmcId().getFechaTempPbmc().getTime());
        cv.put(ConstantsDB.TEMP, temppbmc.getTemperatura());
        cv.put(ConstantsDB.LUGARTEMP, temppbmc.getLugar());
        cv.put(ConstantsDB.OBSTEMP, temppbmc.getObservacion());
        cv.put(ConstantsDB.USUARIO, temppbmc.getUsername());
        cv.put(ConstantsDB.STATUS, temppbmc.getEstado());
        cv.put(ConstantsDB.TODAY, temppbmc.getFecreg().getTime());
        mDb.insertOrThrow(ConstantsDB.TPBMC_TABLE, null, cv);
    }

    /**
     * Borra todas las TempPbmc de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTempPbmc() {
        return mDb.delete(ConstantsDB.TPBMC_TABLE, null, null) > 0;
    }

    /**
     * Lista todas las TempPbmc de la base de datos
     *
     * @return dataset con TempPbmc
     */
    public Cursor obtenerTempPbmc(Date today) throws SQLException {
        Cursor c = null;
        c = mDb.query(true, ConstantsDB.TPBMC_TABLE, null,
                ConstantsDB.TODAY + "=" + today.getTime(), null, null, null, ConstantsDB.FECHA_TEMP + " Desc", null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**METODOS PARA TEMP ROJO**/

    /**
     * Inserta una temp rojo en la base de datos
     *
     * @param temprojo
     *            Objeto TempRojoBhc que contiene la informacion
     *
     */
    public void crearTempRojoBhc(TempRojoBhc temprojo) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.RECURSO, temprojo.getTempRojoBhcId().getRecurso());
        cv.put(ConstantsDB.FECHA_TEMP, temprojo.getTempRojoBhcId().getFechaTempRojoBhc().getTime());
        cv.put(ConstantsDB.TEMP, temprojo.getTemperatura());
        cv.put(ConstantsDB.LUGARTEMP, temprojo.getLugar());
        cv.put(ConstantsDB.OBSTEMP, temprojo.getObservacion());
        cv.put(ConstantsDB.USUARIO, temprojo.getUsername());
        cv.put(ConstantsDB.STATUS, temprojo.getEstado());
        cv.put(ConstantsDB.TODAY, temprojo.getFecreg().getTime());
        mDb.insertOrThrow(ConstantsDB.TRB_TABLE, null, cv);
    }

    /**
     * Borra todas las TempRojoBhc de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTempRojoBhc() {
        return mDb.delete(ConstantsDB.TRB_TABLE, null, null) > 0;
    }

    /**
     * Lista todas las TempRojoBhc de la base de datos
     *
     * @return dataset con TempRojoBhc
     */
    public Cursor obtenerTempRojoBhc(Date today) throws SQLException {
        Cursor c = null;
        c = mDb.query(true, ConstantsDB.TRB_TABLE, null,
                ConstantsDB.TODAY + "=" + today.getTime(), null, null, null, ConstantsDB.FECHA_TEMP + " Desc", null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**METODOS PARA PINCHAZOS**/

    /**
     * Inserta un pinchazo en la base de datos
     *
     * @param pinchazo
     *            Objeto Pinchazo que contiene la informacion
     *
     */
    public void crearPinchazo(Pinchazo pinchazo) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.CODIGO, pinchazo.getPinId().getCodigo());
        cv.put(ConstantsDB.FECHA_PIN, pinchazo.getPinId().getFechaPinchazo().getTime());
        cv.put(ConstantsDB.PINCHAZOS, pinchazo.getNumPin());
        cv.put(ConstantsDB.LUGAR, pinchazo.getLugar());
        cv.put(ConstantsDB.OBSPIN, pinchazo.getObservacion());
        cv.put(ConstantsDB.USUARIO, pinchazo.getUsername());
        cv.put(ConstantsDB.STATUS, pinchazo.getEstado());
        cv.put(ConstantsDB.TODAY, pinchazo.getFecreg().getTime());
        mDb.insertOrThrow(ConstantsDB.PIN_TABLE, null, cv);
    }

    /**
     * Borra todas las Pinchazo de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarPinchazo() {
        return mDb.delete(ConstantsDB.PIN_TABLE, null, null) > 0;
    }

    /**
     * Busca una Pinchazo de la base de datos
     *
     * @return Pinchazo
     */

    public Cursor buscarPinchazo(Integer codigo, Date fecha_pin) throws SQLException {
        Cursor c = null;
        c = mDb.query(true, ConstantsDB.PIN_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo + " and " +
                        ConstantsDB.FECHA_PIN + "=" + fecha_pin.getTime(), null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Lista todas las Pinchazo de la base de datos
     *
     * @return dataset con Pinchazo
     */
    public Cursor obtenerPinchazo(Date today) throws SQLException {
        Cursor c = null;
        c = mDb.query(true, ConstantsDB.PIN_TABLE, null,
                ConstantsDB.FECHA_PIN + "=" + today.getTime(), null, null, null, ConstantsDB.TODAY + " Desc", null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Inserta un RazonNoData en la base de datos
     *
     * @param rnd
     *            Objeto RazonNoData que contiene la informacion
     *
     */
    public void crearRazonNoData(RazonNoData rnd) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.CODIGO, rnd.getRndId().getCodigo());
        cv.put(ConstantsDB.TODAY, rnd.getRndId().getFechaRegistro().getTime());
        cv.put(ConstantsDB.RAZON, rnd.getRazon());
        cv.put(ConstantsDB.ORAZON, rnd.getOtraRazon());
        cv.put(ConstantsDB.USUARIO, rnd.getUsername());
        cv.put(ConstantsDB.STATUS, rnd.getEstado());
        mDb.insertOrThrow(ConstantsDB.NO_DATA_TABLE, null, cv);
    }

    /**
     * Borra todas las RazonNoData de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarRazonNoData() {
        return mDb.delete(ConstantsDB.NO_DATA_TABLE, null, null) > 0;
    }

    /**METODOS PARA ENCUESTAS SATISFACCION**/

    /**
     * Inserta una encuesta de satisfaccion en la base de datos
     *
     * @param enssat satistaccion
     *            Objeto EncuestaSatisfaccion que contiene la informacion
     *
     */
    public void crearEncuestaSatisfaccion(EncuestaSatisfaccion enssat) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.FECHA_ENC_SAT, enssat.getFechaEncuesta().getTime());
        cv.put(ConstantsDB.ESTUDIOSAT, enssat.getEstudio());
        cv.put(ConstantsDB.ATENPEREST, enssat.getAtenPerEst());
        cv.put(ConstantsDB.TIEMATEN, enssat.getTiemAten());
        cv.put(ConstantsDB.ATENPERADM, enssat.getAtenPerAdm());
        cv.put(ConstantsDB.ATENPERENFERM, enssat.getAtenPerEnferm());
        cv.put(ConstantsDB.ATENPERMED, enssat.getAtenPerMed());
        cv.put(ConstantsDB.AMBATEN, enssat.getAmbAten());
        cv.put(ConstantsDB.ATENPERLAB, enssat.getAtenPerLab());
        cv.put(ConstantsDB.EXPLDXENF, enssat.getExplDxEnf());
        cv.put(ConstantsDB.FLUDENSN, enssat.getFludenSN());
        cv.put(ConstantsDB.FLUCONIMP, enssat.getFluConImp());
        cv.put(ConstantsDB.DENCONIMP, enssat.getDenConImp());
        cv.put(ConstantsDB.EXPLPELIGENF, enssat.getExplPeligEnf());
        cv.put(ConstantsDB.EXPMEDCUID, enssat.getExpMedCuid());
        cv.put(ConstantsDB.otrorecurso1, enssat.getOtrorecurso1());
        cv.put(ConstantsDB.ID_INSTANCIA, enssat.getMovilInfo().getIdInstancia());
        cv.put(ConstantsDB.FILE_PATH, enssat.getMovilInfo().getInstancePath());
        cv.put(ConstantsDB.STATUS, enssat.getMovilInfo().getEstado());
        cv.put(ConstantsDB.WHEN_UPDATED, enssat.getMovilInfo().getUltimoCambio());
        cv.put(ConstantsDB.START, enssat.getMovilInfo().getStart());
        cv.put(ConstantsDB.END, enssat.getMovilInfo().getEnd());
        cv.put(ConstantsDB.DEVICE_ID, enssat.getMovilInfo().getDeviceid());
        cv.put(ConstantsDB.SIM_SERIAL, enssat.getMovilInfo().getSimserial());
        cv.put(ConstantsDB.PHONE_NUMBER, enssat.getMovilInfo().getPhonenumber());
        cv.put(ConstantsDB.TODAY, enssat.getMovilInfo().getToday().getTime());
        cv.put(ConstantsDB.USUARIO, enssat.getMovilInfo().getUsername());
        cv.put(ConstantsDB.DELETED, enssat.getMovilInfo().getEliminado());
        cv.put(ConstantsDB.REC1, enssat.getMovilInfo().getRecurso1());
        cv.put(ConstantsDB.REC2, enssat.getMovilInfo().getRecurso2());
        mDb.insertOrThrow(ConstantsDB.ENC_SAT_TABLE, null, cv);
    }

    /**
     * Borra todas las encuestas de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTodasEncuestaSatisfaccion() {
        return mDb.delete(ConstantsDB.ENC_SAT_TABLE, null, null) > 0;
    }

    /**
     * Busca un usuario de la base de datos
     *
     * @return true or false
     */

    public boolean existeUsuario(String user) throws SQLException {
        Cursor c = null;
        c = crearCursor(MainDBConstants.USER_TABLE, ConstantsDB.USERNAME + "='" + user +"'", null, null);
        //c = mDb.query(true, MainDBConstants.USER_TABLE, null,
        //        ConstantsDB.USERNAME + "='" + user +"'", null, null, null, null, null);

        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            if (!c.isClosed()) c.close();
            return true;
        }
        if (!c.isClosed()) c.close();
        return false;
    }

    /**
     * Inserta un usuario en la base de datos
     *
     * @param user
     *            Objeto Usuario que contiene la informacion
     *
     */
    public void crearPermisosUsuario(UserPermissions user) {
        ContentValues cv = UserSistemaHelper.crearPermisosUsuario(user);
        mDb.insertOrThrow(ConstantsDB.USER_PERM_TABLE, null, cv);
    }

    /**
     * Borra todos los usuarios de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTodosUsuarios() {
        return mDb.delete(MainDBConstants.USER_TABLE, null, null) > 0;
    }

    /**
     * Actualiza un usuario en la base de datos
     *
     * @param user
     *            Objeto Usuario que contiene la informacion
     *
     */
    public boolean actualizarUsuario(UserSistema user) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.USERNAME, user.getUsername());
        cv.put(ConstantsDB.ENABLED, user.getEnabled());
        //cv.put(ConstantsDB.PASSWORD, user.getPassword());
		/*cv.put(ConstantsDB.U_ECASA, user.getEncuestaCasa());
		cv.put(ConstantsDB.U_EPART, user.getEncuestaParticipante());
		cv.put(ConstantsDB.U_ELACT, user.getEncuestaLactancia());
		cv.put(ConstantsDB.U_ESAT, user.getEncuestaSatisfaccion());
		cv.put(ConstantsDB.U_MUESTRA, user.getMuestra());
		cv.put(ConstantsDB.U_OBSEQUIO, user.getObsequio());
		cv.put(ConstantsDB.U_PYT, user.getPesoTalla());
		cv.put(ConstantsDB.U_VAC, user.getVacunas());
		cv.put(ConstantsDB.U_VISITA, user.getVisitas());
		cv.put(ConstantsDB.U_RECEPCION, user.getRecepcion());
		cv.put(ConstantsDB.U_CONS, user.getConsentimiento());
		cv.put(ConstantsDB.U_CASAZIKA, user.getCasazika());
		cv.put(ConstantsDB.U_TAMZIKA, user.getTamizajezika());*/
        return mDb.update(MainDBConstants.USER_TABLE, cv, ConstantsDB.USERNAME + "='"
                + user.getUsername() + "'", null) > 0;
    }

    /**METODOS PARA MUESTRAS**/

    /**
     * Inserta una muestra en la base de datos
     *
     * @param muestra
     *            Objeto Muestra que contiene la informacion
     *
     */
    public void crearMuestra(ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra muestra) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.CODIGO, muestra.getmId().getCodigo());
        cv.put(ConstantsDB.FECHA_MUESTRA, muestra.getmId().getFechaMuestra().getTime());
        cv.put(ConstantsDB.MFIEBRE, muestra.getFiebreM());
        cv.put(ConstantsDB.CONSULTA, muestra.getConsulta());
        cv.put(ConstantsDB.BHC, muestra.getTuboBHC());
        cv.put(ConstantsDB.ROJO, muestra.getTuboRojo());
        cv.put(ConstantsDB.LEU, muestra.getTuboLeu());

        cv.put(ConstantsDB.bhc_razonNo, muestra.getBhc_razonNo());
        cv.put(ConstantsDB.rojo_razonNo, muestra.getRojo_razonNo());
        cv.put(ConstantsDB.pbmc_razonNo, muestra.getPbmc_razonNo());

        cv.put(ConstantsDB.bhc_otraRazonNo, muestra.getBhc_otraRazonNo());
        cv.put(ConstantsDB.rojo_otraRazonNo, muestra.getRojo_otraRazonNo());
        cv.put(ConstantsDB.pbmc_otraRazonNo, muestra.getPbmc_otraRazonNo());

        cv.put(ConstantsDB.horaBHC, muestra.getHoraBHC());
        cv.put(ConstantsDB.horaPBMC, muestra.getHoraPBMC());
        cv.put(ConstantsDB.horaInicioPax, muestra.getHoraInicioPax());

        cv.put(ConstantsDB.horaFinPax, muestra.getHoraFinPax());
        cv.put(ConstantsDB.codPax, muestra.getCodPax());
        cv.put(ConstantsDB.terreno, muestra.getTerreno());

        cv.put(ConstantsDB.otrorecurso1, muestra.getOtrorecurso1());
        cv.put(ConstantsDB.otrorecurso2, muestra.getOtrorecurso2());
        cv.put(ConstantsDB.estudiosAct, muestra.getEstudiosAct());
        cv.put(ConstantsDB.hd_sn, muestra.getHd_sn());
        cv.put(ConstantsDB.hdPorqueNo, muestra.getHdPorqueNo());

		cv.put(ConstantsDB.tuboPax, muestra.getTuboPax()); //MA2020

        cv.put(ConstantsDB.PIN, muestra.getPinchazos());
        cv.put(ConstantsDB.ID_INSTANCIA, muestra.getMovilInfo().getIdInstancia());
        cv.put(ConstantsDB.FILE_PATH, muestra.getMovilInfo().getInstancePath());
        cv.put(ConstantsDB.STATUS, muestra.getMovilInfo().getEstado());
        cv.put(ConstantsDB.WHEN_UPDATED, muestra.getMovilInfo().getUltimoCambio());
        cv.put(ConstantsDB.START, muestra.getMovilInfo().getStart());
        cv.put(ConstantsDB.END, muestra.getMovilInfo().getEnd());
        cv.put(ConstantsDB.DEVICE_ID, muestra.getMovilInfo().getDeviceid());
        cv.put(ConstantsDB.SIM_SERIAL, muestra.getMovilInfo().getSimserial());
        cv.put(ConstantsDB.PHONE_NUMBER, muestra.getMovilInfo().getPhonenumber());
        cv.put(ConstantsDB.TODAY, muestra.getMovilInfo().getToday().getTime());
        cv.put(ConstantsDB.USUARIO, muestra.getMovilInfo().getUsername());
        cv.put(ConstantsDB.DELETED, muestra.getMovilInfo().getEliminado());
        cv.put(ConstantsDB.REC1, muestra.getMovilInfo().getRecurso1());
        cv.put(ConstantsDB.REC2, muestra.getMovilInfo().getRecurso2());
        mDb.insertOrThrow(ConstantsDB.MUESTRA_TABLE, null, cv);
    }

    /**
     * Borra todas las muestras de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTodasMuestras() {
        return mDb.delete(ConstantsDB.MUESTRA_TABLE, null, null) > 0;
    }

    /**
     * Borra todos los roles de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTodosRoles() {
        return mDb.delete(MainDBConstants.ROLE_TABLE, null, null) > 0;
    }

    /**
     * Borra todos los roles de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTodosPermisos() {
        return mDb.delete(ConstantsDB.USER_PERM_TABLE, null, null) > 0;
    }


    /**METODOS PARA DatosPartoBB**/

    /**
     * Inserta una DatosPartoBB en la base de datos
     *
     * @param datosPartoBB
     *            Objeto DatosPartoBB que contiene la informacion
     *
     */
    public void crearDatosPartoBB(DatosPartoBB datosPartoBB) {
        ContentValues cv = DatosPartoBBHelper.crearDatosPartoBBContentValues(datosPartoBB);
        mDb.insertOrThrow(ConstantsDB.DATOSPARTOBB_TABLE, null, cv);
    }

    /**
     * Borra todos los DatosPartoBB de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarDatosPartoBB() {
        return mDb.delete(ConstantsDB.DATOSPARTOBB_TABLE, null, null) > 0;
    }

    /**
     * Obtiene una lista de DatosPartoBB de la base de datos
     *
     * @return List<DatosPartoBB>
     */
    public ArrayList<DatosPartoBB> getDatosPartoBBs(String filtro, String orden) throws SQLException {
        ArrayList<DatosPartoBB> mDatosPartoBBs = new ArrayList<DatosPartoBB>();
        Cursor cursorDatosPartoBB = null;
        cursorDatosPartoBB = mDb.query(true, ConstantsDB.DATOSPARTOBB_TABLE, null,
                filtro, null, null, null, null, null);
        if (cursorDatosPartoBB != null && cursorDatosPartoBB.getCount() > 0) {
            cursorDatosPartoBB.moveToFirst();
            mDatosPartoBBs.clear();
            do{
                DatosPartoBB mDatosPartoBB = null;
                mDatosPartoBB = DatosPartoBBHelper.crearDatosPartoBB(cursorDatosPartoBB);
                mDatosPartoBBs.add(mDatosPartoBB);
            } while (cursorDatosPartoBB.moveToNext());
        }
        if (!cursorDatosPartoBB.isClosed()) cursorDatosPartoBB.close();
        return mDatosPartoBBs;
    }




    /**METODOS PARA NewVacuna**/

    /**
     * Inserta una NewVacuna en la base de datos
     *
     * @param newVacuna
     *            Objeto NewVacuna que contiene la informacion
     *
     */
    public void crearNewVacuna(NewVacuna newVacuna) {
        ContentValues cv = NewVacunaHelper.crearNewVacunaContentValues(newVacuna);
        mDb.insertOrThrow(ConstantsDB.NEWVAC_TABLE, null, cv);
    }

    /**
     * Borra todos los NewVacuna de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarNewVacuna() {
        return mDb.delete(ConstantsDB.NEWVAC_TABLE, null, null) > 0;
    }

    /**
     * Obtiene una lista de NewVacuna de la base de datos
     *
     * @return List<NewVacuna>
     */
    public ArrayList<NewVacuna> getNewVacunas(String filtro, String orden) throws SQLException {
        ArrayList<NewVacuna> mNewVacunas = new ArrayList<NewVacuna>();
        Cursor cursorNewVacuna = crearCursor(ConstantsDB.NEWVAC_TABLE, filtro, null, orden);
        if (cursorNewVacuna != null && cursorNewVacuna.getCount() > 0) {
            cursorNewVacuna.moveToFirst();
            mNewVacunas.clear();
            do{
                NewVacuna mNewVacuna = null;
                mNewVacuna = NewVacunaHelper.crearNewVacuna(cursorNewVacuna);
                mNewVacunas.add(mNewVacuna);
            } while (cursorNewVacuna.moveToNext());
        }
        if (!cursorNewVacuna.isClosed()) cursorNewVacuna.close();
        return mNewVacunas;
    }


    /**METODOS PARA Documentos**/

    /**
     * Inserta una Documentos en la base de datos
     *
     * @param newDocumentos
     *            Objeto Documentos que contiene la informacion
     *
     */
    public void crearDocumentos(Documentos newDocumentos) {
        ContentValues cv = DocumentosHelper.crearDocumentosContentValues(newDocumentos);
        mDb.insertOrThrow(ConstantsDB.DOCS_TABLE, null, cv);
    }

    /**
     * Obtiene una lista de Documentos de la base de datos
     *
     * @return List<Documentos>
     */
    public ArrayList<Documentos> getDocumentoss(String filtro, String orden) throws SQLException {
        ArrayList<Documentos> mDocumentos = new ArrayList<Documentos>();
        //Cursor cursorDocumentos = crearCursor(ConstantsDB.DOCS_TABLE, filtro, null, orden);
        Cursor cursorDocumentos = mDb.rawQuery("SELECT codigo,fechaDocumento,tipoDoc,username,estado,fechaRecepcion,fecha_registro FROM documentacion where " + filtro , null);
        if (cursorDocumentos != null && cursorDocumentos.getCount() > 0) {
            cursorDocumentos.moveToFirst();
            mDocumentos.clear();
            do{
                Documentos mDocumento = null;
                mDocumento = DocumentosHelper.crearDocumentos2(cursorDocumentos);
                mDocumentos.add(mDocumento);
            } while (cursorDocumentos.moveToNext());
        }
        if (!cursorDocumentos.isClosed()) cursorDocumentos.close();
        return mDocumentos;
    }

    /**
     * Borra todos los participantes_procesos de la base de datos
     *
     * @return verdadero o falso
     */
    public boolean borrarTodosParticipantesProcesos() {
        return mDb.delete(ConstantsDB.PART_PROCESOS_TABLE, null, null) > 0;
    }

    /**
     * Actualiza un participante en la base de datos.
     *
     * @param participante
     *            Objeto que contiene la info
     * @return verdadero o falso
     */
    public boolean updateParticipantesSent(Participante participante) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.estado, String.valueOf(participante.getEstado()));
        return mDb.update(MainDBConstants.PARTICIPANTE_TABLE, cv,
                MainDBConstants.codigo + "="
                        + participante.getCodigo(), null) > 0;
    }

    /**
     * Actualiza un participanteProc en la base de datos.
     *
     * @param participanteProc
     *            Objeto que contiene la info
     * @return verdadero o falso
     */
    public boolean updateParticipanteProcSent(ParticipanteProcesos participanteProc) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.estado, String.valueOf(participanteProc.getMovilInfo().getEstado()));
        return mDb.update(ConstantsDB.PART_PROCESOS_TABLE, cv,
                ConstantsDB.CODIGO + "="
                        + participanteProc.getCodigo(), null) > 0;
    }

    /**
     * Actualiza un EncuestaCasa en la base de datos.
     *
     * @param encuesta
     *            Objeto que contiene la info
     * @return verdadero o falso
     */
    public boolean updateEncuestasCasasSent(ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa encuesta) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, encuesta.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.ENC_CASA_TABLE, cv,
                ConstantsDB.codigo + "='"
                        + encuesta.getCodigo() + "'", null) > 0;
    }


    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateLacMatSent(LactanciaMaterna lactanciaMaterna) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, lactanciaMaterna.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.LACT_TABLE, cv,
                ConstantsDB.CODIGO + "=" + lactanciaMaterna.getLmId().getCodigo() + " and " +
                        ConstantsDB.FECHA_ENC_LACT + "=" + lactanciaMaterna.getLmId().getFechaEncLM().getTime(), null) > 0;
    }


    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateEncPartSent(ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante encparticipante) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, encparticipante.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.ENC_PART_TABLE, cv,
                ConstantsDB.CODIGO + "=" + encparticipante.getEpId().getCodigo() + " and " +
                        ConstantsDB.FECHA_ENC_PAR + "=" + encparticipante.getEpId().getFechaEncPar().getTime(), null) > 0;
    }


    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updatePTsSent(PesoyTalla pyt) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, pyt.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.PT_TABLE, cv,
                ConstantsDB.CODIGO + "=" + pyt.getPtId().getCodigo() + " and " +
                        ConstantsDB.FECHA_PT + "=" + pyt.getPtId().getFechaPT().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateMuestraSent(ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra muestra) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, muestra.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.MUESTRA_TABLE, cv,
                ConstantsDB.CODIGO + "=" + muestra.getmId().getCodigo() + " and " +
                        ConstantsDB.FECHA_MUESTRA + "=" + muestra.getmId().getFechaMuestra().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateObsequioSent(Obsequio obsequio) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, obsequio.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.OB_TABLE, cv,
                ConstantsDB.CODIGO + "=" + obsequio.getObId().getCodigo() + " and " +
                        ConstantsDB.FECHA_OB + "=" + obsequio.getObId().getFechaEntrega().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateNewVacSent(NewVacuna vacuna) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, vacuna.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.NEWVAC_TABLE, cv,
                ConstantsDB.CODIGO + "=" + vacuna.getVacunaId().getCodigo() + " and " +
                        ConstantsDB.fechaRegistroVacuna + "=" + vacuna.getVacunaId().getFechaRegistroVacuna().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateDatosPartoBB(DatosPartoBB datosPartoBB) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, datosPartoBB.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.DATOSPARTOBB_TABLE, cv,
                ConstantsDB.CODIGO + "=" + datosPartoBB.getDatosPartoId().getCodigo() + " and " +
                        ConstantsDB.fechaDatosParto + "=" + datosPartoBB.getDatosPartoId().getFechaDatosParto().getTime(), null) > 0;
    }
    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateVisitasSent(ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno visita) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, visita.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.VIS_TABLE, cv,
                ConstantsDB.CODIGO + "=" + visita.getVisitaId().getCodigo() + " and " +
                        ConstantsDB.FECHA_VISITA + "=" + visita.getVisitaId().getFechaVisita().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateDatosVisitasSent(DatosVisitaTerreno visita) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, visita.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.DAT_VIS_TABLE, cv,
                ConstantsDB.CODIGO + "=" + visita.getVisitaId().getCodigo() + " and " +
                        ConstantsDB.FECHA_VISITA + "=" + visita.getVisitaId().getFechaVisita().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateBHCsSent(RecepcionBHC tubo) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, tubo.getEstado());
        return mDb.update(ConstantsDB.BHC_TABLE, cv,
                ConstantsDB.CODIGO + "=" + tubo.getRecBhcId().getCodigo() + " and " +
                        ConstantsDB.FECHA_BHC + "=" + tubo.getRecBhcId().getFechaRecBHC().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateSerosSent(RecepcionSero tubo) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, tubo.getEstado());
        return mDb.update(ConstantsDB.SERO_TABLE, cv,
                ConstantsDB.ID + "='" + tubo.getId() + "' ", null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateTpbmcsSent(TempPbmc temp) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, temp.getEstado());
        return mDb.update(ConstantsDB.TPBMC_TABLE, cv,
                ConstantsDB.RECURSO + "='" + temp.getTempPbmcId().getRecurso() + "' and " +
                        ConstantsDB.FECHA_TEMP + "=" + temp.getTempPbmcId().getFechaTempPbmc().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateTrojosSent(TempRojoBhc temp) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, temp.getEstado());
        return mDb.update(ConstantsDB.TRB_TABLE, cv,
                ConstantsDB.RECURSO + "='" + temp.getTempRojoBhcId().getRecurso() + "' and " +
                        ConstantsDB.FECHA_TEMP + "=" + temp.getTempRojoBhcId().getFechaTempRojoBhc().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updatePinchazosSent(Pinchazo pinchazo) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, pinchazo.getEstado());
        return mDb.update(ConstantsDB.PIN_TABLE, cv,
                ConstantsDB.CODIGO + "=" + pinchazo.getPinId().getCodigo() + " and " +
                        ConstantsDB.FECHA_PIN + "=" + pinchazo.getPinId().getFechaPinchazo().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateRazonNoDataSent(RazonNoData pinchazo) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, pinchazo.getEstado());
        return mDb.update(ConstantsDB.NO_DATA_TABLE, cv,
                ConstantsDB.CODIGO + "=" + pinchazo.getRndId().getCodigo() + " and " +
                        ConstantsDB.TODAY + "=" + pinchazo.getRndId().getFechaRegistro().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateDocumentosSent(Documentos documento) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, documento.getEstado());
        return mDb.update(ConstantsDB.DOCS_TABLE, cv,
                ConstantsDB.CODIGO + "=" + documento.getDocsId().getCodigo() + " and " +
                        ConstantsDB.fechaDocumento + "=" + documento.getDocsId().getFechaDocumento().getTime(), null) > 0;
    }

    /**
     * Actualiza la base de datos.
     *
     *
     */
    public boolean updateEncSatSent(EncuestaSatisfaccion encuesta) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.STATUS, encuesta.getMovilInfo().getEstado());
        return mDb.update(ConstantsDB.ENC_SAT_TABLE, cv,
                ConstantsDB.FECHA_ENC_SAT + "=" + encuesta.getFechaEncuesta().getTime(), null) > 0;
    }

    /**
     * Actualiza un participante en la base de datos.
     *
     *            Objeto que contiene la info
     * @return verdadero o falso
     */
    public boolean updateCasaParticipante(Integer codCasa, Integer codigo,String username, String enCasa) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.casa, codCasa);
        //cv.put(ConstantsDB.ENC_CASA, enCasa);
        cv.put(ConstantsDB.STATUS, Constants.STATUS_NOT_SUBMITTED);
        cv.put(MainDBConstants.recordUser, username);
        return mDb.update(MainDBConstants.PARTICIPANTE_TABLE, cv,
                ConstantsDB.CODIGO + "="
                        + codigo, null) > 0;
    }

    /**
     * Actualiza una casa en la base de datos.
     *
     * @param casa
     *            Objeto que contiene la info
     * @return verdadero o falso
     */
    public boolean updateCasaSent(Casa casa) {
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.estado, String.valueOf(casa.getEstado()));
        return mDb.update(MainDBConstants.CASA_TABLE, cv,
                MainDBConstants.codigo + "="
                        + casa.getCodigo(), null) > 0;
    }

    public void createCambioCasa(Integer codigo, Integer codigoCasaAnterior, Integer codCasaActual,String username) {
        ContentValues cv = new ContentValues();
        Date hoy = new Date();
        cv.put(ConstantsDB.TODAY, hoy.getTime());
        cv.put(ConstantsDB.codigo, codigo);
        cv.put(ConstantsDB.codCasaAnterior, codigoCasaAnterior);
        cv.put(ConstantsDB.codCasaActual, codCasaActual);
        cv.put(ConstantsDB.STATUS, Constants.STATUS_NOT_SUBMITTED);
        cv.put(ConstantsDB.USUARIO, username);
        mDb.insertOrThrow(ConstantsDB.CAMB_CASA_TABLE, null, cv);
    }

    /**
     * Obtiene una casa
     *
     * @return Casa
     */
    public Casa getCasa(Integer codCasa) throws SQLException {
        Cursor casas = null;
        Casa casa = new Casa();
        casas = mDb.query(true, MainDBConstants.CASA_TABLE, null,
                MainDBConstants.codigo + "=" + codCasa, null, null, null, null, null);
        if (casas != null && casas.getCount() > 0) {
            casas.moveToFirst();
            casa = CasaHelper.crearCasa(casas);
        }
        casas.close();
        return casa;
    }

    /**
     * Obtiene un participante
     *
     * @return Participante
     */
    public Participante getParticipante(Integer codigo) throws SQLException {
        Cursor participantes = null;
        Participante participante = new Participante();
        participantes = mDb.query(true, ConstantsDB.PART_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (participantes != null && participantes.getCount() > 0) {
            participantes.moveToFirst();
            participante = ParticipanteHelper.crearParticipante(participantes);
        }
        participantes.close();
        return participante;
    }

    /**
     * Obtiene Lista todas las participantes
     *
     * @return lista con participantes
     */
    public List<ParticipanteProcesos> getListaParticipantesProc(String opcion) throws SQLException {
        Cursor participantes = null;
        List<ParticipanteProcesos> mParticipantes = new ArrayList<ParticipanteProcesos>();
        if(opcion.matches(Constants.STATUS_NOT_SUBMITTED)){
            participantes = mDb.query(true, ConstantsDB.PART_PROCESOS_TABLE, null,
                    ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        }
        if (participantes != null && participantes.getCount() > 0) {
            participantes.moveToFirst();
            mParticipantes.clear();
            do{
                mParticipantes.add(ParticipanteHelper.crearParticipanteProcesos(participantes));
            } while (participantes.moveToNext());
        }
        if (participantes!=null && !participantes.isClosed())participantes.close();
        return mParticipantes;
    }

    /**
     * Obtiene Lista todas las encuestas casas sin enviar
     *
     * @return lista con EncuestaCasa
     */
    public List<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa> getListaEncuestaCasasSinEnviar() throws SQLException {
        Cursor enccasas = null;
        List<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa> mEncuestaCasas = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa>();
        enccasas = mDb.query(true, ConstantsDB.ENC_CASA_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (enccasas != null && enccasas.getCount() > 0) {
            enccasas.moveToFirst();
            mEncuestaCasas.clear();
            do{
                mEncuestaCasas.add(crearEncuestaCasa(enccasas));
            } while (enccasas.moveToNext());
        }
        enccasas.close();
        return mEncuestaCasas;
    }

    /**
     * Obtiene Lista todas las encuestas casas para un codigo
     *
     * @return lista con EncuestaCasa
     */
    public ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa> getListaEncuestaCasas(Integer codCasa) throws SQLException {
        Cursor enccasas = null;
        ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa> mEncuestaCasas = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa>();
        enccasas = mDb.query(true, ConstantsDB.ENC_CASA_TABLE, null,
                ConstantsDB.COD_CASA + "=" + codCasa, null, null, null, null, null);
        if (enccasas != null && enccasas.getCount() > 0) {
            enccasas.moveToFirst();
            mEncuestaCasas.clear();
            do{
                mEncuestaCasas.add(crearEncuestaCasa(enccasas));
            } while (enccasas.moveToNext());
        }
        enccasas.close();
        return mEncuestaCasas;
    }

    /**
     * Obtiene Lista todas las encuestas casas chf para un codigo
     *
     * @return lista con EncuestaCasa
     */
    public ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa> getListaEncuestaCasasChf(String codCasa) throws SQLException {
        Cursor enccasas = null;
        ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa> mEncuestaCasas = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa>();
        enccasas = mDb.query(true, ConstantsDB.ENC_CASA_TABLE, null,
                ConstantsDB.codigoCHF + "=" + codCasa, null, null, null, null, null);
        if (enccasas != null && enccasas.getCount() > 0) {
            enccasas.moveToFirst();
            mEncuestaCasas.clear();
            do{
                mEncuestaCasas.add(crearEncuestaCasa(enccasas));
            } while (enccasas.moveToNext());
        }
        enccasas.close();
        return mEncuestaCasas;
    }

    public ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa> getListaEncuestaCasasChfHoy() throws SQLException {
        Cursor encuestas = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa> mEncuestaCasas = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa>();
        encuestas = mDb.query(true, ConstantsDB.ENC_CASA_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.codigoCHF + " , " +ConstantsDB.TODAY, null);
        if (encuestas != null && encuestas.getCount() > 0) {
            encuestas.moveToFirst();
            mEncuestaCasas.clear();
            do{
                if(!encuestas.isNull(encuestas.getColumnIndex(ConstantsDB.codigoCHF))) {
                    mEncuestaCasas.add(crearEncuestaCasa(encuestas));
                }

            } while (encuestas.moveToNext());
        }
        encuestas.close();
        return mEncuestaCasas;
    }

    /**
     * Obtiene Lista todas las encuestas casas para un codigo
     *
     * @return lista con EncuestaCasa
     */
    public ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa> getListaEncuestaCasas() throws SQLException {
        Cursor enccasas = null;
        ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa> mEncuestaCasas = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa>();
        enccasas = mDb.query(true, ConstantsDB.ENC_CASA_TABLE, null,
                null, null, null, null, ConstantsDB.COD_CASA + " , " +ConstantsDB.TODAY, null);
        if (enccasas != null && enccasas.getCount() > 0) {
            enccasas.moveToFirst();
            mEncuestaCasas.clear();
            do{
                mEncuestaCasas.add(crearEncuestaCasa(enccasas));
            } while (enccasas.moveToNext());
        }
        enccasas.close();
        return mEncuestaCasas;
    }
    public ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa> getListaEncuestaCasasHoy() throws SQLException {
        Cursor enccasas = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa> mEncuestaCasas = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa>();
        enccasas = mDb.query(true, ConstantsDB.ENC_CASA_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.COD_CASA + " , " +ConstantsDB.TODAY, null);
        if (enccasas != null && enccasas.getCount() > 0) {
            enccasas.moveToFirst();
            mEncuestaCasas.clear();
            do{
                mEncuestaCasas.add(crearEncuestaCasa(enccasas));
            } while (enccasas.moveToNext());
        }
        enccasas.close();
        return mEncuestaCasas;
    }

    /**
     * Crea una EncuestaCasa
     *
     * @return EncuestaCasa
     */
    public ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa crearEncuestaCasa(Cursor enccasas){
        ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa mEncCasa = new ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaCasa();
        Date fecha = new Date(enccasas.getLong(enccasas.getColumnIndex(ConstantsDB.TODAY)));
        //EncuestaCasaId enccasaId = new EncuestaCasaId();
        //enccasaId.setCodCasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.COD_CASA)));
        //enccasaId.setFechaEncCasa(new Date(enccasas.getLong(enccasas.getColumnIndex(ConstantsDB.FECHA_ENC_CASA))));

        //mEncCasa.setEncCasaId(enccasaId);
        mEncCasa.setCodigo(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.codigo)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.COD_CASA))) mEncCasa.setCodCasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.COD_CASA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.codigoCHF))) mEncCasa.setCodCasaChf(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.codigoCHF)));
        mEncCasa.setFechaEncCasa(new Date(enccasas.getLong(enccasas.getColumnIndex(ConstantsDB.FECHA_ENC_CASA))));

        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN1))) mEncCasa.setCvivencasa1(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN1)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN2))) mEncCasa.setCvivencasa2(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN2)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN3))) mEncCasa.setCvivencasa3(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN3)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN4))) mEncCasa.setCvivencasa4(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN4)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN5))) mEncCasa.setCvivencasa5(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN5)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN6))) mEncCasa.setCvivencasa6(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN6)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CVIVEN7))) mEncCasa.setCvivencasa7(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CVIVEN7)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CCUARTOS))) mEncCasa.setCcuartos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CCUARTOS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.GRIFO))) mEncCasa.setGrifo(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.GRIFO)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.GRIFOCOM))) mEncCasa.setGrifoComSN(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.GRIFOCOM)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.HORASAGUA))) mEncCasa.sethorasagua(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.HORASAGUA)));
        mEncCasa.setMcasa(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.MCASA)));
        mEncCasa.setOcasa(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.OCASA)));
        mEncCasa.setPiso(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PISO)));
        mEncCasa.setOpiso(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.OPISO)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.TECHO))) mEncCasa.setTecho(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.TECHO)));
        mEncCasa.setOtecho(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.OTECHO)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CPROPIA))) mEncCasa.setCpropia(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CPROPIA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.ABANICOS))) mEncCasa.setCabanicos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.ABANICOS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.TVS))) mEncCasa.setCtelevisores(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.TVS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.REFRI))) mEncCasa.setCrefrigeradores(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.REFRI)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.MOTO))) mEncCasa.setMoto(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.MOTO)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CARRO))) mEncCasa.setCarro(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CARRO)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.LENA))) mEncCasa.setCocinalena(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.LENA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.ANIMALES))) mEncCasa.setAnimalesSN(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.ANIMALES)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.POLLOS))) mEncCasa.setPollos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.POLLOS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.POLLOSCASA))) mEncCasa.setPolloscasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.POLLOSCASA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.PATOS))) mEncCasa.setPatos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.PATOS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.PATOSCASA))) mEncCasa.setPatoscasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.PATOSCASA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.PERROS))) mEncCasa.setPerros(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.PERROS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.PERROSCASA))) mEncCasa.setPerroscasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.PERROSCASA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.GATOS))) mEncCasa.setGatos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.GATOS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.GATOSCASA))) mEncCasa.setGatoscasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.GATOSCASA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CERDOS))) mEncCasa.setCerdos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CERDOS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CERDOSCASA))) mEncCasa.setCerdoscasa(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CERDOSCASA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.otrorecurso1))) mEncCasa.setOtrorecurso1(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.otrorecurso2))) mEncCasa.setOtrorecurso2(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.otrorecurso2)));

        //CHF + NUEVAS PREGUNTAS MA2018
        mEncCasa.setViveEmbEnCasa(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.viveEmbEnCasa)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CANTIDAD_CUARTOS))) mEncCasa.setCantidadCuartos(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CANTIDAD_CUARTOS)));
        mEncCasa.setAlmacenaAgua(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.ALMACENA_AGUA)));
        mEncCasa.setAlmacenaEnBarriles(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.ALMACENA_EN_BARRILES)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUMERO_BARRILES))) mEncCasa.setNumeroBarriles(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUMERO_BARRILES)));
        mEncCasa.setBarrilesTapados(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.BARRILES_TAPADOS)));
        mEncCasa.setBarrilesConAbate(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.BARRILES_CON_ABATE)));
        mEncCasa.setAlmacenaEnTanques(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.ALMACENA_EN_TANQUES)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUMERO_TANQUES))) mEncCasa.setNumeroTanques(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUMERO_TANQUES)));
        mEncCasa.setTanquesTapados(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TANQUES_TAPADOS)));
        mEncCasa.setTanquesConAbate(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TANQUES_CON_ABATE)));
        mEncCasa.setAlmacenaEnPilas(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.ALMACENA_EN_PILAS)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUMERO_PILAS))) mEncCasa.setNumeroPilas(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUMERO_PILAS)));
        mEncCasa.setPilasTapadas(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PILAS_TAPADAS)));
        mEncCasa.setPilasConAbate(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PILAS_CON_ABATE)));
        mEncCasa.setAlmacenaEnOtrosrecip(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.ALMACENA_EN_OTROSRECIP)));
        mEncCasa.setDescOtrosRecipientes(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.DESC_OTROS_RECIPIENTES)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUMERO_OTROS_RECIPIENTES))) mEncCasa.setNumeroOtrosRecipientes(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUMERO_OTROS_RECIPIENTES)));
        mEncCasa.setOtrosRecipTapados(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.OTROS_RECIP_TAPADOS)));
        mEncCasa.setOtrosrecipConAbate(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.OTROSRECIP_CON_ABATE)));
        mEncCasa.setUbicacionLavandero(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.UBICACION_LAVANDERO)));
        mEncCasa.setTieneAbanico(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_ABANICO)));
        mEncCasa.setTieneTelevisor(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_TELEVISOR)));
        mEncCasa.setTieneRefrigeradorFreezer(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_REFRIGERADOR_FREEZER)));
        mEncCasa.setTieneAireAcondicionado(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_AIRE_ACONDICIONADO)));
        mEncCasa.setFuncionamientoAire(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.FUNCIONAMIENTO_AIRE)));
        mEncCasa.setOpcFabCarro(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.opcFabCarro)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.yearNow))) mEncCasa.setYearNow(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.yearNow)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.yearFabCarro))) mEncCasa.setYearFabCarro(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.yearFabCarro)));
        mEncCasa.setTieneMicrobus(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_MICROBUS)));
        mEncCasa.setTieneCamioneta(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_CAMIONETA)));
        mEncCasa.setTieneCamion(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_CAMION)));
        mEncCasa.setTieneOtroMedioTrans(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_OTRO_MEDIO_TRANS)));
        mEncCasa.setDescOtroMedioTrans(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.DESC_OTRO_MEDIO_TRANS)));
        mEncCasa.setCocinaConLenia(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.COCINA_CON_LENIA)));
        mEncCasa.setUbicacionCocinaLenia(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.UBICACION_COCINA_LENIA)));
        mEncCasa.setPeriodicidadCocinaLenia(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PERIODICIDAD_COCINA_LENIA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUM_DIARIO_COCINA_LENIA))) mEncCasa.setNumDiarioCocinaLenia(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUM_DIARIO_COCINA_LENIA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUM_SEMANAL_COCINA_LENIA))) mEncCasa.setNumSemanalCocinaLenia(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUM_SEMANAL_COCINA_LENIA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUM_QUINCENAL_COCINA_LENIA))) mEncCasa.setNumQuincenalCocinaLenia(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUM_QUINCENAL_COCINA_LENIA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.NUM_MENSUAL_COCINA_LENIA))) mEncCasa.setNumMensualCocinaLenia(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.NUM_MENSUAL_COCINA_LENIA)));
        mEncCasa.setTieneGallinas(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_GALLINAS)));
        mEncCasa.setTienePatos(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_PATOS)));
        mEncCasa.setTienePerros(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_PERROS)));
        mEncCasa.setTieneGatos(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_GATOS)));
        mEncCasa.setTieneCerdos(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.TIENE_CERDOS)));
        mEncCasa.setPersFumaDentroCasa(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PERS_FUMA_DENTRO_CASA)));
        mEncCasa.setMadreFuma(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.MADRE_FUMA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CANT_CIGARRILLOS_MADRE))) mEncCasa.setCantCigarrillosMadre(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CANT_CIGARRILLOS_MADRE)));
        mEncCasa.setPadreFuma(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PADRE_FUMA)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CANT_CIGARRILLOS_PADRE))) mEncCasa.setCantCigarrillosPadre(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CANT_CIGARRILLOS_PADRE)));
        mEncCasa.setOtrosFuman(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.OTROS_FUMAN)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CANT_OTROS_FUMAN))) mEncCasa.setCantOtrosFuman(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CANT_OTROS_FUMAN)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.CANT_CIGARRILLOS_OTROS))) mEncCasa.setCantCigarrillosOtros(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.CANT_CIGARRILLOS_OTROS)));
        mEncCasa.setServRecolBasura(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.servRecolBasura)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.frecServRecolBasura))) mEncCasa.setFrecServRecolBasura(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.frecServRecolBasura)));
        mEncCasa.setLlantasOtrosContConAgua(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.llantasOtrosContConAgua)));
        mEncCasa.setOpcFabMicrobus(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.opcFabMicrobus)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.yearFabMicrobus))) mEncCasa.setYearFabMicrobus(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.yearFabMicrobus)));
        mEncCasa.setOpcFabCamioneta(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.opcFabCamioneta)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.yearFabCamioneta))) mEncCasa.setYearFabCamioneta(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.yearFabCamioneta)));
        mEncCasa.setOpcFabCamion(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.opcFabCamion)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.yearFabCamion))) mEncCasa.setYearFabCamion(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.yearFabCamion)));
        mEncCasa.setOpcFabOtroMedioTrans(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.opcFabOtroMedioTrans)));
        if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.yearFabOtroMedioTrans))) mEncCasa.setYearFabOtroMedioTrans(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.yearFabOtroMedioTrans)));
        //MA2020
		mEncCasa.setCambiadoCasa(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.cambiadoCasa)));
		mEncCasa.setRemodelacionCasa(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.remodelacionCasa)));
		mEncCasa.setTieneVehiculo(enccasas.getString(enccasas.getColumnIndex(ConstantsDB.tieneVehiculo)));
		if(!enccasas.isNull(enccasas.getColumnIndex(ConstantsDB.participante))) mEncCasa.setParticipante(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.participante)));

        Boolean borrado = enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.DELETED))>0;
        mEncCasa.setMovilInfo(new MovilInfo(enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.FILE_PATH)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.STATUS)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.START)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.END)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.DEVICE_ID)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                enccasas.getString(enccasas.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.REC1)),
                enccasas.getInt(enccasas.getColumnIndex(ConstantsDB.REC2))));

        return mEncCasa;
    }

    /**
     * Obtiene Lista todas las encuestas participantes sin enviar
     *
     * @return lista con EncuestaParticipante
     */
    public List<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante> getListaEncuestaParticipantesSinEnviar() throws SQLException {
        Cursor encparts = null;
        List<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante> mEncuestaParticipantes = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante>();
        encparts = mDb.query(true, ConstantsDB.ENC_PART_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (encparts != null && encparts.getCount() > 0) {
            encparts.moveToFirst();
            mEncuestaParticipantes.clear();
            do{
                mEncuestaParticipantes.add(crearEncuestaParticipante(encparts));
            } while (encparts.moveToNext());
        }
        encparts.close();
        return mEncuestaParticipantes;
    }

    /**
     * Obtiene Lista todas las encuestas participantes de un codigo
     *
     * @return lista con EncuestaParticipante
     */
    public ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante> getListaEncuestaParticipantes(Integer codigo) throws SQLException {
        Cursor encparts = null;
        ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante> mEncuestaParticipantes = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante>();
        encparts = mDb.query(true, ConstantsDB.ENC_PART_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (encparts != null && encparts.getCount() > 0) {
            encparts.moveToFirst();
            mEncuestaParticipantes.clear();
            do{
                mEncuestaParticipantes.add(crearEncuestaParticipante(encparts));
            } while (encparts.moveToNext());
        }
        encparts.close();
        return mEncuestaParticipantes;
    }

    public ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante> getListaEncuestaParticipantesHoy() throws SQLException {
        Cursor encparts = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante> mEncuestaParticipantes = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante>();
        encparts = mDb.query(true, ConstantsDB.ENC_PART_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (encparts != null && encparts.getCount() > 0) {
            encparts.moveToFirst();
            mEncuestaParticipantes.clear();
            do{
                mEncuestaParticipantes.add(crearEncuestaParticipante(encparts));
            } while (encparts.moveToNext());
        }
        encparts.close();
        return mEncuestaParticipantes;
    }

    /**
     * Crea una EncuestaParticipante
     *
     * @return EncuestaParticipante
     */
    public ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante crearEncuestaParticipante(Cursor encparticipantess){
        ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante mEncPart = new ni.org.ics.estudios.appmovil.domain.muestreoanual.EncuestaParticipante();
        Date fecha = new Date(encparticipantess.getLong(encparticipantess.getColumnIndex(ConstantsDB.TODAY)));
        EncuestaParticipanteId encparId = new EncuestaParticipanteId();
        encparId.setCodigo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CODIGO)));
        encparId.setFechaEncPar(new Date(encparticipantess.getLong(encparticipantess.getColumnIndex(ConstantsDB.FECHA_ENC_PAR))));
        mEncPart.setEpId(encparId);
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.FIEBRE))) mEncPart.setFiebre(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.FIEBRE)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.TFIEBRE))) mEncPart.setTiemFieb(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.TFIEBRE)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.LUGCONS))) mEncPart.setLugarCons(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.LUGCONS)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.NOCS))) mEncPart.setNoCs(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.NOCS)));
        mEncPart.setAutomed(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.AUTOMED)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ESC))) mEncPart.setEscuela(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ESC)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.GRADO))) mEncPart.setGrado(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.GRADO)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.TURNO))) mEncPart.setTurno(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.TURNO)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.NESC))) mEncPart.setnEscuela(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.NESC)));
        mEncPart.setOtraEscuela(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.OESC)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CUIDAN))) mEncPart.setCuidan(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CUIDAN)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CCUIDAN))) mEncPart.setCuantosCuidan(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CCUIDAN)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CQVIVE))) mEncPart.setCqVive(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CQVIVE)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.LUGPARTO))) mEncPart.setLugarPart(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.LUGPARTO)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.PAPAALF))) mEncPart.setPapaAlf(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.PAPAALF)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.PAPANIVEL))) mEncPart.setPapaNivel(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.PAPANIVEL)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.PAPATRA))) mEncPart.setPapaTra(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.PAPATRA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.PAPATIPOT))) mEncPart.setPapaTipoTra(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.PAPATIPOT)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.MAMAALF))) mEncPart.setMamaAlf(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.MAMAALF)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.MAMANIVEL))) mEncPart.setMamaNivel(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.MAMANIVEL)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.MAMATRA))) mEncPart.setMamaTra(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.MAMATRA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.MAMATIPOT))) mEncPart.setMamaTipoTra(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.MAMATIPOT)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.COMPARTEHAB))) mEncPart.setComparteHab(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.COMPARTEHAB)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HAB1))) mEncPart.setHab1(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HAB1)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HAB2))) mEncPart.setHab2(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HAB2)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HAB3))) mEncPart.setHab3(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HAB3)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HAB4))) mEncPart.setHab4(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HAB4)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HAB5))) mEncPart.setHab5(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HAB5)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HAB6))) mEncPart.setHab6(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HAB6)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.COMPARTECAMA))) mEncPart.setComparteCama(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.COMPARTECAMA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CAMA1))) mEncPart.setCama1(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CAMA1)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CAMA2))) mEncPart.setCama2(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CAMA2)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CAMA3))) mEncPart.setCama3(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CAMA3)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CAMA4))) mEncPart.setCama4(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CAMA4)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CAMA5))) mEncPart.setCama5(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CAMA5)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CAMA6))) mEncPart.setCama6(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CAMA6)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ASMA))) mEncPart.setAsma(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ASMA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.SILB12))) mEncPart.setSilb12m(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.SILB12)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.SIT1))) mEncPart.setSitResf(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.SIT1)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.SIT2))) mEncPart.setSitEjer(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.SIT2)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.SILB01))) mEncPart.setSilbMesPas(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.SILB01)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.DIFHAB))) mEncPart.setDifHablar(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.DIFHAB)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.VECDIFHAB))) mEncPart.setVecHablar(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.VECDIFHAB)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.DIFDOR))) mEncPart.setDifDormir(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.DIFDOR)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.SUENOPER))) mEncPart.setSuenoPer(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.SUENOPER)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.TOS12))) mEncPart.setTos12m(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.TOS12)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.VECESTOS))) mEncPart.setVecesTos(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.VECESTOS)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.TOS3DIAS))) mEncPart.setTos3Dias(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.TOS3DIAS)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.HOSP12M))) mEncPart.setHosp12m(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.HOSP12M)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.MED12M))) mEncPart.setMed12m(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.MED12M)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.DEP12M))) mEncPart.setDep12m(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.DEP12M)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CRISIS))) mEncPart.setCrisis(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CRISIS)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.FRECASMA))) mEncPart.setFrecAsma(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.FRECASMA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.FUMA))) mEncPart.setFumaSN(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.FUMA)));
        mEncPart.setQuienFuma(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.QUIENFUMA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CIGMADRE))) mEncPart.setCantCigarrosMadre(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CIGMADRE)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CIGPADRE))) mEncPart.setCantCigarrosPadre(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CIGPADRE)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CIGOTRO))) mEncPart.setCantCigarrosOtros(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CIGOTRO)));

        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rash))) mEncPart.setRash(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rash)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.mesActual))) mEncPart.setMesActual(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.mesActual)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.yearActual))) mEncPart.setYearActual(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.yearActual)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rash_year))) mEncPart.setRash_year(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rash_year)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rash_mes))) mEncPart.setRash_mes(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rash_mes)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rash_mesact))) mEncPart.setRash_mesact(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rash_mesact)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rashCara))) mEncPart.setRashCara(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rashCara)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rashMiembrosSup))) mEncPart.setRashMiembrosSup(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rashMiembrosSup)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rashTorax))) mEncPart.setRashTorax(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rashTorax)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rashAbdomen))) mEncPart.setRashAbdomen(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rashAbdomen)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rashMiembrosInf))) mEncPart.setRashMiembrosInf(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rashMiembrosInf)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.rashDias))) mEncPart.setRashDias(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.rashDias)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo))) mEncPart.setOjoRojo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_year))) mEncPart.setOjoRojo_year(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_year)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_mes))) mEncPart.setOjoRojo_mes(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_mes)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_mesact))) mEncPart.setOjoRojo_mesact(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_mesact)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_Dias))) mEncPart.setOjoRojo_Dias(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo_Dias)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.hormigueo))) mEncPart.setHormigueo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.hormigueo)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_year))) mEncPart.setHormigueo_year(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_year)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_mes))) mEncPart.setHormigueo_mes(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_mes)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_mesact))) mEncPart.setHormigueo_mesact(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_mesact)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_Dias))) mEncPart.setHormigueo_Dias(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.hormigueo_Dias)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.consultaRashHormigueo))) mEncPart.setConsultaRashHormigueo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.consultaRashHormigueo)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.uSaludRashHormigueo))) mEncPart.setuSaludRashHormigueo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.uSaludRashHormigueo)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.cSaludRashHormigueo))) mEncPart.setcSaludRashHormigueo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.cSaludRashHormigueo)));
        mEncPart.setoCSRashHormigueo(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.oCSRashHormigueo)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.pSRashHormigueo))) mEncPart.setpSRashHormigueo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.pSRashHormigueo)));
        mEncPart.setoPSRashHormigueo(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.oPSRashHormigueo)));
        mEncPart.setDiagRashHormigueo(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.diagRashHormigueo)));
        mEncPart.setChPapaMama(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.chPapaMama)));
        if (encparticipantess.getLong(encparticipantess.getColumnIndex(ConstantsDB.fechana_papa)) > 0) mEncPart.setFechana_papa(new Date(encparticipantess.getLong(encparticipantess.getColumnIndex(ConstantsDB.fechana_papa))));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.cal_edad_papa))) mEncPart.setCal_edad_papa(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.cal_edad_papa)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.cal_edad2_papa))) mEncPart.setCal_edad2_papa(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.cal_edad2_papa)));
        mEncPart.setNombpapa1(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.nombpapa1)));
        mEncPart.setNombpapa2(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.nombpapa2)));
        mEncPart.setApellipapa1(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.apellipapa1)));
        mEncPart.setApellipapa2(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.apellipapa2)));
        if (encparticipantess.getLong(encparticipantess.getColumnIndex(ConstantsDB.fechana_mama)) > 0) mEncPart.setFechana_mama(new Date(encparticipantess.getLong(encparticipantess.getColumnIndex(ConstantsDB.fechana_mama))));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.cal_edad_mama))) mEncPart.setCal_edad_mama(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.cal_edad_mama)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.cal_edad2_mama))) mEncPart.setCal_edad2_mama(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.cal_edad2_mama)));
        mEncPart.setNombmama1(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.nombmama1)));
        mEncPart.setNombmama2(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.nombmama2)));
        mEncPart.setApellimama1(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.apellimama1)));
        mEncPart.setApellimama2(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.apellimama2)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.otrorecurso1))) mEncPart.setOtrorecurso1(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.otrorecurso2))) mEncPart.setOtrorecurso2(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.otrorecurso2)));

        //CHF + NUEVAS PREGUNTAS MA2018
        mEncPart.setEmancipado(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.EMANCIPADO)));
        mEncPart.setDescEnmancipado(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.descEnmancipado)));
        mEncPart.setOtraDescEmanc(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.otraDescEmanc)));
        mEncPart.setEmbarazada(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.EMBARAZADA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.SEMANAS_EMBARAZO))) mEncPart.setSemanasEmbarazo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.SEMANAS_EMBARAZO)));
        mEncPart.setAlfabeto(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.ALFABETO)));
        mEncPart.setNivelEducacion(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.NIVEL_EDUCACION)));
        mEncPart.setTrabaja(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TRABAJA)));
        mEncPart.setTipoTrabajo(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TIPO_TRABAJO)));
        mEncPart.setOcupacionActual(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.OCUPACION_ACTUAL)));
        mEncPart.setNinoAsisteEscuela(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.NINO_ASISTE_ESCUELA)));
        mEncPart.setGradoEstudiaNino(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.GRADO_ESTUDIA_NINO)));
        mEncPart.setNinoTrabaja(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.NINO_TRABAJA)));
        mEncPart.setOcupacionActualNino(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.OCUPACION_ACTUAL_NINO)));
        mEncPart.setPadreEstudio(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.PADRE_ESTUDIO)));
        mEncPart.setCodigoPadreEstudio(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.CODIGO_PADRE_ESTUDIO)));
        mEncPart.setPadreAlfabeto(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.PADRE_ALFABETO)));
        mEncPart.setTrabajaPadre(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TRABAJA_PADRE)));
        mEncPart.setMadreEstudio(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.MADRE_ESTUDIO)));
        mEncPart.setCodigoMadreEstudio(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.CODIGO_MADRE_ESTUDIO)));
        mEncPart.setMadreAlfabeta(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.MADRE_ALFABETA)));
        mEncPart.setTrabajaMadre(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TRABAJA_MADRE)));
        mEncPart.setFuma(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.FUMACHF)));
        mEncPart.setPeriodicidadFuna(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.PERIODICIDAD_FUNA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CANTIDAD_CIGARRILLOS))) mEncPart.setCantidadCigarrillos(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CANTIDAD_CIGARRILLOS)));
        mEncPart.setFumaDentroCasa(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.FUMA_DENTRO_CASA)));
        mEncPart.setTuberculosisPulmonarActual(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TUBERCULOSIS_PULMONAR_ACTUAL)));
        mEncPart.setFechaDiagTubpulActual(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.FECHA_DIAG_TUBPUL_ACTUAL)));
        mEncPart.setTratamientoTubpulActual(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TRATAMIENTO_TUBPUL_ACTUAL)));
        mEncPart.setCompletoTratamientoTubpulActual(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.COMPLETO_TRATAMIENTO_TUBPUL_ACTUAL)));
        mEncPart.setTuberculosisPulmonarPasado(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TUBERCULOSIS_PULMONAR_PASADO)));
        mEncPart.setFechaDiagTubpulPasadoDes(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.FECHA_DIAG_TUBPUL_PASADO_DES)));
        mEncPart.setFechaDiagTubpulPasado(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.FECHA_DIAG_TUBPUL_PASADO)));
        mEncPart.setTratamientoTubpulPasado(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TRATAMIENTO_TUBPUL_PASADO)));
        mEncPart.setCompletoTratamientoTubpulPas(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.COMPLETO_TRATAMIENTO_TUBPUL_PAS)));
        mEncPart.setAlergiaRespiratoria(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.ALERGIA_RESPIRATORIA)));
        mEncPart.setCardiopatia(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.CARDIOPATIA)));
        mEncPart.setEnfermPulmonarObstCronica(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.ENFERM_PULMONAR_OBST_CRONICA)));
        mEncPart.setDiabetes(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.DIABETES)));
        mEncPart.setPresionAlta(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.PRESION_ALTA)));
        mEncPart.setTosSinFiebreResfriado(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.TOS_SIN_FIEBRE_RESFRIADO)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.CANT_ENFERM_CUADROS_RESP))) mEncPart.setCantEnfermCuadrosResp(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.CANT_ENFERM_CUADROS_RESP)));
        mEncPart.setOtrasEnfermedades(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.OTRAS_ENFERMEDADES)));
        mEncPart.setDescOtrasEnfermedades(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.DESC_OTRAS_ENFERMEDADES)));
        mEncPart.setVacunaInfluenza(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.VACUNA_INFLUENZA)));
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.ANIO_VACUNA_INFLUENZA))) mEncPart.setAnioVacunaInfluenza(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ANIO_VACUNA_INFLUENZA)));
        mEncPart.setRash6m(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.rash6m)));
        mEncPart.setEstudiosAct(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.estudiosAct)));
        mEncPart.setOjoRojo6m(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.ojoRojo6m)));
        //MA2019
        if(!encparticipantess.isNull(encparticipantess.getColumnIndex(ConstantsDB.vacunaInfluenzaMes))) mEncPart.setVacunaInfluenzaMes(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.vacunaInfluenzaMes)));
        mEncPart.setVacunaInfluenzaCSSF(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.vacunaInfluenzaCSSF)));
        mEncPart.setVacunaInfluenzaOtro(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.vacunaInfluenzaOtro)));
        mEncPart.setNombreCDI(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.nombreCDI)));
        mEncPart.setDireccionCDI(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.direccionCDI)));
        //MA2020
		mEncPart.setEnfermedadCronica(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.enfermedadCronica)));
		mEncPart.setOtroLugarCuidan(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.otroLugarCuidan)));
		mEncPart.setTenidoDengue(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.tenidoDengue)));
		mEncPart.setUnidadSaludDengue(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.unidadSaludDengue)));
		mEncPart.setCentroSaludDengue(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.centroSaludDengue)));
		mEncPart.setOtroCentroSaludDengue(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.otroCentroSaludDengue)));
		mEncPart.setPuestoSaludDengue(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.puestoSaludDengue)));
		mEncPart.setOtroPuestoSaludDengue(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.otroPuestoSaludDengue)));
		mEncPart.setHospitalDengue(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.hospitalDengue)));
		mEncPart.setOtroHospitalDengue(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.otroHospitalDengue)));
		mEncPart.setHospitalizadoDengue(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.hospitalizadoDengue)));
		mEncPart.setAmbulatorioDengue(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.ambulatorioDengue)));
		mEncPart.setDiagMedicoDengue(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.diagMedicoDengue)));
		mEncPart.setRashUA(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.rashUA)));
		mEncPart.setConsultaRashUA(encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.consultaRashUA)));

        Boolean borrado = encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.DELETED))>0;
        mEncPart.setMovilInfo(new MovilInfo(encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.FILE_PATH)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.STATUS)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.START)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.END)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.DEVICE_ID)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                encparticipantess.getString(encparticipantess.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.REC1)),
                encparticipantess.getInt(encparticipantess.getColumnIndex(ConstantsDB.REC2))));
        return mEncPart;
    }


    /**
     * Obtiene Lista todas las encuestas de lactancia sin enviar
     *
     * @return lista con LactanciaMaterna
     */
    public List<LactanciaMaterna> getListaLactanciaMaternasSinEnviar() throws SQLException {
        Cursor enclacts = null;
        List<LactanciaMaterna> mLactanciaMaternas = new ArrayList<LactanciaMaterna>();
        enclacts = mDb.query(true, ConstantsDB.LACT_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (enclacts != null && enclacts.getCount() > 0) {
            enclacts.moveToFirst();
            mLactanciaMaternas.clear();
            do{
                mLactanciaMaternas.add(crearLactanciaMaterna(enclacts));
            } while (enclacts.moveToNext());
        }
        enclacts.close();
        return mLactanciaMaternas;
    }

    /**
     * Obtiene Lista todas las encuestas de lactancia de un codigo
     *
     * @return lista con LactanciaMaterna
     */
    public ArrayList<LactanciaMaterna> getListaLactanciaMaternas(Integer codigo) throws SQLException {
        Cursor enclacts = null;
        ArrayList<LactanciaMaterna> mLactanciaMaternas = new ArrayList<LactanciaMaterna>();
        enclacts = mDb.query(true, ConstantsDB.LACT_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (enclacts != null && enclacts.getCount() > 0) {
            enclacts.moveToFirst();
            mLactanciaMaternas.clear();
            do{
                mLactanciaMaternas.add(crearLactanciaMaterna(enclacts));
            } while (enclacts.moveToNext());
        }
        enclacts.close();
        return mLactanciaMaternas;
    }

    public ArrayList<LactanciaMaterna> getListaLactanciaMaternasHoy() throws SQLException {
        Cursor enclacts = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<LactanciaMaterna> mLactanciaMaternas = new ArrayList<LactanciaMaterna>();
        enclacts = mDb.query(true, ConstantsDB.LACT_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (enclacts != null && enclacts.getCount() > 0) {
            enclacts.moveToFirst();
            mLactanciaMaternas.clear();
            do{
                mLactanciaMaternas.add(crearLactanciaMaterna(enclacts));
            } while (enclacts.moveToNext());
        }
        enclacts.close();
        return mLactanciaMaternas;
    }

    /**
     * Crea una LactanciaMaterna
     *
     * @return LactanciaMaterna
     */
    public LactanciaMaterna crearLactanciaMaterna(Cursor lactanciasmaternas){
        LactanciaMaterna mLacMat = new LactanciaMaterna();
        Date fecha = new Date(lactanciasmaternas.getLong(lactanciasmaternas.getColumnIndex(ConstantsDB.TODAY)));
        LactanciaMaternaId lacMatId = new LactanciaMaternaId();
        lacMatId.setCodigo(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.CODIGO)));
        lacMatId.setFechaEncLM(new Date(lactanciasmaternas.getLong(lactanciasmaternas.getColumnIndex(ConstantsDB.FECHA_ENC_LACT))));

        mLacMat.setLmId(lacMatId);
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.EDAD))) mLacMat.setEdad(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.EDAD)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.DIOPECHO))) mLacMat.setDioPecho(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.DIOPECHO)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.TIEMPECHO))) mLacMat.setTiemPecho(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.TIEMPECHO)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.MESDIOPECHO))) mLacMat.setMesDioPecho(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.MESDIOPECHO)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.PECHOEXC))) mLacMat.setPechoExc(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.PECHOEXC)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.PECHOEXCANT))) mLacMat.setPechoExcAntes(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.PECHOEXCANT)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.TPECHOEXCANT))) mLacMat.setTiempPechoExcAntes(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.TPECHOEXCANT)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.MPECHOEXCANT))) mLacMat.setMestPechoExc(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.MPECHOEXCANT)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.FORMALIM))) mLacMat.setFormAlim(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.FORMALIM)));
        mLacMat.setOtraAlim(lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.OTRALIM)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.EDADLIQDP))) mLacMat.setEdadLiqDistPecho(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.EDADLIQDP)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.MESLIQDP))) mLacMat.setMesDioLiqDisPecho(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.MESLIQDP)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.EDADLIQDL))) mLacMat.setEdadLiqDistLeche(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.EDADLIQDL)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.MESLIQDL))) mLacMat.setMesDioLiqDisLeche(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.MESLIQDL)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.EDADALIMS))) mLacMat.setEdAlimSolidos(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.EDADALIMS)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.MESALIMS))) mLacMat.setMesDioAlimSol(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.MESALIMS)));

        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.otrorecurso1))) mLacMat.setOtrorecurso1(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!lactanciasmaternas.isNull(lactanciasmaternas.getColumnIndex(ConstantsDB.otrorecurso2))) mLacMat.setOtrorecurso2(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.otrorecurso2)));

        Boolean borrado = lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.DELETED))>0;
        mLacMat.setMovilInfo(new MovilInfo(lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.FILE_PATH)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.STATUS)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.START)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.END)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.DEVICE_ID)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                lactanciasmaternas.getString(lactanciasmaternas.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.REC1)),
                lactanciasmaternas.getInt(lactanciasmaternas.getColumnIndex(ConstantsDB.REC2))));
        return mLacMat;
    }


    /**
     * Obtiene Lista todas los PesoyTalla sin enviar
     *
     * @return lista con PesoyTalla
     */
    public List<PesoyTalla> getListaPesoyTallasSinEnviar() throws SQLException {
        Cursor pyts = null;
        List<PesoyTalla> mPesoyTallas = new ArrayList<PesoyTalla>();
        pyts = mDb.query(true, ConstantsDB.PT_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (pyts != null && pyts.getCount() > 0) {
            pyts.moveToFirst();
            mPesoyTallas.clear();
            do{
                mPesoyTallas.add(crearPesoyTalla(pyts));
            } while (pyts.moveToNext());
        }
        pyts.close();
        return mPesoyTallas;
    }

    /**
     * Obtiene Lista todas los PesoyTalla de un codigo
     *
     * @return lista con PesoyTalla
     */
    public ArrayList<PesoyTalla> getListaPesoyTallas(Integer codigo) throws SQLException {
        Cursor pyts = null;
        ArrayList<PesoyTalla> mPesoyTallas = new ArrayList<PesoyTalla>();
        pyts = mDb.query(true, ConstantsDB.PT_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (pyts != null && pyts.getCount() > 0) {
            pyts.moveToFirst();
            mPesoyTallas.clear();
            do{
                mPesoyTallas.add(crearPesoyTalla(pyts));
            } while (pyts.moveToNext());
        }
        pyts.close();
        return mPesoyTallas;
    }

    public ArrayList<PesoyTalla> getListaPesoyTallas() throws SQLException {
        Cursor pyts = null;
        ArrayList<PesoyTalla> mPesoyTallas = new ArrayList<PesoyTalla>();
        pyts = mDb.query(true, ConstantsDB.PT_TABLE, null,
                null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (pyts != null && pyts.getCount() > 0) {
            pyts.moveToFirst();
            mPesoyTallas.clear();
            do{
                mPesoyTallas.add(crearPesoyTalla(pyts));
            } while (pyts.moveToNext());
        }
        pyts.close();
        return mPesoyTallas;
    }

    public ArrayList<PesoyTalla> getListaPesoyTallasHoy() throws SQLException {
        Cursor pyts = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<PesoyTalla> mPesoyTallas = new ArrayList<PesoyTalla>();
        pyts = mDb.query(true, ConstantsDB.PT_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (pyts != null && pyts.getCount() > 0) {
            pyts.moveToFirst();
            mPesoyTallas.clear();
            do{
                mPesoyTallas.add(crearPesoyTalla(pyts));
            } while (pyts.moveToNext());
        }
        pyts.close();
        return mPesoyTallas;
    }


    /**
     * Crea un PesoyTalla
     *
     * @return PesoyTalla
     */
    public PesoyTalla crearPesoyTalla(Cursor pesajes){
        PesoyTalla mPyT = new PesoyTalla();
        Date fecha = new Date(pesajes.getLong(pesajes.getColumnIndex(ConstantsDB.TODAY)));
        PesoyTallaId pytId = new PesoyTallaId();
        pytId.setCodigo(pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.CODIGO)));
        pytId.setFechaPT(new Date(pesajes.getLong(pesajes.getColumnIndex(ConstantsDB.FECHA_PT))));

        mPyT.setPtId(pytId);

        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.PESO1))) mPyT.setPeso1(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.PESO1)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.PESO2))) mPyT.setPeso2(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.PESO2)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.PESO3))) mPyT.setPeso3(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.PESO3)));

        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.TALLA1))) mPyT.setTalla1(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.TALLA1)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.TALLA2))) mPyT.setTalla2(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.TALLA2)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.TALLA3))) mPyT.setTalla3(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.TALLA3)));

        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.IMC1))) mPyT.setImc1(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.IMC1)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.IMC2))) mPyT.setImc2(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.IMC2)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.IMC3))) mPyT.setImc3(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.IMC3)));

        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.DIFPESO))) mPyT.setDifPeso(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.DIFPESO)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.DIFTALLA))) mPyT.setDifTalla(pesajes.getDouble(pesajes.getColumnIndex(ConstantsDB.DIFTALLA)));
        mPyT.setTomoMedidaSn(pesajes.getString(pesajes.getColumnIndex(ConstantsDB.tomoMedidaSn)));
        mPyT.setRazonNoTomoMedidas(pesajes.getString(pesajes.getColumnIndex(ConstantsDB.razonNoTomoMedidas)));

        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.otrorecurso1))) mPyT.setOtrorecurso1(pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!pesajes.isNull(pesajes.getColumnIndex(ConstantsDB.otrorecurso2))) mPyT.setOtrorecurso2(pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.otrorecurso2)));
		mPyT.setEstudiosAct(pesajes.getString(pesajes.getColumnIndex(ConstantsDB.estudiosAct)));//MA2020

        Boolean borrado = pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.DELETED))>0;
        mPyT.setMovilInfo(new MovilInfo(pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.FILE_PATH)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.STATUS)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.START)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.END)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.DEVICE_ID)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                pesajes.getString(pesajes.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.REC1)),
                pesajes.getInt(pesajes.getColumnIndex(ConstantsDB.REC2))));
        return mPyT;
    }



    /**
     * Obtiene Lista todas las muestras sin enviar
     *
     * @return lista con Muestra
     */
    public List<ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra> getListaMuestrasSinEnviar() throws SQLException {
        Cursor muestreo = null;
        List<ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra> mMuestras = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra>();
        muestreo = mDb.query(true, ConstantsDB.MUESTRA_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (muestreo != null && muestreo.getCount() > 0) {
            muestreo.moveToFirst();
            mMuestras.clear();
            do{
                mMuestras.add(crearMuestra(muestreo));
            } while (muestreo.moveToNext());
        }
        muestreo.close();
        return mMuestras;
    }

    /**
     * Obtiene Lista todas los Muestra de un codigo
     *
     * @return lista con Muestra
     */
    public ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra> getListaMuestras(Integer codigo) throws SQLException {
        Cursor muestras = null;
        ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra> mMuestras = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra>();
        muestras = mDb.query(true, ConstantsDB.MUESTRA_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (muestras != null && muestras.getCount() > 0) {
            muestras.moveToFirst();
            mMuestras.clear();
            do{
                mMuestras.add(crearMuestra(muestras));
            } while (muestras.moveToNext());
        }
        muestras.close();
        return mMuestras;
    }

	public List<ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra> getMuestrasMA(String filtro, String orden) throws SQLException {
		List<ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra> mMuestras = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra>();
		Cursor cursor = crearCursor(ConstantsDB.MUESTRA_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mMuestras.clear();
			do{
				ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra mMuestra = null;
				mMuestra = crearMuestra(cursor);
				mMuestras.add(mMuestra);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mMuestras;
	}

    public ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra> getListaMuestrasHoy() throws SQLException {
        Cursor muestras = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra> mMuestras = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra>();
        muestras = mDb.query(true, ConstantsDB.MUESTRA_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (muestras != null && muestras.getCount() > 0) {
            muestras.moveToFirst();
            mMuestras.clear();
            do{
                mMuestras.add(crearMuestra(muestras));
            } while (muestras.moveToNext());
        }
        muestras.close();
        return mMuestras;
    }

    public ArrayList<DatosPartoBB> getListaDatosPartoBBHoy() throws SQLException {
        Cursor datos = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<DatosPartoBB> mDatosPartoBBs = new ArrayList<DatosPartoBB>();
        datos = mDb.query(true, ConstantsDB.DATOSPARTOBB_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (datos != null && datos.getCount() > 0) {
            datos.moveToFirst();
            mDatosPartoBBs.clear();
            do{
                mDatosPartoBBs.add(DatosPartoBBHelper.crearDatosPartoBB(datos));
            } while (datos.moveToNext());
        }
        datos.close();
        return mDatosPartoBBs;
    }

    public ArrayList<Documentos> getListaDocumentosHoy() throws SQLException {
        Cursor docs = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<Documentos> mDocumentos = new ArrayList<Documentos>();
        docs = mDb.query(true, ConstantsDB.DOCS_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (docs != null && docs.getCount() > 0) {
            docs.moveToFirst();
            mDocumentos.clear();
            do{
                mDocumentos.add(DocumentosHelper.crearDocumentos2(docs));
            } while (docs.moveToNext());
        }
        docs.close();
        return mDocumentos;
    }

    /**
     * Crea una Muestra
     *
     * @return Muestra
     */
    public ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra crearMuestra(Cursor muestreo){
        Date fecha = new Date(muestreo.getLong(muestreo.getColumnIndex(ConstantsDB.TODAY)));
        ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra mMuestra = new ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra();
        MuestraId muestraId = new MuestraId();
        muestraId.setCodigo(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.CODIGO)));
        muestraId.setFechaMuestra(new Date(muestreo.getLong(muestreo.getColumnIndex(ConstantsDB.FECHA_MUESTRA))));

        mMuestra.setmId(muestraId);

        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.MFIEBRE))) mMuestra.setFiebreM(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.MFIEBRE)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.CONSULTA))) mMuestra.setConsulta(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.CONSULTA)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.BHC))) mMuestra.setTuboBHC(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.BHC)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.ROJO))) mMuestra.setTuboRojo(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.ROJO)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.LEU))) mMuestra.setTuboLeu(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.LEU)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.PIN))) mMuestra.setPinchazos(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.PIN)));

        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.bhc_razonNo))) mMuestra.setBhc_razonNo(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.bhc_razonNo)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.rojo_razonNo))) mMuestra.setRojo_razonNo(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.rojo_razonNo)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.pbmc_razonNo))) mMuestra.setPbmc_razonNo(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.pbmc_razonNo)));

        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.bhc_otraRazonNo))) mMuestra.setBhc_otraRazonNo(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.bhc_otraRazonNo)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.rojo_otraRazonNo))) mMuestra.setRojo_otraRazonNo(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.rojo_otraRazonNo)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.pbmc_otraRazonNo))) mMuestra.setPbmc_otraRazonNo(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.pbmc_otraRazonNo)));

        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.horaBHC))) mMuestra.setHoraBHC(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.horaBHC)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.horaPBMC))) mMuestra.setHoraPBMC(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.horaPBMC)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.horaInicioPax))) mMuestra.setHoraInicioPax(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.horaInicioPax)));

        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.horaFinPax))) mMuestra.setHoraFinPax(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.horaFinPax)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.codPax))) mMuestra.setCodPax(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.codPax)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.terreno))) mMuestra.setTerreno(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.terreno)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.estudiosAct))) mMuestra.setEstudiosAct(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.estudiosAct)));
        //MA2019
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.hd_sn))) mMuestra.setHd_sn(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.hd_sn)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.hdPorqueNo))) mMuestra.setHdPorqueNo(muestreo.getString(muestreo.getColumnIndex(ConstantsDB.hdPorqueNo)));
        //MA2020
		if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.tuboPax))) mMuestra.setTuboPax(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.tuboPax)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.otrorecurso1))) mMuestra.setOtrorecurso1(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!muestreo.isNull(muestreo.getColumnIndex(ConstantsDB.otrorecurso2))) mMuestra.setOtrorecurso2(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.otrorecurso2)));

        Boolean borrado = muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.DELETED))>0;
        mMuestra.setMovilInfo(new MovilInfo(muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.FILE_PATH)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.STATUS)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.START)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.END)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.DEVICE_ID)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                muestreo.getString(muestreo.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.REC1)),
                muestreo.getInt(muestreo.getColumnIndex(ConstantsDB.REC2))));
        return mMuestra;
    }


    /**
     * Obtiene Lista todas los obsequios sin enviar
     *
     * @return lista con Obsequio
     */
    public List<Obsequio> getListaObsequiosSinEnviar() throws SQLException {
        Cursor obsentregados = null;
        List<Obsequio> mObsequios = new ArrayList<Obsequio>();
        obsentregados = mDb.query(true, ConstantsDB.OB_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (obsentregados != null && obsentregados.getCount() > 0) {
            obsentregados.moveToFirst();
            mObsequios.clear();
            do{
                mObsequios.add(crearObsequio(obsentregados));
            } while (obsentregados.moveToNext());
        }
        obsentregados.close();
        return mObsequios;
    }

    /**
     * Obtiene Lista todas los Obsequio de un codigo
     *
     * @return lista con Obsequio
     */
    public ArrayList<Obsequio> getListaObsequios(Integer codigo) throws SQLException {
        Cursor obsequios = null;
        ArrayList<Obsequio> mObsequios = new ArrayList<Obsequio>();
        obsequios = mDb.query(true, ConstantsDB.OB_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (obsequios != null && obsequios.getCount() > 0) {
            obsequios.moveToFirst();
            mObsequios.clear();
            do{
                mObsequios.add(crearObsequio(obsequios));
            } while (obsequios.moveToNext());
        }
        obsequios.close();
        return mObsequios;
    }

    public ArrayList<Obsequio> getListaObsequiosHoy() throws SQLException {
        Cursor obsequios = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<Obsequio> mObsequios = new ArrayList<Obsequio>();
        obsequios = mDb.query(true, ConstantsDB.OB_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (obsequios != null && obsequios.getCount() > 0) {
            obsequios.moveToFirst();
            mObsequios.clear();
            do{
                mObsequios.add(crearObsequio(obsequios));
            } while (obsequios.moveToNext());
        }
        obsequios.close();
        return mObsequios;
    }


    public ArrayList<EncuestaSatisfaccion> getEncuestaSatisfaccionHoy() throws SQLException {
        Cursor encuestas = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<EncuestaSatisfaccion> mEncuestas = new ArrayList<EncuestaSatisfaccion>();
        encuestas = mDb.query(true, ConstantsDB.ENC_SAT_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null,ConstantsDB.TODAY, null);
        if (encuestas != null && encuestas.getCount() > 0) {
            encuestas.moveToFirst();
            mEncuestas.clear();
            do{
                mEncuestas.add(crearEncuestaSatisfaccion(encuestas));
            } while (encuestas.moveToNext());
        }
        encuestas.close();
        return mEncuestas;
    }

    /**
     * Crea un Obsequio
     *
     * @return Obsequio
     */
    public Obsequio crearObsequio(Cursor entregas){
        Date fecha = new Date(entregas.getLong(entregas.getColumnIndex(ConstantsDB.TODAY)));
        Obsequio mObsequio = new Obsequio();
        ObsequioId obseId = new ObsequioId();
        obseId.setCodigo(entregas.getInt(entregas.getColumnIndex(ConstantsDB.CODIGO)));
        obseId.setFechaEntrega(new Date(entregas.getLong(entregas.getColumnIndex(ConstantsDB.FECHA_OB))));
        mObsequio.setObId(obseId);
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.OBSEQ))) mObsequio.setObseqSN(entregas.getInt(entregas.getColumnIndex(ConstantsDB.OBSEQ)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.CARNET))) mObsequio.setCarnetSN(entregas.getInt(entregas.getColumnIndex(ConstantsDB.CARNET)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.PERRETIRA))) mObsequio.setPersRecCarnet(entregas.getString(entregas.getColumnIndex(ConstantsDB.PERRETIRA)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.PERRETIRAREL))) mObsequio.setRelacionFam(entregas.getInt(entregas.getColumnIndex(ConstantsDB.PERRETIRAREL)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.PERRETIRAOREL))) mObsequio.setOtroRelacionFam(entregas.getString(entregas.getColumnIndex(ConstantsDB.PERRETIRAOREL)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.OBS_TEL))) mObsequio.setTelefono(entregas.getInt(entregas.getColumnIndex(ConstantsDB.OBS_TEL)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.CDOM))) mObsequio.setCmDomicilio(entregas.getInt(entregas.getColumnIndex(ConstantsDB.CDOM)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.BARRIO))) mObsequio.setBarrio(entregas.getInt(entregas.getColumnIndex(ConstantsDB.BARRIO)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.DIRECCION))) mObsequio.setDire(entregas.getString(entregas.getColumnIndex(ConstantsDB.DIRECCION)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.OBS))) mObsequio.setObservaciones(entregas.getString(entregas.getColumnIndex(ConstantsDB.OBS)));
        if(!entregas.isNull(entregas.getColumnIndex(ConstantsDB.otrorecurso1))) mObsequio.setOtrorecurso1(entregas.getInt(entregas.getColumnIndex(ConstantsDB.otrorecurso1)));
        Boolean borrado = entregas.getInt(entregas.getColumnIndex(ConstantsDB.DELETED))>0;
        mObsequio.setMovilInfo(new MovilInfo(entregas.getInt(entregas.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.FILE_PATH)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.STATUS)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.START)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.END)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.DEVICE_ID)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                entregas.getString(entregas.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                entregas.getString(entregas.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                entregas.getInt(entregas.getColumnIndex(ConstantsDB.REC1)),
                entregas.getInt(entregas.getColumnIndex(ConstantsDB.REC2))));
        return mObsequio;
    }

    /**
     * Obtiene Lista todas las vacunas sin enviar
     *
     * @return lista con Vacuna
     */
    public List<NewVacuna> getListaNewVacunasSinEnviar() throws SQLException {
        Cursor vacunas = null;
        List<NewVacuna> mNewVacunas = new ArrayList<NewVacuna>();
        vacunas = mDb.query(true, ConstantsDB.NEWVAC_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (vacunas != null && vacunas.getCount() > 0) {
            vacunas.moveToFirst();
            mNewVacunas.clear();
            do{
                mNewVacunas.add(crearNewVacuna(vacunas));
            } while (vacunas.moveToNext());
        }
        vacunas.close();
        return mNewVacunas;
    }


    /**
     * Obtiene Lista todas las DatosPartoBB sin enviar
     *
     * @return lista con DatosPartoBB
     */
    public List<DatosPartoBB> getListaDatosPartoBBSinEnviar() throws SQLException {
        Cursor datosPartoBB = null;
        List<DatosPartoBB> mDatosPartoBB = new ArrayList<DatosPartoBB>();
        datosPartoBB = mDb.query(true, ConstantsDB.DATOSPARTOBB_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (datosPartoBB != null && datosPartoBB.getCount() > 0) {
            datosPartoBB.moveToFirst();
            mDatosPartoBB.clear();
            do{
                mDatosPartoBB.add(DatosPartoBBHelper.crearDatosPartoBB(datosPartoBB));
            } while (datosPartoBB.moveToNext());
        }
        datosPartoBB.close();
        return mDatosPartoBB;
    }

    public ArrayList<NewVacuna> getListaNewVacunasHoy() throws SQLException {
        Cursor vacunas = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<NewVacuna> mVacunas = new ArrayList<NewVacuna>();
        vacunas = mDb.query(true, ConstantsDB.NEWVAC_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (vacunas != null && vacunas.getCount() > 0) {
            vacunas.moveToFirst();
            mVacunas.clear();
            do{
                mVacunas.add(crearNewVacuna(vacunas));
            } while (vacunas.moveToNext());
        }
        vacunas.close();
        return mVacunas;
    }

    /**
     * Crea una NewVacuna
     *
     * @return NewVacuna
     */
    public NewVacuna crearNewVacuna(Cursor vacunacion){
        Date fecha = new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.TODAY)));
        NewVacuna mVac = new NewVacuna();
        NewVacunaId vacunaId = new NewVacunaId();
        vacunaId.setCodigo(vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.CODIGO)));
        vacunaId.setFechaRegistroVacuna(new Date(vacunacion.getLong(vacunacion.getColumnIndex(ConstantsDB.fechaRegistroVacuna))));

        mVac.setVacunaId(vacunaId);
        mVac.setVacuna_sn(vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.vacuna_sn)));
        mVac.setTvacunano(vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.tvacunano)));
        mVac.setOtroMotivoTvacunano(vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.otroMotivoTvacunano)));
        if(!vacunacion.isNull(vacunacion.getColumnIndex(ConstantsDB.otrorecurso1))) mVac.setOtrorecurso1(vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.otrorecurso1)));
        Boolean borrado = vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.DELETED))>0;
        mVac.setMovilInfo(new MovilInfo(vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.FILE_PATH)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.STATUS)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.START)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.END)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.DEVICE_ID)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                vacunacion.getString(vacunacion.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.REC1)),
                vacunacion.getInt(vacunacion.getColumnIndex(ConstantsDB.REC2))));
        return mVac;
    }


    /**
     * Crea una DatosPartoBB
     *
     * @return DatosPartoBB
     */
    @Deprecated
    public DatosPartoBB crearDatosPartoBB(Cursor cursorDatosPartoBB){
        DatosPartoBB mDatosPartoBB = new DatosPartoBB();
        DatosPartoBBId mDatosPartoBBId = new DatosPartoBBId();
        mDatosPartoBBId.setCodigo(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.codigo)));
        mDatosPartoBBId.setFechaDatosParto(new Date(cursorDatosPartoBB.getLong(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fechaDatosParto))));
        mDatosPartoBB.setDatosPartoId(mDatosPartoBBId);
        mDatosPartoBB.setTipoParto(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tipoParto)));
        mDatosPartoBB.setTiempoEmb_sndr(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tiempoEmb_sndr)));
        if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tiempoEmbSemana))) mDatosPartoBB.setTiempoEmbSemana(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.tiempoEmbSemana)));
        mDatosPartoBB.setDocMedTiempoEmb_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedTiempoEmb_sn)));
        mDatosPartoBB.setDocMedTiempoEmb(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedTiempoEmb)));
        mDatosPartoBB.setOtroDocMedTiempoEmb(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otroDocMedTiempoEmb)));
        if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fum))) mDatosPartoBB.setFum(new Date(cursorDatosPartoBB.getLong(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fum))));
        if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.sg))) mDatosPartoBB.setSg(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.sg)));
        mDatosPartoBB.setFumFueraRango_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fumFueraRango_sn)));
        mDatosPartoBB.setFumFueraRango_razon(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.fumFueraRango_razon)));
        if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.edadGest))) mDatosPartoBB.setEdadGest(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.edadGest)));
        mDatosPartoBB.setDocMedEdadGest_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedEdadGest_sn)));
        mDatosPartoBB.setDocMedEdadGest(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedEdadGest)));
        mDatosPartoBB.setOtroDocMedEdadGest(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.OtroDocMedEdadGest)));
        mDatosPartoBB.setPrematuro_sndr(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.prematuro_sndr)));
        mDatosPartoBB.setPesoBB_sndr(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.pesoBB_sndr)));
        mDatosPartoBB.setPesoBB(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.pesoBB)));
        mDatosPartoBB.setDocMedPesoBB_sn(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedPesoBB_sn)));
        mDatosPartoBB.setDocMedPesoBB(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.docMedPesoBB)));
        mDatosPartoBB.setOtroDocMedPesoBB(cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otroDocMedPesoBB)));
        if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otrorecurso1))) mDatosPartoBB.setOtrorecurso1(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!cursorDatosPartoBB.isNull(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otrorecurso2))) mDatosPartoBB.setOtrorecurso2(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.otrorecurso2)));
        Date fecha = new Date(cursorDatosPartoBB.getLong(cursorDatosPartoBB.getColumnIndex(ConstantsDB.TODAY)));
        Boolean borrado = cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.DELETED))>0;
        mDatosPartoBB.setMovilInfo(new MovilInfo(cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.FILE_PATH)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.STATUS)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.START)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.END)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.DEVICE_ID)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                cursorDatosPartoBB.getString(cursorDatosPartoBB.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.REC1)),
                cursorDatosPartoBB.getInt(cursorDatosPartoBB.getColumnIndex(ConstantsDB.REC2))));
        return mDatosPartoBB;
    }


    /**
     * Obtiene Lista todas las visitas sin enviar
     *
     * @return lista con VisitaTerreno
     */
    public List<ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno> getListaVisitaTerrenosSinEnviar() throws SQLException {
        Cursor visitas = null;
        List<ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno> mVisitaTerrenos = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.VIS_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }

    /**
     * Obtiene Lista todas los VisitaTerreno de un codigo
     *
     * @return lista con VisitaTerreno
     */
    public ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno> getListaVisitaTerreno(Integer codigo) throws SQLException {
        Cursor visitas = null;
        ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno> mVisitaTerrenos = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.VIS_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }


    public ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno> getListaVisitaTerrenoHoy() throws SQLException {
        Cursor visitas = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno> mVisitaTerrenos = new ArrayList<ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.VIS_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }

    /**
     * Obtiene Lista todas las visitas sin enviar
     *
     * @return lista con VisitaTerreno
     */
    public List<DatosVisitaTerreno> getListaDatosVisitaTerrenosSinEnviar() throws SQLException {
        Cursor visitas = null;
        List<DatosVisitaTerreno> mVisitaTerrenos = new ArrayList<DatosVisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.DAT_VIS_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearDatosVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }


    /**
     * Obtiene Lista todas los DatosVisitaTerreno de un codigo
     *
     * @return lista con DatosVisitaTerreno
     */
    public ArrayList<DatosVisitaTerreno> getListaDatosVisitaTerreno(Integer codigo) throws SQLException {
        Cursor visitas = null;
        ArrayList<DatosVisitaTerreno> mVisitaTerrenos = new ArrayList<DatosVisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.DAT_VIS_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearDatosVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }

    public ArrayList<DatosVisitaTerreno> getListaDatosVisitaTerrenoHoy() throws SQLException {
        Cursor visitas = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
        ArrayList<DatosVisitaTerreno> mVisitaTerrenos = new ArrayList<DatosVisitaTerreno>();
        visitas = mDb.query(true, ConstantsDB.DAT_VIS_TABLE, null,
                ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
        if (visitas != null && visitas.getCount() > 0) {
            visitas.moveToFirst();
            mVisitaTerrenos.clear();
            do{
                mVisitaTerrenos.add(crearDatosVisitaTerreno(visitas));
            } while (visitas.moveToNext());
        }
        visitas.close();
        return mVisitaTerrenos;
    }

    /**
     * Crea una VisitaTerreno
     *
     * @return VisitaTerreno
     */
    public ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno crearVisitaTerreno(Cursor visitascampo){
        Date fecha = new Date(visitascampo.getLong(visitascampo.getColumnIndex(ConstantsDB.TODAY)));
        ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno mVisitaCasa = new ni.org.ics.estudios.appmovil.domain.muestreoanual.VisitaTerreno();
        VisitaTerrenoId visitasId = new VisitaTerrenoId();
        visitasId.setCodigo(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.CODIGO)));
        visitasId.setFechaVisita(new Date(visitascampo.getLong(visitascampo.getColumnIndex(ConstantsDB.FECHA_VISITA))));
        mVisitaCasa.setVisitaId(visitasId);
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.VISITASN))) mVisitaCasa.setVisitaSN(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.VISITASN)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.MOTNOVIS))) mVisitaCasa.setMotNoVisita(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.MOTNOVIS)));
        mVisitaCasa.setAcomp(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.ACOMP_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.REL_VIS))) mVisitaCasa.setRelacionFam(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.REL_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.ASENT_VIS))) mVisitaCasa.setAsentimiento(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.ASENT_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.otrorecurso1))) mVisitaCasa.setOtrorecurso1(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.otrorecurso2))) mVisitaCasa.setOtrorecurso2(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.otrorecurso2)));
        mVisitaCasa.setOtraRelacionFam(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.otraRelacionFam)));
        mVisitaCasa.setCarnetSN(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.carnetSN)));
		//para peso y talla muestreo 2020
		mVisitaCasa.setEstudiaSN(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.estudiaSN)));
		mVisitaCasa.setnEscuela(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.nEscuela)));
		mVisitaCasa.setOtraEscuela(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.otraEscuela)));
		mVisitaCasa.setTurno(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.turno)));
		mVisitaCasa.setOtroMotNoVisita(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.otroMotNoVisita)));

        Boolean borrado = visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.DELETED))>0;
        mVisitaCasa.setMovilInfo(new MovilInfo(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.FILE_PATH)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.STATUS)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.START)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.END)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.DEVICE_ID)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.REC1)),
                visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.REC2))));
        return mVisitaCasa;
    }

    /**
     * Crea una DatosVisitaTerreno
     *
     * @return DatosVisitaTerreno
     */
    public DatosVisitaTerreno crearDatosVisitaTerreno(Cursor visitascampo){
        Date fecha = new Date(visitascampo.getLong(visitascampo.getColumnIndex(ConstantsDB.TODAY)));
        DatosVisitaTerreno mVisitaCasa = new DatosVisitaTerreno();
        DatosVisitaTerrenoId visitasId = new DatosVisitaTerrenoId();
        visitasId.setCodigo(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.CODIGO)));
        visitasId.setFechaVisita(new Date(visitascampo.getLong(visitascampo.getColumnIndex(ConstantsDB.FECHA_VISITA))));

        mVisitaCasa.setVisitaId(visitasId);
        mVisitaCasa.setCodCasa(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.COD_CASA)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.CDOM_VIS))) mVisitaCasa.setcDom(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.CDOM_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.BARRIO_VIS))) mVisitaCasa.setBarrio(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.BARRIO_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.MANZ_VIS)))mVisitaCasa.setManzana(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.MANZ_VIS)));
        mVisitaCasa.setDireccion(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.DIRE_VIS)));
        mVisitaCasa.setCoordenadas(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.COORD_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.LAT_VIS))) mVisitaCasa.setLatitud(visitascampo.getDouble(visitascampo.getColumnIndex(ConstantsDB.LAT_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.LON_VIS))) mVisitaCasa.setLongitud(visitascampo.getDouble(visitascampo.getColumnIndex(ConstantsDB.LON_VIS)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.otrorecurso1))) mVisitaCasa.setOtrorecurso1(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.otrorecurso1)));
        if(!visitascampo.isNull(visitascampo.getColumnIndex(ConstantsDB.otrorecurso2))) mVisitaCasa.setOtrorecurso2(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.otrorecurso2)));
        mVisitaCasa.setTelefonoClasif1(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoClasif1)));
        mVisitaCasa.setTelefonoConv1(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoConv1)));
        mVisitaCasa.setTelefonoCel1(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoCel1)));
        mVisitaCasa.setTelefonoEmpresa1(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoEmpresa1)));
        mVisitaCasa.setTelefono2SN(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefono2SN)));
        mVisitaCasa.setTelefonoClasif2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoClasif2)));
        mVisitaCasa.setTelefonoConv2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoConv2)));
        mVisitaCasa.setTelefonoCel2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoCel2)));
        mVisitaCasa.setTelefonoEmpresa2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoEmpresa2)));
        mVisitaCasa.setTelefono3SN(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefono3SN)));
        mVisitaCasa.setTelefonoClasif3(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoClasif3)));
        mVisitaCasa.setTelefonoConv3(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoConv3)));
        mVisitaCasa.setTelefonoCel3(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoCel3)));
        mVisitaCasa.setTelefonoEmpresa3(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoEmpresa3)));
        mVisitaCasa.setTelefono4SN(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefono4SN)));
        mVisitaCasa.setTelefonoClasif4(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoClasif4)));
        mVisitaCasa.setTelefonoConv4(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoConv4)));
        mVisitaCasa.setTelefonoCel4(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoCel4)));
        mVisitaCasa.setTelefonoEmpresa4(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.telefonoEmpresa4)));
        mVisitaCasa.setCandidatoNI(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.candidatoNI)));
        mVisitaCasa.setNombreCandNI1(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.nombreCandNI1)));
        mVisitaCasa.setNombreCandNI2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.nombreCandNI2)));
        mVisitaCasa.setApellidoCandNI1(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.apellidoCandNI1)));
        mVisitaCasa.setApellidoCandNI2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.apellidoCandNI2)));
        mVisitaCasa.setNombreptTutorCandNI(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.nombreptTutorCandNI)));
        mVisitaCasa.setNombreptTutorCandNI2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.nombreptTutorCandNI2)));
        mVisitaCasa.setApellidoptTutorCandNI(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.apellidoptTutorCandNI)));
        mVisitaCasa.setApellidoptTutorCandNI2(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.apellidoptTutorCandNI2)));
        mVisitaCasa.setRelacionFamCandNI(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.relacionFamCandNI)));
        mVisitaCasa.setOtraRelacionFamCandNI(visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.otraRelacionFamCandNI)));
        Boolean borrado = visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.DELETED))>0;
        mVisitaCasa.setMovilInfo(new MovilInfo(visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.FILE_PATH)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.STATUS)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.START)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.END)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.DEVICE_ID)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                visitascampo.getString(visitascampo.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.REC1)),
                visitascampo.getInt(visitascampo.getColumnIndex(ConstantsDB.REC2))));
        return mVisitaCasa;
    }

    /**
     * Obtiene Lista todas las BHC sin enviar
     *
     * @return lista con BHC
     */
    public List<RecepcionBHC> getListaRecepcionBHCSinEnviar() throws SQLException {
        Cursor bhcs = null;
        List<RecepcionBHC> mBHCs = new ArrayList<RecepcionBHC>();
        bhcs = mDb.query(true, ConstantsDB.BHC_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (bhcs != null && bhcs.getCount() > 0) {
            bhcs.moveToFirst();
            mBHCs.clear();
            do{
                mBHCs.add(crearRecepcionBHC(bhcs));
            } while (bhcs.moveToNext());
        }
        bhcs.close();
        return mBHCs;
    }

    /**
     * Crea una RecepcionBHC
     *
     * @return RecepcionBHC
     */
    public RecepcionBHC crearRecepcionBHC(Cursor tubosbhc){
        RecepcionBHC mTubo = new RecepcionBHC();
        RecepcionBHCId tuboId = new RecepcionBHCId();
        tuboId.setCodigo(tubosbhc.getInt(tubosbhc.getColumnIndex(ConstantsDB.CODIGO)));
        tuboId.setFechaRecBHC(new Date(tubosbhc.getLong(tubosbhc.getColumnIndex(ConstantsDB.FECHA_BHC))));
        mTubo.setRecBhcId(tuboId);
        mTubo.setPaxgene(tubosbhc.getInt(tubosbhc.getColumnIndex(ConstantsDB.PAXGENE))>0);
        mTubo.setVolumen(tubosbhc.getDouble(tubosbhc.getColumnIndex(ConstantsDB.VOLBHC)));
        mTubo.setLugar(tubosbhc.getString(tubosbhc.getColumnIndex(ConstantsDB.LUGAR)));
        mTubo.setObservacion(tubosbhc.getString(tubosbhc.getColumnIndex(ConstantsDB.OBSBHC)));
        mTubo.setUsername(tubosbhc.getString(tubosbhc.getColumnIndex(ConstantsDB.USERNAME)));
        mTubo.setEstado(tubosbhc.getString(tubosbhc.getColumnIndex(ConstantsDB.STATUS)));
        mTubo.setFecreg(new Date(tubosbhc.getLong(tubosbhc.getColumnIndex(ConstantsDB.TODAY))));
        return mTubo;
    }


    /**
     * Obtiene Lista todas las Sero sin enviar
     *
     * @return lista con Sero
     */
    public List<RecepcionSero> getListaRecepcionSeroSinEnviar() throws SQLException {
        Cursor rojos = null;
        List<RecepcionSero> mRecepcionSero = new ArrayList<RecepcionSero>();
        rojos = mDb.query(true, ConstantsDB.SERO_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (rojos != null && rojos.getCount() > 0) {
            rojos.moveToFirst();
            mRecepcionSero.clear();
            do{
                mRecepcionSero.add(crearRecepcionSero(rojos));
            } while (rojos.moveToNext());
        }
        rojos.close();
        return mRecepcionSero;
    }

    /**
     * Crea una RecepcionSero
     *
     * @return RecepcionSero
     */
    public RecepcionSero crearRecepcionSero(Cursor tubossero){
        RecepcionSero mTubo = new RecepcionSero();
        mTubo.setCodigo(tubossero.getInt(tubossero.getColumnIndex(ConstantsDB.CODIGO)));
        mTubo.setFechaRecSero(new Date(tubossero.getLong(tubossero.getColumnIndex(ConstantsDB.FECHA_SERO))));
        mTubo.setId(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.ID)));
        mTubo.setVolumen(tubossero.getDouble(tubossero.getColumnIndex(ConstantsDB.VOLSERO)));
        mTubo.setLugar(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.LUGAR)));
        mTubo.setObservacion(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.OBSSERO)));
        mTubo.setUsername(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.USERNAME)));
        mTubo.setEstado(tubossero.getString(tubossero.getColumnIndex(ConstantsDB.STATUS)));
        mTubo.setFecreg(new Date(tubossero.getLong(tubossero.getColumnIndex(ConstantsDB.TODAY))));
        return mTubo;
    }

    /**
     * Obtiene Lista todas las Pinchazos sin enviar
     *
     * @return lista con Pinchazos
     */
    public List<Pinchazo> getListaPinchazosSinEnviar() throws SQLException {
        Cursor pins = null;
        List<Pinchazo> mPinchazo = new ArrayList<Pinchazo>();
        pins = mDb.query(true, ConstantsDB.PIN_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (pins != null && pins.getCount() > 0) {
            pins.moveToFirst();
            mPinchazo.clear();
            do{
                mPinchazo.add(crearPinchazo(pins));
            } while (pins.moveToNext());
        }
        pins.close();
        return mPinchazo;
    }

    /**
     * Crea una Pinchazo
     *
     * @return Pinchazo
     */
    public Pinchazo crearPinchazo(Cursor pinmasuno){
        Pinchazo mPin = new Pinchazo();
        PinchazoId pinId = new PinchazoId();
        pinId.setCodigo(pinmasuno.getInt(pinmasuno.getColumnIndex(ConstantsDB.CODIGO)));
        pinId.setFechaPinchazo(new Date(pinmasuno.getLong(pinmasuno.getColumnIndex(ConstantsDB.FECHA_PIN))));
        mPin.setPinId(pinId);
        mPin.setNumPin(pinmasuno.getInt(pinmasuno.getColumnIndex(ConstantsDB.PINCHAZOS)));
        mPin.setLugar(pinmasuno.getString(pinmasuno.getColumnIndex(ConstantsDB.LUGAR)));
        mPin.setObservacion(pinmasuno.getString(pinmasuno.getColumnIndex(ConstantsDB.OBSPIN)));
        mPin.setUsername(pinmasuno.getString(pinmasuno.getColumnIndex(ConstantsDB.USERNAME)));
        mPin.setEstado(pinmasuno.getString(pinmasuno.getColumnIndex(ConstantsDB.STATUS)));
        mPin.setFecreg(new Date(pinmasuno.getLong(pinmasuno.getColumnIndex(ConstantsDB.TODAY))));
        return mPin;
    }


    /**
     * Obtiene Lista todas las RazonNoData sin enviar
     *
     * @return lista con RazonNoData
     */
    public List<RazonNoData> getListaRazonNoDataSinEnviar() throws SQLException {
        Cursor rnds = null;
        List<RazonNoData> mRazonNoData = new ArrayList<RazonNoData>();
        rnds = mDb.query(true, ConstantsDB.NO_DATA_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (rnds != null && rnds.getCount() > 0) {
            rnds.moveToFirst();
            mRazonNoData.clear();
            do{
                mRazonNoData.add(crearRazonNoData(rnds));
            } while (rnds.moveToNext());
        }
        rnds.close();
        return mRazonNoData;
    }

    /**
     * Crea una RazonNoData
     *
     * @return RazonNoData
     */
    public RazonNoData crearRazonNoData(Cursor rnds){
        RazonNoData mRnd = new RazonNoData();
        RazonNoDataId rndId = new RazonNoDataId();
        rndId.setCodigo(rnds.getInt(rnds.getColumnIndex(ConstantsDB.CODIGO)));
        rndId.setFechaRegistro(new Date(rnds.getLong(rnds.getColumnIndex(ConstantsDB.TODAY))));
        mRnd.setRndId(rndId);
        mRnd.setRazon(rnds.getInt(rnds.getColumnIndex(ConstantsDB.RAZON)));
        mRnd.setOtraRazon(rnds.getString(rnds.getColumnIndex(ConstantsDB.ORAZON)));
        mRnd.setUsername(rnds.getString(rnds.getColumnIndex(ConstantsDB.USERNAME)));
        mRnd.setEstado(rnds.getString(rnds.getColumnIndex(ConstantsDB.STATUS)));
        return mRnd;
    }


    /**
     * Obtiene Lista todas las Documentos sin enviar
     *
     * @return lista con Documentos
     */
    public List<Documentos> getDocumentosSinEnviar() throws SQLException {
        Cursor docs = null;
        List<Documentos> mDocumentos = new ArrayList<Documentos>();
        //docs = mDb.rawQuery("SELECT codigo,fechaDocumento,tipoDoc,username,estado,fechaRecepcion,fecha_registro FROM documentacion where "+ ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'" , null);
        docs = mDb.rawQuery("SELECT codigo,fechaDocumento,tipoDoc,username,estado,fechaRecepcion,fecha_registro FROM documentacion", null);
        if (docs != null && docs.getCount() > 0) {
            docs.moveToFirst();
            mDocumentos.clear();
            do{
                mDocumentos.add(DocumentosHelper.crearDocumentos2(docs));
            } while (docs.moveToNext());
        }
        docs.close();
        return mDocumentos;
    }

    /**
     * Obtiene una Documentos de la base de datos
     *
     * @return Documentos
     */
    public Documentos getDocumentos(String filtro) throws SQLException {
        Documentos mDocumentos = null;
        Cursor cursorDocumentos = mDb.rawQuery("SELECT codigo,fechaDocumento,documento,tipoDoc,username,estado,fechaRecepcion,fecha_registro FROM documentacion where " + filtro , null);
        if (cursorDocumentos != null && cursorDocumentos.getCount() > 0) {
            cursorDocumentos.moveToFirst();
            mDocumentos= DocumentosHelper.crearDocumentos(cursorDocumentos);
        }
        if (!cursorDocumentos.isClosed()) cursorDocumentos.close();
        return mDocumentos;
    }



    /**
     * Obtiene Lista todas las TempRojoBhc sin enviar
     *
     * @return lista con TempRojoBhc
     */
    public List<TempRojoBhc> getListaTempRojoBhcSinEnviar() throws SQLException {
        Cursor temps = null;
        List<TempRojoBhc> mTempRojoBhc = new ArrayList<TempRojoBhc>();
        temps = mDb.query(true, ConstantsDB.TRB_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (temps != null && temps.getCount() > 0) {
            temps.moveToFirst();
            mTempRojoBhc.clear();
            do{
                mTempRojoBhc.add(crearTempRojoBhc(temps));
            } while (temps.moveToNext());
        }
        temps.close();
        return mTempRojoBhc;
    }

    /**
     * Obtiene Lista todas los TempRojoBhc de un codigo
     *
     * @return lista con TempRojoBhc
     */
    public ArrayList<TempRojoBhc> getListaTempRojoBhc(Integer codigo) throws SQLException {
        Cursor temps = null;
        ArrayList<TempRojoBhc> mTempRojoBhc = new ArrayList<TempRojoBhc>();
        temps = mDb.query(true, ConstantsDB.TRB_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (temps != null && temps.getCount() > 0) {
            temps.moveToFirst();
            mTempRojoBhc.clear();
            do{
                mTempRojoBhc.add(crearTempRojoBhc(temps));
            } while (temps.moveToNext());
        }
        temps.close();
        return mTempRojoBhc;
    }

    /**
     * Crea una TempRojoBhc
     *
     * @return TempRojoBhc
     */
    public TempRojoBhc crearTempRojoBhc(Cursor temprojos){
        TempRojoBhc mTemp = new TempRojoBhc();
        TempRojoBhcId tempId = new TempRojoBhcId();
        tempId.setRecurso(temprojos.getString(temprojos.getColumnIndex(ConstantsDB.RECURSO)));
        tempId.setFechaTempRojoBhc(new Date(temprojos.getLong(temprojos.getColumnIndex(ConstantsDB.FECHA_TEMP))));
        mTemp.setTempRojoBhcId(tempId);
        mTemp.setTemperatura(temprojos.getDouble(temprojos.getColumnIndex(ConstantsDB.TEMP)));
        mTemp.setLugar(temprojos.getString(temprojos.getColumnIndex(ConstantsDB.LUGARTEMP)));
        mTemp.setObservacion(temprojos.getString(temprojos.getColumnIndex(ConstantsDB.OBSTEMP)));
        mTemp.setUsername(temprojos.getString(temprojos.getColumnIndex(ConstantsDB.USERNAME)));
        mTemp.setEstado(temprojos.getString(temprojos.getColumnIndex(ConstantsDB.STATUS)));
        mTemp.setFecreg(new Date(temprojos.getLong(temprojos.getColumnIndex(ConstantsDB.TODAY))));
        return mTemp;
    }

    /**
     * Obtiene Lista todas las TempPbmc sin enviar
     *
     * @return lista con TempPbmc
     */
    public List<TempPbmc> getListaTempPbmcSinEnviar() throws SQLException {
        Cursor temps = null;
        List<TempPbmc> mTempPbmc = new ArrayList<TempPbmc>();
        temps = mDb.query(true, ConstantsDB.TPBMC_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (temps != null && temps.getCount() > 0) {
            temps.moveToFirst();
            mTempPbmc.clear();
            do{
                mTempPbmc.add(crearTempPbmc(temps));
            } while (temps.moveToNext());
        }
        temps.close();
        return mTempPbmc;
    }

    /**
     * Obtiene Lista todas los TempPbmc de un codigo
     *
     * @return lista con TempPbmc
     */
    public ArrayList<TempPbmc> getListaTempPbmc(Integer codigo) throws SQLException {
        Cursor temps = null;
        ArrayList<TempPbmc> mTempPbmc = new ArrayList<TempPbmc>();
        temps = mDb.query(true, ConstantsDB.TPBMC_TABLE, null,
                ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
        if (temps != null && temps.getCount() > 0) {
            temps.moveToFirst();
            mTempPbmc.clear();
            do{
                mTempPbmc.add(crearTempPbmc(temps));
            } while (temps.moveToNext());
        }
        temps.close();
        return mTempPbmc;
    }

    /**
     * Crea una TempPbmc
     *
     * @return TempPbmc
     */
    public TempPbmc crearTempPbmc(Cursor temppbmcs){
        TempPbmc mTemp = new TempPbmc();
        TempPbmcId tempId = new TempPbmcId();
        tempId.setRecurso(temppbmcs.getString(temppbmcs.getColumnIndex(ConstantsDB.RECURSO)));
        tempId.setFechaTempPbmc(new Date(temppbmcs.getLong(temppbmcs.getColumnIndex(ConstantsDB.FECHA_TEMP))));
        mTemp.setTempPbmcId(tempId);
        mTemp.setTemperatura(temppbmcs.getDouble(temppbmcs.getColumnIndex(ConstantsDB.TEMP)));
        mTemp.setLugar(temppbmcs.getString(temppbmcs.getColumnIndex(ConstantsDB.LUGARTEMP)));
        mTemp.setObservacion(temppbmcs.getString(temppbmcs.getColumnIndex(ConstantsDB.OBSTEMP)));
        mTemp.setUsername(temppbmcs.getString(temppbmcs.getColumnIndex(ConstantsDB.USERNAME)));
        mTemp.setEstado(temppbmcs.getString(temppbmcs.getColumnIndex(ConstantsDB.STATUS)));
        mTemp.setFecreg(new Date(temppbmcs.getLong(temppbmcs.getColumnIndex(ConstantsDB.TODAY))));
        return mTemp;
    }


    /**
     * Obtiene Lista todas las EncuestaSatisfaccion sin enviar
     *
     * @return lista con EncuestaSatisfaccion
     */
    public List<EncuestaSatisfaccion> getEncuestaSatisfaccionSinEnviar() throws SQLException {
        Cursor encuestas = null;
        List<EncuestaSatisfaccion> mEncuestaSatisfaccions = new ArrayList<EncuestaSatisfaccion>();
        encuestas = mDb.query(true, ConstantsDB.ENC_SAT_TABLE, null,
                ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
        if (encuestas != null && encuestas.getCount() > 0) {
            encuestas.moveToFirst();
            mEncuestaSatisfaccions.clear();
            do{
                mEncuestaSatisfaccions.add(crearEncuestaSatisfaccion(encuestas));
            } while (encuestas.moveToNext());
        }
        encuestas.close();
        return mEncuestaSatisfaccions;
    }

    /**
     * Crea una EncuestaSatisfaccion
     *
     * @return EncuestaSatisfaccion
     */
    public EncuestaSatisfaccion crearEncuestaSatisfaccion(Cursor encsats){
        Date fecha = new Date(encsats.getLong(encsats.getColumnIndex(ConstantsDB.TODAY)));
        EncuestaSatisfaccion mEncSat = new EncuestaSatisfaccion();
        mEncSat.setFechaEncuesta(new Date(encsats.getLong(encsats.getColumnIndex(ConstantsDB.FECHA_ENC_SAT))));
        mEncSat.setEstudio(encsats.getString(encsats.getColumnIndex(ConstantsDB.ESTUDIOSAT)));
        mEncSat.setAtenPerEst(encsats.getInt(encsats.getColumnIndex(ConstantsDB.ATENPEREST)));
        mEncSat.setTiemAten(encsats.getInt(encsats.getColumnIndex(ConstantsDB.TIEMATEN)));
        mEncSat.setAtenPerAdm(encsats.getInt(encsats.getColumnIndex(ConstantsDB.ATENPERADM)));
        mEncSat.setAtenPerEnferm(encsats.getInt(encsats.getColumnIndex(ConstantsDB.ATENPERENFERM)));
        mEncSat.setAtenPerMed(encsats.getInt(encsats.getColumnIndex(ConstantsDB.ATENPERMED)));
        mEncSat.setAmbAten(encsats.getInt(encsats.getColumnIndex(ConstantsDB.AMBATEN)));
        mEncSat.setAtenPerLab(encsats.getInt(encsats.getColumnIndex(ConstantsDB.ATENPERLAB)));
        mEncSat.setExplDxEnf(encsats.getInt(encsats.getColumnIndex(ConstantsDB.EXPLDXENF)));
        mEncSat.setFludenSN(encsats.getInt(encsats.getColumnIndex(ConstantsDB.FLUDENSN)));
        /*mEncSat.setFluConImp(encsats.getInt(encsats.getColumnIndex(ConstantsDB.FLUCONIMP)));
        mEncSat.setDenConImp(encsats.getInt(encsats.getColumnIndex(ConstantsDB.DENCONIMP)));
        mEncSat.setExplPeligEnf(encsats.getInt(encsats.getColumnIndex(ConstantsDB.EXPLPELIGENF)));
        mEncSat.setExpMedCuid(encsats.getInt(encsats.getColumnIndex(ConstantsDB.EXPMEDCUID)));*/
        if(!encsats.isNull(encsats.getColumnIndex(ConstantsDB.otrorecurso1))) mEncSat.setOtrorecurso1(encsats.getInt(encsats.getColumnIndex(ConstantsDB.otrorecurso1)));
        Boolean borrado = encsats.getInt(encsats.getColumnIndex(ConstantsDB.DELETED))>0;
		if (encsats.getInt(encsats.getColumnIndex(ConstantsDB.FLUDENSN)) > 0) {
			mEncSat.setFluConImp(encsats.getInt(encsats.getColumnIndex(ConstantsDB.FLUCONIMP)));/*null cuando es FLUDENSN <= 0*/
			mEncSat.setDenConImp(encsats.getInt(encsats.getColumnIndex(ConstantsDB.DENCONIMP)));/*null cuando es FLUDENSN <= 0*/
			mEncSat.setExplPeligEnf(encsats.getInt(encsats.getColumnIndex(ConstantsDB.EXPLPELIGENF)));/*null cuando es FLUDENSN <= 0*/
			mEncSat.setExpMedCuid(encsats.getInt(encsats.getColumnIndex(ConstantsDB.EXPMEDCUID)));/*null cuando es FLUDENSN <= 0*/
		}
        mEncSat.setMovilInfo(new MovilInfo(encsats.getInt(encsats.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.FILE_PATH)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.STATUS)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.START)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.END)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.DEVICE_ID)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.SIM_SERIAL)),
                encsats.getString(encsats.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
                fecha,
                encsats.getString(encsats.getColumnIndex(ConstantsDB.USUARIO)),
                borrado,
                encsats.getInt(encsats.getColumnIndex(ConstantsDB.REC1)),
                encsats.getInt(encsats.getColumnIndex(ConstantsDB.REC2))));
        return mEncSat;
    }

    //reconsentimiento dengue 2018
    /**
     * Metodos para visitas en la base de datos
     *
     * @param visitaTerrenoParticipante
     *            Objeto VisitaTereno que contiene la informacion
     *
     */
    //Crear nuevo VisitaTerrenoParticipante en la base de datos
    public void crearVisitaTerrenoParticipante(VisitaTerrenoParticipante visitaTerrenoParticipante) {
        ContentValues cv = VisitaTerrenoHelper.crearVisitaTerrenoPartContentValues(visitaTerrenoParticipante);
        mDb.insertOrThrow(MainDBConstants.VISITAPART_TABLE, null, cv);
    }
    //Editar VisitaTerrenoParticipante existente en la base de datos
    public boolean editarVisitaTerrenoParticipante(VisitaTerrenoParticipante visitaTerrenoParticipante) {
        ContentValues cv = VisitaTerrenoHelper.crearVisitaTerrenoPartContentValues(visitaTerrenoParticipante);
        return mDb.update(MainDBConstants.VISITAPART_TABLE , cv, MainDBConstants.codigoVisita + "='"
                + visitaTerrenoParticipante.getCodigoVisita()+ "'", null) > 0;
    }
    //Limpiar la tabla de VisitaTerrenoParticipante de la base de datos
    public boolean borrarVisitasTerrenoParticipante() {
        return mDb.delete(MainDBConstants.VISITAPART_TABLE, null, null) > 0;
    }
    //Obtener un VisitaTerrenoParticipante de la base de datos
    public VisitaTerrenoParticipante getVisitaTerrenoParticipante(String filtro, String orden) throws SQLException {
        VisitaTerrenoParticipante mVisitaTerreno = null;
        Cursor cursorVisitaTerreno = crearCursor(MainDBConstants.VISITAPART_TABLE , filtro, null, orden);
        if (cursorVisitaTerreno != null && cursorVisitaTerreno.getCount() > 0) {
            cursorVisitaTerreno.moveToFirst();
            mVisitaTerreno=VisitaTerrenoHelper.crearVisitaTerrenoPart(cursorVisitaTerreno);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursorVisitaTerreno.getInt(cursorVisitaTerreno.getColumnIndex(MainDBConstants.participante)), null);
            mVisitaTerreno.setParticipante(participante);
        }
        if (!cursorVisitaTerreno.isClosed()) cursorVisitaTerreno.close();
        return mVisitaTerreno;
    }
    //Obtener una lista de VisitaTerrenoParticipante de la base de datos
    public List<VisitaTerrenoParticipante> getVisitasTerrenoParticipantes(String filtro, String orden) throws SQLException {
        List<VisitaTerrenoParticipante> mVisitasTerreno = new ArrayList<VisitaTerrenoParticipante>();
        Cursor cursorVisitasTerreno = crearCursor(MainDBConstants.VISITAPART_TABLE, filtro, null, orden);
        if (cursorVisitasTerreno != null && cursorVisitasTerreno.getCount() > 0) {
            cursorVisitasTerreno.moveToFirst();
            mVisitasTerreno.clear();
            do{
                VisitaTerrenoParticipante mVisitaTerreno = null;
                mVisitaTerreno = VisitaTerrenoHelper.crearVisitaTerrenoPart(cursorVisitasTerreno);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursorVisitasTerreno.getInt(cursorVisitasTerreno.getColumnIndex(MainDBConstants.participante)), null);
                mVisitaTerreno.setParticipante(participante);
                mVisitasTerreno.add(mVisitaTerreno);
            } while (cursorVisitasTerreno.moveToNext());
        }
        if (!cursorVisitasTerreno.isClosed()) cursorVisitasTerreno.close();
        return mVisitasTerreno;
    }

    /**
     * Metodos para cambios de domicilio en la base de datos
     *
     * @param datosCoordenadas
     *            Objeto VisitaTereno que contiene la informacion
     *
     */
    //Crear nuevo DatosCoordenadas en la base de datos
    public void crearDatosCoordenadas(DatosCoordenadas datosCoordenadas) {
        ContentValues cv = CambioDomicilioHelper.crearCambioDomicilioContentValues(datosCoordenadas);
        mDb.insertOrThrow(MainDBConstants.DATOS_COORDENADAS_TABLE, null, cv);
    }
    //Editar DatosCoordenadas existente en la base de datos
    public boolean editarDatosCoordenadas(DatosCoordenadas datosCoordenadas) {
        ContentValues cv = CambioDomicilioHelper.crearCambioDomicilioContentValues(datosCoordenadas);
        return mDb.update(MainDBConstants.DATOS_COORDENADAS_TABLE , cv, MainDBConstants.codigo + "='"
                + datosCoordenadas.getCodigo()+ "'", null) > 0;
    }
    //Limpiar la tabla de DatosCoordenadas de la base de datos
    public boolean borrarDatosCoordenadas() {
        return mDb.delete(MainDBConstants.DATOS_COORDENADAS_TABLE, null, null) > 0;
    }
    //Obtener un DatosCoordenadas de la base de datos
    public DatosCoordenadas getDatosCoordenada(String filtro, String orden) throws SQLException {
        DatosCoordenadas mDatosCoordenadas = null;
        Cursor cursorCambioDomicilio = crearCursor(MainDBConstants.DATOS_COORDENADAS_TABLE , filtro, null, orden);
        if (cursorCambioDomicilio != null && cursorCambioDomicilio.getCount() > 0) {
            cursorCambioDomicilio.moveToFirst();
            mDatosCoordenadas =CambioDomicilioHelper.crearCambioDomicilio(cursorCambioDomicilio);
            Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorCambioDomicilio.getInt(cursorCambioDomicilio.getColumnIndex(MainDBConstants.participante)), null);
            mDatosCoordenadas.setParticipante(participante);
            Barrio barrio = this.getBarrio(CatalogosDBConstants.codigo+"="+cursorCambioDomicilio.getInt(cursorCambioDomicilio.getColumnIndex(MainDBConstants.barrio)), null);
            mDatosCoordenadas.setBarrio(barrio);
        }
        if (!cursorCambioDomicilio.isClosed()) cursorCambioDomicilio.close();
        return mDatosCoordenadas;
    }
    //Obtener una lista de DatosCoordenadas de la base de datos
    public List<DatosCoordenadas> getDatosCoordenadas(String filtro, String orden) throws SQLException {
        List<DatosCoordenadas> mCambiosDomicilio = new ArrayList<DatosCoordenadas>();
        Cursor cursorCambiosDomicilio = crearCursor(MainDBConstants.DATOS_COORDENADAS_TABLE, filtro, null, orden);
        if (cursorCambiosDomicilio != null && cursorCambiosDomicilio.getCount() > 0) {
            cursorCambiosDomicilio.moveToFirst();
            mCambiosDomicilio.clear();
            do{
                DatosCoordenadas mDatosCoordenadas = null;
                mDatosCoordenadas = CambioDomicilioHelper.crearCambioDomicilio(cursorCambiosDomicilio);
                Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursorCambiosDomicilio.getInt(cursorCambiosDomicilio.getColumnIndex(MainDBConstants.participante)), null);
                mDatosCoordenadas.setParticipante(participante);
                Barrio barrio = this.getBarrio(CatalogosDBConstants.codigo+"="+cursorCambiosDomicilio.getInt(cursorCambiosDomicilio.getColumnIndex(MainDBConstants.barrio)), null);
                mDatosCoordenadas.setBarrio(barrio);
                mCambiosDomicilio.add(mDatosCoordenadas);
            } while (cursorCambiosDomicilio.moveToNext());
        }
        if (!cursorCambiosDomicilio.isClosed()) cursorCambiosDomicilio.close();
        return mCambiosDomicilio;
    }

    /**
     * Metodos para cambios de domicilio en la base de datos
     *
     * @param enfermedadCronica
     *            Objeto VisitaTereno que contiene la informacion
     *
     */
    //Crear nuevo EnfermedadCronica en la base de datos
    public void crearEnfermedadCronica(EnfermedadCronica enfermedadCronica) {
        ContentValues cv = EnfermedadCronicaHelper.crearEnfermedadCronicaContentValues(enfermedadCronica);
        mDb.insertOrThrow(MainDBConstants.ENFCRONICA_TABLE, null, cv);
    }
    //Editar EnfermedadCronica existente en la base de datos
    public boolean editarEnfermedadCronica(EnfermedadCronica enfermedadCronica) {
        ContentValues cv = EnfermedadCronicaHelper.crearEnfermedadCronicaContentValues(enfermedadCronica);
        return mDb.update(MainDBConstants.ENFCRONICA_TABLE , cv, MainDBConstants.id + "='"
                + enfermedadCronica.getId()+ "'", null) > 0;
    }
    //Limpiar la tabla de EnfermedadCronica de la base de datos
    public boolean borrarEnfermedadesCronicas() {
        return mDb.delete(MainDBConstants.ENFCRONICA_TABLE, null, null) > 0;
    }
    //Obtener un EnfermedadCronica de la base de datos
    public EnfermedadCronica getEnfermedadCronica(String filtro, String orden) throws SQLException {
        EnfermedadCronica mEnfermedadCronica = null;
        Cursor cursorEnfermedad = crearCursor(MainDBConstants.ENFCRONICA_TABLE , filtro, null, orden);
        if (cursorEnfermedad != null && cursorEnfermedad.getCount() > 0) {
            cursorEnfermedad.moveToFirst();
            mEnfermedadCronica=EnfermedadCronicaHelper.crearEnfermedadCronica(cursorEnfermedad);
            Tamizaje tamizaje = this.getTamizaje(MainDBConstants.codigo + "='" + cursorEnfermedad.getString(cursorEnfermedad.getColumnIndex(MainDBConstants.tamizaje)) + "'", null);
            mEnfermedadCronica.setTamizaje(tamizaje);
        }
        if (!cursorEnfermedad.isClosed()) cursorEnfermedad.close();
        return mEnfermedadCronica;
    }
    //Obtener una lista de EnfermedadCronica de la base de datos
    public List<EnfermedadCronica> getEnfermedadesCronicas(String filtro, String orden) throws SQLException {
        List<EnfermedadCronica> mEnfCronica = new ArrayList<EnfermedadCronica>();
        Cursor cursorEnfermedades = crearCursor(MainDBConstants.ENFCRONICA_TABLE, filtro, null, orden);
        if (cursorEnfermedades != null && cursorEnfermedades.getCount() > 0) {
            cursorEnfermedades.moveToFirst();
            mEnfCronica.clear();
            do{
                EnfermedadCronica mEnfermedadCronica = null;
                mEnfermedadCronica = EnfermedadCronicaHelper.crearEnfermedadCronica(cursorEnfermedades);
                Tamizaje tamizaje = this.getTamizaje(MainDBConstants.codigo + "='" +cursorEnfermedades.getString(cursorEnfermedades.getColumnIndex(MainDBConstants.tamizaje))+"'", null);
                mEnfermedadCronica.setTamizaje(tamizaje);
                mEnfCronica.add(mEnfermedadCronica);
            } while (cursorEnfermedades.moveToNext());
        }
        if (!cursorEnfermedades.isClosed()) cursorEnfermedades.close();
        return mEnfCronica;
    }

    /**
     * Metodos para entrega de obsequios
     *
     * @param obsequioGeneral
     *            Objeto ObsequioGeneral que contiene la informacion
     *
     */
    //Crear nuevo ObsequioGeneral en la base de datos
    public void crearObsequioGeneral(ObsequioGeneral obsequioGeneral) {
        ContentValues cv = ObsequioHelper.crearObsequioContentValues(obsequioGeneral);
        mDb.insertOrThrow(MainDBConstants.OBSEQUIOS_TABLE, null, cv);
    }
    //Editar ObsequioGeneral existente en la base de datos
    public boolean editarObsequioGeneral(ObsequioGeneral obsequioGeneral) {
        ContentValues cv = ObsequioHelper.crearObsequioContentValues(obsequioGeneral);
        return mDb.update(MainDBConstants.OBSEQUIOS_TABLE , cv, MainDBConstants.id + "='"
                + obsequioGeneral.getId()+ "'", null) > 0;
    }
    //Limpiar la tabla de ObsequioGeneral de la base de datos
    public boolean borrarObsequiosGenerales() {
        return mDb.delete(MainDBConstants.OBSEQUIOS_TABLE, null, null) > 0;
    }
    //Obtener un ObsequioGeneral de la base de datos
    public ObsequioGeneral getObsequioGeneral(String filtro, String orden) throws SQLException {
        ObsequioGeneral mObsequioGeneral = null;
        Cursor cursorObsequio = crearCursor(MainDBConstants.OBSEQUIOS_TABLE , filtro, null, orden);
        if (cursorObsequio != null && cursorObsequio.getCount() > 0) {
            cursorObsequio.moveToFirst();
            mObsequioGeneral=ObsequioHelper.crearObsequio(cursorObsequio);
        }
        if (!cursorObsequio.isClosed()) cursorObsequio.close();
        return mObsequioGeneral;
    }
    //Obtener una lista de ObsequioGeneral de la base de datos
    public List<ObsequioGeneral> getObsequiosGenerales(String filtro, String orden) throws SQLException {
        List<ObsequioGeneral> mObsequios = new ArrayList<ObsequioGeneral>();
        Cursor cursorObsequios = crearCursor(MainDBConstants.OBSEQUIOS_TABLE, filtro, null, orden);
        if (cursorObsequios != null && cursorObsequios.getCount() > 0) {
            cursorObsequios.moveToFirst();
            mObsequios.clear();
            do{
                ObsequioGeneral mObsequioGeneral = null;
                mObsequioGeneral = ObsequioHelper.crearObsequio(cursorObsequios);
                mObsequios.add(mObsequioGeneral);
            } while (cursorObsequios.moveToNext());
        }
        if (!cursorObsequios.isClosed()) cursorObsequios.close();
        return mObsequios;
    }


    /**
     * Metodos para Muestra en la base de datos
     *
     * @param muestra
     *            Objeto Muestras que contiene la informacion
     *
     */
    //Crear nuevo registro MuestraSuperficie en la base de datos
    public void crearMuestraSuperficie(MuestraSuperficie muestra) {
        ContentValues cv = MuestraHelper.crearMuestraSuperficieContentValues(muestra);
        mDb.insertOrThrow(MuestrasDBConstants.MUESTRA_SUPERFICIE_TABLE, null, cv);
    }
    //Editar MuestraSuperficie existente en la base de datos
    public boolean editarMuestraSuperficie(MuestraSuperficie muestra) {
        ContentValues cv = MuestraHelper.crearMuestraSuperficieContentValues(muestra);
        return mDb.update(MuestrasDBConstants.MUESTRA_SUPERFICIE_TABLE, cv, MuestrasDBConstants.codigo + "='"
                + muestra.getCodigo() + "'", null) > 0;
    }
    //Limpiar la tabla de MuestraSuperficie de la base de datos
    public boolean borrarMuestrasSuperficie() {
        return mDb.delete(MuestrasDBConstants.MUESTRA_SUPERFICIE_TABLE, null, null) > 0;
    }

    //Obtener una MuestraSuperficie de la base de datos
    public MuestraSuperficie getMuestraSuperficie(String filtro, String orden) throws SQLException {
        MuestraSuperficie mMuestras = null;
        Cursor cursor = crearCursor(MuestrasDBConstants.MUESTRA_SUPERFICIE_TABLE , filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mMuestras=MuestraHelper.crearMuestraSuperficie(cursor);
            if (cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.participanteChf))!=null) {
                ParticipanteCohorteFamilia participante = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.participanteChf)), null);
                if (participante != null) mMuestras.setParticipanteChf(participante);
            }
            if (cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.casaChf))!=null) {
                CasaCohorteFamilia casaCohorteFamilia = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" + cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.casaChf)) + "'", null);
                if (casaCohorteFamilia != null) mMuestras.setCasaChf(casaCohorteFamilia);
            }
        }
        if (!cursor.isClosed()) cursor.close();
        return mMuestras;
    }
    //Obtener una lista de MuestraSuperficie de la base de datos
    public List<MuestraSuperficie> getMuestrasSuperficie(String filtro, String orden) throws SQLException {
        List<MuestraSuperficie> mMuestras = new ArrayList<MuestraSuperficie>();
        Cursor cursor = crearCursor(MuestrasDBConstants.MUESTRA_SUPERFICIE_TABLE, filtro, null, orden);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mMuestras.clear();
            do{
                MuestraSuperficie mMuestra = null;
                mMuestra = MuestraHelper.crearMuestraSuperficie(cursor);
                if (cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.participanteChf))!=null) {
                    ParticipanteCohorteFamilia participante = this.getParticipanteCohorteFamilia(MainDBConstants.participante + "=" + cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.participanteChf)), null);
                    if (participante != null) mMuestra.setParticipanteChf(participante);
                }
                if (cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.casaChf))!=null) {
                    CasaCohorteFamilia casaCohorteFamilia = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" + cursor.getString(cursor.getColumnIndex(MuestrasDBConstants.casaChf)) + "'", null);
                    if (casaCohorteFamilia != null) mMuestra.setCasaChf(casaCohorteFamilia);
                }
                mMuestras.add(mMuestra);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) cursor.close();
        return mMuestras;
    }

	/**
	 * Metodos para ParticipanteCasoUO1 en la base de datos
	 *
	 * @param participanteCasoUO1
	 *            Objeto Muestras que contiene la informacion
	 *
	 */
	//Crear nuevo registro ParticipanteCasoUO1 en la base de datos
	public void crearParticipanteCasoUO1(ParticipanteCasoUO1 participanteCasoUO1) {
		ContentValues cv = ParticipanteCasoUO1Helper.crearParticipanteCasoUO1ContentValues(participanteCasoUO1);
		mDb.insertOrThrow(InfluenzaUO1DBConstants.UO1_PARTICIPANTES_CASOS_TABLE, null, cv);
	}
	//Editar ParticipanteCasoUO1 existente en la base de datos
	public boolean editarParticipanteCasoUO1(ParticipanteCasoUO1 participanteCasoUO1) {
		ContentValues cv = ParticipanteCasoUO1Helper.crearParticipanteCasoUO1ContentValues(participanteCasoUO1);
		return mDb.update(InfluenzaUO1DBConstants.UO1_PARTICIPANTES_CASOS_TABLE, cv, InfluenzaUO1DBConstants.codigoCasoParticipante + "='"
				+ participanteCasoUO1.getCodigoCasoParticipante() + "'", null) > 0;
	}
	//Limpiar la tabla de ParticipanteCasoUO1 de la base de datos
	public boolean borrarParticipantesCasoUO1() {
		return mDb.delete(InfluenzaUO1DBConstants.UO1_PARTICIPANTES_CASOS_TABLE, null, null) > 0;
	}

	//Obtener una ParticipanteCasoUO1 de la base de datos
	public ParticipanteCasoUO1 getParticipanteCasoUO1(String filtro, String orden) throws SQLException {
		ParticipanteCasoUO1 mParticipanteUO1 = null;
		Cursor cursor = crearCursor(InfluenzaUO1DBConstants.UO1_PARTICIPANTES_CASOS_TABLE , filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mParticipanteUO1=ParticipanteCasoUO1Helper.crearParticipanteCasoUO1(cursor);
			if (cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.participante))!=null) {
				Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.participante)), null);
				if (participante != null) mParticipanteUO1.setParticipante(participante);
			}
		}
		if (!cursor.isClosed()) cursor.close();
		return mParticipanteUO1;
	}
	//Obtener una lista de ParticipanteCasoUO1 de la base de datos
	public List<ParticipanteCasoUO1> getParticipantesCasosUO1(String filtro, String orden) throws SQLException {
		List<ParticipanteCasoUO1> mParticipantesUO1 = new ArrayList<ParticipanteCasoUO1>();
		Cursor cursor = crearCursor(InfluenzaUO1DBConstants.UO1_PARTICIPANTES_CASOS_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mParticipantesUO1.clear();
			do{
				ParticipanteCasoUO1 mParticipanteUO1 = null;
				mParticipanteUO1=ParticipanteCasoUO1Helper.crearParticipanteCasoUO1(cursor);
				if (cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.participante))!=null) {
					Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.participante)), null);
					if (participante != null) mParticipanteUO1.setParticipante(participante);
				}
				mParticipantesUO1.add(mParticipanteUO1);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mParticipantesUO1;
	}

	/**
	 * Metodos para VisitaCasoUO1 en la base de datos
	 *
	 * @param visitaCasoUO1
	 *            Objeto Muestras que contiene la informacion
	 *
	 */
	//Crear nuevo registro VisitaCasoUO1 en la base de datos
	public void crearVisitaCasoUO1(VisitaCasoUO1 visitaCasoUO1) {
		ContentValues cv = VisitaCasoUO1Helper.crearVisitaCasoUO1ContentValues(visitaCasoUO1);
		mDb.insertOrThrow(InfluenzaUO1DBConstants.UO1_VISITAS_CASOS_TABLE, null, cv);
	}
	//Editar VisitaCasoUO1 existente en la base de datos
	public boolean editarVisitaCasoUO1(VisitaCasoUO1 visitaCasoUO1) {
		ContentValues cv = VisitaCasoUO1Helper.crearVisitaCasoUO1ContentValues(visitaCasoUO1);
		return mDb.update(InfluenzaUO1DBConstants.UO1_VISITAS_CASOS_TABLE, cv, InfluenzaUO1DBConstants.codigoCasoVisita + "='"
				+ visitaCasoUO1.getCodigoCasoVisita() + "'", null) > 0;
	}
	//Limpiar la tabla de VisitaCasoUO1 de la base de datos
	public boolean borrarVisitaCasoUO1() {
		return mDb.delete(InfluenzaUO1DBConstants.UO1_VISITAS_CASOS_TABLE, null, null) > 0;
	}

	//Obtener una VisitaCasoUO1 de la base de datos
	public VisitaCasoUO1 getVisitaCasoUO1(String filtro, String orden) throws SQLException {
		VisitaCasoUO1 mParticipanteUO1 = null;
		Cursor cursor = crearCursor(InfluenzaUO1DBConstants.UO1_VISITAS_CASOS_TABLE , filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mParticipanteUO1=VisitaCasoUO1Helper.crearVisitaCasoUO1(cursor);
			if (cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.participanteCasoUO1))!=null) {
				ParticipanteCasoUO1 participante = this.getParticipanteCasoUO1(InfluenzaUO1DBConstants.codigoCasoParticipante + "='" + cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.participanteCasoUO1))+"'", null);
				if (participante != null) mParticipanteUO1.setParticipanteCasoUO1(participante);
			}
		}
		if (!cursor.isClosed()) cursor.close();
		return mParticipanteUO1;
	}
	//Obtener una lista de VisitaCasoUO1 de la base de datos
	public List<VisitaCasoUO1> getVisitasCasosUO1(String filtro, String orden) throws SQLException {
		List<VisitaCasoUO1> visitaCasoUO1List = new ArrayList<VisitaCasoUO1>();
		Cursor cursor = crearCursor(InfluenzaUO1DBConstants.UO1_VISITAS_CASOS_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			visitaCasoUO1List.clear();
			do{
				VisitaCasoUO1 visitaCasoUO1 = null;
				visitaCasoUO1=VisitaCasoUO1Helper.crearVisitaCasoUO1(cursor);
				if (cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.participanteCasoUO1))!=null) {
					ParticipanteCasoUO1 participante = this.getParticipanteCasoUO1(InfluenzaUO1DBConstants.codigoCasoParticipante + "='" + cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.participanteCasoUO1))+"'", null);
					if (participante != null) visitaCasoUO1.setParticipanteCasoUO1(participante);
				}
				visitaCasoUO1List.add(visitaCasoUO1);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return visitaCasoUO1List;
	}

	//Limpiar la tabla de Muestras Positivos UO1 de la base de datos
	public boolean borrarMuestrasUO1() {
		return mDb.delete(MuestrasDBConstants.MUESTRA_TABLE, MuestrasDBConstants.proposito + "='"+Constants.CODIGO_PROPOSITO_UO1+"' or " + MuestrasDBConstants.proposito + "='"+Constants.CODIGO_PROPOSITO_POS_FLU+"'  or " + MuestrasDBConstants.proposito + "='"+Constants.CODIGO_PROPOSITO_VC_UO1+"'"  , null) > 0;
	}

	/**
	 * Metodos para ParticipanteVacunaUO1 en la base de datos
	 *
	 * @param visitaVacunaUO1
	 *            Objeto Muestras que contiene la informacion
	 *
	 */
	//Crear nuevo registro VisitaVacunaUO1 en la base de datos
	public void crearVisitaVacunaUO1(VisitaVacunaUO1 visitaVacunaUO1) {
		ContentValues cv = VisitaVacunaUO1Helper.crearVisitaVacunaUO1ContentValues(visitaVacunaUO1);
		mDb.insertOrThrow(InfluenzaUO1DBConstants.UO1_VISITAS_VACUNAS_TABLE, null, cv);
	}
	//Editar VisitaVacunaUO1 existente en la base de datos
	public boolean editarVisitaVacunaUO1(VisitaVacunaUO1 visitaVacunaUO1) {
		ContentValues cv = VisitaVacunaUO1Helper.crearVisitaVacunaUO1ContentValues(visitaVacunaUO1);
		return mDb.update(InfluenzaUO1DBConstants.UO1_VISITAS_VACUNAS_TABLE, cv, InfluenzaUO1DBConstants.codigoVisita + "='"
				+ visitaVacunaUO1.getCodigoVisita() + "'", null) > 0;
	}
	//Limpiar la tabla de VisitaVacunaUO1 de la base de datos
	public boolean borrarVisitaVacunaUO1() {
		return mDb.delete(InfluenzaUO1DBConstants.UO1_VISITAS_VACUNAS_TABLE, null, null) > 0;
	}

	//Obtener una VisitaVacunaUO1 de la base de datos
	public VisitaVacunaUO1 getVisitaVacunaUO1(String filtro, String orden) throws SQLException {
		VisitaVacunaUO1 visitaVacunaUO1 = null;
		Cursor cursor = crearCursor(InfluenzaUO1DBConstants.UO1_VISITAS_VACUNAS_TABLE , filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			visitaVacunaUO1=VisitaVacunaUO1Helper.crearVisitaVacunaUO1(cursor);
			if (cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.participante))!=null) {
				Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.participante)), null);
				if (participante != null) visitaVacunaUO1.setParticipante(participante);
			}
		}
		if (!cursor.isClosed()) cursor.close();
		return visitaVacunaUO1;
	}
	//Obtener una lista de VisitaVacunaUO1 de la base de datos
	public List<VisitaVacunaUO1> getVisitasVacunasUO1(String filtro, String orden) throws SQLException {
		List<VisitaVacunaUO1> visitaVacunaUO1List = new ArrayList<VisitaVacunaUO1>();
		Cursor cursor = crearCursor(InfluenzaUO1DBConstants.UO1_VISITAS_VACUNAS_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			visitaVacunaUO1List.clear();
			do{
				VisitaVacunaUO1 visitaVacunaUO1 = null;
				visitaVacunaUO1=VisitaVacunaUO1Helper.crearVisitaVacunaUO1(cursor);
				if (cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.participante))!=null) {
					Participante participante = this.getParticipante(MainDBConstants.codigo + "=" + cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.participante)), null);
					if (participante != null) visitaVacunaUO1.setParticipante(participante);
				}
				visitaVacunaUO1List.add(visitaVacunaUO1);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return visitaVacunaUO1List;
	}

	/**
	 * Metodos para SintomasVisitaCasoUO1 en la base de datos
	 *
	 * @param visitaVacunaUO1
	 *            Objeto Muestras que contiene la informacion
	 *
	 */
	//Crear nuevo registro SintomasVisitaCasoUO1 en la base de datos
	public void crearSintomasVisitaCasoUO1(SintomasVisitaCasoUO1 visitaVacunaUO1) {
		ContentValues cv = SintomasVisitaCasoUO1Helper.crearSintomasVisitaCasoUO1ContentValues(visitaVacunaUO1);
		mDb.insertOrThrow(InfluenzaUO1DBConstants.UO1_SINTOMAS_VISITA_CASO_TABLE, null, cv);
	}
	//Editar SintomasVisitaCasoUO1 existente en la base de datos
	public boolean editarSintomasVisitaCasoUO1(SintomasVisitaCasoUO1 visitaVacunaUO1) {
		ContentValues cv = SintomasVisitaCasoUO1Helper.crearSintomasVisitaCasoUO1ContentValues(visitaVacunaUO1);
		return mDb.update(InfluenzaUO1DBConstants.UO1_SINTOMAS_VISITA_CASO_TABLE, cv, InfluenzaUO1DBConstants.codigoSintoma + "='"
				+ visitaVacunaUO1.getCodigoSintoma() + "'", null) > 0;
	}
	//Limpiar la tabla de SintomasVisitaCasoUO1 de la base de datos
	public boolean borrarSintomasVisitaCasoUO1() {
		return mDb.delete(InfluenzaUO1DBConstants.UO1_SINTOMAS_VISITA_CASO_TABLE, null, null) > 0;
	}

	//Obtener una SintomasVisitaCasoUO1 de la base de datos
	public SintomasVisitaCasoUO1 getSintomasVisitaCasoUO1(String filtro, String orden) throws SQLException {
		SintomasVisitaCasoUO1 visitaVacunaUO1 = null;
		Cursor cursor = crearCursor(InfluenzaUO1DBConstants.UO1_SINTOMAS_VISITA_CASO_TABLE , filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			visitaVacunaUO1=SintomasVisitaCasoUO1Helper.crearSintomasVisitaCasoUO1(cursor);
			if (cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.codigoCasoVisita))!=null) {
				VisitaCasoUO1 visitaCasoUO1 = this.getVisitaCasoUO1(InfluenzaUO1DBConstants.codigoCasoVisita + "='" + cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.codigoCasoVisita)) + "'", null);
				if (visitaCasoUO1 != null) visitaVacunaUO1.setCodigoCasoVisita(visitaCasoUO1);
			}
		}
		if (!cursor.isClosed()) cursor.close();
		return visitaVacunaUO1;
	}

	//Obtener una lista de SintomasVisitaCasoUO1 de la base de datos
	public List<SintomasVisitaCasoUO1> getSintomasVisitasCasosUO1(String filtro, String orden) throws SQLException {
		List<SintomasVisitaCasoUO1> visitaVacunaUO1List = new ArrayList<SintomasVisitaCasoUO1>();
		Cursor cursor = crearCursor(InfluenzaUO1DBConstants.UO1_SINTOMAS_VISITA_CASO_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			visitaVacunaUO1List.clear();
			do{
				SintomasVisitaCasoUO1 visitaVacunaUO1 = null;
				visitaVacunaUO1=SintomasVisitaCasoUO1Helper.crearSintomasVisitaCasoUO1(cursor);
				if (cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.codigoCasoVisita))!=null) {
					VisitaCasoUO1 visitaCasoUO1 = this.getVisitaCasoUO1(InfluenzaUO1DBConstants.codigoCasoVisita + "='" + cursor.getString(cursor.getColumnIndex(InfluenzaUO1DBConstants.codigoCasoVisita)) + "'", null);
					if (visitaCasoUO1 != null) visitaVacunaUO1.setCodigoCasoVisita(visitaCasoUO1);
				}
				visitaVacunaUO1List.add(visitaVacunaUO1);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return visitaVacunaUO1List;
	}

	/**
	 * Metodos para cambios de domicilio en la base de datos
	 *
	 * @param sensorCaso
	 *            Objeto SensorCaso que contiene la informacion
	 *
	 */
	//Crear nuevo SensorCaso en la base de datos
	public void crearSensorCaso(SensorCaso sensorCaso) {
		ContentValues cv = SensoresCasoHelper.crearSensorCasoContentValues(sensorCaso);
		mDb.insertOrThrow(CasosDBConstants.SENSORES_CASOS_TABLE, null, cv);
	}
	//Editar SensorCaso existente en la base de datos
	public boolean editarSensorCaso(SensorCaso sensorCaso) {
		ContentValues cv = SensoresCasoHelper.crearSensorCasoContentValues(sensorCaso);
		return mDb.update(CasosDBConstants.SENSORES_CASOS_TABLE , cv, CasosDBConstants.codigoSensor + "='"
				+ sensorCaso.getCodigoSensor()+ "'", null) > 0;
	}
	//Limpiar la tabla de SensorCaso de la base de datos
	public boolean borrarSensoresCasos() {
		return mDb.delete(CasosDBConstants.SENSORES_CASOS_TABLE, null, null) > 0;
	}
	//Obtener un SensorCaso de la base de datos
	public SensorCaso getSensorCaso(String filtro, String orden) throws SQLException {
		SensorCaso mSensorCaso = null;
		Cursor cursorSensor = crearCursor(CasosDBConstants.SENSORES_CASOS_TABLE, filtro, null, orden);
		if (cursorSensor != null && cursorSensor.getCount() > 0) {
			cursorSensor.moveToFirst();
			mSensorCaso= SensoresCasoHelper.crearSensorCaso(cursorSensor);
			CasaCohorteFamiliaCaso caso = this.getCasaCohorteFamiliaCaso(CasosDBConstants.codigoCaso + "='" + cursorSensor.getString(cursorSensor.getColumnIndex(CasosDBConstants.codigoCaso)) + "'", null);
			AreaAmbiente part = this.getAreaAmbiente(MainDBConstants.codigo +" = '"+ cursorSensor.getString(cursorSensor.getColumnIndex(CasosDBConstants.area)) + "'", null);
			mSensorCaso.setArea(part);
			Cuarto cuarto = this.getCuarto(MainDBConstants.codigo +" = '"+ cursorSensor.getString(cursorSensor.getColumnIndex(CasosDBConstants.habitacionSensor)) + "'", null);
			mSensorCaso.setCuarto(cuarto);
			mSensorCaso.setCodigoCaso(caso);
		}
		if (!cursorSensor.isClosed()) cursorSensor.close();
		return mSensorCaso;
	}
	//Obtener una lista de SensorCaso de la base de datos
	public List<SensorCaso> getSensoresCasos(String filtro, String orden) throws SQLException {
		List<SensorCaso> mEnfCronica = new ArrayList<SensorCaso>();
		Cursor cursorSensores = crearCursor(CasosDBConstants.SENSORES_CASOS_TABLE, filtro, null, orden);
		if (cursorSensores != null && cursorSensores.getCount() > 0) {
			cursorSensores.moveToFirst();
			mEnfCronica.clear();
			do{
				SensorCaso mSensorCaso = null;
				mSensorCaso = SensoresCasoHelper.crearSensorCaso(cursorSensores);
				CasaCohorteFamiliaCaso caso = this.getCasaCohorteFamiliaCaso(CasosDBConstants.codigoCaso + "='" + cursorSensores.getString(cursorSensores.getColumnIndex(CasosDBConstants.codigoCaso)) + "'", null);
				mSensorCaso.setCodigoCaso(caso);
				AreaAmbiente part = this.getAreaAmbiente(MainDBConstants.codigo +" = '"+ cursorSensores.getString(cursorSensores.getColumnIndex(CasosDBConstants.area)) + "'", null);
				mSensorCaso.setArea(part);
				Cuarto cuarto = this.getCuarto(MainDBConstants.codigo +" = '"+ cursorSensores.getString(cursorSensores.getColumnIndex(CasosDBConstants.habitacionSensor)) + "'", null);
				mSensorCaso.setCuarto(cuarto);
				mEnfCronica.add(mSensorCaso);
			} while (cursorSensores.moveToNext());
		}
		if (!cursorSensores.isClosed()) cursorSensores.close();
		return mEnfCronica;
	}


	//Crear nuevo ParticipanteCovid19 en la base de datos desde otro equipo
	public void insertarParticipanteCovid19(String participanteCovid19SQL) {
		mDb.execSQL(participanteCovid19SQL);
	}

	//Crear nuevo ParticipanteCovid19 en la base de datos
	public void crearParticipanteCovid19(ParticipanteCovid19 partcaso) throws Exception {
		ContentValues cv = ParticipanteCovid19Helper.crearParticipanteCovid19ContentValues(partcaso);
		mDb.insertOrThrow(Covid19DBConstants.PARTICIPANTE_COVID_TABLE, null, cv);
	}

	//Editar ParticipanteCovid19 existente en la base de datos
	public boolean editarParticipanteCovid19(ParticipanteCovid19 partcaso) throws Exception{
		ContentValues cv = ParticipanteCovid19Helper.crearParticipanteCovid19ContentValues(partcaso);
		return mDb.update(Covid19DBConstants.PARTICIPANTE_COVID_TABLE, cv, Covid19DBConstants.participante + "="
				+ partcaso.getParticipante().getCodigo(), null) > 0;
	}
	//Limpiar la tabla de ParticipanteCovid19 de la base de datos
	public boolean borrarParticipanteCovid19() {
		return mDb.delete(Covid19DBConstants.PARTICIPANTE_COVID_TABLE, null, null) > 0;
	}
	//Obtener un ParticipanteCovid19 de la base de datos
	public ParticipanteCovid19 getParticipanteCovid19(String filtro, String orden) throws SQLException {
		ParticipanteCovid19 mParticipanteCovid19 = null;
		Cursor cursor = crearCursor(Covid19DBConstants.PARTICIPANTE_COVID_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mParticipanteCovid19=ParticipanteCovid19Helper.crearParticipanteCovid19(cursor);
			Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.participante)), null);
			mParticipanteCovid19.setParticipante(participante);
		}
		if (!cursor.isClosed()) cursor.close();
		return mParticipanteCovid19;
	}
	//Obtener una lista de ParticipanteCovid19 de la base de datos
	public List<ParticipanteCovid19> getParticipantesCovid19(String filtro, String orden) throws SQLException {
		List<ParticipanteCovid19> mParticipanteCovid19s = new ArrayList<ParticipanteCovid19>();
		Cursor cursor = crearCursor(Covid19DBConstants.PARTICIPANTE_COVID_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mParticipanteCovid19s.clear();
			do{
				ParticipanteCovid19 mParticipanteCovid19 = null;
				mParticipanteCovid19 = ParticipanteCovid19Helper.crearParticipanteCovid19(cursor);
				Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.participante)), null);
				mParticipanteCovid19.setParticipante(participante);
				mParticipanteCovid19s.add(mParticipanteCovid19);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mParticipanteCovid19s;
	}

	//Crear nuevo CasoCovid19 en la base de datos
	public void crearCasoCovid19(CasoCovid19 casacaso) throws Exception {
		ContentValues cv = CasoCovid19Helper.crearCasoCovid19ContentValues(casacaso);
		mDb.insertOrThrow(Covid19DBConstants.COVID_CASOS_TABLE, null, cv);
	}

	//Crear nuevo CasoCovid19 en la base de datos desde otro equipo
	public void insertarCasoCovid19(String casoCovid19SQL) {
		mDb.execSQL(casoCovid19SQL);
	}

	//Editar CasoCovid19 existente en la base de datos
	public boolean editarCasoCovid19(CasoCovid19 casacaso) throws Exception{
		ContentValues cv = CasoCovid19Helper.crearCasoCovid19ContentValues(casacaso);
		return mDb.update(Covid19DBConstants.COVID_CASOS_TABLE, cv, Covid19DBConstants.codigoCaso + "='"
				+ casacaso.getCodigoCaso() + "'", null) > 0;
	}
	//Limpiar la tabla de CasoCovid19 de la base de datos
	public boolean borrarCasoCovid19() {
		return mDb.delete(Covid19DBConstants.COVID_CASOS_TABLE, null, null) > 0;
	}
	//Obtener un CasoCovid19 de la base de datos
	public CasoCovid19 getCasoCovid19(String filtro, String orden) throws SQLException {
		CasoCovid19 mCasoCovid19 = null;
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_CASOS_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mCasoCovid19= CasoCovid19Helper.crearCasoCovid19(cursor);
			CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" + cursor.getString(cursor.getColumnIndex(Covid19DBConstants.casa)) +"'", null);
			mCasoCovid19.setCasa(cchf);
		}
		if (!cursor.isClosed()) cursor.close();
		return mCasoCovid19;
	}
	//Obtener una lista de CasoCovid19 de la base de datos
	public List<CasoCovid19> getCasosCovid19(String filtro, String orden) throws SQLException {
		List<CasoCovid19> mCasoCovid19s = new ArrayList<CasoCovid19>();
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_CASOS_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mCasoCovid19s.clear();
			do{
				CasoCovid19 mCasoCovid19 = null;
				mCasoCovid19 = CasoCovid19Helper.crearCasoCovid19(cursor);
				CasaCohorteFamilia cchf = this.getCasaCohorteFamilia(MainDBConstants.codigoCHF + "='" + cursor.getString(cursor.getColumnIndex(MainDBConstants.casa)) +"'", null);
				mCasoCovid19.setCasa(cchf);
				mCasoCovid19s.add(mCasoCovid19);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mCasoCovid19s;
	}

	//Crear nuevo ParticipanteCasoCovid19 en la base de datos
	public void crearParticipanteCasoCovid19(ParticipanteCasoCovid19 partcaso) throws Exception {
		ContentValues cv = ParticipanteCasoCovid19Helper.crearParticipanteCasoCovid19ContentValues(partcaso);
		mDb.insertOrThrow(Covid19DBConstants.COVID_PARTICIPANTES_CASOS_TABLE, null, cv);
	}

	//Crear nuevo ParticipanteCasoCovid19 en la base de datos desde otro equipo
	public void insertarParticipanteCasoCovid19(String participanteCovid19CasoSQL) {
		mDb.execSQL(participanteCovid19CasoSQL);
	}

	//Editar ParticipanteCasoCovid19 existente en la base de datos
	public boolean editarParticipanteCasoCovid19(ParticipanteCasoCovid19 partcaso) throws Exception{
		ContentValues cv = ParticipanteCasoCovid19Helper.crearParticipanteCasoCovid19ContentValues(partcaso);
		return mDb.update(Covid19DBConstants.COVID_PARTICIPANTES_CASOS_TABLE, cv, Covid19DBConstants.codigoCasoParticipante + "='"
				+ partcaso.getCodigoCasoParticipante() + "'", null) > 0;
	}
	//Limpiar la tabla de ParticipanteCasoCovid19 de la base de datos
	public boolean borrarParticipanteCasoCovid19() {
		return mDb.delete(Covid19DBConstants.COVID_PARTICIPANTES_CASOS_TABLE, null, null) > 0;
	}
	//Obtener un ParticipanteCasoCovid19 de la base de datos
	public ParticipanteCasoCovid19 getParticipanteCasoCovid19(String filtro, String orden) throws SQLException {
		ParticipanteCasoCovid19 mParticipanteCasoCovid19 = null;
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_PARTICIPANTES_CASOS_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mParticipanteCasoCovid19=ParticipanteCasoCovid19Helper.crearParticipanteCasoCovid19(cursor);
			CasoCovid19 caso = this.getCasoCovid19(Covid19DBConstants.codigoCaso + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoCaso))+"'", null);
			mParticipanteCasoCovid19.setCodigoCaso(caso);
			Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.participante)), null);
			mParticipanteCasoCovid19.setParticipante(participante);
		}
		if (!cursor.isClosed()) cursor.close();
		return mParticipanteCasoCovid19;
	}
	//Obtener una lista de ParticipanteCasoCovid19 de la base de datos
	public List<ParticipanteCasoCovid19> getParticipantesCasosCovid19(String filtro, String orden) throws SQLException {
		List<ParticipanteCasoCovid19> mParticipanteCasoCovid19s = new ArrayList<ParticipanteCasoCovid19>();
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_PARTICIPANTES_CASOS_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mParticipanteCasoCovid19s.clear();
			do{
				ParticipanteCasoCovid19 mParticipanteCasoCovid19 = null;
				mParticipanteCasoCovid19 = ParticipanteCasoCovid19Helper.crearParticipanteCasoCovid19(cursor);
				CasoCovid19 caso = this.getCasoCovid19(Covid19DBConstants.codigoCaso + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoCaso))+"'", null);
				mParticipanteCasoCovid19.setCodigoCaso(caso);
				Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.participante)), null);
				mParticipanteCasoCovid19.setParticipante(participante);
				mParticipanteCasoCovid19s.add(mParticipanteCasoCovid19);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mParticipanteCasoCovid19s;
	}

	//Crear nuevo VisitaSeguimientoCasoCovid19 en la base de datos
	public void crearVisitaSeguimientoCasoCovid19(VisitaSeguimientoCasoCovid19 visitacaso) throws Exception {
		ContentValues cv = VisitaSeguimientoCasoCovid19Helper.crearVisitaSeguimientoCasoCovid19ContentValues(visitacaso);
		mDb.insertOrThrow(Covid19DBConstants.COVID_VISITAS_CASOS_TABLE, null, cv);
	}

	//Editar VisitaSeguimientoCasoCovid19 existente en la base de datos
	public boolean editarVisitaSeguimientoCasoCovid19(VisitaSeguimientoCasoCovid19 visitacaso) throws Exception{
		ContentValues cv = VisitaSeguimientoCasoCovid19Helper.crearVisitaSeguimientoCasoCovid19ContentValues(visitacaso);
		return mDb.update(Covid19DBConstants.COVID_VISITAS_CASOS_TABLE , cv, Covid19DBConstants.codigoCasoVisita + "='"
				+ visitacaso.getCodigoCasoVisita() + "'", null) > 0;
	}
	//Limpiar la tabla de VisitaSeguimientoCasoCovid19 de la base de datos
	public boolean borrarVisitaSeguimientoCasoCovid19() {
		return mDb.delete(Covid19DBConstants.COVID_VISITAS_CASOS_TABLE, null, null) > 0;
	}
	//Obtener un VisitaSeguimientoCasoCovid19 de la base de datos
	public VisitaSeguimientoCasoCovid19 getVisitaSeguimientoCasoCovid19(String filtro, String orden) throws SQLException {
		VisitaSeguimientoCasoCovid19 mVisitaSeguimientoCasoCovid19 = null;
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_VISITAS_CASOS_TABLE , filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mVisitaSeguimientoCasoCovid19=VisitaSeguimientoCasoCovid19Helper.crearVisitaSeguimientoCasoCovid19(cursor);
			ParticipanteCasoCovid19 caso = this.getParticipanteCasoCovid19(Covid19DBConstants.codigoCasoParticipante + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoCasoParticipante)) +"'", null);
			mVisitaSeguimientoCasoCovid19.setCodigoParticipanteCaso(caso);
		}
		if (!cursor.isClosed()) cursor.close();
		return mVisitaSeguimientoCasoCovid19;
	}
	//Obtener una lista de VisitaSeguimientoCasoCovid19 de la base de datos
	public List<VisitaSeguimientoCasoCovid19> getVisitasSeguimientosCasosCovid19(String filtro, String orden) throws SQLException {
		List<VisitaSeguimientoCasoCovid19> mVisitaSeguimientoCasoCovid19s = new ArrayList<VisitaSeguimientoCasoCovid19>();
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_VISITAS_CASOS_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mVisitaSeguimientoCasoCovid19s.clear();
			do{
				VisitaSeguimientoCasoCovid19 mVisitaSeguimientoCasoCovid19 = null;
				mVisitaSeguimientoCasoCovid19 = VisitaSeguimientoCasoCovid19Helper.crearVisitaSeguimientoCasoCovid19(cursor);
				ParticipanteCasoCovid19 caso = this.getParticipanteCasoCovid19(Covid19DBConstants.codigoCasoParticipante + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoCasoParticipante)) +"'", null);
				mVisitaSeguimientoCasoCovid19.setCodigoParticipanteCaso(caso);
				mVisitaSeguimientoCasoCovid19s.add(mVisitaSeguimientoCasoCovid19);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mVisitaSeguimientoCasoCovid19s;
	}

	//Limpiar la tabla de Muestras Positivos UO1 de la base de datos
	public boolean borrarMuestrasCovid19() {
		return mDb.delete(MuestrasDBConstants.MUESTRA_TABLE, MuestrasDBConstants.proposito + " in ('"+Constants.CODIGO_PROPOSITO_COVID_CP+"','"+Constants.CODIGO_PROPOSITO_T_COVID+"')"  , null) > 0;
	}

	//Crear nuevo VisitaFallidaCasoCovid19 en la base de datos
	public void crearVisitaFallidaCasoCovid19(VisitaFallidaCasoCovid19 visitaFallida) throws Exception {
		ContentValues cv = VisitaFallidaCasoCovid19Helper.crearVisitaFallidaCasoCovid19ContentValues(visitaFallida);
		mDb.insertOrThrow(Covid19DBConstants.COVID_VISITAS_FALLIDAS_CASOS_TABLE, null, cv);
	}

	//Editar VisitaFallidaCasoCovid19 existente en la base de datos
	public boolean editarVisitaFallidaCasoCovid19(VisitaFallidaCasoCovid19 visitaFallida) throws Exception{
		ContentValues cv = VisitaFallidaCasoCovid19Helper.crearVisitaFallidaCasoCovid19ContentValues(visitaFallida);
		return mDb.update(Covid19DBConstants.COVID_VISITAS_FALLIDAS_CASOS_TABLE , cv, Covid19DBConstants.codigoFallaVisita + "='"
				+ visitaFallida.getCodigoFallaVisita() + "'", null) > 0;
	}
	//Limpiar la tabla de VisitaFallidaCasoCovid19 de la base de datos
	public boolean borrarVisitaFallidaCasoCovid19() {
		return mDb.delete(Covid19DBConstants.COVID_VISITAS_FALLIDAS_CASOS_TABLE, null, null) > 0;
	}

	//Obtener un VisitaFallidaCasoCovid19 de la base de datos
	public VisitaFallidaCasoCovid19 getVisitaFallidaCasoCovid19(String filtro, String orden) throws SQLException {
		VisitaFallidaCasoCovid19 mVisitaFallidaCasoCovid19 = null;
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_VISITAS_FALLIDAS_CASOS_TABLE , filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mVisitaFallidaCasoCovid19= VisitaFallidaCasoCovid19Helper.crearVisitaFallidaCasoCovid19(cursor);
			ParticipanteCasoCovid19 caso = this.getParticipanteCasoCovid19(Covid19DBConstants.codigoCasoParticipante + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoParticipanteCaso)) +"'", null);
			mVisitaFallidaCasoCovid19.setCodigoParticipanteCaso(caso);
		}
		if (!cursor.isClosed()) cursor.close();
		return mVisitaFallidaCasoCovid19;
	}
	//Obtener una lista de VisitaFallidaCasoCovid19 de la base de datos
	public List<VisitaFallidaCasoCovid19> getVisitasFallidasCasoCovid19(String filtro, String orden) throws SQLException {
		List<VisitaFallidaCasoCovid19> mVisitaFallidaCasoCovid19s = new ArrayList<VisitaFallidaCasoCovid19>();
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_VISITAS_FALLIDAS_CASOS_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mVisitaFallidaCasoCovid19s.clear();
			do{
				VisitaFallidaCasoCovid19 mVisitaFallidaCasoCovid19 = null;
				mVisitaFallidaCasoCovid19 = VisitaFallidaCasoCovid19Helper.crearVisitaFallidaCasoCovid19(cursor);
				ParticipanteCasoCovid19 caso = this.getParticipanteCasoCovid19(Covid19DBConstants.codigoCasoParticipante + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoParticipanteCaso)) +"'", null);
				mVisitaFallidaCasoCovid19.setCodigoParticipanteCaso(caso);
				mVisitaFallidaCasoCovid19s.add(mVisitaFallidaCasoCovid19);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mVisitaFallidaCasoCovid19s;
	}

	//Crear nuevo CandidatoTransmisionCovid19 en la base de datos
	public void crearCandidatoTransmisionCovid19(CandidatoTransmisionCovid19 partcaso) throws Exception {
		ContentValues cv = CandidatoTransmisionCovid19Helper.crearCandidatoTransmisionCovid19ContentValues(partcaso);
		mDb.insertOrThrow(Covid19DBConstants.COVID_CANDIDATO_TRANSMISION_TABLE, null, cv);
	}

	//Crear nuevo CandidatoTransmisionCovid19 en la base de datos desde otro equipo
	public void insertarCandidatoTransmisionCovid19(String participanteCohorteFamiliaCasoSQL) {
		mDb.execSQL(participanteCohorteFamiliaCasoSQL);
	}

	//Editar CandidatoTransmisionCovid19 existente en la base de datos
	public boolean editarCandidatoTransmisionCovid19(CandidatoTransmisionCovid19 partcaso) throws Exception{
		ContentValues cv = CandidatoTransmisionCovid19Helper.crearCandidatoTransmisionCovid19ContentValues(partcaso);
		return mDb.update(Covid19DBConstants.COVID_CANDIDATO_TRANSMISION_TABLE, cv, Covid19DBConstants.codigo + "='"
				+ partcaso.getCodigo() + "'", null) > 0;
	}
	//Limpiar la tabla de CandidatoTransmisionCovid19 de la base de datos
	public boolean borrarCandidatoTransmisionCovid19() {
		return mDb.delete(Covid19DBConstants.COVID_CANDIDATO_TRANSMISION_TABLE, null, null) > 0;
	}
	//Obtener un CandidatoTransmisionCovid19 de la base de datos
	public CandidatoTransmisionCovid19 getCandidatoTransmisionCovid19(String filtro, String orden) throws SQLException {
		CandidatoTransmisionCovid19 mCandidatoTransmisionCovid19 = null;
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_CANDIDATO_TRANSMISION_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mCandidatoTransmisionCovid19=CandidatoTransmisionCovid19Helper.crearCandidatoTransmisionCovid19(cursor);
			Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.participante)), null);
			mCandidatoTransmisionCovid19.setParticipante(participante);
		}
		if (!cursor.isClosed()) cursor.close();
		return mCandidatoTransmisionCovid19;
	}
	//Obtener una lista de CandidatoTransmisionCovid19 de la base de datos
	public List<CandidatoTransmisionCovid19> getCandidatosTransmisionCovid19(String filtro, String orden) throws SQLException {
		List<CandidatoTransmisionCovid19> mCandidatoTransmisionCovid19s = new ArrayList<CandidatoTransmisionCovid19>();
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_CANDIDATO_TRANSMISION_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mCandidatoTransmisionCovid19s.clear();
			do{
				CandidatoTransmisionCovid19 mCandidatoTransmisionCovid19 = null;
				mCandidatoTransmisionCovid19 = CandidatoTransmisionCovid19Helper.crearCandidatoTransmisionCovid19(cursor);
				Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.participante)), null);
				mCandidatoTransmisionCovid19.setParticipante(participante);
				mCandidatoTransmisionCovid19s.add(mCandidatoTransmisionCovid19);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mCandidatoTransmisionCovid19s;
	}

	//Crear nuevo SintomasVisitaCasoCovid19 en la base de datos
	public void crearSintomasVisitaCasoCovid19(SintomasVisitaCasoCovid19 sintomasVisitaCasoCovid19) throws Exception {
		ContentValues cv = SintomasVisitaCasoCovid19Helper.crearSintomasVisitaCasoCovid19ContentValues(sintomasVisitaCasoCovid19);
		mDb.insertOrThrow(Covid19DBConstants.COVID_SINTOMAS_VISITA_CASO_TABLE, null, cv);
	}

	//Editar SintomasVisitaCasoCovid19 existente en la base de datos
	public boolean editarSintomasVisitaCasoCovid19(SintomasVisitaCasoCovid19 sintomasVisitaCasoCovid19) throws Exception{
		ContentValues cv = SintomasVisitaCasoCovid19Helper.crearSintomasVisitaCasoCovid19ContentValues(sintomasVisitaCasoCovid19);
		return mDb.update(Covid19DBConstants.COVID_SINTOMAS_VISITA_CASO_TABLE , cv, Covid19DBConstants.codigoCasoSintoma + "='"
				+ sintomasVisitaCasoCovid19.getCodigoCasoSintoma() + "'", null) > 0;
	}
	//Limpiar la tabla de SintomasVisitaCasoCovid19 de la base de datos
	public boolean borrarSintomasVisitaCasoCovid19() {
		return mDb.delete(Covid19DBConstants.COVID_SINTOMAS_VISITA_CASO_TABLE, null, null) > 0;
	}

	//Obtener un SintomasVisitaCasoCovid19 de la base de datos
	public SintomasVisitaCasoCovid19 getSintomasVisitaCasoCovid19(String filtro, String orden) throws SQLException {
		SintomasVisitaCasoCovid19 mSintomasVisitaCasoCovid19 = null;
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_SINTOMAS_VISITA_CASO_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mSintomasVisitaCasoCovid19=SintomasVisitaCasoCovid19Helper.crearSintomasVisitaCasoCovid19(cursor);
			VisitaSeguimientoCasoCovid19 visitaSeguimientoCasoCovid19 = this.getVisitaSeguimientoCasoCovid19(Covid19DBConstants.codigoCasoVisita + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoCasoVisita))+"'", null);
			mSintomasVisitaCasoCovid19.setCodigoCasoVisita(visitaSeguimientoCasoCovid19);
		}
		if (!cursor.isClosed()) cursor.close();
		return mSintomasVisitaCasoCovid19;
	}

	//Obtener un SintomasVisitaCasoCovid19 de la base de datos
	public List<SintomasVisitaCasoCovid19> getSintomasVisitasCasosCovid19(String filtro, String orden) throws SQLException {
		List<SintomasVisitaCasoCovid19> mSintomasVisitasCasosCovid19 = new ArrayList<SintomasVisitaCasoCovid19>();
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_SINTOMAS_VISITA_CASO_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mSintomasVisitasCasosCovid19.clear();
			do{
				SintomasVisitaCasoCovid19 sintomasVisitaCasoCovid19 = SintomasVisitaCasoCovid19Helper.crearSintomasVisitaCasoCovid19(cursor);
				VisitaSeguimientoCasoCovid19 visitaSeguimientoCasoCovid19 = this.getVisitaSeguimientoCasoCovid19(Covid19DBConstants.codigoCasoVisita + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoCasoVisita))+"'", null);
				sintomasVisitaCasoCovid19.setCodigoCasoVisita(visitaSeguimientoCasoCovid19);
				mSintomasVisitasCasosCovid19.add(sintomasVisitaCasoCovid19);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mSintomasVisitasCasosCovid19;
	}

	/***DatosAislamientoVisitaCasoCovid19**/
	//Crear nuevo DatosAislamientoVisitaCasoCovid19 en la base de datos
	public void crearDatosAislamientoVisitaCasoCovid19(DatosAislamientoVisitaCasoCovid19 DatosAislamientoVisitaCasoCovid19) throws Exception {
		ContentValues cv = DatosAislamientoVisitaCasoCovid19Helper.crearDatosAislamientoVisitaCasoCovid19ContentValues(DatosAislamientoVisitaCasoCovid19);
		mDb.insertOrThrow(Covid19DBConstants.COVID_DATOS_AISLAMIENTO_VC_TABLE, null, cv);
	}

	//Editar DatosAislamientoVisitaCasoCovid19 existente en la base de datos
	public boolean editarDatosAislamientoVisitaCasoCovid19(DatosAislamientoVisitaCasoCovid19 DatosAislamientoVisitaCasoCovid19) throws Exception{
		ContentValues cv = DatosAislamientoVisitaCasoCovid19Helper.crearDatosAislamientoVisitaCasoCovid19ContentValues(DatosAislamientoVisitaCasoCovid19);
		return mDb.update(Covid19DBConstants.COVID_DATOS_AISLAMIENTO_VC_TABLE , cv, Covid19DBConstants.codigoAislamiento + "='"
				+ DatosAislamientoVisitaCasoCovid19.getCodigoAislamiento() + "'", null) > 0;
	}
	//Limpiar la tabla de DatosAislamientoVisitaCasoCovid19 de la base de datos
	public boolean borrarDatosAislamientoVisitaCasoCovid19() {
		return mDb.delete(Covid19DBConstants.COVID_DATOS_AISLAMIENTO_VC_TABLE, null, null) > 0;
	}

	//Obtener un DatosAislamientoVisitaCasoCovid19 de la base de datos
	public DatosAislamientoVisitaCasoCovid19 getDatosAislamientoVisitaCasoCovid19(String filtro, String orden) throws SQLException {
		DatosAislamientoVisitaCasoCovid19 mDatosAislamientoVisitaCasoCovid19 = null;
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_DATOS_AISLAMIENTO_VC_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mDatosAislamientoVisitaCasoCovid19=DatosAislamientoVisitaCasoCovid19Helper.crearDatosAislamientoVisitaCasoCovid19(cursor);
			VisitaSeguimientoCasoCovid19 visitaSeguimientoCasoCovid19 = this.getVisitaSeguimientoCasoCovid19(Covid19DBConstants.codigoCasoVisita + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoCasoVisita))+"'", null);
			mDatosAislamientoVisitaCasoCovid19.setCodigoCasoVisita(visitaSeguimientoCasoCovid19);
		}
		if (!cursor.isClosed()) cursor.close();
		return mDatosAislamientoVisitaCasoCovid19;
	}

	//Obtener un DatosAislamientoVisitaCasoCovid19 de la base de datos
	public List<DatosAislamientoVisitaCasoCovid19> getDatosAislamientoVisitasCasosCovid19(String filtro, String orden) throws SQLException {
		List<DatosAislamientoVisitaCasoCovid19> mSintomasVisitasCasosCovid19 = new ArrayList<DatosAislamientoVisitaCasoCovid19>();
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_DATOS_AISLAMIENTO_VC_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mSintomasVisitasCasosCovid19.clear();
			do{
				DatosAislamientoVisitaCasoCovid19 DatosAislamientoVisitaCasoCovid19 = DatosAislamientoVisitaCasoCovid19Helper.crearDatosAislamientoVisitaCasoCovid19(cursor);
				VisitaSeguimientoCasoCovid19 visitaSeguimientoCasoCovid19 = this.getVisitaSeguimientoCasoCovid19(Covid19DBConstants.codigoCasoVisita + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoCasoVisita))+"'", null);
				DatosAislamientoVisitaCasoCovid19.setCodigoCasoVisita(visitaSeguimientoCasoCovid19);
				mSintomasVisitasCasosCovid19.add(DatosAislamientoVisitaCasoCovid19);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mSintomasVisitasCasosCovid19;
	}

	//Limpiar la tabla de Muestras Transmision de la base de datos
	public boolean borrarProcesoCovid(String filtro) {
		return mDb.delete(ConstantsDB.PART_PROCESOS_TABLE, filtro, null) > 0;
	}

	//Crear nuevo VisitaFinalCasoCovid19 en la base de datos
	public void crearVisitaFinalCasoCovid19(VisitaFinalCasoCovid19 visitacaso) throws Exception {
		ContentValues cv = VisitaFinalCasoCovid19Helper.crearVisitaFinalCasoCovid19ContentValues(visitacaso);
		mDb.insertOrThrow(Covid19DBConstants.COVID_VISITA_FINAL_CASO_TABLE, null, cv);
	}

	//Editar VisitaFinalCasoCovid19 existente en la base de datos
	public boolean editarVisitaFinalCasoCovid19(VisitaFinalCasoCovid19 visitacaso) throws Exception{
		ContentValues cv = VisitaFinalCasoCovid19Helper.crearVisitaFinalCasoCovid19ContentValues(visitacaso);
		return mDb.update(Covid19DBConstants.COVID_VISITA_FINAL_CASO_TABLE , cv, Covid19DBConstants.codigoVisitaFinal + "='"
				+ visitacaso.getCodigoVisitaFinal() + "'", null) > 0;
	}
	//Limpiar la tabla de VisitaFinalCasoCovid19 de la base de datos
	public boolean borrarVisitaFinalCasoCovid19() {
		return mDb.delete(Covid19DBConstants.COVID_VISITA_FINAL_CASO_TABLE, null, null) > 0;
	}
	//Obtener un VisitaFinalCasoCovid19 de la base de datos
	public VisitaFinalCasoCovid19 getVisitaFinalCasoCovid19(String filtro, String orden) throws SQLException {
		VisitaFinalCasoCovid19 mVisitaFinalCasoCovid19 = null;
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_VISITA_FINAL_CASO_TABLE , filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mVisitaFinalCasoCovid19=VisitaFinalCasoCovid19Helper.crearVisitaFinalCasoCovid19(cursor);
			ParticipanteCasoCovid19 caso = this.getParticipanteCasoCovid19(Covid19DBConstants.codigoCasoParticipante + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoParticipanteCaso)) +"'", null);
			mVisitaFinalCasoCovid19.setCodigoParticipanteCaso(caso);
		}
		if (!cursor.isClosed()) cursor.close();
		return mVisitaFinalCasoCovid19;
	}
	//Obtener una lista de VisitaFinalCasoCovid19 de la base de datos
	public List<VisitaFinalCasoCovid19> getVisitasFinalesCasosCovid19(String filtro, String orden) throws SQLException {
		List<VisitaFinalCasoCovid19> mVisitaFinalCasoCovid19s = new ArrayList<VisitaFinalCasoCovid19>();
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_VISITA_FINAL_CASO_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mVisitaFinalCasoCovid19s.clear();
			do{
				VisitaFinalCasoCovid19 mVisitaFinalCasoCovid19 = null;
				mVisitaFinalCasoCovid19 = VisitaFinalCasoCovid19Helper.crearVisitaFinalCasoCovid19(cursor);
				ParticipanteCasoCovid19 caso = this.getParticipanteCasoCovid19(Covid19DBConstants.codigoCasoParticipante + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoParticipanteCaso)) +"'", null);
				mVisitaFinalCasoCovid19.setCodigoParticipanteCaso(caso);
				mVisitaFinalCasoCovid19s.add(mVisitaFinalCasoCovid19);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mVisitaFinalCasoCovid19s;
	}

	/*SINTOMAS VISITAS FINALES CASOS COVID19*/
	//Crear nuevo SintomasVisitaFinalCovid19 en la base de datos
	public void crearSintomasVisitaFinalCovid19(SintomasVisitaFinalCovid19 visitacaso) throws Exception {
		ContentValues cv = SintomasVisitaFinalCovid19Helper.crearSintomasVisitaFinalCovid19ContentValues(visitacaso);
		mDb.insertOrThrow(Covid19DBConstants.COVID_SINT_VISITA_FINAL_CASO_TABLE, null, cv);
	}

	//Editar SintomasVisitaFinalCovid19 existente en la base de datos
	public boolean editarSintomasVisitaFinalCovid19(SintomasVisitaFinalCovid19 visitacaso) throws Exception{
		ContentValues cv = SintomasVisitaFinalCovid19Helper.crearSintomasVisitaFinalCovid19ContentValues(visitacaso);
		return mDb.update(Covid19DBConstants.COVID_SINT_VISITA_FINAL_CASO_TABLE , cv, Covid19DBConstants.codigoVisitaFinal + "='"
				+ visitacaso.getCodigoVisitaFinal() + "'", null) > 0;
	}
	//Limpiar la tabla de SintomasVisitaFinalCovid19 de la base de datos
	public boolean borrarSintomasVisitaFinalCovid19() {
		return mDb.delete(Covid19DBConstants.COVID_SINT_VISITA_FINAL_CASO_TABLE, null, null) > 0;
	}
	//Obtener un SintomasVisitaFinalCovid19 de la base de datos
	public SintomasVisitaFinalCovid19 getSintomasVisitaFinalCovid19(String filtro, String orden) throws SQLException {
		SintomasVisitaFinalCovid19 mSintomasVisitaFinalCovid19 = null;
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_SINT_VISITA_FINAL_CASO_TABLE , filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mSintomasVisitaFinalCovid19=SintomasVisitaFinalCovid19Helper.crearSintomasVisitaFinalCovid19(cursor);
			VisitaFinalCasoCovid19 visita = this.getVisitaFinalCasoCovid19(Covid19DBConstants.codigoVisitaFinal + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoVisitaFinal)) +"'", null);
			mSintomasVisitaFinalCovid19.setCodigoVisitaFinal(visita);
		}
		if (!cursor.isClosed()) cursor.close();
		return mSintomasVisitaFinalCovid19;
	}
	//Obtener una lista de SintomasVisitaFinalCovid19 de la base de datos
	public List<SintomasVisitaFinalCovid19> getSintomasVisitasFinalesCovid19(String filtro, String orden) throws SQLException {
		List<SintomasVisitaFinalCovid19> mSintomasVisitaFinalCovid19s = new ArrayList<SintomasVisitaFinalCovid19>();
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_SINT_VISITA_FINAL_CASO_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mSintomasVisitaFinalCovid19s.clear();
			do{
				SintomasVisitaFinalCovid19 mSintomasVisitaFinalCovid19 = null;
				mSintomasVisitaFinalCovid19=SintomasVisitaFinalCovid19Helper.crearSintomasVisitaFinalCovid19(cursor);
				VisitaFinalCasoCovid19 visita = this.getVisitaFinalCasoCovid19(Covid19DBConstants.codigoVisitaFinal + "='" +cursor.getString(cursor.getColumnIndex(Covid19DBConstants.codigoVisitaFinal)) +"'", null);
				mSintomasVisitaFinalCovid19.setCodigoVisitaFinal(visita);
				mSintomasVisitaFinalCovid19s.add(mSintomasVisitaFinalCovid19);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mSintomasVisitaFinalCovid19s;
	}

	//Crear nuevo CuestionarioCovid19 en la base de datos
	public void crearCuestionarioCovid19(CuestionarioCovid19 partcaso) throws Exception {
		ContentValues cv = CuestionarioCovid19Helper.crearCuestionarioCovid19ContentValues(partcaso);
		mDb.insertOrThrow(Covid19DBConstants.COVID_CUESTIONARIO_TABLE, null, cv);
	}

	//Crear nuevo CuestionarioCovid19 en la base de datos desde otro equipo
	public void insertarCuestionarioCovid19(String participanteCohorteFamiliaCasoSQL) {
		mDb.execSQL(participanteCohorteFamiliaCasoSQL);
	}

	//Editar CuestionarioCovid19 existente en la base de datos
	public boolean editarCuestionarioCovid19(CuestionarioCovid19 partcaso) throws Exception{
		ContentValues cv = CuestionarioCovid19Helper.crearCuestionarioCovid19ContentValues(partcaso);
		return mDb.update(Covid19DBConstants.COVID_CUESTIONARIO_TABLE, cv, Covid19DBConstants.codigo + "='"
				+ partcaso.getCodigo() + "'", null) > 0;
	}
	//Limpiar la tabla de CuestionarioCovid19 de la base de datos
	public boolean borrarCuestionarioCovid19() {
		return mDb.delete(Covid19DBConstants.COVID_CUESTIONARIO_TABLE, null, null) > 0;
	}
	//Obtener un CuestionarioCovid19 de la base de datos
	public CuestionarioCovid19 getCuestionarioCovid19(String filtro, String orden) throws SQLException {
		CuestionarioCovid19 mCuestionarioCovid19 = null;
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_CUESTIONARIO_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mCuestionarioCovid19=CuestionarioCovid19Helper.crearCuestionarioCovid19(cursor);
			Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.participante)), null);
			mCuestionarioCovid19.setParticipante(participante);
		}
		if (!cursor.isClosed()) cursor.close();
		return mCuestionarioCovid19;
	}
	//Obtener una lista de CuestionarioCovid19 de la base de datos
	public List<CuestionarioCovid19> getCuestionariosCovid19(String filtro, String orden) throws SQLException {
		List<CuestionarioCovid19> mCuestionarioCovid19s = new ArrayList<CuestionarioCovid19>();
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_CUESTIONARIO_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mCuestionarioCovid19s.clear();
			do{
				CuestionarioCovid19 mCuestionarioCovid19 = null;
				mCuestionarioCovid19 = CuestionarioCovid19Helper.crearCuestionarioCovid19(cursor);
				Participante participante = this.getParticipante(MainDBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.participante)), null);
				mCuestionarioCovid19.setParticipante(participante);
				mCuestionarioCovid19s.add(mCuestionarioCovid19);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mCuestionarioCovid19s;
	}

	//Crear nuevo OtrosPositivosCovid en la base de datos
	public void crearOtrosPositivosCovid(OtrosPositivosCovid partcaso) throws Exception {
		ContentValues cv = CandidatoTransmisionCovid19Helper.crearOtrosPositivosCovidContentValues(partcaso);
		mDb.insertOrThrow(Covid19DBConstants.COVID_OTROS_POSITIVOS_TABLE, null, cv);
	}

	//Crear nuevo OtrosPositivosCovid en la base de datos desde otro equipo
	public void insertarOtrosPositivosCovid(String participanteCohorteFamiliaCasoSQL) {
		mDb.execSQL(participanteCohorteFamiliaCasoSQL);
	}

	//Editar OtrosPositivosCovid existente en la base de datos
	public boolean editarOtrosPositivosCovid(OtrosPositivosCovid partcaso) throws Exception{
		ContentValues cv = CandidatoTransmisionCovid19Helper.crearOtrosPositivosCovidContentValues(partcaso);
		return mDb.update(Covid19DBConstants.COVID_OTROS_POSITIVOS_TABLE, cv, Covid19DBConstants.codigo + "='"
				+ partcaso.getCodigo() + "'", null) > 0;
	}
	//Limpiar la tabla de OtrosPositivosCovid de la base de datos
	public boolean borrarOtrosPositivosCovid() {
		return mDb.delete(Covid19DBConstants.COVID_OTROS_POSITIVOS_TABLE, null, null) > 0;
	}
	//Obtener un OtrosPositivosCovid de la base de datos
	public OtrosPositivosCovid getOtrosPositivosCovid(String filtro, String orden) throws SQLException {
		OtrosPositivosCovid mOtrosPositivosCovid = null;
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_OTROS_POSITIVOS_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mOtrosPositivosCovid = CandidatoTransmisionCovid19Helper.crearOtrosPositivosCovid(cursor);
			CandidatoTransmisionCovid19 candidato = this.getCandidatoTransmisionCovid19(Covid19DBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.codigoCandidato)), null);
			mOtrosPositivosCovid.setCandidatoTransmisionCovid19(candidato);
		}
		if (!cursor.isClosed()) cursor.close();
		return mOtrosPositivosCovid;
	}
	//Obtener una lista de OtrosPositivosCovid de la base de datos
	public List<OtrosPositivosCovid> getOtrosPositivosCovidList(String filtro, String orden) throws SQLException {
		List<OtrosPositivosCovid> mOtrosPositivosCovids = new ArrayList<OtrosPositivosCovid>();
		Cursor cursor = crearCursor(Covid19DBConstants.COVID_OTROS_POSITIVOS_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mOtrosPositivosCovids.clear();
			do{
				OtrosPositivosCovid mOtrosPositivosCovid = null;
				mOtrosPositivosCovid = CandidatoTransmisionCovid19Helper.crearOtrosPositivosCovid(cursor);
				CandidatoTransmisionCovid19 candidato = this.getCandidatoTransmisionCovid19(Covid19DBConstants.codigo + "=" +cursor.getInt(cursor.getColumnIndex(Covid19DBConstants.codigoCandidato)), null);
				mOtrosPositivosCovid.setCandidatoTransmisionCovid19(candidato);
				mOtrosPositivosCovids.add(mOtrosPositivosCovid);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mOtrosPositivosCovids;
	}

	/*****INICIO ENTOMOLOGIA***/
	//Crear nuevo CuestionarioHogar en la base de datos
	public void crearCuestionarioHogar(CuestionarioHogar cuestionarioHogar) throws Exception {
		ContentValues cv = EntomologiaHelper.crearCuestionarioHogarContentValues(cuestionarioHogar);
		mDb.insertOrThrow(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_TABLE, null, cv);
	}

	//Editar CuestionarioHogar existente en la base de datos
	public boolean editarCuestionarioHogar(CuestionarioHogar cuestionarioHogar) throws Exception{
		ContentValues cv = EntomologiaHelper.crearCuestionarioHogarContentValues(cuestionarioHogar);
		return mDb.update(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_TABLE , cv, EntomologiaBConstants.codigoEncuesta + "='"
				+ cuestionarioHogar.getCodigoEncuesta() + "'", null) > 0;
	}
	//Limpiar la tabla de CuestionarioHogar de la base de datos
	public boolean borrarCuestionarioHogar() {
		return mDb.delete(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_TABLE, null, null) > 0;
	}
	//Obtener un CuestionarioHogar de la base de datos
	public CuestionarioHogar getCuestionarioHogar(String filtro, String orden) throws SQLException {
		CuestionarioHogar mCuestionarioHogar = null;
		Cursor cursor = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_TABLE , filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mCuestionarioHogar=EntomologiaHelper.crearCuestionarioHogar(cursor);
		}
		if (!cursor.isClosed()) cursor.close();
		return mCuestionarioHogar;
	}
	//Obtener una lista de CuestionarioHogar de la base de datos
	public List<CuestionarioHogar> getCuestionariosHogar(String filtro, String orden) throws SQLException {
		List<CuestionarioHogar> mCuestionarioHogars = new ArrayList<CuestionarioHogar>();
		Cursor cursor = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mCuestionarioHogars.clear();
			do{
				CuestionarioHogar mCuestionarioHogar = null;
				mCuestionarioHogar=EntomologiaHelper.crearCuestionarioHogar(cursor);
				mCuestionarioHogars.add(mCuestionarioHogar);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mCuestionarioHogars;
	}

	//Crear nuevo CuestionarioHogarPoblacion en la base de datos
	public void crearCuestionarioHogarPoblacion(CuestionarioHogarPoblacion cuestionarioHogarPoblacion) throws Exception {
		ContentValues cv = EntomologiaHelper.crearCuestionarioHogarPoblacionContentValues(cuestionarioHogarPoblacion);
		mDb.insertOrThrow(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_POB_TABLE, null, cv);
	}

	//Editar CuestionarioHogarPoblacion existente en la base de datos
	public boolean editarCuestionarioHogarPoblacion(CuestionarioHogarPoblacion cuestionarioHogarPoblacion) throws Exception{
		ContentValues cv = EntomologiaHelper.crearCuestionarioHogarPoblacionContentValues(cuestionarioHogarPoblacion);
		return mDb.update(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_POB_TABLE , cv, EntomologiaBConstants.codigoPoblacion + "='"
				+ cuestionarioHogarPoblacion.getCodigoPoblacion() + "' ", null) > 0;
	}
	//Limpiar la tabla de CuestionarioHogarPoblacion de la base de datos
	public boolean borrarCuestionarioHogarPoblacion() {
		return mDb.delete(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_POB_TABLE, null, null) > 0;
	}
	//Obtener un CuestionarioHogarPoblacion de la base de datos
	public CuestionarioHogarPoblacion getCuestionarioHogarPoblacion(String filtro, String orden) throws SQLException {
		CuestionarioHogarPoblacion mCuestionarioHogarPoblacion = null;
		Cursor cursor = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_POB_TABLE , filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mCuestionarioHogarPoblacion=EntomologiaHelper.crearCuestionarioHogarPoblacion(cursor);
		}
		if (!cursor.isClosed()) cursor.close();
		return mCuestionarioHogarPoblacion;
	}
	//Obtener una lista de CuestionarioHogarPoblacion de la base de datos
	public List<CuestionarioHogarPoblacion> getCuestionariosHogarPoblacion(String filtro, String orden) throws SQLException {
		List<CuestionarioHogarPoblacion> mCuestionarioHogarPoblacions = new ArrayList<CuestionarioHogarPoblacion>();
		Cursor cursor = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_HOGAR_POB_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mCuestionarioHogarPoblacions.clear();
			do{
				CuestionarioHogarPoblacion mCuestionarioHogarPoblacion = null;
				mCuestionarioHogarPoblacion=EntomologiaHelper.crearCuestionarioHogarPoblacion(cursor);
				mCuestionarioHogarPoblacions.add(mCuestionarioHogarPoblacion);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mCuestionarioHogarPoblacions;
	}

	//Crear nuevo CuestionarioPuntoClave en la base de datos
	public void crearCuestionarioPuntoClave(CuestionarioPuntoClave cuestionarioPuntoClave) throws Exception {
		ContentValues cv = EntomologiaHelper.crearCuestionarioPuntoClaveContentValues(cuestionarioPuntoClave);
		mDb.insertOrThrow(EntomologiaBConstants.ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE, null, cv);
	}

	//Editar CuestionarioPuntoClave existente en la base de datos
	public boolean editarCuestionarioPuntoClave(CuestionarioPuntoClave cuestionarioPuntoClave) throws Exception{
		ContentValues cv = EntomologiaHelper.crearCuestionarioPuntoClaveContentValues(cuestionarioPuntoClave);
		return mDb.update(EntomologiaBConstants.ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE , cv, EntomologiaBConstants.codigoCuestionario + "='"
				+ cuestionarioPuntoClave.getCodigoCuestionario() + "' ", null) > 0;
	}
	//Limpiar la tabla de CuestionarioPuntoClave de la base de datos
	public boolean borrarCuestionarioPuntoClave() {
		return mDb.delete(EntomologiaBConstants.ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE, null, null) > 0;
	}
	//Obtener un CuestionarioPuntoClave de la base de datos
	public CuestionarioPuntoClave getCuestionarioPuntoClave(String filtro, String orden) throws SQLException {
		CuestionarioPuntoClave mCuestionarioPuntoClave = null;
		Cursor cursor = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE , filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mCuestionarioPuntoClave=EntomologiaHelper.crearCuestionarioPuntoClave(cursor);
		}
		if (!cursor.isClosed()) cursor.close();
		return mCuestionarioPuntoClave;
	}
	//Obtener una lista de CuestionarioPuntoClave de la base de datos
	public List<CuestionarioPuntoClave> getCuestionariosPuntoClave(String filtro, String orden) throws SQLException {
		List<CuestionarioPuntoClave> mCuestionarioPuntoClaves = new ArrayList<CuestionarioPuntoClave>();
		Cursor cursor = crearCursor(EntomologiaBConstants.ENTO_CUESTIONARIO_PUNTO_CLAVE_TABLE, filtro, null, orden);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			mCuestionarioPuntoClaves.clear();
			do{
				CuestionarioPuntoClave mCuestionarioPuntoClave = null;
				mCuestionarioPuntoClave=EntomologiaHelper.crearCuestionarioPuntoClave(cursor);
				mCuestionarioPuntoClaves.add(mCuestionarioPuntoClave);
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) cursor.close();
		return mCuestionarioPuntoClaves;
	}

	/**METODOS PARA PerimetroAbdominal**/

	/**
	 * Inserta un PerimetroAbdominal en la base de datos
	 *
	 * @param perimetroAbdominal
	 *            Objeto PerimetroAbdominal que contiene la informacion
	 *
	 */
	public void crearPerimetroAbdominal(PerimetroAbdominal perimetroAbdominal) {
		ContentValues cv = new ContentValues();
		cv.put(ConstantsDB.CODIGO, perimetroAbdominal.getPaId().getCodigo());
		cv.put(ConstantsDB.FECHA, perimetroAbdominal.getPaId().getFecha().getTime());
		cv.put(ConstantsDB.PABDOMINAL1, perimetroAbdominal.getPabdominal1());
		cv.put(ConstantsDB.PABDOMINAL2, perimetroAbdominal.getPabdominal2());
		cv.put(ConstantsDB.PABDOMINAL3, perimetroAbdominal.getPabdominal3());
		cv.put(ConstantsDB.DIFPABDOMINAL, perimetroAbdominal.getDifpabdominal());
		cv.put(ConstantsDB.tomoMedidaSn, perimetroAbdominal.getTomoMedidaSn());
		cv.put(ConstantsDB.razonNoTomoMedidas, perimetroAbdominal.getRazonNoTomoMedidas());
		cv.put(ConstantsDB.estudiosAct, perimetroAbdominal.getEstudiosAct());
		cv.put(ConstantsDB.otrorecurso1, perimetroAbdominal.getOtrorecurso1());
		cv.put(ConstantsDB.otrorecurso2, perimetroAbdominal.getOtrorecurso2());
		cv.put(ConstantsDB.ID_INSTANCIA, perimetroAbdominal.getMovilInfo().getIdInstancia());
		cv.put(ConstantsDB.FILE_PATH, perimetroAbdominal.getMovilInfo().getInstancePath());
		cv.put(ConstantsDB.STATUS, perimetroAbdominal.getMovilInfo().getEstado());
		cv.put(ConstantsDB.WHEN_UPDATED, perimetroAbdominal.getMovilInfo().getUltimoCambio());
		cv.put(ConstantsDB.START, perimetroAbdominal.getMovilInfo().getStart());
		cv.put(ConstantsDB.END, perimetroAbdominal.getMovilInfo().getEnd());
		cv.put(ConstantsDB.DEVICE_ID, perimetroAbdominal.getMovilInfo().getDeviceid());
		cv.put(ConstantsDB.SIM_SERIAL, perimetroAbdominal.getMovilInfo().getSimserial());
		cv.put(ConstantsDB.PHONE_NUMBER, perimetroAbdominal.getMovilInfo().getPhonenumber());
		cv.put(ConstantsDB.TODAY, perimetroAbdominal.getMovilInfo().getToday().getTime());
		cv.put(ConstantsDB.USUARIO, perimetroAbdominal.getMovilInfo().getUsername());
		cv.put(ConstantsDB.DELETED, perimetroAbdominal.getMovilInfo().getEliminado());
		cv.put(ConstantsDB.REC1, perimetroAbdominal.getMovilInfo().getRecurso1());
		cv.put(ConstantsDB.REC2, perimetroAbdominal.getMovilInfo().getRecurso2());
		mDb.insertOrThrow(ConstantsDB.PERIMETRO_ABDOMINAL_TABLE, null, cv);
	}

	/**
	 * Borra todas las RecepcionBHC de la base de datos
	 *
	 * @return verdadero o falso
	 */
	public boolean borrarPerimetroAbdominal() {
		return mDb.delete(ConstantsDB.PERIMETRO_ABDOMINAL_TABLE, null, null) > 0;
	}

	/********************************************************/
	/**
	 * Obtiene Lista todas los Permietros Abdominales sin enviar
	 *
	 * @return lista con Permietros Abdominales
	 */
	public List<PerimetroAbdominal> getListaPerimetrosAbdominalesEnviar() throws SQLException {
		Cursor cPabdominal = null;
		List<PerimetroAbdominal> mPabdominal = new ArrayList<PerimetroAbdominal>();
		cPabdominal = mDb.query(true, ConstantsDB.PERIMETRO_ABDOMINAL_TABLE, null,
				ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
		if (cPabdominal != null && cPabdominal.getCount() > 0) {
			cPabdominal.moveToFirst();
			mPabdominal.clear();
			do{
				mPabdominal.add(crearPermietroAbdominal(cPabdominal));
			} while (cPabdominal.moveToNext());
		}
		cPabdominal.close();
		return mPabdominal;
	}

	/**
	 * Obtiene Lista todas Permietros Abdominales de un codigo
	 *
	 * @return lista
	 */
	public ArrayList<PerimetroAbdominal> getListaPerimetrosAbdominales(Integer codigo) throws SQLException {
		Cursor cPabdominal = null;
		ArrayList<PerimetroAbdominal> mPabdominal = new ArrayList<PerimetroAbdominal>();
		cPabdominal = mDb.query(true, ConstantsDB.PERIMETRO_ABDOMINAL_TABLE, null,
				ConstantsDB.CODIGO + "=" + codigo, null, null, null, null, null);
		if (cPabdominal != null && cPabdominal.getCount() > 0) {
			cPabdominal.moveToFirst();
			mPabdominal.clear();
			do{
				mPabdominal.add(crearPermietroAbdominal(cPabdominal));
			} while (cPabdominal.moveToNext());
		}
		cPabdominal.close();
		return mPabdominal;
	}

	public ArrayList<PerimetroAbdominal> getListaPerimetrosAbdominales() throws SQLException {
		Cursor cPabdominal = null;
		ArrayList<PerimetroAbdominal> mPabdominal = new ArrayList<PerimetroAbdominal>();
		cPabdominal = mDb.query(true, ConstantsDB.PERIMETRO_ABDOMINAL_TABLE, null,
				null, null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
		if (cPabdominal != null && cPabdominal.getCount() > 0) {
			cPabdominal.moveToFirst();
			mPabdominal.clear();
			do{
				mPabdominal.add(crearPermietroAbdominal(cPabdominal));
			} while (cPabdominal.moveToNext());
		}
		cPabdominal.close();
		return mPabdominal;
	}

	public ArrayList<PerimetroAbdominal> getListaPerimetrosAbdominalesHoy() throws SQLException {
		Cursor cPabdominal = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateWithoutTime = null;
		try {
			dateWithoutTime = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timestamp timeStamp = new Timestamp(dateWithoutTime.getTime());
		ArrayList<PerimetroAbdominal> mPabdominal = new ArrayList<PerimetroAbdominal>();
		cPabdominal = mDb.query(true, ConstantsDB.PERIMETRO_ABDOMINAL_TABLE, null,
				ConstantsDB.TODAY + "=" + timeStamp.getTime(), null, null, null, ConstantsDB.CODIGO + " , " +ConstantsDB.TODAY, null);
		if (cPabdominal != null && cPabdominal.getCount() > 0) {
			cPabdominal.moveToFirst();
			mPabdominal.clear();
			do{
				mPabdominal.add(crearPermietroAbdominal(cPabdominal));
			} while (cPabdominal.moveToNext());
		}
		cPabdominal.close();
		return mPabdominal;
	}


	/**
	 * Crea un Perimetro Abdominal
	 *
	 * @return PerimetroAbdomunal
	 */
	public PerimetroAbdominal crearPermietroAbdominal(Cursor perimetroAbdominal) {
		PerimetroAbdominal mPabdominal = new PerimetroAbdominal();
		Date fecha = new Date(perimetroAbdominal.getLong(perimetroAbdominal.getColumnIndex(ConstantsDB.TODAY)));
		PerimetroAbdominalId perimetroAbdominalId = new PerimetroAbdominalId();
		perimetroAbdominalId.setCodigo(perimetroAbdominal.getInt(perimetroAbdominal.getColumnIndex(ConstantsDB.CODIGO)));
		perimetroAbdominalId.setFecha(new Date(perimetroAbdominal.getLong(perimetroAbdominal.getColumnIndex(ConstantsDB.FECHA))));

		mPabdominal.setPaId(perimetroAbdominalId);

		if(!perimetroAbdominal.isNull(perimetroAbdominal.getColumnIndex(ConstantsDB.PABDOMINAL1))) mPabdominal.setPabdominal1(perimetroAbdominal.getDouble(perimetroAbdominal.getColumnIndex(ConstantsDB.PABDOMINAL1)));
		if(!perimetroAbdominal.isNull(perimetroAbdominal.getColumnIndex(ConstantsDB.PABDOMINAL2))) mPabdominal.setPabdominal2(perimetroAbdominal.getDouble(perimetroAbdominal.getColumnIndex(ConstantsDB.PABDOMINAL2)));
		if(!perimetroAbdominal.isNull(perimetroAbdominal.getColumnIndex(ConstantsDB.PABDOMINAL3))) mPabdominal.setPabdominal3(perimetroAbdominal.getDouble(perimetroAbdominal.getColumnIndex(ConstantsDB.PABDOMINAL3)));
		if(!perimetroAbdominal.isNull(perimetroAbdominal.getColumnIndex(ConstantsDB.DIFPABDOMINAL))) mPabdominal.setDifpabdominal(perimetroAbdominal.getDouble(perimetroAbdominal.getColumnIndex(ConstantsDB.DIFPABDOMINAL)));
		mPabdominal.setTomoMedidaSn(perimetroAbdominal.getString(perimetroAbdominal.getColumnIndex(ConstantsDB.tomoMedidaSn)));
		mPabdominal.setRazonNoTomoMedidas(perimetroAbdominal.getString(perimetroAbdominal.getColumnIndex(ConstantsDB.razonNoTomoMedidas)));
		if(!perimetroAbdominal.isNull(perimetroAbdominal.getColumnIndex(ConstantsDB.otrorecurso1))) mPabdominal.setOtrorecurso1(perimetroAbdominal.getInt(perimetroAbdominal.getColumnIndex(ConstantsDB.otrorecurso1)));
		if(!perimetroAbdominal.isNull(perimetroAbdominal.getColumnIndex(ConstantsDB.otrorecurso2))) mPabdominal.setOtrorecurso2(perimetroAbdominal.getInt(perimetroAbdominal.getColumnIndex(ConstantsDB.otrorecurso2)));
		mPabdominal.setEstudiosAct(perimetroAbdominal.getString(perimetroAbdominal.getColumnIndex(ConstantsDB.estudiosAct)));//MA2020

		Boolean borrado = perimetroAbdominal.getInt(perimetroAbdominal.getColumnIndex(ConstantsDB.DELETED))>0;
		mPabdominal.setMovilInfo(new MovilInfo(perimetroAbdominal.getInt(perimetroAbdominal.getColumnIndex(ConstantsDB.ID_INSTANCIA)),
				perimetroAbdominal.getString(perimetroAbdominal.getColumnIndex(ConstantsDB.FILE_PATH)),
				perimetroAbdominal.getString(perimetroAbdominal.getColumnIndex(ConstantsDB.STATUS)),
				perimetroAbdominal.getString(perimetroAbdominal.getColumnIndex(ConstantsDB.WHEN_UPDATED)),
				perimetroAbdominal.getString(perimetroAbdominal.getColumnIndex(ConstantsDB.START)),
				perimetroAbdominal.getString(perimetroAbdominal.getColumnIndex(ConstantsDB.END)),
				perimetroAbdominal.getString(perimetroAbdominal.getColumnIndex(ConstantsDB.DEVICE_ID)),
				perimetroAbdominal.getString(perimetroAbdominal.getColumnIndex(ConstantsDB.SIM_SERIAL)),
				perimetroAbdominal.getString(perimetroAbdominal.getColumnIndex(ConstantsDB.PHONE_NUMBER)),
				fecha,
				perimetroAbdominal.getString(perimetroAbdominal.getColumnIndex(ConstantsDB.USUARIO)),
				borrado,
				perimetroAbdominal.getInt(perimetroAbdominal.getColumnIndex(ConstantsDB.REC1)),
				perimetroAbdominal.getInt(perimetroAbdominal.getColumnIndex(ConstantsDB.REC2))));
		return mPabdominal;
	}

	/**
	 * Actualiza la base de datos.
	 *
	 *
	 */
	public boolean updatePermietroAbdominal(PerimetroAbdominal perimetroAbdominal) {
		ContentValues cv = new ContentValues();
		cv.put(ConstantsDB.STATUS, perimetroAbdominal.getMovilInfo().getEstado());
		return mDb.update(ConstantsDB.PERIMETRO_ABDOMINAL_TABLE, cv,
				ConstantsDB.CODIGO + "=" + perimetroAbdominal.getPaId().getCodigo() + " and " +
						ConstantsDB.FECHA + "=" + perimetroAbdominal.getPaId().getFecha().getTime(), null) > 0;
	}

	/**
	 * Obtiene Lista todos los Perimetros Abdominales sin enviar
	 *
	 * @return lista con PerimetroAbdominal
	 */
	public List<PerimetroAbdominal> getListaPerimetroAbdominalSinEnviar() throws SQLException {
		Cursor pAbdominal = null;
		List<PerimetroAbdominal> mPabdominal = new ArrayList<PerimetroAbdominal>();
		pAbdominal = mDb.query(true, ConstantsDB.PERIMETRO_ABDOMINAL_TABLE, null,
				ConstantsDB.STATUS + "= '" + Constants.STATUS_NOT_SUBMITTED+ "'", null, null, null, null, null);
		if (pAbdominal != null && pAbdominal.getCount() > 0) {
			pAbdominal.moveToFirst();
			mPabdominal.clear();
			do{
				mPabdominal.add(crearPermietroAbdominal(pAbdominal));
			} while (pAbdominal.moveToNext());
		}
		pAbdominal.close();
		return mPabdominal;
	}
	/********************************************************/
	/********************************************************/
	/********************************************************/
	/********************************************************/


	/*****FIN ENTOMOLOGIA****/
	public boolean bulkInsertMessageResourceBySql(List<MessageResource> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		//SQLiteDatabase db = null;
		try {
			//db = openHelper.getWritableDatabase();
			SQLiteStatement stat = mDb.compileStatement(CatalogosDBConstants.INSERT_MESSAGES_TABLE);
			mDb.beginTransaction();
			for (MessageResource remoteAppInfo : list) {
				MessageResourceHelper.fillMessageResourceStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertUsuariosBySql(List<UserSistema> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		//SQLiteDatabase db = null;
		try {
			//db = openHelper.getWritableDatabase();
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_USER_TABLE);
			mDb.beginTransaction();
			for (UserSistema remoteAppInfo : list) {
				UserSistemaHelper.fillUserSistemaStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertRolesBySql(List<Authority> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_ROLE_TABLE);
			mDb.beginTransaction();
			for (Authority remoteAppInfo : list) {
				UserSistemaHelper.fillRoleStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertUserPermissionsBySql(List<UserPermissions> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(ConstantsDB.INSERT_USER_PERM_TABLE);
			mDb.beginTransaction();
			for (UserPermissions remoteAppInfo : list) {
				UserSistemaHelper.fillUserPermissionStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertEstutiosBySql(List<Estudio> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(CatalogosDBConstants.INSERT_ESTUDIO_TABLE);
			mDb.beginTransaction();
			for (Estudio remoteAppInfo : list) {
				EstudiosHelper.fillEstudioStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertBarriosBySql(List<Barrio> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(CatalogosDBConstants.INSERT_BARRIO_TABLE);
			mDb.beginTransaction();
			for (Barrio remoteAppInfo : list) {
				BarrioHelper.fillBarrioStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertCasasBySql(List<Casa> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_CASA_TABLE);
			mDb.beginTransaction();
			for (Casa remoteAppInfo : list) {
				CasaHelper.fillCasaStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertParticipantesBySql(List<Participante> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_PARTICIPANTE_TABLE);
			mDb.beginTransaction();
			for (Participante remoteAppInfo : list) {
				ParticipanteHelper.fillParticipanteStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertParticipantesProcBySql(List<ParticipanteProcesos> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(ConstantsDB.INSERT_PARTPROC_TABLE);
			mDb.beginTransaction();
			for (ParticipanteProcesos remoteAppInfo : list) {
				ParticipanteHelper.fillParticipanteProcesosStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertCasasChfBySql(List<CasaCohorteFamilia> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_CASA_CHF_TABLE);
			mDb.beginTransaction();
			for (CasaCohorteFamilia remoteAppInfo : list) {
				CasaCohorteFamiliaHelper.fillCasaCohorteFamiliaStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertParticipantesChfBySql(List<ParticipanteCohorteFamilia> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_PARTICIPANTE_CHF_TABLE);
			mDb.beginTransaction();
			for (ParticipanteCohorteFamilia remoteAppInfo : list) {
				ParticipanteCohorteFamiliaHelper.fillParticipanteCohorteFamiliaStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertParticipantesSABySql(List<ParticipanteSeroprevalencia> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(SeroprevalenciaDBConstants.INSERT_PARTICIPANTESA_TABLE);
			mDb.beginTransaction();
			for (ParticipanteSeroprevalencia remoteAppInfo : list) {
				ParticipanteSeroprevalenciaHelper.fillParticipanteSeroprevalenciaStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertContactoParticipantesBySql(List<ContactoParticipante> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_CONTACTO_PARTICIPANTE_TABLE);
			mDb.beginTransaction();
			for (ContactoParticipante remoteAppInfo : list) {
				ParticipanteHelper.fillContactoParticipanteStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertCocinasBySql(List<Cocina> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_AREA_AMBIENTE_TABLE);
			mDb.beginTransaction();
			for (AreaAmbiente remoteAppInfo : list) {
				AreaAmbienteHelper.fillAreaAmbienteStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertComedoresBySql(List<Comedor> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_AREA_AMBIENTE_TABLE);
			mDb.beginTransaction();
			for (AreaAmbiente remoteAppInfo : list) {
				AreaAmbienteHelper.fillAreaAmbienteStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertSalasBySql(List<Sala> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_AREA_AMBIENTE_TABLE);
			mDb.beginTransaction();
			for (AreaAmbiente remoteAppInfo : list) {
				AreaAmbienteHelper.fillAreaAmbienteStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertHabitacionesBySql(List<Habitacion> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_AREA_AMBIENTE_TABLE);
			mDb.beginTransaction();
			for (AreaAmbiente remoteAppInfo : list) {
				AreaAmbienteHelper.fillAreaAmbienteStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertBaniosBySql(List<Banio> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_AREA_AMBIENTE_TABLE);
			mDb.beginTransaction();
			for (Banio remoteAppInfo : list) {
				AreaAmbienteHelper.fillBanioStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertVentanasBySql(List<Ventana> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_AREA_AMBIENTE_TABLE);
			mDb.beginTransaction();
			for (Ventana remoteAppInfo : list) {
				AreaAmbienteHelper.fillVentanaStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertCuartosBySql(List<Cuarto> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_CUARTO_TABLE);
			mDb.beginTransaction();
			for (Cuarto remoteAppInfo : list) {
				CuartoHelper.fillCuartoStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertCamasBySql(List<Cama> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_CAMA_TABLE);
			mDb.beginTransaction();
			for (Cama remoteAppInfo : list) {
				CamasHelper.fillCamaStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertPersonasCamasBySql(List<PersonaCama> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_PERSONACAMA_TABLE);
			mDb.beginTransaction();
			for (PersonaCama remoteAppInfo : list) {
				CamasHelper.fillPersonaCamaStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertMuestrasChfBySql(List<Muestra> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MuestrasDBConstants.INSERT_MUESTRA_TABLE);
			mDb.beginTransaction();
			for (Muestra remoteAppInfo : list) {
				MuestraHelper.fillMuestraChfStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertTelefonosContactoBySql(List<TelefonoContacto> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(MainDBConstants.INSERT_TELEFONO_CONTACTO_TABLE);
			mDb.beginTransaction();
			for (TelefonoContacto remoteAppInfo : list) {
				TelefonoContactoHelper.fillTelefContactoStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertCasosChfBySql(String tabla, List<?> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			mDb.beginTransaction();
			SQLiteStatement stat = null;
			switch (tabla) {
				case CasosDBConstants.CASAS_CASOS_TABLE:
					stat = mDb.compileStatement(CasosDBConstants.INSERT_CASAS_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						CasaCohorteFamiliaCasoHelper.fillCasaCohorteFamiliaCasoStatement(stat, (CasaCohorteFamiliaCaso)remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case CasosDBConstants.PARTICIPANTES_CASOS_TABLE:
					stat = mDb.compileStatement(CasosDBConstants.INSERT_PARTICIPANTES_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						ParticipanteCohorteFamiliaCasoHelper.fillParticipanteCohorteFamiliaCasoStatement(stat, (ParticipanteCohorteFamiliaCaso)remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case CasosDBConstants.VISITAS_CASOS_TABLE:
					stat = mDb.compileStatement(CasosDBConstants.INSERT_VISITAS_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						VisitaSeguimientoCasoHelper.fillVisitaSeguimientoCasoStatement(stat, (VisitaSeguimientoCaso)remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case CasosDBConstants.VISITAS_FALLIDAS_CASOS_TABLE:
					stat = mDb.compileStatement(CasosDBConstants.INSERT_VISITAS_FALLIDAS_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						VisitaFallidaCasoHelper.fillVisitaFallidaCasoStatement(stat, (VisitaFallidaCaso)remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case CasosDBConstants.SINTOMAS_CASOS_TABLE:
					stat = mDb.compileStatement(CasosDBConstants.INSERT_SINTOMAS_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						VisitaSeguimientoCasoSintomasHelper.fillVisitaSeguimientoCasoSintomasStatement(stat, (VisitaSeguimientoCasoSintomas)remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case CasosDBConstants.SENSORES_CASOS_TABLE:
					stat = mDb.compileStatement(CasosDBConstants.INSERT_SENSORES_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						SensoresCasoHelper.fillSensorCasoStatement(stat, (SensorCaso)remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case CasosDBConstants.VISITAS_FINALES_CASOS_TABLE:
					stat = mDb.compileStatement(CasosDBConstants.INSERT_VISITAS_FINALES_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						VisitaFinalCasoHelper.fillVisitaFinalCasoStatement(stat, (VisitaFinalCaso) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case CasosDBConstants.CONTACTOS_CASOS_TABLE:
					stat = mDb.compileStatement(CasosDBConstants.INSERT_CONTACTOS_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						FormularioContactoCasoHelper.fillFormularioContactoCasoStatement(stat, (FormularioContactoCaso) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case MainDBConstants.OBSEQUIOS_TABLE:
					stat = mDb.compileStatement(MainDBConstants.INSERT_OBSEQUIOS_TABLE);
					for (Object remoteAppInfo : list) {
						ObsequioHelper.fillObsequioStatement(stat, (ObsequioGeneral) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case MuestrasDBConstants.MUESTRA_SUPERFICIE_TABLE:
					stat = mDb.compileStatement(MuestrasDBConstants.INSERT_MUESTRA_SUPERFICIE_TABLE);
					for (Object remoteAppInfo : list) {
						MuestraHelper.fillMuestraSuperficieStatement(stat, (MuestraSuperficie) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				default:
					break;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertU01BySql(String tabla, List<?> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			mDb.beginTransaction();
			SQLiteStatement stat = null;
			switch (tabla) {
				case InfluenzaUO1DBConstants.UO1_PARTICIPANTES_CASOS_TABLE:
					stat = mDb.compileStatement(InfluenzaUO1DBConstants.INSERT_UO1_PARTICIPANTES_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						ParticipanteCasoUO1Helper.fillParticipanteCasoUO1Statement(stat, (ParticipanteCasoUO1) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case InfluenzaUO1DBConstants.UO1_VISITAS_CASOS_TABLE:
					stat = mDb.compileStatement(InfluenzaUO1DBConstants.INSERT_UO1_VISITAS_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						VisitaCasoUO1Helper.fillVisitaCasoUO1Statement(stat, (VisitaCasoUO1) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case InfluenzaUO1DBConstants.UO1_VISITAS_VACUNAS_TABLE:
					stat = mDb.compileStatement(InfluenzaUO1DBConstants.INSERT_UO1_VISITAS_VACUNAS_TABLE);
					for (Object remoteAppInfo : list) {
						VisitaVacunaUO1Helper.fillVisitaVacunaUO1ContentValues(stat, (VisitaVacunaUO1) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case InfluenzaUO1DBConstants.UO1_SINTOMAS_VISITA_CASO_TABLE:
					stat = mDb.compileStatement(InfluenzaUO1DBConstants.INSERT_UO1_SINTOMAS_VISITA_CASO_TABLE);
					for (Object remoteAppInfo : list) {
						SintomasVisitaCasoUO1Helper.fillSintomasVisitaCasoUO1Statement(stat, (SintomasVisitaCasoUO1) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				default:
					break;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertCovidBySql(String tabla, List<?> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			mDb.beginTransaction();
			SQLiteStatement stat = null;
			switch (tabla) {
				case Covid19DBConstants.PARTICIPANTE_COVID_TABLE:
					stat = mDb.compileStatement(Covid19DBConstants.INSERT_PARTICIPANTE_COVID_TABLE);
					for (Object remoteAppInfo : list) {
						ParticipanteCovid19Helper.fillParticipanteCovid19Statement(stat, (ParticipanteCovid19) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case Covid19DBConstants.COVID_CASOS_TABLE:
					stat = mDb.compileStatement(Covid19DBConstants.INSERT_COVID_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						CasoCovid19Helper.fillCasoCovid19Statement(stat, (CasoCovid19) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case Covid19DBConstants.COVID_PARTICIPANTES_CASOS_TABLE:
					stat = mDb.compileStatement(Covid19DBConstants.INSERT_COVID_PARTICIPANTES_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						ParticipanteCasoCovid19Helper.fillParticipanteCasoCovid19Statement(stat, (ParticipanteCasoCovid19) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case Covid19DBConstants.COVID_VISITAS_CASOS_TABLE:
					stat = mDb.compileStatement(Covid19DBConstants.INSERT_COVID_VISITAS_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						VisitaSeguimientoCasoCovid19Helper.fillVisitaSeguimientoCasoCovid19Statement(stat, (VisitaSeguimientoCasoCovid19) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case Covid19DBConstants.COVID_SINTOMAS_VISITA_CASO_TABLE:
					stat = mDb.compileStatement(Covid19DBConstants.INSERT_COVID_SINTOMAS_VISITA_CASO_TABLE);
					for (Object remoteAppInfo : list) {
						SintomasVisitaCasoCovid19Helper.fillSintomasVisitaCasoCovid19Statement(stat, (SintomasVisitaCasoCovid19) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case Covid19DBConstants.COVID_VISITAS_FALLIDAS_CASOS_TABLE:
					stat = mDb.compileStatement(Covid19DBConstants.INSERT_COVID_VISITAS_FALLIDAS_CASOS_TABLE);
					for (Object remoteAppInfo : list) {
						VisitaFallidaCasoCovid19Helper.fillVisitaFallidaCasoCovid19Statement(stat, (VisitaFallidaCasoCovid19) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case Covid19DBConstants.COVID_CANDIDATO_TRANSMISION_TABLE:
					stat = mDb.compileStatement(Covid19DBConstants.INSERT_COVID_CANDIDATO_TRANSMISION_TABLE);
					for (Object remoteAppInfo : list) {
						CandidatoTransmisionCovid19Helper.fillCandidatoTransmisionCovid19Statement(stat, (CandidatoTransmisionCovid19) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case Covid19DBConstants.COVID_DATOS_AISLAMIENTO_VC_TABLE:
					stat = mDb.compileStatement(Covid19DBConstants.INSERT_COVID_DATOS_AISLAMIENTO_VC_TABLE);
					for (Object remoteAppInfo : list) {
						DatosAislamientoVisitaCasoCovid19Helper.fillDatosAislamientoVisitaCasoCovid19Statement(stat, (DatosAislamientoVisitaCasoCovid19) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case Covid19DBConstants.COVID_VISITA_FINAL_CASO_TABLE:
					stat = mDb.compileStatement(Covid19DBConstants.INSERT_COVID_VISITA_FINAL_CASO_TABLE);
					for (Object remoteAppInfo : list) {
						VisitaFinalCasoCovid19Helper.fillVisitaFinalCasoCovid19Statement(stat, (VisitaFinalCasoCovid19) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case Covid19DBConstants.COVID_SINT_VISITA_FINAL_CASO_TABLE:
					stat = mDb.compileStatement(Covid19DBConstants.INSERT_COVID_SINT_VISITA_FINAL_CASO_TABLE);
					for (Object remoteAppInfo : list) {
						SintomasVisitaFinalCovid19Helper.fillSintomasVisitaFinalCovid19Statement(stat, (SintomasVisitaFinalCovid19) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				case Covid19DBConstants.COVID_OTROS_POSITIVOS_TABLE:
					stat = mDb.compileStatement(Covid19DBConstants.INSERT_CCOVID_OTROS_POSITIVOS_TABLE);
					for (Object remoteAppInfo : list) {
						CandidatoTransmisionCovid19Helper.fillOtrosPositivosCovidStatement(stat, (OtrosPositivosCovid) remoteAppInfo);
						long result = stat.executeInsert();
						if (result < 0) return false;
					}
					break;
				default:
					break;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertMuestrasMABySql(List<ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(ConstantsDB.INSERT_MUESTRA_TABLE);
			mDb.beginTransaction();
			for (ni.org.ics.estudios.appmovil.domain.muestreoanual.Muestra remoteAppInfo : list) {
				MuestraHelper.fillMuestraMAStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean bulkInsertCuestionarioHogarBySql(List<CuestionarioHogar> list) throws Exception {
		if (null == list || list.size() <= 0) {
			return false;
		}
		try {
			SQLiteStatement stat = mDb.compileStatement(EntomologiaBConstants.INSERT_ENTO_CUESTIONARIO_HOGAR_TABLE);
			mDb.beginTransaction();
			for (CuestionarioHogar remoteAppInfo : list) {
				EntomologiaHelper.fillCuestionarioHogarStatement(stat, remoteAppInfo);
				long result = stat.executeInsert();
				if (result < 0) return false;
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (null != mDb) {
					mDb.endTransaction();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
