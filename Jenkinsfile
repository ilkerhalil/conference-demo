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
  containers:
  - name: openjdk
    image: epamedp/jenkins-agent-maven-openshift
    command:
    - cat
    tty : true
    volumeMounts:
    - name: home-volume
      mountPath: /home/jenkins
    
    env:
    - name: HOME
      value: /home/jenkins
    - name: MAVEN_OPTS
      value: -Duser.home=/home/jenkins
    - name: DOCKER_REGISTRY
      value: "default-route-openshift-image-registry.apps-crc.testing"
    - name: kubeconfig
      mountPath: "/root/.kube/"
  volumes:
  - name: home-volume
    emptyDir: {}
  - name: kubeconfig
    secret:
    secretName: kube-config
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
                    sh 'oc login'
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
    
}