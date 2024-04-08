package com.arrival.ucs

import com.arrival.common.ParentClazz

class ProjectClazz extends ParentClazz {
    String project = 'project'
    String version = 'version'
    String commit = 'commit'
    // , String project, String version
    ProjectClazz(def pipeline) {
        super(pipeline)
        // this.project = project
        // this.version = version
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