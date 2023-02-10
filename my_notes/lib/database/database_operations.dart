import 'package:firebase_database/firebase_database.dart';
import 'package:my_notes/Utilities/global_variable.dart';
import 'package:my_notes/models/User_model.dart';
class DatabaseOperations{
  DatabaseReference ref=FirebaseDatabase.instance.ref();
  
  CreateUser(UserCreds user){
    ref.child(GlobalVariable.USERS).child(user.userId).set(user);
  }
}