package org.compiere.process;

import java.util.Calendar;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

import com.epa.agil.util.MonthlyHistory;



public class XXCNPlanificationMHProcess extends SvrProcess {


	private static final CLogger log = CLogger.getCLogger(XXCNPlanificationMHProcess.class);
	
	/**
	 * @author vcruz - Vanessa Cruz
	 * planificado que va a correr siempre en el agilroot para traerse 
	 * al inicio de cada mes todos los movimiento de cada producto en el MonthlyHistory
	 * */
	
	@Override
	protected String doIt() throws Exception {
		
		
		
		if(MonthlyHistory.IsUpdate(get_Trx(), getAD_Client_ID())) {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			
			if(month == 1) {
				month = 12;
			}else {
				month = month - 1;
			}
		
			Object[] params = {cal.get(Calendar.YEAR), month };
		
			StringBuilder sql = new StringBuilder("INSERT INTO XX_CN_MONTHLYHISTORY (AD_CLIENT_ID, AD_ORG_ID,CREATED, CREATEDBY, UPDATED, UPDATEDBY,ISACTIVE,");
			sql.append("M_PRODUCT_ID,XX_CN_INITIALINVLOGICALQTY, XX_CN_INIINVTRADINGQTY,XX_CN_NRQTY, XX_CN_SURPLUSARQTY, XX_CN_MISSINGARQTY,");
			sql.append("XX_CN_NTDISPATCHEDQTY, XX_CN_NTRECEIVEDQTY, XX_CN_IUQTY, XX_CN_RZQTY, XX_CN_SURPLUSAPIQTY, ");
			sql.append("XX_CN_MISSINGAPIQTY, XX_CN_NETSALESQTY, XX_CN_FINALINVTRADINGQTY, XX_CN_FINALINVLOGICALQTY,");
			sql.append("XX_CN_INITIALINVLOGICALCURRENCY, XX_CN_NRCURRENCY, XX_CN_SURPLUSARCURRENCY, XX_CN_MISSINGARCURRENCY, XX_CN_NTDISPATCHEDCURRENCY,");
			sql.append(" XX_CN_NTRECEIVEDCURRENCY, XX_CN_IUCURRENCY, XX_CN_RZCURRENCY, XX_CN_NETSALESCURRENCY, XX_CN_FINALINVLOGICALCURRENCY,"); 
			sql.append("XX_CN_PRICEINCREASES, XX_CN_PRICEOFF, PRICE, COSTAVERAGE, XX_CN_MONTHLYHISTORY_ID, XX_CN_YEAR, XX_CN_MONTH) ");	
			sql.append("SELECT AD_CLIENT_ID, AD_ORG_ID, CREATED, CREATEDBY, UPDATED, UPDATEDBY, ISACTIVE, M_PRODUCT_ID,");	
			sql.append("XX_CN_FINALINVLOGICALQTY, XX_CN_FINALINVTRADINGQTY, XX_CN_NRQTY - XX_CN_NRQTY, XX_CN_SURPLUSARQTY - XX_CN_SURPLUSARQTY, ");
			sql.append("XX_CN_MISSINGARQTY - XX_CN_MISSINGARQTY, XX_CN_NTDISPATCHEDQTY - XX_CN_NTDISPATCHEDQTY, XX_CN_NTRECEIVEDQTY - XX_CN_NTRECEIVEDQTY,");	
			sql.append(" XX_CN_IUQTY - XX_CN_IUQTY, XX_CN_RZQTY - XX_CN_RZQTY, XX_CN_SURPLUSAPIQTY - XX_CN_SURPLUSAPIQTY,");	
			sql.append("XX_CN_MISSINGAPIQTY - XX_CN_MISSINGAPIQTY, XX_CN_NETSALESQTY - XX_CN_NETSALESQTY, XX_CN_FINALINVTRADINGQTY - XX_CN_FINALINVTRADINGQTY," );	
			sql.append("XX_CN_FINALINVLOGICALQTY - XX_CN_FINALINVLOGICALQTY, XX_CN_FINALINVLOGICALCURRENCY, XX_CN_NRCURRENCY - XX_CN_NRCURRENCY, XX_CN_SURPLUSARCURRENCY - XX_CN_SURPLUSARCURRENCY," );	
			sql.append("XX_CN_MISSINGARCURRENCY - XX_CN_MISSINGARCURRENCY, XX_CN_NTDISPATCHEDCURRENCY - XX_CN_NTDISPATCHEDCURRENCY,");	  
			sql.append("XX_CN_NTRECEIVEDCURRENCY - XX_CN_NTRECEIVEDCURRENCY, XX_CN_IUCURRENCY - XX_CN_IUCURRENCY, XX_CN_RZCURRENCY - XX_CN_RZCURRENCY,");	
			sql.append("XX_CN_NETSALESCURRENCY - XX_CN_NETSALESCURRENCY , XX_CN_FINALINVLOGICALCURRENCY - XX_CN_FINALINVLOGICALCURRENCY ,");	
			sql.append("XX_CN_PRICEINCREASES - XX_CN_PRICEINCREASES, XX_CN_PRICEOFF - XX_CN_PRICEOFF,");
			sql.append("PRICE , COSTAVERAGE , XX_NextID(0,'XX_CN_MonthlyHistory'), XX_CN_YEAR ,");
				
				 
				if(month == 12) {
					sql.append("XX_CN_MONTH - 11 ");
				}else {
					sql.append("XX_CN_MONTH + 1 ");
				}	
				sql.append("FROM XX_CN_MONTHLYHISTORY WHERE XX_CN_YEAR = ? AND XX_CN_MONTH = ?");

				DB.executeUpdate(get_Trx(), sql.toString(), params);
				
				
		}else {
			log.info("No debe actualizar MonthlyHistory - "
				+ "La Columna XX_SI_UpdateMonthlyHistory en AD_CLIENTINFO no está Activada");
		}

		return null;
	}

	@Override
	protected void prepare() {
		

	}

}
