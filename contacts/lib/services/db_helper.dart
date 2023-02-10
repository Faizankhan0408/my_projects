import 'dart:io';
import 'package:flutter/cupertino.dart';
import 'package:path/path.dart' as Path;
import 'package:sqflite/sqflite.dart' as sql;

import '../Contact.dart';

class DbHelper{

  // creating table if not exists
  static Future<void> createTable(sql.Database database) async{
    await database.execute("""
        CREATE TABLE contacts(
        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        name TEXT not null,
        number VARCHAR not null
        )
    """);
  }

  //creating database if not exists
  static Future<sql.Database> db() async{
    return sql.openDatabase(
      'ContactsDb.db',
      version: 1,
      onCreate: (sql.Database database,int version) async{
        await createTable(database);
      }
    );
  }

  static Future<int> addContact(Contact contact) async{
    final db=await DbHelper.db();
    final data=contact.toMap();
    final id=await db.insert('contacts', // table name
        data,                            // contact which we are inserting
        conflictAlgorithm: sql.ConflictAlgorithm.replace
    );
    return id;
  }

  static Future<List<Map<String,dynamic>>> getContacts() async{
    final db=await DbHelper.db();
    return db.query('contacts',orderBy: "name");
  }

  static Future<List<Map<String,dynamic>>> getContact(String number) async{
    final db=await DbHelper.db();
    return db.query(
      'contacts',
      where: "number=?",
      whereArgs: [number],
      limit: 1
    );
  }

  static Future<int> updateContact(Contact contact,String number) async{
    final db=await DbHelper.db();
    final data=contact.toMap();
    final result = await db.update('contacts',
      data,
      where:'number=?',
      whereArgs: [number]
    );
    return result;
  }

  static Future<void> deleteContact(String number) async{
    final db=await DbHelper.db();
    try{
      await db.delete('contacts',where: 'number=?',whereArgs: [number]);
    }catch(err){
      debugPrint("Something went wrong when deleting an item: $err");
    }
   }

}