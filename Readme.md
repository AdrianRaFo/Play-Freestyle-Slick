## Features
The project use a [Freestyle-Slick](http://frees.io/docs/integrations/slick/) integration in a [Play framework](https://www.playframework.com) application, including a form for insert a user data with validation, security filters and response in Json as part of example of how to do an Api Rest.

## How to run
To run this project you need to have installed:
  - `JDK`: v8
  - `SBT`: v0.13.15
  - `Scala SDK`: v2.11.11
  
Once you have it all installed in project root execute in terminal `sbt run` and open in a browser `localhost:9000` to see it.

## Folder guide
`Controllers`: Compose by actions that get the request and return a response or redirect to another action.

`Dao`: Folder of Slick generated mapping and Fresstyle-Slick integration.

`Views`: The twirl templates.

`Filters`: Security filters.

`Conf`: The `application.conf` with the configuration and `routes` with the app routes.

`Public`: Assets folder.

## How to make a mapping

I'm using ìn this project [Slick-CodeGen](http://slick.lightbend.com/doc/3.2.0/codegen-api/index.html#package).

To generate the file `Tables.scala` with the mapping you can execute `sbt slick-gen`.

## Schema.

To generate the schema you can run `Tables.schema.create` first in the `main` method.

**Note**: Set your own `user` and `password` in `application.conf` and `build.sbt`.
