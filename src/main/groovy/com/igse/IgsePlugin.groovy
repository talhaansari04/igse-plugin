package com.igse

import com.igse.runner.ApplicationEngine
import com.igse.runner.TestEngine
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.testing.Test

class IgsePlugin implements Plugin<Project> {
    String IGSE_STUB = "igse-stub"
    static String name
    static final String MSG = "Igse plugin begin-" + getUniqueName()

    static String getUniqueName() {
        if (name == null) {
            name = "NewBuild: " + UUID.randomUUID().toString().substring(0, 3)
        }
        return name
    }

    @Override
    void apply(Project project) {
        println(MSG)
        project.plugins.apply(ApplicationEngine)
        project.plugins.apply(TestEngine)
        addStubRunner(project)
    }

    void addStubRunner(Project project) {
        project.tasks.register('stubRun', JavaExec) {
            group = IGSE_STUB
            classpath project.sourceSets.test.runtimeClasspath
            mainClass = project.hasProperty('StubApplication') ? project.getProperty('StubApplication') : 'StubApplication'
            dependsOn('testClasses')
        }
    }


}
