package com.adventium.magasin;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.RepositoryConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * Created by jlesaux on 04/07/17.
 * Axione {PACKAGE_NAME} for {PROJECT_NAME}
 * File ${FILE}
 */
@ActiveProfiles(profiles = "test")
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = { AbstractBaseJpaTest.LocalConfiguration.class,
    RepositoryConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EntityScan("com.adventium.magasin")
@EnableJpaRepositories("com.adventium.magasin")
public abstract class AbstractBaseJpaTest {

    @Configuration
    @ComponentScan({
        "com.adventium.magasin.domain",
        "com.adventium.magasin.repository",
        "com.adventium.magasin.config"
    })
    static class LocalConfiguration {
    }

    @Autowired
    private DataSource dataSource;

    @BeforeAll
    public void setUp() throws Exception {
        LiquibaseUtils.initLiquibase(getContexts(), dataSource);
    }

    @AfterAll
    public void tearDown() throws Exception {
        LiquibaseUtils.resetLiquibase(dataSource);
    }

    /**
     *
     * @return the contexts to execute
     */
    public abstract String getContexts();
}

