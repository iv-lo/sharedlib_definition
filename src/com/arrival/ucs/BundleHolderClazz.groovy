package com.arrival.ucs

import com.arrival.common.ParentClazz
import groovy.json.JsonSlurper


class BundleHolderClazz extends ParentClazz {
    protected Map<String, List<Project>> _bundles = [:]
    String initBundles
    String defaultVehicle
   
    BundleHolderClazz(def pipeline) {
        super(pipeline)
    }

    void addBundle(String projectName, Project bundle) {
        if (!_bundles.containsKey(projectName)) {
            _bundles[projectName] = []
        }
        _bundles[projectName].add(bundle)
    }

    // String toJsonString() {
    //     def bundleStrings = _bundles.collect { projectName, bundleList ->
    //         def projectStrings = bundleList.collect { project ->
    //             project.toJsonString()
    //         }.join(',\n    ')
            
    //         return "\"${projectName}\": [\n    ${projectStrings}\n]"
    //     }.join(",\n") 

    //     return "{\n${bundleStrings}\n}"
    // }
    Map toMapString() {
        return _bundles.collectEntries { vehicleName, projectList ->
            [vehicleName, projectList.collect { project ->
                project.toMapString()
            }]
        }
    }

    // void initializeFromString(String bundlesProjectsText) {
    //     _bundles.clear()
    //     def jsonSlurper = new JsonSlurper()
    //     def projectsMap = jsonSlurper.parseText(bundlesProjectsText)

    //     projectsMap.each { projectName, bundlesList ->
    //         bundlesList.each { bundleInfo ->
    //             String component = bundleInfo['Component']
    //             String version = bundleInfo['Version']
    //             String commit = bundleInfo['Commit'] ?: 'now' 
                
    //             this.addBundle(projectName, new ProjectClazz(this.pipeline, component, version, commit))
    //         }
    //     }
    // }
    void initializeFromString(String bundlesProjectsText) {
        _bundles.clear()
        def jsonSlurper = new JsonSlurper()
        def projectsMap = jsonSlurper.parseText(bundlesProjectsText)

        projectsMap.each { projectName, bundleStrings ->
            // Preparing a list to hold Project instances for the current projectName
            List<Project> projectsList = []

            bundleStrings.each { bundleString ->
                // Splitting the string to extract component, version, and commit
                def parts = bundleString.split(/\s+/)
                String component = parts[0]
                String version = parts.length > 1 ? parts[1] : "unknown" // Default to "unknown" if version is not specified
                String commit = parts.length > 2 ? parts[2] : 'now' // Default to 'now' if commit is not specified

                // Creating a new Project instance and adding it to the projectsList
                projectsList.add(new Project(this.pipeline, component, version, commit))
            }

            // Adding the projectsList to the _bundles map for the current projectName
            _bundles[projectName] = projectsList
        }
    }

    List<Map<String, String>> getBundleProjects(String key) {
        return _bundles.getOrDefault(key, [])
    }

    Set<String> getBundleNames() {
        return new ArrayList<>(_bundles.keySet())
    }

    List<String> getVehicleList() {
        List<String> result = this._bundles.keySet() as List<String>
        result -= this.defaultVehicle
        return [this.defaultVehicle] + result
    }

}