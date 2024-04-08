package com.arrival.ucs


class ProjectClazz {
    String project
    String version
    String commit

    Project(String project, String version) {
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