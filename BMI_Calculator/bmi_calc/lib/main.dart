import 'package:flutter/material.dart';
import 'package:bmi_calc/home.dart';

void main() => runApp(MyApp());


class MyApp extends StatelessWidget {
  int weight = 0;
  @override
  Widget build(BuildContext context){
    return MaterialApp(
      builder: (context, child) => SafeArea(child: child),
      home: HomePage(),
    );
  }
}
