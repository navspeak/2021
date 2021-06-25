const express= require('express');
const path= require('path');
const app = express();
const server = require('http').Server(app);
const io = require('socket.io')(server);
const btcRoute = require('./routes/btcRoute');
const port = 8080;
app.set('view engine', 'ejs');

app.get('/test', (req, res, next) => {
    res.json({"test": "val"})
});
app.use(express.static(path.join(__dirname, "public")));
app.use('/btc', btcRoute);
io.on('connection', function(socket) {
    console.log(`New Connection Made`);
    socket.emit('message-from-server', {
        greeting: 'Hello From Server'
    });
    socket.on('message-from-client', function(msg) {
         console.log(msg);
    })

})



server.listen(port, () => console.log(`Listening on ${port}`))