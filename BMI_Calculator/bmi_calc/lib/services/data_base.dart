import 'package:cloud_firestore/cloud_firestore.dart';


class DatabaseService {
  DatabaseService({this.data});
  String data, stamp;
  final CollectionReference bmiCollection = Firestore.instance.collection('BMIs');
  Future<dynamic> addTimeStamp () async {
    stamp = DateTime.now().toString();
    return await bmiCollection.document(stamp).setData({
      'BMI' : data,
      'time stamp' : stamp,
    });
  }
}