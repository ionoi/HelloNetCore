pipeline {
    agent any

    environment {
        def SeoPath = "${env.seo_root}"
        def DataFolderName = "data"
        def DataPath = "${SeoPath}/${DataFolderName}"

        def OutPutFolderName = "output"
        def OutputPath = "${SeoPath}/${OutPutFolderName}"


    }


    options
	{
		timestamps()
        skipDefaultCheckout()
        buildDiscarder(logRotator(numToKeepStr: "30", daysToKeepStr: '7'))
	}

        parameters {
            choice (name : 'cleanup', choices : 'no\nyes', description : 'cleanup the data folder?')
        }

    stages {
        stage('Prepare') {
            steps {
                script {
                    echo "INFO: Check and Create Data Folder"
                    CheckAndCreate("${SeoPath}", "${DataFolderName}")
                    CheckAndCreate("${SeoPath}", "${OutPutFolderName}")
                }
            }
        }

        stage('aws') {
            steps {
                script {
                    echo "Zip"
                    bat '''
                    dir
                    aws --version
                    '''

                }
            }
        }

        stage('Run') {
            steps {
                script {
                    echo "Hello Jenkins"

                    // bat '''
                    // dir
                    // cd HelloNetCore
                    // dotnet run
                    // '''

                }
            }
        }

    }

   
    post {
        success {            
            echo 'INFO: Success'
        }
        always {
            deleteDir()
        }
    }
}


def CheckAndCreate(path, name) {
    def folder_name = "${path}/${name}" 
    bat '''
    if (-not (Test-Path ${folder_name})) {
        New-Item  -Path ${path} -Name "${folder_name}" -ItemType "directory"
        echo "create folder: ${folder_name}"
    } else {
        echo "${folder_name} already exists"
    }'''
}