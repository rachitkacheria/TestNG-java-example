MESSAGE_BUILD_PASS = """
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Execution Report</title>
	</head>
	<body>
		<p>Hello<b>!</b><br/>
		<%
			def browserName = "Mozilla Firefox"
			if ("${build.buildVariableResolver.resolve("driver_name")}" == ('chromeDriver')) {
				browserName = "Google Chrome"
			} 
			else if ("${build.buildVariableResolver.resolve("driver_name")}" == ('appiumDriver')) {
				browserName = "Apple Safari"
			}
			else if ("${build.buildVariableResolver.resolve("driver_name")}" == ('chromeRemoteDriver')) {
				browserName = "Google Chrome"
			}
			else if ("${build.buildVariableResolver.resolve("driver_name")}" == ('firefoxDriver')) {
				browserName = "Mozilla Firefox"
			}
			else if ("${build.buildVariableResolver.resolve("driver_name")}" == ('firefoxRemoteDriver')) {
				browserName = "Mozilla Firefox"
			}
			else if ("${build.buildVariableResolver.resolve("driver_name")}" == ('iExplorerDriver')) {
				browserName = "Internet Explorer"
			}
			else if ("${build.buildVariableResolver.resolve("driver_name")}" == ('iExplorerRemoteDriver')) {
				browserName = "Internet Explorer"
			}
			def testResult = build.testResultAction
			
			int passCount = 0
			int failCount = 0
			int skipCount = 0
			
			if (testResult) {
		    	def rootUrl = hudson.model.Hudson.instance.rootUrl
				
				build.logFile.text.eachLine{
				line -> 
					if(line.contains("SuiteNumbers=")){
					def countList =line.split('<>')
					passCount=countList[1] as Integer
					failCount=countList[2] as Integer
					skipCount=countList[3] as Integer
					}
				}
				
				int totalTests = passCount + failCount + skipCount
				def buildSuccessRate = String.format("%.2f", (totalTests - (failCount + skipCount)) * 100f / totalTests)
		 		int executionTime = testResult.result.duration
		 		def base = new Date(0)
				def totalTime = new Date(executionTime * 1000)
				def totalExecutionTime = null
				use(groovy.time.TimeCategory) {
					totalExecutionTime = (totalTime-base).toString().replace(',','').replace('.000','')
				}
			%>
			<br/>Please find the execution report.</p>
			<table border=0 width=100%>
	 			<tr width=100%>
	 				<td>
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Job Name</b></div>
	 				</td>
	 				<td>
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Browser</b></div>
	 				</td>
	 				
	 				<td>
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Platform</b></div>
	 				</td>
	 				
	 			</tr>
	 			<tr width=100%>
	 				<td>
	 					<div style="width:100%;background:Linen;text-align:center;border-style: none;">${project.name}</div>
	 				</td>
	 				<td>
	 					<div style="width:100%;background:Linen;text-align:center;border-style: none;">${browserName}</div>
	 				</td>
	 				
	 				<td>
	 					<div style="width:100%;background:Linen;text-align:center;border-style: none;">${build.buildVariableResolver.resolve("Platform")}</div>
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
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Execution Time</b></div>
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
	 					<div style="width:100%;background:Linen;text-align:center;border-style: none;"><b>${buildSuccessRate} %</b></div>
	 				</td>
	 				<td>
	 					<div style="width:100%;background:Linen;text-align:center;border-style: none;">${totalExecutionTime}</div>
	 				</td>
	 			</tr>
	 		</table>
	 		<%
				def dashboardNumber = ""
				def channel = null
				if (build.workspace.isRemote()) {
					channel = build.workspace.channel
				}
				def filePath = new hudson.FilePath(channel, build.workspace.toString() + "\\test-results\\meta-info.json")
				if (filePath != null) {
					dashboardNumber = filePath.readToString().split('\"startTime\": ')[1].split(' }')[0]
				} 
			%> 
			<p>For execution dashboard, please <a href="http://sat1mvlxa021.tlab.ally.corp:8080/job/${project.name}/ws/dashboard.htm#${dashboardNumber}">click here</a>.</p>
			<p>Scenario wise execution details <b>-</b></p>
			<table border=0 width=100%>
	 			<tr width=100%>
	 				<td style="width:90%">
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Name</b></div>
	 				</td>
	 				<td style="width:10%">
	 					<div style="width:100%;text-align:center;border-style: solid;border-width: 1.5px;"><b>Status</b></div>
	 				</td>
	 			</tr>	
			<%
				
		 		testResult.result.passedTests.each {
					passedScripts ->
					def passedScriptName = passedScripts.safeName
					%>
					<tr width=100%>
						<td style="width:85%">
							<div style="width:100%;background:Linen;text-align:left;border-style: none;">&nbsp;&nbsp;&nbsp;${passedScriptName}</div>
						</td>
						<td style="width:15%">
							<div style="width:100%;background:GreenYellow;text-align:center;border-style: none;">Pass</div>
						</td>
					</tr>
				<% }				
				testResult.result.failedTests.each {
					failedScripts ->
					def failedScriptName = failedScripts.safeName
					%>
					<tr width=100%>
						<td style="width:85%">
							<div style="width:100%;background:Linen;text-align:left;border-style: none;">&nbsp;&nbsp;&nbsp;${failedScriptName}</div>
						</td>
						<td style="width:15%">
							<div style="width:100%;background:OrangeRed;text-align:center;border-style: none;">Fail</div>
						</td>
					</tr>
				<% }
				if(skipCount>0){
				testResult.result.skippedTests.each {
					skippedScripts ->
					def skippedScriptName = skippedScripts.safeName
					%>
					<tr width=100%>
						<td style="width:85%">
							<div style="width:100%;background:Linen;text-align:left;border-style: none;">&nbsp;&nbsp;&nbsp;${skippedScriptName}</div>
						</td>
						<td style="width:15%">
							<div style="width:100%;background:Gold;text-align:center;border-style: none;">Skip</div>
						</td>
					</tr>
				<% }}
			%>
			</table>
		
		<p>Kindly let us know if you require any further information.<br/></p>
		<% } else {  %>
		<p>Due to some technical issue, we are not able to provide execution report.<br/><br/>Sorry for the inconvenience caused.<br/></p>
		<% } %>
		<p><b>Regards,</b><br/>Ally AAOS Automation<br/><img src="https://www.ally.com/resources/pres/global/images/logo.png" style="width:100px;height:30px;"></p>
	</body>
</html>
"""