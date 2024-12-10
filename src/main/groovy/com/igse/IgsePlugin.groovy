package com.igse

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.testing.Test

class IgsePlugin implements Plugin<Project> {
    String IGSE_TEST = "igse-test"
    static final String MSG = "Igse plugin begin-" + UUID.randomUUID().toString().substring(0, 3)

    @Override
    void apply(Project project) {
        println(MSG)
        addStubRunner(project)
        addTestingTask(project)
    }

    void addStubRunner(Project project) {
        project.tasks.register('stubRun', JavaExec) {
            group = IGSE_TEST
        }
    }

    void addTestingTask(Project project) {
        project.plugins.withType(JavaPlugin) {

            project.test {
                include '**/unit/**'
                useJUnitPlatform()
            }

            project.tasks.register('componentTest', Test.class) {
                group = IGSE_TEST
                mustRunAfter:
                project.test
                include '**/component/**'
                exclude '**/unit/**'
                useJUnitPlatform()
            }
            project.tasks.register('blackboxTest', Test.class) {
                group = IGSE_TEST
                mustRunAfter:
                project.componentTest
                include '**/blackbox/**'
                exclude '**/unit/**'
                // useJUnitPlatform()
                testLogging.showStandardStreams = true
            }
            //project.componentTest.mustRunAfter project.test
            project.check.dependsOn project.componentTest
            project.check.dependsOn project.blackboxTest
//            project.build.dependsOn project.blackboxTest
            project.tasks.withType(Test) {
                reports.html.destination = project.file("${project.reporting.baseDir}/${name}")
                testLogging {
                    events "passed", "skipped", "failed", "standardError"
                    exceptionFormat = 'full'
                }
            }
        }

    }
}
