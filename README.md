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
 
	tool.setKey("sk-a29f4cffdb59466e86xxxcde9c8f9be9")
 
	val sds = tool.getAIResponse("你好")

 <img width="509" alt="1739343134574_A5A175E0-A158-4478-A854-56526DED4AB8" src="https://github.com/user-attachments/assets/994e173f-e166-4738-a449-334b7936bd8c" />


 2025.02.13:v1.0.3-支持多轮对话功能
 
 tool.setMultiRound(true)
 ![image](https://github.com/user-attachments/assets/fd6d23ce-5112-4170-bc9f-159b093770d2)


2025.02.14：v1.0.4-支持fim补全功能，调用方法

@param prompt 前缀 

@param suffix 后缀  

@param max_tokens 最大token数

示例：tool.getFimResponse("def fib(a):", "return fib(a - 1) + fib(a - 2)", 128);
![image](https://github.com/user-attachments/assets/5aa09b07-1c1e-4faa-9662-64a4a0defdf6)

