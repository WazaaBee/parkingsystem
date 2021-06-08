package com.parkit.parkingsystem.service;

import java.math.BigDecimal;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }
        BigDecimal discount = new BigDecimal(1);
        long inHour = ticket.getInTime().getTime();
        long outHour = ticket.getOutTime().getTime();
        BigDecimal duration = new BigDecimal ((outHour - inHour)/1000.00/60.00/60.00);
        if (duration.doubleValue() < 0.5) {
        	ticket.setPrice(new BigDecimal(0));
        }
        else {        
        	//Condition utilisateur récurent
        	if(ticket.getUserExists() == true) {
        		discount = Fare.DISOUNT_RECURENT_USER;
        		System.out.println("Price is set with 5% discount");
        	}
        	switch (ticket.getParkingSpot().getParkingType()){
            	case CAR: {
            		ticket.setPrice(duration.multiply(Fare.CAR_RATE_PER_HOUR).multiply(discount));
            		break;
            	}
            	case BIKE: {
            		ticket.setPrice(duration.multiply(Fare.BIKE_RATE_PER_HOUR).multiply(discount));
            		break;
            	}
            	default: throw new IllegalArgumentException("Unkown Parking Type");
        	}
        }
    }
}