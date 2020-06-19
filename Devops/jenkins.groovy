import groovy.io.FileType

pipeline {
    agent any

    environment {
        def SeoPath = "${env.seo_root}"
        def DataFolderName = "data"
        def DataPath = "${SeoPath}\\${DataFolderName}"

        def OutPutFolderName = "output"
        def OutputPath = "${SeoPath}\\${OutPutFolderName}"


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
                    // CheckAndCreate("${DataPath}")
                    // CheckAndCreate("${OutPutPath}")

                    bat '''
                    if (-not (Test-Path C:\\Workspace\\Jenkins_build_root\\__seo__\\data)) {
                        New-Item  -Path C:\\Workspace\\Jenkins_build_root\\__seo__ -Name "data" -ItemType "directory"
                        echo "create folder: C:\\Workspace\\Jenkins_build_root\\__seo__\\data"
                    } else {
                        echo "C:\\Workspace\\Jenkins_build_root\\__seo__\\data already exists"
                    }'''
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


def CheckAndCreate(folder_name) {
    echo "INFO: Check folder: ${folder_name}"
    def fp = new File("${folder_name}")
    if (fp.exists()) {
        echo "INFO: ${folder_name} already exists"
    } else {
        fp.mkdirs()
        echo "INFO: ${folder_name} created"
    }
}