# **COE - GRAPH QL POINT-OF-VIEW**
This is the graphQL sample reference code built for the COE. In this code we are going to explore different options available within GraphQL. Here, we have mongo databas as our back-end and with the help of graph ql dependencies we are going to leverage GraphQL capability.

```
<dependency>
    <groupId>com.graphql-java</groupId>
    <artifactId>graphql-java</artifactId>
    <version>3.0.0</version>
</dependency>
<dependency>
    <groupId>com.graphql-java</groupId>
    <artifactId>graphql-java-servlet</artifactId>
    <version>4.0.0</version>
    </dependency>
```        
## PreRequisites
## MongoDB Server
Make sure you are having a mongo db server running on local. Just start the mongoDB server, server will wait for connections on port 28017. The Graph QL code will make a connection to port 28017. Make sure you are having database named coelinks created in the mongodb server


## Running the Project
Run the project by the following mvn command by going into the folder .../graphql-java-master where the pom.xml is located
 ```    
mvn jetty:run 
 ```    
This will run the graph QL server on port 8080. 


## GraphiQL URLS and other details
GraphiQL is an in-browser IDE allowing you to explore the schema, fire queries/mutations and see the results. We can use this tool to play around the GraphQL.
Start Jetty server as mentioned in the previous step and open the below mentioned link. This link will give you the interface for GraphiQL.
```
http://localhost:8080/
```


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
***Response 1***
```
{
  "data": {
    "createLink": {
      "url": "mohit.sss",
      "description": "my first link"
    }
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
***Response 1***
```
{
  "data": {
    "allLinks": [
      {
        "id": "5c18b740033ba437344c8af1",
        "url": "mohit.sss",
        "description": "my first link"
      },
      {
        "id": "5c18b753033ba437344c8af2",
        "url": "mohit.ttt",
        "description": "my second link"
      }
    ]
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
***Response 2***
```
{
  "data": {
    "allLinks": [
      {
        "id": "5c18b740033ba437344c8af1",
        "url": "mohit.sss",
        "description": "my first link",
        "postedBy": {
          "name": "mohit"
        }
      },
      {
        "id": "5c18b753033ba437344c8af2",
        "url": "mohit.ttt",
        "description": "my second link",
        "postedBy": {
          "name": "mohit"
        }
      }
    ]
  }
}
```
***Request 3***
```
query allLinks {
		allLinks(first:2){
			id
			url
			description
			postedBy {
				name
			} 
	}
	}
```
***Response 3***
```
{
  "data": {
    "allLinks": [
      {
        "id": "5c18b740033ba437344c8af1",
        "url": "mohit.sss",
        "description": "my first link",
        "postedBy": {
          "name": "mohit"
        }
      },
      {
        "id": "5c18b753033ba437344c8af2",
        "url": "mohit.ttt",
        "description": "my second link",
        "postedBy": {
          "name": "mohit"
        }
      }
    ]
  }
}
```
***Request 4***
```
query allLinks {
		allLinks(filter: {url_contains:"sss"}){
			id
			url
			description
			postedBy {
				name
			} 
	}
	}
```
***Response 4***
```
{
  "data": {
    "allLinks": [
      {
        "id": "5c18b740033ba437344c8af1",
        "url": "mohit.sss",
        "description": "my first link",
        "postedBy": {
          "name": "mohit"
        }
      }
    ]
  }
}
```
***Request 5***
```
query allLinks {
		allLinks(filter: {url_contains:"sss", description_contains:"first"}){
			id
			url
			description
			postedBy {
				name
			} 
	}
	}
```
***Response 5***
```
{
  "data": {
    "allLinks": [
      {
        "id": "5c18b740033ba437344c8af1",
        "url": "mohit.sss",
        "description": "my first link",
        "postedBy": {
          "name": "mohit"
        }
      }
    ]
  }
}
```


   
	
	
