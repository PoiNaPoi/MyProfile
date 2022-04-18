import 'package:flutter/material.dart';
import 'index.dart';

class firstPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Nightingale'),
      ),
      body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Image(image: AssetImage('assets/icon/icon.png')),
              Text('Enjoy with everyone by live stream!!.',
                  style: TextStyle(fontSize: 20.0)),
              FlatButton(
                color: Colors.blue,
                textColor: Colors.white,
                disabledColor: Colors.grey,
                disabledTextColor: Colors.black,
                padding: EdgeInsets.all(8.0),
                splashColor: Colors.blueAccent,
                onPressed: () {
                  Navigator.pop(context);
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => IndexPage()),
                  );
                },
                child: Text(
                  "Let's start!!",
                  style: TextStyle(fontSize: 20.0),
                ),
              )
            ],
          )),
    );
  }
}