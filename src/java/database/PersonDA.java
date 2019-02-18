/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import business.Person;
import java.sql.*;

/**
 *
 * @author mp565340
 */
public class PersonDA 
{
    public static int insert(Person person)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query
                = "INSERT INTO Person (EmployeeID, FirstName, MiddleName, LastName, BirthDate, HireDate) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try 
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, person.getEmployeeID());
            ps.setString(2, person.getFirstName());
            ps.setString(3, person.getMiddleName());
            ps.setString(4, person.getLastName());
            //ps.setDate(5, person.getBirthDate());
            return ps.executeUpdate();
        } 
        catch (SQLException e) 
        {
            System.out.println(e);
            return 0;
        } 
        finally 
        {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
