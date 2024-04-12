package com.arrival.ucs

import com.arrival.common.ParentClazz

class BundleHolderClazz extends ParentClazz {
    protected Map<String, List<Project>> _bundles = [:]
    protected String _defaultVehicle
   
    BundleHolderClazz(def pipeline) {
        super(pipeline)
    }

    Map toMapString() {
        return _bundles.collectEntries { vehicleName, projectList ->
            [vehicleName, projectList.collect { project ->
                project.toMapString()
            }]
        }
    }

    void updateBundleProjects(String vehicleName, String projectsText) {
        _bundles[vehicleName] = []

        def projectStrings = projectsText.split("\\n")
        projectStrings.each { projectString ->
            if (!projectString.trim().isEmpty()) {
                def parts = projectString.split(/\s+/)

                String component = parts[0]
                String version = parts.length > 1 ? parts[1] : "main"
                String commit = parts.length > 2 ? parts[2] : 'now'

                _bundles[vehicleName].add(new Project(this.pipeline, component, version, commit))
            }
        }
    }

    List<Project> getBundleProjects(String key) {
        return _bundles.getOrDefault(key, [])
    }

    List<String> getVehicleList() {
        List<String> result = this._bundles.keySet() as List<String>
        result -= this._defaultVehicle
        return [this._defaultVehicle] + result
    }

}