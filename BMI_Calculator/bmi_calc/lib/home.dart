import 'package:bmi_calc/loading.dart';
import 'package:bmi_calc/services/data_base.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  double weight = 0, height = 0, bmi = 0, _cardOpacity = 0;
  int _widgetIndex = 0;
  final _formKey = GlobalKey<FormState>();
  @override
  Widget build(BuildContext context) {
    var dimensions = MediaQuery.of(context).size;
    return Scaffold(
        backgroundColor: Colors.teal,
        appBar: AppBar(
              backgroundColor: Colors.teal[800],
              elevation: 0,
              title: Center(child: Text('BMI Calculator')),
          ),
        body: Form(
            key: _formKey,
            child: Column(
              children: <Widget> [
                Container(
                  margin: EdgeInsets.fromLTRB(dimensions.width*0.06, dimensions.height*0.05, dimensions.width*0.06, dimensions.height*0.025),
                  child: TextFormField(
                    cursorColor: Colors.teal[800],
                    validator: (val) {
                      try {
                        if (double.parse(val) <= 0){
                          return 'Weight should be positive';
                        }
                        return null;
                      } catch (e){
                        return 'Weight cannot contain alphabets or symbols';
                      }
                    },
                    onChanged: (val) {
                      try {
                        setState((){ weight = double.parse(val); _cardOpacity = 0;});
                      } catch (e){}
                    },
                    decoration: InputDecoration(
                        enabledBorder: OutlineInputBorder(
                          borderSide: BorderSide(
                            color: Colors.teal[800],
                            width: 3,
                          ),
                        ),
                        focusedBorder: OutlineInputBorder(
                          borderSide: BorderSide(
                            color: Colors.black,
                            width: 2,
                          ),
                        ),
                        fillColor: Colors.teal[50],
                        filled: true,
                        hintText: 'Enter weight in KGs'),
                    keyboardType: TextInputType.number,
                  ),
                ),
                Container(
                  margin: EdgeInsets.symmetric(horizontal: dimensions.width*0.06, vertical: dimensions.height*0.025),
                  child: TextFormField(
                    cursorColor: Colors.teal[800],
                    validator: (val){
                      try {
                        if (double.parse(val) <= 0){
                          return 'Height should be positive';
                        }
                        return null;
                      } catch (e){
                        return 'Height cannot contain alphabets or symbols';
                      }
                    },
                    onChanged: (val) {
                      try {
                        setState(() {height = double.parse(val); _cardOpacity = 0;});
                      } catch (e){}
                    },
                    decoration: InputDecoration(
                        enabledBorder: OutlineInputBorder(
                          borderSide: BorderSide(
                            color: Colors.teal[800],
                            width: 3,
                          ),
                        ),
                        focusedBorder: OutlineInputBorder(
                          borderSide: BorderSide(
                            color: Colors.black,
                            width: 2,
                          ),
                        ),
                        fillColor: Colors.teal[50],
                        filled: true,
                        hintText: 'Enter height in cms'),
                    keyboardType: TextInputType.number,
                  ),
                ),
                IndexedStack(
                  index: _widgetIndex,
                  children: <Widget> [
                    Center(
                      child: IconButton(
                        padding: EdgeInsets.symmetric(horizontal: dimensions.width*0.06, vertical: dimensions.height*0.025),
                        color: Colors.black,
                        icon: Icon(Icons.exit_to_app),
                        iconSize: 55,
                        tooltip: 'Press to calculate BMI',
                        onPressed: () async {
                          if (_formKey.currentState.validate()){
                            setState(() => _widgetIndex = 1);
                            double temp = (weight/((height/100)*(height/100)));
                            dynamic a = DatabaseService(data : bmi.toStringAsFixed(2));
                            var abc = await a.addTimeStamp();
                            print(temp);
                            setState((){bmi = temp; _cardOpacity = 1; _widgetIndex = 0;});
                          }
                        }
                      ),
                    ),
                      Loading(),
                  ]
                ),
                Container(
                  child: Card(
                    elevation: 0,
                    color: Colors.teal,
                    child: Padding(
                      padding: EdgeInsets.symmetric(horizontal: dimensions.width*0.06, vertical: dimensions.height*0.02),
                      child: AnimatedOpacity(
                        duration: Duration(milliseconds: 500),
                        opacity: _cardOpacity,
                        child: SelectableText(
                          'BMI = ${bmi.toStringAsFixed(2)}',
                          style: TextStyle(
                            fontSize: 22,
                          ),
                        ),
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
      );
  }
}
