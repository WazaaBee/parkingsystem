package com.parkit.parkingsystem.constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Fare {
    //Changement type de variable double vers BigDecimal
	public static final BigDecimal BIKE_RATE_PER_HOUR = new BigDecimal("1").setScale(2, RoundingMode.HALF_UP);
    public static final BigDecimal CAR_RATE_PER_HOUR = new BigDecimal("1.5").setScale(2, RoundingMode.HALF_UP);
    //Création constante rédduction utilisateur récurent
    public static final BigDecimal DISOUNT_RECURENT_USER = new BigDecimal("0.95").setScale(2, RoundingMode.HALF_UP);
}
