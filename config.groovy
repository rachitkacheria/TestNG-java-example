// --------------- Application ---------------------------------------

// Android
ANDROID_APPLICATION_NAME = "JetPay.apk"

// iOS
IOS_APPLICATION_NAME = "JetPay.ipa"
//IOS_AUTOMATION_APPLICATION_NAME = "JetPay.ipa"



// ---------------- Automation Configuration ----------------------------

// Automation to run on specific branch: provide comma seperated list
AUTOMATION_RUN_ON = ["Jenkins_ci_cd"]

// QA
AUTOMATION_QA_TEST_GROUP_ID = "SMOKE"

// SIT
AUTOMATION_SIT_TEST_GROUP_ID = "Regression"

// Jenkins_ci_cd
//AUTOMATION_JENKINS_CI_CD_TEST_GROUP_ID = "WDJPPLONE-382"
AUTOMATION_JENKINS_CI_CD_TEST_GROUP_ID = "WDJPPLONE-382"


// ---------------- Email Configuration --------------------

// Get notified when linting, unit test, application build failed 
DEVELOPER_TEAM = "rachit.kacheria@infostretch.com, rajan.palaniya@infostretch.com, prashant.sanghavi@infostretch.com, amit.mistry@infostretch.com, dhaval.panchal@infostretch.com, Jyubin.patel@infostretch.com, 8c790f07.infostretch.com@apac.teams.ms"
QA_TEAM = "bhumit.shah@infostretch.com, viraj.patel@infostretch.com, nitin.dixit@infostretch.com"
MANAGEMENT_TEAM = "sowjanya.yendamuri@infostretch.com, harish.nair@infostretch.com, paras.shah@infostretch.com, Nivrutti.Chandel@infostretch.com"
ALL_TEAM = "${DEVELOPER_TEAM}, ${QA_TEAM}, ${MANAGEMENT_TEAM}"

// Configuration
BODY_MIME_TYPE = "text/plain"

// Project Details
MESSAGE_PROJECT_DETAILS = """
Project Name: ${JOB_NAME}
Branch: ${BRANCH_NAME}
Project URL: ${JOB_URL}
Build Number: ${BUILD_NUMBER}
Build URL: ${BUILD_URL}
"""

// Subject Line
SUBJECT = "Jenkins: ${JOB_NAME} - ${BRANCH_NAME} - #${BUILD_NUMBER}"

//Failed Messages
MESSAGE_BUILD_FAIL = """
${MESSAGE_PROJECT_DETAILS}

Build Failed!!! 

Kindly check build log(build.log) file in attachment for more details.
"""
MESSAGE_CODE_LINT_FAIL = """
${MESSAGE_PROJECT_DETAILS}

Code Linting failed at build #${BUILD_NUMBER}. 

Kindly check report(test-report.html) in attachment for more details.
"""

// Unit Test
MESSAGE_UNIT_TEST_FAIL = """
${MESSAGE_PROJECT_DETAILS}

Unit Test cases failed at build #${BUILD_NUMBER}. 

Check ${BUILD_URL}" for More details
"""

// Passed Messages
MESSAGE_BUILD_PASS = """
${MESSAGE_PROJECT_DETAILS}

Build Successfully Completed. 

"""