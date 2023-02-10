class Contact{
   String name;
   String number;
  Contact({required this.name, required this.number});
  Map<String,dynamic> toMap(){
    return{
      'name':name,
      'number':number
    };
  }

   static Contact fromMap(Map<String,dynamic> contact){
    return Contact(name: contact['name'], number: contact['number']);
   }
}