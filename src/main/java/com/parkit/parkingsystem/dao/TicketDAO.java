package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;


public class TicketDAO {

    private static final Logger logger = LogManager.getLogger("TicketDAO");

    public DataBaseConfig dataBaseConfig = new DataBaseConfig();

	public boolean saveTicket(Ticket ticket){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.SAVE_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME, USER_EXISTS)
            //ps.setInt(1,ticket.getId());
            ps.setInt(1,ticket.getParkingSpot().getId());
            ps.setString(2, ticket.getVehicleRegNumber());
            ps.setBigDecimal(3, ticket.getPrice());
            ps.setTimestamp(4, new Timestamp(ticket.getInTime().getTime()));
            ps.setTimestamp(5, (ticket.getOutTime() == null)?null: (new Timestamp(ticket.getOutTime().getTime())) );
            //Ajout user_exists
            ps.setBoolean(6, ticket.getUserExists());
            return ps.execute();
        }catch (RuntimeException e) {
    		throw e;
    	}catch (Exception ex) {
            logger.error("Error fetching next available slot",ex);
        }finally {
        	DataBaseConfig.close(null, ps, con);
        }
        return false;
    }


	public Ticket getTicket(String vehicleRegNumber, boolean vehicleExitingIsFalse) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Ticket ticket = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            ps.setString(1,vehicleRegNumber);
            if (vehicleExitingIsFalse == false) {
            	ps.close();
            	ps = con.prepareStatement("select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE, t.USER_EXISTS"
            			+ " from ticket t,parking p"
            			+ " where p.parking_number = t.parking_number"
            			+ " and t.VEHICLE_REG_NUMBER=?"
            			+ " and t.out_time is null"
            			+ " order by t.IN_TIME limit 1");
            	ps.setString(1, vehicleRegNumber);
            }
            rs = ps.executeQuery();
            if(rs.next()){
                ticket = new Ticket();
                ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(1), ParkingType.valueOf(rs.getString(6)),false);
                ticket.setParkingSpot(parkingSpot);
                ticket.setId(rs.getInt(2));
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(rs.getBigDecimal(3));
                ticket.setInTime(rs.getTimestamp(4));
                ticket.setOutTime(rs.getTimestamp(5));
                //Ajout user_exists
                ticket.setUserExists(rs.getBoolean(7));
            }
        }catch (RuntimeException e) {
    		throw e;
    	}catch (Exception ex){
            logger.error("Error fetching next available slot",ex);
        }finally {
        	DataBaseConfig.close(rs, ps, con);
        }
        return ticket;
    }

    public boolean updateTicket(Ticket ticket) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.UPDATE_TICKET);
            ps.setBigDecimal(1, ticket.getPrice());
            ps.setTimestamp(2, new Timestamp(ticket.getOutTime().getTime()));
            ps.setInt(3,ticket.getId());
            ps.execute();
            return true;
        }catch (RuntimeException e) {
    		throw e;
    	}catch (Exception ex){
            logger.error("Error saving ticket info",ex);
        }finally {
        	DataBaseConfig.close(null, ps, con);
        }
        return false;
    }
    
    public boolean checkUserExists (String vehicleRegNumber, ParkingType type) {
    	boolean checkUserExists = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
    		con = dataBaseConfig.getConnection();
    		ps = con.prepareStatement(DBConstants.CHECK_USER_EXISTS);
    		ps.setString(1,	vehicleRegNumber);
    		if (type == ParkingType.CAR) {
    			ps.setString(2, "1");
    			ps.setString(3, "2");
    			ps.setString(4, "3");
    		}
    		else if(type == ParkingType.BIKE) {
    			ps.setString(2, "4");
    			ps.setString(3, "5");
    			ps.setString(4, "0"); // valeur incorrecte pour combler la query
    		} 		
    		rs = ps.executeQuery();
    		while(rs.next()) {
    			checkUserExists = rs.getBoolean(1);
    			if (checkUserExists == true) {
    				System.out.println("Welcome back! As a recurring user of our parking lot, you'll benefit from a 5% discount.");
    				break;
    			}
    		}
    	}catch (RuntimeException e) {
    		throw e;
    	}catch (Exception ex) {
    		logger.error("Error checking USER STATUS", ex);
    	}finally {
    		DataBaseConfig.close(rs, ps, con);
    	}
    	return checkUserExists;
    }
    
    //Methode pour modifier le status de USER_EXISTS 
    public boolean updateUserExists(String vehicleRegNumber, ParkingType type) {
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
    		con = dataBaseConfig.getConnection();
    		ps = con.prepareStatement(DBConstants.UPDATE_RECURENT_USER);
    		ps.setString(1, vehicleRegNumber);
    		if (type == ParkingType.CAR) {
    			ps.setString(2, "1");
    			ps.setString(3, "2");
    			ps.setString(4, "3");
    		}
    		else if(type == ParkingType.BIKE) {
    			ps.setString(2, "4");
    			ps.setString(3, "5");
    			ps.setString(4, "0"); // valeur incorrecte pour combler la query
    		}
    		ps.execute();
    		return true;
    	}catch (RuntimeException e) {
    		throw e;
    	}catch (Exception ex) {
     		logger.error("Error updating USER STATUS", ex);
    	}finally {
    		DataBaseConfig.close(null, ps, con);
		}
    	return false;
    }
}
