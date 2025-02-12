Add it in your root build.gradle at the end of repositories:

	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.colinwenjian:deepseek:1.0.2'
	}


调用方法：

	val tool = DeepSeekTool()
 
	tool.setModel("deepseek-chat")
 
	tool.setKey("sk-a29f4cffdb59466e8636ccde9c8f9be9")
 
	val sds = tool.getAIResponse("你好")
