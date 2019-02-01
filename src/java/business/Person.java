/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author fssco
 */
public class Person implements Serializable{
    private String firstName;
    private String middleName;
    private String lastName;
    private int employeeID;
    private LocalDate birthDate;
    private LocalDate hireDate;

    public Person () {};
    
    public Person (String firstName, String middleName, String lastName, 
            int employeeID, LocalDate birthDate, LocalDate hireDate) 
    {
        this.firstName=firstName;
        this.middleName=middleName;
        this.lastName=lastName;
        this.employeeID=employeeID;
        this.birthDate=birthDate;
        this.hireDate=hireDate;
    }
    
    ////////Getters and Setters/////////////////
    public String getFirstName() 
    {
        return firstName;
    }

    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }

    public String getMiddleName() 
    {
        return middleName;
    }

    public void setMiddleName(String middleName) 
    {
        this.middleName = middleName;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

    public int getEmployeeID() 
    {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) 
    {
        this.employeeID = employeeID;
    }

    public LocalDate getBirthDate() 
    {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) 
    {
        this.birthDate = birthDate;
    }

    public LocalDate getHireDate() 
    {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) 
    {
        this.hireDate = hireDate;
    }
    
    
}
