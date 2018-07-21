# FakeAndDraw backend application
Server side application for FakeAndDraw game

Using clean architecture application structure: https://github.com/mattia-battiston/clean-architecture-example

Run -> mvn spring-boot:run

### DB:
- /src/main/resources/schema.sql -> schema definition for embebed database, will be executed on boot
- /src/main/resources/data.sql -> data definition for embebed database, will be executed on boot
- /src/main/resources/application.properties -> main configuration file, check comments there
- H2 embebed database console will be loaded on http://localhost:8080/h2-console
  

# Websocket API

##TIMEOUTS

direction s = s-c
type s = game-timeout
direction s = s-c
type s = match-timeout
direction s = s-c
type s = draw-timeout

##FLOW

##GAME CREATION
direction s = c-s
type s = game-create
direction s = s-c
type s = game-created
+ body (application/json)
	{
		gameCode s = HFKDC,
		lifespanTimestamp n = 123123123123
	}

##NEW USER
direction s = c-s
type s = new-user
body
  nickname s = Nick
  gameCode s = HFKDC
  
direction s = s-c (to Master)
type s = user-added
body
  nickname s = Nick
  
direction s = s-c (to User)
type s = user-added
body
  nickname s = Nick
  
##MATCH START
direction s = c-s
type s = match-start
DRAW ASSIGN ()
direction s = s-c
type s = title-assign
body
  lifespanTimestamp n = 123123123123
DRAW SUBMIT (device send draw)
direction s = c-s
type s = draw-submit
body
  image s = base64
START ROUND (master show image, device waiting)
direction s = s-c
type s = start-round
body
  image s = BASE64
  lifespanTimestamp n = 123123123123
TITLE GUESS
direction s = c-s
type s = title-guess
body
  title s = La casa de la pradera
direction s = s-c
type s = title-guess-result
body
  accepted b = true
VOTE STARTS (master showing image, devices show list)
direction s = s-c
type s = voting-start
body
  votes []
    id n = 0
    vote s = vote
VOTE SUBMIT (master showing image, device sending vote)
    
direction s = c-s
type s = vote
body
  id n = 0
ROUND RESULTS (master shows results, device waiting)
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
MATCH RESULTS (master shows results, device waiting)
direction s = s-c
type s = match-results
body
  users []
    name s = Juan
    points n = 123123
-- TOMATCHSTART