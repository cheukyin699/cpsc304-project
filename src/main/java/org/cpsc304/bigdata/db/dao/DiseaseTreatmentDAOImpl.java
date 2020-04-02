package org.cpsc304.bigdata.db.dao;

public class DiseaseTreatmentDAOImpl {

    @Autowired
    private DatabaseConnectionHandler handler;
    private Logger logger = LoggerFactory.getLogger(DiseaseTreatmentDAOImpl.class);


    @override
    public void add(final Disease disease, final Treatment treatment){
        final Connection connection = handler.getConnection();
        final String q1 = "INSERT INTO Disease_ClinicalTrial VALUES (?, ?)";
        final String q2 = "INSERT INTO Disease VALUES (?, ?, ?)";
        final String q3 = "INSERT INTO Treatment VALUES (?, ?, ?, ?)";
        try {
            // Treatment
            PreparedStatement statement = connection.prepareStatement(q3);
            statement.setString(1, treatment.getTreatmentName());
            statement.setFloat(2, treatment.getEfficiency());
            statement.setString(3, treatment.getEquipment());
            statement.setString(4, treatment.getRisks());
            statement.execute();
            // Disease
            statement = connection.prepareStatement(q2);
            statement.setString(1, disease.getName());
            statement.setString(2, disease.getPrevalence());
            statement.setInt(3, disease.getSymptoms());
            statement.execute();
            // Disease_Treatment
            statement = connection.prepareStatement(q1);
            statement.setString(1, disease.getTrialName());
            statement.setString(2, treatment.getTreatmentName());
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }


    @Override
    public Disease findDiseaseByName(final String diseaseName) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM Disease_Treatment NATURAL JOIN Disease WHERE DiseaseName = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, diseaseName);
            final ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Disease(
                        diseaseName,
                        set.getInt("Prevalence"),
                        set.getString("Symptoms")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public Treatment findTreatmentByName(final String treatmentName) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM Disease_Treatment NATURAL JOIN Treatment WHERE treatmentName = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, treatmentName);
            final ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Treatment(
                        treatmentName,
                        set.getFloat("Efficiency"),
                        set.getString("Equipment"),
                        set.getString("Risks")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }
}
