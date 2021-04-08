package com.iledeslegendes.charactersheet;

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
            .importPackages("com.iledeslegendes.charactersheet");

        noClasses()
            .that()
            .resideInAnyPackage("com.iledeslegendes.charactersheet.service..")
            .or()
            .resideInAnyPackage("com.iledeslegendes.charactersheet.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.iledeslegendes.charactersheet.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
