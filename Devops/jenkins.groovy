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

                    bat '''
                    dir
                    cd HelloNetCore
                    dotnet run
                    '''

                }
            }
        }
    }
}