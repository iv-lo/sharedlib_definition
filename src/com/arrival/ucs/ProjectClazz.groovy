package com.arrival.ucs

import com.arrival.common.ParentClazz

class ProjectClazz extends ParentClazz {
    String project
    String version
    String commit
    // , String project, String version
    ProjectClazz(def pipeline) {
        super(pipeline)
    }

    ProjectClazz createProject(String project, String version, String commit) {
        ProjectClazz project = ProjectClazz(this)
        project.project = project
        project.version = version
        project.commit = commit
    }

    String toJsonString() {
        return "{\"${project}\": \"${version}\"}"
    }

    String toWorkspaceCfgLink(){
        return "LINK ${project} ${version}"
    }

    String getName(){
        return project.split('/')[-1]
    }
}