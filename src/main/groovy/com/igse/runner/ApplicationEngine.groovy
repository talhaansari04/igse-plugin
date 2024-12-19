package com.igse.runner

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

class ApplicationEngine implements Plugin<Project> {

    private static final String SPRING_BOOT_DEPENDENCY_MGMT_PLUGIN = "io.spring.dependency-management"
    private static final String SPRING_BOOT_DEPENDENCY_PLUGIN = "org.springframework.boot"
    private static final String CODE_COVERAGE_PLUGIN = "jacoco"


    @Override
    void apply(Project project) {
        project.plugins.apply(JavaPlugin)
        springPlugins(project)
    }

    void springPlugins(Project project) {
        project.plugins.apply(SPRING_BOOT_DEPENDENCY_MGMT_PLUGIN,)
        project.plugins.apply(SPRING_BOOT_DEPENDENCY_PLUGIN)
        project.plugins.apply(CODE_COVERAGE_PLUGIN)
    }

}
