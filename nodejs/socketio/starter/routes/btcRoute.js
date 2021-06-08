const express = require('express');
const router = express.Router();

router
 .route('/')
 .get(function(req, res, next){
    console.log('Test')
    res.render('btc');
  })

module.exports = router;