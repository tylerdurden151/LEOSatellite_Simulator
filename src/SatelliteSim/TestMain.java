package SatelliteSim;

public class TestMain {
    public static void main(String[] args) {
        SatelliteDataBaseManager manager = new SatelliteDataBaseManager();

        // Test adding a valid satellite
        try {
            Satellite satellite = new Satellite();
            satellite.setSatelliteName("Hubble");
            satellite.setUserID(1L);
            satellite.setMass(11110.5);
            satellite.setAltitude(547.12);
            satellite.setSpeed(7.66);
            satellite.setArea(4.7);
            manager.addSatellite(satellite);
            System.out.println("Test Case: Add valid satellite - Passed");
        } catch (SatelliteError e) {
            System.err.println("Test Case: Add valid satellite - Failed | " + e.toString());
        }

        // Test adding an invalid satellite (negative mass)
        try {
            Satellite satellite = new Satellite();
            satellite.setSatelliteName("InvalidSat");
            satellite.setUserID(2L);
            satellite.setMass(-5.0); // Invalid mass
            satellite.setAltitude(500.0);
            satellite.setSpeed(5.5);
            satellite.setArea(3.5);
            manager.addSatellite(satellite);
            System.out.println("Test Case: Add invalid satellite - Failed (Expected ValidationError)");
        } catch (ValidationError e) {
            System.out.println("Test Case: Add invalid satellite - Passed | " + e.toString());
        }

        // Test getting a satellite by ID
        try {
            Satellite satellite = manager.getSatelliteById(1);
            System.out.println("Test Case: Get satellite by ID - Passed | Satellite Name: " + satellite.getSatelliteName());
        } catch (SatelliteError e) {
            System.err.println("Test Case: Get satellite by ID - Failed | " + e.toString());
        }

        // Test getting a satellite with a nonexistent ID
        try {
            Satellite satellite = manager.getSatelliteById(99); // Nonexistent ID
            System.out.println("Test Case: Get nonexistent satellite - Failed (Expected DatabaseError)");
        } catch (DatabaseError e) {
            System.out.println("Test Case: Get nonexistent satellite - Passed | " + e.toString());
        }

        // Test updating a valid satellite
        try {
            Satellite satellite = new Satellite();
            satellite.setSatelliteID(1); // Existing ID
            satellite.setSatelliteName("UpdatedHubble");
            satellite.setUserID(1L);
            satellite.setMass(12000.8);
            satellite.setAltitude(600.5);
            satellite.setSpeed(8.12);
            satellite.setArea(5.5);
            manager.updateSatellite(satellite);
            System.out.println("Test Case: Update valid satellite - Passed");
        } catch (SatelliteError e) {
            System.err.println("Test Case: Update valid satellite - Failed | " + e.toString());
        }

        // Test updating a satellite with invalid mass
        try {
            Satellite satellite = new Satellite();
            satellite.setSatelliteID(1); // Existing ID
            satellite.setSatelliteName("InvalidUpdate");
            satellite.setUserID(1L);
            satellite.setMass(-10.0); // Invalid mass
            satellite.setAltitude(500.0);
            satellite.setSpeed(5.5);
            satellite.setArea(3.5);
            manager.updateSatellite(satellite);
            System.out.println("Test Case: Update invalid satellite - Failed (Expected ValidationError)");
        } catch (ValidationError e) {
            System.out.println("Test Case: Update invalid satellite - Passed | " + e.toString());
        }

        // Test deleting a satellite by ID
        try {
            manager.deleteSatelliteById(1); // Existing ID
            System.out.println("Test Case: Delete satellite by ID - Passed");
        } catch (SatelliteError e) {
            System.err.println("Test Case: Delete satellite by ID - Failed | " + e.toString());
        }

        // Test deleting a satellite with a nonexistent ID
        try {
            manager.deleteSatelliteById(99); // Nonexistent ID
            System.out.println("Test Case: Delete nonexistent satellite - Failed (Expected DatabaseError)");
        } catch (DatabaseError e) {
            System.out.println("Test Case: Delete nonexistent satellite - Passed | " + e.toString());
        }
        // Test getSatelliteIdByName()
        try {
            int satelliteId = manager.getSatelliteIdByName("Hubble");
            System.out.println("Test Case: Get Satellite ID by Name - Passed | Satellite ID: " + satelliteId);
        } catch (DatabaseError e) {
            System.err.println("Test Case: Get Satellite ID by Name - Failed | " + e.toString());
        }

        // Test getSatelliteIdByName() for nonexistent name
        try {
            manager.getSatelliteIdByName("NonexistentSat");
            System.out.println("Test Case: Get ID for nonexistent satellite - Failed (Expected DatabaseError)");
        } catch (DatabaseError e) {
            System.out.println("Test Case: Get ID for nonexistent satellite - Passed | " + e.toString());
        }

        // Test getSatelliteNamesAndIds()
        try {
            String[] satelliteNames = manager.getSatelliteNamesAndIds();
            System.out.println("Test Case: Get Satellite Names and IDs - Passed");
            for (int i = 0; i < satelliteNames.length; i++) {
                if (satelliteNames[i] != null) {
                    System.out.println("ID: " + i + ", Name: " + satelliteNames[i]);
                }
            }
        } catch (DatabaseError e) {
            System.err.println("Test Case: Get Satellite Names and IDs - Failed | " + e.toString());
        }

        // Test getSatelliteNamesAndIds() for empty database
        try {
            // Assuming the database is empty now
            String[] satelliteNames = manager.getSatelliteNamesAndIds();
            if (satelliteNames.length == 0) {
                System.out.println("Test Case: Get Names and IDs for empty database - Passed");
            } else {
                System.out.println("Test Case: Get Names and IDs for empty database - Failed");
            }
        } catch (DatabaseError e) {
            System.err.println("Test Case: Get Names and IDs for empty database - Failed | " + e.toString());
        }
    }

}
}

