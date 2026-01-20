<h1>OAuth2-JWT 💻</h1>

<p>
    <img src="https://img.shields.io/badge/-java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java Badge"/>
    <img src="https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="SpringBoot Badge"/>
    <img src="https://img.shields.io/badge/-Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security Badge" />
    <img src="https://img.shields.io/badge/OAuth2-000000?style=for-the-badge&logo=oauth&logoColor=white" alt="OAuth2 Badge"/>
    <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT Badge"/>
</p>

<p>
OAuth2-JWT is a back-end application developed for learning purposes, demonstrating the implementation of Google social login using OAuth2 and JWT.
</p>

<h2>🚀 Getting started</h2>

<h3>💻 Prerequisites</h3>

- [JDK 21](https://www.oracle.com/br/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)

<h3>🛸 Cloning</h3>

```
git clone https://github.com/MiguelSperle/oauth2-jwt.git
```

📂 Access at folder

```
cd oauth2-jwt
```

📡 Install dependencies

```
mvn clean install
```

<h3>🔑 System environment variables</h3>

```
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: YOUR_CLIENT_ID
            client-secret: YOUR_CLIENT_SECRET
            scope:
              - email
              - profile

application:
  jwt:
    issuer: oauth2
    public:
      key: classpath:app.pub
    private:
      key: classpath:app.key
  web:
    cors:
      allowed-origin: http://localhost:3000
```

<h3>👨🏻‍💻 Developer</h3>

<table>
  <tr>
    <td>
      <a href="https://github.com/MiguelSperle">
        <img src="https://avatars.githubusercontent.com/u/102910354?v=4" width="100px;" alt="Miguel Sperle Profile Picture"/><br>
      </a>
    </td>
  </tr>
</table>