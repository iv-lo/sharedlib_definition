package com.arrival.ucs

import com.arrival.common.ParentClazz

class ProjectClazz extends ParentClazz {
    String project
    String version
    String commit

    ProjectClazz(def pipeline, String project, String version) {
        super(pipeline)
        this.project = project
        this.version = version
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