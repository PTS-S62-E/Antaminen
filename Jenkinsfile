pipeline {
  agent any
  stages {
    stage('Build') {
        steps {
          withMaven(
            maven: 'Maven 3.5.3',
            mavenSettingsConfig: 'maven_artifactory'
          ) {
                sh 'mvn clean install package -P development'
                input message: 'Please check if build has succeeded'
          }
      }
    }
    stage ('Test') {
        steps {
            withMaven(
                maven: 'Maven 3.5.3',
                mavenSettingsConfig: 'maven_artifactory'
            ) {
                sh 'mvn test -P development'
                input message: 'Please check if test have executed successfully'
            }
        }
    }
    stage ('Analyze') {
        steps {
            withMaven(
                maven: 'Maven 3.5.3',
                mavenSettingsConfig: 'maven_artifactory'
            ) {
                sh 'mvn sonar:sonar -Dsonar.host.url=http://85.144.215.28:9001 -Dsonar.login=089ecbe71f30a12f9af77098b09921b83cf88786'
                input message: 'Please check the Analyze report in Sonarqube'
            }
        }
    }
    stage ('Deploy to Artifactory') {
              steps {
                              script {
                                  git url: 'https://github.com/PTS-S62-E/Antaminen.git'

                                  def server = Artifactory.server 'Artifactory'
                                  def buildInfo = Artifactory.newBuildInfo()
                                  def rtMaven = Artifactory.newMavenBuild()
                                  rtMaven.tool = 'Maven 3.5.3'

                                  rtMaven.deployer releaseRepo:'libs-release-local', snapshotRepo:'libs-snapshot-local', server: server
                                  rtMaven.resolver releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot', server: server

                                  rtMaven.deployer.deployArtifacts = false
                                  buildInfo.env.capture = true

                                  rtMaven.run pom: 'Antaminen/pom.xml', goals: 'clean install -DskipTests=true -P development', buildInfo: buildInfo
                                  rtMaven.deployer.deployArtifacts buildInfo

                                  server.publishBuildInfo buildInfo
                              }
              }
    }
    stage ('Deploy to Wildfly') {
        steps {
            withMaven(
                maven: 'Maven 3.5.3',
                mavenSettingsConfig: 'maven_artifactory'
            ) {
                sh 'mvn wildfly:deploy -Dwildfly.address=192.168.24.100 -Dwildfly.hostname=192.168.24.100 -Dwildfly.port=9990 -Dwildfly.username=admin -Dwildfly.password=proftaak'
                input message: 'Please check if the application is deployed successfully'
            }
        }
    }
  }
}