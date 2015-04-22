/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asecurity.esbdb;

import java.util.ArrayList;
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
     * @return List - return last events from EventsLog.ApacsEvents_CHA by SourceID
     */
    @WebMethod(operationName = "getAccessEvents")
    public Object[][] getAccessEvents(@WebParam(name = "SourceIDs") String sourceids, @WebParam(name = "MaxResult") int maxResult){
        if (sourceids == null){
            return null;
        }
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT AEC.holderid, AEC.eventtypedesc, AEC.eventtime\n");
        sqlBuilder.append("FROM ApacseventsCha AEC\n"); 
        sqlBuilder.append("WHERE ");
        String[] srcs = sourceids.split(",");
        sqlBuilder.append("(AEC.sourceid = '").append(srcs[0]).append("')\n");
        for (int i = 1; i < srcs.length; i++) {
            sqlBuilder.append("OR (AEC.sourceid = '").append(srcs[i]).append("')\n");
        }
        sqlBuilder.append("ORDER BY AEC.eventtime DESC");
        String sql = sqlBuilder.toString();
        return  getApacseventsCha(sql, maxResult);
    }

    /**
     *
     * @return List - return faults events from EventsLog.ApacsEvents_CHA
     */
    @WebMethod(operationName = "getAccessFaults")
    public Object[][] getAccessFaults () {
        String sql = "SELECT AEC.holderid, AEC.eventid, AEC.holdername, AEC.eventtypedesc, AEC.sourcename, AEC.eventtime\n" +
                     "FROM ApacseventsCha AEC\n" +
                     "WHERE to_date(AEC.eventtime, 'YYYY-MM-DD\"T\"HH24:MI:SS.MS') = CURRENT_DATE\n" +
                           "AND AEC.eventtype NOT LIKE 'TApcCardHolderAccess_Granted'\n" +
                     "ORDER BY AEC.holdername, AEC.eventid";
        int ulimitedResult = -1;
        return getApacseventsCha(sql, ulimitedResult);
    }
    
    private Object[][] getApacseventsCha (String sql, int resultCount){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Object[]> aeCha = new ArrayList<>();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery(sql).setMaxResults(resultCount);
            aeCha = (List<Object[]>)q.list();
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
        return aeCha.toArray(new Object[aeCha.size()][]);
    }
}
