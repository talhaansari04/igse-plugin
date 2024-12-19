package com.igse.runner

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.testing.Test

class TestEngine implements Plugin<Project> {
    String IGSE_TEST = "igse-test"

    @Override
    void apply(Project project) {
        addTestingTask(project)
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
