var express = require('express');
app = express();

app.get('/', function (req, res) {
  res.send('Hello World! s2i 23:32');
});

app.listen(8080, function () {
  console.log('Example app listening on port 8080!');
});

