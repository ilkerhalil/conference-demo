env.label = "ci-pod-${UUID.randomUUID().toString()}"
env.podTemplate = (env.BRANCH_NAME == 'development'?'build-pod-template/development.yaml':'build-pod-template/master.yaml')
    agent{
        kubernetes{
        label "${env.label}"
           yamlFile "${env.podTemplate}"
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

        stage("Build & Deploy For Beta"){
            when{
              expression {
                     env.BRANCH_NAME == 'development'
                }
            }
            steps{
                    container(name:'openjdk') {
                      sh 'mvn clean'
                      sh 'mvn versions:set -DnewVersion=$(/root/.dotnet/tools/minver -d beta)'
                      sh 'mvn package -Ddekorate.build=true -Ddekorate.deploy=true -Dmaven.test.skip=true -P=beta -q'                      
                }
            }
        }
        

    }
    
}