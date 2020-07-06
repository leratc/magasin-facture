package com.adventium.magasin;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.adventium.magasin");

        noClasses()
            .that()
            .resideInAnyPackage("com.adventium.magasin.service..")
            .or()
            .resideInAnyPackage("com.adventium.magasin.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.adventium.magasin.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
