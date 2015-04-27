/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecurity.esbdb;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    public List<ApacseventsCha> getAccessEvents(@WebParam(name = "SourceIDs") String sourceids, @WebParam(name = "MaxResult") int maxResult){
        if (sourceids == null){
            return null;
        }
        StringBuilder hqlBuilder = new StringBuilder();
        hqlBuilder.append("FROM ApacseventsCha AEC\n"); 
        hqlBuilder.append("WHERE ");
        String[] srcs = sourceids.split(",");
        hqlBuilder.append("(AEC.sourceid = '").append(srcs[0]).append("')\n");
        for (int i = 1; i < srcs.length; i++) {
            hqlBuilder.append("OR (AEC.sourceid = '").append(srcs[i]).append("')\n");
        }
        hqlBuilder.append("ORDER BY AEC.eventtime DESC");
        String hql = hqlBuilder.toString();
        return  getApacseventsCha(hql, maxResult);
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
                     "ORDER BY AEC.holdername, AEC.eventid";
        int ulimitedResult = -1;
        return getApacseventsCha(hql, ulimitedResult);
    }
    
    private List<ApacseventsCha> getApacseventsCha (String hql, int resultCount){
        
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<ApacseventsCha> aeCha = null;
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            Query q = session.createQuery(hql).setMaxResults(resultCount);
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
