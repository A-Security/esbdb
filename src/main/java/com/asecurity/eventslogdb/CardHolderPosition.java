package com.asecurity.eventslogdb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "CardHolderPosition")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardHolderPosition")
public class CardHolderPosition  implements java.io.Serializable {
    @XmlElement(name = "holdername")
    private String holdername;
    @XmlElement(name = "holdershortname")
    private String holdershortname;
    @XmlElement(name = "holdercompany")
    private String holdercompany;
    @XmlElement(name = "holderoccup")
    private String holderoccup;
    @XmlElement(name = "cardno")
    private String cardno;
    @XmlElement(name = "eventtime")
    private String eventtime;
    @XmlElement(name = "zones")
    private String zones;

    public CardHolderPosition() {
    }
    public CardHolderPosition(String holdername) {
        this.holdername = holdername;
    }
    
    public CardHolderPosition(String holdername, String holdershortname, String holdercompany, String holderoccup, String cardno, String eventtime, String zones) {
        this.holdername = holdername;
        this.holdershortname = holdershortname;
        this.holdercompany = holdercompany;
        this.holderoccup = holderoccup;
        this.cardno = cardno;
        this.eventtime = eventtime;
        this.zones = zones;
    }
    /**
     * @return the holdername
     */
    public String getHoldername() {
        return holdername;
    }

    /**
     * @return the holdershortname
     */
    public String getHoldershortname() {
        return holdershortname;
    }

    /**
     * @return the holdercompany
     */
    public String getHoldercompany() {
        return holdercompany;
    }

    /**
     * @return the holderoccup
     */
    public String getHolderoccup() {
        return holderoccup;
    }

    /**
     * @return the cardno
     */
    public String getCardno() {
        return cardno;
    }

    /**
     * @return the eventtime
     */
    public String getEventtime() {
        return eventtime;
    }

    /**
     * @return the zones
     */
    public String getZones() {
        return zones;
    }

    /**
     * @param holdername the holdername to set
     */
    public void setHoldername(String holdername) {
        this.holdername = holdername;
    }

    /**
     * @param holdershortname the holdershortname to set
     */
    public void setHoldershortname(String holdershortname) {
        this.holdershortname = holdershortname;
    }

    /**
     * @param holdercompany the holdercompany to set
     */
    public void setHoldercompany(String holdercompany) {
        this.holdercompany = holdercompany;
    }

    /**
     * @param holderoccup the holderoccup to set
     */
    public void setHolderoccup(String holderoccup) {
        this.holderoccup = holderoccup;
    }

    /**
     * @param cardno the cardno to set
     */
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    /**
     * @param eventtime the eventtime to set
     */
    public void setEventtime(String eventtime) {
        this.eventtime = eventtime;
    }

    /**
     * @param zones the zones to set
     */
    public void setZones(String zones) {
        this.zones = zones;
    }
}