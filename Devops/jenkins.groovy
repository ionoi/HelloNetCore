import groovy.io.FileType

pipeline {
    agent any

    environment {
        def SeoPath = "${env.seo_root}"
        def DataFolderName = "data"
        def DataPath = "${SeoPath}\\${DataFolderName}"

        def OutputFolderName = "output-${env.BUILD_NUMBER}"
        def OutputBasePath = "${SeoPath}\\output"
        def OutputPath = "${OutputBasePath}\\${OutputFolderName}"

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
                    // CheckAndCreate("${OutputPath}")
                    CheckAndCreate("${SeoPath}", "${DataFolderName}")
                    CheckAndCreate("${OutputBasePath}", "${OutputFolderName}")
                }
            }
        }
        stage('Crawl') {
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
        stage('Upload S3') {
            steps {
                script {
                    echo "Zip"
                    powershell '''
                    dir
                    aws --version
                    $Env:Path
                    '''
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
