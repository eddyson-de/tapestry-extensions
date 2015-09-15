package de.eddyson.tapestry.extensions.integration.pages

import de.eddyson.tapestrygeb.TapestryPage

class TaggingDemo extends TapestryPage{
  static at = {title == "Tagging Demo"}
  static url = "taggingdemo"
  static content = {
    search {$("input[type='search']")}
    taglist {$("#taglist")}
    submit {$("input[type='submit']")}
  }
}
