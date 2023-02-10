import 'package:firebase_auth/firebase_auth.dart';

import '../models/User_model.dart';

class UserLogin{
  FirebaseAuth auth = FirebaseAuth.instance;

  void userSignInWithEmail(String email,String password) async{
    try {
      UserCredential userCredential = await FirebaseAuth.instance.signInWithEmailAndPassword(
          email:email,
          password: password
      );
      print("Successfully login");
    } on FirebaseAuthException catch (e) {
      if (e.code == 'user-not-found') {
        print('No user found for that email.');
      } else if (e.code == 'wrong-password') {
        print('Wrong password provided for that user.');
      }
    }
  }

  void createAccountUsingEmail(UserCreds user) async{
    try{
      UserCredential userCredential=await FirebaseAuth.instance.createUserWithEmailAndPassword(email:user.email , password: user.password);
      print("created successfully");

    } on FirebaseAuthException catch (e){
      print(e);
    }
  }

}