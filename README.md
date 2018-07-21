# FakeAndDraw backend application
Server side application for FakeAndDraw game.
You can find the client side code [here](https://github.com/dalogax/FakeAndDrawFront).


## Requirements
- Maven
- Java 1.8

To run the application just type the following command:
```
$ mvn spring-boot:run
```

## Application architecture
Using [clean architecture](https://github.com/mattia-battiston/clean-architecture-example).

- EntryPoints
- Domain
- Dataproviders

## Database
- /src/main/resources/schema.sql -> schema definition for embebed database, will be executed on boot.
- /src/main/resources/data.sql -> data definition for embebed database, will be executed on boot.
- /src/main/resources/application.properties -> main configuration file, check comments there.
- H2 embebed database console will be loaded on http://localhost:8080/h2-console
  

# Websocket API

## Message definition

All messages on both directions are based on [Flux action objects](https://github.com/redux-utilities/flux-standard-action) with the following structure:

```
{
  string type,
  object payload?,
  boolean error?
} = message
```
In case of error the payload will have the following structure:
```
{
  number code,
  string message
} = errorPayload
```
To identify the origin and destiny of every message we will use the following icons:
:pager: Server
:tv: -> Master client
:iphone: -> Player client

### Game create
:computer: :arrow_right: :pager:
A game is created with a "game-create" message originated on the master client.
```
{
  string type = 'game-create'
} = GameCreateMessage
```
Example:
```
{
  "type": "game-create",
}
```
### Game created
:pager::arrow_right::computer:
If the game is succesfully created the server will send a "game-created" message to the master client.
```
{
  string type = 'game-created',
  GameCreatedPayload payload,
  boolean error?
} = GameCreatedMessage

{
  string gameCode,
  number lifespanTimestamp
} = GameCreatedPayload
```
Example:
```
{
  "type": "game-created",
  "payload": {
    "gameCode": "HFKDC",
    "lifespanTimestamp": 123123123123 
  }
}
```

###NEW USER
direction s = c-s
type s = new-user
body
  nickname s = Nick
  gameCode s = HFKDC
  
direction s = s-c (to Master)
type s = user-added
body
  userId n = 1
  nickname s = Nick
  avatarUrl s = http://sdasdad.com/asdsad.jpg
  
direction s = s-c (to added User)
type s = user-added
body
  userId n = 1
  nickname s = Nick
  avatarUrl s = http://sdasdad.com/asdsad.jpg
  
###DRAWING STARTED
direction s = s-c
type s = drawing-started
body
	lifespanTimestamp n = 123123123123

###TITLE ASSIGN ()
direction s = s-c
type s = title-assign
body
  	lifespanTimestamp n = 123123123123
  	title s = Elephant
  
###DRAWING SUBMIT (device send draw)
direction s = c-s
type s = drawing-submit
body
  image s = base64
  
###DRAWING ADDED
direction s = c-s
type s = drawing-added
body
    userId n = 1
    nickname s = Nick
    avatarUrl s = http://sdasdad.com/asdsad.jpg
 
###START ROUND (master show image, device waiting)
direction s = s-c
type s = start-round
body
  image s = BASE64
  lifespanTimestamp n = 123123123123
###TITLE GUESS
direction s = c-s
type s = title-guess
body
  title s = La casa de la pradera
direction s = s-c
type s = title-guess-result
body
  accepted b = true
###VOTE STARTS (master showing image, devices show list)
direction s = s-c
type s = voting-start
body
  votes []
    id n = 0
    vote s = vote
###VOTE SUBMIT (master showing image, device sending vote)
    
direction s = c-s
type s = vote
body
  id n = 0
###ROUND RESULTS (master shows results, device waiting)
direction s = s-c
type s = round-results
body
  titles []
    title s = Pradera
    owner s = Owner
    points n = 123123
    users[]
      name s = Votee
    
 -- TOSTART
### MATCH RESULTS (master shows results, device waiting)
direction s = s-c
type s = match-results
body
  users []
    name s = Juan
    points n = 123123
-- TOMATCHSTART
