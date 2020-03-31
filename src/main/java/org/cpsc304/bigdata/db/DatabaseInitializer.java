package org.cpsc304.bigdata.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Helper static class that initializes the database every single time the application is started if there is
 * a valid connection.
 */
public class DatabaseInitializer {

    private static final String[] DROP_TABLES = {
            "ClinicalTrial_Treatment", "Disease_ClinicalTrial",
            "ClinicalTrial", "Treatment", "Disease",
            "DiagnosedBy", "DiagnosticTest", "WorkOn",
            "SuffersFrom",
            "MedicalRecord", "Patient",
            "Strain", "InfectiousOrganism",
            "InfectiousDisease", "PhysiologicalDisease", "HereditaryDisease", "HPattern", "DeficiencyDisease",
            "Researcher", "Physician", "User_Info", "User_Dept"

    };
    private static final String[] CREATE_TABLES = {
            "CREATE TABLE User_Dept (" +
                    "Speciality VARCHAR(20) PRIMARY KEY," +
                    "Department VARCHAR(20)" +
                    ")",
            "CREATE TABLE User_Info (" +
                    "Username VARCHAR(15) PRIMARY KEY," +
                    "Name VARCHAR(20)," +
                    "Password VARCHAR(15) NOT NULL," +
                    "Speciality VARCHAR(20)," +
                    "FOREIGN KEY (Speciality) REFERENCES User_Dept(Speciality)" +
                    "ON DELETE SET NULL" +
                    ")",
            "CREATE TABLE Physician (" +
                    "Username VARCHAR(15) PRIMARY KEY," +
                    "Hospital VARCHAR(30)," +
                    "FOREIGN KEY (Username) REFERENCES User_Info(Username)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE Researcher (" +
                    "Username VARCHAR(15) PRIMARY KEY," +
                    "Institute VARCHAR(30)," +
                    "NumOfPublications NUMBER(3, 0)," +
                    "FOREIGN KEY (Username) REFERENCES User_Info(Username)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE ClinicalTrial (" +
                    "TrialName VARCHAR(50) PRIMARY KEY," +
                    "Duration_Years NUMBER(3, 0)," +
                    "Type VARCHAR(50)," +
                    "Risks VARCHAR(50)," +
                    "Num_Participants NUMBER(3, 0)," +
                    "IsComplete NUMBER(3, 0)," +
                    "CHECK (IsComplete >= 0 AND IsComplete <= 1)" +
                    ")",
            "CREATE TABLE Disease (" +
                    "Name VARCHAR(30) PRIMARY KEY, " +
                    "Prevalence NUMBER(3, 0), " +
                    "Symptoms VARCHAR(60), " +
                    "CHECK (Prevalence <= 100 AND Prevalence >= 0)" +
                    ")",
            "CREATE TABLE Disease_ClinicalTrial (" +
                    "DName VARCHAR(30)," +
                    "CTName VARCHAR (50), " +
                    "PRIMARY KEY (DName, CTName), " +
                    "FOREIGN KEY (DName) REFERENCES Disease(Name), " +
                    "FOREIGN KEY (CTName) REFERENCES ClinicalTrial(TrialName)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE DeficiencyDisease (" +
                    "Name VARCHAR(30) PRIMARY KEY," +
                    "DietaryElements VARCHAR(20)," +
                    "FOREIGN KEY (Name) REFERENCES Disease(Name)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE HPattern (" +
                    "Pattern VARCHAR(20) PRIMARY KEY," +
                    "GenderAffected NUMBER(3, 0)," +
                    "CHECK (GenderAffected < 5 AND GenderAffected >= 0)" +
                    ")",
            "CREATE TABLE HereditaryDisease (" +
                    "Name VARCHAR(30) PRIMARY KEY," +
                    "HeritancePattern VARCHAR(20)," +
                    "Genes VARCHAR(20)," +
                    "FOREIGN KEY (HeritancePattern) REFERENCES HPattern(Pattern)" +
                    "ON DELETE SET NULL" +
                    ")",
            "CREATE TABLE PhysiologicalDisease (" +
                    "Name VARCHAR(30) PRIMARY KEY," +
                    "AssociatedArea VARCHAR(30)," +
                    "FOREIGN KEY (Name) REFERENCES Disease(Name)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE InfectiousDisease (" +
                    "Name VARCHAR(30) PRIMARY KEY," +
                    "TransmissionRoute VARCHAR(15)," +
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
                    "Family_History VARCHAR(60)," +
                    "Age NUMBER(3, 0)," +
                    "Sex NUMBER(3, 0)," +
                    "P_Username VARCHAR(20) NOT NULL," +
                    "FOREIGN KEY (P_Username) REFERENCES Physician(Username)," +
                    "CHECK (Sex <= 1 AND Sex >= 0)" +
                    ")",
            "CREATE TABLE MedicalRecord (" +
                    "PatientID VARCHAR(20)," +
                    "Start_date DATE," +
                    "End_date DATE," +
                    "Disease VARCHAR(100)," +
                    "Implants_Surgeries VARCHAR(50)," +
                    "Allergies VARCHAR(50)," +
                    "Medication VARCHAR(50)," +
                    "Lifestyle VARCHAR(50)," +
                    "PRIMARY KEY (PatientID, Start_date, Disease)," +
                    "FOREIGN KEY (PatientID) REFERENCES Patient(ID)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE SuffersFrom (" +
                    "P_ID VARCHAR(20)," +
                    "D_Name VARCHAR(100)," +
                    "Duration NUMBER(3, 0)," +
                    "SeverityIndex FLOAT," +
                    "PRIMARY KEY (P_ID, D_Name)," +
                    "FOREIGN KEY (P_ID) REFERENCES Patient(ID)" +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY (D_Name) REFERENCES Disease(Name)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE Treatment (" +
                    "TreatmentName VARCHAR(20) PRIMARY KEY," +
                    "Efficiency FLOAT," +
                    "Cost NUMBER(3, 0)," +
                    "Equipment VARCHAR(20)," +
                    "Risks VARCHAR(100)" +
                    ")",
            "CREATE TABLE ClinicalTrial_Treatment (" +
                    "CTName VARCHAR(100)," +
                    "TName VARCHAR(20)," +
                    "PRIMARY KEY(CTName, TName)," +
                    "FOREIGN KEY(CTName) REFERENCES ClinicalTrial(TrialName)" +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY(TName) REFERENCES Treatment(TreatmentName)" +
                    "ON DELETE CASCADE" +
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
                    "D_Name VARCHAR(30)," +
                    "Target VARCHAR(20)," +
                    "PRIMARY KEY (DT_Name, D_Name)," +
                    "FOREIGN KEY (DT_Name) REFERENCES DiagnosticTest(DT_Name)" +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY (D_Name) REFERENCES Disease(Name)" +
                    "ON DELETE CASCADE" +
                    ")"
    };

    private static final String[] TUPLE_USER_INFO = {
            "INSERT INTO User_Info VALUES('aheale', 'Andrew Heale', '000', 'Human Physiology')",
            "INSERT INTO User_Info VALUES('hbtaussig', 'Helen Brooke Taussig', '123456789', 'Molecular Oncology')",
            "INSERT INTO User_Info VALUES('zjanzekovic', 'Zora Janzekovic', '111111111', 'Molecular Oncology')",
            "INSERT INTO User_Info VALUES('vapgar','Virginia Apgar', 'qwerty', 'Nephrology')",
            "INSERT INTO User_Info VALUES('jhopps', 'John Hopkins', '11dtg', 'Molecular Virology')"
    };

    private static final String[] TUPLE_USER_DEPT = {
            "INSERT INTO User_Dept VALUES('Molecular Oncology', 'Cancer Studies')",
            "INSERT INTO User_Dept VALUES('Nephrology', 'Physiology')",
            "INSERT INTO User_Dept VALUES('Human Physiology', 'Physiology')",
            "INSERT INTO User_Dept VALUES('Molecular Virology', 'Immunology')"
    };

    private static final String[] TUPLE_PHYSICIAN = {
            "INSERT INTO Physician VALUES('hbtaussig', 'John Hopkins Hospital')",
            "INSERT INTO Physician VALUES('zjanzekovic', 'General Hospital Maribor')"
    };

    private static final String[] TUPLE_RESEARCHER = {
            "INSERT INTO Researcher VALUES('jhopps', 'Johns Hopkins Hospital', '32')",
            "INSERT INTO Researcher VALUES('vapgar', 'University of British Columbia', '2')",
            "INSERT INTO Researcher VALUES('aheale', 'University of Washington', '10')"
    };

    private static final String[] TUPLE_DISEASE = {
            "INSERT INTO Disease VALUES('Iron deficiency', '15', 'Unusual tiredness paleness shortness of breath')",
            "INSERT INTO Disease VALUES('Glaucoma', '9', 'Blind spots in sides or central vision in both eyes')",
            "INSERT INTO Disease VALUES('Pellagra', '3', 'Diarrhea, abdominal pain, weakness')",
            "INSERT INTO Disease VALUES('Avian influenza', '25', 'dry cough, fever, sore throat, headache, runny nose')",
            "INSERT INTO Disease VALUES('AIDS', '3', 'Swollen lymph nodes, fever, fatigue, rash')",
            "INSERT INTO Disease VALUES('Scurvy', '4', 'Anemia, bleeding, exhaustion')",
            "INSERT INTO Disease VALUES('Sickle cell anemia', '2', 'Pain, fatigue, spleen and liver congestion, dactylitis')",
            "INSERT INTO Disease VALUES('Hemophilia', '4', 'blood in urine and stool, excessive bleeding, bruises')",
            "INSERT INTO Disease VALUES('Lebers optic neuropathy', '1', 'Blurring and clouding of vision')",
            "INSERT INTO Disease VALUES('Diabetes', '12', 'Thirst, hunger, frequent urination, weight loss')",
            "INSERT INTO Disease VALUES('Asthma', '7', 'Tight chest, short breath, wheezing')",
            "INSERT INTO Disease VALUES('Dengue fever', '5', 'High fever, headache, muscle pain')",

    };

    private static final String[] TUPLE_DEFICIENCY_DISEASE = {
            "INSERT INTO DeficiencyDisease VALUES('Iron deficiency', 'Iron')",
            "INSERT INTO DeficiencyDisease VALUES('Pellagra', 'Niacin')",
            "INSERT INTO DeficiencyDisease VALUES('Scurvy', 'Ascorbic acid')"
    };

    private static final String[] TUPLE_HEREDITARY_DISEASE = {
            "INSERT INTO HereditaryDisease VALUES ('Sickle cell anemia', 'Autosomal recessive', 'Hemoglobin-Beta gene')",
            "INSERT INTO HereditaryDisease VALUES ('Hemophilia', 'X-linked recessive', 'F8 gene')",
            "INSERT INTO HereditaryDisease VALUES ('Lebers optic neuropathy', 'Mitochondrial', 'MT-ND1 gene')"
    };

    private static final String[] TUPLE_HPATTERN = {
            "INSERT INTO HPattern VALUES ('Mitochondrial', '0')",
            "INSERT INTO HPattern VALUES ('Autosomal recessive','0')",
            "INSERT INTO HPattern VALUES ('X-linked recessive', '3')"
    };

    private static final String[] TUPLE_PHYSIOLOGICAL_DISEASE = {
            "INSERT INTO PhysiologicalDisease VALUES ('Diabetes', 'Pancreas')",
            "INSERT INTO PhysiologicalDisease VALUES ('Glaucoma', 'Optic nerve')",
            "INSERT INTO PhysiologicalDisease VALUES ('Asthma', 'Respiratory tract')"
    };

    private static final String[] TUPLE_INFECTIOUS_DISEASE = {
            "INSERT INTO InfectiousDisease VALUES ('Avian influenza', 'Fecal-oral')",
            "INSERT INTO InfectiousDisease VALUES ('AIDS', 'Blood fluid')",
            "INSERT INTO InfectiousDisease VALUES ('Dengue fever', 'Fecal-oral')"
    };

    private static final String[] TUPLE_INFECTIOUS_ORGANISM = {
            "INSERT INTO InfectiousOrganism VALUES('Retroviridae', 'AIDS', 'HIV')",
            "INSERT INTO InfectiousOrganism VALUES('Flaviviridae', 'Dengue fever', 'Dengue virus')",
            "INSERT INTO InfectiousOrganism VALUES('Orthomyxoviridae', 'Avian influenza', 'Flu')"
    };

    private static final String[] TUPLE_STRAIN = {
            "INSERT INTO Strain VALUES('01','Orthomyxoviridae', 'Influenza A H1N1')",
            "INSERT INTO Strain VALUES('05','Orthomyxoviridae', 'Influenza A H3N2')",
            "INSERT INTO Strain VALUES('DENV-2','Flaviviridae', NULL)"
    };

    private static final String[] TUPLE_PATIENT = {
            "INSERT INTO Patient VALUES('1', 'John Smith', '1020 Queens Ave. Vancouver'," +
                    " 'Mother: Meniere’s | Grandmother: Cardiac arrest', '31', '0', 'hbtaussig')",
            "INSERT INTO Patient VALUES('2', 'Bob Baker', '756 Kings Dr. Burnaby', NULL, '20', '0', 'zjanzekovic')",
            "INSERT INTO Patient VALUES('3', 'Ann Brown', '123 Lake St. North Vancouver'," +
                    " 'Father: cystic fibrosis | Brother: follicular lymphoma', '20', '0', 'zjanzekovic')"
    };

    private static final String[] TUPLE_MEDICAL_RECORD = {
            "INSERT INTO MedicalRecord VALUES('1', '1999-04-23', '1999-05-08', 'Pneumonia', NULL, 'tetracycline, tree nuts'" +
                    "'Ciprofloxacin', 'active, vegan, high stress')",
            "INSERT INTO MedicalRecord VALUES('2', '2012-11-01', '2012-11-08', 'Glaucoma', 'Argon laser trabeculoplasty'," +
                    " 'penicillin', 'morphine', 'sedentary, alcoholic')",
    };

    private static final String[] TUPLE_SUFFERS_FROM = {
            "INSERT INTO SuffersFrom VALUES('1', 'Iron deficiency', 496, 0.12)",
            "INSERT INTO SuffersFrom VALUES('1', 'Avian influenza', 20, 0.21)",
            "INSERT INTO SuffersFrom VALUES('2', 'Glaucoma', 670, 0.35)",
    };

    private static final String[] TUPLE_WORK_ON = {
            "INSERT INTO WorkOn VALUES('jhopps', 'Alpelisib SOLAR-1 Phase 3')",
            "INSERT INTO WorkOn VALUES('vapgar', 'Cerebral Blood Flow During Hemodialysis')",
            "INSERT INTO WorkOn VALUES('aheale', 'Lactoferrin versus Ferrous Sulfate in Iron-deficiency During Pregnancy')"
    };

    private static final String[] TUPLE_DIAGNOSTIC_TEST = {
            "INSERT INTO DiagnosticTest VALUES('HIV1/HIV2 Immunoassay', 0.9)",
            "INSERT INTO DiagnosticTest VALUES('Serum iron test', 0.63)",
            "INSERT INTO DiagnosticTest VALUES('Heidelberg Retinal Tomography', 0.69)",
            "INSERT INTO DiagnosticTest VALUES('Transferrin', 0.8)"
    };

    private static final String[] TUPLE_DIAGNOSED_BY = {
            "INSERT INTO DiagnosedBy VALUES('HIV1/HIV2 Immunoassay', 'AIDS', 'HIV Antigen/Antibody')",
            "INSERT INTO DiagnosedBy VALUES('Serum iron test', 'Iron Deficiency', 'serum iron')",
            "INSERT INTO DiagnosedBy VALUES('Transferrin', 'Iron Deficiency', 'Transferrin protein')",
            "INSERT INTO DiagnosedBy VALUES('Heidelberg Retinal Tomography', 'Glaucoma', 'Optic nerve head')"
    };

    private static final String[] TUPLE_TREATMENT = {
            "INSERT INTO Treatment VALUES('Hemodialysis', 0.76, 30000, 'Dialysis machine', " +
                    "'hypotension, anemia, blood clot, infection')",
            "INSERT INTO Treatment VALUES('Alpelisib', 0.67, 15500, NULL, 'severe hypersensitivity, anaphylaxis, anaphylactic shock')",
            "INSERT INTO Treatment VALUES('Implantable cardioverter defibrillator', 36000, 'surgery room, pacemaker', " +
                    "'infection, allergic reaction, bleeding')",
            "INSERT INTO Treatment VALUES('Ferrous sulfate', 30, NULL, " +
                    "'constipation, diarrhea, GI irritation')"
    };

    private static final String[] TUPLE_CLINICAL_TRIAL = {
            "INSERT INTO ClinicalTrial VALUES('Lactoferrin versus Ferrous Sulfate in Iron-deficiency During Pregnancy',"+
                    "'0.9', 'Randomized Parallel assignment, no masking', " +
                    "'Diarrhea, fatigue, chills, loss of appetite', '98', " +
                    "'1')",
            "INSERT INTO ClinicalTrial VALUES('Alpelisib SOLAR-1 Phase 3'," +
                    "'5', 'Phase III randomized double-blind, triple masking', " +
                    "'Severe hypersensitivity, anaphylaxis, anaphylactic shock', '572', " +
                    "'0')",
            "INSERT INTO ClinicalTrial VALUES('Cerebral Blood Flow During Hemodialysis'," +
                    "'5', 'Single group assignment, no masking', " +
                    "'hypotension, anemia, blood clot, infection', '15', " +
                    "'1')"
    };

    private static final String[] TUPLE_CLINICAL_TRIAL_TREATMENT = {
            "INSERT INTO ClinicalTrial_Treatment VALUES('Alpelisib SOLAR-1 Phase 3', 'Alpelisib')",
            "INSERT INTO ClinicalTrial_Treatment VALUES('Cerebral Blood Flow During Hemodialysis', 'Hemodialysis')",
            "INSERT INTO ClinicalTrial_Treatment VALUES('Lactoferrin versus Ferrous Sulfate in Iron-deficiency During Pregnancy'," +
                    "'Ferrous sulfate')",
    };

    private static final String[] TUPLE_DISEASE_CLINICAL_TRIAL = {
            "INSERT INTO Disease_ClinicalTrial VALUES('Iron deficiency', 'Lactoferrin versus Ferrous Sulfate in Iron-deficiency During Pregnancy')",
            "INSERT INTO Disease_ClinicalTrial VALUES('AIDS', 'Alpelisib SOLAR-1 Phase 3')",
            "INSERT INTO Disease_ClinicalTrial VALUES('Kidney Disease', 'Cerebral Blood Flow During Hemodialysis')"
    };

    private static Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    public static void initDatabase(final Connection connection) {
        logger.info("Initializing database");
        initTables(connection);
        populateTables(connection);
    }

    private static void initTables(final Connection connection) {
        dropExistingTables(connection);
        createTables(connection);
    }

    private static void dropExistingTables(final Connection connection){
        logger.info("Dropping existing tables multiple times...");
        String tableName = "";
        boolean droppedATable = true;
        while (droppedATable) {
            droppedATable = false;
            for (final String table : DROP_TABLES) {
                try {
                    tableName = table;
                    PreparedStatement statement = connection.prepareStatement("DROP TABLE " + table + " CASCADE CONSTRAINTS PURGE");
                    statement.executeUpdate();
                    droppedATable = true;
                } catch (SQLException e) {
                    logger.debug(tableName + ": " + e.getMessage().replace("\n", ""));
                }
            }
        }
    }

    private static void createTables(final Connection connection) {
        logger.info("Creating tables...");
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
        logger.info("Populating tables...");

        String[][] tables = new String[][] {
                TUPLE_USER_DEPT,TUPLE_USER_INFO, TUPLE_PHYSICIAN, TUPLE_RESEARCHER, TUPLE_DISEASE, TUPLE_DEFICIENCY_DISEASE,
                TUPLE_HPATTERN, TUPLE_HEREDITARY_DISEASE, TUPLE_PHYSIOLOGICAL_DISEASE, TUPLE_INFECTIOUS_DISEASE,
                TUPLE_INFECTIOUS_ORGANISM, TUPLE_STRAIN, TUPLE_PATIENT, TUPLE_MEDICAL_RECORD, TUPLE_SUFFERS_FROM, TUPLE_WORK_ON,
                TUPLE_DIAGNOSTIC_TEST, TUPLE_DIAGNOSED_BY, TUPLE_TREATMENT, TUPLE_CLINICAL_TRIAL, TUPLE_CLINICAL_TRIAL_TREATMENT,
                TUPLE_DISEASE_CLINICAL_TRIAL
        };

        for (String[] table: tables) {
            populateTableHelper(connection, table);
        }
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
