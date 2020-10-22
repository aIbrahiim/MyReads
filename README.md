# MyReads

## Steps to setup

**1.Clone the application**

**2.Create myreads database**
```bash
create database myreads
```

*3. Change postgresql username and password as per your installation**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your postgresql installation

**4. Run the app using maven**

```bash
mvn spring-boot:run
```
The app will start running at <http://localhost:8080>

## Explore Rest APIs

### Auth

| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/auth/signup | Sign up | [JSON](#signup) |
| POST   | /api/auth/signin | Log in | [JSON](#signin) |


Test them using postman or any other rest client.

## Sample Valid JSON Request Bodys

##### <a id="signup">Sign Up -> /myreads/auth/signup</a>
```json
{
	"firstName": "Jordan",
	"lastName": "Henderson",
	"username": "hendo",
	"password": "password",
	"email": "hendo14@gmail.com",
	"gender":"male",
	"birthDay":"1990-06-11",
	"website":null,
	"bio":null
}
```

##### <a id="signin">Log In -> /myreads/auth/signin</a>
```json
{
	"usernameOrEmail": "hendo",
	"password": "password"
}
```
