package com.parkit.parkingsystem.constants;

public class DBConstants {

    public static final String GET_NEXT_PARKING_SPOT = "select min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = ?";
    public static final String UPDATE_PARKING_SPOT = "update parking set available = ? where PARKING_NUMBER = ?";

    public static final String SAVE_TICKET = "insert into ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME, USER_EXISTS)"
    		+ " values(?,?,?,?,?,?)";
    public static final String UPDATE_TICKET = "update ticket set PRICE=?, OUT_TIME=? where ID=?";
    public static final String GET_TICKET = "select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE, t.USER_EXISTS"
    		+ " from ticket t,parking p"
    		+ " where p.parking_number = t.parking_number"
    		+ " and t.VEHICLE_REG_NUMBER=?"
    		+ " order by t.IN_TIME limit 1";
    public static final String CHECK_USER_EXISTS = "select (case when vehicle_reg_number = ?"
    			+ " and out_time is not null"
    			+ " and parking_number in (?, ?, ?) then true"
    		+ " else false end) as recurent_user from ticket";
    public static final String UPDATE_RECURENT_USER = "update ticket"
    		+ " set user_exists = true"
    		+ " where vehicle_reg_number = ?"
    			+ " and parking_number in (?, ?, ?)"
    			+ " and out_time is null";
}
