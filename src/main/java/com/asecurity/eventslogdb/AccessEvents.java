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
        hqlBuilder.append("WHERE AEC.eventtime >= CURRENT_DATE\n");
        hqlBuilder.append("AND AEC.sourceid IN ('").append(sourceids).append("')\n");
        hqlBuilder.append("ORDER BY AEC.eventtime DESC");
        String hql = hqlBuilder.toString();
        return getApacseventsCha(hql, maxResult, false);
    }

    /**
     *
     * @return List<ApacseventsCha> - return faults events from EventsLog.ApacsEvents_CHA
     */
    @WebMethod(operationName = "getAccessFaults")
    public List<ApacseventsCha> getAccessFaults () {
        String hql = "FROM ApacseventsCha AEC\n" +
                     "WHERE AEC.eventtime >= CURRENT_DATE\n" +
                           "AND AEC.eventtype NOT LIKE 'TApcCardHolderAccess_Granted%'\n" +
                     "ORDER BY AEC.eventtime DESC";
        int ulimitedResult = -1;
        return getApacseventsCha(hql, ulimitedResult, false);
    }
    
    /**
     *
     * @param ExitIDs
     * @return List<CardHolderPosition> - return last sourceid per holders from EventsLog.ApacsEvents_CHA
     */
    @WebMethod(operationName = "getCHsPositions")
    public List<CardHolderPosition> getCHsPositions (@WebParam(name = "ExitIDs") String ExitIDs) {
        ExitIDs = ExitIDs.replace(",", "', '");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT aec2.*\n");
        sqlBuilder.append("FROM apacsevents_cha_maxeventid_view AS aec1\n");
        sqlBuilder.append("INNER JOIN Apacsevents_cha AS aec2 ");
        sqlBuilder.append("ON aec1.holderid = aec2.holderid AND aec1.maxeventid = aec2.eventid\n");
        sqlBuilder.append("WHERE aec2.sourceid NOT IN ('").append(ExitIDs).append("')\n");
        sqlBuilder.append("ORDER BY aec2.holdershortname, aec2.sourceid;");
        String sql = sqlBuilder.toString();
        int ulimitedResult = -1;
        List<ApacseventsCha> CHsLastEvent = getApacseventsCha(sql, ulimitedResult, true);
        List<CardHolderPosition> result = new ArrayList<>();
        for (ApacseventsCha aec : CHsLastEvent)
        {
            CardHolderPosition chp = new CardHolderPosition(aec.getHoldername());
            chp.setSourceid(aec.getSourceid());
            chp.setHoldercompany(aec.getHoldercompany());
            chp.setHolderoccup(aec.getHolderjobtitle());
            chp.setHoldercategory(aec.getHoldercategory());
            result.add(chp);
        }
        return result;
    }
    
    private List<ApacseventsCha> getApacseventsCha (String hql, int resCnt, boolean useNativeSQL){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<ApacseventsCha> aeCha = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query q;
            if (useNativeSQL) {
                q = session.createSQLQuery(hql);
                q.setResultTransformer(Transformers.aliasToBean(ApacseventsCha.class));
            }
            else {
                q = session.createQuery(hql);
            }
            q.setMaxResults(resCnt);
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
