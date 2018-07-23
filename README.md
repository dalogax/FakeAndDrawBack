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
- /src/main/resources/schema.sql -> schema definition for embedded database, will be executed on boot.
- /src/main/resources/data.sql -> data definition for embedded database, will be executed on boot.
- /src/main/resources/application.properties -> main configuration file, check comments there.
- H2 embebed database console will be loaded on http://localhost:8080/h2-console
  - jdbc url: jdbc:h2:mem:fakeanddraw
  - user name: fad
  - password: <empty>
  

# Websocket API

## Message definition

All messages on both directions are based on [Flux action objects](https://github.com/redux-utilities/flux-standard-action) with the following structure:

```javascript
{
  string type,
  object payload?,
  boolean error?
} = message
```
In case of error the payload will have the following structure:
```javascript
{
  number code,
  string message
} = errorPayload
```
### Common payloads
#### Player Info Payload
> Represents a player
```javascript
{
	number userId,
	string nickname,
	string avatarUrl
} = UserInfoPayload
```
To identify the origin and destiny of every message we will use the following icons:
- :radio: Server
- :tv: Master client
- :iphone: Player client

### Game create 
:tv: to :radio:
> A game is created with a "game-create" message originated on the master client.
```javascript
{
  string type = 'game-create'
} = GameCreateMessage
```
Example:
```javascript
{
  "type": "game-create",
}
```
### Game created
:radio: to :tv:
> If the game is succesfully created the server will send a "game-created" message to the master client.
```javascript
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
```javascript
{
  "type": "game-created",
  "payload": {
    "gameCode": "HFKDC",
    "lifespanTimestamp": 1532211569 
  }
}
```

### New User
:iphone: to :radio:
> When a player introduces his nickname and the game code and clicks on join game a new-user message is sent to the server
```javascript
{
  string type = 'new-user',
  NewUserPayload payload
} = NewUserMessage

{
  string nickname,
	string gameCode
} = NewUserPayload
```
Example:
```javascript
{
  "type": "new-user",
  "payload": {
		"nickname": "Mike",
		"gameCode": "HFKDC"
  }
}
```
### User Added
:radio: to :tv: and :iphone:
> If the user is succesfully added the server will send a "user-added" message to the master client and the added player.
```javascript
{
  string type = 'user-added',
  PlayerInfoPayload payload,
  boolean error?
} = UserAddedMessage
```
Example:
```javascript
{
  "type": "user-added",
  "payload": {
    "userId": 1,
    "nickname": "Mike",
		"avatarUrl": "https://media.giphy.com/media/3oFzm4W5S8U6VDnBdK/giphy.gif"
  }
}
```

### Drawing started
:radio: to :tv:
> The server will notify the master that the players will start drawing the suggested titles so master so he can show the drawing timeout.
```javascript
{
  string type = 'drawing-started',
  DrawingStartedPayload payload
} = DrawingStartedMessage

{
  number lifespanTimestamp,
} = DrawingStartedPayload
```
Example:
```javascript
{
  "type": "drawing-started",
  "payload": {
    "lifespanTimestamp": 1532211569
  }
}
```

### Title assign
:radio: to :iphone:
> The server will send to each player a "title-assign" message with the suggested title they will try to represent with a drawing.
```javascript
{
  string type = 'title-assign',
  TitleAssignPayload payload
} = TitleAssignMessage

{
  number lifespanTimestamp,
	string title
} = TitleAssignPayload
```
Example:
```javascript
{
  "type": "title-assign",
  "payload": {
    "lifespanTimestamp": 1532211569,
		"title": "Flying cow"
  }
}
```

### Drawing submit
:iphone: to :radio:
> When the player finishes his drawing he will send the drawing to the server with a The server will send to each player a "drawing-assign" message.
```javascript
{
  string type = 'drawing-submit',
  DrawingSubmitPayload payload
} = DrawingSubmitMessage

{
	string image (base 64)
} = DrawingSubmitPayload
```
Example:
```javascript
{
  "type": "drawing-submit",
  "payload": {
    "image": "kZJRgABAQAAAQABAAD/2wCEAAkGBxQS..."
  }
}
```

### Drawing added
:radio: to :tv: 
> When a drawing is succesfully submitted the server will notify the master that a player finished drawing with the player info.
```javascript
{
  string type = 'drawing-added',
  PlayerInfoPayload payload
} = DrawingAddedMessage
```
Example:
```javascript
{
  "type": "drawing-added",
  "payload": {
    "userId": 1,
    "nickname": "Mike",
    "avatarUrl": "https://media.giphy.com/media/3oFzm4W5S8U6VDnBdK/giphy.gif"
  }
}
```
 
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
