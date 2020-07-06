package com.adventium.magasin;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.core.H2Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Christophe
 *
 */
public final class LiquibaseUtils {

    /**
     * default private constructor
     */
    private LiquibaseUtils() {
        // do nothing
    }

    /**
     * Liquibase :: manual database initialization.
     *
     * @param context context
     * @param dataSource datasource
     * @throws LiquibaseException
     *             LiquibaseException
     * @throws SQLException
     *             SQLException
     */
    public static void initLiquibase(String context, DataSource dataSource) throws LiquibaseException, SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Database database = new H2Database();
            database.setConnection(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("/config/liquibase/master.xml", new ClassLoaderResourceAccessor(), database);
            String contexts = "legacy,not-legacy" + (context != null ? "," + context : "");
            liquibase.update(contexts);
            database.close();
        }
    }

    /**
     * Liquibase :: manual database cleanup.
     *
     * @param dataSource datasource
     * @throws SQLException SQLException
     */
    public static void resetLiquibase(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP ALL OBJECTS");
            }
        }
    }
}
