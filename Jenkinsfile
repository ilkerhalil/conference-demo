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
    fsGroup: 0
  containers:
  - name: openjdk
    image: nexus-docker-registry-nexus3.192.168.1.255.xip.io/docker/maven-oc-build
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
    volumeMounts:
    - name: kubeconfig
      mountPath: "/home/jenkins/.kube"
    - mountPath: /home/jenkins
      name: cache-volume
  imagePullSecrets:
  - name: nexus
  volumes:
  - name: kubeconfig
    secret:
      secretName: kube-config
  - name: cache-volume
    emptyDir: {}
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
                    //sh 'mvn clean compile -q'
                    sh 'cp /home/jenkins/.kube/config /root/'
                }
            }
        }
        

        stage("Login Beta"){
            when{
              expression {
                     env.BRANCH_NAME == 'development'
                }
            }
            steps{
                    container(name:'openjdk') {
                      sh 'oc login --insecure-skip-tls-verify=true -u system:openshift-master --config=/root/config https://192.168.1.225:8443 -n conference-demo-dev'                
                      //sh 'mvn package versions:set -DnewVersion=$(/root/.dotnet/tools/minver) package P=Beta  -q'
                }
            }

        }
        
        stage("Login Prod"){
            when{
              expression {
                     env.BRANCH_NAME == 'master'
                }
            }
            steps{
                    container(name:'openjdk') {
                      sh 'oc login --insecure-skip-tls-verify=true -u system:openshift-master --config=/root/config https://192.168.1.225:8443 -n conference-demo-prod'
                      //sh 'mvn package versions:set -DnewVersion=$(/root/.dotnet/tools/minver)  -P=Prod -q'
                }
            }
        }

        stage("Build & Deploy"){
            steps{
                    container(name:'openjdk') {
                      sh 'mvn clean versions:set -DnewVersion=$(/root/.dotnet/tools/minver) package  -Ddekorate.build=true -Ddekorate.deploy=true -Dmaven.test.skip=true -P=beta -q'                      
                }
            }
        }
        

    }
    
}