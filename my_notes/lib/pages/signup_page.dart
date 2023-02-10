import 'package:flutter/material.dart';
import 'package:my_notes/User_creds/login_check.dart';
import 'package:my_notes/models/User_model.dart';
import 'package:my_notes/pages/home_page.dart';
class SignUpPage extends StatefulWidget {
  const SignUpPage({Key? key}) : super(key: key);

  @override
  State<SignUpPage> createState() => _SignUpPageState();
}

class _SignUpPageState extends State<SignUpPage> {
  TextEditingController nameController=TextEditingController();
  TextEditingController emailController=TextEditingController();
  TextEditingController passwordController=TextEditingController();

  void createAccount(UserCreds user){
   UserLogin newUser=UserLogin();
   newUser.createAccountUsingEmail(user);
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: SafeArea(
          child:  Scaffold(
              body: Padding(
                padding: const EdgeInsets.only(top: 100),
                child: Column(
                  children: [
                    const Text("SignUp"),
                    const SizedBox(height: 10,),

                    // name field
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: TextField(controller: nameController,
                        decoration: const InputDecoration(
                            border: OutlineInputBorder(),
                            hintText:"Enter your name"
                        ),
                      ),
                    ),
                    const SizedBox(height: 10,),

                    //password field
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: TextField(controller: emailController,
                        decoration: const InputDecoration(
                            border: OutlineInputBorder(),
                            hintText:"Enter your email"
                        ),
                      ),
                    ),
                    const SizedBox(height: 10,),

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
                      String email=emailController.text;
                      String password=passwordController.text;
                      String name=nameController.text;
                      UserCreds user=UserCreds(email: email, password: password,userId: "");
                      user.name=name;
                      createAccount(user);
                      nameController.text="";
                      emailController.text="";
                      passwordController.text="";
                      Navigator.of(context).pushAndRemoveUntil(MaterialPageRoute(
                          builder: (context) => const HomePage()), (Route route) => false);
                    }, child: const Text("Sign up"))
                  ],
                ),
              )
          )
      ),
    );
  }
}
