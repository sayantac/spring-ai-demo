The PoC is a small hands-on the Spring AI project using Gemini 1.5 Pro LLM from Google Vertex AI

The service-accounts-key.json is not included in the source control and should be added to the folder structure as follows from your own GCP account:

your-project/  
├── Dockerfile  
├── service-account-key.json  
├── target/  
│   └── your-spring-boot-app.jar  
└── ...  

**#Docker commands to build and run the app for the first time:**

* docker build -t spring-ai-gemini:latest .
* docker run -p 8080:8080 --name spring-ai-gemini -d spring-ai-gemini:latest

**#Docker commands to stop and start the pre-built container:**

* docker stop spring-ai-gemini
* docker run -p 8080:8080 spring-ai-gemini

**#Endpoints:**

localhost:8080/ai/generate (Generate Text)  
localhost:8080/ai/generateStream (Generate Text Stream)  
localhost:8080/ai/weather (Function Calling using Agents)  
localhost:8080/ai/multimodal (Multimodal prompts using Image and Text)  


