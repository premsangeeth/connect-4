# Connect-4 Game Rest Services

Spring boot application which exposes REST services for gaming. Storage used is redis cloud.


### Build

```sh
$ ./gradle build
```

### Run


```sh
$ java -jar build/libs/connect-4-0.0.1.jar
```

Spring boot plugin enables to run without a build
```sh
$ ./gradle bootRun
```

### REST Services
##### Create Game
method:GET
URI:/connect-4/new?userId={userId}&color={YELLOW/RED}

#### Get Game
method:GET
URI:/connect-4/{gameId}

#### Join Game
method:PUT
URI:/connect-4/{gameId}/join/{userId}

#### Play Game
method:PUT
URI:/connect-4/{gameId}/play/{userId}/{column}