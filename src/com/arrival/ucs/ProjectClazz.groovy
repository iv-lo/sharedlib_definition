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

    static ProjectClazz updateProject(String project, String version, String commit) {
        this.project = project
        this.version = version
        this.commit = commit
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