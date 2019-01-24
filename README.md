# **COE - GRAPH QL POINT-OF-VIEW**
This is the graphQL sample reference code built against the COE.


## Running the Project
Run the project by the following mvn command by going into the folder .../graphql-java-master where the pom.xml is located
 ```    
mvn jetty:run 
 ```    
This will run the graph QL server on port 8080


## GraphiQL URLS and other details
GraphiQL is an in-browser IDE allowing you to explore the schema, fire queries/mutations and see the results.
start Jetty as the previous step and open the below mentioned link.
```
http://localhost:8080/
```

## MongoDB Server
Start the mongoDB server, server will wait for connections on port 28017. Make sure you are having database named coelinks created in the mongodb server

## STEPS OF EXECUTION

### A)
Comment out the following code in the path com.graphql.coe.GraphQLEndPoint (Line number 64-73)
```
	/*@Override
    protected GraphQLContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
        User user = request
                .map(req -> req.getHeader("Authorization"))
                .filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ", ""))
                .map(userRepository::findById)
                .orElse(null);
        return new AuthContext(user, request, response);
    }*/
```
### B) Create a user by using GraphiQL (localhost:8080)

***Request***
```
mutation createUser {
  createUser(
    name: "mohit",
	authProvider: {email: "mohit.mehrotra19@gmail.com", password: "mohit1"})
	{ id
	  name
	}
}
```

***Response***	
```	
{
  "data": {
	"createUser": {
		"id": "5c18b28c033ba41bd0fc2482",
		"name": "mohit"
	}
 }
}
```
	
### C) Sign-in with the user to get the token

***Request***
```
mutation signinUser {
   signinUser(auth: {email: "mohit.mehrotra19@gmail.com", password: "mohit1"}) 
      {
		token
		user {
			id
			name
		}
	 }
}
```
***Response***
```
{
 "data": {
   "signinUser": {
		"token": "5c18b28c033ba41bd0fc2482",
		"user": {
			"id": "5c18b28c033ba41bd0fc2482",
			"name": "mohit"
		}
	}
 }
}
```
### D) Go to the index.html (src/main/webapp/) - line number 117.
Change the Authorization code with the token recieved in the previous step. Also, uncomment the code coomented in ***step A)***.

### E) Run the server again with mvn jetty:run
   
   
### F) Queries for creating links (MUTATION SAMPLES)
***Request 1***
```
mutation createLink {
	createLink(url: "mohit.sss", description: "my first link"){
		url
		description
	}
}
```
***Request 2***
```
mutation createLink {
    createLink(url: "mohit.ttt", description: "my second link"){
		url
		description
	}
}
```
### G) Queries for querying links (QUERY SAMPLE)
***Request 1***
```
query allLinks {
	allLinks{
		id
		url
		description
	}
}
```
***Request 2***
```
query allLinks {
	allLinks{
		id
		url
		description
		postedBy {
			name
		} 
	}
}
```
   
	
	
