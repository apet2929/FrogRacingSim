
let app = require("express")();
var server = require("http").Server(app);
var io = require("socket.io")(server, {
    allowEIO3: true
});

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
    socket.emit("getPlayers", players);

    let player = new Player(socket.id, 0, 10); 
    players.push(player);

    socket.broadcast.emit("newPlayer", player);
    socket.on('disconnect', function() {
        console.log("Player disconnected!" + socket.id);
        socket.broadcast.emit("playerDisconnected", { id: socket.id });
        for(var i = 0; i < players.length; i++){
            if(players[i].id === socket.id){
                players.splice(i, 1);
            }
        }

    });

    socket.on("tick", function(data) {
        data.id = socket.id;
        socket.broadcast.emit("tick", data);
        console.log("Tick recieved! data = " + JSON.stringify(data, null, 4));
        for(var i = 0; i < players.length; i++){
            if(players[i].id === socket.id){
                players[i].x = data.x;
                players[i].y = data.y;
            }
        }
    });

    
    console.log(players);

});

class Player {
    constructor(id, x, y) {
        this.id = id;
        this.x = x;
        this.y = y;

    }
}