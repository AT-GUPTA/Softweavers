# Eternity
Eternity is a scientific calculator web application developed as a software engineering project by our team. It allows users to compute 7 transcendental functions (arccos(x), ab^x, sinh(x), gamma function, standard deviation, x^y, and logb(x)), and also supports evaluating functions.

## Overview
Eternity is a well-organized web calculator app with a client-server architecture that separates the application into different components and handles different responsibilities. This architecture enables the separation of concerns and allows each component to focus on its specific tasks, making the application scalable, maintainable, and extensible. The software architecture employs the use of the client-server architecture and Model-View-Controller (MVC) pattern. The client is developed using ReactJS with HTML, CSS, and JavaScript, while the server is a Java Spring Boot application developed using Maven.

## Architecture
The macro-architecture of Eternity primarily follows the client-server architecture along with MVC pattern. 
The client-side is developed using ReactJS while the server-side is developed using Java Spring Boot framework with Maven build.

## Project Strategy
Our overarching strategy for the Eternity project was modularization. For most of the members, this was the first time doing an engineering project, so it was necessary to divide and simplify the project into smaller pieces.

The project was divided into five stages:

Analysis and Planning - In this stage, interviews were conducted to better define the needs of the users, personas were created based on the interview responses, and some use cases were defined.

Designing - Some of the key decisions were made at the designing stage. One of the most important decisions was which coding language would be used. The team had agreed on using Java for the back-end and HTML, CSS, React, and JavaScript for the front-end. The team had also decided to use a graphical user interface on a web page hosted on the user's local machine. Each member of the team individually decided which approach for their function would be best.

Development and Review - In this stage, front-end and back-end code was completed by each team member and merged onto the main branch. Each member reviewed another team member's code using a template creating by one of the team members. Code review assignments were done in an asymmetric fashion so that no two members were evaluating each other's code. It was conducted in this fashion to ensure the objectivity.

Testing - QA testing for the product was conducted in this stage. Issues with the code, including syntax errors, incorrect results, and inefficiencies were identiied and fixed.

Inference- Final review for the whole project was done prior to the delivery.

## Local Installation

#### Clone the Github repository  ``git clone https://github.com/AT-GUPTA/Softweavers.git``

### Prerequisites
Before you begin, make sure you have the following software installed on your machine:

- Java JDK 19
- Node.js
- Maven

### Installation
Setting up the Client
• Open the command prompt and navigate to the eternityfrontend directory ``./Source/FrontEnd/eternity-frontend/``.
• Run the command npm install to install all the dependencies required for the client.
• After the installation process is complete, run the command npm start to start the client
application.
• The client application will be accessible on ``http://localhost:3000``.
Starting the Server
• Open the command prompt and navigate to the api directory ``./Source/api/eternity/``.
• Run the command mvn clean install to build the server application.
• After the build process is complete, run the command ``java -jar target/eternity.jar`` to start the server application.

***
Thank you for checking out this project.
