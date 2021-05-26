const express = require('express');
const bodyParser = require('body-parser');

const PORT = process.env.PORT || 4000;
const app = express();

app.use(bodyParser.urlencoded({
  extended: true
}));

app.get('/', (req, res) => {
  res.send(`
  <html>
  <head>
    <title>CSRF demo application</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
  </head>
  <body>
      <h3>Attacker's page on a different domain</h3>
      <form action="http://localhost:3000/entry" method="POST">
        <div>
          <label for="message">Enter a message</label>
          <input id="message" name="message" type="text" />
        </div>
        <input type="submit" value="Submit" />
      </form>
  </body>
</html>
  `);
});

app.get('/test', (req, res) => {
  res.redirect(301, 'http://localhost:3000/test');
});

app.listen(PORT, () => {
  console.log(`Listening on http://localhost:${PORT}`);
});