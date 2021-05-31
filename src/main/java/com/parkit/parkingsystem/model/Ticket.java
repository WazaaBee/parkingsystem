package com.parkit.parkingsystem.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private BigDecimal price = new BigDecimal("0"); // Changement type variable double en BigDecimal ainsi que le setter and getter arrondis & 2 chiffres après la virgule
    private Date inTime;
    private Date outTime;
    private boolean userExists; // Création d'un variable type boolean pour fonction 5% de reduction

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public BigDecimal getPrice() {
        return price.setScale(2, RoundingMode.HALF_UP);
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }

    public Date getInTime() {
        Date inTimeRepresentation = inTime;
    	return inTimeRepresentation;
    }

    public void setInTime(Date inTime) {
    	this.inTime = new Date(inTime.getTime());
    }

    public Date getOutTime() {
    	Date outTimeRepresentation = outTime;
        return outTimeRepresentation;
    }

    public void setOutTime(Date outTime) {
        if (outTime != null) {
        	this.outTime = new Date(outTime.getTime());
        }
    }
    
    public boolean getUserExists() {
    	return userExists;
    }
    
    public void setUserExists(boolean userExists) {
    	this.userExists = userExists;
    }
}
