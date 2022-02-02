const express = require('express');
const path = require('path');
const dotenv = require('dotenv');
const parser = require('body-parser');
const mysql = require('mysql');
const cors = require('cors');
const Twit = require('twit');
const movielens = require('movielens');
var SpotifyWebApi = require('spotify-web-api-node');
const app = express();
const route = express.Router();

app.use(cors());
app.use(parser.json());
app.use(express.urlencoded({ extended: true }));
app.use('/', route);
dotenv.config();

var status = 0; // 0: non user, 1: Admin, 2:user
var Using_name = "";

var spotifyApi = new SpotifyWebApi({
    clientId: '3b9f02301bab4d7f8006d379f9d769e2',
    clientSecret: '6dc478e315f54c3d881a66b2de84bb4f',
    redirectUri: 'http://localhost:8080/callback'
});
let cookie='ml4_session=f5b2528f44e9199c54335c97d25bbcaef5a31ffb_5b9f779c-f950-452c-90d5-27abb7396831; Max-Age=31536000; Expires=Sun, 09 May 2021 14:15:18 GMT; Path=/; Secure; HTTPOnly';

movielens.login('anakin_rew@hotmail.com', 'student013').then((cookie)=>{}).catch(function(err) {
    // console.error(err);
});

var ConnectDB = mysql.createConnection({
    host: process.env.HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME
});

spotifyApi.clientCredentialsGrant().then((data)=>{
    spotifyApi.setAccessToken(data.body['access_token']);
});

ConnectDB.connect((err)=>{
    if(err) throw err;
    console.log("Connected DB: " + process.env.DB_NAME);
});

app.listen(process.env.PORT, function () {
    console.log("Start service: http://localhost:" + process.env.PORT);
})

route.get('/', (req, res)=>{
    res.sendFile(path.join(__dirname + '/HTML/index.html'));
});

route.get('/SettingUser', (req, res)=>{
    if(status==1) res.sendFile(path.join(__dirname + '/HTML/SettingUser.html'));
});

route.get('/Main', (req, res)=>{
    if(status) res.sendFile(path.join(__dirname + '/HTML/Main.html'));
});

route.post('/Search', (req, res)=>{
    var search = req.body.movie.moviename;
    var params = {
        q: search,
        hasRated: 'no'
    };
    movielens.explore(cookie, params).then((data)=>{
        var sendout = data.data.searchResults[0];
        return res.send({
            error: false,
            data: sendout,
            message: ''
        });
    });
});

route.post('/Music', (req, res)=>{
    var search = req.body.movie.moviename;
    spotifyApi.searchTracks('album:' + search).then((data)=>{
        return res.send({
            error: false,
            data: data.body.tracks.items[0].album.id,
            message: ""
        });
    });
});

route.post('/login', (req, res)=>{
    var FromHTML = req.body.USER;
    var User = FromHTML.username;
    var Pass= FromHTML.password;
    if (!User || !FromHTML) {
        return res.status(400).send({
            error: true,
            message: 'Please Fill Username And Password!!!'
        });
    }
    ConnectDB.query('SELECT * FROM user', (error, results)=>{
        if (error) throw error;
        var message = "";
        for(var i=0;i<results.length;i++){
            if(results[i].ID == User){
                if(results[i].password == Pass) {
                    if(results[i].role == "Admin") status = 1;
                    else if(results[i].role == "user") status = 2;
                    message = "Login Complete!!!";
                    Using_name = results[i].Fname + " " + results[i].Lname;
                    console.log(Using_name + ": Login Success!!!");
                }
                else message = "Wrong Password!!!";
                break;
            }
            else message = "User not found!!!"
        }
        return res.send({
            error: false,
            data: status,
            message: message
        });
    });
});

route.get('/logout', (req, res)=>{
    console.log(Using_name + ": Logout Success!!!");
    status = 0;
    Using_name = "";
    return res.send({
        error: false,
        data: status,
        message: "Logout Success!!!"
    });
});

route.post('/insert', (req, res)=>{
    let FromHTML = req.body.USER;
    const User = FromHTML.username;
    const Pass= FromHTML.password;
    const Role = FromHTML.role;
    const Fnam = FromHTML.firstname;
    const Lnam = FromHTML.lastname;
    const Emal = FromHTML.email;
    if (!FromHTML) {
        return res.status(400).send({
            error: true,
            message: 'Please Fill Data!!!'
        });
    }
    ConnectDB.query(`INSERT INTO user VALUES (
        '${User}',
        '${Pass}',
        '${Role}',
        '${Fnam}',
        '${Lnam}',
        '${Emal}')`,
        (error, results)=>{
            if (error) throw error;
            return res.send({
                error: false, data: results.affectedRows,
                message: 'New User Has Been Added!!!'
            });
        });
});

route.put('/update', (req, res)=>{
    let FromHTML = req.body.USER;
    var User = FromHTML.username;
    var Pass= FromHTML.password;
    var Role = FromHTML.role;
    var Fnam = FromHTML.firstname;
    var Lnam = FromHTML.lastname;
    var Emal = FromHTML.email;
    if (!User || !FromHTML) {
        return res.status(400).send({
            error: true,
            message: 'Please provide ID!!!'
        });
    }
    ConnectDB.query('SELECT * FROM user where ID = ?', [User], (error, results)=>{
        if (error) throw error;
        if(Pass=="") Pass = results[0].password;
        if(Role=="") Role = results[0].role;
        if(Fnam=="") Fnam = results[0].Fname;
        if(Lnam=="") Lnam = results[0].Lname;
        if(Emal=="") Emal = results[0].email;

        ConnectDB.query(`UPDATE user SET   
        password = '${Pass}', 
        role = '${Role}', 
        Fname = '${Fnam}',
        Lname = '${Lnam}',
        email = '${Emal}' WHERE ID = ?`, [User], (error, results)=>{
            if (error) throw error;
            return res.send({
                error: false,
                message: User + ': has been updated successfully.'
            });
        });
    });
});

route.delete('/delete', (req, res)=>{
    var User = req.body.USER.username;
    if (!User) {
        return res.status(400).send({
            error: true,
            message: 'Please Provide ID To Delete'
        });
    }
    ConnectDB.query('DELETE FROM user WHERE ID = ?', [User], (error, results)=>{
        if (error) throw error;
        return res.send({ 
            error: false,
            message: User + ': has been removed successfully.'
        });
    });
});

route.get('/selectall', (req, res)=>{
    ConnectDB.query('SELECT * FROM user', (error, results)=>{
        if (error) throw error;
        return res.send({
            error: false,
            data: results,
            message: 'All User List'
        });
    });
});

route.post('/Tweet', (req, res)=>{
    const apikey = '9VN9zUHOsxjusjr1R74bmj2sg';
    const apiseckey = 'pDwDdBil8rCHVUx3NHcYAn4N4StFg77kbxJTdZnjnWPrwJLaLg';
    const accToken = '1030081015270662144-zeRNLXAcdfXl0HyCex6qC3X1P1rcUK';
    const accTokensec = '837mChg8ghz16y0WxVp8m8uH5OkXTDoNf2tY3NiQaeIns';

    var ConnectTwit = new Twit({
        consumer_key: apikey,
        consumer_secret: apiseckey,
        access_token: accToken,
        access_token_secret: accTokensec
    });

    var search = req.body.movie.moviename;
    // var mewanfuc = req.body.tweet;
    var today = new Date();
    var dd = String(today.getDate()-7).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();
    today = yyyy + '-' + mm + '-' + dd;
    var queue = search+' since:'+today;

    ConnectTwit.get('search/tweets', { q: `${queue}`, count: 20 }, (err, data, response)=>{
        let Comment =[];
        for(let i=0; i<data.statuses.length; i++){
            Comment[i] = "<b>" + data.statuses[i].user.name +
                    "</b> <br>"+ data.statuses[i].text + 
                    "<br> <b>created at:</b> " + 
                    data.statuses[i].created_at;
        }
        let output2;
        output2 = "<h1>Tweets result</h1>" + "<br>";
        for(let i=0; i<Comment.length; i++){
            x = i + 1;
            output2 += x + ". " + Comment[i] + "<br>";
            output2 += "---------------------------" + "<br>";
        }
        return res.send({
            error: false,
            data: output2,
            message: ''
        });
    });
});

