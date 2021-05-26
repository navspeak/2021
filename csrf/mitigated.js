const express = require('express');
const bodyParser = require('body-parser');
const csurf = require('csurf');
const cookieParser = require('cookie-parser');

const PORT = process.env.PORT || 3000;
const app = express();

const csrfMiddleware = csurf({cookie: true});

app.use(bodyParser.urlencoded({
  extended: true
}));
app.use(cookieParser());
app.use(csrfMiddleware);


app.get('/', (req, res) => {
  res.send(`
    <h1>Hello World</h1>
    <form action="/entry" method="POST">
      <div>
        <label for="message">Enter a message</label>
        <input id="message" name="message" type="text" />
      </div>
      <input type="submit" value="Submit" />
      <input type="hidden" name="_csrf" value="${req.csrfToken()}" />
    </form>
  `);
});

app.get('/test', (req, res) => {
  console.log(`GET /test}`);
  res.status(200).json({"a":"b"});
});

app.post('/entry', (req, res) => {
  console.log(`Message received: ${req.body.message}`);
  res.send(`CSRF token used: ${req.body._csrf}, Message received: ${req.body.message}`);
});

app.listen(PORT, () => {
  console.log(`Listening on http://localhost:${PORT}`);
});