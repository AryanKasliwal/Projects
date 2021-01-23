import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';


class Loading extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var dimensions = MediaQuery.of(context).size;
    return Container(
      color: Colors.teal,
      child: Center(
        child: Padding(
          padding: EdgeInsets.symmetric(horizontal: dimensions.width*0.06, vertical: dimensions.height*0.025),
          child: Column(
            children: [
              SpinKitCubeGrid(
                color: Colors.black,
                size: 55,
              ),
              Text(
                'Data uploading to firebase',
                style: TextStyle(
                  color: Colors.black,
                  fontSize: 14,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
