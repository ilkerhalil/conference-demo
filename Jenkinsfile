env.label = "ci-pod-${UUID.randomUUID().toString()}"

pipeline{
    agent{
        kubernetes{
        label "${env.label}"
           yaml """
apiVersion: v1
kind: Pod
metadata:
spec:
  securityContext:
    fsGroup: 999
  containers:
  - name: openjdk
    image: registry.redhat.io/openshift3/jenkins-slave-maven-rhel7
    securityContext:
      privileged: true
    volumeMounts:
    - name: dockersock
      mountPath: /var/run/docker.sock
    - name: docker
      mountPath: /usr/bin/docker
    command:
    - cat
    tty : true
        """
        }

    }
    stages{
        stage("Checkout"){
          steps{
            checkout([$class: 'GitSCM',
      branches: scm.branches,
      doGenerateSubmoduleConfigurations: false,
      extensions: [[$class: 'CloneOption', noTags: false, reference: '', shallow: false],
      [$class: 'LocalBranch']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'jenkins-generated-ssh-key', url: 'git@github.com:ilkerhalil/conference-demo.git']]])
          }
      }
        stage("Clean"){
            steps{
                    container(name:'openjdk') {
                    sh 'mvn clean'
                }
            }
        }
        stage("Build"){
            steps{
                    container(name:'openjdk') {
                    sh 'mvn compile'
                }
            }
        }
        stage("Create Package"){
            steps{
                    container(name:'openjdk') {
                      sh 'mvn package'
                }
            }
        }
        stage("Docker-Build"){
              when{
                  expression {
                    env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'development'
                }
            }
            steps{
                    container(name:'openjdk') {
                    sh 'mvn oc:build'
                }
            }
        }
        stage("Docker-Push"){
              when{
                  expression {
                    env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'development'
                  }
              }
               steps{
                      container(name:'openjdk') {
                      sh 'mvn oc:push'
                        }
                    }
               }
        stage("Deploy"){
              when{
                  expression {
                    env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'development'
                }
            }
            steps{
                    container('openjdk') {
                    sh 'mvn oc:deploy'
                }
            }
        }

    }
    post{
        always{
            echo "====++++ ++++===="
        }
        success{
            slackSend channel: 'jenkins-build', color: 'good', message: "Build Completed..! Job Name:${env.JOB_NAME} Job Number:${env.BUILD_NUMBER}", teamDomain: 'yolcu360', tokenCredentialId: 'SlackToken'
        }
        failure{
            slackSend channel: 'jenkins-build', color: 'danger', message: "Build Failure. Plese check log: ${env.BUILD_URL}console", teamDomain: 'yolcu360', tokenCredentialId: 'SlackToken'
        }
    }
}