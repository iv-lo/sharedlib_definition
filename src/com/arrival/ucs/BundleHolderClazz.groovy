package com.arrival.ucs

import com.arrival.common.ParentClazz
import groovy.json.JsonSlurper


class BundleHolderClazz extends ParentClazz {
    protected Map<String, List<ProjectClazz>> _bundles = [:]
    String initBundles = ""
   
    BundleHolderClazz(def pipeline) {
        super(pipeline)
    }


    void addBundle(String projectName, ProjectClazz bundle) {
        if (!_bundles.containsKey(projectName)) {
            _bundles[projectName] = []
        }
        _bundles[projectName].add(bundle)
    }

    String toJsonString() {
        def bundleStrings = _bundles.collect { projectName, bundleList ->
            def bundleStrings = bundleList.collect { bundle ->
                "${bundle.toJsonString()}"
            }.join(',\n    ')
            
            "\"${projectName}\": [\n    ${bundleStrings}\n]"
        }.join(",\n") 

        return "{\n${bundleStrings}\n}"
    }


    void initializeFromString(String bundlesProjectsText) {
        _bundles.clear()
        def jsonSlurper = new JsonSlurper()
        def projectsMap = jsonSlurper.parseText(bundlesProjectsText)
        println(projectsMap)
        // projectsMap.each { projectName, bundlesList ->
        //     bundlesList.each { Map bundleInfo ->
        //         bundleInfo.each { projectPath, version ->
        //             this.addBundle(projectName, new ProjectClazz(this.pipeline, projectPath, version, "now")) //projectPath, version
        //         }
        //     }
        // }
    }

    List<Map<String, String>> getBundleProjects(String key) {
        return _bundles.getOrDefault(key, [])
    }

}