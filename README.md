# BOOK STORE


<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#built-with">Built With</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>


### Built With

* [Java 11](https://adoptopenjdk.net)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [H2](https://www.h2database.com/)

## Getting Started

To get a local copy up and running follow these steps:

### Prerequisites

* Java 11 AdoptOpenJDK
  [Installation Guide](https://adoptopenjdk.net/installation.html)
  
* Postman, Insomnia, Swagger UI, Paw or similar API tool 

* Maven 3.2 or higher [Installation Guide](https://maven.apache.org/install.html)

### Installation

1. Clone the project to your local environment
2. Go to the project root file
3. Run the command: ``` mvn spring-boot:run ```
4. On your API tool do the requests

## Usage

The application populates with simulated data during startup, take a look at the data: [stock](https://github.com/AdrianMedico/bookstore/blob/master/src/main/resources/stock.json)
<br>

***`/books`***

POST request where we send books and the amount to buy in JSON format
<br>
e.g:

``` 
http://localhost:8080/books
``` 
body
``` 
[
    {
        "id": "22d580fc-d02e-4f70-9980-f9693c18f6e0",
        "name": "dolore aliqua sint ipsum laboris",
        "quantity": 1
    },
    {
        "id": "b6e8c865-2221-4435-9a65-d30ca0a63701",
        "name": "ad laborum pariatur consequat commodo",
        "quantity": 1
    }
]
``` 

***`/orders`***

GET request with all orders, includes a query param where we specify the media type: 
<br>
e.g: 
``` 
http://localhost:8080/orders?format=text/csv 
``` 
"text / csv" will return a CSV string as a body any other will return us Json format.


## Roadmap
[![Issues][issues-shield]][issues-url]

See the [open issues](https://github.com/AdrianMedico/bookstore/issues) for a list of proposed features.

## Contact

[![LinkedIn][linkedin-shield]][linkedin-url]

[issues-shield]: https://img.shields.io/github/issues/AdrianMedico/bookstore?style=for-the-badge
[issues-url]: https://github.com/AdrianMedico/bookstore/issues
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/adrian-medico

