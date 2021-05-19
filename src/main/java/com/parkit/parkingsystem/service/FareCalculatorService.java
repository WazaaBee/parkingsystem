package com.parkit.parkingsystem.service;

import java.math.BigDecimal;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        long inHour = ticket.getInTime().getTime();
        long outHour = ticket.getOutTime().getTime();

        //TODO: Some tests are failing here. Need to check if this logic is correct
        //Changement du type des variables de int a long et double pour la durée
        //Addition d'un BigDecimal pour le prix
        //Erreur corriggée en convertissant le temps en heure et avec l'utilisation d'un nombre décimal
        BigDecimal duration = new BigDecimal ((outHour - inHour)/1000.00/60.00/60.00);

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration.multiply(Fare.CAR_RATE_PER_HOUR));
                break;
            }
            case BIKE: {
                ticket.setPrice(duration.multiply(Fare.BIKE_RATE_PER_HOUR));
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}