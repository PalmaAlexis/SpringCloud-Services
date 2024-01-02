# SpringCloud-Microservices

• This application is based on **microservices**, it applies different technologies to work well, fast and efficient.

• I implemented **Eureka** to interconnect the microservices into themselves.

• I applied Ribbon for **Load Balancer**, then Ribbon was in the Services I used.

• I put **Feign, RestTemplate** into practice for consuming an API.

• I added a new microservice which is **Commons** for using the same Entity Model in two diffent microservices.

• I  utilized a **Gateway**, because for consulting my microservices because I configured them as random ports,
  the Gateways I added were **Zuul and Spring Cloud Gateway.**

• I implemented **Hystrix for Zuul** managing the timeout, also, used **Resilience4J for Spring Cloud Gateway**,
  exercising the **Circuit Braker** into my application.

• I carried out some configuration of each microservice into a **Configuration Service**, at the beggining using
  **Git and then GitHub**, creating an independent config microservice.

• I implemented **Spring Secutiry via JWT**, using a microservice for generating the tokens, and exercising 
  **Oauth 2.0** for validating the token in Zuul, in the other hand, used **JJWT library** for validating it 
  in Spring Cloud Gateway.

• I connected **two Databases: mySQL and PostgreSQL** for saving data from two of my microservices.

• Then, I added **Sleuth and Zipkin**, respectively, for monitoring the consults I made to my application, simulating 
  different cases and information into my application.



