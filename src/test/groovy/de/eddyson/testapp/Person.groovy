package de.eddyson.testapp

import groovy.transform.TupleConstructor

@TupleConstructor
class Person {

  String firstName;
  String lastName;

  public static final Person JOHN_DOE = new Person("John", "Doe")

  public static final Person JANE_ROE = new Person("Jane", "Roe")
}