package org.cpsc304.bigdata.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Helper static class that initializes the database every single time the application is started if there is
 * a valid connection.
 */
public class DatabaseInitializer {

    private static final String TABLES[] = {
            "User_Dept", "User_Info", "Physician", "Researcher",
            "Disease", "DeficiencyDisease", "HereditaryDisease", "HPattern", "PhysiologicalDisease", "InfectiousDisease",
            "InfectiousOrganism", "Strain",
            "Patient", "MedicalRecord",
            "SuffersFrom",
            "Treatment", "ClinicalTrial",
            "WorkOn", "DiagnosticTest", "DiagnosedBy"
    };
    private static final String CREATE_TABLES[] = {
            "CREATE TABLE User_Dept (" +
                    "Speciality VARCHAR(20) PRIMARY KEY," +
                    "Department VARCHAR(20)" +
                    ")",
            "CREATE TABLE User_Info (" +
                    "Username VARCHAR(20) PRIMARY KEY," +
                    "Name VARCHAR(20)," +
                    "Password VARCHAR(20) NOT NULL," +
                    "Speciality VARCHAR(20)," +
                    "FOREIGN KEY (Speciality) REFERENCES User_Dept(Speciality)" +
                    "ON DELETE SET NULL" +
                    ")",
            "CREATE TABLE Physician (" +
                    "Username VARCHAR(20) PRIMARY KEY," +
                    "Hospital VARCHAR(20)," +
                    "FOREIGN KEY (Username) REFERENCES User_Info(Username)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE Researcher (" +
                    "Username VARCHAR(20) PRIMARY KEY," +
                    "Institute VARCHAR(30)," +
                    "NumOfPublications INTEGER," +
                    "FOREIGN KEY (Username) REFERENCES User_Info(Username)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE ClinicalTrial (" +
                    "TrialName VARCHAR(100) PRIMARY KEY," +
                    "Duration_Years FLOAT," +
                    "Type VARCHAR(100)," +
                    "Risks VARCHAR(100)," +
                    "Num_Participants INTEGER," +
                    "IsComplete BOOLEAN," +
                    "T_Name VARCHAR(20) NOT NULL," +
                    "D_Name VARCHAR(100)," +
                    "FOREIGN KEY (T_Name) REFERENCES Treatment(TreatmentName)" +
                    "ON DELETE SET NULL," +
                    "FOREIGN KEY (D_Name) REFERENCES Disease(Name)" +
                    "ON DELETE NO ACTION" +
                    ")",
            "CREATE TABLE Disease (" +
                    "Name VARCHAR(100) PRIMARY KEY," +
                    "Prevalence INTEGER," +
                    "Symptoms VARCHAR(1000)," +
                    "ClinicalTrial VARCHAR(100)," +
                    "FOREIGN KEY (ClinicalTrial) REFERENCES ClinicalTrial(TrialName)" +
                    "ON DELETE SET NULL," +
                    "CHECK (Prevalence <= 100 && Prevalence >= 0)" +
                    ")",
            "CREATE TABLE DeficiencyDisease (" +
                    "Name VARCHAR(20) PRIMARY KEY," +
                    "DietaryElements VARCHAR(50)," +
                    "FOREIGN KEY (Name) REFERENCES Disease(Name)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE HPattern (" +
                    "Pattern VARCHAR(20) PRIMARY KEY," +
                    "GenderAffected INTEGER," +
                    "CHECK (GenderAffected < 5 && GenderAffected >= 0)" +
                    ")",
            "CREATE TABLE HereditaryDisease (" +
                    "Name VARCHAR(100) PRIMARY KEY," +
                    "HeritancePattern VARCHAR(20)," +
                    "Genes VARCHAR(20)," +
                    "FOREIGN KEY (HeritancePattern) REFERENCES HPattern(Pattern)" +
                    "ON DELETE SET NULL" +
                    ")",
            "CREATE TABLE PhysiologicalDisease (" +
                    "Name VARCHAR(100) PRIMARY KEY," +
                    "AssociatedArea VARCHAR(100)," +
                    "FOREIGN KEY (Name) REFERENCES Disease(Name)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE InfectiousDisease (" +
                    "Name VARCHAR(100) PRIMARY KEY," +
                    "TransmissionRoute VARCHAR(20)," +
                    "FOREIGN KEY (Name) REFERENCES Disease(Name)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE InfectiousOrganism (" +
                    "FamilyName VARCHAR(20) PRIMARY KEY," +
                    "ID_Name VARCHAR(20) UNIQUE," +
                    "CommonName VARCHAR(20)," +
                    "FOREIGN KEY (ID_Name) REFERENCES InfectiousDisease(Name)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE Strain (" +
                    "StrainName VARCHAR(20)," +
                    "FamilyName VARCHAR(20)," +
                    "Acronym VARCHAR(10)," +
                    "PRIMARY KEY (StrainName, FamilyName)," +
                    "FOREIGN KEY (FamilyName) REFERENCES InfectiousOrganism(FamilyName)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE Patient (" +
                    "ID VARCHAR(20) PRIMARY KEY," +
                    "Name VARCHAR(20)," +
                    "Address VARCHAR(50)," +
                    "Family_History VARCHAR(100)," +
                    "Age INTEGER," +
                    "Sex BOOLEAN," +
                    "P_Username VARCHAR(20) NOT NULL," +
                    "FOREIGN KEY (P_Username) REFERENCES Physician(Username)" +
                    ")",
            "CREATE TABLE MedicalRecord (" +
                    "PatientID VARCHAR(20)," +
                    "Start DATE," +
                    "End DATE," +
                    "Disease VARCHAR(100)," +
                    "Implants_Surgeries VARCHAR(500)," +
                    "Allergies VARCHAR(500)," +
                    "Medication VARCHAR(500)," +
                    "Lifestyle VARCHAR(500)," +
                    "PRIMARY KEY (PatientID, Start, Disease)," +
                    "FOREIGN KEY (PatientID) REFERENCES Patient(ID)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE SuffersFrom (" +
                    "P_ID VARCHAR(20)," +
                    "D_Name VARCHAR(20)," +
                    "Duration INTEGER," +
                    "SeverityIndex FLOAT," +
                    "PRIMARY KEY (P_ID, D_Name)," +
                    "FOREIGN KEY P_ID REFERENCES Patient" +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY (D_Name) REFERENCES Disease(Name)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE Treatment (" +
                    "TreatmentName VARCHAR(20) PRIMARY KEY," +
                    "Efficiency FLOAT," +
                    "Cost INTEGER," +
                    "Equipment VARCHAR(20)," +
                    "Risks VARCHAR(100)," +
                    "CT_Name VARCHAR(100)," +
                    "FOREIGN KEY (CT_Name) REFERENCES ClinicalTrial(TrialName)" +
                    "ON DELETE SET NULL" +
                    ")",
            "CREATE TABLE WorkOn (" +
                    "R_Username VARCHAR(20)," +
                    "CT_Name VARCHAR(20)," +
                    "PRIMARY KEY (R_Username, CT_Name)," +
                    "FOREIGN KEY (R_Username) REFERENCES Researcher(Username)" +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY (CT_Name) REFERENCES ClinicalTrial(TrialName)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE DiagnosticTest (" +
                    "DT_Name VARCHAR(20) PRIMARY KEY," +
                    "Accuracy FLOAT" +
                    ")",
            "CREATE TABLE DiagnosedBy (" +
                    "DT_Name VARCHAR(20)," +
                    "D_Name VARCHAR(100)," +
                    "Target VARCHAR(20)," +
                    "PRIMARY KEY (DT_Name, D_Name)," +
                    "FOREIGN KEY (DT_Name) REFERENCES DiagnosticTest(DT_Name)" +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY (D_Name) REFERENCES Disease(Name)" +
                    "ON DELETE CASCADE" +
                    ")"
    };

    private static final String TUPLE_USER_INFO[] = {
            "INSERT INTO User_Info VALUES( A, A, 0, Molecular Oncology )",
            "INSERT INTO User_Info VALUES(hbtaussig, Helen Brooke Taussig, 123456789, Molecular Oncology )",
            "INSERT INTO User_Info VALUES(zjanzekovic, Zora Janzekovic, 111111111, Dentistry )",
            "INSERT INTO User_Info VALUES( vapgar, Virginia Apgar, qwerty, Dentistry )"
    };

    private static final String TUPLE_USER_DEPT[] = {
            "INSERT INTO User_Dept VALUES( Molecular Oncology, Cancer Studies )",
            "INSERT INTO User_Dept VALUES( Dentistry, Dentistry )"
    };

    private static final String TUPLE_PHYSICIAN[] = {
            "INSERT INTO Physician VALUES(hbtaussig, Johns Hopkins Hospital )",
            "INSERT INTO Physician VALUES( zjanzekovic, General Hospital Maribor )"
    };

    private static final String TUPLE_RESEARCHER[] = {
            "INSERT INTO Researcher VALUES(jhopps, Johns Hopkins Hospital 32 )",
            "INSERT INTO Researcher VALUES( bwane, University of British Columbia 2 )"
    };

    private static final String TUPLE_DISEASE[] = {
            "INSERT INTO Disease VALUES(" +
                    "Iron deficiency," +
                    "50" +
                    "Unusual tiredness, paleness, shortness of breath" +
                    "Lactoferrin versus Ferrous Sulfate in Iron-deficiency During Pregnancy" +
                    ")",
            "INSERT INTO Disease VALUES(" +
                    "Glaucoma," +
                    "20" +
                    "Blind spots in sides or central vision in both eyes" +
                    "NULL" +
                    ")"
    };

    private static Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    public static void initDatabase(final Connection connection) {
        logger.info("Initializing database");
        initTables(connection);
        populateTables(connection);
    }

    private static void initTables(final Connection connection) {
        deleteExistingTables(connection);
        createTables(connection);
    }

    private static void deleteExistingTables(final Connection connection) {
        String tableName = "";
        for (final String table : TABLES) {
            try {
                tableName = table;
                PreparedStatement statement = connection.prepareStatement("DELETE FROM " + table);
                statement.executeUpdate();
            } catch (SQLException e) {
                logger.warn(tableName + ": " + e.getMessage().replace("\n", ""));
            }
        }
    }

    private static void createTables(final Connection connection) {
        String tableName = "";
        for (final String stmt : CREATE_TABLES) {
            try {
                tableName = stmt.split(" ")[2];
                PreparedStatement statement = connection.prepareStatement(stmt);
                statement.executeUpdate();
            } catch (SQLException e) {
                logger.warn(tableName + ": " + e.getMessage().replace("\n", ""));
            }
        }
    }

    private static void populateTables(final Connection connection) {
        populateTableHelper(connection, TUPLE_USER_INFO);
        populateTableHelper(connection, TUPLE_USER_DEPT);
        populateTableHelper(connection, TUPLE_PHYSICIAN);
        populateTableHelper(connection, TUPLE_RESEARCHER);
        populateTableHelper(connection, TUPLE_DISEASE);
    }



    private static void populateTableHelper(final Connection connection, String[] tuples){
        for (final String stmt : tuples) {
            try {
                PreparedStatement statement = connection.prepareStatement(stmt);
                statement.executeUpdate();
            } catch (SQLException e) {
                logger.warn(stmt + ": " + e.getMessage().replace("\n", ""));
            }
        }
    }


}
