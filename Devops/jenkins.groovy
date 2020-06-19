import groovy.io.FileType

pipeline {
    agent any

    environment {
        def SeoPath = "${env.seo_root}"
        def DataFolderName = "data"
        def DataPath = "${SeoPath}\\${DataFolderName}"

        def OutputFolderName = "output-{env.BUILD_NUMBER}"
        def OutputBasePath = "${SeoPath}\\output"
        def OutputPath = "${OutputBasePath}\\${OutputFolderName}"


    }


    optionsp
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
                    // CheckAndCreate("${OutputPath}")
                    CheckAndCreate("${SeoPath}", "${DataFolderName}")
                    CheckAndCreate("${OutputBasePath}", "${OutputFolderName}")

                    // powershell "Test-Path C:\\Workspace\\Jenkins_build_root\\__seo__\\data"
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


// def CheckAndCreate(folder_name) {
//     echo "INFO: Check folder: ${folder_name}"
//     def fp = new File("${folder_name}")
//     if (fp.exists()) {
//         echo "INFO: ${folder_name} already exists"
//     } else {
//         fp.mkdirs()
//         echo "INFO: ${folder_name} created"
//     }
// }

def CheckAndCreate(path, name) {
    def folder_name = "${path}\\${name}" 	
    echo "${folder_name}"
    def script =  """if (-not (Test-Path ${folder_name})) {
        New-Item  -Path ${path} -Name "${name}" -ItemType "directory"	
        echo "create folder: ${folder_name}"	
    } else {	
        echo "${folder_name} already exists"	
    }"""	

    // echo "scipt: ${script}"	
    powershell "${script}"	
} 
