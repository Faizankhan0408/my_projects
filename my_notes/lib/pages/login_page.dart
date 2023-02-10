import 'package:flutter/material.dart';
import 'package:my_notes/User_creds/login_check.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({Key? key}) : super(key: key);

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {

  loginFuntion(String email,String password){
    UserLogin user=UserLogin();
    user.userSignInWithEmail(email,password);
  }

  TextEditingController nameController=TextEditingController();
  TextEditingController passwordController=TextEditingController();

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child:  Scaffold(
       body: Column(
         children: [
           const Text("Login"),
           const SizedBox(height: 10,),

           // name field
           Padding(
             padding: const EdgeInsets.all(8.0),
             child: TextField(controller: nameController,
              decoration: const InputDecoration(
                border: OutlineInputBorder(),
                hintText:"Enter your email"
              ),
             ),
           ),
           const SizedBox(height: 10,),

           //password field
           Padding(
             padding: const EdgeInsets.all(8.0),
             child: TextField(controller: passwordController,
               decoration: const InputDecoration(
                   border: OutlineInputBorder(),
                   hintText:"Enter your password"
               ),
             ),
           ),
           const SizedBox(height: 10,),

           TextButton(onPressed: (){
             String email=nameController.text;
             String password=passwordController.text;
             loginFuntion(email,password);
           }, child: const Text("Login"))
         ],
       )
     )
    );
  }
}
