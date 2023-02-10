import 'package:contacts/services/db_helper.dart';
import 'package:flutter/material.dart';

import 'Contact.dart';

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {

  List<Contact>contacts = List.empty(growable: true);
  var selectedIndex=-1;
  // controller for edit texts
  TextEditingController nameController=TextEditingController();
  TextEditingController numberController=TextEditingController();

  void _refreshContacts()async{
    final data=await DbHelper.getContacts();
    setState(() {
      for(Map<String,dynamic> contact in data){
       var newContact= Contact.fromMap(contact);
       contacts.add(newContact);
      }
    });
  }

  void addContact(Contact contact) async {
    try{
      final data = await DbHelper.addContact(contact);
    }catch(err){
      print("Unable to add contact because of : $err");
    }
  }

  void deleteContact(String number) async{
    try{
      final data = await DbHelper.deleteContact(number);
    }catch(err){
      print("Unable to delete contact because of : $err");
    }
  }

  void updateContact(Contact newContact,String number)async{
    try{
      final data = await DbHelper.updateContact(newContact,number);
    }catch(err){
      print("Unable to delete contact because of : $err");
    }
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _refreshContacts();
    print(".. number of contacts ${contacts.length}");
  }

  @override
  Widget build(BuildContext  context) {
    return  Scaffold(
      appBar: AppBar(
        title: const Text("Contacts"),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          children: [
              TextField(
              controller: nameController,
              decoration: const InputDecoration(
                  border: OutlineInputBorder(
                      borderRadius: BorderRadius.all(Radius.circular(10))
                  ),
                  hintText: "Contact Name"
              ),
            ),
            const SizedBox(height: 10),

             TextField(
               controller: numberController,
              keyboardType: TextInputType.number,
              maxLength: 10,
              decoration: const InputDecoration(
                border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(10))
                ),
                hintText: "Contact Number",
              ),
            ),
            const SizedBox(height: 10),

            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                ElevatedButton(onPressed: () {
                  String name=nameController.text.trim();
                  String number=numberController.text.trim();
                  if(name.isNotEmpty && number.isNotEmpty){
                    setState(() {
                      final contact=Contact(name: name, number: number);
                      contacts.add(Contact(name: name, number: number));
                      addContact(contact);
                      nameController.text="";
                      numberController.text="";
                    });
                  }
                }, child: const Text('Save')),
                ElevatedButton(onPressed: () {
                  String name=nameController.text.trim();
                  String number=numberController.text.trim();
                  if(name.isNotEmpty && number.isNotEmpty){
                    setState(() {
                      String prevNumber=contacts[selectedIndex].number;
                     contacts[selectedIndex].name=name;
                     contacts[selectedIndex].number=number;
                      nameController.text="";
                      numberController.text="";
                      updateContact(contacts[selectedIndex],prevNumber);
                    });
                  }
                }
                , child: const Text('Update')),
              ],
            ),
            const SizedBox(height: 10),

            contacts.isEmpty?const Text("No contact yet...",style: TextStyle(fontSize: 22),):
            Expanded(
              child: ListView.builder(
                itemBuilder: (context, index) => getRow(index),
                itemCount: contacts.length,),
            ),
          ],
        ),
      ),
    );
  }

  Widget getRow(int index) {
    return Card(
      child: ListTile(
        leading: CircleAvatar(
          backgroundColor: index%2==0?Colors.amber:Colors.red,
          child: Text(contacts[index].name[0].toUpperCase(),style: const TextStyle(fontWeight: FontWeight.bold),),
        ),
        title: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(contacts[index].name,style: const TextStyle(fontWeight: FontWeight.bold),),
            Text(contacts[index].number),
          ],
        ),
        trailing: SizedBox(
          width: 70,
          child: Row(
            children:  [
             InkWell(
                 onTap: (){
                   setState(() {
                     nameController.text=contacts[index].name;
                     numberController.text=contacts[index].number;
                     selectedIndex=index;
                   });
                 },
                 child: const Icon(Icons.edit,)),
             InkWell(
                 onTap: (){
                   setState(() {
                     contacts.remove(contacts[index]);
                     deleteContact(contacts[index].number);
                   });
                 },
                 child: Icon(Icons.delete))
            ],
          ),
        ),
      ),
    );
  }
}
