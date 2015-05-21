/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecurity.eventslogdb;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.jws.WebResult;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

/**
 *
 * @author vudalov
 */
@WebService(serviceName = "AccessEvents")
@Stateless()
public class AccessEvents {
    /**
     *
     * @param sourceids - some SourceIDs delimet by ',' or single SourceID
     * @param maxResult
     * @return List<ApacseventsCha> - return last events from EventsLog.ApacsEvents_CHA by SourceID
     */
    @WebMethod(operationName = "getAccessEvents")
    @WebResult(name = "ApacseventsCha")
    public List<ApacseventsCha> getAccessEvents(@WebParam(name = "SourceIDs") String sourceids, @WebParam(name = "MaxResult") int maxResult){
        if (sourceids == null){
            return null;
        }
        sourceids = sourceids.replaceAll(",", "','");
        StringBuilder hqlBuilder = new StringBuilder();
        hqlBuilder.append("FROM ApacseventsCha AEC\n"); 
        hqlBuilder.append("WHERE to_date(AEC.eventtime, 'YYYY-MM-DD\"T\"HH24:MI:SS.MS') = CURRENT_DATE\n");
        hqlBuilder.append("AND AEC.sourceid IN ('").append(sourceids).append("')\n");
        hqlBuilder.append("ORDER BY AEC.eventtime DESC");
        String hql = hqlBuilder.toString();
        return (List<ApacseventsCha>)getApacseventsCha(hql, maxResult, false);
    }

    /**
     *
     * @return List<ApacseventsCha> - return faults events from EventsLog.ApacsEvents_CHA
     */
    @WebMethod(operationName = "getAccessFaults")
    public List<ApacseventsCha> getAccessFaults () {
        String hql = "FROM ApacseventsCha AEC\n" +
                     "WHERE to_date(AEC.eventtime, 'YYYY-MM-DD\"T\"HH24:MI:SS.MS') = CURRENT_DATE\n" +
                           "AND AEC.eventtype NOT LIKE 'TApcCardHolderAccess_Granted'\n" +
                     "ORDER BY AEC.holdername, AEC.eventid DESC";
        int ulimitedResult = -1;
        return (List<ApacseventsCha>)getApacseventsCha(hql, ulimitedResult, false);
    }
    
    /**
     *
     * @param exitsourceids
     * @return List<CardHolderPosition> - return last sourceid per holders from EventsLog.ApacsEvents_CHA
     */
    @WebMethod(operationName = "getCHsPositions")
    public List<CardHolderPosition> getCHsPositions (@WebParam(name = "ExitSourceIDs") String exitsourceids) {
        
        if (exitsourceids == null || exitsourceids.isEmpty()){
            exitsourceids = "0000111A,00001102,0000481C,0000493E,000000FC,00000114";
        }
        exitsourceids = exitsourceids.replaceAll(",", "','");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT aec2.*\n");
        sqlBuilder.append("FROM apacsevents_cha_maxeventid_view AS aec1\n");
        sqlBuilder.append("INNER JOIN Apacsevents_cha AS aec2 ");
        sqlBuilder.append("ON aec1.holderid = aec2.holderid AND aec1.maxeventid = aec2.eventid\n");
        sqlBuilder.append("WHERE sourceid NOT IN ('").append(exitsourceids).append("')\n");
        sqlBuilder.append("ORDER BY aec2.holdershortname, aec2.sourceid;");
        String sql = sqlBuilder.toString();
        int ulimitedResult = -1;
        List<ApacseventsCha> CHsLastEvent = getApacseventsCha(sql, ulimitedResult, true);
        List<CardHolderPosition> result = new ArrayList<>();
        final String spkInT1     = "00001174";
        final String spkInT2     = "0000118C";
        final String spkOutT1    = "00001180";
        final String spkOutT2    = "00001198";
        
        final String vdprVerhIn  = "000000F0";
        final String vdprVerhOut = "000000FC";
        final String vdprNizhIn  = "00000108";
        final String vdprNizhOut = "00000114";
        
        final String ckppInT1    = "000010F6";
        final String ckppInT2    = "0000110E";
        final String ckppOutT1   = "00001102";
        final String ckppOutT2   = "0000111A";
        final String ckppOutCB   = "0000481C";

        final String ckppAutoIn  = "00004932";
        final String ckppAutoOut = "0000493E";
        
        final String gsuIn       = "000011CD";
        final String gsuOut      = "000011D9";
        
        final String krueIn      = "0000599D";
        final String krueOut     = "000059A9";
        
        final String mzIn        = "000011E9";
        final String mzOut       = "000011F5";
        for (ApacseventsCha aec : CHsLastEvent)
        {
            CardHolderPosition chp = new CardHolderPosition(aec.getHoldername());
            String zones;
            switch (aec.getSourceid())
            {
                case ckppInT1: case ckppInT2: case ckppAutoIn: case spkOutT1: 
                    case spkOutT2: case mzOut: case krueOut: {
                        zones = "";
                        break;
                }
                case spkInT1: case spkInT2: case mzIn: case gsuOut: {
                    zones = "spk";
                    break;
                }
                case krueIn: {
                    zones = "krue";
                    break;
                }
                case gsuIn: {
                    zones = "gsu";
                    break;
                }
                case vdprVerhIn: case vdprNizhIn:{
                    zones = "vdpr";
                    break;
                }
                default: {
                    zones = "";
                    break;
                }
            }
            chp.setZones(zones);
            result.add(chp);
        }
        return result;
    }
    
    
    private List<ApacseventsCha> getApacseventsCha (String hql, int resultCount, boolean useNativeSQL){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<ApacseventsCha> aeCha = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query q = useNativeSQL ? session.createSQLQuery(hql) : session.createQuery(hql);
            q.setMaxResults(resultCount);
            q.setResultTransformer(Transformers.aliasToBean(ApacseventsCha.class));
            aeCha = (List<ApacseventsCha>)q.list();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (tx != null) {
                tx.rollback();
            }
            if (session.isOpen()) {
                session.close();
            }
        }
        return aeCha;
    }
}
