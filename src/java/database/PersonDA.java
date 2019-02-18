/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import business.Person;
import java.sql.*;
import static java.sql.Types.VARCHAR;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashMap;

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
            if(person.getMiddleName().equals("") || person.getMiddleName().isEmpty())
            {
               ps.setNull(3, VARCHAR); 
            }
            else
            {
                ps.setString(3, person.getMiddleName());
            }
            ps.setString(4, person.getLastName());
            //found similar code here: https://stackoverflow.com/questions/33066904/localdate-to-java-util-date-and-vice-versa-simplest-conversion
            Date bDate = Date.valueOf(person.getBirthDate());
            ps.setDate(5, bDate);
            Date hDate = Date.valueOf(person.getHireDate());
            ps.setDate(6, hDate);
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
    
    public int addEmps()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query
                = "INSERT INTO Person (EmployeeID, FirstName, MiddleName, LastName, BirthDate, HireDate) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        Person[] tempArray = new Person[8];
        
            tempArray[0]  = new Person("Cathy", "", "Jacobs", 123,
                LocalDate.of(1995, Month.JANUARY, 14), LocalDate.of(2016, Month.FEBRUARY, 4));
            tempArray[1]  = new Person("Pam", "Elise", "Baker", 124,
                LocalDate.of(1997, Month.MAY, 9), LocalDate.of(2018, Month.MARCH, 15));
            tempArray[2]  = new Person("Brandon", "M", "Crell", 125,
                LocalDate.of(1980, Month.OCTOBER, 10), LocalDate.of(2018, Month.JANUARY, 20));
            tempArray[3]  = new Person("BrookLynn", "Mia Louise", "Polenske", 126,
                LocalDate.of(1999, Month.JULY, 18), LocalDate.of(2019, Month.FEBRUARY, 4));
            tempArray[4]  = new Person("Echo", "Curly", "Polenske", 127,
                LocalDate.of(2006, Month.APRIL, 1), LocalDate.of(2018, Month.DECEMBER, 16));
            tempArray[5]  = new Person("RyAnna", "Emerson Lynn", "Polenske", 128,
                LocalDate.of(2001, Month.OCTOBER, 23), LocalDate.of(2015, Month.JUNE, 26));
            tempArray[6]  = new Person("McKayleigh", "Sue Marie", "Polenske", 129,
                LocalDate.of(1995, Month.OCTOBER, 11), LocalDate.of(2019, Month.FEBRUARY, 9));
            tempArray[7]  = new Person("Dwight", "Kurt", "Schrute", 130,
                LocalDate.of(1981, Month.JUNE, 19), LocalDate.of(2013, Month.SEPTEMBER, 27));
        
        for(Person temp : tempArray)
        {
            int ret = 0;
            try 
            {
                ps = connection.prepareStatement(query);
                ps.setInt(1, temp.getEmployeeID());
                ps.setString(2, temp.getFirstName());
                if(temp.getMiddleName().equals("") || temp.getMiddleName().isEmpty())
                {
                    ps.setNull(3, VARCHAR); 
                }
                else
                {
                    ps.setString(3, temp.getMiddleName());
                }
                ps.setString(4, temp.getLastName());
                //found similar code here: https://stackoverflow.com/questions/33066904/localdate-to-java-util-date-and-vice-versa-simplest-conversion
                Date bDate = Date.valueOf(temp.getBirthDate());
                ps.setDate(5, bDate);
                Date hDate = Date.valueOf(temp.getHireDate());
                ps.setDate(6, hDate);
                ret = ps.executeUpdate();
                return ps.executeUpdate();
            } 
            catch (SQLException e) 
            {
                System.out.println(e);
                ret = 0;
                return 0;
            } 
            finally 
            {
                DBUtil.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        }
        
        
    }
    
}
