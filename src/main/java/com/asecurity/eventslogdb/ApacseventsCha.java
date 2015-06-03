package com.asecurity.eventslogdb;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ApacseventsCha")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApacseventsCha")
public class ApacseventsCha  implements java.io.Serializable {
     @XmlElement(name = "eventid")
     private String eventid;
     @XmlElement(name = "eventtype")
     private String eventtype;
     @XmlElement(name = "eventtypedesc")
     private String eventtypedesc;
     @XmlElement(name = "holderid")
     private String holderid;
     @XmlElement(name = "holdername")
     private String holdername;
     @XmlElement(name = "holdershortname")
     private String holdershortname;
     @XmlElement(name = "cardno")
     private String cardno;
     @XmlElement(name = "sourceid")
     private String sourceid;
     @XmlElement(name = "sourcename")
     private String sourcename;
     @XmlElement(name = "eventtime")
     private Date eventtime;
     @XmlElement(name = "messagexml")
     private String messagexml;
     @XmlElement(name = "messageid")
     private String messageid;
     @XmlElement(name = "messagetime")
     private Date messagetime;
     @XmlElement(name = "holdercompany")
     private String holdercompany;
     @XmlElement(name = "holderjobtitle")
     private String holderjobtitle;
     @XmlElement(name = "holdercategory")
     private String holdercategory;

    public ApacseventsCha() {
    }

	
    public ApacseventsCha(String eventid) {
        this.eventid = eventid;
    }
    public ApacseventsCha(String eventid, String eventtype, String eventtypedesc, String holderid, String holdername, String holdershortname, String cardno, String sourceid, String sourcename, Date eventtime, String messagexml, String messageid, Date messagetime, String holdercompany, String holderjobtitle, String holdercategory) {
       this.eventid = eventid;
       this.eventtype = eventtype;
       this.eventtypedesc = eventtypedesc;
       this.holderid = holderid;
       this.holdername = holdername;
       this.holdershortname = holdershortname;
       this.cardno = cardno;
       this.sourceid = sourceid;
       this.sourcename = sourcename;
       this.eventtime = eventtime;
       this.messagexml = messagexml;
       this.messageid = messageid;
       this.messagetime = messagetime;
       this.holdercompany = holdercompany;
       this.holderjobtitle = holderjobtitle;
       this.holdercategory = holdercategory;
    }
   
    public String getEventid() {
        return this.eventid;
    }
    
    public void setEventid(String eventid) {
        this.eventid = eventid;
    }
    public String getEventtype() {
        return this.eventtype;
    }
    
    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }
    public String getEventtypedesc() {
        return this.eventtypedesc;
    }
    
    public void setEventtypedesc(String eventtypedesc) {
        this.eventtypedesc = eventtypedesc;
    }
    public String getHolderid() {
        return this.holderid;
    }
    
    public void setHolderid(String holderid) {
        this.holderid = holderid;
    }
    public String getHoldername() {
        return this.holdername;
    }
    
    public void setHoldername(String holdername) {
        this.holdername = holdername;
    }
    public String getHoldershortname() {
        return this.holdershortname;
    }
    
    public void setHoldershortname(String holdershortname) {
        this.holdershortname = holdershortname;
    }
    public String getCardno() {
        return this.cardno;
    }
    
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }
    public String getSourceid() {
        return this.sourceid;
    }
    
    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }
    public String getSourcename() {
        return this.sourcename;
    }
    
    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }
    public Date getEventtime() {
        return this.eventtime;
    }
    
    public void setEventtime(Date eventtime) {
        this.eventtime = eventtime;
    }
    public String getMessagexml() {
        return this.messagexml;
    }
    
    public void setMessagexml(String messagexml) {
        this.messagexml = messagexml;
    }
    public String getMessageid() {
        return this.messageid;
    }
    
    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }
    public Date getMessagetime() {
        return this.messagetime;
    }
    
    public void setMessagetime(Date messagetime) {
        this.messagetime = messagetime;
    }
    public String getHoldercompany() {
        return this.holdercompany;
    }
    
    public void setHoldercompany(String holdercompany) {
        this.holdercompany = holdercompany;
    }
    public String getHolderjobtitle() {
        return this.holderjobtitle;
    }
    
    public void setHolderjobtitle(String holderjobtitle) {
        this.holderjobtitle = holderjobtitle;
    }
    public String getHoldercategory() {
        return this.holdercategory;
    }
    
    public void setHoldercategory(String holdercategory) {
        this.holdercategory = holdercategory;
    }




}


