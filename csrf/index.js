const express = require('express');
const bodyParser = require('body-parser');

const PORT = process.env.PORT || 3000;
const app = express();

app.use(bodyParser.urlencoded({
  extended: true
}));

app.get('/', (req, res) => {
  res.send(`
    <h1>Hello World</h1>
    <form action="/entry" method="POST">
      <div>
        <label for="message">Enter a message</label>
        <input id="message" name="message" type="text" />
      </div>
      <input type="submit" value="Submit" />
    </form>
  `);
});

app.post('/entry', (req, res) => {
  console.log(`Message received: ${req.body.message}`);
  res.send(`Message received: ${req.body.message}`);
});

app.listen(PORT, () => {
  console.log(`Listening on http://localhost:${PORT}`);
});