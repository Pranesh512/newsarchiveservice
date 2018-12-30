# News Archive Scrapper service

This project contains Spring boot web application which scraps the articles from news portal's archives(currently only one portal is used- "The Hindu"). The scrapped articles and author information is stored in [Elasticsearch](https://www.elastic.co/products/elasticsearch). Also the same application provides REST APIs to search authors and articles.

### Steps to deploy in your local machine:

1. Install [docker](https://docs.docker.com/install/) and [docker-compose](https://docs.docker.com/compose/install/)

2. Build and run the application:

```sh
$ sh buildnrun.sh
```
​	This will build the Spring Boot application and run the docker containers of this application and 		Elasticsearch server. The application for first time will start to scrap the archived articles and store in Elasticsearch in background. In the meantime the REST APIs will be ready to accept requests. [Postman](https://www.getpostman.com/apps) collection can be found in ``` newsarticle-archive-scrapper.postman_collection.json ``` file.

#### ToDos

- Split the single Spring Boot application into three modules *Scrapper application*, *Search application* and *Core library*. Where Scrapper and Search applications depends on Core library.
- The *Scrapper application* can be schedule to invoke on daily basis to scrap the previous day's archived articles.

