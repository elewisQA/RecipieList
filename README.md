# Recipe List

This Repo holds the code for my second project with the QA-Academy.  
It is a web-front end website that displays a list of recipes (with sub-lists for ingredients and method) which is reliant on a spring-boot (Java) back-end.  
Since the primary focus for this project was testing, there is unit-tests, integration-tests and selenium-tests all found under `src/test`

## Getting Started

To get started with this project, simply clone down the repository and open it in your preferred IDE (i.e: Eclipse) as a SpringBoot or Maven package.  
I would also recommend opening up the website files `src/main/resources` in a seperate IDE such as Visual Studio Code which can be used to host the site.
### Prerequisites

What things you need to install the software and how to install them

```
Java 13  
JUnit 5  
Selenium  
SpringBoot 2.x  
Mockito
```
## Testing  
### Running the tests

The integration and JUnit tests should be runnable by any standard Java IDE.  
  
To run the Selenium tests, you will need to run the project as a SpringBoot application and then host the site.  
If you are hosting the website files on something other than VisualStudio Code's Liveserver, you may need to alter the URL location found in the test file.


### Unit Tests 

These test individual parts of the program. As a lot of the program was written using Lombok and Spring, I was only able to cover so much code.

### Integration Tests 

These test the API service and controller files.  
*NOTE:* The Recipe-Service integration test is not complete as I ran out of time to find and fix the problems with it.

### JMeter  
JMeter test-plan and results can be found under `src/test/resources/JMeter`  
This includes a load-test and a spike-test.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [SpringBoot](https://spring.io/projects/spring-boot) - API Running and Mangement

## Authors

* **Elizabeth Lewis** - [elewisQA](https://github.com/elewisQA)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

## Acknowledgments

* (Nick Johnson)[https://github.com/nickrstewarttds] - Teaching Java, and my Mentor @ QA 
* (Vinesh Ghela)[https://github.com/vineshghela] - Teaching Spring
* (Alan Davies)[https://github.com/MorickClive] -  Teaching Selenium
* Edward Reynolds - Teaching JMeter