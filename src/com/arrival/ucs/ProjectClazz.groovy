package com.arrival.ucs

import com.arrival.common.ParentClazz

class ProjectClazz extends ParentClazz {
    String project
    String version
    String commit
    // , String project, String version
    ProjectClazz(def pipeline, String project, String version, String commit) {
        super(pipeline)
        this.project = project
        this.version = version
        this.commit = commit
    }

    // ProjectClazz createProject(String project, String version, String commit) {
    //     ProjectClazz projectObj = ProjectClazz(this)
    //     projectObj.project = project
    //     projectObj.version = version
    //     projectObj.commit = commit
    //     return projectObj
    // }

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