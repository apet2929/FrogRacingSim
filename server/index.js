const { get } = require("http");

let app = require("express")();
var server = require("http").Server(app);
var io = require("socket.io")(server, {
    allowEIO3: true
});

var activeRooms = [];
var players = [];

console.log(app);
console.log(server);
console.log(io);

server.listen(8081, function() {
    console.log("Server is now running...");
});

server.on("connect_error", (err) => {
    console.log(`connect_error due to ${err.message}`);
  });

io.on('connection', function(socket) {
    console.log("Player connected!" + socket.id);
    socket.emit('socketID', { id: socket.id });

    let player = new Player(socket.id, null,null,null,null,null,null,null); 
    players.push(player);

    socket.on('disconnect', function() {
        console.log("Player disconnected!" + socket.id);
        for(var i = 0; i < players.length; i++){
            if(players[i].id === socket.id){
                if(players[i].roomId != null) socket.to(players[i].roomId).emit("playerDisconnected", {id : socket.id});
                players.splice(i, 1);
            }
        }
    });


    socket.on("tick", function(data) {
        socket.to(data.roomId).emit("tick", data);
        // console.log("Tick recieved! data = " + JSON.stringify(data, null, 4));
        
        let player = getPlayer(data.id);  
        if(player === null){
            console.error("Error onTick! Player with ID: " + data.id + " not found!");
            return;
        } 
        player.x = data.x;
        player.y = data.y;
        player.vx = data.vx;
        player.vy = data.vy;
        player.state = data.state;
        player.animation = data.animation;
        player.frame = data.frame;
        
    });

    socket.on("joinRoom", data => {
        let roomId = data.roomId;
        let playerId = data.id;
        if(!(roomId in activeRooms)){
            activeRooms.push(roomId);
        }
        let player = getPlayer(playerId);
        if(player === null){
            console.error("Error onJoinRoom! Player with ID: " + data.id + " not found!");
            socket.emit("joinRoomFailure");
            return;
        } 
        player.roomId = roomId;
        socket.to(roomId).emit("newPlayer", { id : playerId });
        socket.join(roomId);
        socket.emit("joinRoomSuccess");
        socket.emit("getPlayers", getPlayersInRoom(roomId)); 
        console.log("Join room success!");

    });
    socket.on("leaveRoom", data => {

    });

    
    console.log(players);

});

function getPlayer(playerId){
    for(var i = 0; i < players.length; i++){
        if(players[i].id === playerId){
            return players[i];
        }
    }
    return null;
}

function getPlayersInRoom(roomId){
    return players.filter(val => val.roomId === roomId);
}

class Player {
    constructor(id, x, y, vx, vy, state, animation, frame) {
        this.id = id;
        this.roomId = -1;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.state = state;
        this.animation = animation;
        this.frame = frame;
        this.grappleX = null;
        this.grappleY = null;
    }
}