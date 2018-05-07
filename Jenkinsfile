node {
    def server
    def buildInfo
    def rtMaven

    stage ('Clone') {
        git url: 'https://github.com/PTS-S62-E/Antaminen.git'
    }

    stage ('Artifactory configuration') {
        // Obtain an Artifactory server instance, defined in Jenkins --> Manage:
        server = Artifactory.server 'Artifactory'

        rtMaven = Artifactory.newMavenBuild()
        rtMaven.tool = 'Maven 3.5.3' // Tool name from Jenkins configuration
        rtMaven.deployer releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local', server: server
        rtMaven.resolver releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot', server: server
        rtMaven.deployer.deployArtifacts = false // Disable artifacts deployment during Maven run

        buildInfo = Artifactory.newBuildInfo()
    }

    stage ('Test') {
        rtMaven.run pom: 'antaminen/pom.xml', goals: 'clean test -P development'
        input(
                id: 'testEnded', message: 'Did the tests pass?', parameters: [
                [$class: 'BooleanParameterDefinition', defaultValue: true, description: '', name: 'Please confirm you agree with this']
                ])
    }

    stage ('Build') {
        rtMaven.run pom: 'antaminen/pom.xml', goals: 'install -P development', buildInfo: buildInfo
        input(
                id: 'buildEnded', message: 'Did the build pass?', parameters: [
                [$class: 'BooleanParameterDefinition', defaultValue: true, description: '', name: 'Please confirm you agree with this']
                ])
    }

    stage ('Analyze') {
        rtMaven.run pom: 'antaminen/pom.xml', goals: 'sonar:sonar -Dsonar.host.url=http://85.144.215.28:9001 -Dsonar.login=089ecbe71f30a12f9af77098b09921b83cf88786'
        input(
                id: 'analyzeEnded', message: 'The the analyze stage finish?', parameters: [
                [$class: 'BooleanParameterDefinition', defaultValue: true, description: '', name: 'Please confirm you agree with this']
                ])
    }

    stage ('Deploy to Artifactory') {
        rtMaven.deployer.deployArtifacts buildInfo
    }

    stage ('Publish build info to Artifactory') {
        server.publishBuildInfo buildInfo
        input(
                id: 'artifactoryDeployEnded', message: 'Was the application deployed to Artifactory?', parameters: [
                [$class: 'BooleanParameterDefinition', defaultValue: true, description: '', name: 'Please confirm you agree with this']
                ])
    }

    stage ('Deploy to Wildfly') {
        rtMaven.run pom: 'antaminen/pom.xml', goals: 'wildfly:deploy -Dwildfly.address=192.168.24.100 -Dwildfly.hostname=192.168.24.100 -Dwildfly.port=9990 -Dwildfly.username=admin -Dwildfly.password=proftaak'
        input(
                id: 'wildflyDeployEnded', message: 'Is the application deployed to wildfly?', parameters: [
                [$class: 'BooleanParameterDefinition', defaultValue: true, description: '', name: 'Please confirm you agree with this']
                ])
    }
}