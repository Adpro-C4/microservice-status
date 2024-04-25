package id.ac.ui.cs.advprog.statustrackingorder.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Tracking {
    private Long trackingId;
    private Long orderId;
    private String methode;
    private String resiCode;

    public Long getTrackingId() {
        return trackingId;
    }

    public Tracking(Long trackingId, Long orderId, String methode, String resiCode) {
        this.trackingId = trackingId;
        this.orderId = orderId;
        this.methode = methode;
        this.resiCode = resiCode;
    }

    public Tracking(){

    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getMethode() {
        return methode;
    }

    public void setMethode(String methode) {
        this.methode = methode;
    }

    public String getResiCode() {
        return resiCode;
    }

    public void setResiCode(String resiCode) {
        this.resiCode = resiCode;
    }

    public void setTrackingId(Long trackingId) {
        this.trackingId = trackingId;
    }
}




