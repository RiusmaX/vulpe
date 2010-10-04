// -----------------------------------------------------------------------------
// CallPLSQLFunc.java
// -----------------------------------------------------------------------------

 /*
  * =============================================================================
  * Copyright (c) 1998-2009 Jeffrey M. Hunter. All rights reserved.
  *
  * All source code and material located at the Internet address of
  * http://www.idevelopment.info is the copyright of Jeffrey M. Hunter and
  * is protected under copyright laws of the United States. This source code may
  * not be hosted on any other site without my express, prior, written
  * permission. Application to host any of the material elsewhere can be made by
  * contacting me at jhunter@idevelopment.info.
  *
  * I have made every effort and taken great care in making sure that the source
  * code and other content included on my web site is technically accurate, but I
  * disclaim any and all responsibility for any loss, damage or destruction of
  * data or any other property which may arise from relying on it. I will in no
  * case be liable for any monetary damages arising from such loss, damage or
  * destruction.
  *
  * As with any code, ensure to test this code in a development environment
  * before attempting to run it in production.
  * =============================================================================
  */

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;


/**
 * -----------------------------------------------------------------------------
 * The PL/SQL language allows you to write procedures, functions, and
 * packages allowing you to centralize business logic and store the code
 * directly in the database. This business logic may be called in many
 * different ways including from a JDBC program.
 *
 * The following class provides an example of using JDBC to to call a PL/SQL
 * function.
 *
 * In short, there are five steps involved in calling a PL/SQL Function from
 * within a JDBC application:
 *
 *      1.) Create and prepare a JDBC CallableStatement object that contains
 *          a call to your PL/SQL function. The CallableStatement is similar
 *          to the PreparedStatement.
 *
 *      2.) Register the output parameter for your PL/SQL function.
 *
 *      3.) Provide all of the required parameter values to your PL/SQL
 *          function.
 *
 *      4.) Call the execute() method for your CallableStatement object,
 *          which then performs the call to your PL/SQL procedure.
 *
 *      5.) Read the returned value from your PL/SQL function.
 *
 * NOTE: In order to successfully use this class, you will need to run the
 *       create_all_ddl.sql file included in the same section this example class
 *       is located.
 * -----------------------------------------------------------------------------
 */

public class CallPLSQLFunc {

    final static String driverClass    = "oracle.jdbc.driver.OracleDriver";
    final static String connectionURL  = "jdbc:oracle:thin:@10.0.27.181:1521:siatuh";
    final static String userID         = "legado";
    final static String userPassword   = "anel98";
    Connection   con                   = null;


    /**
     * Construct a CallPLSQLFunc object. This constructor will create an Oracle
     * database connection.
     */
    public CallPLSQLFunc() {

        try {

            System.out.print("  Loading JDBC Driver  -> " + driverClass + "\n");
            Class.forName(driverClass).newInstance();

            System.out.print("  Connecting to        -> " + connectionURL + "\n");
            this.con = DriverManager.getConnection(connectionURL, userID, userPassword);
            System.out.print("  Connected as         -> " + userID + "\n\n");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Method to call the PL/SQL function "get_employee_salary"
     */
    public void performFuncCall() {

        CallableStatement cstmt = null;
        String retorno;

        try {

            // -------------------------------------------------------------
            // Call PL/SQL Function to retrieve an employee’s monthly salary
            // -------------------------------------------------------------
            cstmt = con.prepareCall("{? = call pk_transfere_objeto.Gera_Transferencia(?)}");
            cstmt.registerOutParameter(1, java.sql.Types.VARCHAR);
            cstmt.setInt(2, 3302);
            cstmt.execute();
            retorno = cstmt.getString(1);
            cstmt.close();

            System.out.println(retorno);

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }


    /**
     * Close down Oracle connection.
     */
    public void closeConnection() {

        try {
            System.out.print("  Closing Connection...\n");
            con.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     * @exception java.lang.InterruptedException
     *            Thrown from the Thread class.
     */
    public static void main(String[] args)
            throws java.lang.InterruptedException {

        CallPLSQLFunc mainPrg = new CallPLSQLFunc();
        mainPrg.performFuncCall();
        mainPrg.closeConnection();

    }

}
