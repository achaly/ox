/**
 * Created by love on 16-6-28.
 */
var express = require('express');
var qiniu = require("qiniu");
//var qiniu = require("../../../node_modules/leanengine/node_modules/leancloud-storage/node_modules/qiniu");
var router = express.Router();
var bucket = 'oxcoder';
var domain = 'o8uspr2vl.bkt.clouddn.com';
qiniu.conf.ACCESS_KEY = '6Z0Puj4w1BgwJqEogbHg-RXt51zJmEniZbxmoheG';
qiniu.conf.SECRET_KEY = 'I-3RpxTNa-tyDd5MnEXvxDeFln16CRhpNl4dA23C';
/* GET users listing. */
router.get('/', function(req, res, next) {
    token = uptoken(bucket);
    var ret = {code:0,message:'',result:{token:token,domain:domain}};
    res.send(ret);
});
router.post('/',function(req, res, next){
    token = uptoken(bucket);
    var ret = {code:0,message:'',result:{token:token,domain:domain}};
    res.send(ret);
});
module.exports = router;

function uptoken(bucket) {
    var putPolicy = new qiniu.rs.PutPolicy(bucket);
    return putPolicy.token();
}
function parseRequestParam(req) {
    if(req.method=="GET"){
        key = req.query.key;
 //       console.log("key is "+key);
        return {key:''}
    }else{
        console.log("method is POST");
        key = req.body.key;
//        console.log("key is "+key);
        return {key:key}
    }
}
