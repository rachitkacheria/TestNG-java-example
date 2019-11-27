import java.util.concurrent.TimeUnit
@NonCPS

//-----------------Read XML file to fetch the result-------------------
def xml = readFile "${env.WORKSPACE}/target/surefire-reports/testng-results.xml"
       
passCount = new XmlParser().parseText(xml).@'passed' as Integer
failCount = new XmlParser().parseText(xml).@'failed' as Integer 
totalTests = new XmlParser().parseText(xml).@'total' as Integer
skipCount = new XmlParser().parseText(xml).@'ignored' as Integer   
totalExecutionTime = new XmlParser().parseText(xml).suite.collect{it.@'duration-ms'}
long millis = totalExecutionTime.join('') as long
browserName = "Google Chrome"
String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
def buildSuccessRate = String.format("%.2f", (totalTests - (failCount + skipCount)) * 100f / totalTests)
// --------------- Application ---------------------------------------

// Android
ANDROID_APPLICATION_NAME = "JetPay.apk"

// iOS
IOS_APPLICATION_NAME = "JetPay.ipa"
//IOS_AUTOMATION_APPLICATION_NAME = "JetPay.ipa"




// ---------------- Email Configuration --------------------

// Get notified when linting, unit test, application build failed 
QA_TEAM = "rachit.kacheria@infostretch.com, Nivrutti.Chandel@infostretch.com, nitin.dixit@infostretch.com, Khushboo.Srivastava@infostretch.com, sowjanya.yendamuri@infostretch.com, harish.nair@infostretch.com, paras.shah@infostretch.com, amit.mistry@infostretch.com, e2e57c87.infostretch.com@apac.teams.ms"

// Configuration
BODY_MIME_TYPE = "text/html"

// Project Details
Mail = """
<!DOCTYPE html>
        <html>
	    <head>
		<meta charset="UTF-8">
		<title>Execution Report</title>
	    </head>
	    <body>
		<p>Hello<b>!</b><br/>
		
			<br/>Please find the execution report.</p>
			<table border=0 width=100%>
	 			<tr width=100%>
	 				<td>
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Job Name</b></div>
	 				</td>
	 				<td>
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Browser</b></div>
	 				</td>
	 				
	 					 				
	 			</tr>
	 			<tr width=100%>
	 				<td>
	 					<div style="width:100%;background:Linen;text-align:center;border-style: none;">${JOB_NAME}</div>
	 				</td>
	 				<td>
	 					<div style="width:100%;background:Linen;text-align:center;border-style: none;">${browserName}</div>
	 				</td>
	 				
	 					 				
	 			</tr>
	 		</table>
	 		<br/>
	 		<table border=0 width=100%>
	 			<tr width=100%>
	 				<td>
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Scripts Executed</b></div>
	 				</td>
	 				<td>
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Pass</b></div>
	 				</td>
	 				<td>
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Fail</b></div>
	 				</td>
	 				<td>
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Skip</b></div>
	 				</td>
	 				<td>
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Success Rate</b></div>
	 				</td>
	 				<td>
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Execution Time (hh:mm:ss)</b></div>
	 				</td>
	 			</tr>
	 			<tr width=100%>
	 				<td>
	 					<div style="width:100%;background:Linen;text-align:center;border-style: none;"><b>${totalTests}</b></div>
	 				</td>
	 				<td>
	 					<div style="width:100%;background:GreenYellow;text-align:center;border-style: none;"><b>${passCount}</b></div>
	 				</td>
	 				<td>
	 					<div style="width:100%;background:OrangeRed;text-align:center;border-style: none;"><b>${failCount}</b></div>
	 				</td>
	 				<td>
	 					<div style="width:100%;background:Gold;text-align:center;border-style: none;"><b>${skipCount}</b></div>
	 				</td>
	 				<td>
	 					<div style="width:100%;background:Linen;text-align:center;border-style: none;"><b>${buildSuccessRate}%</b></div>
	 				</td>
	 				<td>
	 					<div style="width:100%;background:Linen;text-align:center;border-style: none;">${hms}</div>
	 				</td>
	 			</tr>
	 		</table>
"""
