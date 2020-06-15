pipeline {
    agent any

    options
    {
        timestamps()
    }

    stages {
        stage('Run') {
            steps {
                script {
                    echo "Hello Jenkins"

                    sh "ls -la ${pwd()}"

                    
                }
            }
        }
    }
}