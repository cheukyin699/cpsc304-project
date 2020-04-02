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
            "InfectiousDisease", "PhysiologicalDisease",
            "Researcher", "Physician", "User_Info", "User_Dept",
            "HereditaryDisease", "HPattern", "DeficiencyDisease"

    };
    private static final String[] CREATE_TABLES = {
            "CREATE TABLE User_Dept (" +
                    "Speciality VARCHAR2(20) PRIMARY KEY," +
                    "Department VARCHAR2(20)" +
                    ")",
            "CREATE TABLE User_Info (" +
                    "Username VARCHAR2(30) PRIMARY KEY," +
                    "Name VARCHAR2(50)," +
                    "Password VARCHAR2(30) NOT NULL," +
                    "Speciality VARCHAR2(30)," +
                    "FOREIGN KEY (Speciality) REFERENCES User_Dept(Speciality)" +
                    "ON DELETE SET NULL" +
                    ")",
            "CREATE TABLE Physician (" +
                    "Username VARCHAR2(30) PRIMARY KEY," +
                    "Hospital VARCHAR2(30)," +
                    "FOREIGN KEY (Username) REFERENCES User_Info(Username)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE Researcher (" +
                    "Username VARCHAR2(15) PRIMARY KEY," +
                    "Institute VARCHAR2(30)," +
                    "NumOfPublications INTEGER," +
                    "FOREIGN KEY (Username) REFERENCES User_Info(Username)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE ClinicalTrial (" +
                    "TrialName VARCHAR2(200) PRIMARY KEY," +
                    "Type VARCHAR2(200)," +
                    "Num_Participants INTEGER," +
                    "IsComplete NUMBER(3, 0)," +
                    "CHECK (IsComplete >= 0 AND IsComplete <= 1)" +
                    ")",
            "CREATE TABLE Disease (" +
                    "Name VARCHAR2(30) PRIMARY KEY, " +
                    "Prevalence NUMBER(3, 0), " +
                    "Symptoms VARCHAR2(100), " +
                    "CHECK (Prevalence <= 100 AND Prevalence >= 0)" +
                    ")",
            "CREATE TABLE Disease_ClinicalTrial (" +
                    "DName VARCHAR2(50)," +
                    "CTName VARCHAR2 (200), " +
                    "PRIMARY KEY (DName, CTName), " +
                    "FOREIGN KEY (DName) REFERENCES Disease(Name), " +
                    "FOREIGN KEY (CTName) REFERENCES ClinicalTrial(TrialName)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE PhysiologicalDisease (" +
                    "Name VARCHAR2(30) PRIMARY KEY," +
                    "AssociatedArea VARCHAR2(30)," +
                    "FOREIGN KEY (Name) REFERENCES Disease(Name)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE InfectiousDisease (" +
                    "Name VARCHAR2(30) PRIMARY KEY," +
                    "TransmissionRoute VARCHAR2(15)," +
                    "FOREIGN KEY (Name) REFERENCES Disease(Name)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE InfectiousOrganism (" +
                    "FamilyName VARCHAR2(50) PRIMARY KEY," +
                    "ID_Name VARCHAR2(50) UNIQUE," +
                    "CommonName VARCHAR2(50)," +
                    "FOREIGN KEY (ID_Name) REFERENCES InfectiousDisease(Name)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE Strain (" +
                    "StrainName VARCHAR2(20)," +
                    "FamilyName VARCHAR2(20)," +
                    "Acronym VARCHAR2(20)," +
                    "PRIMARY KEY (StrainName, FamilyName)," +
                    "FOREIGN KEY (FamilyName) REFERENCES InfectiousOrganism(FamilyName)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE Patient (" +
                    "ID VARCHAR2(20) PRIMARY KEY," +
                    "Name VARCHAR2(20)," +
                    "Family_History VARCHAR2(60)," +
                    "Age NUMBER(3, 0)," +
                    "Sex NUMBER(3, 0)," +
                    "P_Username VARCHAR2(20) NOT NULL," +
                    "FOREIGN KEY (P_Username) REFERENCES Physician(Username)," +
                    "CHECK (Sex <= 1 AND Sex >= 0)" +
                    ")",
            "CREATE TABLE MedicalRecord (" +
                    "PatientID VARCHAR2(20)," +
                    "Start_date DATE," +
                    "End_date DATE," +
                    "Disease VARCHAR2(100)," +
                    "Implants_Surgeries VARCHAR2(50)," +
                    "Allergies VARCHAR2(50)," +
                    "Medication VARCHAR2(50)," +
                    "PRIMARY KEY (PatientID, Start_date, Disease)," +
                    "FOREIGN KEY (PatientID) REFERENCES Patient(ID)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE SuffersFrom (" +
                    "P_ID VARCHAR2(20)," +
                    "D_Name VARCHAR2(100)," +
                    "Duration NUMBER(3, 0)," +
                    "PRIMARY KEY (P_ID, D_Name)," +
                    "FOREIGN KEY (P_ID) REFERENCES Patient(ID)" +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY (D_Name) REFERENCES Disease(Name)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE Treatment (" +
                    "TreatmentName VARCHAR2(100) PRIMARY KEY," +
                    "Efficiency FLOAT," +
                    "Equipment VARCHAR2(100)," +
                    "Risks VARCHAR2(100)" +
                    ")",
            "CREATE TABLE ClinicalTrial_Treatment (" +
                    "CTName VARCHAR2(200)," +
                    "TName VARCHAR2(50)," +
                    "PRIMARY KEY(CTName, TName)," +
                    "FOREIGN KEY(CTName) REFERENCES ClinicalTrial(TrialName)" +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY(TName) REFERENCES Treatment(TreatmentName)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE WorkOn (" +
                    "R_Username VARCHAR2(20)," +
                    "CT_Name VARCHAR2(200)," +
                    "PRIMARY KEY (R_Username, CT_Name)," +
                    "FOREIGN KEY (R_Username) REFERENCES Researcher(Username)" +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY (CT_Name) REFERENCES ClinicalTrial(TrialName)" +
                    "ON DELETE CASCADE" +
                    ")",
            "CREATE TABLE DiagnosticTest (" +
                    "DT_Name VARCHAR2(50) PRIMARY KEY," +
                    "Accuracy FLOAT" +
                    ")",
            "CREATE TABLE DiagnosedBy (" +
                    "DT_Name VARCHAR2(50)," +
                    "D_Name VARCHAR2(30)," +
                    "Target VARCHAR2(20)," +
                    "PRIMARY KEY (DT_Name, D_Name)," +
                    "FOREIGN KEY (DT_Name) REFERENCES DiagnosticTest(DT_Name)" +
                    "ON DELETE CASCADE," +
                    "FOREIGN KEY (D_Name) REFERENCES Disease(Name)" +
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
    };

    private static final String[] TUPLE_USER_INFO = {
            "INSERT INTO User_Info VALUES('aheale', 'Andrew Heale', '000', 'Human Physiology')",
            "INSERT INTO User_Info VALUES('hbtaussig', 'Helen Brooke Taussig', '123456789', 'Molecular Oncology')",
            "INSERT INTO User_Info VALUES('zjanzekovic', 'Zora Janzekovic', '111111111', 'Molecular Oncology')",
            "INSERT INTO User_Info VALUES('vapgar','Virginia Apgar', 'qwerty', 'Nephrology')",
            "INSERT INTO User_Info VALUES('jhopps', 'John Hopkins', '11dtg', 'Molecular Virology')",
            "INSERT INTO User_Info VALUES('vmckusick', 'Victor Mckusick', 'typewriter', 'Cardiology')",
            "INSERT INTO User_Info VALUES('czhou', 'Carl Zhou', 'thisismypassword', 'Molecular Oncology')",
            "INSERT INTO User_Info VALUES('sdudrick', 'Samuel Dudrick', 'zmakqo10', 'Cardiology')",
            "INSERT INTO User_Info VALUES('bwane', 'Brodie Wane', 'icedcoffee', 'Molecular Oncology')",
            "INSERT INTO User_Info VALUES('jchou', 'Jamie Chou', '54321', 'Molecular Virology')"

    };

    private static final String[] TUPLE_USER_DEPT = {
            "INSERT INTO User_Dept VALUES('Molecular Oncology', 'Cancer Studies')",
            "INSERT INTO User_Dept VALUES('Nephrology', 'Physiology')",
            "INSERT INTO User_Dept VALUES('Human Physiology', 'Physiology')",
            "INSERT INTO User_Dept VALUES('Molecular Virology', 'Immunology')",
            "INSERT INTO User_Dept VALUES('Cardiology', 'Immunology')"
};

    private static final String[] TUPLE_PHYSICIAN = {
            "INSERT INTO Physician VALUES('hbtaussig', 'John Hopkins Hospital')",
            "INSERT INTO Physician VALUES('zjanzekovic', 'General Hospital Maribor')",
            "INSERT INTO Physician VALUES('vmckusick', 'General Hospital Maribor')",
            "INSERT INTO Physician VALUES('czhou', 'John Hopkins Hospital')",
            "INSERT INTO Physician VALUES('sdudrick', 'Saint Mary’s Hospital')"

    };

    private static final String[] TUPLE_RESEARCHER = {
            "INSERT INTO Researcher VALUES('jhopps', 'Johns Hopkins Hospital', 1)",
            "INSERT INTO Researcher VALUES('vapgar', 'University of British Columbia', 1)",
            "INSERT INTO Researcher VALUES('aheale', 'University of Washington', 2)",
            "INSERT INTO Researcher VALUES('bwane', 'BC Cancer Agency', 2)",
            "INSERT INTO Researcher VALUES('jchou', 'Stemcell Technologies', 42)",

    };

    private static final String[] TUPLE_DISEASE = {
            "INSERT INTO Disease VALUES('Glaucoma', '9', 'Blind spots in sides or central vision in both eyes')",
            "INSERT INTO Disease VALUES('Avian influenza', '25', 'dry cough, fever, sore throat, headache, runny nose')",
            "INSERT INTO Disease VALUES('AIDS', '3', 'Swollen lymph nodes, fever, fatigue, rash')",
            "INSERT INTO Disease VALUES('Diabetes', '12', 'Thirst, hunger, frequent urination, weight loss')",
            "INSERT INTO Disease VALUES('Asthma', '7', 'Tight chest, short breath, wheezing')",
            "INSERT INTO Disease VALUES('Dengue fever', '5', 'High fever, headache, muscle pain')",
            "INSERT INTO Disease VALUES('Iron deficiency', '15', 'Unusual tiredness paleness shortness of breath')",
            "INSERT INTO Disease VALUES('Kidney disease', '7', 'Fatigue, dry itchy skin, insomnia, bloody or foamy urine')",
            "INSERT INTO Disease VALUES('Elephantiasis', '1', 'Enlargement of body, limbs, genitals - accumulation of fluid')",
            "INSERT INTO Disease VALUES('Follicular lymphoma', '3', 'Swollen lymph nodes, fatigues, shortness of breath, weight loss')",
            "INSERT INTO Disease VALUES('Cholera', '3', 'Excessive diarrhea, vomiting, low blood pressure, rapid heart rate')",
            "INSERT INTO Disease VALUES('Urinary tract infection', '12', 'Burning urination, abnormal urine, fatigue, fever, back or abdominal pain')",
            "INSERT INTO Disease VALUES('Scurvy', '4', 'Anemia, bleeding, exhaustion')",
            "INSERT INTO Disease VALUES('Pellagra', '3', 'Diarrhea, abdominal pain, weakness')",
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
            "INSERT INTO PhysiologicalDisease VALUES ('Asthma', 'Respiratory tract')",
            "INSERT INTO PhysiologicalDisease VALUES ('Elephantiasis', 'Limbs and genitals')",
            "INSERT INTO PhysiologicalDisease VALUES ('Follicular lymphoma', 'Lymphocytes')"

    };

    private static final String[] TUPLE_INFECTIOUS_DISEASE = {
            "INSERT INTO InfectiousDisease VALUES ('Avian influenza', 'Fecal-oral')",
            "INSERT INTO InfectiousDisease VALUES ('AIDS', 'Blood fluid')",
            "INSERT INTO InfectiousDisease VALUES ('Dengue fever', 'Fecal-oral')",
            "INSERT INTO InfectiousDisease VALUES ('Cholera', 'Fecal-oral')",
            "INSERT INTO InfectiousDisease VALUES ('Urinary tract infection', 'Fecal-urinary')",

    };

    private static final String[] TUPLE_INFECTIOUS_ORGANISM = {
            "INSERT INTO InfectiousOrganism VALUES('Retroviridae', 'AIDS', 'HIV')",
            "INSERT INTO InfectiousOrganism VALUES('Flaviviridae', 'Dengue fever', 'Dengue virus')",
            "INSERT INTO InfectiousOrganism VALUES('Orthomyxoviridae', 'Avian influenza', 'Flu')",
            "INSERT INTO InfectiousOrganism VALUES('Enterobacteriaceae', 'Urinary tract infection', NULL)",
            "INSERT INTO InfectiousOrganism VALUES('Vibrionaceae', 'Cholera', NULL)",
    };

    private static final String[] TUPLE_STRAIN = {
            "INSERT INTO Strain VALUES('01','Orthomyxoviridae', 'Influenza A H1N1')",
            "INSERT INTO Strain VALUES('05','Orthomyxoviridae', 'Influenza A H3N2')",
            "INSERT INTO Strain VALUES('O157:H7','Enterobacteriaceae', 'E.coli')",
            "INSERT INTO Strain VALUES('V1A','Orthomyxoviridae', 'Influenza B V1A')",
            "INSERT INTO Strain VALUES('O139','Vibrionaceae', NULL)"
    };

    private static final String[] TUPLE_PATIENT = {
            "INSERT INTO Patient VALUES('1', 'John Smith'," +
                    " 'Mother: Meniere’s, Grandmother: Cardiac arrest', '31', '0', 'hbtaussig')",
            "INSERT INTO Patient VALUES('2', 'Bob Baker', NULL, '20', '0', 'zjanzekovic')",
            "INSERT INTO Patient VALUES('3', 'Ann Brown'," +
                    " 'Father: cystic fibrosis, Brother: follicular lymphoma', '53', '0', 'czhou')",
            "INSERT INTO Patient VALUES('4', 'Julian Park', 'Aunt: Parkinsons', '19', '0', 'sdudrick')",
            "INSERT INTO Patient VALUES('5', 'Chris Lim', 'Uncle: melanoma, Grandfather: Alzheimers', '38', '0', 'czhou')",
    };

    private static final String[] TUPLE_MEDICAL_RECORD = {
            "INSERT INTO MedicalRecord VALUES('1', date '1999-04-23', date '1999-05-08', 'Pneumonia', NULL, 'tetracycline, tree nuts'," +
                    "'Ciprofloxacin')",
            "INSERT INTO MedicalRecord VALUES('2', date '2012-11-01', date '2012-11-08', 'Glaucoma', 'Argon laser trabeculoplasty'," +
                    " 'penicillin', 'morphine')",
            "INSERT INTO MedicalRecord VALUES('2', date '2015-09-04', date '2015-09-15', 'Cardiac arrest', NULL," +
                    " NULL, 'morphine')",
            "INSERT INTO MedicalRecord VALUES('3', date '2017-12-12', date '2017-12-30', 'Kidney disease', 'dialysis, kidney transplant'," +
                    " NULL, 'chloramphenicol')",
            "INSERT INTO MedicalRecord VALUES('4', date '2014-04-16', date '2014-04-17', 'Metacarpal fracture', 'cast'," +
                    " 'peanuts', NULL)",
    };

    private static final String[] TUPLE_SUFFERS_FROM = {
            "INSERT INTO SuffersFrom VALUES('1', 'Iron deficiency', 496)",
            "INSERT INTO SuffersFrom VALUES('1', 'Avian influenza', 20)",
            "INSERT INTO SuffersFrom VALUES('2', 'Glaucoma', 670)",
            "INSERT INTO SuffersFrom VALUES('3', 'Urinary tract infection', 9)",
            "INSERT INTO SuffersFrom VALUES('5', 'Avian influenza', 5)"

    };

    private static final String[] TUPLE_WORK_ON = {
            "INSERT INTO WorkOn VALUES('jhopps', 'Alpelisib SOLAR-1 Phase 3')",
            "INSERT INTO WorkOn VALUES('vapgar', 'Cerebral Blood Flow During Hemodialysis')",
            "INSERT INTO WorkOn VALUES('aheale', 'Lactoferrin versus Ferrous Sulfate in Iron-deficiency During Pregnancy')",
            "INSERT INTO WorkOn VALUES('vapgar', 'Pentoxifylline in Diabetic Kidney Disease')",
            "INSERT INTO WorkOn VALUES('jhopps', 'Safety, Tolerability, Efficacy and Immunogenicity of an " +
                    "Influenza A Vaccine (FP-01.1) in Healthy Volunteers Following Virus Challenge')",
    };

    private static final String[] TUPLE_DIAGNOSTIC_TEST = {
            "INSERT INTO DiagnosticTest VALUES('HIV1/HIV2 Immunoassay', 0.9)",
            "INSERT INTO DiagnosticTest VALUES('Serum iron test', 0.63)",
            "INSERT INTO DiagnosticTest VALUES('Heidelberg Retinal Tomography', 0.69)",
            "INSERT INTO DiagnosticTest VALUES('Real time RT-PCR assay', 0.95)",
            "INSERT INTO DiagnosticTest VALUES('Transferrin', 0.8)"
    };

    private static final String[] TUPLE_DIAGNOSED_BY = {
            "INSERT INTO DiagnosedBy VALUES('HIV1/HIV2 Immunoassay', 'AIDS', 'HIV Antigen/Antibody')",
            "INSERT INTO DiagnosedBy VALUES('Serum iron test', 'Iron deficiency', 'serum iron')",
            "INSERT INTO DiagnosedBy VALUES('Transferrin', 'Iron deficiency', 'Transferrin protein')",
            "INSERT INTO DiagnosedBy VALUES('Heidelberg Retinal Tomography', 'Glaucoma', 'Optic nerve head')",
            "INSERT INTO DiagnosedBy VALUES('Real time RT-PCR assay', 'AIDS', 'HIV DNA')"

    };

    private static final String[] TUPLE_TREATMENT = {
            "INSERT INTO Treatment VALUES('Hemodialysis', 0.76, 'Dialysis machine', " +
                    "'hypotension, anemia, blood clot, infection')",
            "INSERT INTO Treatment VALUES('Alpelisib', 0.67, NULL, 'severe hypersensitivity, anaphylaxis, anaphylactic shock')",
            "INSERT INTO Treatment VALUES('Implantable cardioverter defibrillator', 0.85, 'surgery room, pacemaker', " +
                    "'infection, allergic reaction, bleeding')",
            "INSERT INTO Treatment VALUES('Ferrous sulfate', 0.64, NULL, " +
                    "'constipation, diarrhea, GI irritation')",
            "INSERT INTO Treatment VALUES('Pentoxifylline', 0.71, NULL, 'nausea, bloating, diarrhea, dizziness')",
            "INSERT INTO Treatment VALUES('Vaccine FP-01.1', 0.92, NULL, NULL)"

    };

    private static final String[] TUPLE_CLINICAL_TRIAL = {
            "INSERT INTO ClinicalTrial VALUES('Lactoferrin versus Ferrous Sulfate in Iron-deficiency During Pregnancy',"+
                    "'Randomized parallel assignment, no masking', '98', '1')",
            "INSERT INTO ClinicalTrial VALUES('Alpelisib SOLAR-1 Phase 3'," +
                    "'Phase III randomized double-blind, triple masking', + '18', '0')",
            "INSERT INTO ClinicalTrial VALUES('Cerebral Blood Flow During Hemodialysis'," +
                    " 'Single group assignment, no masking', '15', '1')",
            "INSERT INTO ClinicalTrial VALUES('Pentoxifylline in Diabetic Kidney Disease'," +
                    "'Phase IV Randomized parallel assignment double-blind, quadruple masking', + '2510', '0')",
            "INSERT INTO ClinicalTrial VALUES('Safety, Tolerability, Efficacy and Immunogenicity of an " +
                    "Influenza A Vaccine (FP-01.1) in Healthy Volunteers Following Virus Challenge'," +
                    "'Phase I, II randomized parallel assignment, quadruple masking', + '111', '1')",
    };

    private static final String[] TUPLE_CLINICAL_TRIAL_TREATMENT = {
            "INSERT INTO ClinicalTrial_Treatment VALUES('Alpelisib SOLAR-1 Phase 3', 'Alpelisib')",
            "INSERT INTO ClinicalTrial_Treatment VALUES('Cerebral Blood Flow During Hemodialysis', 'Hemodialysis')",
            "INSERT INTO ClinicalTrial_Treatment VALUES('Lactoferrin versus Ferrous Sulfate in Iron-deficiency During Pregnancy'," +
                    "'Ferrous sulfate')",
            "INSERT INTO ClinicalTrial_Treatment VALUES('Pentoxifylline in Diabetic Kidney Disease', 'Pentoxifylline')",
            "INSERT INTO ClinicalTrial_Treatment VALUES('Safety, Tolerability, Efficacy and Immunogenicity of an " +
                    "Influenza A Vaccine (FP-01.1) in Healthy Volunteers Following Virus Challenge', 'Vaccine FP-01.1')",
    };

    private static final String[] TUPLE_DISEASE_CLINICAL_TRIAL = {
            "INSERT INTO Disease_ClinicalTrial VALUES('Iron deficiency', 'Lactoferrin versus Ferrous Sulfate in Iron-deficiency During Pregnancy')",
            "INSERT INTO Disease_ClinicalTrial VALUES('AIDS', 'Alpelisib SOLAR-1 Phase 3')",
            "INSERT INTO Disease_ClinicalTrial VALUES('Kidney disease', 'Cerebral Blood Flow During Hemodialysis')",
            "INSERT INTO Disease_ClinicalTrial VALUES('Kidney disease', 'Pentoxifylline in Diabetic Kidney Disease')",
            "INSERT INTO Disease_ClinicalTrial VALUES('Avian influenza', 'Safety, Tolerability, Efficacy and Immunogenicity " +
                    "of an Influenza A Vaccine (FP-01.1) in Healthy Volunteers Following Virus Challenge')"
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
                TUPLE_USER_DEPT,TUPLE_USER_INFO, TUPLE_PHYSICIAN, TUPLE_RESEARCHER, TUPLE_DISEASE,
                TUPLE_PHYSIOLOGICAL_DISEASE, TUPLE_INFECTIOUS_DISEASE,
                TUPLE_INFECTIOUS_ORGANISM, TUPLE_STRAIN, TUPLE_PATIENT, TUPLE_MEDICAL_RECORD, TUPLE_SUFFERS_FROM,
                TUPLE_DIAGNOSTIC_TEST, TUPLE_DIAGNOSED_BY, TUPLE_TREATMENT, TUPLE_CLINICAL_TRIAL, TUPLE_CLINICAL_TRIAL_TREATMENT,
                TUPLE_DISEASE_CLINICAL_TRIAL, TUPLE_WORK_ON, TUPLE_DEFICIENCY_DISEASE,
                TUPLE_HPATTERN, TUPLE_HEREDITARY_DISEASE
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
