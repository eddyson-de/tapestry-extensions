package de.eddyson.testapp.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

public class TaggingDemo {

  @Property
  @Persist
  List<String> tags;

  @OnEvent(value = "completeTags")
  Object completions(String query){
    ArrayList<String> list = new ArrayList<>();
    list.add("Foo");
    list.add("Funny");
    if(query.toLowerCase().startsWith("f")) return list;

    list.clear();
    list.add("Gene");
    list.add("Gaile");
    if(query.toLowerCase().startsWith("g")) return  list;
    return null;
  }
}
