package com.igse

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.testing.Test

class IgsePlugin implements Plugin<Project> {
    String IGSE_TEST = "igse-test"

    @Override
    void apply(Project project) {
        println("Igse plugin begin")
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
            /*project.test{
     include 'unit/**'
     useJunit
 }*/
            project.tasks.register('componentTest', Test.class) {
                group = IGSE_TEST
                //mustRunAfter:project.test
                include "component/**"
                useJUnitPlatform()
            }
            project.tasks.register('blackBoxTest', Test.class) {
                group = IGSE_TEST
                //mustRunAfter:project.test
                include "blackBox/**"
                useJUnitPlatform()
            }
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
