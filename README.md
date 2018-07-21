# FakeAndDraw backend application
Server side application for FakeAndDraw game

Using clean architecture application structure: https://github.com/mattia-battiston/clean-architecture-example

Using flux standad action for message definition: https://github.com/redux-utilities/flux-standard-action

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

###GAME CREATION
direction s = c-s
type s = game-create
direction s = s-c
type s = game-created
+ body (application/json)
	{
		gameCode s = HFKDC,
		lifespanTimestamp n = 123123123123
	}

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
###MATCH RESULTS (master shows results, device waiting)
direction s = s-c
type s = match-results
body
  users []
    name s = Juan
    points n = 123123
-- TOMATCHSTART



###TODO
Sprint 3:
-Schedule start of the drawing 
- When drawing starts
	- sent to master "drawing-started"
	- send to each player a new title with the message "title-assign"
	- generate new timeout for drawing and schedule round start
-Manage reception of "drawing-submit" message
	-Save the drawing
	-Notify master that player submitted drawing with the message "drawing-added"
	-check if all drawings were added
		- true -> send message start round to master (empty by now); Cancel scheduled round start.
- When round start
	- send message start round to master (empty by now)