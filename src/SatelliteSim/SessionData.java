/* Project name: CMSC495
 * File name: Simulation.java
 * Authors: Timothy Eckart, Tyler Blumenshine, Ricardo Gordon, Mitch Mclaughlin, Siddharth Patel
 * Date: 5 May 2025
 * Purpose: Setters and Getters for UserID.
 */

package SatelliteSim;

public class SessionData {
    private static int userID;

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        SessionData.userID = userID;
    }
}

