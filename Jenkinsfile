env.label = "ci-pod-${UUID.randomUUID().toString()}"

pipeline{
    agent{
        kubernetes{
        label "${env.label}"
           yaml """
apiVersion: v1
kind: Pod
metadata:
  namespace: conference-demo-dev
spec:
  securityContext:
    fsGroup: 1000150000
  containers:
  - name: openjdk
    image: openshift/origin-docker-builder:v3.11
    command:
    - cat
    tty: true
    env:
    - name: HOME
      value: /home/jenkins
    - name: MAVEN_OPTS
      value: -Duser.home=/home/jenkins
    - name: OPENSHIFT_PASSWORD
      valueFrom:
        secretKeyRef:
          key: password
          name: openshift-login

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
                    sh 'mvn clean oc:resource -q'
                }
            }
        }
        stage("Create Package"){
            steps{
                    container(name:'openjdk') {
                      sh 'oc login --insecure-skip-tls-verify=true -u kubeadmin -p ${OPENSHIFT_PASSWORD} https://192.168.1.225:8443' 
                      sh 'oc project conference-demo-dev'
                      sh 'mvn package oc:build  -q'
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
                      sh 'mvn oc:push -q'
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
                    sh 'mvn oc:deploy -q'
                }
            }
        }

    }
    
}