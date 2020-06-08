package nl.feniqs.qwik.web;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("nl.feniqs.qwik.web");

        noClasses()
            .that()
                .resideInAnyPackage("nl.feniqs.qwik.web.service..")
            .or()
                .resideInAnyPackage("nl.feniqs.qwik.web.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..nl.feniqs.qwik.web.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
