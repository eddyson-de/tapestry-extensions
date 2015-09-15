package de.eddyson.testapp.pages

import de.eddyson.testapp.Person;

class InfiniGridDemo {

  def getPeople() {
    [
      Person.JOHN_DOE,
      Person.JANE_ROE
      ] * 10
    }
  }
