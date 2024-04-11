package com.arrival.ucs

import com.arrival.common.ParentClazz

class Project extends ParentClazz {
    String project
    String version
    String commit

    Project(def pipeline, String project, String version, String commit) {
        super(pipeline)
        this.project = project
        this.version = version
        this.commit = commit
    }

    // String toJsonString() {
    //     return "{\n" +
    //         "\t\"Component\": \"${this.project}\",\n" +
    //         "\t\"Version\": \"${this.version}\",\n" +
    //         "\t\"Commit\": \"${this.commit}\"\n" +
    //         "}"
    // }
    String toJsonString() {
        return "\'${this.project} ${this.version} ${this.commit}\'"
    }

    String toWorkspaceCfgLink(){
        return "LINK ${project} ${version}"
    }

    String getName(){
        return project.split('/')[-1]
    }
}