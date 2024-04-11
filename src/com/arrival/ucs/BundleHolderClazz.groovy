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
        def projectsMap = Eval.me(bundlesProjectsText)

        projectsMap.each { projectName, bundleStrings ->
            List<ProjectClazz> projectsList = []

            bundleStrings.each { bundleString ->
                def parts = bundleString.split(/\s+/)
                String component = parts[0]
                String version = parts.length > 1 ? parts[1] : "unknown"
                String commit = parts.length > 2 ? parts[2] : 'now'

                projectsList.add(new ProjectClazz(this.pipeline, component, version, commit))
            }

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